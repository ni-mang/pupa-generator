layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/cfg/extend/add");
    $("#reqType").val("POST");
    $("#tableName").val("extendTable");
});