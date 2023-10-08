package com.nimang.pupa.common.tool.webTool;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nimang.pupa.common.tool.typeFun.LambdaUtils;
import com.nimang.pupa.common.tool.typeFun.TypeFunction;
import com.nimang.pupa.common.util.SqlUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页数据
 */
@Data
public class PageQuery<T> extends BaseModel
{
    /**
     * 页码
     * @default 1
     */
    private Integer pageNum;

    /**
     * 单页数据量，不传不分页
     * @default 10
     */
    private Integer pageSize;

    /**
     * 排序规则，示例：按name正序并且按login_time倒序>>>name:1,login_time:0
     */
    private List<String> orderBy = new ArrayList<>();

    /**
     * 排序
     */
    @JsonIgnore
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     * url排序规则参数分隔符
     */
    private static final String ORDER_SPLIT = ":";

    /**
     * 正序标识
     */
    private static final String ORDER_ASC = "1";

    /**
     * 列名安全包裹
     */
    private static final String COLUMN_PACK = "`";


    public Integer getPageNum() {
        if (pageNum == null) {
            return 1;
        }
        return pageNum;
    }

    public Integer getPageSize() {
        if (pageSize == null) {
            pageSize = -1;
        }
        return pageSize;
    }

    /**
     * 将orderBy参数添加进排序规则
     */
    public void putOrderItem(){
        if(!this.orderBy.isEmpty()){
            for (String orderStr:orderBy) {
                String[] fields = orderStr.split(ORDER_SPLIT);
                if (fields.length == 2){
                    putOrderItem(fields[0], ObjectUtil.equal(fields[1], ORDER_ASC));
                }else {
                    putOrderItem(fields[0],true);
                }
            }
        }
    }

    /**
     * 添加排序规则
     * @param column
     * @param asc
     */
    public void putOrderItem(String column, final boolean asc){
        if(column!=null && !(column.startsWith(COLUMN_PACK) && column.endsWith(COLUMN_PACK))){
            column = COLUMN_PACK + column + COLUMN_PACK;
        }
        this.orderItems.add(new OrderItem(column,asc));
    }

    /**
     * 添加排序规则-lambda
     * @param tTypeFunction
     * @param asc
     */
    public void putOrderItem(final TypeFunction<T,?> tTypeFunction, final boolean asc){
        String column = LambdaUtils.getColumnName(tTypeFunction);
        putOrderItem(column,asc);
    }

    /**
     * 判断有误排序规则
     * @return Boolean
     */
    public Boolean isEmptyOrderItem(){
        putOrderItem();
        return this.orderItems.isEmpty();
    }



    /**
     * 组装分页组件
     * @return
     */
    public Page<T> getPage(){
        // 设置传入排序规则
        putOrderItem();
        // 检查排序规则内容合法性
        checkOrderItem();
        Page<T> page = new Page<>(this.getPageNum(),this.getPageSize());
        if(!orderItems.isEmpty()){
            page.addOrder(this.orderItems);
        }
        return page;
    }

    /**
     * 检查排序语句是否存在SQL注入
     */
    public void checkOrderItem(){
        for(OrderItem item:this.orderItems){
            String value = SqlUtil.escapeOrderBySql(item.getColumn());
            SqlUtil.filterKeyword(value);
        }
    }
}
