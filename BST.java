import java.util.NoSuchElementException;


public class BST {
	private int size = 0;
	private Node head;
	
	protected class Node {
		int val;
		Node left;
		Node right;
		Node parent;
		
		public Node(int i) {
			val = i;
		}
	}
	
	public BST() {
		
	}	
	public BST(int i) {
		head = new Node(i);
		size = 1;
		//depth = 0;
		//levelNodes[0] = 1;
	}
	
	public int size() {
		return size;
	}
	public int length() {
		return size;
	}
	
	// get depth
	public int depth() {
		return getSubDepth(head);
	}
	// recursive method to get depth of any subtree
	private int getSubDepth(Node node) {
		if(node == null) return -1;
		//System.out.println("Current node: " + subBST.val);
		return 1 + (getSubDepth(node.left) >= getSubDepth(node.right) 
				? getSubDepth(node.left) 
				: getSubDepth(node.right));
	}
	
	public void add(int newItem) {
		if(size == 0) {
			head = new Node(newItem);
		}
		else {
			//int level = 0; // the current level
			double d; // to be used to randomly choose left or right subtree
			Node current = head;
			Node newNode = new Node(newItem);
			while(current != null) {
				//level++; // level increased by one
				// go to left subtree if new item is less than current node key
				if(current.val > newItem) {
					// add the node to its current left if current lefft is empty
					if(current.left == null) {
						current.left = newNode;
						newNode.parent = current;
						break;
					}
					// go to its left subtree
					else
						current = current.left;
				}
				// go to right subtree if new item is greater than current node key
				else if(current.val < newItem) {
					if(current.right == null) {
						current.right = newNode;
						newNode.parent = current;
						break;
					}
					else
						current = current.right;
				}
				// when new item is the same as the current node key
				else {
					// add to its left subtree of current left subtree is empty 
					if(current.left == null) {
						current.left = newNode;
						newNode.parent = current;
						break;
					}
					// add to its right subtree of current right subtree is empty 
					else if(current.right == null) {
						current.right = newNode;
						newNode.parent = current;
						break;
					}
					// when neither left or right subtree is empty, randomly choose left or right subtree
					else {
						d = Math.random();
						//System.out.println("current node's key = new item; randomly choose " + (d>0.5 ? "left":"right") );
						current = (d>0.5) ? current.left:current.right;
					}
				}
			}
			
			//System.out.println(newItem + " added at level " + level);
		}
		
		size++;
	}
	
	public void remove(int newItem) {
		if(size == 0) throw new NoSuchElementException("empty tree!");
		else if(size==1) {
			if(head.val == newItem) {
				head = null;
				size = 0;
			}
			else
				return;
		}
		Node current = head;
		while(current != null) {
			if(current.val < newItem) {
				current = current.right;
			}
			else if(current.val > newItem)
				current = current.left;
			else {
				size--; // reduce size;
				// current node has no child, just remove it
				if(current.left==null && current.right==null) {
					if(current.parent.val <= current.val)
						current.parent.right = null;
					else
						current.parent.left = null;
					break;
				}
				// current node has only a right node, move right node up to current
				else if(current.left == null) {
					current.parent.left = current.right; // = current.right;
					break;
				}
				// current node has only a left node, move left node up to current
				else if(current.right == null) {
					current.parent.right = current.left;
					break;
				}
				// current node has two children, find the smallest key greater than current node, move that key to current node
				else {
					Node node = current.right;
					Node p = current;
					while(node != null) {
						p = node;
						node = node.left;
					}
					int replace = p.val;
					p.parent.left = null;
					current.val = replace;
				}
			}
		}
	}
	
	public int max() {
		if(size == 0) throw new NoSuchElementException("Empty tree!");
		int max = head.val;
		Node current = head;
		while(current.right != null) {
			current = current.right;
			max = current.val;
		}
		return max;
	}
	public int min() {
		if(size == 0) throw new NoSuchElementException("Empty tree!");
		int min = head.val;
		Node current = head;
		while(current.left != null) {
			current = current.left;
			min = current.val;
		}
		return min;
	}
	
