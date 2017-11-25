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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author pranj
 */
public class UI extends JPanel{
    private final JLabel jLabel;
    private final JTextField jTextField;
    private final JButton jButton;
    private final JFileChooser jFileChooser;
    JFrame f;
    public FilePicker filePicker;
    public int modeCompress = 0;
    public UI() throws FileNotFoundException{
        f = new JFrame("file compressor");
        jFileChooser = new JFileChooser();
        
        setLayout(new FlowLayout(FlowLayout.CENTER, 5,5));
        
        jLabel = new JLabel("Pick a File");
        jTextField = new JTextField(30);
        jButton = new JButton("Browse..");
        
        jButton.addActionListener((ActionEvent evt) -> {
            try {             
                buttonActionPerforme(evt);
            } catch (IOException ex) {
                Logger.getLogger(FilePicker.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        add(jLabel);
        add(jTextField);
        add(jButton);
        f.setLocationRelativeTo(null);
        JButton compress = new JButton("Compress");
        JButton expand = new JButton("Decompress");
        
         compress.addActionListener((ActionEvent evt) -> {
             try {
                 buttonActionPerformed(evt);
             } catch (IOException ex) {
                 Logger.getLogger(FilePicker.class.getName()).log(Level.SEVERE, null, ex);
             } catch (ClassNotFoundException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
         
          expand.addActionListener((ActionEvent evt) -> {
             try {
                 modeCompress = 1;
                 buttonActionPerformed(evt);
             } catch (IOException ex) {
                 Logger.getLogger(FilePicker.class.getName()).log(Level.SEVERE, null, ex);
             } catch (ClassNotFoundException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
          
        compress.setBounds(50,200,120,30);
        expand.setBounds(200,200,120,30);
        
        f.add(compress);
        f.add(expand);
        
        f.setSize(600,500);
        f.setLayout(new FlowLayout());
        f.setVisible(true);    
    }
        private void buttonActionPerforme(ActionEvent evt)throws IOException{
            if(jFileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
                jTextField.setText(jFileChooser.getSelectedFile().getAbsolutePath());
            }
        }
        
         private void buttonActionPerformed(ActionEvent evt)throws IOException, FileNotFoundException, ClassNotFoundException{
                String name = FilenameUtils.getBaseName(filePicker.getSelectedFilePath());
                String ext = FilenameUtils.getExtension(filePicker.getSelectedFilePath());
                if(modeCompress==0)
                Huffman.encode(filePicker,name.getBytes(),ext.getBytes(),filePicker.ary);
                else if(modeCompress==1)
                Huffman.decode(filePicker);    
        }
        
}
