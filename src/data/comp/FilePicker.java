/*
 * FilePicker class is made for the browsing the file 
 * we use two modes for it for saving and opening.
 */
package data.comp;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author pranj
 */
public class FilePicker extends JPanel{
    
    private final JLabel jLabel;
    private final JTextField jTextField;
    private final JButton jButton;
    private final JFileChooser jFileChooser;
    
    private int mode;
    public static final int MODE_OPEN = 1;
    public static final int MODE_SAVE = 2;
    
    public FilePicker(){
        
        jFileChooser = new JFileChooser();
        
        setLayout(new FlowLayout(FlowLayout.CENTER, 5,5));
        
        jLabel = new JLabel("Pick a File");
        jTextField = new JTextField(30);
        jButton = new JButton("Browse..");
        
        jButton.addActionListener((ActionEvent evt) -> {
            buttonActionPerformed(evt);             
        });
        
        add(jLabel);
        add(jTextField);
        add(jButton);
        
    }
    
    private void buttonActionPerformed(ActionEvent evt){
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
