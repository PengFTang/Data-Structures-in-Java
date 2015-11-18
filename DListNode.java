package list;
import java.util.NoSuchElementException;

public class DListNode {
	public int val;
	public DListNode next;
	public DListNode prev;

	public DListNode() {
		val = 0;
		next = null;
		prev = null;
	}
	public DListNode(int i) {
		val = i;
		next = null;
		prev = null;
	}
	public DListNode(int i, DListNode next, DListNode prev) {
		val = i;
		this.next = next;
		this.prev = prev;
	}

	// insert a node after a specific node
	public void insertAfter(DListNode node) {
		node.next = this.next;
		if(this.next!=null)
			this.next.prev = node;
		
		this.next = node;
		node.prev = this;
	}
	
	// remove a specified node
	public void removeNode() {
		if( this.next==null && this.prev!=null ) {
			this.prev.next = null;
		}
		else if( this.next!=null && this.prev==null ) {
			this.next.prev = null;
		}
		else if( this.next!=null && this.prev!=null ) {
			this.prev.next = this.next;
			this.next.prev = this.prev;
		}
		else {
			throw new NoSuchElementException("Null node!");
		}
	}
	
	public String toString() {
		String str = "null<==";
		DListNode head = this;
		while(head != null) {
			str += head.val + "<==>";
			head = head.next;
		}
		str = str.substring(0,str.length()-4) + "==>null";
		
		return str;
	}
	
	public static void main(String[] args) {
		DListNode head = new DListNode();
		DListNode current = head;
		for(int i=1; i<=10; i++) {
			DListNode newNode = new DListNode(i*i);
			current.next = newNode;
			newNode.prev = current;
			current = newNode;
		}
		System.out.println(head.toString());
	}
}
