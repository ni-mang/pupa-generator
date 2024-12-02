layui.use(['form'], function () {
    let form = layui.form,
        table = layui.table,
        layer = layui.layer,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/cfg/mapper/edit");
    $("#reqType").val("PUT");
    $("#tableName").val("mapperTable");




    // 填充表单数据
    let id = $("#id").val();
    let reqData = {id:id};
    let url = serverPath + "/cfg/mapper/get";
    ajax(url,"GET",reqData,
        {
            successFn:function(res) {
                let data = res["data"]
                form.val('editForm', data);

                // 数据库品牌
                renderEnums(form, 'brand', 'datasource_brand', data.brand);
                // 程序语言
                renderEnums(form, 'lang', 'temp_lang', data.lang)

                // 获取数据库类型映射数据并渲染
                $("#mapper").val(JSON.stringify(data.mapperList));
                renderMapper(table,'#mapperDiv',"/columnMapperCfg", data.brand, data.lang, id, "", data.mapperList);
            }
        });

    // 切换数据库品牌
    form.on('select(brand)', function(obj){
        var lang = $("select[name=lang]").val()
        if(obj.value === "" || lang === ""){
            return;
        }
        // 获取数据库类型映射数据并渲染
        renderMapper(table,'#mapperDiv',"/columnMapperCfg", obj.value, lang, id)
    });
    // 切换程序语言
    form.on('select(lang)', function(obj){
        var brand = $("select[name=brand]").val()
        if(obj.value === "" || brand === ""){
            return;
        }
        // 获取数据库类型映射数据并渲染
        renderMapper(table,'#mapperDiv',"/columnMapperCfg", brand, obj.value, id)
    });

    // 触发提交运行扩展函数-获取扩展数据、类型映射数据
    window.extendFun = function(data){
        data["mapperList"] = table.cache["mapperDiv"];
    }


});