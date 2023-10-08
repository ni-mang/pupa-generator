layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        $ = layui.$;

});

function pageRender(){
    // 设置页面关键值
    $("#reqUrl").val("/cfg/config/add");
    $("#reqType").val("POST");
}