layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        table = layui.table,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/pro/project/edit");
    $("#reqType").val("PUT");
    $("#tableName").val("projectTable");

    // 填充表单数据
    let reqData = {id:$("#id").val()};
    let url = serverPath + "/pro/project/get";
    ajax(url,"GET",reqData,
        {
            successFn:function(res) {
                let data = res["data"];
                form.val('editForm', data)

                // 加载配置数据下拉选项
                renderSelect(
                    $("select[name='configId']"),
                    "/cfg/config/listForSelect",
                    {},
                    "name", "id",
                    data.configId,
                    function (){
                        form.render("select")
                    }
                );

                // 获取扩展数据并渲染
                $("#extend").val(JSON.stringify(data.extendList));
                renderExtend(table,'#extendDiv',data.configId,0, data.extendList);
            }
        });

    // 切换配置项
    form.on('select(configId)', function(obj){
        if(obj.value === ""){
            return;
        }

        let valData = JSON.parse($("#extend").val());
        // 获取扩展数据并渲染
        renderExtend(table,'#extendDiv',obj.value,0,valData);
    });

    // 触发提交运行扩展函数-获取扩展数据
    window.extendFun = function(data){
        data["extendList"] = table.cache["extendDiv"];
    }

});