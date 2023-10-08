layui.use(['form', 'table','miniPage','element','upload'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        upload = layui.upload,
        miniPage = layui.miniPage;
    form.render();
    let openWH = miniPage.getOpenWidthHeight();

    table.render({
        elem: '#viewTable',
        url: serverPath + "/cfg/config/query",
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
            {field: 'name', width: 200, title: '配置名称'},
            {field: 'comments', width: 400, title: '说明'},
            {field: 'type', width: 100, title: '类型', templet:function (d){
                    return d.type==0?'公共':'私有';
                }},
            {field: 'useNum', width: 150, title: '引用数'},
            {field: 'genTimes', width: 150, title: '生成次数'},
            {field: 'status', width: 100, title: '状态', templet:function (d){
                    let checked = d.status==0?'':'checked'
                    let disabled = isAdmin?"":d.readOnly?'disabled':''
                    let append = checked + ' ' + disabled;
                    return '<input type="checkbox" value="' + d.status + '" rowId="' + d.id + '" lay-filter="status" lay-skin="switch" lay-text="启用|禁用" ' + append + '>';
                }, sort: true},
            {field: 'userName', width: 200, title: '所有者'},
            {field: 'createTime', width: 200, title: '创建时间', sort: true},
            {title: '操作', minWidth: 350, toolbar: '#viewTableBar', align: "center", fixed:"right"}
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
            ,url: serverPath + "/cfg/config/importAll"
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
                table.reload('viewTable');
            }
        });
    }

    // 监听搜索操作
    form.on('submit(dataSearchBtn)', function (data) {
        //执行搜索重载
        table.reload('viewTable', {
            page: {
                curr: 1
            }
            , where: {
                "name": data.field.name,
                "status": data.field.status
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
            let content = miniPage.getHrefContent('config/add.html');

            let index = layer.open({
                title: '添加',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['800px', '700px'],
                content: content,
                success: function(layero, index){
                    pageRender(form);
                }
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'delete') {  // 监听删除操作
        } else if (obj.event === 'exportAll') { // 导出全部
            exportMsg();
            location.href = obj2Param(serverPath + "/cfg/config/exportAll", form.val("dataSearchBar"));
        } else if (obj.event === 'exportSelect') { // 导出选中
            if(ids.length<1){
                layer.msg("请选择要导出的数据", {
                    icon: 5,
                    time: 1000
                });
            }else {
                exportMsg();
                location.href = obj2Param(serverPath + "/cfg/config/exportSelect", {ids: ids});
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
        let isOwner = rowData.userId === loginUser.id || isAdmin
        if (obj.event === 'edit') {

            let content = miniPage.getHrefContent('config/edit.html');

            let index = layer.open({
                title: rowData.name + '-编辑',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['800px', '700px'],
                content: content,
                success: function(layero, index){
                    pageRender(form,rowData);
                }
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
            return false;
        } else if (obj.event === 'delete') {
            layer.confirm('删除配置后对关联项目的维护可能产生影响，是否继续？',
                {
                    title: "操作提示",
                    icon: 0,
                },
                function (index) {
                    var reqData = {
                        id : rowData.id
                    };
                    //请求接口
                    let url = serverPath + "/cfg/config/remove";
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
        } else if (obj.event === 'mapper') {
            let content = miniPage.getHrefContent('mapper/list.html?configId=' + rowData.id + "&isOwner=" + isOwner);
            let index = layer.open({
                title: rowData.name + '-映射',
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
        } else if (obj.event === 'extend') {
            let content = miniPage.getHrefContent('extend/list.html?configId=' + rowData.id + "&isOwner=" + isOwner);
            let index = layer.open({
                title: rowData.name + '-扩展',
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
        } else if (obj.event === 'template') {
            let content = miniPage.getHrefContent('template/list.html?configId=' + rowData.id + "&isOwner=" + isOwner);
            let index = layer.open({
                title: rowData.name + '-模板',
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
            let reqData = {
                id: rowData.id,
            }
            //请求接口
            let url = serverPath + "/cfg/config/clone";
            ajax(url,"POST",reqData,
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
                            parent.layui.table.reload('viewTable');
                        });
                    },
                    completeFn:function(){
                        // 关闭遮罩层
                        layer.close(this.layerIndex);
                    }
                })
        }
    });

    form.on('switch(status)', function(data) {
        $(this).val($(this).val() == 1?0:1)
        let reqData = {
            id:$(this).attr("rowId"),
            status:$(this).val()
        }
        let url = serverPath + "/cfg/config/change";
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