
package seproject;


import java.util.regex.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.*;
import javax.swing.*;

import java.util.Timer;
import java.util.TimerTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import java.util.Locale;
import java.util.*;
import java.awt.*;

import java.awt.Color;
import java.io.BufferedWriter;
import javax.swing.*;

//import javax.speech.Central;
//import javax.speech.synthesis.Synthesizer;
//import javax.speech.synthesis.SynthesizerModeDesc;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;


public class Notepad extends JFrame implements ActionListener, FocusListener
{
   	static Container c;//container to support the java components
    	public static JScrollPane sc;//scroll pane for the window
  	public static JTextArea t = new JTextArea() //text area for the window
 	{
            public void addNotify()
            {
        	super.addNotify();
        	requestFocus();
            }
	};

    	private static JMenuBar menubar;//menu bar for the windiw

    	private JMenu file; //items in the menu bar, file menu
    	private JMenuItem file_new;//items in the menu, for a new file
    	private JMenuItem file_open;//for opening an existing file
    	private JSeparator file_sep1;//division separating new-open and save-saveas options
    	private JMenuItem file_save;//to save
    	private JMenuItem file_save_as;//for 'save as' option
        private JMenuItem file_save_as_Image; // for image encoding or decoding
    	private JSeparator file_sep2;//division separating save-saveas and print options
    	private JMenuItem file_print;//to print
	private JMenuItem file_print_preview;
   	private JMenuItem file_auto;
    	private JSeparator file_sep3;
    	private JMenuItem file_close;//to close
    	private JMenuItem file_exit;//to exit

    	private JMenu edit;//edit menu in the menu bar
    	private JMenuItem edit_undo;//to undo the edition
    	private JMenuItem edit_redo;//to redo the undone edition
    	private JSeparator edit_sep1;//division separating undo-redo and copy-cut-paste-delete options
    	private JMenuItem edit_copy;//to copy string
    	private JMenuItem edit_cut;//to cut string
    	private JMenuItem edit_paste;// to paste string
    	private JMenuItem edit_delete;// to delete string
    	private JSeparator edit_sep2;//division copy-cut-paste-delete and find-findnext-replace options
    	private JMenuItem edit_find;//to find a text
    	private JMenuItem edit_find_next;//to find next occurance of the word
    	private JMenuItem edit_replace;// to replace
    	private JSeparator edit_sep3;//separate find-findnext-replace and selectall-time/date
    	private JMenuItem edit_selectall;//to select all
    	private JMenuItem edit_timedate;// insert time and date

    	private JMenu format;//menu to format the content in the text area
    	private JMenuItem format_font;//format the font
    	private JMenu convert;//options to switch the cases-capital or small letter
    	private JMenuItem str2uppr, str2lwr;//switch to lower or to upper case
    	private JCheckBoxMenuItem format_wordwarp;//enable or disable word wrap

	private JMenu tts;
	private JMenuItem co;

 	private JMenu help;//menu for help
  	private JMenuItem help_about;//option about

        //FOR ENCRYPTION-------------------------
        Steganography stg_obj=new Steganography();
        //----------------------------------------

    	UndoManager undo = new UndoManager();//operations on undoing or redoing the edition
    	UIManager.LookAndFeelInfo lnf[];//for look and feel and obtain various default values

    	Find finder;//instance of 'Find' class
    	FontChooser fc;//instance of 'FontChooser' class
    	About abt;//instance of 'About' class
	String path, content;//path-file name in the folder and content- content of the file

	JLabel lab;
	JTextField text;
	JButton b1;

