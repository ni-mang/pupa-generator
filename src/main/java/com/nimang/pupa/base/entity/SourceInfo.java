package com.nimang.pupa.base.entity;

import cn.hutool.core.util.StrUtil;
import com.nimang.pupa.dbExtends.DatasourceBrandEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SourceInfo {

    /**
     * 驱动
     */
    private String driver;

    /**
     * 地址
     */
    private String url;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
