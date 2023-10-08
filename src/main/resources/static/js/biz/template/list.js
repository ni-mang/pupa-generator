layui.use(['form', 'table','miniPage','element'], function () {
    let $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        miniPage = layui.miniPage;
    let openWH = miniPage.getOpenWidthHeight();
    form.render();
    let isOwner = $("#isOwner").val();
    let configId = $("#configId").val();
    let param = "?configId=" + configId;

    // 加载配置数据下拉选项
    renderSelect(
        $("select[name='tempTypeSearch']"),
        "/enum/getEnums",
        {label:'temp_type'},
        "desc", "value",
        '',
        function (){
            form.render("select")
        }
    );

    table.render({
        elem: '#templateTable',
        url: serverPath + "/cfg/template/query" + param,
        toolbar: '#templateToolbar',
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
            {field: 'name', width: 200, title: '模板名称'},
            {field: 'tempTypeDesc', width: 150, title: '模板引擎'},
            {field: 'contentLang', width: 150, title: '语言', sort: true},
            {field: 'path', width: 500, title: '文件生成路径'},
            {field: 'status', width: 100, title: '状态', templet:function (d){
                    let checked = d.status==0?'':'checked'
                    let disabled = isOwner=='true'?'':'disabled'
                    let append = checked + ' ' + disabled;
                    return '<input type="checkbox" value="' + d.status + '" rowId="' + d.id + '" lay-filter="status" lay-skin="switch" lay-text="启用|禁用" ' + append + '>';
                }, sort: true},
            {field: 'notes', width: 400, title: '备注'},
            {field: 'createTime', width: 200, title: '创建时间', sort: true},
            {title: '操作', minWidth: 150, toolbar: '#templateTableBar', align: "center", fixed:"right"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });

    // 监听搜索操作
    form.on('submit(templateSearchBtn)', function (data) {
        //执行搜索重载
        table.reload('templateTable', {
            page: {
                curr: 1
            }
            , where: {
                "name": data.field.name,
                "tempType": data.field.tempTypeSearch,
                "status": data.field.status
            }
        }, 'data');

        return false;
    });

    /**
     * toolbar事件监听
     */
    table.on('toolbar(templateTable)', function (obj) {
        if (obj.event === 'add') {   // 监听添加操作
            let content = miniPage.getHrefContent('template/add.html' + param);
            let index = layer.open({
                title: '添加',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: [openWH[0] + 'px', openWH[1] + 'px'],
                offset: [openWH[2] + 'px', openWH[3] + 'px'],
                content: content,
                end: function(){
                    layer.close($("#configInfoIndex").val())
                }
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'delete') {  // 监听删除操作
            let checkStatus = table.checkStatus('templateTable')
                , data = checkStatus.data;
            layer.alert(JSON.stringify(data));
        }
    });

    //监听表格复选框选择
    table.on('checkbox(templateTable)', function (obj) {
        console.log(obj)
    });

    table.on('tool(templateTable)', function (obj) {
        let rowData = obj.data;
        if (obj.event === 'edit') {

            let content = miniPage.getHrefContent('template/edit.html' + param + "&id=" + rowData.id);

            let index = layer.open({
                title: rowData.name + '-编辑',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: [openWH[0] + 'px', openWH[1] + 'px'],
                offset: [openWH[2] + 'px', openWH[3] + 'px'],
                content: content,
                end: function(){
                    layer.close($("#configInfoIndex").val())
                }
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
                    let url = serverPath + "/cfg/template/remove";
                    ajax(url,"DELETE",reqData,
                        {
                            successFn:function(res) {
                                layer.msg('删除成功', {
                                    icon: 6,
                                    time: 1000,
                                    shade: 0.3
                                }, function () {
                                    parent.layui.table.reload('templateTable');
                                });
                            },
                            completeFn:function(){
                                layer.close(index);
                            }
                        })
                    return false;
            });
        } else if (obj.event === 'view') {
            let content = miniPage.getHrefContent('template/view.html?id=' + rowData.id);
            let index = layer.open({
                title: rowData.name + '-查看',
                type: 1,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: [openWH[0] + 'px', openWH[1] + 'px'],
                offset: [openWH[2] + 'px', openWH[3] + 'px'],
                content: content,
                end: function(){
                    layer.close($("#configInfoIndex").val())
                }
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
        let url = serverPath + "/cfg/template/change";
        ajax(url,"PUT",reqData,
            {
                successFn:function(res) {
                    layer.msg('操作成功', {
                        icon: 6,
                        time: 1000,
                        shade: 0.3
                    }, function () {
                        parent.layui.table.reload('templateTable');
                    });
                }
            })

    });

    // 复制span的内容
    window.copyText = function(span){
        // navigator clipboard 需要https等安全上下文
        if (navigator.clipboard && window.isSecureContext) {
            // navigator clipboard 向剪贴板写文本
            navigator.clipboard.writeText($(span).text()).then(() => {
                showInfo(span);
            });
        } else {
            // 拷贝DIV内的文本
            const range = document.createRange();
            range.selectNode(span);
            const selection = window.getSelection();
            if (selection.rangeCount > 0) selection.removeAllRanges();
            selection.addRange(range);
            let res = document.execCommand('copy');
            if(res){
                showInfo(span);
            }
        }
    }

    window.showInfo = function(span){
        let offset = $(span).offset();
        layer.msg('已复制', {
            time: 1000,
            shade: 0,
            offset: [offset.top, offset.left]
        });
    }
    // 打开配置参数页
    window.showConfigInfo = function(obj){
        let width = 500;
        let parent = $(obj).parent()
        let offset = parent.offset();
        let right = offset.left+parent.width() - width;
        let down = offset.top+parent.height();
        // 获取参数说明
        let reqData = {id:$("#configId").val()};
        let url = serverPath + "/cfg/config/showConfigInfo";
        ajax(url,"GET",reqData,
            {
                successFn:function(res) {
                    let data = res["data"];
                    let temp = "<p style=' color: #fff;'><div class='config-info-name my-title' {notes}>{name}</div><div class='config-info-key' onclick='copyText(this)'>{key}</div></p>";
                    let boundLine = "<p style='height: 1px;background-color: #7e8488'></p>";
                    let underBlock = "<div style='height: 50px;'></div>";
                    let html = "";
                    let content = "";
                    let indexStart = "";
                    for(let i=0;i<data.length;i++){
                        let key = data[i].key;
                        let keyStart = key.substring(0,key.indexOf("."));
                        if(indexStart != "" && indexStart != keyStart){
                            content += boundLine;
                        }
                        indexStart = keyStart;
                        html = temp.replace("{key}", key).replace("{name}", data[i].name);
                        if(data[i].notes !== ""){
                            html = html.replace("{notes}", "titVal='" +  data[i].notes + "'")
                        }
                        if(key.indexOf(".ext.") > 0){
                            html = html.replace("config-info-key", "config-info-key config-info-ext")
                        }
                        content += html;
                    }
                    content += underBlock;
                    let configInfoIndex = layer.open({
                        type: 1
                        ,title: '参数说明'
                        ,skin: 'config-info-div'
                        ,area: [width + 'px', '600px']
                        ,shade: 0
                        ,id: 'showConfigInfo'
                        ,offset: [
                            down,right
                        ]
                        ,content: content
                        ,moveOut: true
                        ,success: function(layero, index){
                            $(".my-title").mousemove(function(e){
                                const x = e.pageX - $(this).offset().left + 10;
                                const y = e.pageY - $(this).offset().top + 10;
                                e.target.style.setProperty('--x', `${ x }px`)
                                e.target.style.setProperty('--y', `${ y }px`)
                            });
                        }
                    });
                    $("#configInfoIndex").val(configInfoIndex);
                }
            });
    }
});