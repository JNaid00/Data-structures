public class Node<T extends Comparable<T>> {
   public T data;
   public int height;
   public Node<T> left = null;
   public Node<T> right = null;

    /** Used to indicate whether the right / left pointer is a normal
    pointer or a pointer to inorder successor.
     **/
    boolean rightThread = false;
    boolean leftThread = false;

    public Node(T item) {
        data = item;
        left = right = null;
    }

    
}
