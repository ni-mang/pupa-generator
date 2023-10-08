package com.nimang.pupa.common.tool.render;

import com.nimang.pupa.common.exception.ApiException;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;

import java.io.StringWriter;
import java.util.Map;

public class FreemarkerRender extends Render{
    private final Configuration configuration;

    public FreemarkerRender() {
        this.ofName = "Freemarker";
        this.configuration = buildConfiguration();
    }

    public FreemarkerRender(Map<String, Object> genData) {
        this.ofName = "Freemarker";
        this.genData = genData;
        this.configuration = buildConfiguration();
    }

    private Configuration buildConfiguration(){
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        configuration.setTemplateLoader(new StringTemplateLoader());
        configuration.setDefaultEncoding("utf-8");
        return configuration;
    }

    /**
     * 字符串渲染
     * @param tempStr
     * @param genData
     * @return
     */
    public String render(String tempStr, Map<String, Object> genData){
        StringWriter sw = new StringWriter();
        try {
            //构建
            freemarker.template.Template template = new freemarker.template.Template("freemarker", tempStr, configuration);
            //输出
            template.process(genData, sw);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
        return sw.toString();
    }

    public String render(String tempStr){
        return render(tempStr, genData);
    }
}
