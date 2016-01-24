package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Graph<Type> {
    private HashSet<Type> vertex;
    private HashMap<Type, LinkedList<Edge<Type>>> adj;
    
    private int V;
    private int E;
    
    public Graph() {
        V = E = 0;
        vertex = new HashSet<>();
        adj = new HashMap<>();
    }
    
    private static class Edge<Type2> {
        private final Type2 V1, V2;
        private final int WEIGHT;
        
        public Edge(Type2 item1, Type2 item2, int weight) {
            V1 = item1;
            V2 = item2;
            WEIGHT = weight;
        }
    }
    
    private class Vertex<Type3> implements Comparable<Vertex<Type3>> {
    	private Type3 v;
    	private int WEIGHT;
    	
    	public Vertex(Type3 v, int weight) {
    		this.v = v;
    		this.WEIGHT = weight;
    	}

		@Override
		public int compareTo(Vertex<Type3> o) {
			// TODO Auto-generated method stub
			return this.WEIGHT - o.WEIGHT;
		}
    }
    
    public int E() {
    	return E;
    }
    public int V() {
    	return V;
    }
    
    public void addVertex(Type item) {
    	vertex.add(item);
    	adj.put(item, new LinkedList<>());
    	++V;
    }
    
    public void addEdge(Edge<Type> edge) {
    	validVertex(edge.V1);
    	validVertex(edge.V2);
    	adj.get(edge.V1).add(edge);
    	adj.get(edge.V2).add(edge);
    	++E;
    }
    
    private void validVertex(Type vertex) {
    	if(!adj.containsKey(vertex)) throw new NoSuchElementException("invalid vertex");
    }
    
    
    public Map<Type, Integer> shortesDijkstra(Type v) {
    	Map<Type, Integer> map = new HashMap<>();
    	PriorityQueue<Vertex<Type>> pq = new PriorityQueue<>();
    	Set<Type> set = new HashSet<>();
    	for(Type vv : vertex) {
    		set.add(vv);
    	}
    	map.put(v, 0);
    	set.remove(v);
    	while(!set.isEmpty()) {
	    	for(Edge<Type> edge : adj.get(v)) {
	    		Type v2 = edge.V1==v ? edge.V2 : edge.V1;
	    		if(set.contains(v2)) pq.add(new Vertex<Type>(v2, edge.WEIGHT+map.get(v)));
	    	}
	    	Vertex<Type> nextVertex = pq.poll();
	    	v = nextVertex.v;
	    	map.put(nextVertex.v, nextVertex.WEIGHT);
	    	set.remove(v);
    	}
    	return map;
    }
    
    
    public List<Type> bfs() {
    	List<Type> list = new ArrayList<>();
    	Map<Type, Boolean> visited = new HashMap<>();
    	for(Type v : vertex) {
    		visited.put(v,  false); // initialize visited map
    	}
    	Map<Type, Type> parent = new HashMap<>();
    	Iterator<Type> it = vertex.iterator();
    	while(it.hasNext()) {
    		bfs(it.next(), visited, parent, list); // run bfs on every node
    	}
    	// System.out.println(parent);
    	return list;
    }
    private void bfs(Type v, Map<Type, Boolean> visited, Map<Type, Type> parent, List<Type> list) {
    	if(!visited.get(v)) {
	    	list.add(v);
	    	visited.put(v, true);
	    	Queue<Type> q = new LinkedList<>();
	    	q.offer(v);
	    	while(!q.isEmpty()) {
		    	LinkedList<Edge<Type>> a = adj.get(q.poll());
		    	for(Edge<Type> edge : a) {
		    		Type next = edge.V1 == v ? edge.V2 : edge.V1;
		    		if(visited.get(next)) continue;
		    		else {
		    			visited.put(next, true);
		    			list.add(next);
		    			parent.put(next, v); // add parent pointer
		    			q.offer(next); // enqueue
		    		}
		    	}
	    	}
    	}
    }
    
    
    public List<Type> dfs() {
    	List<Type> list = new ArrayList<>();
    	Map<Type, Boolean> visited = new HashMap<>();
    	for(Type v : vertex) {
    		visited.put(v, false);
    	}
    	Iterator<Type> it = vertex.iterator();
    	while(it.hasNext()) {
    		dfs(it.next(), visited, list);
    	}
    	return list;
    }
    private void dfs(Type v, Map<Type, Boolean> visited, List<Type> list) {
    	if(!visited.get(v)) {
    		//System.out.println(v);
	    	list.add(v);
	    	visited.put(v, true);
	    	LinkedList<Edge<Type>> a = adj.get(v);
	    	for(Edge<Type> edge : a) {
	    		Type next = edge.V1 == v ? edge.V2 : edge.V1;
	    		if(visited.get(next)) continue;
	    		else dfs(next, visited, list);
	    	}
    	}
    }
    
    public static void main(String[] args) {
    	Graph<Character> g = new Graph<>();
    	g.addVertex('A');
    	g.addVertex('B');
    	g.addVertex('C');
    	g.addVertex('D');
    	g.addVertex('E');

    	g.addEdge(new Edge<>('A', 'B', 10));
    	g.addEdge(new Edge<>('A', 'C', 3));
    	g.addEdge(new Edge<>('A', 'E', 1));
    	g.addEdge(new Edge<>('B', 'C', 2));
    	g.addEdge(new Edge<>('C', 'D', 5));
    	g.addEdge(new Edge<>('D', 'E', -2));
    	g.addEdge(new Edge<>('C', 'E', 8));

    	System.out.println("vertex count: " + g.V());
    	System.out.println("edge count: " + g.E());


    	//System.out.println(g.bfs());
    	//System.out.println(g.dfs());
    	
    	//System.out.println(g.adj);
    	
    	System.out.println(g.shortesDijkstra('A'));
    	//System.out.println(g.dfs());
    }
}
