layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/cfg/extend/edit");
    $("#reqType").val("PUT");
    $("#tableName").val("extendTable");
    // 填充表单数据
    let reqData = {id:$("#id").val()};
    let url = serverPath + "/cfg/extend/get";
    ajax(url,"GET",reqData,
        {
            successFn:function(res) {
                let data = res["data"]
                form.val('editForm', data);
            }
        });


});