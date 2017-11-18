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
    private final JLabel jLabel;
    private final JTextField jTextField;
    private final JButton jButton;
    private final JFileChooser jFileChooser;
    public byte [] ary;
    private int mode;
    public static final int MODE_OPEN = 1;
    public static final int MODE_SAVE = 2;
    
    public FilePicker() throws FileNotFoundException{
        
        jFileChooser = new JFileChooser();
        
        setLayout(new FlowLayout(FlowLayout.CENTER, 5,5));
        
        jLabel = new JLabel("Pick a File");
        jTextField = new JTextField(30);
        jButton = new JButton("Browse..");
        
        jButton.addActionListener((ActionEvent evt) -> {
            try {             
                buttonActionPerformed(evt);
            } catch (IOException ex) {
                Logger.getLogger(FilePicker.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        add(jLabel);
        add(jTextField);
        add(jButton);
        
    }
    
    private void buttonActionPerformed(ActionEvent evt)throws IOException{
        if(mode==MODE_OPEN){
            if(jFileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
                jTextField.setText(jFileChooser.getSelectedFile().getAbsolutePath());
            }
        }
        else if(mode==MODE_SAVE){
            if(jFileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
                jTextField.setText(jFileChooser.getSelectedFile().getAbsolutePath());
            }
        }
        fin = new FileInputStream(getSelectedFilePath());//get input
        setSpace();
    }
    
    public void setSpace() throws IOException{
        ary = new byte[fin.available()];
        fin.read(ary);
        String name = FilenameUtils.getBaseName(getSelectedFilePath());
        String ext = FilenameUtils.getExtension(getSelectedFilePath());
       /* byte [] Tempn = name.getBytes(StandardCharsets.UTF_8);
        byte [] Tempe = ext.getBytes(StandardCharsets.UTF_8);
        int i=0,j=0;
        for( ;j<Tempn.length; i++,j++){
            ary[i] = Tempn[j];
        }
        for(j=0;j<Tempe.length;i++,j++){
            ary[i] = Tempe[j];
        }*/
        for(byte b : ary)
            str += getBits(b);
        FileOutputStream fos = new FileOutputStream("C:\\Users\\pranj\\Desktop\\new");
        fos.write(str.getBytes());
    }
    
    public String getBits(byte b){
        String result = "";
        for(int i=0; i<8; i++){
            result += (b&(1<<i))==0? "0":"1";
        }
        return result;
    }
    
    public void setMode(int mode){
        this.mode = mode;
    }
    
    public String getSelectedFilePath(){
        return jTextField.getText();
    }
    
    public JFileChooser getFileChooser(){
        return this.jFileChooser;
    }
}
