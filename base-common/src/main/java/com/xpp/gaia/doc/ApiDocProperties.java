package com.xpp.gaia.doc;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 接口文档分组属性
 *
 * @author Akira
 * @since 2021/11/25
 */
@Data
@ConfigurationProperties(prefix = "xpp.doc")
public class ApiDocProperties {

    private String title;

    private String description;

    private String author;

    private String serviceUrl;

    private List<Group> groups;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Group {

        String name;
        String basePackage;
    }

}
