layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/user/edit");
    $("#reqType").val("PUT");
    // 填充表单数据
    let reqData = {id:$("#id").val()};
    let url = serverPath + "/user/get";
    ajax(url,"GET",reqData,
        {
            successFn:function(res) {
                console.log(res)
                form.val('editForm', res["data"])
            }
        });

});
