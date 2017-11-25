/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.comp;
import data.comp.FilePicker;
import data.comp.Huffman;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author pranj
 */
public class testcon {
    public FileInputStream fin;
    FilePicker filePicker;
    public testcon(String s) throws FileNotFoundException, IOException, ClassNotFoundException {
        fin = new FileInputStream(s);
        filePicker = new FilePicker(fin);
               String name = FilenameUtils.getBaseName(s);
                String ext = FilenameUtils.getExtension(s);
                //Huffman.encode(filePicker,name.getBytes(),ext.getBytes(),filePicker.ary);
                Huffman.decode(filePicker);   
    }
    
}
