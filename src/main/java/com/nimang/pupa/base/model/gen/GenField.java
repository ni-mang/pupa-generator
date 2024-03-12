package com.nimang.pupa.base.model.gen;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.nimang.pupa.base.entity.ProExtend;
import com.nimang.pupa.base.entity.ProField;
import com.nimang.pupa.base.model.proExtend.ProExtendValueBO;
import com.nimang.pupa.base.model.proMapper.ColumnMapper;
import com.nimang.pupa.common.enums.proConfig.ProExtendScopeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenField {

    /**
     * 列名
     * 如：user_name
     */
    private String columnName;

    /**
     * 顺位
     */
    private Long ordinalPosition;

    /**
     * 默认值
     */
    private String columnDefault;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * 键
     * 索引
     */
    private String columnKey;

    /**
     * 主键类型
     * 常用区分主键值是否自增
     */
    private String idType;

    /**
     * 属性类型
     */
    private String attrType;

    /**
     * 引用路径
     */
    private String importPath;

    /**
     * 属性名(首字母小写)
     * 如：user_name => userName
     */
    private String attrName;

    /**
     * 属性名(首字母大写)
     * 如：user_name => UserName
     */
    private String attrNameUp;

    /**
     * 中文名
     */
    private String columnCn;

    /**
     * 注释
     */
    private String columnNotes;

    /**
     * 是否主键
     * 0:否,1:是
     */
    private Boolean primary;

    /**
     * 是否必填
     * 0:否,1:是
     */
    private Boolean requiredFlag;

    /**
     * 下限
     */
    private String boundMin;

    /**
     * 上限
     */
    private String boundMax;

    /**
     * 插入标识
     * 0:否,1:是
     */
    private Boolean insertFlag;

    /**
     * 展示标识
     * 0:否,1:是
     */
    private Boolean viewFlag;

    /**
     * 查询标识
     * 0:否,1:是
     */
    private Boolean queryFlag;

    /**
     * 枚举名
     */
    private String enumName;

    /**
     * 存在标识
     * 0:否,1:是
     */
    private Boolean existFlag;

    /**
     * 扩展配置
     */
    private Map<String, String> ext = new HashMap<>();

    public GenField(ProField proField) {
        this.columnName = proField.getColumnName();
        this.ordinalPosition = proField.getOrdinalPosition();
        this.columnDefault = proField.getColumnDefault();
        this.columnKey = proField.getColumnKey();
        this.columnType = proField.getDataType();
        this.idType = proField.getIdType();
        this.attrName = proField.getAttrName();
        this.attrNameUp = StrUtil.upperFirst(this.attrName);
        this.columnCn = proField.getColumnCn();
        this.columnNotes = proField.getColumnNotes();
        this.primary = proField.getPrimary();
        this.requiredFlag = proField.getRequiredFlag();
        this.boundMin = proField.getBoundMin();
        this.boundMax = proField.getBoundMax();
        this.insertFlag = proField.getInsertFlag();
        this.viewFlag = proField.getViewFlag();
        this.queryFlag = proField.getQueryFlag();
        this.enumName = proField.getEnumName();
        this.existFlag = proField.getExistFlag();

        if(StrUtil.isNotBlank(proField.getExtend())){
            List<ProExtendValueBO> extendList = JSON.parseArray(proField.getExtend(), ProExtendValueBO.class);
            this.ext = extendList.stream().collect(Collectors.toMap(ProExtendValueBO::getKey, ProExtendValueBO::getValue));
        }
    }

    /**
     * 填充属性类型及引用路径
     * @param columnMapperMap
     */
    public void fillAttrType(Map<String, ColumnMapper> columnMapperMap) {
        String attrType = "String";
        if (columnMapperMap != null){
            // 根据数据类型映射关系匹配属性类型
            ColumnMapper columnMapper = columnMapperMap.get(this.columnType);
            if(columnMapper != null){
                attrType = columnMapper.getAttrType();
                this.importPath = columnMapper.getImportPath();
            }
        }
        this.attrType = attrType;
    }

    public static void showInfo(List<GenDataInfo> infoList, List<ProExtend> extendList){
        String start = "field";
        infoList.add(new GenDataInfo(start + ".columnName", "列名", "例：user_name"));
        infoList.add(new GenDataInfo(start + ".ordinalPosition", "顺位"));
        infoList.add(new GenDataInfo(start + ".columnDefault", "默认值"));
        infoList.add(new GenDataInfo(start + ".idType", "主键类型","含：AUTO，NONE，INPUT，ASSIGN_ID，ASSIGN_UUID"));
        infoList.add(new GenDataInfo(start + ".columnKey", "约束", "例：PRI/UNI/MUL"));
        infoList.add(new GenDataInfo(start + ".columnType", "列类型（物理类型）", "例：varchar"));
        infoList.add(new GenDataInfo(start + ".attrType", "属性类型", "例：String"));
        infoList.add(new GenDataInfo(start + ".importPath", "属性类型引用路径", "例：java.time.LocalDate"));
        infoList.add(new GenDataInfo(start + ".attrName", "属性名(首字母小写)", "例：userName"));
        infoList.add(new GenDataInfo(start + ".attrNameUp", "属性名(首字母大写)", "例：UserName"));
        infoList.add(new GenDataInfo(start + ".columnCn", "中文名"));
        infoList.add(new GenDataInfo(start + ".columnNotes", "注释"));
        infoList.add(new GenDataInfo(start + ".primary", "是否主键", "0:否,1:是"));
        infoList.add(new GenDataInfo(start + ".requiredFlag", "是否必填", "0:否,1:是"));
        infoList.add(new GenDataInfo(start + ".boundMin", "下限", "数值或字符串长度最低限制"));
        infoList.add(new GenDataInfo(start + ".boundMax", "上限", "数值或字符串长度最高限制"));
        infoList.add(new GenDataInfo(start + ".insertFlag", "插入标识", "0:否,1:是，判断是否用于新增"));
        infoList.add(new GenDataInfo(start + ".viewFlag", "展示标识", "0:否,1:是，判断是否返回展示"));
        infoList.add(new GenDataInfo(start + ".queryFlag", "查询标识", "0:否,1:是，判断是否用于搜索"));
        infoList.add(new GenDataInfo(start + ".enumName", "枚举名", "用于匹配项目中的枚举类"));
        infoList.add(new GenDataInfo(start + ".existFlag", "存在标识", "判断属性在数据库是否有存在相应的列"));

        List<ProExtend> proExtends = extendList.stream().filter(e -> ProExtendScopeEnum.PES_4.equals(e.getScope())).collect(Collectors.toList());
        for(ProExtend extend:proExtends){
            infoList.add(new GenDataInfo(start + ".ext." + extend.getKey(),extend.getName(),extend.getComments()));
        }
    }

}
