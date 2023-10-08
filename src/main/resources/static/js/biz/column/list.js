layui.use(['form', 'table','miniPage','element'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        miniPage = layui.miniPage;
    form.render();

    // 加载配置数据下拉选项
    renderSelect(
        $("select[name='brandSearch']"),
        "/enum/getEnums",
        {label:'datasource_brand'},
        "desc", "value",
        '',
        function (){
            form.render("select")
        }
    );

    table.render({
        elem: '#viewTable',
        url: serverPath + "/column-type/query",
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
            {type: "checkbox", width: 50},
            {field: 'id', hide: true},
            {field: 'brandDesc', width: 200, title: '数据库品牌'},
            {field: 'columnType', width: 200, title: '列类型', sort: true},
            {title: '操作', minWidth: 150, toolbar: '#viewTableBar', align: "center", fixed:"right"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 50,
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
                "brand": data.field.brandSearch,
                "status": data.field.status,
            }
        }, 'data');

        return false;
    });

    /**
     * toolbar事件监听
     */
    table.on('toolbar(viewTable)', function (obj) {
        let ids = checkId();
        if (obj.event === 'add') {   // 监听添加操作
            let content = miniPage.getHrefContent('column/add.html');

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
            if(ids.length<1){
                layer.msg("请选中操作数据", {
                    icon: 5,
                    time: 1000
                });
            }else {
                layer.confirm('确定删除？',
                    {
                        title: "操作提示",
                        icon: 0,
                    },
                    function (index) {
                        let reqData = {
                            ids: ids
                        }
                        //请求接口
                        let url = serverPath + "/column-type/removeBatch";
                        ajax(url, "DELETE", reqData,
                            {
                                successFn: function (res) {
                                    layer.msg('删除成功', {
                                        icon: 6,
                                        time: 1000,
                                        shade: 0.3
                                    }, function () {
                                        parent.layui.table.reload('viewTable');
                                    });
                                },
                                completeFn: function () {
                                    layer.close(index);
                                }
                            })
                        return false;
                    });
            }
        }
    });

    function checkId(){
        let checkStatus = table.checkStatus('viewTable')
            , data = checkStatus.data, ids = [];
        for(let i=0;i<data.length;i++){
            ids.push(data[i].id)
        }
        return ids;
    }

    //监听表格复选框选择
    table.on('checkbox(viewTable)', function (obj) {
        console.log(obj)
    });

    table.on('tool(viewTable)', function (obj) {
        let rowData = obj.data;
        if (obj.event === 'edit') {

            let content = miniPage.getHrefContent('column/edit.html?id=' + rowData.id);

            let index = layer.open({
                title: '编辑',
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
                    let url = serverPath + "/column-type/remove";
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
        }
    });
});