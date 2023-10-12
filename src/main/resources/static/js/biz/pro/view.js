layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        table = layui.table,
        $ = layui.$;

    // 当前弹出层，防止ID被覆盖
    const parentIndex = layer.index;

    // 填充表单数据
    let reqData = {id:$("#id").val()};
    let url = serverPath + "/pro/project/get";
    ajax(url,"GET",reqData,
        {
            successFn:function(res) {
                let data = res["data"];
                form.val('viewForm', data)

                // 获取扩展数据并渲染
                $("#extend").val(JSON.stringify(data.extendList));
                renderExtend(table,'#extendDiv',data.configId,0, data.extendList, true);
            }
        });

    // 克隆配置项
    form.on('submit(cloneBtn)', function(obj){
        let url = serverPath + "/cfg/config/clone";
        ajax(url,"POST",{id:$("#configId").val()},
            {
                successFn:function(res) {
                    layer.msg('操作完成', {
                        icon: 6,
                        time: 1000,
                        shade: 0.3
                    },function (){
                        location.href = "#" + pagePath + "/config/list.html";
                        layer.close(parentIndex);
                    });
                }
            })
    });
});