package com.wthfeng.cheetah.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangtonghe
 * @date 2017/5/5 09:35
 */
public final class FileUtil {

    /**
     * 默认下载线程数
     */
    private static int DEFAULT_DOWNLOAD_THREAD_NUM = 20;


    /**
     * 一次读取默认字节
     */
    private static byte step[] = new byte[4096];

    /**
     * 文件下载
     *
     * @param originUrl 文件源路径
     * @param destPath  文件下载目录
     */
    private static void download(String originUrl, String destPath) {


        String fileName = originUrl.substring(originUrl.lastIndexOf("/") + 1);
        if (StringUtil.isEmpty(fileName)) {
            return;
        }
        try {
            URL destUrl = new URL(originUrl);
            URLConnection urlConnection = destUrl.openConnection();
            urlConnection.setRequestProperty("User-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36");
            urlConnection.setRequestProperty("Cookie","__jsluid=4aba900eae2481a58f890b7e766df71d");
            InputStream inputStream = urlConnection.getInputStream();
            OutputStream outputStream = null;
            if (destPath.endsWith("/")) {
                destPath = destPath.substring(0, destPath.length() - 1);
            }

            byte[] buffer = new byte[1024];
            int readNum = 0;

            String destFile = destPath + File.separator + fileName;
            outputStream = new FileOutputStream(destFile);

            while ((readNum = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, readNum);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 批量下载文件
     *
     * @param fileUrls  文件路径集合
     * @param path      文件目录
     * @param threadNum 线程数
     */
    public static void batchDownloadFile(Set<String> fileUrls, String path, int threadNum) {
        File destDir = new File(path);
        if (!destDir.isDirectory() ) {
            System.err.println("下载目录错误");
            return;
        }
        if(!destDir.exists()){
            destDir.mkdirs();
        }
        threadNum = threadNum > 0 ? threadNum : DEFAULT_DOWNLOAD_THREAD_NUM;
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        fileUrls.forEach((url -> {
            executorService.execute(() -> FileUtil.download(url, path));
        }));
        //TODO 等爬虫线程停止后再停止，或至少等待一段时间再停止
        executorService.shutdown();
    }

    /**
     * 批量下载文件
     *
     * @param fileUrls 文件路径集合
     * @param path     文件目录
     */
    public static void batchDownloadFile(Set<String> fileUrls, String path) {
        batchDownloadFile(fileUrls, path, DEFAULT_DOWNLOAD_THREAD_NUM);
    }


}
