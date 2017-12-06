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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author pranj
 */
public class UI extends JPanel{
    SwingWorker <Void,String> worker_browse;
    SwingWorker <Void,String> worker_compress;
    SwingWorker <Void,String> worker_expand;
    private final JLabel jLabel;
    private final JTextField jTextField;
    private final JButton jButton;
    private final JFileChooser jFileChooser;
    private final JTextArea jTextArea;
    private JFrame f;
    public FilePicker filePicker;
    public int modeCompress = 0;
    public UI() throws FileNotFoundException{
        f = new JFrame("file compressor");
        jFileChooser = new JFileChooser();
        jTextArea = new JTextArea(40,40);
        //f.setLayout(new FlowLayout(FlowLayout.CENTER, 5,5));
        
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
        
        f.add(jLabel);
        f.add(jTextField);
        f.add(jButton);
        //f.setLocationRelativeTo(null);
        JButton compress = new JButton("Compress");
        JButton expand = new JButton("Expand");
        
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
        
        jTextArea.setBounds(20,400,100,100);  
        compress.setBounds(50,200,120,30);
        expand.setBounds(200,200,120,30);
        f.add(compress);
        f.add(expand);
        f.add(jTextArea);
        jTextArea.doLayout();
        f.setSize(600,500);
        f.setLayout(new FlowLayout());
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
        private void buttonActionPerforme(ActionEvent evt)throws IOException{
            if(jFileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
                String str = jFileChooser.getSelectedFile().getAbsolutePath();
                jTextField.setText(str);
                worker_browse = new SwingWorker<Void,String>(){
                    @Override
                    protected Void doInBackground() throws Exception {
                        filePicker = new FilePicker(str);
                        publish("getting a file.....");
                        return null;
                    }
                    @Override
                        protected void process(List<String> chunks){
                            for(final String chunk : chunks){
                                jTextArea.append(chunk);
                                jTextArea.append("\n");
                            }
                        }
                };
                worker_browse.execute();           
            }
        }
        
         private void buttonActionPerformed(ActionEvent evt)throws IOException, FileNotFoundException, ClassNotFoundException{
                String name = FilenameUtils.getBaseName(filePicker.getSelectedFilePath());
                String ext = FilenameUtils.getExtension(filePicker.getSelectedFilePath());
                if(modeCompress==0){
                        worker_compress = new SwingWorker<Void,String>(){
                        @Override
                        protected Void doInBackground() throws Exception {
                            publish("Compressing.....");
                            Huffman.encode(filePicker,name.getBytes(),ext.getBytes());
                            publish("Hurra!! A file is compressed");
                            publish("Find a file at the same location as .pra extension");
                            return null;
                        }
                        @Override
                        protected void process(List<String> chunks){
                            for(final String chunk : chunks){
                                jTextArea.append(chunk);
                                jTextArea.append("\n");
                            }
                        }
                    };
                    worker_compress.execute();
                }
                else if(modeCompress==1){
                        worker_expand = new SwingWorker<Void,String>(){
                        @Override
                        protected Void doInBackground() throws Exception {
                           publish("Expanding.....");
                           Huffman.decode(filePicker);
                           publish("Hurra!! File is expanded");
                           publish("Go find at the same location");
                           return null;
                        }
                        @Override
                        protected void process(List<String> chunks){
                            for(final String chunk : chunks){
                                jTextArea.append(chunk);
                                jTextArea.append("\n");
                            }
                        }
                    };
                    worker_expand.execute();
                }     
        }
         
        
}
