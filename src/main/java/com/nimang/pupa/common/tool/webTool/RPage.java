package com.nimang.pupa.common.tool.webTool;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.Collection;

/**
 * 响应信息主体
 *
 * @author fangzg
 */
public class RPage<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final int SUCCESS = HttpStatus.SUCCESS;

    private int code;

    private String msg;

    /**
     * 查获总数据量
     */
    private Long total;

    /**
     * 返回数据
     */
    private Collection<T> data;


    public static <T> RPage<T> ok(Collection<T> data)
    {
        return restResult(data, null);
    }

    public static <T> RPage<T> ok(Page<T> page)
    {
        return restResult(page, null);
    }

    public static <T> RPage<T> ok(Page<T> page, String msg)
    {
        return restResult(page, msg);
    }


    private static <T> RPage<T> restResult(Page<T> page, String msg)
    {
        RPage<T> apiResult = new RPage<>();
        apiResult.setCode(RPage.SUCCESS);
        apiResult.setData(page.getRecords());
        apiResult.setTotal(page.getTotal()==0? page.getRecords().size():page.getTotal());
        apiResult.setMsg(msg);
        return apiResult;
    }
    private static <T> RPage<T> restResult(Collection<T> data, String msg)
    {
        RPage<T> apiResult = new RPage<>();
        apiResult.setCode(RPage.SUCCESS);
        apiResult.setData(data);
        apiResult.setTotal((long) data.size());
        apiResult.setMsg(msg);
        return apiResult;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Collection<T> getData()
    {
        return data;
    }

    public void setData(Collection<T> data)
    {
        this.data = data;
    }

    public Boolean isError()
    {
        return !isSuccess();
    }

    public Boolean isSuccess()
    {
        return RPage.SUCCESS == getCode();
    }
}
