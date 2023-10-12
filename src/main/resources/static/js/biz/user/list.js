layui.use(['form', 'table','miniPage','element'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        miniPage = layui.miniPage;
    form.render();
    let openWH = miniPage.getOpenWidthHeight();
    table.render({
        elem: '#viewTable',
        url: serverPath + "/user/query",
        toolbar: '#toolbar',
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
            {field: 'loginName', width: 200, title: '登录名'},
            {field: 'nickName', width: 200, title: '昵称'},
            {field: 'typeDesc', width: 200, title: '用户类型'},
            {field: 'status', width: 100, title: '状态', templet:function (d){
                    let checked = d.status==0?'':'checked'
                    let disabled = d.id == superAdminId?'disabled':''
                    let append = checked + ' ' + disabled;
                    return '<input type="checkbox" value="' + d.status + '" rowId="' + d.id + '" lay-filter="status" lay-skin="switch" lay-text="启用|禁用" ' + append + '>';
                }, sort: true},
            {field: 'createTime', width: 200, title: '创建时间', sort: true},
            {title: '操作', minWidth: 200, toolbar: '#viewTableBar', align: "center", fixed:"right"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });

    // 监听搜索操作
    form.on('submit(dataSearchBtn)', function (data) {
        //执行搜索重载
        table.reload('viewTable', {
            page: {
                curr: 1
            }
            , where: {
                "loginName": data.field.loginName,
                "nickName": data.field.nickName,
                "status": data.field.status
            }
        }, 'data');

        return false;
    });

    /**
     * toolbar事件监听
     */
    table.on('toolbar(viewTable)', function (obj) {
        if (obj.event === 'add') {   // 监听添加操作
            let content = miniPage.getHrefContent(pagePath + '/user/add.html');

            let index = layer.open({
                title: '添加',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['800px', '700px'],
                content: content
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'delete') {  // 监听删除操作
            let checkStatus = table.checkStatus('viewTable')
                , data = checkStatus.data;
            layer.alert(JSON.stringify(data));
        }
    });

    //监听表格复选框选择
    table.on('checkbox(viewTable)', function (obj) {
        console.log(obj)
    });

    table.on('tool(viewTable)', function (obj) {
        let rowData = obj.data;
        if (obj.event === 'edit') {

            let content = miniPage.getHrefContent(pagePath + '/user/edit.html?id=' + rowData.id);

            let index = layer.open({
                title: rowData.nickName + '-编辑',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['800px', '700px'],
                content: content
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
            return false;
        } else if (obj.event === 'delete') {
            layer.confirm('确定删除？',
                {
                    title: "操作提示",
                    icon: 0,
                },
                function (index) {
                    var reqData = {
                        id : rowData.id
                    };
                    //请求接口
                    let url = serverPath + "/user/remove";
                    ajax(url,"DELETE",reqData,
                        {
                            successFn:function(res) {
                                layer.msg('删除成功', {
                                    icon: 6,
                                    time: 1000,
                                    shade: 0.3
                                }, function () {
                                    parent.layui.table.reload('viewTable');
                                });
                            },
                            completeFn:function(){
                                layer.close(index);
                            }
                        })
                    return false;
            });
        } else if (obj.event === 'resetPwd') {
            let content = miniPage.getHrefContent(pagePath + '/user/resetPwd.html?userId=' + rowData.id);
            let index = layer.open({
                title: rowData.nickName + '-密码重置',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['800px', '700px'],
                content: content
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
            return false;
        }
    });

    form.on('switch(status)', function(data) {
        $(this).val($(this).val() == 1?0:1)
        let reqData = {
            id:$(this).attr("rowId"),
            status:$(this).val()
        }
        let url = serverPath + "/user/changeStatus";
        ajax(url,"PUT",reqData,
            {
                successFn:function(res) {
                    layer.msg('操作成功', {
                        icon: 6,
                        time: 1000,
                        shade: 0.3
                    }, function () {
                        parent.layui.table.reload('viewTable');
                    });
                }
            })

    });



});