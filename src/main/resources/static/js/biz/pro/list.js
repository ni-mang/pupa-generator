layui.use(['form', 'table','miniPage','element','upload'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        upload = layui.upload,
        miniPage = layui.miniPage;
    let openWH = miniPage.getOpenWidthHeight();

    table.render({
        elem: '#projectTable',
        url: serverPath + "/pro/project/query",
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
            {field: 'ownerId', hide: true},
            {field: 'configId', hide: true},
            {field: 'name', width: 200, title: '项目名称'},
            {field: 'configName', width: 200, title: '配置名称'},
            {field: 'owner', width: 200, title: '所有者'},
            {field: 'comments', width: 400, title: '项目说明'},
            {field: 'createTime', width: 200, title: '创建时间', sort: true},
            {title: '操作', minWidth: 350, toolbar: '#projectTableBar', align: "center", fixed:"right"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line',
        done: function(res, curr, count){
            renderUpload();
        }
    });

    function renderUpload(){
        upload.render({
            elem: '#import'
            ,url: serverPath + "/pro/project/importAll"
            ,accept: 'file'
            ,exts:'pupa'
            ,before: function (){
                // 添加遮罩层
                this.layerIndex = layer.load(0, { shade: [0.5, '#393D49'] });
            }
            ,done: function(res, index, upload){
                //上传完毕回调
                // 关闭遮罩层
                layer.close(this.layerIndex);
                // 刷新列表
                table.reload('projectTable');

                layer.msg("演示环境禁用此操作", {
                    icon: 5
                    , time: 3000
                });
            }
        });
    }

    // 监听搜索操作
    form.on('submit(dataSearchBtn)', function (data) {
        //执行搜索重载
        table.reload('projectTable', {
            page: {
                curr: 1
            }
            , where: {
                "name": data.field.name
            }
        }, 'data');

        return false;
    });

    /**
     * toolbar事件监听
     */
    table.on('toolbar(projectTable)', function (obj) {
        let ids = checkId();
        if (obj.event === 'add') {   // 监听添加操作
            var content = miniPage.getHrefContent(pagePath + '/pro/add.html');
            var index = layer.open({
                title: '添加',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['1000px', '700px'],
                content: content,
                success: function(layero, index){
                }
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'delete') {  // 监听删除操作
            var checkStatus = table.checkStatus('projectTable')
                , data = checkStatus.data;
            layer.alert(JSON.stringify(data));
        } else if (obj.event === 'exportAll') { // 导出全部
            exportMsg();
            location.href = obj2Param(serverPath + "/pro/project/exportAll", form.val("dataSearchBar"));
        } else if (obj.event === 'exportSelect') { // 导出选中
            if(ids.length<1){
                layer.msg("请选择要导出的数据", {
                    icon: 5,
                    time: 1000
                });
            }else {
                exportMsg();
                location.href = obj2Param(serverPath + "/pro/project/exportSelect", {ids: ids});
            }
        }
    });

    function exportMsg(){
        layer.msg('正在导出，请稍后，请勿重复操作', {
            icon: 6,
            time: 2000,
            shade: 0.3
        });
    }

    function checkId(){
        let checkStatus = table.checkStatus('projectTable')
            , data = checkStatus.data, ids = [];
        for(let i=0;i<data.length;i++){
            ids.push(data[i].id)
        }
        return ids;
    }

    //监听表格复选框选择
    table.on('checkbox(projectTable)', function (obj) {
        console.log(obj)
    });

    table.on('tool(projectTable)', function (obj) {
        let rowData = obj.data;
        let isOwner = rowData.ownerId === loginUser.id || isAdmin
        if (obj.event === 'edit') {
            let content = miniPage.getHrefContent(pagePath + '/pro/edit.html?id=' + rowData.id);
            let index = layer.open({
                title: rowData.name + '-编辑',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['1000px', '700px'],
                content: content,
                success: function(layero, index){
                }
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
            return false;
        } else if (obj.event === 'delete') {
            layer.confirm('删除项目将同时删除关联的所有表数据，是否继续？',
                {
                    title: "操作提示",
                    icon: 0,
                },
                function (index) {
                    let reqData = {
                        id : rowData.id
                    };
                    //请求接口
                    let url = serverPath + "/pro/project/remove";
                    ajax(url,"DELETE",reqData,
                        {
                            successFn:function(res) {
                                layer.msg('删除成功', {
                                    icon: 6,
                                    time: 1000,
                                    shade: 0.3
                                }, function () {
                                    parent.layui.table.reload('projectTable');
                                });
                            },
                            completeFn:function(){
                                layer.close(index);
                            }
                        })
                    return false;
            });
        } else if (obj.event === 'view') {
            let content = miniPage.getHrefContent(pagePath + '/pro/view.html?id=' + rowData.id);
            let index = layer.open({
                title: rowData.name + '-查看',
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
        } else if (obj.event === 'member') {
            let content = miniPage.getHrefContent(pagePath + '/member/list.html?projectId=' + rowData.id + "&configId=" + rowData.configId + "&isOwner=" + isOwner);
            let index = layer.open({
                title: rowData.name + '-成员',
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
        } else if (obj.event === 'datasource') {
            let content = miniPage.getHrefContent(pagePath + '/datasource/list.html?projectId=' + rowData.id + "&configId=" + rowData.configId + "&isOwner=" + isOwner);
            let index = layer.open({
                title: rowData.name + '-数据源',
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
        } else if (obj.event === 'copy') {  // 监听拷贝操作
            //请求接口
            let url = serverPath + "/pro/project/clone";
            ajax(url,"POST",{id: rowData.id},
                {
                    beforeSendFn:function () {
                        // 添加遮罩层
                        this.layerIndex = layer.load(0, { shade: [0.5, '#393D49'] });
                    },
                    successFn:function(res) {
                        layer.msg('操作完成', {
                            icon: 6,
                            time: 1000,
                            shade: 0.3
                        }, function () {
                            parent.layui.table.reload('projectTable');
                        });
                    },
                    completeFn:function(){
                        // 关闭遮罩层
                        layer.close(this.layerIndex);
                    }
                })
        }
    });

});