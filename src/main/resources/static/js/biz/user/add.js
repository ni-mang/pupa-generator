layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/user/add");
    $("#reqType").val("POST");

    // 触发提交运行扩展函数-密码加密
    window.extendFun = function(data){
        data.password = md5(data.password);
    }
});