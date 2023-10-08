package com.nimang.pupa.common.tool.mp.query;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.AbstractLambdaWrapper;
import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.nimang.pupa.common.tool.mp.annotation.MPIgnore;
import com.nimang.pupa.common.tool.mp.annotation.MPQuery;
import com.nimang.pupa.common.tool.mp.enums.Compare;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Mybatis-Plus查询条件构造器扩展
 * 扩展功能：
 * 1、使用@MPQuery注解为入参定义查询规则
 * 2、使用@MPIgnore注解忽略特定入参
 * 3、未使用@MPQuery及@MPIgnore注解标注的字段，将默认使用本字段名的下划线形式作为列名，默认规则为EQ
 * 4、使用@MPQuery的logic属性，可配置逻辑查询方式，默认为and
 * 5、自动构建查询规则 wrapper.build(queryB0)，列名转为用"`"安全包裹的小写形式
 * 6、支持数据库列名定义规则 wrapper.eq("name","张三")
 * 7、支持映射定义规则 wrapper.eq(Person::getName,"张三")
 *
 *    //示例：
 *      //使用"LIKE"为条件查询此字段对应的列
 *      @MPQuery(rule = Compare.LIKE)
 *      //使用"LIKE_LEFT"为条件查询“name”列
 *      @MPQuery(column = "name", rule = Compare.LIKE_LEFT)
 *      //使用"BETWEEN"为条件查询“time”列，起始值为当前字段值，截止值为指定字段"timeEnd"的值
 *      @MPQuery(column = "time", rule = Compare.BETWEEN, after = "timeEnd")
 *      //使用"LIKE_LEFT"为条件查询“name”、“phone”列，且两者为"and"关系
 *      @MPQuery(column = {"name","phone"}, rule = Compare.LIKE_LEFT)
 *      //使用"LIKE_LEFT"为条件查询“name”、“phone”列，且两者为"or"关系
 *      @MPQuery(column = {"name","phone"}, rule = Compare.LIKE_LEFT, logic = Compare.OR)
 *      //使用"LIKE"为条件查询“name”列,使用"LIKE_LEFT"为条件查询“phone”列，且两者为"and"关系
 *      @MPQuery(column = {"name","phone"}, rule = {Compare.LIKE,Compare.LIKE_LEFT})
 *      //使用"LIKE"为条件查询“name”列,使用"LIKE_LEFT"为条件查询“phone”列，且两者为"or"关系
 *      @MPQuery(column = {"name","phone"}, rule = {Compare.LIKE,Compare.LIKE_LEFT}, logic = Compare.OR)
 *
 * @date 2023/2/22
 */
public class MPQueryWrapper<T> extends AbstractLambdaWrapper<T, MPQueryWrapper<T>> implements Query<MPQueryWrapper<T>, T, SFunction<T, ?>> {
    private static final String SPLIT_STR = ",";
    private static final String WRAP_STR = "`";
    private SharedString sqlSelect;

    public MPQueryWrapper() {
        this((T) null);
    }

    public MPQueryWrapper(T entity) {
        this.sqlSelect = new SharedString();
        super.setEntity(entity);
        super.initNeed();
    }

    public MPQueryWrapper(Class<T> entityClass) {
        this.sqlSelect = new SharedString();
        super.setEntityClass(entityClass);
        super.initNeed();
    }

    MPQueryWrapper(T entity, Class<T> entityClass, SharedString sqlSelect, AtomicInteger paramNameSeq, Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString paramAlias, SharedString lastSql, SharedString sqlComment, SharedString sqlFirst) {
        this.sqlSelect = new SharedString();
        super.setEntity(entity);
        super.setEntityClass(entityClass);
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.sqlSelect = sqlSelect;
        this.paramAlias = paramAlias;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
        this.sqlFirst = sqlFirst;
    }

    @SafeVarargs
    public final MPQueryWrapper<T> select(SFunction<T, ?>... columns) {
        if (ArrayUtils.isNotEmpty(columns)) {
            this.sqlSelect.setStringValue(this.columnsToString(false, columns));
        }

        return (MPQueryWrapper)this.typedThis;
    }

