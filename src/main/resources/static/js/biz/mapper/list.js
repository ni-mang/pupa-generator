layui.use(['form', 'table','miniPage','element'], function () {
    let $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        miniPage = layui.miniPage;

    form.render();
    let isOwner = $("#isOwner").val();
    let configId = $("#configId").val();
    let param = "?configId=" + configId;

    // 数据库品牌
    renderEnums(form, 'brandSearch', 'datasource_brand', '');

    table.render({
        elem: '#mapperTable',
        url: serverPath + "/cfg/mapper/query" + param,
        toolbar: '#mapperToolbar',
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
            {field: 'brandDesc', width: 200, title: '数据库品牌', sort: true},
            {field: 'langDesc', width: 200, title: '程序语言', sort: true},
            {field: 'comments', title: '说明'},
            {title: '操作', minWidth: 150, toolbar: '#mapperTableBar', align: "center", fixed:"right"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });

    // 监听搜索操作
    form.on('submit(mapperSearchBtn)', function (data) {
        //执行搜索重载
        table.reload('mapperTable', {
            page: {
                curr: 1
            }
            , where: {
                "name": data.field.name,
                "brand": data.field.brandSearch
            }
        }, 'data');

        return false;
    });

    /**
     * toolbar事件监听
     */
    table.on('toolbar(mapperTable)', function (obj) {
        if (obj.event === 'add') {   // 监听添加操作
            let content = miniPage.getHrefContent('mapper/add.html' + param);
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
            let checkStatus = table.checkStatus('mapperTable')
                , data = checkStatus.data;
            layer.alert(JSON.stringify(data));
        }
    });

    //监听表格复选框选择
    table.on('checkbox(mapperTable)', function (obj) {
        console.log(obj)
    });

    table.on('tool(mapperTable)', function (obj) {
        let rowData = obj.data;
        if (obj.event === 'edit') {

            let content = miniPage.getHrefContent('mapper/edit.html' + param + "&id=" + rowData.id);

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
                    let url = serverPath + "/cfg/mapper/remove";
                    ajax(url,"DELETE",reqData,
                        {
                            successFn:function(res) {
                                layer.msg('删除成功', {
                                    icon: 6,
                                    time: 1000,
                                    shade: 0.3
                                }, function () {
                                    parent.layui.table.reload('mapperTable');
                                });
                            },
                            completeFn:function(){
                                layer.close(index);
                            }
                        })
                    return false;
            });
        } else if (obj.event === 'view') {
            let content = miniPage.getHrefContent('mapper/view.html?id=' + rowData.id);
            let index = layer.open({
                title: rowData.name + '-查看',
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
});