/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.comp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import org.apache.commons.io.FilenameUtils;


/**
 *
 * @author pranj
 */
public class Huffman {
public static Map<Character, Integer> cFrequency;    
public static int [] getCharacterFrequency(String ary){    
    cFrequency = new HashMap<>();
    int value =0;
    for(int i=0; i<ary.length();i++){
        cFrequency.put(ary.charAt(i),value);
    }
    
    for(int i=0; i<ary.length(); i++){
        
        if(cFrequency.containsKey(ary.charAt(i))){
            cFrequency.put(ary.charAt(i),cFrequency.get(ary.charAt(i))+1);
        }
    }
    int count[] = new int[cFrequency.size()];
    int i =0;
    for(Map.Entry m : cFrequency.entrySet()){
        count[i] = (int) m.getValue();
        i++;
    }
    for(i=0; i< count.length; i++)
        System.out.println(count[i]);
    
    return count;
}

public static Tree getHuffmanTree(int []counts){
    //create a Heap to hold the trees
    PriorityQueue <Tree> heap = new PriorityQueue<>();
    for(int i=0; i<counts.length; i++){
        if(counts[i]>0){
            heap.add(new Tree(counts[i], (char) cFrequency.keySet().toArray()[i])); //A leaf node tree
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
    String codes[] = new String[cFrequency.size()];
    System.out.println(codes.length+" "+cFrequency.size());
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
        for(int i=0; i<cFrequency.size(); i++){
            if(root.element == (char)cFrequency.keySet().toArray()[i]){
                codes[i] = root.code;
                System.out.println("Codes Else : " + codes[i]+" " + i);
            }
        }
        //codes[(int)root.element]=root.code;
        System.out.println(root.weight);
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
public static void encode(FilePicker filePicker,byte[]name,byte []ext,byte[] ary) throws FileNotFoundException, IOException{
    String s = new String(ary);
    String en = "";
    System.out.println(s.length());
    int [] counts = getCharacterFrequency(s);
    Tree tree;
    tree = getHuffmanTree(counts);
    System.out.println("after tree");
    String []codes = getCodes(tree.root);
    String []codes_x = null;
    for(String k : codes)
        System.out.print(k);
    Map <Character,String> map = new HashMap <>();
    for(int i=0; i<codes.length; i++){
        if(counts[i]>0){
            map.put((char) cFrequency.keySet().toArray()[i] , codes[i]);
        }
    }
    for(Map.Entry m : map.entrySet()){
           System.out.println(m.getKey()+""+m.getValue());
}
    System.out.println("after map");
    codes_x = new String[s.length()];
    for(int i=0; i<s.length();i++){
        if(map.containsKey(s.charAt(i))){
            en += map.get(s.charAt(i));
            
            System.out.println(s.charAt(i)+""+map.get(s.charAt(i)));
            codes_x[i] = map.get(s.charAt(i));
        }
    }
    System.out.println("CODES_X "+ Arrays.toString(codes_x));
    System.out.println("en run");
    BitSet bitset = new BitSet(en.length());
    for(int i=0; i< en.length(); i++){
        if(en.charAt(i)=='1')
            bitset.set(i);
    }
    System.out.println("bitset");
    File_Object obj;
    obj = new File_Object(name,ext,bitset,map);
    FileOutputStream fout = new FileOutputStream(FilenameUtils.getFullPath(filePicker.getSelectedFilePath())+"out.pranjal");
    ObjectOutputStream oos = new ObjectOutputStream(fout);
    oos.writeObject(obj);
    System.out.println("hurra!!");            
}

public static void decode(FilePicker filePicker) throws FileNotFoundException, IOException, ClassNotFoundException{
    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePicker.getSelectedFilePath()));
    File_Object obj = (File_Object)ois.readObject();
    String name = new String(obj.name);
    String ext = new String(obj.ext);
    String code = "";
    for(int i=0; i<obj.bits.length();i++){
        if(obj.bits.get(i))
            code += "1";
        else
            code += "0";
    }
    char[]arr;
    arr = code.toCharArray();
    String temp = "";
    String result = "";
    for(int i=0; i<code.length();i++){
        temp += String.valueOf(arr[i]);
        for(Map.Entry m : obj.map.entrySet()){
            if(m.getValue().equals(temp)){
                result += String.valueOf(m.getKey());
                temp = "";
                break;
            }
        }
    }
    FileOutputStream fout;
    fout = new FileOutputStream("C:\\Users\\pranj\\Desktop\\testw.doc");
    fout.write(result.getBytes());
    System.out.println("decoded");
}
        

}