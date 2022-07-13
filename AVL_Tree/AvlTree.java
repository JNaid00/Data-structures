//Jesse Naidoo
//u21433102

public class AvlTree<T extends Comparable<T>> {
    public Node<T> root;

    public AvlTree() {
        this.root = null;
    }

    int getHeight(Node<T> N) {
        if (N == null)
            return 0;

        return N.height;
    }

    int getBalanceFactor(Node<T> nodeptr) {
        if (nodeptr == null){
            return 0;
        }
        int left = 0;
        int right = 0;
        if (nodeptr.left != null) {
            left = getHeight(nodeptr.left) + 1;
        }

        if (nodeptr.right != null) {
            right = getHeight(nodeptr.right) + 1;
        }

        return right - left;
    }

    /* Printing AvlTree in inorder */
    void print(Node<T> node) {
        if (node == null)
            return;

        print(node.left);

        System.out.print(node.data + " ");

        print(node.right);
    }

    int getMaxheight(Node<T> node) {
        if (node == null){
            return 0;
        }
        if (node.right == null && node.left == null) {
            return 0;
        } else if (node.right == null && node.left != null) {
            return getHeight(node.left) + 1;
        } else if (node.right != null && node.left == null) {
            return getHeight(node.right) + 1;
        } else {
            if (getHeight(node.right) >= getHeight(node.left)) {
                return getHeight(node.right) + 1;
            } else {
                return getHeight(node.left) + 1;
            }
        }

    }

    public Node<T> rightRotation(Node<T> nodeptr) {
        Node<T> tempLeft = nodeptr.left;
        Node<T> transfer = tempLeft.right;
        tempLeft.right = nodeptr;
        nodeptr.left = transfer;

        tempLeft.height = getMaxheight(tempLeft);
        nodeptr.height = getMaxheight(nodeptr);
        return tempLeft;
    }

    public Node<T> leftRotation(Node<T> nodeptr) {
        Node<T> tempRight = nodeptr.right;
        Node<T> transfer = tempRight.left;
        tempRight.left = nodeptr;
        nodeptr.right = transfer;

        nodeptr.height = getMaxheight(nodeptr);
        tempRight.height = getMaxheight(tempRight);

        return tempRight;
    }

    boolean containsData(Node<T> node, T data) {
        while (node != null) {
            if (node.data == data) {
                return true;
            } else if (node.data.compareTo(data) > 0)
                node = node.left;
            else if (node.data.compareTo(data) < 0)
                node = node.right;
        }

        return false;
    }

    /* Do not edit the code above */

    /**
     * Insert the given data into the tree.
     * Duplicate data is not allowed. just return the node.
     */

    Node<T> insert(Node<T> node, T data) {
        if (node == null) {
            Node<T> newNode = new Node<T>(data);
            // newNode.height = 1;
            return newNode;
        }

        if (data.compareTo(node.data) > 0)
            node.right = insert(node.right, data);
        else if (data.compareTo(node.data) < 0)
            node.left = insert(node.left, data);
        else
            return node;

        node.height = getMaxheight(node);

        int balance = getBalanceFactor(node);

        if (balance < -1) {
            if (data.compareTo(node.left.data) > 0) {
                node.left = leftRotation(node.left);
                node = rightRotation(node);
                
                // return node;
                
            } else if (data.compareTo(node.left.data) < 0) {
                node =  rightRotation(node);
            }
            node.height = getMaxheight(node);
            return node;
        } else if (balance > 1) {
            if (data.compareTo(node.right.data) < 0) {
                node.right = rightRotation(node.right);
                node = leftRotation(node);
                
                
            } else if (data.compareTo(node.right.data) > 0) {
                node =  leftRotation(node);
            }
            node.height = getMaxheight(node);
            return node;
        }
        return node;
    }

    /**
     * Remove / Delete the node based on the given data
     * Return the node / root if the data do not exist
     */
    //correcy version
    Node<T> removeNode(Node<T> root, T data) {
        if (root == null) {
            return null;
        }
        // if (containsData(root, data) == false) {
        //     return root;
        // }
        //Node<T> parent = root;// Only applicable after node found, parent of root;
        if (root.data.compareTo(data) > 0) {
            root.left = removeNode(root.left, data);
        } else if (root.data.compareTo(data) < 0) {
            root.right = removeNode(root.right, data);
        } else if (root.data == data) {
            if (root.left == null || root.right == null) {
                Node<T> replace = root.right == null ? root.left : root.right;
                root = replace == null ? null : replace;
                if (root == null)
                    return null;

            } else {
                // get rightmost node in left substree
                Node<T> righmostNode = root.left;
                // Node<T> parentRightMost = righmostNode;
                while (righmostNode.right != null) {
                    // parentRightMost = righmostNode;
                    righmostNode = righmostNode.right;
                }

                T tempdata = righmostNode.data;
                //root.left serves as the parent in this case
                root.left = removeNode(root.left, righmostNode.data);
                root.data = tempdata;
                
            }

        }
        root.height = getMaxheight(root);
        // start balance factor checking
        int balanceFactor = getBalanceFactor(root);
        if (balanceFactor > 1) {
            if (balanceFactor == 2) {
                if (getBalanceFactor(root.right) >= 0)
                    root = leftRotation(root);
                else if (getBalanceFactor(root.right) < 0) {
                    root.right = rightRotation(root.right);
                    root = leftRotation(root);
                }
                root.height = getMaxheight(root);

                return root;
            }
        }
        if (balanceFactor < -1) {
            if (balanceFactor == -2) {
                if (getBalanceFactor(root.left) <= 0)
                    root = rightRotation(root);
                else if (getBalanceFactor(root.left) > 0) {
                    root.left = leftRotation(root.left);
                    root = rightRotation(root);
                }
                root.height = getMaxheight(root);

                return root;
            }
        }


        return root;
    }
}
