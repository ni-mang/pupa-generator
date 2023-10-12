layui.use(['form', 'table','miniPage','element'], function () {
    let $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        miniPage = layui.miniPage;

    form.render();
    let projectId = $("#projectId").val();
    let configId = $("#configId").val();
    let sourceId = $("#sourceId").val();
    let tableId = $("#tableId").val();
    let isOwner = $("#isOwner").val();
    let param = "?projectId=" + projectId + "&configId=" + configId + "&sourceId=" + sourceId + "&tableId=" + tableId;

    let disabled = isOwner?'':'disabled';
    table.render({
        elem: '#fieldTable',
        url: serverPath + "/pro/field/query?sourceId=" + sourceId + "&tableId=" + tableId,
        toolbar: '#fieldToolbar',
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
            {field: 'ordinalPosition', width: 100, title: '顺位', sort: true},
            {field: 'columnCn', width: 150, title: '中文名'},
            {field: 'columnNotes', width: 150, title: '注释'},
            {field: 'columnName', width: 150, title: '列名', sort: true},
            {field: 'attrName', width: 150, title: '属性名'},
            {field: 'dataType', width: 150, title: '物理类型'},

            {field: 'primary', width: 100, title: '是否主键', templet:function (d){
                    return d.primary==true?'<span style="color: green">是</span>':'<span style="color: orangered">否</span>';
                }},
            {field: 'requiredFlag', width: 100, title: '是否必须', templet:function (d){
                    return d.requiredFlag==true?'<span style="color: green">是</span>':'<span style="color: orangered">否</span>';
                }},

            {field: 'boundMin', width: 150, title: '范围下限'},
            {field: 'boundMax', width: 150, title: '范围上限'},
            {field: 'columnDefault', width: 150, title: '默认值'},
            {field: 'enumName', width: 150, title: '枚举名'},

            {field: 'insertFlag', width: 100, title: '插入标识', templet:function (d){
                    let checked = d.insertFlag==true?'checked':'';
                    let append = checked + ' ' + disabled;
                    return '<input type="checkbox" lay-skin="primary" rowId="' + d.id + '" name="insertFlag" lay-filter="flagChange" ' + append + '>';
                    // return d.insertFlag==true?'<span style="color: green">是</span>':'<span style="color: orangered">否</span>';
                }},
            {field: 'viewFlag', width: 100, title: '展示标识', templet:function (d){
                    let checked = d.viewFlag==true?'checked':'';
                    let append = checked + ' ' + disabled;
                    return '<input type="checkbox" lay-skin="primary" rowId="' + d.id + '" name="viewFlag" lay-filter="flagChange" ' + append + '>';
                    // return d.viewFlag==true?'<span style="color: green">是</span>':'<span style="color: orangered">否</span>';
                }},
            {field: 'queryFlag', width: 100, title: '查询标识', templet:function (d){
                    let checked = d.queryFlag==true?'checked':'';
                    let append = checked + ' ' + disabled;
                    return '<input type="checkbox" lay-skin="primary" rowId="' + d.id + '" name="queryFlag" lay-filter="flagChange" ' + append + '>';
                    // return d.queryFlag==true?'<span style="color: green">是</span>':'<span style="color: orangered">否</span>';
                }},
            {field: 'existFlag', width: 200, title: '是否存在于数据库', sort: true, templet:function (d){
                    return d.existFlag==true?'<span style="color: green">存在</span>':'<span style="color: orangered">不存在</span>';
                }},
            {title: '操作', minWidth: 150, toolbar: '#fieldTableBar', align: "center", fixed:"right"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 50,
        page: true,
        skin: 'line'
    });

    // 监听搜索操作
    form.on('submit(fieldSearchBtn)', function (data) {
        //执行搜索重载
        table.reload('fieldTable', {
            page: {
                curr: 1
            }
            , where: {
                "columnName": data.field.columnName,
                "columnCn": data.field.columnCn,
            }
        }, 'data');

        return false;
    });

    /**
     * toolbar事件监听
     */
    table.on('toolbar(fieldTable)', function (obj) {
        let ids = checkId();
        if (obj.event === 'add') {   // 监听添加操作
            let content = miniPage.getHrefContent(pagePath + '/field/add.html' + param);
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
                        let url = serverPath + "/pro/field/removeBatch";
                        ajax(url, "DELETE", reqData,
                            {
                                successFn: function (res) {
                                    layer.msg('删除成功', {
                                        icon: 6,
                                        time: 1000,
                                        shade: 0.3
                                    }, function () {
                                        parent.layui.table.reload('fieldTable');
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
        let checkStatus = table.checkStatus('fieldTable')
            , data = checkStatus.data, ids = [];
        for(let i=0;i<data.length;i++){
            ids.push(data[i].id)
        }
        return ids;
    }

    //监听表格复选框选择
    // table.on('checkbox(fieldTable)', function (obj) {
    //     console.log(obj)
    // });

    form.on('checkbox(flagChange)', function(data) {
        let reqData = {
            id:$(this).attr("rowId"),
            filed:$(this).attr("name"),
            value:$(this).prop('checked')
        }
        let url = serverPath + "/pro/field/change";
        ajax(url,"PUT",reqData,
            {
                successFn:function(res) {
                    layer.msg('操作成功', {
                        icon: 6,
                        time: 1000,
                    }, function () {
                        // parent.layui.table.reload('fieldTable');
                    });
                }
            })

    });

    table.on('tool(fieldTable)', function (obj) {
        let rowData = obj.data;
        if (obj.event === 'edit') {
            let content = miniPage.getHrefContent(pagePath + '/field/edit.html' + param + '&id=' + rowData.id);

            let index = layer.open({
                title: rowData.columnName + '-编辑',
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
                    let url = serverPath + "/pro/field/remove";
                    ajax(url,"DELETE",reqData,
                        {
                            successFn:function(res) {
                                layer.msg('删除成功', {
                                    icon: 6,
                                    time: 1000,
                                    shade: 0.3
                                }, function () {
                                    parent.layui.table.reload('fieldTable');
                                });
                            },
                            completeFn:function(){
                                layer.close(index);
                            }
                        })
                    return false;
            });
        } else if (obj.event === 'view') {
            let content = miniPage.getHrefContent(pagePath + '/field/view.html' + param + '&id=' + rowData.id);
            let index = layer.open({
                title: rowData.columnName + '-查看',
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
        }
    });

});

function process(data){
    console.log(data)
    data.primary = data.primary + "";
    data.requiredFlag = data.requiredFlag + "";
    data.insertFlag = data.insertFlag + "";
    data.viewFlag = data.viewFlag + "";
    data.queryFlag = data.queryFlag + "";

    idTypeHide(data.primary);
}

function idTypeHide(val){
    if(val === "true"){
        $("#idTypeDiv").removeClass("layui-hide")
    }else {
        $("#idTypeDiv").addClass("layui-hide")
    }
}