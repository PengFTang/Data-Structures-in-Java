package list;

public class DriverDList {
	public static void main(String[] args) {
		
		DList head = new DList(); // empty list: head<==>head
		head.insertAfterHead(new DListNode(3)); // current list: head<==3<==>head
		head.insertBeforeHead(new DListNode(200)); // current list: head<==3<==>200<==>head
		head.insertAfterHead(new DListNode(5)); // current list: head<==5<==>3<==>200<==>head
		head.insertBeforeHead(new DListNode(100)); // current list: head<==5<==>3<==>200<==>100<==>head
		System.out.println("L1: " + head.toString()); // expected output: head<==>5<==>3<==>200<==>100<==>head
		
		DListNode node1 = new DListNode(222);
		head.insertAfterHead(node1); // current list: head<==222<==>5<==>3<==>200<==>100<==>head
		System.out.println("L2: " + head.toString()); // expected output: head<==222<==>5<==>3<==>200<==>100<==>head
		DListNode node2 = new DListNode(345);
		head.insertBeforeHead(node2); // current list: head<==222<==>5<==>3<==>200<==>100<==>345<==>head
		System.out.println("L3: " + head.toString()); // expected output: head<==222<==>5<==>3<==>200<==>100<==>345<==>head

		head.insertAfter(10, node1); // current list: head<==222<==>10<==>5<==>3<==>200<==>100<==>345<==>head
		head.insertBefore(66, node2); // current list: head<==222<==>10<==>5<==>3<==>200<==>100<==>66<==>345<==>head
		System.out.println("L4: " + head.toString()); // expected output: head<==222<==>10<==>5<==>3<==>200<==>100<==>66<==>345<==>head
		
		head.remove(new DListNode()); // will do nothing
		System.out.println("L5: " + head.toString()); // expected output: head<==222<==>10<==>5<==>3<==>200<==>100<==>66<==>345<==>head
		head.remove(node1); // current list: head<==10<==>5<==>3<==>200<==>100<==>66<==>345<==>head
		System.out.println("L6: " + head.toString()); // expected output: head<==10<==>5<==>3<==>200<==>100<==>66<==>345<==>head
		
		System.out.println("List size:" + head.size()); // current size: 7
		System.out.println("First node value: " + head.first().val); // first node value: 10
		System.out.println("Last node value: " + head.last().val); // last node value: 345
	}
}