	public int search(int target) {
		if(size == 0) throw new NoSuchElementException("Empty tree!");
		
		int level = 0;
		Node current = head;
		while(current != null) {
			if(current.val == target)
				return level;
			else if(current.val < target)
				current = current.right;
			else
				current = current.left;
			level++;
		}
		return -1;
	}
	
	// find the node whose key is the minimal of those greater than target
	public void closest(int target) {
		if(size == 0) throw new NoSuchElementException("Empty tree!");
		
		int[] arr = new int[2];
		boolean existNextLarge, existNextSmall;
		existNextLarge = existNextSmall = false;
		
		Node current = head;
		while(current != null) {
			if(current.val == target) {
				System.out.println(target + " is in the tree.");
			}
			else if(current.val < target) {
				arr[1] = (!existNextSmall) ? current.val : (arr[0]>current.val ? current.val : arr[1]);
				current = current.right;
				existNextSmall = true;
			}
			else {
				arr[0] = (!existNextLarge) ? current.val : (arr[0]>current.val ? current.val : arr[0]);
				current = current.left;
				existNextLarge = true;
			}
		}
		if(existNextLarge)
			System.out.print("Next greater key: " + arr[0] + "; ");
		else
			System.out.print("Next greater key: na; ");
		if(existNextSmall)
			System.out.print("Next smaller key: " + arr[1] + ".");
		else
			System.out.print("Next smaller key: na.");
	}
	
	/*
	public String toString() {
		String str = "";
		BST current = this;
		while(current != null) {
			str += nodeToString(current);
			BST currentLeft = current.left;
			BST currentRight = current.right;
			while(currentLeft != null) 
				str += nodeToString(currentLeft);
			while(currentRight != null) 
				str += nodeToString(currentRight);
		}
		return str;
	}
	private String getString(BST subBST) {
		while(subBST != null) {
			str += nodeToString(subBST);
		}
	}
	private String nodeToString(BST subBST) {
		if(subBST == null) return "";

		return subBST.val + ":" + (subBST.left!=null?subBST.left.val:" ") + "\t" + (subBST.right!=null?subBST.right.val:" ") + "\n";
		//return subBST.val + ":" + nodeToString(subBST.left) + "\t" + nodeToString(subBST.right);
	}
	*/
	public static void main(String[] args) {
		
		BST head = new BST();
		head.add(15);
		head.add(30);
		head.add(0);
		head.add(7);
		head.add(40);
		head.add(25);
		head.add(4);
		head.add(10);
		head.add(-5);
		head.add(7);
		head.add(7);
		head.add(7);
		head.add(7);
		head.add(7);
		//head.add(35);
		//head.add(30);
		System.out.println("Tree size: " + head.size());
		System.out.println("Max value: " + head.max());
		System.out.println("Min value: " + head.min());
		System.out.println("Tree depth:  " + head.depth());
		head.closest(8);
		//System.out.println("Tree:\n" + head.toString());
		
		/*
		System.out.println("search 10:" + head.search(10));
		System.out.println("search 23: " + head.search(23));
		head.remove(23);
		head.remove(7);
		head.remove(25);
		head.remove(40);
		head.remove(15);
		head.remove(15);
		head.remove(7);
		head.remove(40);
		head.remove(25);
		head.remove(30);
		head.remove(0);
		head.remove(23);
		head.remove(23);
		//head.remove(15);
		//head.remove(23);
		System.out.println("search for 15: " + head.search(15));
		System.out.println("Tree depth: (method): " + head.depth());
		System.out.println("size: " + head.size());
		System.out.println("Max value: " + head.max());
		System.out.println("Min value: " + head.min());
		*/
		
		/*
		//Test running time of getDepth()
		BST head = new BST(10);
		int N;
		System.out.println("N\tElapsed Time");
		for(int j=1; j<20; j++) {
			N = (int)Math.pow(2, j);
			for(int i=0; i<N; i++) {
				head.add(10);
			}
			long t1 = System.nanoTime();
			head.depth();
			long t2 = System.nanoTime();
			System.out.println(N + "\t" + (t2-t1)/1000000000.);
		}
		// Result shows that getDepth is O(n) and is roughly 3n
		*/
	}
}
