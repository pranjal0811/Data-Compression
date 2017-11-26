/*
 * FilePicker class is made for the browsing the file 
 * we use two modes for it for saving and opening.
 * There is the binary input intialization in array.
 */
package data.comp;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.apache.commons.io.FilenameUtils;
/**
 *
 * @author pranj
 */
public class FilePicker extends JPanel{
    public String str = "";
    public FileInputStream fin; 
    
    public byte [] ary;
    
    public FilePicker(FileInputStream fin) throws FileNotFoundException, IOException{
        this.fin = fin;
        ary = new byte[fin.available()];
        fin.read(ary);  
    }
    
    
    public String getSelectedFilePath(){
        return DataComp.s;
    }
    
}
