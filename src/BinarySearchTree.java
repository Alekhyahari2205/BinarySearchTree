/** @author 
 *  Binary search tree (starter code)
 **/


import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;
public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    Entry<T> root;
    Entry<T> t;
    int size;
    Stack<Entry <T>> stack = new Stack<Entry <T>>();
    static class Entry<T> {
        T element;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }


        public Entry(T x) {
            element = x;
            right = null;
            left = null;

        }
    }

    public BinarySearchTree() {
        root = null;
        size = 0;
    }
    Entry<T> find(T x) {
        stack.push(null);
        return find(root,x);
    }
    private Entry<T> find(Entry<T> t, T x){
        if(t.element.equals(x) || t == null ){
            return t;
        }
        while(true){
            if (x.compareTo(t.element) < 0 ) {
                if (t.left == null)
                    break;
                else {
                    stack.push(t);
                    t = t.left;
                }
            }
            else if (x.compareTo(t.element) > 0){
                if(t.right == null)
                    break;
                else {
                    stack.push(t);
                    t = t.right;
                }
            }
            else{
                break;
            }

        }
        return t;
    }

    /** TO DO: Is x contained in tree?
     */
    public boolean contains(T x) {
        t = find(x);
        if(x.compareTo(t.element) != 0 || t == null)
            return false;
        else
            return true;
    }

    /** TO DO: Is there an element that is equal to x in the tree?
     *  Element in tree that is equal to x is returned, null otherwise.
     */
    public T get(T x) {
        t = find(x);
        if(x.compareTo(t.element) != 0 )
            return null;
        else if (t == null){
            return null;
        }
        else
            return x;
    }

    /** TO DO: Add x to tree. 
     *  If tree contains a node with same key, replace element by x.
     *  Returns true if x is a new element added to tree.
     */
    public boolean add(T x) {
        if(size == 0){
            root = new Entry<T>(x);
            size = 1;
        }
        else{
            t = find(x);
            if(t.element.compareTo(x) == 0){
                t.element = x;
                return false;
            }
            else if(x.compareTo(t.element)>0) t.right = new Entry<>(x);
            else t.left = new Entry<>(x);

            size ++;

        }

	    return true;
    }

    /** TO DO: Remove x from tree. 
     *  Return x if found, otherwise return null
     */
    public T remove(T x) {
        if(root == null)
            return null;
        t = find(x);
        if(t.element.compareTo(x) !=0)
            return null;
        T result = t.element;
        if(t.right== null || t.left == null){
            bypass(t);
        }
        else{
            stack.push(t);
            Entry<T> RightMin = find(t.right, x);
            t.element = RightMin.element;
            bypass(RightMin);

        }
        size --;
        return result;
    }

    public T min() {
        if (size == 0) return null;

        t = root;
        while (t.left != null) t = t.left;

        return t.element;
    }

    public T max() {
        if (size == 0) return null;
        t = root;
        while (t.right != null) t = t.right;

        return t.element;

    }
    private void bypass(Entry<T> t) {

        Entry<T> parentNode = stack.peek(); // parent of t

        Entry<T> child = (t.left == null)? t.right: t.left; // Precondition 1

        // when t is root of the tree
        if (parentNode == null) root = child; // bypassing root

        else {
            // When the king(t) is dead, long live the king! ;)
            if (parentNode.left == t) parentNode.left = child;
            else parentNode.right = child; // t was rightChild of parent
        }
    }

    // TODO: Create an array with the elements using in-order traversal of tree
    public Comparable[] toArray() {
	Comparable[] arr = new Comparable[size];
        if (root == null) return null;
        Stack<Entry<T>> stack = new Stack<Entry<T>>();
        t = root;
        int i=0;
        while (stack.size() > 0 || t != null) {

            while (t != null) {
                stack.push(t);
                t = t.left;
            }
            t = stack.pop();

            arr[i] = t.element;
            i++;

            t = t.right;
        }
	return arr;
    }


// Start of Optional problem 2

    /** Optional problem 2: Iterate elements in sorted order of keys
	Solve this problem without creating an array using in-order traversal (toArray()).
     */
    public Iterator<T> iterator() {
	return null;
    }

    // Optional problem 2.  Find largest key that is no bigger than x.  Return null if there is no such key.
    public T floor(T x) {
        return null;
    }

    // Optional problem 2.  Find smallest key that is no smaller than x.  Return null if there is no such key.
    public T ceiling(T x) {
        return null;
    }

    // Optional problem 2.  Find predecessor of x.  If x is not in the tree, return floor(x).  Return null if there is no such key.
    public T predecessor(T x) {
        return null;
    }

    // Optional problem 2.  Find successor of x.  If x is not in the tree, return ceiling(x).  Return null if there is no such key.
    public T successor(T x) {
        return null;
    }
    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if(node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }

// End of Optional problem 2
    public static void main(String[] args) {
	BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            int x = in.nextInt();
            if(x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if(x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for(int i=0; i<t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                return;
            }           
        }
    }




}
/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10 

*/
