layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/cfg/template/edit");
    $("#reqType").val("PUT");
    $("#tableName").val("templateTable");

    // 填充表单数据
    let reqData = {id:$("#id").val()};
    let url = serverPath + "/cfg/template/get";
    ajax(url,"GET",reqData,
        {
            successFn:function(res) {
                let data = res["data"]
                form.val('editForm', data);

                // 渲染下拉选项
                // 模板引擎类型
                renderEnums(form, 'tempType', 'temp_type', data.tempType)
                // 模板语言
                renderEnums(form, 'contentLang', 'temp_lang', data.contentLang)

                // 渲染monaco编辑器
                renderMonaco("container", data.content, data.contentLang)
            }
        });

    // 切换模板内容语言
    form.on('select(contentLang)', function(obj){
        monaco.editor.setModelLanguage(editor.getModel(), obj.value);
    });

    // 触发提交运行扩展函数-获取编辑器内容
    window.extendFun = function(data){
        data["content"] = editor.getValue();
    }

});