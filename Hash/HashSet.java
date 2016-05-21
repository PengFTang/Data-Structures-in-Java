package hash;

import java.util.NoSuchElementException;
import java.util.Iterator;

public class HashSet<T> {
	
	private int CAPACITY;
	private float LF;
	private int MAX_SIZE;
	private List<T>[] arr;
	private int size = 0; 
	
	@SuppressWarnings("unchecked")
	public HashSet() {
		CAPACITY = 16;
		LF = 0.75f;
		MAX_SIZE = (int)(LF*CAPACITY);
		arr = new List[CAPACITY];
		for(int i=0; i<CAPACITY; i++) {
			arr[i] = new List<>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public HashSet(int initialCapacity) {
		assert(initialCapacity > 0);
		
		CAPACITY = getNearestPowerTwo(initialCapacity);
		MAX_SIZE = (int)(LF*CAPACITY);
		
		arr = new List[CAPACITY];
		for(int i=0; i<CAPACITY; i++) {
			arr[i] = new List<>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public HashSet(int initialCapacity, int loadFactor) {
		assert(initialCapacity > 0);
		assert(loadFactor > 0 && loadFactor <= 1);

		CAPACITY = getNearestPowerTwo(initialCapacity);
		LF = loadFactor;
		MAX_SIZE = (int)(LF*CAPACITY);
		
		arr = new List[CAPACITY];
		for(int i=0; i<CAPACITY; i++) {
			arr[i] = new List<>();
		}
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	public boolean add(T item) {
		if(contains(item)) return false; // already existed
		if(size==MAX_SIZE) resize(); // resize
		addItem(item); // add item into array
		++size; // increase size
		return true;
	}
	private void addItem(T item) {
		int index = hashing(item); // rehash
		arr[index].add(item);
	}
	
	public boolean contains(T item) {
		int index = hashing(item);
		List<T> list = arr[index];
		Iterator<T> it = list.iterator();
		while(it.hasNext()) {
			if(it.next().equals(item)) return true;
		}
		return false;
	}
	
	public boolean remove(T item) {
		int index = hashing(item);
		List<T> list = arr[index];
		Iterator<T> it = list.iterator();
		while(it.hasNext()) {
			if(it.next().equals(item)) {
				it.remove();
				--size;
				return true;
			}
		}
		return false;
	}

	/*
	// needs to implement the iterator method
	public Iterator<T> iterator() {
		return new HashSetIterator<T>();
	}
	private class HashSetIterator<T> implements Iterator<T> {
		private List<T> current = null, next = null;
		private T currentVal = null;
		private int currentId = 0;
		
		public HashSetIterator() {
			while(currentId < CAPACITY) {
				if(!arr[currentId].isEmpty()) {
					next = (List<T>) arr[currentId];
					currentVal = current;
					break;
				}
				++currentId;
			}
		}
		
		public boolean hasNext() {
			return next != null;
		}
		
		public T next() {
			if(next==null) throw new NoSuchElementException();
			
			T item = next.get(0);
			if(it.hasNext()) item = it.next();
			else {
				while(++currentId < N) {
					if(!arr[currentId].isEmpty()) {
						current = arr[currentId];
						it = arr[currentId].iterator();
					}
				}
			}
			
			Iterator<T> it = arr[currentId].iterator();
			while(it.hasNext()) {
				current = 
			}
			return item;
		}
	}
	*/

	private int getNearestPowerTwo(int capacity) {
		int shifts = 0;
		while(capacity > 0) {
			capacity = capacity >> 1;
			++shifts;
		}
		return 1 << ++shifts;
	}
	
	@SuppressWarnings("unchecked")
	private boolean resize() {
		int temp = CAPACITY;
		CAPACITY = CAPACITY<<1;
		MAX_SIZE = (int)(LF*CAPACITY);
		List<T>[] arrCopy = arr;
		arr = new List[CAPACITY];
		for(int i=0; i<CAPACITY; i++) {
			arr[i] = new List<>();
		}
		for(int i=0; i<temp; i++) {
			Iterator<T> it = arrCopy[i].iterator();
			while(it.hasNext()) {
				addItem(it.next());
			}
		}
		return true;
	}
	
	// following hash() method in HashMap.class (Java JDK 1.8)
	public int hashing(T item) {
		int h;
        int hash = (item == null) ? 0 : (h = item.hashCode()) ^ (h >>> 16);
        return hash & (CAPACITY-1);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		for(int i=0; i<CAPACITY; i++) {
			sb.append(arr[i].toString() + (i==CAPACITY-1 ? "" : ", "));
		}
		return sb.toString();
	}
	


	private class List<T> {
		public Node<T> head, tail;
		public int size;
		public List() {
			head = tail = null;
			size = 0;
		}
		
		public boolean isEmpty() {
			return size==0;
		}
		public void add(T val) {
			if(head==null) {
				head = tail = new Node<>(val);
			}
			else {
				tail.next = new Node<>(val);
				tail = tail.next;
			}
			++size;
		}
		public void remove(T val) {
			Node<T> current = head, prev = new Node<>(val);
			prev.next = current;
			while(current != null) {
				if(val.equals(current.val)) {
					prev.next = current.next;
					--size;
					return;
				}
			}
			throw new NoSuchElementException();
		}
		
		public String toString() {
			Node<T> current = head;
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			while(current!=null) {
				sb.append(current.val + ",");
				current = current.next;
			}
			if(sb.length()>1) sb.deleteCharAt(sb.length()-1);
			sb.append("]");
			return sb.toString();
		}
		
		public Iterator<T> iterator() {
			return new ListIterator<>();
		}
		private class ListIterator<T> implements Iterator<T> {
			Node<T> next;
			public ListIterator() {
				next = (Node<T>) head;
			}
			public boolean hasNext() {
				return next != null;
			}
			public T next() {
				if(next==null) throw new NoSuchElementException();
				T val = next.val;
				next = next.next;
				return val;
			}
			// incorrect and needs to be corrected!
			public void remove() {
				if(next==null) throw new NoSuchElementException();
				T val = next.val;
				next = next.next;
			}
		}
		
		private class Node<T> {
			public T val;
			public Node<T> next;
			public Node(T val) {
				this.val = val;
				this.next = null;
			}
		}
	}
	
	public static void main(String[] args) {

		HashSet<String> list = new HashSet<>();

		System.out.println("add(abc)");
		list.add("abc");
		System.out.println("add(def)");
		list.add("def");
		
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());

		System.out.println();
		
		System.out.println("add(ghi)");
		list.add("ghi");
		
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());

		System.out.println();
		
		System.out.println("add(jkl)");
		list.add("jkl");
		
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());

		System.out.println();
		
		System.out.println("add(bcd)");
		list.add("bcd");
		
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());

		System.out.println();
		System.out.println("add(abcdefg)");
		list.add("abcdefg");

		System.out.println("items: " + list);
		System.out.println("size: " + list.size());
		System.out.println("contains(abcd): " + list.contains("abcd"));
		System.out.println("contains(a): " + list.contains("a"));
		System.out.println("contains(b): " + list.contains("b"));
		System.out.println("contains(): " + list.contains(""));


		System.out.println();
		
		System.out.println("remove(bcd): ");
		list.remove("bcd");
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());
		
		
		System.out.println();
		
		System.out.println("remove(bcd): ");
		list.remove("bcd");
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());

