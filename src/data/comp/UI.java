/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.comp;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author pranj
 */
public class UI {
    JFrame f;
    public FilePicker filePicker;
    public UI() throws FileNotFoundException{
        f = new JFrame("file compressor");
        
        filePicker = new FilePicker();
        filePicker.setMode(FilePicker.MODE_OPEN);
        f.add(filePicker);
        f.setLocationRelativeTo(null);
        JButton b = new JButton("Compress");
        JButton d = new JButton("Decompress");
        
         b.addActionListener((ActionEvent evt) -> {
             try {
                 buttonActionPerformed(evt);
             } catch (IOException ex) {
                 Logger.getLogger(FilePicker.class.getName()).log(Level.SEVERE, null, ex);
             }
        });

        b.setBounds(50,200,120,30);
        d.setBounds(200,200,120,30);
        
        f.add(b);
        f.add(d);
        
        f.setSize(600,500);
        f.setLayout(new FlowLayout());
        f.setVisible(true);    
    }
    private void buttonActionPerformed(ActionEvent evt)throws IOException{
                FileOutputStream fout;
                 fout = new FileOutputStream(FilenameUtils.getFullPath(filePicker.getSelectedFilePath())+"pra");
                 String s = Huffman.encode(filePicker.ary);
                 BitSet bitSet = new BitSet(s.length());
                 for(int i=0; i<s.length(); i++){
                     if(s.charAt(i)=='1')
                         bitSet.set(i);
                 }
                 fout.write(bitSet.toByteArray());
        }
}
