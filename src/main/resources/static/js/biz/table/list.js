layui.use(['form', 'table','miniPage','element'], function () {
    let $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        miniPage = layui.miniPage;

    form.render();
    let projectId = $("#projectId").val();
    let configId = $("#configId").val();
    let sourceId = $("#sourceId").val();
    let isOwner = $("#isOwner").val();
    let param = "?projectId=" + projectId + "&configId=" + configId + "&sourceId=" + sourceId;

    table.render({
        elem: '#dbTableTable',
        url: serverPath + "/pro/table/query?sourceId=" + sourceId,
        toolbar: '#dbTableToolbar',
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
            {field: 'cnName', width: 200, title: '中文名'},
            {field: 'tableName', width: 250, title: '表名', sort: true},
            {field: 'infixName', width: 250, title: '无前缀名'},
            {field: 'module', width: 300, title: '模块', sort: true},
            {field: 'existFlag', width: 100, title: '是否存在于数据库', sort: true, templet:function (d){
                    return d.existFlag==true?'<span style="color: green">存在</span>':'<span style="color: orangered">不存在</span>';
                }},
            {field: 'createTime', width: 200, title: '创建时间', sort: true},
            {field: 'updateTime', width: 200, title: '变更时间', sort: true},
            {title: '操作', minWidth: 250, toolbar: '#dbTableTableBar', align: "center", fixed:"right"}
        ]],
        limits: [10, 15, 20, 25, 50],
        limit: 50,
        page: true,
        skin: 'line'
    });

    // 监听搜索操作
    form.on('submit(dbTableSearchBtn)', function (data) {
        //执行搜索重载
        table.reload('dbTableTable', {
            page: {
                curr: 1
            }
            , where: {
                "module": data.field.module,
                "tableName": data.field.tableName,
            }
        }, 'data');

        return false;
    });

    /**
     * toolbar事件监听
     */
    table.on('toolbar(dbTableTable)', function (obj) {
        let ids = checkId();
        if (obj.event === 'add') {   // 监听添加操作
            let content = miniPage.getHrefContent('table/add.html' + param);
            let index = layer.open({
                title: '添加',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['1000px', '700px'],
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
                layer.confirm('删除库表将同时删除相关的字段信息，确定删除？',
                    {
                        title: "操作提示",
                        icon: 0,
                    },
                    function (index) {
                        let reqData = {
                            ids: ids
                        }
                        //请求接口
                        let url = serverPath + "/pro/table/removeBatch";
                        ajax(url, "DELETE", reqData,
                            {
                                successFn: function (res) {
                                    layer.msg('删除成功', {
                                        icon: 6,
                                        time: 1000,
                                        shade: 0.3
                                    }, function () {
                                        parent.layui.table.reload('dbTableTable');
                                    });
                                },
                                completeFn: function () {
                                    layer.close(index);
                                }
                            })
                        return false;
                    });
            }
        } else if (obj.event === 'pull') {  // 监听同步操作
            let reqData = {
                sourceId: sourceId,
                tableIds: ids
            }
            //请求接口
            let url = serverPath + "/pro/datasource/pull";
            ajax(url,"POST",reqData,
                {
                    beforeSendFn:function(){
                        // 添加遮罩层
                        this.layerIndex = layer.load(0, { shade: [0.5, '#393D49'] });
                    },
                    successFn:function(res) {
                        layer.msg('同步完成', {
                            icon: 6,
                            time: 1000,
                            shade: 0.3
                        }, function () {
                            parent.layui.table.reload('dbTableTable');
                        });
                    },
                    completeFn:function (){
                        // 关闭遮罩层
                        layer.close(this.layerIndex);
                    }
                })
        } else if (obj.event === 'genAll') { // 生成全部
            genMsg();
            location.href = serverPath + "/gen/genAll?sourceId=" + sourceId;
        } else if (obj.event === 'genTables') { // 生成选中
            if(ids.length<1){
                layer.msg("请选择要生成代码的表", {
                    icon: 5,
                    time: 1000
                });
            }else {
                genMsg();
                location.href = obj2Param(serverPath + "/gen/genTables", {tableIds: ids});
            }
        }
    });

    function genMsg(){
        layer.msg('正在生成，请稍后，请勿重复操作', {
            icon: 6,
            time: 2000,
            shade: 0.3
        });
    }

    function checkId(){
        let checkStatus = table.checkStatus('dbTableTable')
            , data = checkStatus.data, ids = [];
        for(let i=0;i<data.length;i++){
            ids.push(data[i].id)
        }
        return ids;
    }

    //监听表格复选框选择
    table.on('checkbox(dbTableTable)', function (obj) {
        console.log(obj)
    });

    table.on('tool(dbTableTable)', function (obj) {
        let rowData = obj.data;
        if (obj.event === 'edit') {

            let content = miniPage.getHrefContent('table/edit.html' + param + '&id=' + rowData.id);

            let index = layer.open({
                title: rowData.tableName + '-编辑',
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
            layer.confirm('删除库表将同时删除相关的字段信息，确定删除？',
                {
                    title: "操作提示",
                    icon: 0,
                },
                function (index) {
                    var reqData = {
                        id : rowData.id
                    };
                    //请求接口
                    let url = serverPath + "/pro/table/remove";
                    ajax(url,"DELETE",reqData,
                        {
                            successFn:function(res) {
                                layer.msg('删除成功', {
                                    icon: 6,
                                    time: 1000,
                                    shade: 0.3
                                }, function () {
                                    parent.layui.table.reload('dbTableTable');
                                });
                            },
                            completeFn:function(){
                                layer.close(index);
                            }
                        })
                    return false;
            });
        } else if (obj.event === 'view') {
            let content = miniPage.getHrefContent('table/view.html' + param + '&id=' + rowData.id);
            let index = layer.open({
                title: rowData.tableName + '-查看',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['1000px', '700px'],
                content: content,
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
            return false;
        } else if (obj.event === 'field') {
            let openWH = miniPage.getOpenWidthHeight();
            let content = miniPage.getHrefContent('field/list.html' + param + '&tableId=' + rowData.id + "&isOwner=true");
            let index = layer.open({
                title: rowData.tableName + '-字段',
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
        } else if (obj.event === 'preview') {
            let openWH = miniPage.getOpenWidthHeight();
            let content = miniPage.getHrefContent('table/preview.html?id=' + rowData.id);
            let index = layer.open({
                title: rowData.tableName + '-生成预览',
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