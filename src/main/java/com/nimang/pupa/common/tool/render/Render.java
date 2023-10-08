package com.nimang.pupa.common.tool.render;

import lombok.Data;

import java.util.Map;

@Data
public abstract class Render {

    protected String ofName;
    protected Map<String, Object> genData;


    public abstract String render(String tempStr);
}
