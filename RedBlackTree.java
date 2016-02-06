package tree;

/**
 * implements a basic red black tree data structure
 * 
 * @author FLAG
 * @param <Type> generic type of data stored in tree node
 */


public class RedBlackTree<Type extends Comparable<Type>> implements Comparable<Type> {
	public static final boolean RED = true;
	public static final boolean BLACK = false;
	
	private Node<Type> root;
	private int size;
	
	
	private class Node<Type2> {
		private Type2 val;
		private Node<Type2> left, right;
		private boolean color;
		
		public Node(Type2 val, boolean color) {
			this.val = val;
			this.color = color;
		}
	}

	public RedBlackTree() {
		size = 0;
		root = null;
	}

	@Override
	public int compareTo(Type val) {
		return root.val.compareTo(val);
	}	
	
	
	public Node<Type> get(Type target) {
		return get(root, target);
	}
	private Node<Type> get(Node<Type> root, Type target) {
		while(root!=null) {
			if(root.val==target) return root;
			else if(root.val.compareTo(target)>0) root = root.left;
			else root = root.right;
		}
		return null;
	}
	
	public boolean contains(Type target) {
		return get(target)!=null;
	}
	
	public void put(Type newValue) {
		root = put(root, newValue);
		root.color = BLACK;
	}
	
	private Node<Type> put(Node<Type> root, Type newValue) {
		if(root==null) {
			size++;
			return new Node<Type>(newValue, RED);
		}
		
		if(root.val.compareTo(newValue)>0) root.left = put(root.left, newValue);
		else if(root.val.compareTo(newValue)<0) root.right = put(root.right, newValue);
		else return root;

		
		if(isRed(root.right) && !isRed(root.left)) root = rotateLeft(root);
		if(isRed(root.left) && isRed(root.left.left)) root = rotateRight(root);
		if(isRed(root.left) && isRed(root.right)) flipColor(root);

		return root;
	}
	
	
	public boolean isRed(Node<Type> node) {
		return (node==null) ? false : node.color==RED;
	}
	
