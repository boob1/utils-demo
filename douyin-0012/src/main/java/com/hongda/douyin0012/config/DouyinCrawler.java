package com.hongda.douyin0012.config;

import com.hongda.douyin0012.pojo.DouyinVideo;
import com.hongda.douyin0012.utils.JSONUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.*;
import java.util.concurrent.*;
import org.joda.time.format.*;

public class DouyinCrawler {

    private static final String FEED_URL = "https://www.douyin.com/aweme/v1/web/feed/";
    private static final String DETAIL_URL = "https://www.douyin.com/aweme/v1/web/aweme/detail/";
    private static final DateTimeFormatter fmt = ISODateTimeFormat.dateTimeParser();
    private static final Random random = new Random();

    public List<DouyinVideo> crawlTopVideos() throws Exception {
        // 1. 获取推荐视频列表
        List<String> videoIds = fetchRecommendationIds();

        // 2. 并发获取视频详情
        List<DouyinVideo> videos = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<DouyinVideo>> futures = new ArrayList<>();

        for (String videoId : videoIds.subList(0, Math.min(videoIds.size(), 50))) {
            futures.add(executor.submit(() -> fetchVideoDetail(videoId)));
            Thread.sleep(200 + random.nextInt(300)); // 随机延迟防封
        }

        for (Future<DouyinVideo> future : futures) {
            DouyinVideo video = future.get();
            if (video != null && video.isWithinLastNHours(10)) {
                videos.add(video);
            }
        }
        executor.shutdown();

        // 3. 按播放量排序取TOP20
        videos.sort((v1, v2) -> Long.compare(v2.getViewCount(), v1.getViewCount()));
        return videos.subList(0, Math.min(videos.size(), 20));
    }

    private List<String> fetchRecommendationIds() throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(FEED_URL);
            addHeaders(request);  // 添加请求头

            String json = client.execute(request, response ->
                    EntityUtils.toString(response.getEntity()));

            JsonNode root = JSONUtils.parse(json);
            List<String> ids = new ArrayList<>();
            for (JsonNode item : root.path("aweme_list")) {
                ids.add(item.path("aweme_id").asText());
            }
            return ids;
        }
    }

    // 在调用JSON解析的地方：
    private DouyinVideo fetchVideoDetail(String videoId) throws Exception {
        String url = DETAIL_URL + "?aweme_id=" + videoId;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            addHeaders(request);

            String json = client.execute(request, response ->
                    EntityUtils.toString(response.getEntity()));

            // 使用我们自定义的JSONUtils进行解析
            JsonNode detail = JSONUtils.parse(json).path("aweme_detail");

            DouyinVideo video = new DouyinVideo();
            video.setVideoId(videoId);
            video.setTitle(detail.path("desc").asText());
            video.setViewCount(detail.path("statistics").path("play_count").asLong());
            video.setCreateTime(fmt.parseDateTime(detail.path("create_time").asText()));
            video.setPlayUrl(detail.path("video").path("play_addr").path("url_list").get(0).asText());
            return video;
        }
    }

    private void addHeaders(HttpGet request) {
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36");
        request.setHeader("Cookie", "\n" +
                "fg_uid=RID202505111425205EF77ED9F4800820A5FD; Expires=Fri, 12-Jun-2026 14:50:28 GMT; Max-Age=31536000; Secure; Path=/; SameSite=None"); // 需替换实际Cookie
        request.setHeader("X-Bogus", XBogusGenerator.generateXBogus(request.getURI().toString()));
    }
}
