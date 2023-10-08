layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/auth/resetPwd");
    $("#reqType").val("PUT");

    // 触发提交运行扩展函数-密码加密
    window.extendFun = function(data){
        data.newPassword = md5(data.newPassword);
        data.confirmPassword = md5(data.confirmPassword);
    }
});
