package com.nimang.pupa.base.model.gen;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.nimang.pupa.base.entity.ProDatasource;
import com.nimang.pupa.base.entity.ProExtend;
import com.nimang.pupa.base.entity.ProProject;
import com.nimang.pupa.base.entity.ProProjectUser;
import com.nimang.pupa.base.model.proExtend.ProExtendValueBO;
import com.nimang.pupa.common.enums.proConfig.ProExtendScopeEnum;
import com.nimang.pupa.common.util.UserUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenProject {
    /**
     * 项目名
     */
    private String name;

    /**
     * 项目说明
     */
    private String comments;

    /**
     * 作者署名
     */
    private String author;

    /**
     * 扩展配置
     */
    private Map<String, String> ext = new HashMap<>();

    public GenProject(ProProject proProject, ProProjectUser projectUser, ProDatasource proDatasource) {
        if(ObjectUtil.isNull(projectUser)){
            projectUser = new ProProjectUser();
        }
        this.name = proProject.getName();
        this.comments = proProject.getComments();
        this.author = StrUtil.isBlank(projectUser.getAuthor()) ? UserUtil.get().getNickName() : projectUser.getAuthor();

        List<ProExtendValueBO> extendList = new ArrayList<>();
        if(StrUtil.isNotBlank(proProject.getExtend())){
            extendList.addAll(JSON.parseArray(proProject.getExtend(), ProExtendValueBO.class));
        }
        if(StrUtil.isNotBlank(projectUser.getExtend())){
            extendList.addAll(JSON.parseArray(projectUser.getExtend(), ProExtendValueBO.class));
        }
        if(StrUtil.isNotBlank(proDatasource.getExtend())){
            extendList.addAll(JSON.parseArray(proDatasource.getExtend(), ProExtendValueBO.class));
        }
        this.ext = extendList.stream().collect(Collectors.toMap(ProExtendValueBO::getKey, ProExtendValueBO::getValue));
    }

    public void putGenData(Map<String, Object> genData) {
        genData.put("project", this);
    }
    public static void showInfo(List<GenDataInfo> infoList, List<ProExtend> extendList){
        String start = "project";
        infoList.add(new GenDataInfo(start + ".name", "项目名"));
        infoList.add(new GenDataInfo(start + ".comments", "项目说明"));
        infoList.add(new GenDataInfo(start + ".author", "作者署名"));
        List<ProExtend> proExtends = extendList.stream().filter(e -> ProExtendScopeEnum.PES_0.equals(e.getScope())
                || ProExtendScopeEnum.PES_1.equals(e.getScope())
                || ProExtendScopeEnum.PES_2.equals(e.getScope())).collect(Collectors.toList());
        for(ProExtend extend:proExtends){
            infoList.add(new GenDataInfo(start + ".ext." + extend.getKey(), extend.getName(), extend.getComments()));
        }
    }


}
