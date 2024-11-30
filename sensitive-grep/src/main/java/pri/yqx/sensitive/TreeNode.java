package pri.yqx.sensitive;

import java.util.HashMap;
import java.util.Map;

public class TreeNode {
    private final Map<Character, TreeNode> nextNode;
    TreeNode(){
        nextNode=new HashMap<>();
    }

    public boolean hasNext() {
        return this.nextNode.size() > 0;
    }

    public void addNode(Character c, TreeNode treeNode) {
        this.nextNode.put(c, treeNode);
    }

    public TreeNode getNode(Character c) {
        return (TreeNode)this.nextNode.get(c);
    }
}
