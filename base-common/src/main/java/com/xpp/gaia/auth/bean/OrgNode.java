package com.xpp.gaia.auth.bean;

import java.util.List;
import lombok.Data;

/**
 * 组织专用对象
 *
 * @author Akira
 * @since 2022/4/2
 */
@Data
public class OrgNode {

    private String id;

    private String text;

    private String parentId;

    private String codeRule;

    private List<OrgNode> children;
}
