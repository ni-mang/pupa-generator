layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/pro/project-user/add");
    $("#reqType").val("POST");
    $("#tableName").val("memberTable");

    // 选择用户
    renderSelect(
        $("select[name='userId']"),
        "/pro/project-user/userForSelect",
        {projectId:$("#projectId").val(), isMember:false},
        "nickName", "id",
        "",
        function (){
            form.render("select")
        }
    );

});