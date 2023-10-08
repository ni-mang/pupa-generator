package com.nimang.pupa.base.model.gen;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.nimang.pupa.base.entity.ProExtend;
import com.nimang.pupa.base.entity.ProField;
import com.nimang.pupa.base.entity.ProTable;
import com.nimang.pupa.base.model.proExtend.ProExtendValueBO;
import com.nimang.pupa.common.enums.proConfig.ProExtendScopeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenTable {

    /**
     * 表名称
     * 如：sys_user_run
     */
    private String table_name;

    /**
     * 类名(首字母大写)
     * 如：sys_user_run => SysUserRun
     */
    private String classNameUp;

    /**
     * 类名(首字母小写)
     * 如：sys_user_run => sysUserRun
     */
    private String className;

    /**
     * 类名(中间横杆)
     * 如：sys_user_run => sys-user-run
     */
    private String classNameHyphen;

    /**
     * 无前缀名
     * 如：user_run
     */
    private String infix_name;

    /**
     * 无前缀类名(首字母大写)
     * 如：user_run => UserRun
     */
    private String infixNameUp;

    /**
     * 无前缀类名(首字母小写)
     * 如：user_run => userRun
     */
    private String infixName;

    /**
     * 无前缀类名(中间横杆)
     * 如：user_run => user-run
     */
    private String infixNameHyphen;

    /**
     * 模块
     */
    private String module;

    /**
     * 中文名
     */
    private String cnName;

    /**
     * 表注释
     */
    private String comments;

    /**
     * 主键
     */
    private GenField pk;

    /**
     * 属性
     */
    private List<GenField> fields;

    /**
     * 属性类型集合
     */
    private Set<String> attrTypes;

    /**
     * 属性类型引入集合
     */
    private Set<String> importPaths;

    /**
     * 扩展配置
     */
    private Map<String, String> ext = new HashMap<>();



    public GenTable(ProTable proTable, List<ProField> fieldList) {
        // 表名常用处理
        this.table_name = proTable.getTableName().toLowerCase();
        this.className = StrUtil.toCamelCase(this.table_name);
        this.classNameUp = StrUtil.upperFirst(this.className);
        this.classNameHyphen = this.table_name.replace("_", "-");
        this.infix_name = proTable.getInfixName().toLowerCase();
        this.infixName = StrUtil.toCamelCase(this.infix_name);
        this.infixNameUp = StrUtil.upperFirst(this.infixName);
        this.infixNameHyphen = this.infix_name.replace("_", "-");
        this.comments = proTable.getTableComment();
        this.module = proTable.getModule();
        this.cnName = proTable.getCnName();

        // 列处理
        this.fields = new ArrayList<>();
        this.attrTypes = new HashSet<>();
        for(ProField proField: fieldList){
            GenField field = new GenField(proField);
            if(proField.getPrimary()){
                this.pk = field;
            }
            fields.add(field);
        }
        // 扩展配置
        if(StrUtil.isNotBlank(proTable.getExtend())){
            List<ProExtendValueBO> extendList = JSON.parseArray(proTable.getExtend(), ProExtendValueBO.class);
            this.ext = extendList.stream().collect(Collectors.toMap(ProExtendValueBO::getKey, ProExtendValueBO::getValue));
        }
    }

    public void putGenData(Map<String, Object> genData) {
        genData.put("table", this);
    }

    public static void showInfo(List<GenDataInfo> infoList, List<ProExtend> extendList){
        String start = "table";
        infoList.add(new GenDataInfo(start + ".table_name", "表名", "例：sys_user_run"));
        infoList.add(new GenDataInfo(start + ".classNameUp","类名(首字母大写)","例：SysUserRun"));
        infoList.add(new GenDataInfo(start + ".className","类名(首字母小写)","例：sysUserRun"));
        infoList.add(new GenDataInfo(start + ".classNameHyphen","类名(中间横杆)","例：sys-user-run"));
        infoList.add(new GenDataInfo(start + ".infix_name","无前缀表名","例：user_run"));
        infoList.add(new GenDataInfo(start + ".infixNameUp","无前缀类名(首字母大写)","例：UserRun"));
        infoList.add(new GenDataInfo(start + ".infixName","无前缀类名(首字母小写)","例：userRun"));
        infoList.add(new GenDataInfo(start + ".infixNameHyphen","无前缀类名(中间横杆)","例：user-run"));
        infoList.add(new GenDataInfo(start + ".pk","主键字段","详见field参数"));
        infoList.add(new GenDataInfo(start + ".fields","字段集合","详见field参数"));
        infoList.add(new GenDataInfo(start + ".attrTypes","属性类型集合","可用于判断当前类是否含有某类型的属性"));
        infoList.add(new GenDataInfo(start + ".importPaths","属性引用路径集合","例：java.time.LocalDate"));
        infoList.add(new GenDataInfo(start + ".module","模块"));
        infoList.add(new GenDataInfo(start + ".cnName","中文名"));
        infoList.add(new GenDataInfo(start + ".comments","表注释"));
        List<ProExtend> proExtends = extendList.stream().filter(e -> ProExtendScopeEnum.PES_3.equals(e.getScope())).collect(Collectors.toList());
        for(ProExtend extend:proExtends){
            infoList.add(new GenDataInfo(start + ".ext." + extend.getKey(),extend.getName(),extend.getComments()));
        }
    }
}
