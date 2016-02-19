package hash;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.LinkedList;

public class HashSet<T> {
	
	private static int CAPACITY = 16;
	private static float LF = 0.75f;
	private static int MAX_SIZE = (int)(LF*CAPACITY);
	@SuppressWarnings("unchecked")
	private List<T>[] arr = new LinkedList[CAPACITY];
	private int size = 0; 

	public HashSet() {
		for(int i=0; i<CAPACITY; i++) {
			arr[i] = new LinkedList<>();
		}
	}
	public HashSet(int initialCapacity) {
		CAPACITY = initialCapacity;
		MAX_SIZE = (int)(LF*CAPACITY);
		for(int i=0; i<CAPACITY; i++) {
			arr[i] = new LinkedList<>();
		}
	}
	public HashSet(int initialCapacity, int loadFactor) {
		CAPACITY = initialCapacity;
		LF = loadFactor;
		MAX_SIZE = (int)(LF*CAPACITY);
		for(int i=0; i<CAPACITY; i++) {
			arr[i] = new LinkedList<>();
		}
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	public void add(T item) {
		if(contains(item)) return; // already existed
		if(size==MAX_SIZE) resize(); // resize
		addItem(item); // add item into array
		++size; // increase size
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
	
	// needs to add iterator method

	@SuppressWarnings("unchecked")
	private boolean resize() {
		int temp = CAPACITY;
		CAPACITY = CAPACITY<<1;
		MAX_SIZE = (int)(LF*CAPACITY);
		List<T>[] arrCopy = arr;
		arr = new LinkedList[CAPACITY];
		for(int i=0; i<CAPACITY; i++) {
			arr[i] = new LinkedList<>();
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
		list.add("a");
		
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
		
		System.out.println("remove(bcd): " + list.remove("bcd"));
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());
		
		
		System.out.println();
		
		System.out.println("remove(bcd): " + list.remove("bcd"));
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

		System.out.println("remove(a): " + list.remove("a"));
		System.out.println("remove(abcdefg): " + list.remove("abcdefg"));
		System.out.println("remove(def): " + list.remove("def"));
		System.out.println("remove(ghi): " + list.remove("ghi"));

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
