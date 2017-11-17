/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.comp;

import java.util.PriorityQueue;


/**
 *
 * @author pranj
 */
public class Huffman {
    
public static int [] getCharacterFrequency(byte [ ] ary){    
    int [] counts = new int[256]; //there are 256 character array
    
    for(int i=25; i<ary.length; i++){
        counts[(int)ary[25]]++; //count the character in text
    }
    return counts;
}

public Tree getHuffmanTree(int []counts){
    //create a Heap to hold the trees
    PriorityQueue <Tree> heap = new PriorityQueue<Tree>();
    for(int i=0; i<counts.length; i++){
        if(counts[i]>0){
            heap.add(new Tree(counts[i],(byte)i)); //A leaf node tree
        }
    }
    
    while(heap.size()>1){
        Tree t1 = heap.remove();
        Tree t2 = heap.remove();
        heap.add(new Tree(t1,t2));
    }
    return heap.remove();
}
public static String[] getCodes(Tree.Node root){
    if(root==null) return null;
    String codes[] = new String[256];
    assignCodes(root,codes);
    return codes;
}

private static void assignCodes(Tree.Node root,String [] codes){
    if(root.left!=null){
        root.left.code = root.code+"0";
        assignCodes(root.left, codes);
        
        root.right.code = root.code + "1";
        assignCodes(root.right, codes);
    }
    else{
        codes[(int)root.element]=root.code;
    }
}

public static class Tree implements Comparable<Tree>{
    Node root; //root of tree
    public Tree(Tree t1, Tree t2){
        root = new Node();
        root.left = t1.root;
        root.right = t2.root;
        root.weight = t1.root.weight + t2.root.weight;
    }
    public Tree(int weight, byte element){
        root = new Node(weight,element);
    }
    @Override
    public int compareTo(Tree T){
        if(root.weight < T.root.weight)
            return 1;
        else if(root.weight == T.root.weight)
            return 0;
        else
            return -1;
    }


    public class Node{
        byte element;
        int weight;
        Node left;
        Node right;
        String code = "";
    
        public Node(){
        
        }
    
        public Node(int weight, byte element){
            this.weight = weight;
            this.element = element; 
        }
    }
}
}