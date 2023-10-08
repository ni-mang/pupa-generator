layui.use(['form'], function () {
    var form = layui.form,
        layer = layui.layer,
        table = layui.table,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/pro/project/add");
    $("#reqType").val("POST");
    $("#tableName").val("projectTable");

    // 加载配置数据下拉选项
    renderSelect(
        $("select[name='configId']"),
        "/cfg/config/listForSelect",
        {},
        "name", "id",
        "",
        function (){
            form.render("select")
        }
    );

    // 切换配置项
    form.on('select(configId)', function(obj){
        if(obj.value === ""){
            return;
        }
        // 获取扩展数据并渲染
        renderExtend(table,'#extendDiv',obj.value,0);
    });

    // 触发提交运行扩展函数-获取扩展数据
    window.extendFun = function(data){
        data["extendList"] = table.cache["extendDiv"];
    }

});