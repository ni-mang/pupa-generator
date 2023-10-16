package com.nimang.pupa.base.model.proDatasource;

import com.nimang.pupa.base.model.proExtend.ProExtendValueBO;
import com.nimang.pupa.common.annotation.ValidEnum;
import com.nimang.pupa.dbExtends.DatasourceBrandEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * 数据源-新增BO
 * @author LinLaichun
 * @date 2023-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProDatasourceAddBO implements Serializable{
	private static final long serialVersionUID = 1L;

    /**
     * 项目ID
     */
    @NotNull(message = "缺少“项目ID”")
    private Long projectId;

    /**
     * 连接名
     */
    @NotNull(message = "缺少“连接名”")
    private String name;

    /**
     * 数据库品牌
     * @see DatasourceBrandEnum
     */
    @NotNull(message = "缺少“数据库品牌”")
    @ValidEnum(enumClass = DatasourceBrandEnum.class)
    private Integer brand;

    /**
     * 地址
     */
    @NotBlank(message = "缺少“地址”")
    @Size(max = 100,message="“地址”不应超过100个字符")
    private String mainAddr;

    /**
     * 库名
     */
    @NotBlank(message = "缺少“库名”")
    @Size(max = 30,message="“库名”不应超过30个字符")
    private String schema;

    /**
     * 链接后缀
     * 如：serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=true
     */
    @Size(max = 200,message="“链接后缀”不应超过200个字符")
    private String urlSuffix;

    /**
     * 账号
     */
    @NotBlank(message = "缺少“账号”")
    @Size(max = 30,message="“账号”不应超过30个字符")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "缺少“密码”")
    @Size(max = 30,message="“密码”不应超过30个字符")
    private String password;

    /**
     * 忽略前缀
     * 如：tb_,sys_
     */
    @Size(max = 30,message="“忽略前缀”不应超过30个字符")
    private String prefix;

    /**
     * 扩展配置
     */
    @Valid
    private List<ProExtendValueBO> extendList;

}