//www.programiz.com/ -> insert

import java.io.IOException;

class Node {

    String data;
    String numLines;
    Node parent;
    Node left;
    Node right;
    int color;
    RedBlackTree rd;

}

class RedBlackTree {

    private Node root;
    private final Node TNULL;

    private Node checkAssoc(Node node, String key, String num) {
        while (node != TNULL) {
            if (key.toLowerCase().equals(node.data)) {
                String[] values = node.numLines.split(" ");
                for (int i = 0; i < values.length; i++) {
                    if (values[i].equals(num)) {
                        return node;
                    }
                }
                int flag = 1;
                if (flag == 1) {
                    return null;
                }

            }

            if (key.compareTo(node.data) < 0) {  //key < node.data
                //return searchTreeHelper(node.left, key);
                node = node.left;
            } //return searchTreeHelper(node.right, key);
            else if (key.compareTo(node.data) > 0) {
                node = node.right;
            }
        }
        return null;
    }

    private int checkRepeat(Node node, String key, String numLines) {
        while (node != TNULL) {

            if (key.toLowerCase().equals(node.data)) {
                String[] values = node.numLines.split(" ");
                int flag = 0;
                for (int i = 0; i < values.length; i++) {
                    if (values[i].equals(numLines)) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    node.numLines += " " + numLines;
                    node.rd.insert(numLines, "");
                }
                return 1;
            }
            if (key.compareTo(node.data) < 0) {  //key < node.data
                //return searchTreeHelper(node.left, key);
                node = node.left;
            } //return searchTreeHelper(node.right, key);
            else if (key.compareTo(node.data) > 0) {
                node = node.right;
            }

        }

        return 0;
    }

    private Node checkLines(Node node, String key) {

        key = key.toLowerCase();

        while (node != TNULL) {

            if (key.toLowerCase().equals(node.data)) {
                return node;
            }

            if (key.compareTo(node.data) < 0) {  //key < node.data
                //return searchTreeHelper(node.left, key);
                node = node.left;
            } //return searchTreeHelper(node.right, key);
            else if (key.compareTo(node.data) > 0) {
                node = node.right;
            }
        }
        return null;
    }

    String preOrder(Node node) {
        if (node != null) {
            String str = node.data;
            str += " " + preOrder(node.left);
            str += " " + preOrder(node.right);
            return str;
        }
        return "";
    }

    private void printHelper(Node currPtr, String indent, boolean last) {
        // print the tree structure on the screen
        if (currPtr != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }

            System.out.println(currPtr.data);

            printHelper(currPtr.left, indent, false);
            printHelper(currPtr.right, indent, true);
        }
    }

    private void fixInsert(Node k) {
        Node u;
        while (k.parent.color == 1) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                if (u.color == 1) {
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right;

                if (u.color == 1) {
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = 0;
    }

    public RedBlackTree() {
        TNULL = new Node();
        TNULL.color = 0;
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }

    public void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    public void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    public void insert(String key, String numLines) {
        key = key.toLowerCase();
        Node node = new Node();
        node.parent = null;
        node.data = key;
        node.numLines = numLines;
        node.left = TNULL;
        node.right = TNULL;
        node.color = 1;

        Node y = null;
        Node x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.data.compareTo(x.data) < 0) { // node.data < x.data
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.data.compareTo(y.data) < 0) { //node.data < y.data
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null) {
            node.color = 0;
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        fixInsert(node);
    }

    static String readLn(int maxLg) {
        {
            byte lin[] = new byte[maxLg];
            int lg = 0, car = -1;
            String line = "";
            try {
                while (lg < maxLg) {
                    car = System.in.read();
                    if ((car < 0) || (car == '\n')) {
                        break;
                    }
                    lin[lg++] += car;
                }
            } catch (IOException e) {
                return (null);
            }
            if ((car < 0) && (lg == 0)) {
                return (null);
            }
            return (new String(lin, 0, lg));
        }

    }

    public static void main(String[] args) {

        RedBlackTree tree = new RedBlackTree();
        String[] stats = new String[1000];
        int numStats = 0;

        while (true) {
            String comands;
            comands = readLn(400);
            String[] action = comands.split(" ");
            if (action[0].equals("TEXTO")) {
                int numLines = 0;
                while (true) {
                    String line = readLn(5000);
                    String[] l = line.split(" |\\(|\\)|\\,|\\;|\\.");
                    if (line.equals("FIM.")) {
                        stats[numStats] = "GUARDADO.";
                        numStats++;
                        break;
                    }
                    for (String l1 : l) {
                        l1 = l1.toLowerCase();
                        int num = tree.checkRepeat(tree.root, l1, String.valueOf(numLines));
                        if (num == 0) {
                            tree.insert(l1, String.valueOf(numLines));
                            Node auxNode = tree.root;
                            while (auxNode != null) {

                                if (l1.equals(auxNode.data)) {
                                    auxNode.rd = new RedBlackTree();
                                    auxNode.rd.insert(String.valueOf(numLines), "");
                                    break;
                                } else if (l1.compareTo(auxNode.data) < 0) {  //key < node.data
                                    auxNode = auxNode.left;
                                } else if (l1.compareTo(auxNode.data) > 0) {
                                    auxNode = auxNode.right;
                                }
                            }
                        }
                    }
                    numLines++;
                }

            } else if (action[0].equals("LINHAS")) {

                Node auxNode = tree.checkLines(tree.root, action[1]);
                if (auxNode == null) {
                    stats[numStats] = "-1";
                    numStats++;
                } else {
                    //stats[numStats] = auxNode.numLines;
                    //auxNode contem a arvore para percorrer 
                    stats[numStats] = auxNode.rd.preOrder(auxNode.rd.root);
                    numStats++;
                }

            } else if (action[0].equals("ASSOC")) {
                Node auxNode = tree.checkAssoc(tree.root, action[1], action[2]);
                //percorrer o auxnode e verificar se existe na linha
                if (auxNode == null) {
                    stats[numStats] = "NAO ENCONTRADA";
                    numStats++;
                } else {
                    stats[numStats] = "ENCONTRADA";
                    numStats++;
                }
            } else if (action[0].equals("TCHAU")) {
                for (int i = 0; i < numStats; i++) {
                    System.out.println(stats[i]);
                }

                //tree.printHelper(tree.root, "", true);
                break;
            }
        }
    }
}
