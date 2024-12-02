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
        var lang = $("select[name=lang]").val()
        if(obj.value === "" || lang === ""){
            return;
        }
        // 获取数据库类型映射数据并渲染
        renderMapper(table,'#mapperDiv',"/columnMapperCfg", obj.value, lang)
    });
    // 切换程序语言
    form.on('select(lang)', function(obj){
        var brand = $("select[name=brand]").val()
        if(obj.value === "" || brand === ""){
            return;
        }
        // 获取数据库类型映射数据并渲染
        renderMapper(table,'#mapperDiv',"/columnMapperCfg", brand, obj.value)
    });

    // 触发提交运行扩展函数-获取扩展数据、类型映射数据
    window.extendFun = function(data){
        data["mapperList"] = table.cache["mapperDiv"];
    }
});