package list;

public class ArrayList<T> {
	
	private static int CAPACITY = 10;
	private T[] arr = (T[]) new Object[CAPACITY];
	private int size = 0;
	
	public ArrayList() { }
	
	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size==0;
	}
	public void add(T item) {
		if(size==CAPACITY) resize();
		arr[size++] = item;
	}
	private boolean resize() {
		int temp = CAPACITY;
		CAPACITY = CAPACITY<<1;
		T[] arrCopy = arr;
		arr = (T[]) new Object[CAPACITY];
		for(int i=0; i<temp; i++) {
			arr[i] = arrCopy[i];
		}
		return true;
	}
	
	public T remove(int index) {
		if(index<0 || index>=size) throw new ArrayIndexOutOfBoundsException();
		T res = arr[index];
		for(int i=index; i<size-1; i++) {
			arr[i] = arr[i+1];
		}
		--size;
		return res;
	}
	
	public T get(int index) {
		if(index<0 || index>=CAPACITY) throw new ArrayIndexOutOfBoundsException();
		return arr[index];
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<size; i++) {
			sb.append(arr[i] + ",");
		}
		return sb.toString().substring(0, sb.length()-1);
	}
	
	public static void main(String[] args) {
		
		ArrayList<Integer> list = new ArrayList<>();

		System.out.println("add(0)");
		list.add(0);
		System.out.println("add(10)");
		list.add(10);
		System.out.println("add(20)");
		list.add(20);
		System.out.println("add(30)");
		list.add(30);
		System.out.println("add(40)");
		list.add(40);
		System.out.println("add(50)");
		list.add(50);

		System.out.println("items: " + list);
		System.out.println("size: " + list.size());
		System.out.println("get(0): " + list.get(0));
		System.out.println("get(1): " + list.get(1));
		System.out.println("get(2): " + list.get(2));
		System.out.println("get(5): " + list.get(5));

		System.out.println();
		
		System.out.println("remove(3): " + list.remove(3));
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());

		System.out.println();
		
		System.out.println("remove(3): " + list.remove(3));
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());

		System.out.println();
		

		System.out.println("remove(3): " + list.remove(3));
		System.out.println("items: " + list);
		System.out.println("size: " + list.size());

		System.out.println();

		System.out.println("add(60)");
		list.add(60);
		System.out.println("add(70)");
		list.add(70);

		System.out.println("items: " + list);
		System.out.println("size: " + list.size());
	}
}