		System.out.println();

		
		System.out.println("add(abc)");
		list.add("abc");
		System.out.println("add(abc)");
		list.add("abc");
		System.out.println("add(abc)");
		list.add("abc");
		System.out.println("add(b)");
		list.add("b");
		
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());

		System.out.println();

		System.out.println("remove(a): ");
		list.remove("a");
		System.out.println("remove(abcdefg): ");
		list.remove("abcdefg");
		System.out.println("remove(def): ");
		list.remove("def");
		System.out.println("remove(ghi): ");
		list.remove("ghi");

		System.out.println("items: " + list);
		System.out.println("size: " + list.size());
		System.out.println();
		

		System.out.println("add(bbb)");
		list.add("bbb");
		System.out.println("add(bbbbb)");
		list.add("bbbbb");
		System.out.println("add(abbbc)");
		list.add("abbbc");
		System.out.println("add(bbbbbbb)");
		list.add("bbbbbbb");
		System.out.println("add(abbbbbc)");
		list.add("abbbbbc");
		System.out.println("add(bbbbbbbbb)");
		list.add("bbbbbbbbb");
		System.out.println("add(abbbbbbbc)");
		list.add("abbbbbbbc");
		System.out.println("add(bbbbbbbbbbb)");
		list.add("bbbbbbbbbbb");
		System.out.println("add(abbbbbbbbbc)");
		list.add("abbbbbbbbbc");
		System.out.println("add(bbbbbbbbbbbbb)");
		list.add("bbbbbbbbbbbbb");
		System.out.println("add(abbbbbbbbbbbc)");
		list.add("abbbbbbbbbbbc");
		System.out.println("add(bbbbbbbbbbbbbbb)");
		list.add("bbbbbbbbbbbbbbb");
		System.out.println("add(abbbbbbbbbbbbbc)");
		list.add("abbbbbbbbbbbbbc");
		System.out.println("add(bbbbbbbbbbbbbbbbb)");
		list.add("bbbbbbbbbbbbbbbbb");
		
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());

		
		
		
		
		/*
		// integer set
		HashSet<Integer> list = new HashSet<>();

		System.out.println("add(5)");
		list.add(5);
		System.out.println("add(4)");
		list.add(4);
		
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());

		System.out.println();
		
		System.out.println("add(3)");
		list.add(3);
		
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());

		System.out.println();
		
		System.out.println("add(2)");
		list.add(2);
		
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());

		System.out.println();
		
		System.out.println("add(1)");
		list.add(1);
		
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());

		System.out.println();
		System.out.println("add(0)");
		list.add(0);

		System.out.println("items: " + list);
		System.out.println("size: " + list.size());
		System.out.println("contains(0): " + list.contains(0));
		System.out.println("contains(1): " + list.contains(1));
		System.out.println("contains(2): " + list.contains(2));
		System.out.println("contains(6): " + list.contains(6));

		System.out.println();
		
		System.out.println("remove(3): " + list.remove(3));
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());

		System.out.println();
		
		System.out.println("remove(10): " + list.remove(10));
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());

		System.out.println();

		System.out.println("add(60)");
		list.add(60);
		System.out.println("add(60)");
		list.add(60);
		System.out.println("add(60)");
		list.add(60);
		System.out.println("add(70)");
		list.add(70);

		System.out.println("add(6)");
		list.add(6);
		System.out.println("add(22)");
		list.add(22);
		System.out.println("add(38)");
		list.add(38);
		
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());

		System.out.println();

		System.out.println("contains(60): " + list.contains(60));
		System.out.println("remove(60): " + list.remove(60));
		System.out.println("contains(60): " + list.contains(60));
		
		
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());
		
		System.out.println();
		
		System.out.println("contains(22): " + list.contains(22));
		System.out.println("remove(22): " + list.remove(22));
		System.out.println("contains(22): " + list.contains(22));

		System.out.println();
		
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());
		*/
	}
}
