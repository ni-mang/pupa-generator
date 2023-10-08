layui.use(['form'], function () {
    let form = layui.form,
        table = layui.table,
        layer = layui.layer,
        $ = layui.$;

    // 设置页面关键值
    $("#tableName").val("mapperTable");




    // 填充表单数据
    let reqData = {id:$("#id").val()};
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
                renderMapper(table,'#mapperDiv',data.brand,data.mapperList, true)
            }
        });
});