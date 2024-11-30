package pri.yqx.sensitive;

import java.util.List;

public class SensitiveWordConverter {
        private TreeNode head;

        public SensitiveWordConverter(List<String> strs) {
            TreeNode head = new TreeNode();
            for (String str : strs) {
                head = this.init(head, str);
            }
            this.head = head;
        }
        public String convertStr(String text) {
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

        private TreeNode init(final TreeNode head, String str) {
            TreeNode node=head;
            for(int i = 0; i < str.length(); ++i) {
                Character c = str.charAt(i);
                TreeNode node1 = node.getNode(c);
                if (node1 == null) {
                    node1 = new TreeNode();
                    node.addNode(c, node1);
                }
                node = node1;
            }

            return head;
        }
    }