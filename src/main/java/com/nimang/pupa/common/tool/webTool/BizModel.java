package com.nimang.pupa.common.tool.webTool;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 完整数据模型
 */
@Data
public class BizModel<T extends BaseModel> implements Serializable {

    /**
     * 接口版本 V1.0
     * @mock V1.0
     */
    @NotBlank(message = "version不能为空！")
    private String version;

    /**
     * 请求流水号
     * @mock @now("yyyyMMddHHmmss")@string('number', 6, 6)
     */
    @NotBlank(message = "requestNo不能为空！")
    private String requestNo;

    /**
     * 业务数据模型
     */
    @Valid
    private T data ;

    /**
     * 当前时间戳，格式为 yyyy-MM-dd HH:mm:ss
     * 例如：2022-01-01 12:00:00。
     * @mock @timestamp
     */
    @NotBlank(message = "timestamp不能为空！")
    private String timestamp;

}
