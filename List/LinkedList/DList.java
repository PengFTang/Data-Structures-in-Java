package list;
public class DList {
	private int size;
	public DListNode head;
	
	// construct an empty DList
	public DList() {
		size = 0;
		head = new DListNode(1);
		head.next = head;
		head.prev = head;
	}
	// construct a DList with one item 
	public DList(int i) {
		head = new DListNode(1);
		DListNode first = new DListNode(i);
		head.next = first;
		head.prev = first;
		first.next = head;
		first.prev = head;
		size = 1;
	}
	// construct a DList with one item 
	public DList(DListNode node) {
		head = new DListNode(1);
		head.next = node;
		head.prev = node;
		node.next = head;
		node.prev = head;
		size = 1;
	}

	// get size
	public int size() {
		return size;
	}
	public int length() {
		return size;
	}
	
	// check whether empty
	public boolean isEmpty() {
		return size==0;
	}

	// get first node
	public DListNode first() {
		if(size==0) return null; // return null if DList is empty
		return head.next;
	}
	// get last node
	public DListNode last() {
		if(size==0) return null; // return null if DList is empty
		return head.prev;
	}
	
	// insert a new node after the head
	public void insertAfterHead(DListNode node) {
		head.next.prev = node; // set the head's next node's prev pointer to input node
		node.next = head.next; // set new node's next pointer to head's next node
		head.next = node; // set head's next pointer to new node
		node.prev = head; // set node's prev pointer to head
		size++;
	}
	// insert a new node before the head
	public void insertBeforeHead(DListNode node) {
		head.prev.next = node; // set the head's prev node's next pointer to input node
		node.prev = head.prev; // set new node's prev pointer to head's prev node
		head.prev = node; // set head's prev pointer to new node
		node.next = head; // set node's next pointer to head
		size++;
	}
	
	// insert item right after node if node is not null, otherwise do nothing
	public void insertAfter(int i, DListNode node) {
	    if(node==null) return;
	    if(node.next==null) return; // if node is not attached to a DList, do nothing?
	    DListNode newNode = new DListNode(i);
	    node.next.prev = newNode;
	    newNode.next = node.next;
	    node.next = newNode;
	    newNode.prev = node;
	    size++;
	}
	// insert item right before node if node is not null, otherwise do nothing
	public void insertBefore(int i, DListNode node) {
	    if(node==null) return;
	    if(node.prev==null) return; // if node is not attached to a DList, do nothing?
	    DListNode newNode = new DListNode(i);
	    node.prev.next = newNode;
	    newNode.prev = node.prev;
	    newNode.next = node;
	    node.prev = newNode;
	    size++;
	}
	
	// remove a specific node from the List. If node is null, do nothing. If node's next pointer is null, do nothing as well.
	public void remove(DListNode node) {
		if(node==null) return;
		if(node.next==null) return;
		
		node.prev.next = node.next;
		node.next.prev = node.prev;
		size--;
	} 
	
	// convert DList contents to string for display
	public String toString() {
		String str = "head<==>";
		DListNode current = head.next;
		int L = size;
		while(L>=1) {
			str += current.val + "<==>";
			current = current.next;
			L--;
		}
		str += "head";
		return str;
	}
}
