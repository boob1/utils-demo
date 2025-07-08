package com.hongda.douyin0012.pojo;


import lombok.Data;
import org.joda.time.DateTime;

@Data
public class DouyinVideo {
    private String videoId;
    private String title;
    private long viewCount;
    private DateTime createTime;
    private String playUrl;

    // Getters & Setters
    public boolean isWithinLastNHours(int hours) {
        DateTime cutoff = DateTime.now().minusHours(hours);
        return createTime.isAfter(cutoff);
    }
}