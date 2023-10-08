layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        table = layui.table,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/pro/datasource/add");
    $("#reqType").val("POST");
    $("#tableName").val("datasourceTable");

    let configId = $("#configId").val();

    // 加载配置数据下拉选项
    renderSelect(
        $("select[name='brand']"),
        "/enum/getEnums",
        {label:'datasource_brand'},
        "desc", "value",
        '',
        function (){
            form.render("select")
        }
    );

    // 获取扩展数据并渲染
    renderExtend(table,'#extendDiv',configId,2);

    // 触发提交运行扩展函数-获取扩展数据、类型映射数据
    window.extendFun = function(data){
        data["extendList"] = table.cache["extendDiv"];
    }
});