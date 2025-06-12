package com.xpp.gaia.http;

import java.util.List;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * 自定义RestTemplate MessageConverter
 *
 * @author Akira
 * @since 2021/11/21
 */
public class CustomRestTemplateMessageConverters {

    public List<HttpMessageConverter<?>> converters;

    public static class MyMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    }
}
