// 请求路径
layui.use(['form'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.$;

    // 初始化表单，要加上，不然刷新部分组件可能会不加载
    form.render();

    // 当前弹出层，防止ID被覆盖
    const parentIndex = layer.index;

    //监听提交
    form.on('submit(saveBtn)', function(obj){
        layer.msg('正在提交...', {
            offset: '250px',
            icon: 16,
            time:1000000,
            shade:0.3
        });

        //请求接口
        let reqUrl = $("#reqUrl").val();
        let reqType = $("#reqType").val();
        let tableName = $("#tableName").val();
        if(tableName===undefined){
            tableName = 'viewTable';
        }
        // 扩展函数
        let hasExtend = $("#hasExtend").val();
        if(hasExtend){
            try {
                extendFun(obj.field);
            } catch(e) {}
        }
        const url = serverPath + reqUrl;
        ajax(url,reqType,obj.field,
            {
                beforeSendFn:function(){
                    // 添加遮罩层
                    this.layerIndex = layer.load(0, { shade: [0.5, '#393D49'] });
                },
                successFn:function(res) {
                    layer.msg('保存成功', {
                        icon: 6,
                        time: 1000,
                        shade: 0.3
                    }, function () {
                        // 刷新列表页
                        parent.layui.table.reload(tableName);
                        // 关闭弹出层
                        layer.close(parentIndex);
                    });
                },
                completeFn:function(){
                    // 关闭遮罩层
                    layer.close(this.layerIndex);
                }
            })
        return false;
    });
});