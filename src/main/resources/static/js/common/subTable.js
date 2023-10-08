/**
 * 获取扩展数据并渲染
 * @param table
 * @param elem
 * @param configId
 * @param scope
 * @param valData
 */
function renderExtend(table,elem,configId,scope,valData,readOnly){
    ajax(serverPath + "/cfg/extend/query","GET",{configId:configId,scope:scope,status:1},
        {
            successFn:function(res) {
                let data = res.data;
                if(valData){
                    for(let i=0;i<data.length;i++){
                        for(let j=0;j<valData.length;j++){
                            let extend = data[i];
                            let val = valData[j];
                            if(extend["key"] === val["key"]){
                                extend["value"] = val["value"];
                            }
                        }
                    }
                }
                // 加载扩展项
                let valueOp = {field: 'value', edit:'text', title: '<span style="color: green">值（可编辑）</span>', templet:function (d){
                        return '<span style="color: green">' + d.value + '</span>'
                    }};
                if(readOnly == true){
                    valueOp = {field: 'value', title: '<span style="color: green">值</span>', templet:function (d){
                        return '<span style="color: green">' + d.value + '</span>'
                    }}
                }
                table.render({
                    elem: elem,
                    data: data,
                    cols: [[
                        {field: 'name', width: 150, title: '名称'},
                        {field: 'comments', width: 200, title: '说明'},
                        {field: 'key', width: 200, title: '<span style="color: red">键</span>', templet:function (d){
                                return '<span style="color: red">' + d.key + '</span>'
                            }},
                        valueOp,
                    ]],
                    limit: 50,
                });

            }
        });
}

/**
 * 获取数据库类型映射数据并渲染
 * @param table
 * @param elem
 * @param brand
 * @param valData
 * @param readOnly
 */
function renderMapper(table,elem,brand,valData,readOnly){
    ajax(serverPath + "/column-type/columnMapper","GET",{brand:brand},
        {
            successFn:function(res) {
                let data = res.data;
                if(valData){
                    for(let i=0;i<data.length;i++){
                        for(let j=0;j<valData.length;j++){
                            let mapper = data[i];
                            let val = valData[j];
                            if(mapper["columnType"] === val["columnType"]){
                                mapper["attrType"] = val["attrType"];
                                mapper["importPath"] = val["importPath"];
                            }
                        }
                    }
                }
                // 加载扩展项
                let attrTypeOP = {field: 'attrType', width: 150, edit:'text', title: '<span style="color: green">属性类型</span>', templet:function (d){
                        return '<span style="color: green">' + d.attrType + '</span>'
                    }};
                let importPathOP = {field: 'importPath', edit:'text', title: '<span style="color: green">引用路径</span>', templet:function (d){
                        return '<span style="color: green">' + d.importPath + '</span>'
                    }};
                if(readOnly == true){
                    attrTypeOP = {field: 'attrType', width: 150, title: '<span style="color: green">属性类型</span>', templet:function (d){
                            return '<span style="color: green">' + d.attrType + '</span>'
                        }};
                    importPathOP = {field: 'importPath', title: '<span style="color: green">引用路径</span>', templet:function (d){
                            return '<span style="color: green">' + d.importPath + '</span>'
                        }};
                }
                table.render({
                    elem: elem,
                    data: data,
                    cols: [[
                        {field: 'columnType', width: 150, title: '<span style="color: red">列类型</span>', templet:function (d){
                                return '<span style="color: red">' + d.columnType + '</span>'
                            }},
                        attrTypeOP,
                        importPathOP
                    ]],
                    limit: 50,
                });

            }
        });
}