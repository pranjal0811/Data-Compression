/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.comp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import org.apache.commons.io.FilenameUtils;


/**
 *
 * @author pranj
 */
public class Huffman {
    
public static int [] getCharacterFrequency(String ary){    
    int [] counts = new int[256]; //there are 256 character array
    
    for(int i=0; i<ary.length(); i++){
        counts[(int)ary.charAt(i)]++; //count the character in text
    }
    return counts;
}

public static Tree getHuffmanTree(int []counts){
    //create a Heap to hold the trees
    PriorityQueue <Tree> heap = new PriorityQueue<>();
    for(int i=0; i<counts.length; i++){
        if(counts[i]>0){
            heap.add(new Tree(counts[i],(char)i)); //A leaf node tree
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

public static void encode(FilePicker filePicker,byte[]name,byte []ext,byte[] ary) throws FileNotFoundException, IOException{
    String s = new String(ary);
    String en = "";
    int [] counts = getCharacterFrequency(s);
    Tree tree;
    tree = getHuffmanTree(counts);
    String []codes = getCodes(tree.root);
    Map <Character,String> map = new HashMap <>();
    for(int i=0; i<codes.length; i++){
        if(counts[i]!=0){
            map.put((char)i , codes[i]);
        }
    }        
    for(int i=0; i<s.length();i++){
        if(map.containsKey(s.charAt(i))){
            en += map.get(s.charAt(i));
        }
    } 
    File_Object obj;
    obj = new File_Object(name,ext,en,map);
    FileOutputStream fout = new FileOutputStream(FilenameUtils.getFullPath(filePicker.getSelectedFilePath())+"out.pranjal");
    ObjectOutputStream oos = new ObjectOutputStream(fout);
    oos.writeObject(obj);
    System.out.println("hurra!!");
            
}
        
public static class Tree implements Comparable<Tree>{
    Node root; //root of tree
    public Tree(Tree t1, Tree t2){
        root = new Node();
        root.left = t1.root;
        root.right = t2.root;
        root.weight = t1.root.weight + t2.root.weight;
    }
    public Tree(int weight, char element){
        root = new Node(weight,element);
    }
    @Override
    public int compareTo(Tree T){
        if(root.weight > T.root.weight)
            return 1;
        else if(root.weight == T.root.weight)
            return 0;
        else
            return -1;
    }


    public class Node{
        char element;
        int weight;
        Node left;
        Node right;
        String code = "";
    
        public Node(){
        
        }
    
        public Node(int weight, char element){
            this.weight = weight;
            this.element = element; 
        }
    }
}
}