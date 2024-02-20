package com.nimang.pupa.business.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nimang.pupa.base.entity.*;
import com.nimang.pupa.base.model.gen.*;
import com.nimang.pupa.base.model.proMapper.ColumnMapper;
import com.nimang.pupa.base.service.*;
import com.nimang.pupa.business.service.BizGenService;
import com.nimang.pupa.common.enums.StatusEnum;
import com.nimang.pupa.common.enums.proTemp.ProTempTypeEnum;
import com.nimang.pupa.common.exception.ApiException;
import com.nimang.pupa.common.tool.render.EnjoyRender;
import com.nimang.pupa.common.tool.render.FreemarkerRender;
import com.nimang.pupa.common.tool.render.Render;
import com.nimang.pupa.common.tool.render.VelocityRender;
import com.nimang.pupa.common.util.ConvertUtil;
import com.nimang.pupa.common.util.LDTUtils;
import com.nimang.pupa.common.util.UserUtil;
import com.nimang.pupa.common.util.ZipUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BizGenServiceImpl implements BizGenService {
    private final IProProjectService proProjectService;
    private final IProProjectUserService proProjectUserService;
    private final IProDatasourceService proDatasourceService;
    private final IProTableService proTableService;
    private final IProFieldService proFieldService;
    private final IProTemplateService proTemplateService;
    private final IProMapperService proMapperService;
    private final IProConfigService proConfigService;

    /**
     * 全库生成
     * @param sourceId Long 数据源ID
     * @return Integer 同步表数量
     */
    @Override
    public void genAll(Long sourceId) {
        // 获取表
        List<ProTable> proTableList = proTableService.list(new LambdaQueryWrapper<ProTable>()
                .eq(ProTable::getSourceId, sourceId));
        // 获取字段
        List<ProField> proFieldList = proFieldService.list(new LambdaQueryWrapper<ProField>()
                .eq(ProField::getSourceId, sourceId)
                .orderByAsc(true, ProField::getOrdinalPosition));
        genZip(sourceId, proTableList, proFieldList);
    }

    /**
     * 生成指定表
     * @param tableIds List<Long> 表ID集合
     * @return Integer 同步表数量
     */
    @Override
    public void genTables(List<Long> tableIds) {
        // 获取表
        List<ProTable> proTableList = proTableService.listByIds(tableIds);
        // 获取字段
        List<ProField> proFieldList = proFieldService.list(new LambdaQueryWrapper<ProField>()
                .in(ProField::getTableId, tableIds)
                .orderByAsc(true, ProField::getOrdinalPosition));
        // 获取数据源
        Long sourceId = proTableList.get(0).getSourceId();

        genZip(sourceId, proTableList, proFieldList);
    }

    /**
     * 预览指定表生成的内容
     * @param tableId Long
     * @return List<GenTemplate>
     */
    @Override
    public List<GenTemplate> previewTable(Long tableId) {
        // 获取表
        ProTable proTable = proTableService.getById(tableId);
        // 获取字段
        List<ProField> proFieldList = proFieldService.list(new LambdaQueryWrapper<ProField>()
                .eq(ProField::getTableId, tableId)
                .orderByAsc(true, ProField::getOrdinalPosition));
        // 获取数据源
        Long sourceId = proTable.getSourceId();

        return doGen(sourceId, Collections.singletonList(proTable), proFieldList);
    }

    /**
     * 下载渲染后的文件
     * @param sourceId Long
     * @param proTableList List<ProTable>
     * @param proFieldList List<ProField>
     */
    public void genZip(Long sourceId, List<ProTable> proTableList, List<ProField> proFieldList) {
        List<GenTemplate> gtList = doGen(sourceId, proTableList, proFieldList);
        //下载
        downZip(gtList);
    }

    /**
     * 代码生成
     * @param sourceId Long
     * @param proTableList List<ProTable>
     * @param proFieldList List<ProField>
     * @return
     */
    public List<GenTemplate> doGen(Long sourceId, List<ProTable> proTableList, List<ProField> proFieldList) {
        if(ObjectUtil.isEmpty(proTableList)){
            throw new ApiException("无法获取表单信息");
        }
        if(ObjectUtil.isEmpty(proFieldList)){
            throw new ApiException("无法获取字段信息");
        }
        // 获取数据源
        ProDatasource proDatasource = proDatasourceService.getById(sourceId);
        if(proDatasource.getPassAbsentFlag()){
            proTableList = proTableList.stream().filter(ProTable::getExistFlag).collect(Collectors.toList());
            proFieldList = proFieldList.stream().filter(ProField::getExistFlag).collect(Collectors.toList());
        }
        // 获取项目
        ProProject proProject = proProjectService.getById(proDatasource.getProjectId());
        // 获取当前项目成员
        ProProjectUser projectUser = proProjectUserService.getOne(new LambdaQueryWrapper<ProProjectUser>()
                .eq(ProProjectUser::getUserId, UserUtil.getId())
                .eq(ProProjectUser::getProjectId, proProject.getId()));
        // 获取模板
        List<ProTemplate> templateList = proTemplateService.list(new LambdaQueryWrapper<ProTemplate>()
                .eq(ProTemplate::getConfigId, proProject.getConfigId())
                .eq(ProTemplate::getStatus, StatusEnum.STATUS_1.getCode()));
        if(ObjectUtil.isEmpty(templateList)){
            throw new ApiException("无法获取模板");
        }
        //获取映射关系
        Map<String, String> mapperMap = proMapperService.list(new LambdaQueryWrapper<ProMapper>()
                .eq(ProMapper::getConfigId, proProject.getConfigId())
                .eq(ProMapper::getBrand, proDatasource.getBrand()))
                .stream().collect(Collectors.toMap(ProMapper::getLang, ProMapper::getMapper));
        if(ObjectUtil.isEmpty(mapperMap)){
            throw new ApiException("未匹配到该数据库数据类型的映射关系，请至“配置管理-映射”中进行配置！");
        }

        // 文件生成
        List<GenTemplate> gtList = new ArrayList<>();
        Map<String, Object> genData = new HashMap<>();
        Map<Integer, Render> renderMap = getRenderMap(genData);
        new GenCommon().putGenData(genData);
        new GenProject(proProject, projectUser, proDatasource).putGenData(genData);

        Set<String> attrTypes = new HashSet<>();
        Set<String> importPaths = new HashSet<>();

        for(ProTable table: proTableList){
            // 获取当前表关联的字段
            List<ProField> currentFieldList = proFieldList.stream().filter(f -> f.getTableId().equals(table.getId())).collect(Collectors.toList());
            if(ObjectUtil.isEmpty(currentFieldList)){
                throw new ApiException("无法获取表【" + table.getTableName() + "】的字段信息");
            }
            GenTable genTable = new GenTable(table, currentFieldList);
            genTable.setAttrTypes(attrTypes);
            genTable.setImportPaths(importPaths);

            Map<String, ColumnMapper> columnMapperMap = null;
            Map<String,GenTable> genTableMap = new HashMap<>();

            // 逐一渲染模板
            List<GenTemplate> genTemplateList = new ArrayList<>();
            for(ProTemplate template: templateList){
                // 匹配当前表与模板程序语言的映射关系
                GenTable existTable = genTableMap.get(template.getContentLang());
                if(existTable != null){
                    // 已存在映射关系，直接使用
                    existTable.putGenData(genData);
                }else {
                    // 获取映射关系
                    String mapperJson = mapperMap.get(template.getContentLang());
                    if(StrUtil.isNotBlank(mapperJson)){
                        columnMapperMap = JSON.parseArray(mapperJson, ColumnMapper.class)
                                .stream().collect(Collectors.toMap(ColumnMapper::getColumnType, Function.identity()));
                    }
                    attrTypes.clear();
                    importPaths.clear();
                    // 根据映射关系转化字段数据，如无映射关系，默认属性类型为“String”
                    for (GenField genField : genTable.getFields()) {
                        genField.fillAttrType(columnMapperMap);
                        attrTypes.add(genField.getAttrType());
                        if(StrUtil.isNotBlank(genField.getImportPath())){
                            importPaths.add(genField.getImportPath());
                        }
                    }
                    genTable.putGenData(genData);
                    genTableMap.put(template.getContentLang(), ConvertUtil.clone(genTable));
                }
                // 执行渲染
                GenTemplate gt = new GenTemplate(template);
                Render render = renderMap.get(template.getTempType());
                if(ObjectUtil.isNotNull(render)){
                    try {
                        gt.setContent(render.render(gt.getContent()));
                        gt.setPath(render.render(gt.getPath()));
                    } catch (Exception e) {
                        throw new ApiException(render.getOfName() + " 渲染模板“" + template.getName() + " 》 " + table.getTableName() + "”失败，请检查模板“内容”或“文件生成路径”中使用的语法是否正确! " + e.getMessage());
                    }
                    genTemplateList.add(gt);
                }
            }
            gtList.addAll(genTemplateList);
        }
        // 更新配置生成次数
        ProConfig config = proConfigService.getById(proProject.getConfigId());
        config.setGenTimes(ObjectUtil.isNull(config.getGenTimes()) ? 1 : config.getGenTimes() + 1);
        proConfigService.updateById(config);
        return gtList;
    }

    /**
     * 获取公共模型数据
     * @return Map<String, Object>
     */
    public  Map<String, Object> getGenData(){
        Map<String, Object> genData = new HashMap<>();
        genData.put("date", LDTUtils.formatNow("yyyy-MM-dd"));
        genData.put("dateTime", LDTUtils.formatNow("yyyy-MM-dd HH:mm:ss"));
        return genData;
    }

    /**
     * 初始化各类模板引擎
     * @param genData Map<String, Object>
     * @return Map<Integer, Render>
     */
    public  Map<Integer, Render> getRenderMap(Map<String, Object> genData){
        Map<Integer, Render> renderMap = new HashMap<>();
        renderMap.put(ProTempTypeEnum.PTT_0.getCode(), new EnjoyRender(genData));
        renderMap.put(ProTempTypeEnum.PTT_1.getCode(), new FreemarkerRender(genData));
        renderMap.put(ProTempTypeEnum.PTT_2.getCode(), new VelocityRender(genData));
        return renderMap;
    }

    /**
     * 下载ZIP
     * @param genTemplateList List<GenTemplate>
     */
    public void  downZip(List<GenTemplate> genTemplateList){
        try {
            InputStream inputStream = ZipUtil.toZipForBytes(packZip(genTemplateList));
            ZipUtil.downloadZip(inputStream, "genCodes.zip");
        } catch (Exception e) {
            throw new ApiException("下载ZIP压缩包异常，建议检查“文件路径”是否正确:" + e.getMessage());
        }
    }

    /**
     * zip打包
     * @param genTemplateList List<GenTemplate>
     * @return List<ZipUtil.ZipByteInfo>
     */
    public List<ZipUtil.ZipByteInfo> packZip(List<GenTemplate> genTemplateList){
        List<ZipUtil.ZipByteInfo> byteInfoList = new ArrayList<>();
        for(GenTemplate template:genTemplateList){
            byteInfoList.add(new ZipUtil.ZipByteInfo(template.getPath(), template.getContent().getBytes(StandardCharsets.UTF_8)));
        }
        return byteInfoList;
    }
}
