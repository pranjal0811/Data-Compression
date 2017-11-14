/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.comp;

import java.awt.FlowLayout;
import java.io.FileNotFoundException;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author pranj
 */
public class UI {
    JFrame f;
    UI() throws FileNotFoundException{
        f = new JFrame("file compressor");
        
        FilePicker filePicker = new FilePicker();
        filePicker.setMode(FilePicker.MODE_OPEN);
        f.add(filePicker);
        f.setLocationRelativeTo(null);
        JButton b = new JButton("Compress");
        JButton d = new JButton("Decompress");
        
        b.setBounds(50,200,120,30);
        d.setBounds(200,200,120,30);
        
        f.add(b);
        f.add(d);
        
        f.setSize(600,500);
        f.setLayout(new FlowLayout());
        f.setVisible(true);    
    }
}
