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
        elem: '#datasourceTable',
        url: serverPath + "/pro/datasource/query" + param,
        toolbar: '#datasourceToolbar',
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
            {field: 'name', width: 300, title: '连接名'},
            {field: 'brandDesc', width: 200, title: '数据库品牌', sort: true},
            {field: 'mainAddr', width: 300, title: '主机'},
            {field: 'schema', width: 200, title: '库、目录'},
            {field: 'urlSuffix', width: 400, title: '链接后缀'},
            {field: 'prefix', width: 200, title: '忽略前缀'},
            {field: 'createTime', width: 200, title: '创建时间', sort: true},
            {title: '操作', minWidth: 250, toolbar: '#datasourceTableBar', align: "center", fixed:"right"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });

    // 监听搜索操作
    form.on('submit(datasourceSearchBtn)', function (data) {
        //执行搜索重载
        table.reload('datasourceTable', {
            page: {
                curr: 1
            }
            , where: {
                "brand": data.field.brandSearch,
                "name": data.field.name,
                "schema": data.field.schema,
            }
        }, 'data');

        return false;
    });

    /**
     * toolbar事件监听
     */
    table.on('toolbar(datasourceTable)', function (obj) {
        if (obj.event === 'add') {   // 监听添加操作
            let content = miniPage.getHrefContent(pagePath + '/datasource/add.html' + param);
            let index = layer.open({
                title: '添加',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['1000px', '900px'],
                content: content,
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'delete') {  // 监听删除操作
            let checkStatus = table.checkStatus('datasourceTable')
                , data = checkStatus.data;
            layer.alert(JSON.stringify(data));
        }
    });

    //监听表格复选框选择
    table.on('checkbox(datasourceTable)', function (obj) {
        console.log(obj)
    });

    table.on('tool(datasourceTable)', function (obj) {
        let rowData = obj.data;
        if (obj.event === 'edit') {

            let content = miniPage.getHrefContent(pagePath + '/datasource/edit.html' + param + '&configId=' + configId + '&id=' + rowData.id);

            let index = layer.open({
                title: rowData.name + '-编辑',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['1000px', '900px'],
                content: content
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
            return false;
        } else if (obj.event === 'delete') {
            layer.confirm('删除数据源将同时删除相关的表、字段信息，确定删除？',
                {
                    title: "操作提示",
                    icon: 0,
                },
                function (index) {
                    var reqData = {
                        id : rowData.id
                    };
                    //请求接口
                    let url = serverPath + "/pro/datasource/remove";
                    ajax(url,"DELETE",reqData,
                        {
                            successFn:function(res) {
                                layer.msg('删除成功', {
                                    icon: 6,
                                    time: 1000,
                                    shade: 0.3
                                }, function () {
                                    parent.layui.table.reload('datasourceTable');
                                });
                            },
                            completeFn:function(){
                                layer.close(index);
                            }
                        })
                    return false;
            });
        } else if (obj.event === 'view') {
            let content = miniPage.getHrefContent(pagePath + '/datasource/view.html' + param + '&configId=' + configId + '&id=' + rowData.id);
            let index = layer.open({
                title: rowData.name + '-查看',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['1000px', '900px'],
                content: content,
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
            return false;
        } else if (obj.event === 'table') {
            let openWH = miniPage.getOpenWidthHeight();
            let content = miniPage.getHrefContent(pagePath + '/table/list.html' + param + '&configId=' + configId + '&sourceId=' + rowData.id + "&isOwner=true");
            let index = layer.open({
                title: rowData.name + '-' + rowData.schema + '-库表',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: [openWH[0] + 'px', openWH[1] + 'px'],
                offset: [openWH[2] + 'px', openWH[3] + 'px'],
                content: content,
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
            return false;
        }
    });

});