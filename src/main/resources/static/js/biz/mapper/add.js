layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        table = layui.table,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/cfg/mapper/add");
    $("#reqType").val("POST");
    $("#tableName").val("mapperTable");

    // 数据库品牌
    renderEnums(form, 'brand', 'datasource_brand', '');
    // 程序语言
    renderEnums(form, 'lang', 'temp_lang', '')

    // 切换数据库品牌
    form.on('select(brand)', function(obj){
        if(obj.value === ""){
            return;
        }
        // 获取数据库类型映射数据并渲染
        renderMapper(table,'#mapperDiv',obj.value)
    });

    // 触发提交运行扩展函数-获取扩展数据、类型映射数据
    window.extendFun = function(data){
        data["mapperList"] = table.cache["mapperDiv"];
    }
});