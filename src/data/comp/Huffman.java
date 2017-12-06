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
public static void getCharacterFrequency(String ary){    
    cFrequency = new HashMap<>();
    for(int i=0; i<ary.length(); i++){
        
        if(!cFrequency.containsKey(ary.charAt(i))){
            cFrequency.put(ary.charAt(i),1);
        }
        else{
            cFrequency.put(ary.charAt(i),cFrequency.get(ary.charAt(i))+1);
        }
    }
    /*for(Map.Entry m: cFrequency.entrySet()){
        System.out.println(m.getKey()+" "+m.getValue());
    }*/
}

public static Tree getHuffmanTree(){
    //create a Heap to hold the trees
    PriorityQueue <Tree> heap = new PriorityQueue<>();
    for(Map.Entry m : cFrequency.entrySet()){
            heap.add(new Tree((int) m.getValue(),(char) m.getKey())); //A leaf node tre
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
    //System.out.println(codes.length+" "+cFrequency.size());
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
          //      System.out.println("Codes Else : " + codes[i]+" " + i);
            }
        }
        //codes[(int)root.element]=root.code;
        //System.out.println(root.weight);
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
public static void encode(FilePicker filePicker,byte[]name,byte []ext) throws FileNotFoundException, IOException{
    String s = new String(filePicker.ary);
    //System.out.println(filePicker.ary.length);
    
    getCharacterFrequency(s);
    Tree tree;
    tree = getHuffmanTree();
    //System.out.println("after tree");
    String []codes = getCodes(tree.root);
    String []codes_x = null;
    //for(String k : codes)
      //  System.out.print(k);
    Map <Character,String> map = new HashMap <>();
    for(int i=0; i<codes.length; i++){
            map.put((char) cFrequency.keySet().toArray()[i] , codes[i]);
    }
   /* for(Map.Entry m : map.entrySet()){
           System.out.println(m.getKey()+""+m.getValue());
}*/
    //System.out.println("after map");
    codes_x = new String[filePicker.ary.length];
    for(int i=0; i<s.length();i++){
        // System.out.println(s.charAt(i)+""+map.get(s.charAt(i)));
          codes_x[i] = map.get(s.charAt(i));    
    }
    StringBuilder en = new StringBuilder("");
    for(String code1 : codes_x){
        en.append(code1);
    }
   // System.out.println(en);
    BitSet bitset = new BitSet(en.length());
    for(int i=0; i< en.length(); i++){
        if(en.charAt(i) =='1')
            bitset.set(i);
    }
    //System.out.println(bitset.length()+""+bitset.size());
    File_Object obj;
    obj = new File_Object(name,ext,bitset,map);
    FileOutputStream fout = new FileOutputStream(FilenameUtils.getFullPath(filePicker.getSelectedFilePath())+"out.pranjal");
    ObjectOutputStream oos = new ObjectOutputStream(fout);
    oos.writeObject(obj);
    //System.out.println("hurra!!");            
}

public static void decode(FilePicker filePicker) throws FileNotFoundException, IOException, ClassNotFoundException{
    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePicker.getSelectedFilePath()));
    File_Object obj = (File_Object)ois.readObject();
    String name = new String(obj.name);
    String ext = new String(obj.ext);
    StringBuilder code = new StringBuilder("");
    for(int i=0; i<=obj.bits.length();i++){
        if(obj.bits.get(i))
            code.append("1");
        else
            code.append("0");
    }
    //System.out.println("After code formed");
    //System.out.println(code.length()+" "+code);
    Map<String,Character> rev = new HashMap<>();
    for(Map.Entry m : obj.map.entrySet()){
        rev.put((String)m.getValue(),(char)m.getKey());
    }
    //System.out.println("get reversed");
    //System.out.println(Arrays.toString(code));
    StringBuilder temp = new StringBuilder("");
    StringBuilder result = new StringBuilder("");
    for (int k =0 ;k<code.length(); k++) {
        temp.append(code.charAt(k));
        /*for(Map.Entry m : obj.map.entrySet()){
            if(m.getValue().equals(temp)){
                result += String.valueOf(m.getKey());
                temp="";
            }
        }*/
       if(rev.containsKey(String.valueOf(temp))){
            result.append(String.valueOf(rev.get(String.valueOf(temp))));
            temp = new StringBuilder("");
        }
    }
    FileOutputStream fout;
    fout = new FileOutputStream(FilenameUtils.getFullPath(filePicker.getSelectedFilePath())+name+"."+ext);
    fout.write(result.toString().getBytes());
    //System.out.println("decoded");
}
        

}