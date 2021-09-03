/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepadeditor;

/**
 *
 * @author 91832
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.GraphicsEnvironment;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JColorChooser;
public class NotepadEditor implements ActionListener,WindowListener
{
    JMenuItem neww,open,save,saveAs,cut,copy,paste,font,font_color,background_color;
    JTextArea textarea;
    JFrame jf,font_frame;
    JFileChooser fileChooser;
    File file;
    JComboBox font_family,font_size,font_style;
    JButton ok;
    NotepadEditor()
    {
       try
       {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
       }
       catch(Exception e)
       {
           System.out.println(e);
       }
         jf=new JFrame("*Untitled* - Notepad");
        jf.setSize(700,600);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        
        JMenuBar jmenubar=new JMenuBar();
        
        JMenu file=new JMenu("File");
         neww=new JMenuItem("New");
        neww.addActionListener(this);
        file.add(neww);
         open=new JMenuItem("Open");
        open.addActionListener(this);
        file.add(open);
         save=new JMenuItem("Save");
        save.addActionListener(this);
        file.add(save);
        saveAs=new JMenuItem("SaveAs");
        saveAs.addActionListener(this);
        file.add(saveAs);
        jmenubar.add(file);
      
         
        JMenu edit=new JMenu("Edit");
        jmenubar.add(edit);
      
        cut=new JMenuItem("cut");
        cut.addActionListener(this);
        edit.add(cut);
        
        copy=new JMenuItem("copy");
        copy.addActionListener(this);
        edit.add(copy);
        
        paste=new JMenuItem("paste");
        paste.addActionListener(this);
        edit.add(paste);
        
        JMenu format=new JMenu("Format");
        jmenubar.add(format);
        
        font=new JMenuItem("font");
        font.addActionListener(this);
        format.add(font);
        
        font_color=new JMenuItem("font color");
        font_color.addActionListener(this);
        format.add(font_color);
        
        
        background_color=new JMenuItem("background color");
        background_color.addActionListener(this);
        format.add(background_color);
        
        
        textarea=new JTextArea();
        jf.add(textarea);
        JScrollPane scrollpane=new JScrollPane(textarea);
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        jf.add(scrollpane);
        jf.addWindowListener(this);
        jf.setJMenuBar(jmenubar);
        jf.setVisible(true);
    }
    public static void main(String[] args) 
    {
       new NotepadEditor();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==neww)
        {
            newFile();
        }
        if(e.getSource()==open)
        {
             openFile();
        }
        if(e.getSource()==save)
        {
             saveFile();
        }
        if(e.getSource()==saveAs)
        {
            saveAsFile();
        }
        if(e.getSource()==cut)
        {
            textarea.cut();
        }
        if(e.getSource()==copy)
        {
            textarea.copy();
        }
        if(e.getSource()==paste)
        {
            textarea.paste();
        }
        if(e.getSource()==font)
        {
            openFontFrame();
        }
        if(e.getSource()==ok)
        {
            String fontfamily=(String)font_family.getSelectedItem();
            String fontsize=(String)font_size.getSelectedItem();  //10,20,30
            String fontstyle=(String)font_style.getSelectedItem(); // plain,bold,italic
            int style=0;
            if(fontstyle.equals("plain"))
            {
                style=0;
            }
            else if(fontstyle.equals("bold"))
            {
                style=1;
            }
            else if(fontstyle.equals("italic"))
            {
                style=2;
            }
            Font fontt=new Font(fontfamily,1,Integer.parseInt(fontsize));
            textarea.setFont(fontt);
            
            font_frame.setVisible(false);
        }
        if(e.getSource()==font_color)
        {
            Color c=JColorChooser.showDialog(jf, "Choose a color", Color.black);
            textarea.setForeground(c);
        }
        if(e.getSource()==background_color)
        {
            Color c=JColorChooser.showDialog(jf, "Choose background color", Color.white);
            textarea.setBackground(c);
        }
       
    }
    void openFile()
    {
            fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(jf);
            if(result==0)
            {
                    textarea.setText("");
                    file=fileChooser.getSelectedFile();
                    jf.setTitle(file.getName());
                    try(FileInputStream fis=new FileInputStream(file))
                    {
                        int i;
                        while((i=fis.read())!=-1)
                        {
                            textarea.append(String.valueOf((char)i));
                        }
                    }
                    catch(Exception ee)
                    {
                        ee.printStackTrace();
                    }
            }
    }
    void saveAsFile()
    {
        fileChooser = new JFileChooser();
             int result = fileChooser.showSaveDialog(jf);
            if(result==0)
            {
                String text=textarea.getText();
                file=fileChooser.getSelectedFile();
                 jf.setTitle(file.getName());
                try(FileOutputStream fos=new FileOutputStream(file))
                {
                    byte[] b=text.getBytes();
                    fos.write(b);
                }
                catch(Exception ee)
                {
                    ee.printStackTrace();
                }
            }
    }
    void newFile()
    {
        String text=textarea.getText();
            if(!text.equals(""))
            {
                int i=JOptionPane.showConfirmDialog(jf,"Do you want to save this file ?");
                if(i==0)
                {
                    saveFile();
                    textarea.setText("");
                    jf.setTitle("*Untitled* - Notepad");
                }
                else if(i==1)
                {
                    textarea.setText("");
                }
            }
    }
     void saveFile()
        {
            String title=jf.getTitle();
            if(title.equals("*Untitled* - Notepad"))
                    {
                        saveAsFile();
                    }
            else
            {
                String text=textarea.getText();
                try(FileOutputStream fos=new FileOutputStream(file))
                {
                    byte[] b=text.getBytes();
                    fos.write(b);
                }
                catch(Exception ee)
                {
                    ee.printStackTrace();
                }
            }
        }
     void openFontFrame()
     {
         font_frame=new JFrame("Choose Frame");
         font_frame.setSize(500,500);
         font_frame.setLocationRelativeTo(jf);
         font_frame.setLayout(null);
         
         String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
         font_family=new JComboBox(fonts);
         font_family.setBounds(50,100,100,30);
         font_frame.add(font_family);
         
         String[] sizes={"10","12","14","18","24","28","34","42","72"};
         font_size=new JComboBox(sizes);
         font_size.setBounds(170,100,100,30);
         font_frame.add(font_size);
         
         String[] styles={"plain","bold","italic"};
         font_style=new JComboBox(styles);
         font_style.setBounds(300,100,100,30);
         font_frame.add(font_style);
         
         ok=new JButton("OK");
         ok.addActionListener(this);
         ok.setBounds(180,200,100,50);
         font_frame.add(ok);
                 
         
         
         font_frame.setVisible(true);
     }

    @Override
    public void windowOpened(WindowEvent e) 
    {

    }

    @Override
    public void windowClosing(WindowEvent e) 
    {
        JOptionPane.showConfirmDialog(jf,"Do u want to save this file");
        saveFile();
    }

    @Override
    public void windowClosed(WindowEvent e) 
    {
        
    }

    @Override
    public void windowIconified(WindowEvent e) 
    {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) 
    {
        
    }

    @Override
    public void windowActivated(WindowEvent e) 
    {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) 
    {
        
    }
}
