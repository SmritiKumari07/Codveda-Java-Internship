import java.util.Scanner;

public class BinarySearchTree {

    static class Node {
        int key;
        Node left, right;
        Node(int key) { this.key = key; }
    }

    private Node root;

    public void insert(int key) {
        root = insertRecord(root, key);
    }

    private Node insertRecord(Node node, int key) {
        if (node == null) return new Node(key);
        if (key < node.key) node.left = insertRecord(node.left, key);
        else if (key > node.key) node.right = insertRecord(node.right, key);
        return node;
    }

    public boolean search(int key) { return searchRecord(root, key); }
    private boolean searchRecord(Node node, int key) {
        if (node == null) return false;
        if (key == node.key) return true;
        return key < node.key ? searchRecord(node.left, key) : searchRecord(node.right, key);
    }

    public void delete(int key) { root = deleteRecord(root, key); }
    private Node deleteRecord(Node node, int key) {
        if (node == null) return null;
        if (key < node.key) node.left = deleteRecord(node.left, key);
        else if (key > node.key) node.right = deleteRecord(node.right, key);
        else {
            if (node.left == null) return node.right;
            else if (node.right == null) return node.left;
            node.key = minValue(node.right);
            node.right = deleteRecord(node.right, node.key);
        }
        return node;
    }

    private int minValue(Node node) {
        int minv = node.key;
        while (node.left != null) {
            minv = node.left.key;
            node = node.left;
        }
        return minv;
    }

    public void inorder() { inorderRecord(root); System.out.println(); }
    private void inorderRecord(Node node) {
        if (node != null) {
            inorderRecord(node.left);
            System.out.print(node.key + " ");
            inorderRecord(node.right);
        }
    }

    public void preorder() { preorderRecord(root); System.out.println(); }
    private void preorderRecord(Node node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preorderRecord(node.left);
            preorderRecord(node.right);
        }
    }
    public void postorder() { postorderRecord(root); System.out.println(); }
    private void postorderRecord(Node node) {
        if (node != null) {
            postorderRecord(node.left);
            postorderRecord(node.right);
            System.out.print(node.key + " ");
        }
    }
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        Scanner sc = new Scanner(System.in);
        System.out.println("= Binary Search Tree Demo =");
        while (true) {
            System.out.println("\n1) Insert 2) Search 3) Delete 4) Inorder 5) Preorder 6) Postorder 7) Exit");
            System.out.print("Choose: ");
            int c;
            try {
                c = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }
            switch (c) {
                case 1 -> {
                    System.out.print("Enter value to insert: ");
                    int v = Integer.parseInt(sc.nextLine().trim());
                    bst.insert(v);
                    System.out.println("Inserted " + v);
                }
                case 2 -> {
                    System.out.print("Enter value to search: ");
                    int v = Integer.parseInt(sc.nextLine().trim());
                    System.out.println("Found: " + bst.search(v));
                }
                case 3 -> {
                    System.out.print("Enter value to delete: ");
                    int v = Integer.parseInt(sc.nextLine().trim());
                    bst.delete(v);
                    System.out.println("Deleted " + v + " (if existed).");
                }
                case 4 -> { System.out.print("Inorder: "); bst.inorder(); }
                case 5 -> { System.out.print("Preorder: "); bst.preorder(); }
                case 6 -> { System.out.print("Postorder: "); bst.postorder(); }
                case 7 -> { System.out.println("Exiting."); sc.close(); return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
