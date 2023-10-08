layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/column-type/add");
    $("#reqType").val("POST");

    // 加载配置数据下拉选项
    renderSelect(
        $("select[name='brand']"),
        "/enum/getEnums",
        {label:'datasource_brand'},
        "desc", "value",
        '',
        function (){
            form.render("select")
        }
    );
});