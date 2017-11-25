package data.comp;


import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class File_Object implements Serializable {
    Map <Character,String> map = new HashMap<>();
    byte [] name;
    byte [] ext;
    BitSet bits;
    public  File_Object(byte []name, byte []ext, BitSet bits, Map map) throws FileNotFoundException{
        /*BitSet bitset = new BitSet(en.length());
        for(int i=0 ; i<en.length(); i++){
            if(en.charAt(i)=='1'){
                bitset.set(i);
            }
        }*/
        //this.bits = bitset.toByteArray();
        this.bits = bits;
        this.map = map;
        this.name = name;
        this.ext = ext; 
    }
}