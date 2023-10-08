package com.nimang.pupa.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FileUtil {
    /**
     * 下载文件
     * @param is
     * @param fileName
     * @param mimeType https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
     *
     */
    public static void download(HttpServletResponse response, InputStream is, String fileName, String mimeType) {
        if(mimeType == null || "".equals(mimeType)){
            mimeType = "application/octet-stream";
        }

        BufferedInputStream bufInStream = null;
        OutputStream outStream = null;
        try {

            bufInStream = new BufferedInputStream(is);

            response.setContentType(mimeType);
            response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setCharacterEncoding("UTF-8");
            //设置响应头部，以及文件名进行中文防止乱码转码操作
            response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));

            byte[] buffer = new byte[10240];
            int len = 0;
            outStream = response.getOutputStream();
            while ((len = bufInStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, len);
            }
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (bufInStream != null) {
                    bufInStream.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void download(InputStream is, String fileName) {
        download(is, fileName, null);
    }

    public static void download(InputStream is, String fileName, String mimeType) {
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        assert response != null;
        download(response, is, fileName, mimeType);
    }

    public static void download(byte[] bytes, String fileName) {
        download(bytes, fileName, null);
    }

    public static void download(byte[] bytes, String fileName, String mimeType) {
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        assert response != null;
        InputStream is = new ByteArrayInputStream(bytes);
        download(response, is, fileName, mimeType);
    }


    public static String read(MultipartFile file) {
        StringBuilder builder = new StringBuilder();
        try {
            InputStream is = file.getInputStream();
            InputStreamReader isReader = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isReader);
            //循环逐行读取
            while (br.ready()) {
                builder.append(br.readLine());
            }
            //关闭流
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }
}
