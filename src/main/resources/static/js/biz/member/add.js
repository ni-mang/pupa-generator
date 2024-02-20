layui.use(['transfer','form'], function () {
    let form = layui.form,
        layer = layui.layer,
        transfer = layui.transfer,
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

    ajax(serverPath + "/pro/project-user/userForTransfer",
        "GET",
        {projectId:$("#projectId").val(), isMember:false},
        {
            successFn:function(res) {
                // 渲染
                transfer.render({
                    id: 'proUsers',
                    elem: '#userSearch',
                    data: res.data.data,
                    title: ['待选用户', '成员用户'],
                    value: res.data.value,
                    showSearch: true
                });
            }
        });

    // 触发提交运行扩展函数-获取穿梭框内容
    window.extendFun = function(data){
        const getData = transfer.getData('proUsers');
        const proUsers = [];
        getData.forEach(function(item){
            if(item.value){
                proUsers.push(item.value);
            }
        })
        data["userIds"] = proUsers;
    }
});