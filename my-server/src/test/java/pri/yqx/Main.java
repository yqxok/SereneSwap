//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pri.yqx.sensitive.GrepFileLoader;
import pri.yqx.sensitive.SensitiveWordConverter;

public class Main {

    public static void main(String[] args) {
        List<String> list = GrepFileLoader.loadGrepFile();
        SensitiveWordConverter converter = new SensitiveWordConverter(list);
        String str = converter.convertStr("滚,我操你妈的");
        System.out.println(str);
    }

    static class TreeNodeBuilder {
        private static final Logger log = LoggerFactory.getLogger(TreeNodeBuilder.class);
        private TreeNode head;

        public TreeNodeBuilder(String[] strs) {
            TreeNode head = new TreeNode();
            String[] var3 = strs;
            int var4 = strs.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String str = var3[var5];
                head = this.init(head, str);
            }

            this.head = head;
        }

        public String convertStr(String text) {
            if (this.head == null) {
                log.error("请先调用initTreeNode方法初始化敏感词前缀树");
                return text;
            } else {
                StringBuilder builder = new StringBuilder();

                for(int i = 0; i < text.length(); ++i) {
                    Character c = text.charAt(i);
                    TreeNode node = this.head.getNode(c);
                    if (node == null) {
                        builder.append(c);
                    } else {
                        for(int pos = i; pos < text.length(); node = node.getNode(text.charAt(pos))) {
                            if (node == null) {
                                builder.append(c);
                                break;
                            }

                            if (!node.hasNext()) {
                                for(int j = i; j <= pos; ++j) {
                                    builder.append('*');
                                }

                                i = pos;
                                break;
                            }

                            ++pos;
                        }
                    }
                }

                return builder.toString();
            }
        }

        private TreeNode init(TreeNode head, String str) {
            for(int i = 0; i < str.length(); ++i) {
                Character c = str.charAt(i);
                TreeNode node1 = head.getNode(c);
                if (node1 == null) {
                    node1 = new TreeNode();
                    head.addNode(c, node1);
                }

                head = node1;
            }

            return head;
        }
    }

    /**
     * 前缀树节点
     */
    static class TreeNode {
        private Map<Character, TreeNode> nextNode = new HashMap();

        TreeNode() {
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
}
