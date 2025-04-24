package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/23 21:13
 */
public class TreeNode {
  private Map<String, Object> node;
  private List<TreeNode> children = new ArrayList<>();

  public TreeNode(Map<String, Object> nodeData) {
    this.node = new HashMap<>(nodeData);
  }

  // 添加子节点
  public void addChild(TreeNode child) {
    children.add(child);
  }

  // Getters
  public Map<String, Object> getNode() {
    return node;
  }

  public List<TreeNode> getChildren() {
    return children;
  }
}
