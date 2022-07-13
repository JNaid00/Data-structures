public class ThreadedAvlTree<T extends Comparable<T>> {
    public Node<T> root;

    public ThreadedAvlTree() {
        this.root = null;
    }

    int getHeight(Node<T> N) {
        if (N == null)
            return 0;

        return N.height;
    }

    static Node getLeftMost(Node node) {
        while (node != null && node.left != null)
            node = node.left;
        return node;
    }

    // Inorder traversal of a threaded avl tree
    void print(Node<T> node) {
        if (node == null)
            return;

        Node<T> cur = getLeftMost(node);

        while (cur != null) {
            System.out.print(" " + cur.data + " ");

            if (cur.rightThread == true)
                cur = cur.right;
            else
                cur = getLeftMost(cur.right);
        }
    }

    /* Do not edit the code above */
    int getBalanceFactor(Node<T> nodeptr) {
        if (nodeptr == null) {
            return 0;
        }
        int left = 0;
        int right = 0;
        if (nodeptr.left != null && nodeptr.leftThread == false) {
            left = getHeight(nodeptr.left) + 1;
        }

        if (nodeptr.right != null && nodeptr.rightThread == false) {
            right = getHeight(nodeptr.right) + 1;
        }

        return right - left;
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

    public int getMaxheight(Node<T> node) {
        if (node == null) {
            return 0;
        }
        if (node.right == null && node.left == null) {
            return 0;
        } else if (node.rightThread == true && node.leftThread == false) {
            if (node.left == null)
                return 0;
            return getHeight(node.left) + 1;
        } else if (node.rightThread == false && node.leftThread == true) {
            if (node.right == null)
                return 0;
            return getHeight(node.right) + 1;
        } else if (node.rightThread == true && node.leftThread == true) {
            return 0;
        } else {
            if (getHeight(node.right) >= getHeight(node.left)) {
                return getHeight(node.right) + 1;
            } else {
                return getHeight(node.left) + 1;
            }
        }
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

    public int numberNodes(Node<T> node) {
        int ans = 0;
        if (node == null) {
            return 0;
        }
        if (node.left != null) {
            ans += 1 + numberNodes(node.left);
        }

        if (node.right != null) {
            ans += 1 + numberNodes(node.right);
        }

        return ans;

    }

    void createQueue(Node<T> node, int[] index, Object[] queueTree) {
        if (node == null) {
            return;
        }

        if (node.left != null) {
            createQueue(node.left, index, queueTree);
        }

        queueTree[index[0]--] = node;

        if (node.right != null) {
            createQueue(node.right, index, queueTree);
        }

    }

    void convertAVLtoThreaded(Node<T> node) {
        this.root = node;
        Object[] queueTree = new Object[this.numberNodes(node) + 1];
        int[] index = { queueTree.length - 1 };
        createQueue(node, index, queueTree);

        // for (int i = queueTree.length - 1; i >= 0; i--) {
        // Node<T> temp = (Node<T>) queueTree[i];
        // System.out.print(temp.data + " ");
        // }

        int indexTree = queueTree.length - 1;
        while (indexTree >= 0) {
            Node<T> temp = (Node<T>) queueTree[indexTree];
            if (indexTree == 0) {
                temp.right = null;
                temp.rightThread = false;
                indexTree--;
            } else if (temp.right == null) {
                temp.right = (Node<T>) queueTree[--indexTree];
                temp.rightThread = true;
            } else {
                indexTree--;
            }
        }
    }

    private void removeRightThread(Node<T> node) {
        if (node == null) {
            return;
        }
        removeRightThread(node.left);
        if (node.rightThread == true) {
            node.rightThread = false;
            node.right = null;
        }
        removeRightThread(node.right);
    }

    /**
     * Insert the given data into the tree.
     * Duplicate data is not allowed. just return the node.
     */

    // private void countRightThreads(Node<T> node) {
    //     if (node == null) {
    //         return;
    //     }
    //     countRightThreads(node.left);
    //     if (node.rightThread == true) {
    //         System.out.print(node.data + " ");
    //     } else
    //     countRightThreads(node.right);

    // }

    Node<T> insert(Node<T> node, T data) {
        // System.out.println("Nodes with right thread");
        // countRightThreads(node);
        removeRightThread(node);

        node = insertIntoTree(node, data);
        convertAVLtoThreaded(node);
        return node;
    }

    private Node<T> insertIntoTree(Node<T> node, T data) {
        if (node == null) {
            Node<T> newNode = new Node<T>(data);
            return newNode;
        }

        if (data.compareTo(node.data) > 0)
            node.right = insertIntoTree(node.right, data);
        else if (data.compareTo(node.data) < 0)
            node.left = insertIntoTree(node.left, data);
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
                node = rightRotation(node);
            }
            node.height = getMaxheight(node);
        } else if (balance > 1) {
            if (data.compareTo(node.right.data) < 0) {
                node.right = rightRotation(node.right);
                node = leftRotation(node);

            } else if (data.compareTo(node.right.data) > 0) {
                node = leftRotation(node);
            }
            node.height = getMaxheight(node);
        }

        return node;
    }
    /**
     * Delete the given element \texttt{data} from the tree. Re-balance the tree
     * after deletion.
     * If the data is not in the tree, return the given node / root.
     */
    Node<T> removeNode(Node<T> root, T data) {
        removeRightThread(root);
        root = deleleFromTree(root, data);
        convertAVLtoThreaded(root);
        return root;
    }

    private Node<T> deleleFromTree(Node<T> root, T data) {
        if (root == null) {
            return null;
        }
        // if (containsData(root, data) == false) {
        //     return root;
        // }
        //Node<T> parent = root;// Only applicable after node found, parent of root;
        if (root.data.compareTo(data) > 0) {
            root.left = deleleFromTree(root.left, data);
        } else if (root.data.compareTo(data) < 0) {
            root.right = deleleFromTree(root.right, data);
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
                root.left = deleleFromTree(root.left, righmostNode.data);
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
