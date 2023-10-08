layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/column-type/edit");
    $("#reqType").val("PUT");

    let id = $("#id").val();
    // 填充表单数据
    let url = serverPath + "/column-type/get";
    ajax(url,"GET",{id:id},
        {
            successFn:function(res) {
                let data = res["data"]
                form.val('editForm', data)

                // 加载配置数据下拉选项
                renderSelect(
                    $("select[name='brand']"),
                    "/enum/getEnums",
                    {label:'datasource_brand'},
                    "desc", "value",
                    data.brand,
                    function (){
                        form.render("select")
                    }
                );
            }
        });

});