package com.hongda.douyin0012;

import com.hongda.douyin0012.config.DouyinCrawler;
import com.hongda.douyin0012.config.VideoDownloader;
import com.hongda.douyin0012.pojo.DouyinVideo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Douyin0012Application {

    public static void main(String[] args) {
        SpringApplication.run(Douyin0012Application.class, args);


        try {
            DouyinCrawler crawler = new DouyinCrawler();
            List<DouyinVideo> topVideos = crawler.crawlTopVideos();

            System.out.println("=== TOP 20 Videos ===");
            for (int i = 0; i < topVideos.size(); i++) {
                DouyinVideo video = topVideos.get(i);
                System.out.printf("%d. %s (%,d views)\n",
                        i + 1, video.getTitle(), video.getViewCount());

                // 下载视频
                VideoDownloader.downloadVideo(
                        video.getPlayUrl(),
                        "downloads/"
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
