package com.xpp.gaia.http;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * MappingJackson2HttpMessageConverter扩展
 *
 * @author Akira
 * @implNote Resttemplate中默认的MappingJackson2HttpMessageConverter仅支持application/json解析，现增加text/plain类型解析
 * @since 2023/7/27
 */
public class GaiaMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public GaiaMappingJackson2HttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        super.setSupportedMediaTypes(mediaTypes);
    }
}
