layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        $ = layui.$;

    // 设置页面关键值
    $("#reqUrl").val("/cfg/template/add");
    $("#reqType").val("POST");
    $("#tableName").val("templateTable");

    // 渲染下拉选项
    // 模板引擎类型
    renderEnums(form, 'tempType', 'temp_type', '')
    // 模板内容语言
    renderEnums(form, 'contentLang', 'temp_lang', '')

    // 渲染monaco编辑器
    renderMonaco("container", "", "java")

    // 切换模板内容语言
    form.on('select(contentLang)', function(obj){
        monaco.editor.setModelLanguage(editor.getModel(), obj.value);
    });

    // 触发提交运行扩展函数-获取编辑器内容
    window.extendFun = function(data){
        data["content"] = editor.getValue();
    }

});

