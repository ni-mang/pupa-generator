layui.use(['form', 'table','miniPage','element'], function () {
    let $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        miniPage = layui.miniPage;

    form.render();
    let isOwner = $("#isOwner").val();
    let configId = $("#configId").val();
    let param = "?configId=" + configId;

    table.render({
        elem: '#extendTable',
        url: serverPath + "/cfg/extend/query" + param,
        toolbar: '#extendToolbar',
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
            {field: 'name', width: 200, title: '名称'},
            {field: 'comments', width: 400, title: '说明'},
            {field: 'key', width: 200, title: '<span style="color: red">键</span>', templet:function (d){
                    return '<span style="color: red">' + d.key + '</span>'
                }},
            {field: 'value', width: 200, title: '<span style="color: green">值</span>', templet:function (d){
                    return '<span style="color: green">' + d.value + '</span>'
                }},
            {field: 'scopeDesc', width: 200, title: '作用域', sort: true},
            {field: 'status', width: 100, title: '状态', templet:function (d){
                    //return d.status == 0 ? '<p style="color: red">禁用</p>' : '<p style="color: green">启用</p>'
                    let checked = d.status==0?'':'checked'
                    let disabled = isOwner=='true'?'':'disabled'
                    let append = checked + ' ' + disabled;
                    return '<input type="checkbox" value="' + d.status + '" rowId="' + d.id + '" lay-filter="status" lay-skin="switch" lay-text="启用|禁用" ' + append + '>';
                }, sort: true},
            {field: 'createTime', width: 200, title: '创建时间', sort: true},
            {title: '操作', minWidth: 150, toolbar: '#extendTableBar', align: "center", fixed:"right"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });

    // 监听搜索操作
    form.on('submit(extendSearchBtn)', function (data) {
        //执行搜索重载
        table.reload('extendTable', {
            page: {
                curr: 1
            }
            , where: {
                "key": data.field.key,
                "name": data.field.name,
                "scope": data.field.scope,
                "status": data.field.status
            }
        }, 'data');

        return false;
    });

    /**
     * toolbar事件监听
     */
    table.on('toolbar(extendTable)', function (obj) {
        if (obj.event === 'add') {   // 监听添加操作
            let content = miniPage.getHrefContent('extend/add.html' + param);
            let index = layer.open({
                title: '添加',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['800px', '700px'],
                content: content,
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'delete') {  // 监听删除操作
            let checkStatus = table.checkStatus('extendTable')
                , data = checkStatus.data;
            layer.alert(JSON.stringify(data));
        }
    });

    //监听表格复选框选择
    table.on('checkbox(extendTable)', function (obj) {
        console.log(obj)
    });

    table.on('tool(extendTable)', function (obj) {
        let rowData = obj.data;
        if (obj.event === 'edit') {

            let content = miniPage.getHrefContent('extend/edit.html' + param + "&id=" + rowData.id);

            let index = layer.open({
                title: rowData.name + '-编辑',
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
                    let url = serverPath + "/cfg/extend/remove";
                    ajax(url,"DELETE",reqData,
                        {
                            successFn:function(res) {
                                layer.msg('删除成功', {
                                    icon: 6,
                                    time: 1000,
                                    shade: 0.3
                                }, function () {
                                    parent.layui.table.reload('extendTable');
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

    form.on('switch(status)', function(data) {
        $(this).val($(this).val() == 1?0:1)
        let reqData = {
            id:$(this).attr("rowId"),
            status:$(this).val()
        }
        let url = serverPath + "/cfg/extend/change";
        ajax(url,"PUT",reqData,
            {
                successFn:function(res) {
                    layer.msg('操作成功', {
                        icon: 6,
                        time: 1000,
                        shade: 0.3
                    }, function () {
                        parent.layui.table.reload('extendTable');
                    });
                }
            })

    });

});