package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/23 21:14
 */
public class TreeBuilder {
  // 递归构建树结构方法
  public static List<TreeNode> buildTree(List<Map<String, Object>> dataList) {
    List<TreeNode> roots = new ArrayList<>();
    Map<Integer, TreeNode> nodeMap = new HashMap<>();

    // 第一遍：创建所有节点并建立映射
    for (Map<String, Object> item : dataList) {
      TreeNode node = new TreeNode(item);
      nodeMap.put((Integer) item.get("AUTOSTATISTICS_ID"), node);
    }

    // 第二遍：建立父子关系
    for (Map<String, Object> item : dataList) {
      Integer pid = (Integer) item.get("AUTOSTATISTICS_PID");
      TreeNode currentNode = nodeMap.get(item.get("AUTOSTATISTICS_ID"));

      if (pid == -1) {
        roots.add(currentNode);
      } else {
        TreeNode parentNode = nodeMap.get(pid);
        if (parentNode != null) {
          parentNode.addChild(currentNode);
        }
      }
    }

    return roots;
  }
}
