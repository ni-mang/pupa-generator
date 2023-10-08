layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        $ = layui.$;

});
function pageRender(form,data){
    // 设置页面关键值
    $("#reqUrl").val("/cfg/config/edit");
    $("#reqType").val("PUT");
    // 填充表单数据
    let reqData = {id:data.id};
    let url = serverPath + "/cfg/config/get";
    ajax(url,"GET",reqData,
        {
            successFn:function(res) {
                let data = res["data"];
                form.val('editForm', data)

                // 选择用户
                renderSelect(
                    $("select[name='userId']"),
                    "/user/userForSelect",
                    {hasAdmin:true},
                    "nickName", "id",
                    data.userId,
                    function (){
                        form.render("select")
                    }
                );
            }
        });
}
