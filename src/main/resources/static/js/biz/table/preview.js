layui.use(['form'], function () {
    let form = layui.form,
        layer = layui.layer,
        $ = layui.$;

    let fileObj = {}

    // 代码生成预览
    let reqData = {id:$("#id").val()};
    let url = serverPath + "/gen/previewTable";
    ajax(url,"POST",reqData,
        {
            beforeSendFn:function(){
                // 添加遮罩层
                this.layerIndex = layer.load(0, { shade: [0.5, '#393D49'] });
            },
            successFn:function(res) {
                let data = res["data"]
                for(let i=0;i<data.length;i++){
                    fileObj[data[i].fileName] = data[i];
                }

                // 加载配置数据下拉选项
                renderSelectWithData(
                    $("select[name='file']"),
                    data,
                    "fileName", "fileName",
                    data.brand,
                    function (){
                        form.render("select")
                    },false
                );
                $("#genPath").val(data[0].path);
                // 渲染monaco编辑器
                renderMonaco("container", data[0].content, data[0].contentLang)
            },
            completeFn:function(){
                // 关闭遮罩层
                layer.close(this.layerIndex);
            }
        });

    // 切换文件
    form.on('select(file)', function(obj){
        let data = fileObj[obj.value];
        $("#genPath").val(data.path);
        monaco.editor.setModelLanguage(editor.getModel(), data.contentLang);
        editor.setValue(data.content);
    });

});