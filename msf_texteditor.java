/**
 * @author firaol
 * program that opens text file for editing !
 */
package msf_texteditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
public class msf_texteditor extends JFrame implements  java.awt.event.ActionListener{
  private JMenuBar bar;
  private JMenu filemenu; 
  private JMenuItem menu;
  private JMenuItem save;
  private JMenuItem exit1;
 private JTextArea input;
 private JScrollPane scroll;
 private JButton cbot;
 private JButton clbot;
 private JButton about;
 public  String filename=null;
 private File file;
 private FileOutputStream fout;
 private FileInputStream finput;
 private FileWriter fwriter;
private FileReader freader;
private int selected=0;
 //constructor 
    public msf_texteditor()
    {
        super("msf text editor");
        filemenu=new JMenu("file");
        menu=new JMenuItem("open");
        save=new JMenuItem("save");
        exit1=new JMenuItem("exit");
        input=new JTextArea(20,40);
        input.setEditable(true);
        //set buttons 
        cbot=new JButton("clear");
        clbot=new JButton("close the file");
        about=new JButton("about");
        clbot.setEnabled(false);
        //set scroll to text area !
        scroll=new JScrollPane(input);
        bar=new JMenuBar();
        bar.add(filemenu);
        filemenu.add(menu);
        filemenu.add(save);
        filemenu.add(exit1);
      setJMenuBar(bar);
      add(scroll);
      add(cbot);
      add(clbot);
      add(about);
  //add action listner
  exit1.addActionListener(this);
  menu.addActionListener(this);
  save.addActionListener(this);
  cbot.addActionListener(this);
  clbot.addActionListener(this);
  about.addActionListener(this);
//=========================================        
        setSize(500,500);
        FlowLayout la=new FlowLayout(FlowLayout.LEFT);
        setLayout(la);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //place the frame on the center!
        setLocationRelativeTo(null);
        setVisible(true);
    }
    //main class !
    public static void main(String[] args) {
       new msf_texteditor();
    }

    //implement the method action performed !
    public void actionPerformed(ActionEvent e) {
       if(e.getSource()==menu)
       {
  //open the filemenu 
         JFileChooser choose=new JFileChooser();
         choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
         choose.setDialogTitle("select file");
         choose.setApproveButtonText("open file");
         choose.showOpenDialog(bar);
         file=choose.getSelectedFile();
           try {     
               finput=new FileInputStream(file); //open
               //reserve a bytes by file size !
           byte[] buffer=new byte[(int)file.length()];
             try {
                 //read the file to the byte array
                 finput.read(buffer);
                //now cast the bytes to the string 
               String sbuffer;
               StringBuffer sb=new StringBuffer();
               sb.setLength((int)file.length());
               for(int i=0;i<file.length();i++)
                  sb.append((char)buffer[i]);
               //convert the stringbuffer to string !
               sbuffer=sb.toString();
               //write the string to the input area !
               input.setText(sbuffer);
               //enable close button
               clbot.setEnabled(true);
               //mark the file is opened
               selected=1;
               finput.close();
             } catch (IOException ex) {
                 System.out.println("cant read the file !!!!!!");
                  System.exit(1);
             }
           } catch (FileNotFoundException ex) {
             ex.printStackTrace();
             System.exit(1);
           }
        
       } else if (e.getSource()==exit1)
       {
          System.exit(0);
          
       } else if(e.getSource()==save) {
           try {
               // if the useer clicked save button
                  //if the no file opened 
               if(selected==0)
               {
              JFileChooser chooser=new JFileChooser();
              chooser.setDialogTitle("SAVE AS ");
              chooser.setApproveButtonText("save to file");
              chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
              chooser.setApproveButtonToolTipText("create a new file or click on file you wish to save");
              chooser.showSaveDialog(bar);
              file=chooser.getSelectedFile();
               }
               fwriter=new FileWriter(file);
               //output the modiefied text from text area to the file
               String sbuffer;
               sbuffer=input.getText();
               fwriter.write(sbuffer);
               //mark the opened file already exist
               //it uses present file instead of taking new 
               selected=1;
               fwriter.close();
               //announce the file saved 
                JOptionPane.showMessageDialog(this, "the file saved successfully","msf editor",JOptionPane.PLAIN_MESSAGE);
               //enable close bot
               clbot.setEnabled(true);
           } catch (IOException ex) {
               Logger.getLogger(msf_texteditor.class.getName()).log(Level.SEVERE, null, ex);
           }
       } else if(e.getSource()==cbot)
       {
           input.setText("");
       } else if(e.getSource()==clbot)
       {
           
               //close the file
               input.setText("");
               clbot.setEnabled(false);
               selected=0;
       } else if(e.getSource()==about)
       {
          JInternalFrame frame=new JInternalFrame("about",true,true,true,true);
          JPanel panel=new JPanel();
          JLabel label=new JLabel("this is program written by \n msf 2021 @firaol wakuma");
          panel.add(label);
          //add the pannel to internal frame 
          frame.add(panel);
          //desktop pane manage internal frames 
          JDesktopPane desktop=new JDesktopPane();
          this.add(desktop);
          this.add(frame);
          frame.setVisible(true);
       }
    } ///end action performed !
    
} //end class!