	public Node<Type> rotateLeft(Node<Type> node) {
		assert(node!=null);
		Node<Type> x = node.right;
		assert(isRed(x));
		node.right = x.left;
		x.left = node;
		x.color = node.color;
		node.color = RED;
		return x;
	}
	public Node<Type> rotateRight(Node<Type> node) {
		assert(node!=null);
		Node<Type> x = node.left;
		assert(isRed(x));
		assert(isRed(x.left));
		node.left = x.right;
		x.right = node;
		x.color = node.color;
		node.color = RED;
		return x;
	}
	public void flipColor(Node<Type> node) {
		node.color = !node.color;
		node.left.color = !node.left.color;
		node.right.color = !node.right.color;
	}

	
	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size==0;
	}
	public int height() {
		return height(root);
	}
	private int height(Node<Type> root) {
		if(root==null) return 0;
		int height = Math.max(height(root.left), height(root.right)) + 1;
		if(isRed(root)) height--;
		return height;
	}
	
	
	public void inorder() {
		inorder(root);
	}
	private void inorder(Node<Type> root) {
		if(root==null) return;
		inorder(root.left);
		System.out.print(root.val + " ");
		inorder(root.right);
	}
	
	public void preorder() {
		preorder(root);
	}
	private void preorder(Node<Type> root) {
		if(root==null) return;
		System.out.print(root.val + " ");
		preorder(root.left);
		preorder(root.right);
	}
	
	public void postorder() {
		postorder(root);
	}
	private void postorder(Node<Type> root) {
		if(root==null) return;
		postorder(root.left);
		postorder(root.right);
		System.out.print(root.val + " ");
	}

	// reference: http://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram#answer-27153988
	public String toString() {
	    return toString(root, new StringBuffer(), true, new StringBuffer()).toString();
	}
	public StringBuffer toString(Node<Type> root, StringBuffer prefix, boolean isTail, StringBuffer sb) {
	    if(root.right!=null) {
	        toString(root.right, new StringBuffer().append(prefix).append(isTail ? "│   " : "    "), false, sb);
	    }
	    String str = root.color ? "*" + root.val + "*" : " " + root.val + " ";
	    sb.append(prefix).append(isTail ? "└──" : "┌──").append(str).append("\n");
	    if(root.left!=null) {
	        toString(root.left, new StringBuffer().append(prefix).append(isTail ? "    " : "│   "), true, sb);
	    }
	    return sb;
	}
	
	public static void main(String[] args) {
		RedBlackTree<Character> tree = new RedBlackTree<Character>();
		
		tree.put('s');
		
		System.out.println("----------------------");
		System.out.println("\tput s\t");
		System.out.println("----------------------");
		System.out.println("\ntree content (** indicates a red node): ");
		System.out.println(tree.toString() + "\n");
		
		tree.put('e');
		
		System.out.println("----------------------");
		System.out.println("\tput e\t");
		System.out.println("----------------------");
		System.out.println("\ntree content (** indicates a red node): ");
		System.out.println(tree.toString() + "\n");
		
		tree.put('a');
		
		System.out.println("----------------------");
		System.out.println("\tput a\t");
		System.out.println("----------------------");
		System.out.println("\ntree content (** indicates a red node): ");
		System.out.println(tree.toString() + "\n");
		
		tree.put('r');
		
		System.out.println("----------------------");
		System.out.println("\tput r\t");
		System.out.println("----------------------");
		System.out.println("\ntree content (** indicates a red node): ");
		System.out.println(tree.toString() + "\n");
		
		tree.put('c');
		
		System.out.println("----------------------");
		System.out.println("\tput c\t");
		System.out.println("----------------------");
		System.out.println("\ntree content (** indicates a red node): ");
		System.out.println(tree.toString() + "\n");
		
		tree.put('h');
		
		System.out.println("----------------------");
		System.out.println("\tput h\t");
		System.out.println("----------------------");
		System.out.println("\ntree content (** indicates a red node): ");
		System.out.println(tree.toString() + "\n");
		
		tree.put('x');
		
		System.out.println("----------------------");
		System.out.println("\tput x\t");
		System.out.println("----------------------");
		System.out.println("\ntree content (** indicates a red node): ");
		System.out.println(tree.toString() + "\n");
		
		tree.put('m');
		
		System.out.println("----------------------");
		System.out.println("\tput m\t");
		System.out.println("----------------------");
		System.out.println("\ntree content (** indicates a red node): ");
		System.out.println(tree.toString() + "\n");
		
		tree.put('p');
		
		System.out.println("----------------------");
		System.out.println("\tput p\t");
		System.out.println("----------------------");
		System.out.println("\ntree content (** indicates a red node): ");
		System.out.println(tree.toString() + "\n");
		
		tree.put('l');
		
		System.out.println("----------------------");
		System.out.println("\tput l\t");
		System.out.println("----------------------");
		System.out.println("\ntree content (** indicates a red node): ");
		System.out.println(tree.toString() + "\n");
		
		System.out.println("\ntree content (** indicates a red node): ");
		System.out.println(tree.toString() + "\n");
		
		System.out.println("tree size: " + tree.size());
		System.out.println("tree height: " + tree.height());
		System.out.println("contains 's'? " + tree.contains('s'));
		System.out.println("contains 't'? " + tree.contains('t'));
		System.out.println("contains 'b'? " + tree.contains('b'));
		
		System.out.println();
		
		tree.put('t');
		
		System.out.println("----------------------");
		System.out.println("\tput t\t");
		System.out.println("----------------------");
		
		System.out.println("\ntree content (** indicates a red node): ");
		System.out.println(tree.toString() + "\n");
		
		tree.put('y');
		
		System.out.println("----------------------");
		System.out.println("\tput y\t");
		System.out.println("----------------------");
		
		System.out.println("\ntree content (** indicates a red node): ");
		System.out.println(tree.toString() + "\n");
		
		tree.put('w');
		
		System.out.println("----------------------");
		System.out.println("\tput w\t");
		System.out.println("----------------------");
		
		System.out.println("\ntree content (** indicates a red node): ");
		System.out.println(tree.toString() + "\n");

		System.out.println("tree size: " + tree.size());
		System.out.println("tree height: " + tree.height());
		System.out.println("contains 's'? " + tree.contains('s'));
		System.out.println("contains 't'? " + tree.contains('t'));
		System.out.println("contains 'b'? " + tree.contains('b'));
		
		System.out.println();
		
		System.out.print("inorder traversal\t: ");
		tree.inorder();
		System.out.print("\npreorder traversal\t: ");
		tree.preorder();
		System.out.print("\npostorder traversal\t: ");
		tree.postorder();
		System.out.println();
		
	}
}
