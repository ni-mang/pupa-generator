layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        $ = layui.$;


    // 填充表单数据
    let reqData = {id:$("#id").val()};
    let url = serverPath + "/cfg/template/get";
    ajax(url,"GET",reqData,
        {
            successFn:function(res) {
                let data = res["data"]
                form.val('viewForm', data);

                // 渲染下拉选项
                // 模板引擎类型
                renderEnums(form, 'tempType', 'temp_type', data.tempType)
                // 模板语言
                renderEnums(form, 'contentLang', 'temp_lang', data.contentLang)

                // 渲染monaco编辑器
                renderMonaco("container", data.content, data.contentLang, true)
            }
        });

});