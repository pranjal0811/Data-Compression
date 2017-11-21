/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.comp;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
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
                String name = FilenameUtils.getBaseName(filePicker.getSelectedFilePath());
                String ext = FilenameUtils.getExtension(filePicker.getSelectedFilePath());
                Huffman.encode(filePicker,name.getBytes(),ext.getBytes(),filePicker.ary);
        }
}
