package trie;

import java.util.ArrayList;
import java.util.List;

/**
 * implements a trie that stores strings/words consisted of ASCII characters
 * 
 * implemented methods include: 
 *  - insert word into trie
 *  - search for a word
 *  - search for a prefix
 *  - print trie content following trie format
 *  - list all words in trie
 * 
 * @author Peng F. Tang
 * pengftang@gmail.com
 */

public class Trie {
	private static final int R = 256; // extended ASCII
    private TrieNode root;
	
    private class TrieNode {
    	boolean isEnd;
    	TrieNode[] children;
    	
        public TrieNode() {
            isEnd = true;
            children = new TrieNode[R];
        }
    }
    
    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
    	TrieNode current = root;
    	for(int i=0, L=word.length(); i<L; i++) {
        	int id = word.charAt(i) - 0;
        	if(current.children[id]==null) {
        		current.children[id] = new TrieNode();
        		current.children[id].isEnd = false;
        	}
        	current = current.children[id];
        }
        current.isEnd = true;
    }

    // Returns if the word is in the trie.
    public boolean contains(String word) {
        return search(word, 1);
    }

    // Returns if there is any word in the trie that starts with the given prefix.
    public boolean containsPrefix(String prefix) {
        return search(prefix, 2);
    }
    
    private boolean search(String str, int type) {
        TrieNode current = root;
        int i=-1, L=str.length();
        while(++i<L) {
	        int id = str.charAt(i) - 0;
	        if(current.children[id]==null) return false;
	    	current = current.children[id];
        }
        return type==1 ? current.isEnd : true;
    }
    
    //list of words in the trie
    public List<String> listWords() {
    	List<String> list = new ArrayList<>();
    	list(root, 0, "", list);
    	return list;
    }
    private void list(TrieNode current, int id, String prefix, List<String> list) {
    	if(current==null) return;
    	for(int i=0; i<R; i++) {
			TrieNode child = current.children[i];
    		if(child!=null) {
    			String res = prefix + (char)i;
    			if(child.isEnd) list.add(res);
    			list(child, i, res, list);
    		}
    	}
    }
    
    // print trie contents
    public void print() {
        print("", root, 0, true, true);
    }
    // reference: http://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram#answer-8948691
    private void print(String prefix, TrieNode root, int id, boolean isTail, boolean isRoot) {
    	if(!isRoot) System.out.println(prefix 
    			+ (isTail ? "└── " : "├── ") 
    			+ (char)id 
    			+ (root.isEnd ? " ***" : ""));
    	
    	TrieNode lastChild = null; // last child of root
    	int lastChildId = 0; // id of last child
    	boolean isLastChild = true;
    	for (int i=R-1; i>=0; i--) {
            if(root.children[i]!=null) {
            	if(isLastChild) {
            		isLastChild = false;
	            	lastChild = root.children[i];
	            	lastChildId = i;
            	}
            	else print(prefix + (isRoot ? "" : (isTail ? "    " : "│   ")), root.children[i], i, false, false);
            }
        }
        if (lastChild!=null) {
            print(prefix + (isRoot ? "" : (isTail ?"    " : "│   ")), lastChild, lastChildId, true, false);
        }
    }

    public static void main(String[] args) {
    	Trie trie = new Trie();
    	trie.insert("abc");
    	trie.insert("bay");
    	trie.insert("bey");
    	trie.insert("bea");
    	trie.insert("bed");
    	trie.insert("bee");
    	trie.insert("boy");
    	trie.insert("boyc");
    	trie.insert("boyd");
    	trie.insert("boye");
    	trie.insert("boycd");
    	trie.insert("bye-bye");
    	trie.insert("by-by");
    	trie.insert("bye");
    	trie.insert("zad");
    	trie.insert("zed");
    	trie.insert("zef");
    	
    	System.out.println("trie content (*** indicates the end of a word): ");
    	trie.print();
    	System.out.println();

    	System.out.println("list of words in trie: ");
    	System.out.println(trie.listWords());
    	System.out.println();
    	
    	System.out.println("contains boy? " + trie.contains("boy"));
    	System.out.println("contains bo? " + trie.contains("bo"));
    	System.out.println("contains boye? " + trie.contains("boye"));
    	System.out.println("contains byebye? " + trie.contains("byebye"));
    	System.out.println("contains bye-bye? " + trie.contains("bye-bye"));
    	
    	System.out.println();
    	
    	System.out.println("contains prefix bo? " + trie.containsPrefix("bo"));
    	System.out.println("contains prefix b o? " + trie.containsPrefix("b o"));
    	System.out.println("contains prefix bye? " + trie.containsPrefix("bye"));
    	System.out.println("contains prefix ye? " + trie.containsPrefix("ye"));
    	
    	
    	/*
		expected output:
		trie content (*** indicates the end of a word): 
		├── b
		│   ├── o
		│   │   └── y ***
		│   │       ├── d ***
		│   │       ├── c ***
		│   │       │   └── d ***
		│   │       └── e ***
		│   ├── e
		│   │   ├── e ***
		│   │   ├── d ***
		│   │   ├── a ***
		│   │   └── y ***
		│   ├── a
		│   │   └── y ***
		│   └── y
		│       ├── -
		│       │   └── b
		│       │       └── y ***
		│       └── e ***
		│           └── -
		│               └── b
		│                   └── y
		│                       └── e ***
		├── a
		│   └── b
		│       └── c ***
		└── z
		    ├── a
		    │   └── d ***
		    └── e
		        ├── d ***
		        └── f ***
		
		list of words in trie: 
		[abc, bay, bea, bed, bee, bey, boy, boyc, boycd, boyd, boye, by-by, bye, bye-bye, zad, zed, zef]
		
		contains boy? true
		contains bo? false
		contains boye? true
		contains byebye? false
		contains bye-bye? true
		
		contains prefix bo? true
		contains prefix b o? false
		contains prefix bye? true
		contains prefix ye? false
		*/
    }
}
