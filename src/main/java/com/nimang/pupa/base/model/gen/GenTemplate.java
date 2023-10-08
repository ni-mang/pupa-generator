package com.nimang.pupa.base.model.gen;

import com.nimang.pupa.base.entity.ProTemplate;
import com.nimang.pupa.common.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenTemplate {

    /**
     * 输出路径
     */
    private String path;

    /**
     * 内容
     */
    private String content;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 内容语言
     */
    private String contentLang;

    public void setPath(String path) {
        this.path = path;
        int lastSlash = path.lastIndexOf(Constants.CHAR_SPLIT_SLASH);
        this.fileName = lastSlash>=0?path.substring(lastSlash+1):path;
    }

    public GenTemplate(ProTemplate template) {
        this.path = template.getPath();
//        this.content = Base64.decodeStr(template.getContent());
        this.content = template.getContent();
        int lastPoint = template.getPath().lastIndexOf(".");
        this.fileType = lastPoint>=0?template.getPath().substring(lastPoint+1):"";
        this.contentLang = template.getContentLang();
    }

}
