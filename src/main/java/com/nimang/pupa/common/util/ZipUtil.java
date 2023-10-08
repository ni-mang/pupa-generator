package com.nimang.pupa.common.util;

import cn.hutool.http.HttpDownloader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.springframework.util.StreamUtils.BUFFER_SIZE;

/**
 * @ClassName ZipUtil
 * @Author linlc
 * @Date 2022/3/11 16:24
 */
public class ZipUtil {

    /**
     * url获取文件并压缩为zip
     * @param urlList
     * @return
     * @throws RuntimeException
     */
    public static InputStream  urlsToZip(List<NetFileInfo> urlList) throws RuntimeException {
        List<ZipEntryInfo> inList = new ArrayList<>();

        for (NetFileInfo fileInfo:urlList){
            byte[] fileBytes = HttpDownloader.downloadBytes(fileInfo.getFileUrl());
            InputStream inputStream = new ByteArrayInputStream(fileBytes);
            inList.add(new ZipEntryInfo(fileInfo.getFileName(), inputStream));
        }
        return toZipForStream(inList);
    }

    /**
     * 把文件集合打成zip压缩包
     * @throws RuntimeException 异常
     */
    public static InputStream  toZipForStream(List<ZipEntryInfo> entryInfoList) throws RuntimeException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(bos);
            for (ZipEntryInfo entryInfo:entryInfoList){
                InputStream in = entryInfo.getInputStream();
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(entryInfo.getFileName()));
                int len;
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
//                zos.setComment(downName);
                zos.closeEntry();
                in.close();
                bos.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("zipFile error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                }
            }
        }
        return new ByteArrayInputStream(bos.toByteArray());
    }

    public static InputStream  toZipForBytes(List<ZipByteInfo> byteInfoList) throws RuntimeException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(bos);
            for (ZipByteInfo byteInfo:byteInfoList){
                byte[] buf = byteInfo.getBytes();
                zos.putNextEntry(new ZipEntry(byteInfo.getFileName()));
                zos.write(buf, 0, buf.length);
                zos.closeEntry();
                bos.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("zipFile error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                }
            }
        }
        return new ByteArrayInputStream(bos.toByteArray());
    }

    /**
     * 下载文件
     * @param in
     * @param fileName
     */
    public static void downloadZip(InputStream in, String fileName) {
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        assert response != null;

        BufferedInputStream bufInStream = null;
        OutputStream outStream = null;
        try {

            bufInStream = new BufferedInputStream(in);

            response.setContentType("application/zip");
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
                if (in != null) {
                    in.close();
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ZipEntryInfo{
        /** 文件名 **/
        private String fileName;

        /** 输入流 **/
        private InputStream inputStream;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ZipByteInfo{
        /** 文件名 **/
        private String fileName;

        /** 输入流 **/
        private byte[] bytes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NetFileInfo{
        /** 文件名 **/
        private String fileName;

        /** 文件链接 **/
        private String fileUrl;
    }
}
