package com.hongda.douyin0012.config;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.net.URL;

public class VideoDownloader {

    public static void downloadVideo(String url, String path) throws Exception {
        FileUtils.copyURLToFile(
                new URL(url),
                new File(path + System.currentTimeMillis() + ".mp4"),
                5000,  // 连接超时
                60000  // 读取超时
        );
    }
}
