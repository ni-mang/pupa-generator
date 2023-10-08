layui.use(['form', 'table','miniPage','element'], function () {
    let $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        miniPage = layui.miniPage;

    form.render();
    let projectId = $("#projectId").val();
    let configId = $("#configId").val();
    let isOwner = $("#isOwner").val();
    let param = "?projectId=" + projectId;

    // 当前弹出层，防止ID被覆盖
    const parentIndex = layer.index;

    table.render({
        elem: '#memberTable',
        url: serverPath + "/pro/project-user/query" + param,
        toolbar: '#memberToolbar',
        defaultToolbar: ['filter', 'exports', 'print'],
        request: {
            pageName: 'pageNum', // 页码
            limitName: 'pageSize' // 每页数据条数
        },
        parseData: function (res){
            checkLogin(res);
            return parseData(res);
        },
        cols: [[
            // {type: "checkbox", width: 50},
            {field: 'id', hide: true},
            {field: 'nickName', width: 400, title: '昵称'},
            {field: 'author', width: 400, title: '署名'},
            {field: 'roleDesc', width: 400, title: '角色'},
            {field: 'createTime', width: 400, title: '创建时间', sort: true},
            {title: '操作', minWidth: 180, toolbar: '#memberTableBar', align: "center", fixed:"right"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });

    // 监听搜索操作
    form.on('submit(memberSearchBtn)', function (data) {
        //执行搜索重载
        table.reload('memberTable', {
            page: {
                curr: 1
            }
            , where: {
                "nickName": data.field.nickName,
            }
        }, 'data');

        return false;
    });

    /**
     * toolbar事件监听
     */
    table.on('toolbar(memberTable)', function (obj) {
        if (obj.event === 'add') {   // 监听添加操作
            let content = miniPage.getHrefContent('member/add.html' + param);
            let index = layer.open({
                title: '添加',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['800px', '400px'],
                content: content,
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'delete') {  // 监听删除操作
            let checkStatus = table.checkStatus('memberTable')
                , data = checkStatus.data;
            layer.alert(JSON.stringify(data));
        }
    });

    //监听表格复选框选择
    table.on('checkbox(memberTable)', function (obj) {
        console.log(obj)
    });

    table.on('tool(memberTable)', function (obj) {
        let rowData = obj.data;
        if (obj.event === 'edit') {

            let content = miniPage.getHrefContent('member/edit.html' + param + '&configId=' + configId + "&isOwner=" + isOwner + '&id=' + rowData.id);

            let index = layer.open({
                title: rowData.nickName + '-编辑',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['1000px', '700px'],
                content: content
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
            return false;
        } else if (obj.event === 'delete') {
            layer.confirm('确定删除此成员？',
                {
                    title: "操作提示",
                    icon: 0,
                },
                function (index) {
                    var reqData = {
                        id : rowData.id
                    };
                    //请求接口
                    let url = serverPath + "/pro/project-user/remove";
                    ajax(url,"DELETE",reqData,
                        {
                            successFn:function(res) {
                                layer.msg('删除成功', {
                                    icon: 6,
                                    time: 1000,
                                    shade: 0.3
                                }, function () {
                                    parent.layui.table.reload('memberTable');
                                });
                            },
                            completeFn:function(){
                                layer.close(index);
                            }
                        })
                    return false;
            });
        } else if (obj.event === 'transfer') {
            layer.confirm('确定将项目所有权转移给当前成员？',
                {
                    title: "操作提示",
                    icon: 0,
                },
                function (index) {
                    var reqData = {
                        id : rowData.id
                    };
                    //请求接口
                    let url = serverPath + "/pro/project-user/transfer";
                    ajax(url,"PUT",reqData,
                        {
                            successFn:function(res) {
                                layer.msg('操作成功', {
                                    icon: 6,
                                    time: 1000,
                                    shade: 0.3
                                }, function () {
                                    parent.layui.table.reload('projectTable');
                                    layer.close(parentIndex);
                                });
                            },
                            completeFn:function(){
                                layer.close(index);
                            }
                        })
                    return false;
                });
        } else if (obj.event === 'quit') {
            layer.confirm('确定退出当前项目？',
                {
                    title: "操作提示",
                    icon: 0,
                },
                function (index) {
                    var reqData = {
                        id : rowData.id
                    };
                    //请求接口
                    let url = serverPath + "/pro/project-user/remove";
                    ajax(url,"DELETE",reqData,
                        {
                            successFn:function(res) {
                                layer.msg('退出成功', {
                                    icon: 6,
                                    time: 1000,
                                    shade: 0.3
                                }, function () {
                                    parent.layui.table.reload('projectTable');
                                    layer.close(parentIndex);
                                });
                            },
                            completeFn:function(){
                                layer.close(index);
                            }
                        })
                    return false;
                });
        }
    });

});