    public MPQueryWrapper<T> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        if (entityClass == null) {
            entityClass = this.getEntityClass();
        } else {
            this.setEntityClass(entityClass);
        }

        Assert.notNull(entityClass, "entityClass can not be null", new Object[0]);
        this.sqlSelect.setStringValue(TableInfoHelper.getTableInfo(entityClass).chooseSelect(predicate));
        return (MPQueryWrapper)this.typedThis;
    }

    public String getSqlSelect() {
        return this.sqlSelect.getStringValue();
    }

    protected MPQueryWrapper<T> instance() {
        return new MPQueryWrapper(this.getEntity(), this.getEntityClass(), (SharedString)null, this.paramNameSeq, this.paramNameValuePairs, new MergeSegments(), this.paramAlias, SharedString.emptyString(), SharedString.emptyString(), SharedString.emptyString());
    }

    public void clear() {
        super.clear();
        this.sqlSelect.toNull();
    }


    public MPQueryWrapper<T> eq(SFunction<T,?> column, Object val) {
        return this.addCondition(ObjectUtil.isNotEmpty(val), column, SqlKeyword.EQ, val);
    }

    public MPQueryWrapper<T> eq(String column, Object val) {
        return this.addCondition(ObjectUtil.isNotEmpty(val), column, SqlKeyword.EQ, val);
    }

    public MPQueryWrapper<T> ne(SFunction<T,?> column, Object val) {
        return this.addCondition(ObjectUtil.isNotEmpty(val), column, SqlKeyword.NE, val);
    }

    public MPQueryWrapper<T> ne(String column, Object val) {
        return this.addCondition(ObjectUtil.isNotEmpty(val), column, SqlKeyword.NE, val);
    }

    public MPQueryWrapper<T> gt(SFunction<T,?> column, Object val) {
        return this.addCondition(ObjectUtil.isNotEmpty(val), column, SqlKeyword.GT, val);
    }

    public MPQueryWrapper<T> gt(String column, Object val) {
        return this.addCondition(ObjectUtil.isNotEmpty(val), column, SqlKeyword.GT, val);
    }

    public MPQueryWrapper<T> ge(SFunction<T,?> column, Object val) {
        return this.addCondition(ObjectUtil.isNotEmpty(val), column, SqlKeyword.GE, val);
    }

    public MPQueryWrapper<T> ge(String column, Object val) {
        return this.addCondition(ObjectUtil.isNotEmpty(val), column, SqlKeyword.GE, val);
    }

    public MPQueryWrapper<T> lt(SFunction<T,?> column, Object val) {
        return this.addCondition(ObjectUtil.isNotEmpty(val), column, SqlKeyword.LT, val);
    }

    public MPQueryWrapper<T> lt(String column, Object val) {
        return this.addCondition(ObjectUtil.isNotEmpty(val), column, SqlKeyword.LT, val);
    }

    public MPQueryWrapper<T> le(SFunction<T,?> column, Object val) {
        return this.addCondition(ObjectUtil.isNotEmpty(val), column, SqlKeyword.LE, val);
    }

    public MPQueryWrapper<T> le(String column, Object val) {
        return this.addCondition(ObjectUtil.isNotEmpty(val), column, SqlKeyword.LE, val);
    }

    public MPQueryWrapper<T> like(SFunction<T,?> column, Object val) {
        return this.likeValue(ObjectUtil.isNotEmpty(val), SqlKeyword.LIKE, column, val, SqlLike.DEFAULT);
    }

    public MPQueryWrapper<T> like(String column, Object val) {
        return this.likeValue(ObjectUtil.isNotEmpty(val), SqlKeyword.LIKE, column, val, SqlLike.DEFAULT);
    }

    public MPQueryWrapper<T> notLike(SFunction<T,?> column, Object val) {
        return this.likeValue(ObjectUtil.isNotEmpty(val), SqlKeyword.NOT_LIKE, column, val, SqlLike.DEFAULT);
    }

    public MPQueryWrapper<T> notLike(String column, Object val) {
        return this.likeValue(ObjectUtil.isNotEmpty(val), SqlKeyword.NOT_LIKE, column, val, SqlLike.DEFAULT);
    }

    public MPQueryWrapper<T> likeLeft(SFunction<T,?> column, Object val) {
        return this.likeValue(ObjectUtil.isNotEmpty(val), SqlKeyword.LIKE, column, val, SqlLike.LEFT);
    }

    public MPQueryWrapper<T> likeLeft(String column, Object val) {
        return this.likeValue(ObjectUtil.isNotEmpty(val), SqlKeyword.LIKE, column, val, SqlLike.LEFT);
    }

    public MPQueryWrapper<T> likeRight(SFunction<T,?> column, Object val) {
        return this.likeValue(ObjectUtil.isNotEmpty(val), SqlKeyword.LIKE, column, val, SqlLike.RIGHT);
    }

    public MPQueryWrapper<T> likeRight(String column, Object val) {
        return this.likeValue(ObjectUtil.isNotEmpty(val), SqlKeyword.LIKE, column, val, SqlLike.RIGHT);
    }

    public MPQueryWrapper<T> notLikeLeft(SFunction<T,?> column, Object val) {
        return this.likeValue(ObjectUtil.isNotEmpty(val), SqlKeyword.NOT_LIKE, column, val, SqlLike.LEFT);
    }

    public MPQueryWrapper<T> notLikeLeft(String column, Object val) {
        return this.likeValue(ObjectUtil.isNotEmpty(val), SqlKeyword.NOT_LIKE, column, val, SqlLike.LEFT);
    }

    public MPQueryWrapper<T> notLikeRight(SFunction<T,?> column, Object val) {
        return this.likeValue(ObjectUtil.isNotEmpty(val), SqlKeyword.NOT_LIKE, column, val, SqlLike.RIGHT);
    }

    public MPQueryWrapper<T> notLikeRight(String column, Object val) {
        return this.likeValue(ObjectUtil.isNotEmpty(val), SqlKeyword.NOT_LIKE, column, val, SqlLike.RIGHT);
    }

    public MPQueryWrapper<T> between(SFunction<T,?> column, Object val1, Object val2) {
        return this.maybeDo(ObjectUtil.isNotEmpty(val1) && ObjectUtil.isNotEmpty(val2), () -> {
            this.appendSqlSegments(this.columnToSqlSegment(column), SqlKeyword.BETWEEN, () -> {
                return this.formatParam((String)null, val1);
            }, SqlKeyword.AND, () -> {
                return this.formatParam((String)null, val2);
            });
        });
    }

    public MPQueryWrapper<T> between(String column, Object val1, Object val2) {
        return this.maybeDo(ObjectUtil.isNotEmpty(val1) && ObjectUtil.isNotEmpty(val2), () -> {
            this.appendSqlSegments(this.columnToSqlSegment(column), SqlKeyword.BETWEEN, () -> {
                return this.formatParam((String)null, val1);
            }, SqlKeyword.AND, () -> {
                return this.formatParam((String)null, val2);
            });
        });
    }

    public MPQueryWrapper<T> notBetween(SFunction<T,?> column, Object val1, Object val2) {
        return this.maybeDo(ObjectUtil.isNotEmpty(val1) && ObjectUtil.isNotEmpty(val2), () -> {
            this.appendSqlSegments(this.columnToSqlSegment(column), SqlKeyword.NOT_BETWEEN, () -> {
                return this.formatParam((String)null, val1);
            }, SqlKeyword.AND, () -> {
                return this.formatParam((String)null, val2);
            });
        });
    }

    public MPQueryWrapper<T> notBetween(String column, Object val1, Object val2) {
        return this.maybeDo(ObjectUtil.isNotEmpty(val1) && ObjectUtil.isNotEmpty(val2), () -> {
            this.appendSqlSegments(this.columnToSqlSegment(column), SqlKeyword.NOT_BETWEEN, () -> {
                return this.formatParam((String)null, val1);
            }, SqlKeyword.AND, () -> {
                return this.formatParam((String)null, val2);
            });
        });
    }

    public MPQueryWrapper<T> isNull(SFunction<T,?> column) {
        return this.maybeDo(true, () -> {
            this.appendSqlSegments(this.columnToSqlSegment(column), SqlKeyword.IS_NULL);
        });
    }

    public MPQueryWrapper<T> isNull(String column) {
        return this.maybeDo(true, () -> {
            this.appendSqlSegments(this.columnToSqlSegment(column), SqlKeyword.IS_NULL);
        });
    }

    public MPQueryWrapper<T> isNotNull(SFunction<T,?> column) {
        return this.maybeDo(true, () -> {
            this.appendSqlSegments(this.columnToSqlSegment(column), SqlKeyword.IS_NOT_NULL);
        });
    }

    public MPQueryWrapper<T> isNotNull(String column) {
        return this.maybeDo(true, () -> {
            this.appendSqlSegments(this.columnToSqlSegment(column), SqlKeyword.IS_NOT_NULL);
        });
    }

    public MPQueryWrapper<T> in(SFunction<T,?> column, Collection<?> coll) {
        return this.maybeDo(ObjectUtil.isNotEmpty(coll), () -> {
            this.appendSqlSegments(this.columnToSqlSegment(column), SqlKeyword.IN, this.inExpression(coll));
        });
    }

    public MPQueryWrapper<T> in(String column, Collection<?> coll) {
        return this.maybeDo(ObjectUtil.isNotEmpty(coll), () -> {
            this.appendSqlSegments(this.columnToSqlSegment(column), SqlKeyword.IN, this.inExpression(coll));
        });
    }

    public MPQueryWrapper<T> in(SFunction<T,?> column, Object... values) {
        return this.maybeDo(ObjectUtil.isNotEmpty(values), () -> {
            this.appendSqlSegments(this.columnToSqlSegment(column), SqlKeyword.IN, this.inExpression(values));
        });
    }

    public MPQueryWrapper<T> in(String column, Object... values) {
        return this.maybeDo(ObjectUtil.isNotEmpty(values), () -> {
            this.appendSqlSegments(this.columnToSqlSegment(column), SqlKeyword.IN, this.inExpression(values));
        });
    }

    public MPQueryWrapper<T> notIn(SFunction<T,?> column, Collection<?> coll) {
        return this.maybeDo(ObjectUtil.isNotEmpty(coll), () -> {
            this.appendSqlSegments(this.columnToSqlSegment(column), SqlKeyword.NOT_IN, this.inExpression(coll));
        });
    }

    public MPQueryWrapper<T> notIn(String column, Collection<?> coll) {
        return this.maybeDo(ObjectUtil.isNotEmpty(coll), () -> {
            this.appendSqlSegments(this.columnToSqlSegment(column), SqlKeyword.NOT_IN, this.inExpression(coll));
        });
    }

    public MPQueryWrapper<T> notIn(SFunction<T,?> column, Object... values) {
        return this.maybeDo(ObjectUtil.isNotEmpty(values), () -> {
            this.appendSqlSegments(this.columnToSqlSegment(column), SqlKeyword.NOT_IN, this.inExpression(values));
        });
    }

    public MPQueryWrapper<T> notIn(String column, Object... values) {
        return this.maybeDo(ObjectUtil.isNotEmpty(values), () -> {
            this.appendSqlSegments(this.columnToSqlSegment(column), SqlKeyword.NOT_IN, this.inExpression(values));
        });
    }

    protected MPQueryWrapper<T> likeValue(boolean condition, SqlKeyword keyword, String column, Object val, SqlLike sqlLike) {
        return this.maybeDo(condition, () -> {
            this.appendSqlSegments(this.columnToSqlSegment(column), keyword, () -> {
                return this.formatParam((String)null, SqlUtils.concatLike(val, sqlLike));
            });
        });
    }

    protected MPQueryWrapper<T> addCondition(boolean condition, String column, SqlKeyword sqlKeyword, Object val) {
        return this.maybeDo(condition, () -> {
            this.appendSqlSegments(this.columnToSqlSegment(column), sqlKeyword, () -> {
                return this.formatParam((String)null, val);
            });
        });
    }

    protected final ISqlSegment columnToSqlSegment(String column) {
        return () -> {
            return column;
        };
    }

    /**
     * 构建查询条件
     * @param query
     * @return
     */
    public MPQueryWrapper<T> build(Object query) {
        Field[] fields = query.getClass().getDeclaredFields();
        Map<String,MPCondition> allConditions = new LinkedHashMap<>();
        List<String> ordinaryFiled = new ArrayList<>();
        List<String> specialFiled = new ArrayList<>();
        for (Field field:fields){
            // 忽略静态字段
            if(Modifier.isStatic(field.getModifiers())){
                continue;
            }
            // 如字段值为空则忽略
            Object val = ReflectUtil.getFieldValue(query, field);
            if(ObjectUtil.isEmpty(val)){
                continue;
            }
            // 忽略添加@QueryIgonre注解的字段
            MPIgnore MPIgnore = field.getAnnotation(MPIgnore.class);
            if(MPIgnore != null){
                continue;
            }

            List<String> columns;
            List<Compare> rules;
            String after;
            Compare logic;
            MPQuery MPQuery = field.getAnnotation(MPQuery.class);
            if(MPQuery == null){
                // 取消默认功能，无@MPQuery注解时忽略忽略
                continue;
                // 如无忽略且没有@MPQuery注解，默认列名为当前字段转下划线，默认规则为EQ
//                columns = Collections.singletonList(StringUtils.toUnderScoreCase(field.getName()));
//                after = "";
//                rules = Collections.singletonList(Compare.EQ);
//                logic = Compare.AND;
            }else {
                columns =  Arrays.asList(MPQuery.column());
                if(ObjectUtil.isEmpty(columns) || ObjectUtil.isEmpty(columns.get(0))){
                    // 未指定数据库列名，默认列名为当前字段转下划线
                    columns = Collections.singletonList(StrUtil.toUnderlineCase(field.getName()));
                }
                after = MPQuery.after();
                rules = Arrays.asList(MPQuery.rule());
                logic = MPQuery.logic();
            }
            // 数据整理，特殊规则处理（BETWEEN、NOT_BETWEEN）
            MPCondition condition = new MPCondition();
            condition.setColumns(columns);
            condition.setRules(rules);
            condition.setLogic(logic);
            condition.setVal(val);
            condition.setAfter(after);
            if(rules.contains(Compare.BETWEEN) || rules.contains(Compare.NOT_BETWEEN)){
                specialFiled.add(field.getName());
            }else {
                ordinaryFiled.add(field.getName());
            }
            allConditions.put(field.getName(), condition);
        }
        // 先处理特殊规则
        buildSpecial(allConditions, ordinaryFiled, specialFiled);
        // 再处理普通规则
        buildOrdinary(allConditions, ordinaryFiled);

        return this;
    }

    private void buildSpecial(Map<String,MPCondition> allConditions, List<String> ordinaryFiled, List<String> specialFiled) {
        List<String> columns;
        List<Compare> rules;
        Compare logic;
        String afterFiled;
        Object val1,val2;
        for(String filed: specialFiled){
            MPCondition condition = allConditions.get(filed);
            rules = condition.getRules();
            columns = condition.getColumns();
            val1 = condition.getVal();
            afterFiled = condition.getAfter();
            MPCondition afterCondition = allConditions.get(afterFiled);
            val2 = afterCondition.getVal();
            ordinaryFiled.remove(afterFiled);
            if(columns.size()>1){
                logic = condition.getLogic();
                if(logic.equals(Compare.AND)){
                    buildAnd(rules, columns, val1, val2);
                }else if(logic.equals(Compare.OR)){
                    buildOr(rules, columns, val1, val2);
                }
            }else {
                dealCommonField(rules.get(0), columns.get(0), val1, val2);
            }
        }
    }

    private void buildOrdinary(Map<String,MPCondition> allConditions, List<String> ordinaryFiled) {
        List<String> columns;
        List<Compare> rules;
        Compare logic;
        Object val;
        for(String filed: ordinaryFiled){
            MPCondition condition = allConditions.get(filed);
            rules = condition.getRules();
            columns = condition.getColumns();
            val = condition.getVal();
            if(columns.size()>1){
                logic = condition.getLogic();
                if(logic.equals(Compare.AND)){
                    buildAnd(rules, columns, val, null);
                }else if(logic.equals(Compare.OR)){
                    buildOr(rules, columns, val, null);
                }
            }else {
                dealCommonField(rules.get(0), columns.get(0), val);
            }
        }
    }

    private void buildOr(List<Compare> rules, List<String> columns, Object val1, Object val2) {
        and(true,new Consumer<MPQueryWrapper<T>>() {
            @Override
            public void accept(MPQueryWrapper<T> tMPQueryWrapper) {
                for(int i=0;i<columns.size();i++){
                    String column = columns.get(i);
                    Compare rule;
                    if(rules.isEmpty()){
                        rule = Compare.EQ;
                    }else if (i>=rules.size()){
                        rule = rules.get(rules.size() - 1);
                    }else {
                        rule = rules.get(i);
                    }
                    Compare finalRule = rule;
                    tMPQueryWrapper.or(new Consumer<MPQueryWrapper<T>>() {
                        @Override
                        public void accept(MPQueryWrapper<T> tMPQueryWrapper) {
                            if(Compare.BETWEEN.equals(finalRule) || Compare.NOT_BETWEEN.equals(finalRule)){
                                tMPQueryWrapper.dealCommonField(finalRule, column, val1, val2);
                            }else {
                                tMPQueryWrapper.dealCommonField(finalRule, column, val1);
                            }
                        }
                    });
                }
            }
        });
    }
    private void buildAnd(List<Compare> rules, List<String> columns, Object val1, Object val2) {
        and(true,new Consumer<MPQueryWrapper<T>>() {
            @Override
            public void accept(MPQueryWrapper<T> tMPQueryWrapper) {
                for(int i=0;i<columns.size();i++){
                    String column = columns.get(i);
                    Compare rule;
                    if(rules.isEmpty()){
                        rule = Compare.EQ;
                    }else if (i>=rules.size()){
                        rule = rules.get(rules.size() - 1);
                    }else {
                        rule = rules.get(i);
                    }
                    if(Compare.BETWEEN.equals(rule) || Compare.NOT_BETWEEN.equals(rule)){
                        tMPQueryWrapper.dealCommonField(rule, column, val1, val2);
                    }else {
                        tMPQueryWrapper.dealCommonField(rule, column, val1);
                    }
                }
            }
        });
    }

    private void dealCommonField(Compare rule, String column, Object val) {
        column = wrapColumn(column);
        switch (rule) {
            case EQ : eq(column, val);break;
            case NE : ne(column, val);break;
            case GT : gt(column, val);break;
            case GE : ge(column, val);break;
            case LT : lt(column, val);break;
            case LE : le(column, val);break;
            case LIKE : like(column, val);break;
            case NOT_LIKE : notLike(column, val);break;
            case LIKE_LEFT : likeLeft(column, val);break;
            case LIKE_RIGHT : likeRight(column, val);break;
            case NOT_LIKE_LEFT : notLikeLeft(column, val);break;
            case NOT_LIKE_RIGHT : notLikeRight(column, val);break;
            case IN :
                if(val instanceof Collection){
                    in(column, (Collection<?>)val);
                }else {
                    in(column, val);
                }
                break;
            case NOT_IN :
                if(val instanceof Collection){
                    notIn(column, (Collection<?>)val);
                }else {
                    notIn(column, val);
                }
                break;
            case IS_NULL : isNull(column);break;
            case IS_NOT_NULL : isNotNull(column);break;
            default : throw new RuntimeException("不支持的类型:" + rule);
        }
    }
    private void dealCommonField(Compare rule, String column, Object val1, Object val2) {
        column = wrapColumn(column);
        switch (rule) {
            case BETWEEN: between(column, val1, val2);break;
            case NOT_BETWEEN: notBetween(column, val1, val2);break;
            default: throw new RuntimeException("不支持的类型:" + rule);
        }
    }

    private String wrapColumn(String column) {
        if(!column.startsWith(WRAP_STR)){
            column = WRAP_STR + column;
        }
        if(!column.endsWith(WRAP_STR)){
            column = column + WRAP_STR;
        }
        return column.toLowerCase();
    }
}