    	public Notepad()
	{
           	super("Secure Note");//name/heading on the window
        	try//exception handling for the user interface look and feel option
        	{
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        	}
        	catch(Exception ex)
        	{
                    ex.printStackTrace();
                }
        	Container c = getContentPane();//container to add components


        	t.setFont(new Font("Verdana",Font.PLAIN, 12));//default font
		t.addMouseListener(new java.awt.event.MouseAdapter()
		{
    			public void mouseClicked(java.awt.event.MouseEvent evt)
				{
					if (evt.getModifiers()==4)
					{
      				final JPopupMenu jp = new JPopupMenu();
						JButton c=new JButton("cut");
						JButton cc=new JButton("copy");
						JButton p=new JButton("paste");
						JButton d=new JButton("delete");
						JButton u=new JButton("UPPERCASE");
	 	  				JButton l=new JButton("lowercase");
						JButton tt=new JButton("tts");
						JButton re=new JButton("retrieve");

						re.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								try
								{
									connect(t.getSelectedText());
									jp.setVisible(false);
								}
								catch(Exception ex){}
							}
						});

						c.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								edit_cut();
								jp.setVisible(false);
							}
						});
						cc.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								edit_copy();
								jp.setVisible(false);
							}
						});
						p.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								edit_paste();
								jp.setVisible(false);
							}
						});
						d.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								edit_delete();
								jp.setVisible(false);
							}
						});
						u.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								str2uppr();
								jp.setVisible(false);
							}
						});

						l.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								str2lwr();
								jp.setVisible(false);
							}
						});
						tt.addActionListener(new ActionListener()
						{

            			public void actionPerformed(ActionEvent e)
            			{
                			jp.setVisible(false);
								talk();
    	 			       }
       				});
						c.setContentAreaFilled(false);
						cc.setContentAreaFilled(false);
						p.setContentAreaFilled(false);
						d.setContentAreaFilled(false);
						u.setContentAreaFilled(false);
						l.setContentAreaFilled(false);
						tt.setContentAreaFilled(false);
						re.setContentAreaFilled(false);

						jp.add(c);
						jp.add(cc);
						jp.add(p);
						jp.add(d);
						jp.add(u);
						jp.add(l);
						jp.add(re);
						jp.add(tt);

						jp.show((Component)evt.getSource(), evt.getX(), evt.getY());

					}
    			}
			});

		  	//adding scrollbar to text area;
    	   sc = new JScrollPane(t, sc.VERTICAL_SCROLLBAR_AS_NEEDED, sc.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        	c.add(sc);//add components to container
        	menubar = new JMenuBar();//instance of java menu bar

                //---------AutoSave part----------
                Timer timer = new Timer("AutoSave");
		AutoSave as = new AutoSave(t,content);
                timer.schedule(as, 0, 100);

                 //---------------------------------

			text=new JTextField(20);
			text.setBounds(140,40,140,20);

			text.setText("Enter Website name");
			text.setForeground(Color.gray);
			KeyListener keyListener = new KeyListener()
			{
      		public void keyPressed(KeyEvent e)
				{
      			if (e.getKeyCode()==KeyEvent.VK_ENTER)
					{
						try
						{
							String s = text.getText().trim();
							connect(s);
						}
						catch(Exception ae){}
					}
  	    		}
				public void keyReleased(KeyEvent keyEvent)
				{
       			
      		}

      		public void keyTyped(KeyEvent keyEvent)
				{
        			
      		}
			};

			text.addKeyListener(keyListener);
			text.addFocusListener(new FocusListener()
			{
				int i=0;
      		public void focusGained(FocusEvent e)
				{
					if(i==0)
					{
       				text.setText("");
		 				text.setForeground(Color.black);
						i=1;
					}
					else
					{}
      		}

      		public void focusLost(FocusEvent e)
				{
     				// text.setText("Enter Website name");
					//text.setForeground(Color.gray);
      		}
  			});

			b1=new JButton("Retrieve");
			b1.addActionListener(this);


        	file = new JMenu("File");//instance of file menu
        	file_new = new JMenuItem("New");//instance of component-new in the file menu
        	//trigger action through keystrokes
        	file_new.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        	file_new.addActionListener(this);//to male the item listen
        	file.add(file_new);//add it to the file menu
        	file_open = new JMenuItem("Open");//instance of component-open in the file menu
        	file_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        	file_open.addActionListener(this);
        	file.add(file_open);
        	file_sep1 = new JSeparator();//instance of component-a separator in the file menu
        	file.add(file_sep1);
        	file_save = new JMenuItem("Save");//instance of component-save in the file menu
        	file_save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        	file_save.addActionListener(this);
        	file.add(file_save);
        	file_save_as = new JMenuItem("Save As");//instance of component-save_as in the file menu
        	file_save_as.addActionListener(this);
        	file.add(file_save_as);
                file_save_as_Image = new JMenuItem("Save As Image");//instance of component-save_as in the file menu
                file_save_as_Image.addActionListener(this);
                file.add(file_save_as_Image);
        	file_sep2 = new JSeparator();//instance of component-a separator in the file menu
        	file.add(file_sep2);
        	file_print = new JMenuItem("Print");//instance of component-print in the file menu
        	file_print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        	file_print.addActionListener(this);
        	file.add(file_print);
       	file_print_preview = new JMenuItem("Print Preview");
       	file_print_preview.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
        	file_print_preview.addActionListener(this);
        	file.add(file_print_preview);
		  	file_sep3 = new JSeparator();
        	file.add(file_sep3);
		  	file_auto = new JMenuItem("Auto");//instance of component-save_as in the file menu
        	file_auto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));

		  	file_auto.addActionListener(this);
		  	file.add(file_auto);
    	   file_close = new JMenuItem("Close");//instance of component-close in the file menu
        	file_close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.CTRL_MASK));
        	file_close.addActionListener(this);
        	file.add(file_close);
        	file_exit = new JMenuItem("Exit");//instance of component-exit in the file menu
        	file_exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
        	file_exit.addActionListener(this);
        	file.add(file_exit);
        	menubar.add(file);//add file menu to the menu bar



        	edit = new JMenu("Edit");
        	edit_undo = new JMenuItem("Undo");
        	edit_undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        	edit_undo.addActionListener(this);
        	edit.add(edit_undo);
        	edit_redo = new JMenuItem("Redo");
        	edit_redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
        	edit_redo.addActionListener(this);
        	edit.add(edit_redo);
        	edit_sep1 = new JSeparator();
        	edit.add(edit_sep1);
        	edit_copy = new JMenuItem("Copy");
        	edit_copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        	edit_copy.addActionListener(this);
        	edit.add(edit_copy);
        	edit_cut = new JMenuItem("Cut");
        	edit_cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        	edit_cut.addActionListener(this);
        	edit.add(edit_cut);
        	edit_paste = new JMenuItem("Paste");
        	edit_paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        	edit_paste.addActionListener(this);
        	edit.add(edit_paste);
        	edit_delete = new JMenuItem("Delete");
        	edit_delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        	edit_delete.addActionListener(this);
        	edit.add(edit_delete);
        	edit_sep2 = new JSeparator();
        	edit.add(edit_sep2);
        	edit_find = new JMenuItem("Find");
        	edit_find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
        	edit_find.addActionListener(this);
        	edit.add(edit_find);
        	edit_find_next = new JMenuItem("Find Next");
        	edit_find_next.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
        	edit_find_next.addActionListener(this);
        	edit.add(edit_find_next);
        	edit_replace = new JMenuItem("Replace");
        	edit_replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        	edit_replace.addActionListener(this);
        	edit.add(edit_replace);
        	edit_sep3 = new JSeparator();
        	edit.add(edit_sep3);
        	edit_selectall = new JMenuItem("Select All");
        	edit_selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        	edit_selectall.addActionListener(this);
        	edit.add(edit_selectall);
        	edit_timedate = new JMenuItem("Time/Date");
        	edit_timedate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        	edit_timedate.addActionListener(this);
        	edit.add(edit_timedate);
        	menubar.add(edit);

        	format = new JMenu("Format");
        	format_font = new JMenuItem("Font");
        	format_font.addActionListener(this);
        	format.add(format_font);

        	convert = new JMenu("Convert");//'convert' menu in the 'edit' menu
        	str2uppr = new JMenuItem("To Uppercase...");
        	str2uppr.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.CTRL_MASK));
        	str2uppr.addActionListener(this);
        	convert.add(str2uppr);
        	str2lwr = new JMenuItem("To Lowercase...");
        	str2lwr.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.CTRL_MASK));
        	str2lwr.addActionListener(this);
        	convert.add(str2lwr);
        	format.add(convert);

        	format_wordwarp = new JCheckBoxMenuItem("Word Wrap");
        	format_wordwarp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
        	format_wordwarp.addActionListener(this);
        	format.add(format_wordwarp);
        	menubar.add(format);


 			tts = new JMenu("TTS");
 			co = new JMenuItem("Convert");
 			co.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));
 			co.addActionListener(this);
 			tts.add(co);
 			menubar.add(tts);

	     	help = new JMenu("Help");
      	help_about = new JMenuItem("About");
        	help_about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        	help_about.addActionListener(this);
        	help.add(help_about);
        	menubar.add(help);

			JToolBar toolbar = new JToolBar();
			JButton t1 = new JButton();
			//JButton t2 = new JButton();
			t1.setIcon(new ImageIcon("save.jpg"));
			//t2.setIcon(new ImageIcon("save.jpg"));

			toolbar.add(t1);
			toolbar.addSeparator(  );
		//	toolbar.add(t2);
			//toolbar.addSeparator(  );
			toolbar.add(text);
			toolbar.addSeparator(  );
			toolbar.add(b1);

		 	this.setJMenuBar(menubar);
		  	c.add(toolbar,BorderLayout.NORTH);
		  	setLocationRelativeTo(menubar);

		   	     // undo manager
        	Document doc= t.getDocument();//container for text, for swing component operations

        	doc.addUndoableEditListener(
                new UndoableEditListener( )
                {
                    public void undoableEditHappened( UndoableEditEvent event )
                    {
         //Returns an UndoableEdit edit that occurred and has information about and commands to undo or redo the edit.
                        undo.addEdit(event.getEdit());
                    }
                }
        );

        // find_window
        finder = new Find(this);//initialise the instance of 'Find' class
        finder.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        // font chooser
        fc = new FontChooser(this);//initialise the instance of 'FontChooser' class
        abt = new About(this);//initialise the instance of 'About' class

        // set window size
        int w = 600;
        int h = 550;
        setSize(600, 550);
        // set window position
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();//find centre of the screen
        setLocation(center.x-w/2, center.y-h/2);
        this.setVisible(true);//show the container's components
        path = "";
        content="";
    }

    public void actionPerformed(ActionEvent e)
	 {
        	//set action to be performed when an option is chosen
        	if(e.getSource()==file_new)
            file_new();
        	else if(e.getSource()==file_open)
            file_open();
        	else if(e.getSource()==file_save)
            file_save();
        	else if(e.getSource()==file_save_as)
            file_save_as();
                 else if(e.getSource()==file_save_as_Image)
            file_save_as_Image();
       	else if(e.getSource()==file_print)
            file_print();
		   else if(e.getSource()==file_print_preview)
            file_print_preview();
			else if(e.getSource()==file_auto)
            file_auto();
        	else if(e.getSource()==file_close)
            file_close();
        	else if(e.getSource()==file_exit)
            file_exit();

        	else if(e.getSource()==edit_undo)
            edit_undo();
        	else if(e.getSource()==edit_redo)
            edit_redo();
        	else if(e.getSource()==edit_cut)
            edit_cut();
        	else if(e.getSource()==edit_copy)
            edit_copy();
        	else if(e.getSource()==edit_paste)
            edit_paste();
        	else if(e.getSource()==edit_delete)
            edit_delete();
        	else if(e.getSource()==edit_find)
            edit_find();
        	else if(e.getSource()==edit_find_next)
            edit_find_next();
        	else if(e.getSource()==edit_replace)
            edit_replace();
        	else if(e.getSource()==edit_selectall)
            edit_selectall();
        	else if(e.getSource()==edit_timedate)
            edit_timedate();

        	else if(e.getSource()==format_font)
            format_font();
        	else if(e.getSource()==str2uppr)
            str2uppr();
        	else if(e.getSource()==str2lwr)
            str2lwr();
        	else if(e.getSource()==format_wordwarp)
            format_wordwarp();

			else if(e.getSource()==co)
				talk();
			else if(e.getSource()==b1)
			{
				try
				{
					String s = text.getText().trim();
					//System.out.println(s);
					connect(s);
				}
				catch(Exception ae){}
			}

        	else if(e.getSource()==help_about)
            help_about();
    	}

		public void focusGained(FocusEvent e)
		{
        	text.setText("");
		  	text.setForeground(Color.black);
		}
	 	public void focusLost(FocusEvent fe)
		{
    		System.out.println("");
		}

		public static void connect(String n) throws Exception
		{
			try
			{
 				String a=new String();
 				a=n;
 				URL url = new URL(n);
 				URLConnection spoof = url.openConnection();

 				BufferedReader in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));
 				String strLine = "";
				t.setText("");
				while ((strLine = in.readLine()) != null)
				{
					try
					{
						t.append(strLine+"\n");
					}
					catch (Exception e)
					{
 		   			System.err.println("Error: " + e.getMessage());
					}
   			}

 				JOptionPane.showMessageDialog(null, "Retrieved");
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "No Connection");
			}
		}

 		public void talk()
		{
/*			try
 			{
    			System.setProperty("freetts.voices","com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
     			Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

   			String s = t.getSelectedText();
				if(s!=null)
				{
                                Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
   				synthesizer.allocate();
   				synthesizer.resume();
				synthesizer.speakPlainText(s, null);
 	  			synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
                                }
	  			else
	  	  			JOptionPane.showMessageDialog(null, "Select some text");
			}
   		catch(Exception e)
   		{
     			e.printStackTrace();
   		}*/
		}



        
    	public void file_new()
		{
        //if the file is empty or it is not modified
        	if(t.getText().equals("") || t.getText().equals(content))
        	{
            t.setText("");
            content = "";
            path = "";
            setTitle("untitled - Secure Notes");
        	}
        	else //if file is modified
        	{
            int a = JOptionPane.showConfirmDialog(null, "The file has been edited\nDo you want to save the changes?");
            if(a==0)//if 'yes' is pressed
            {
                file_save();

                t.setText("");
                path = "";
                setTitle("untitled - Secure Notes");
            }
                else if(a==1)//if 'no' is pressed
            {
                t.setText("");
                path = "";
                setTitle("untitled - Secure Notes");
            }
            else if(a==2)//if 'cancel' is pressed
                return;
        	}
    	}

    public void file_open()
    {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int r=fc.showOpenDialog(this);
        if(r==fc.CANCEL_OPTION)
            return;
        File myfile = fc.getSelectedFile();
        if(myfile == null || myfile.getName().equals(""))
        {
            
            JOptionPane.showMessageDialog(this, "Select a file!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String file_ext= myfile.getName();
        CharSequence png = "png";
        CharSequence txt = "txt";
        CharSequence img_tester = "@@$$~";
        try
        {
        if(file_ext.contains(png))
        {

            int x=myfile.getName().length();
            String path_temp= myfile.getAbsolutePath().replace(myfile.getName(),"");
            String temp = stg_obj.decode(path_temp,myfile.getName().replace(".png",""));
            if(temp.contains(img_tester))
            {
                t.setText(temp.toString().replace(img_tester,""));
                content = t.getText();
                path = myfile.toString();
                setTitle(myfile.getName()+" - SecureNote");
            }
            else
            {
                //If a image is not encrypted , i.e., when a user opens invalid image
            JOptionPane.showMessageDialog(null, "Invalid Image opened  "); 
            }
        }
        else
        {
                BufferedReader input = new BufferedReader(new FileReader(myfile));
                StringBuffer str = new StringBuffer();
                String line;
                while((line = input.readLine()) != null)
                    str.append(line+"\n");
                t.setText(crypter(str.toString()));
                setTitle(myfile.getName() + "- SecureNote");
        }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "File not found: "); //changed here!!!

        }

    }

    public void file_save()
    {

        if(path.equals(""))//file not named
        {
            file_save_as();
            return;
        }
        try
        {
            FileWriter fw = new FileWriter(path);
            fw.write(crypter(t.getText()));//write into file
            content = crypter(t.getText());
            fw.close();
        }
        catch(IOException i)
        {
            JOptionPane.showMessageDialog(this,"Failed to save the file","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void file_save_as(){
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int r = fc.showSaveDialog(this);
        if(r==fc.CANCEL_OPTION)
            return;
        File myfile = fc.getSelectedFile();
        if(myfile==null || myfile.getName().equals(""))
        {
            JOptionPane.showMessageDialog(this,"Please enter a file name!","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(myfile.exists())
        {
            r = JOptionPane.showConfirmDialog(this, "A file with same name already exists!\nAre you sure want to overwrite?");
            if(r != 0)
                return;
        }

        try
        {
            
            FileWriter fw = new FileWriter(myfile);
            fw.write(crypter(t.getText()));
            content = crypter(t.getText());
            setTitle(myfile.getName()+" - Secure Notes");
            fw.close();
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(this,"Failed to save the file","Error",JOptionPane.ERROR_MESSAGE);
        }
    }


    public void file_save_as_Image()
    {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int r = fc.showSaveDialog(this);
        if(r==fc.CANCEL_OPTION)
            return;
        File myfile = fc.getSelectedFile();
        if(myfile==null || myfile.getName().equals(""))
        {
            JOptionPane.showMessageDialog(this,"Please enter a file name!","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(myfile.exists())
        {
            r = JOptionPane.showConfirmDialog(this, "A file with same name already exists!\nAre you sure want to overwrite?");
            if(r != 0)
                return;
        }
        String fp = myfile.getAbsolutePath().replace(myfile.getName(),"");
        String temp = myfile.getName().replace(".png","");
        temp = temp.replace(".jpg","");
        temp = temp.replace(".jpeg","");
        System.out.println(temp);
        stg_obj.encode(fp, "note","png",temp,t.getText()+"@@$$~");
        setTitle(myfile.getName()+" - SecureNote");
                                    //@@$$~ represents software steg protocol
    }

     public String crypter(String j)
    {
        int a;
       ArrayList temp = new ArrayList();
        for(a=0;a<j.length();a++)
        {
            if(((int)j.charAt(a))>=65 &&((int)j.charAt(a)) <=122)
               temp.add((char)(122-(((int)j.charAt(a))%65)));

        }
        String listString = "";

        for (Object s : temp)
        {
            listString = listString + s ;
        }
        return listString;
    }



    	public void file_print()
		{
        	PrinterJob printer = PrinterJob.getPrinterJob();
        	HashPrintRequestAttributeSet printAttr = new HashPrintRequestAttributeSet();
        	if(printer.printDialog(printAttr))     // Display print dialog
        	{            // If true is returned...
            try
            {
                printer.print(printAttr);    // then print
            }
            catch(PrinterException e)
            {
                JOptionPane.showMessageDialog(this,"Failed to print the file: "+e,"Error",JOptionPane.ERROR_MESSAGE);
            }
        	}
    	}

	  	public void file_print_preview()
		{
	 	  	new File("C:/SecureNote").mkdir();
		  	File file = new File("C:/SecureNote/testing.txt");
	  		try
	  		{
		  		FileWriter fw = new FileWriter(file);
            fw.write(t.getText());
            content = t.getText();
           // setTitle(file.getName()+" - Notepad");
            fw.close();
			}
	   	 catch(IOException e)
         {
            JOptionPane.showMessageDialog(this,"Failed to save the file","Error",JOptionPane.ERROR_MESSAGE);
         }
 		new TestPreview();
	}
	public void file_auto()
	{
		try
		{
	   	spellingcrt s=new spellingcrt("C:/Users/Nikhil/Desktop/Dictionary.txt",t);
	   	s.work();
	  	}
		catch(IOException e)
		{
  			e.printStackTrace();
		}
	}
   public void file_close()
	{
		if(t.getText().equals("") || t.getText().equals(content))
		{//if nt modified or not used
			t.setText("");
         path = "";
         setTitle("untitled - Secure Notes");
      }
      else
      {
      	int a = JOptionPane.showConfirmDialog(null, "The text has been changed\nDo you want to save the changes?");
         if(a==0)
         	file_save();
         else if(a==1)
         {
            t.setText("");
            path = "";
            setTitle("untitled -Secure Notes");
         }
         else if(a==2)
            return;
       }
   }

   public void file_exit()
	{

			if(t.getText().equals("") || t.getText().equals(content))
            System.exit(0);
       	else
        	{
            int b = JOptionPane.showConfirmDialog(null, "The text has been changed.\nDo you want to save the changes?");

            if(b==0)
                    file_save();
            else if(b==1)
                    System.exit(0);
            else if(b==2)
                    return;
        	}
   }

	public void edit_undo()
	{
   	if( undo.canUndo())
      {
            try
            {
                undo.undo();
            }
            catch(CannotUndoException e)
            {
            }
       }
   }

   public void edit_redo()
	{
        if( undo.canRedo())
        {
            try
            {
                undo.redo();
            }
            catch(CannotRedoException e)
            {
            }
        }
   }

   public void edit_cut()
	{
        t.cut();
   }

   public void edit_copy()
	{
        t.copy();
   }

   public void edit_paste()
	{
        t.paste();
   }

   public void edit_delete()
	{
        String temp = t.getText();
        t.setText(temp.substring(0, t.getSelectionStart())+temp.substring(t.getSelectionEnd()));
   }

   public void edit_find()
	{
        finder.setVisible(true);
   }

   public void edit_find_next()
	{
        finder.find_next();
   }

   public void edit_replace()
	{
        finder.setVisible(true);
   }

   public void edit_selectall()
	{
        t.selectAll();
   }

   public void edit_timedate()
	{

       try
       {
        	int start = t.getSelectionStart();
        	int end   = t.getSelectionEnd();
        	Calendar cal = Calendar.getInstance();
        	SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy h:m a");
        	String now = sdf.format(cal.getTime());
        	String temp1 = t.getText().substring(0,start);
        	String temp2 = t.getText().substring(end);
        	t.setText(temp1+" "+now+" "+temp2);
        	t.select(start+1, start+1+now.length());
       }
       catch(NullPointerException e){}
   }

   public void format_font()
	{
        fc.window.setVisible(true);
   }

   public void str2uppr()
	{
        try
        {
        		int start = t.getSelectionStart();//beggining of the selected text
        		int end   = t.getSelectionEnd();//end of the selected text
        		String temp1 = t.getText().substring(0,start);//beginning of the file till selected text
        		String temp2 = t.getText().substring(end);//end of the selected text to end of the file
        		String conv  = t.getSelectedText().toUpperCase();
       	 	t.setText(temp1+conv+temp2);
        		t.select(start, end);
        }
        catch(NullPointerException e){}
   }

   public void str2lwr()
   {
        try
        {
        		int start = t.getSelectionStart();
        		int end   = t.getSelectionEnd();
        		String temp1 = t.getText().substring(0,start);
        		String temp2 = t.getText().substring(end);
        		String conv  = t.getSelectedText().toLowerCase();
        		t.setText(temp1+conv+temp2);
        		t.select(start, end);
        }
        catch(NullPointerException e){}
   }

   public void format_wordwarp()
	{
        if(t.getLineWrap()==false)
            t.setLineWrap(true);
        else
            t.setLineWrap(false);
   }

   public void help_about()
	{
        abt.window.setVisible(true);
   }
}

class spellingcrt
{

	private final HashMap<String, Integer> nWords = new HashMap<String, Integer>();
	public JTextArea t,t2;
   public spellingcrt(String file,JTextArea t) throws IOException
	{
     	BufferedReader in = new BufferedReader(new FileReader(file));
      Pattern p = Pattern.compile("\\w+");
		this.t=t;
      for(String temp = ""; temp != null; temp = in.readLine())
		{
         Matcher m = p.matcher(temp.toLowerCase());
         while(m.find())
					nWords.put((temp = m.group()), nWords.containsKey(temp) ? nWords.get(temp) + 1 : 1);
      }
      in.close();
   }

   private final ArrayList<String> edits(String word)
	{
      ArrayList<String> result = new ArrayList<String>();
      for(int i=0; i < word.length(); ++i)
				result.add(word.substring(0, i) + word.substring(i+1));
      for(int i=0; i < word.length()-1; ++i)
				result.add(word.substring(0, i) + word.substring(i+1, i+2) + word.substring(i, i+1) + word.substring(i+2));
      for(int i=0; i < word.length(); ++i)
			for(char c='a'; c <= 'z'; ++c)
				result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i+1));
      for(int i=0; i <= word.length(); ++i)
			for(char c='a'; c <= 'z'; ++c)
				result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i));
      return result;
   }

   public final String correct(String word)
	{
      if(nWords.containsKey(word))
			return word;
      ArrayList<String> list = edits(word);
      HashMap<Integer, String> candidates = new HashMap<Integer, String>();
      for(String s : list)
			if(nWords.containsKey(s))
				candidates.put(nWords.get(s),s);
      if(candidates.size() > 0)
			return candidates.get(Collections.max(candidates.keySet()));
      for(String s : list)
			for(String w : edits(s))
				if(nWords.containsKey(w))
					candidates.put(nWords.get(w),w);
      return candidates.size() > 0 ? candidates.get(Collections.max(candidates.keySet())) : word;
   }

	public void work()
	{

		String content2="";
		String content1=t.getText();
	   String input = content1;
      Pattern p = Pattern.compile("[\\w']+");
      Matcher m = p.matcher(input);

     	while ( m.find() )
		{
			String str=input.substring(m.start(), m.end());
			String k=correct(str);
			content2=content2.concat(" ");
			content2=content2.concat(k);
			t.setText(content2);

		}
	}


}
