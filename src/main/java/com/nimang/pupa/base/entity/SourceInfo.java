package com.nimang.pupa.base.entity;

import cn.hutool.core.util.StrUtil;
import com.nimang.pupa.dbExtends.DatasourceBrandEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

    public SourceInfo(ProDatasource datasource) {
        DatasourceBrandEnum pdbEnum = DatasourceBrandEnum.getByCode(datasource.getBrand());
        StringBuilder urlBuild = new StringBuilder();
        urlBuild.append(pdbEnum.getPrefix());
        urlBuild.append(datasource.getMainAddr()).append(":").append(datasource.getPort());
        urlBuild.append("/").append(pdbEnum.getSchema());
        if(StrUtil.isNotBlank(datasource.getUrlSuffix())){
            urlBuild.append("?").append(datasource.getUrlSuffix());
        }
        this.driver = pdbEnum.getDriver();
        this.url = urlBuild.toString();
        this.username = datasource.getAccount();
        this.password = datasource.getPassword();
    }
}
