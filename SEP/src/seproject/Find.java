/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package seproject;

/**
 *
 * @author Bharani
 */

import java.awt.GraphicsEnvironment;
import java.awt.Label;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Find extends JFrame implements ActionListener
{
    int startIndex=0;//index to search
    Label l1, l2;//labels for the textboxes
    TextField text_find, text_replace;//text fields, one to find the word, another to replace
    JButton find_btn, find_next, replace, replace_all, cancel;//buttons in the dialogue box

    Notepad samp;//instance of the class Notepad

    public Find(Notepad mynote)
    {
        super("Find / Replace");
        samp = mynote;//initialise samp with mynote, values from 'Notepad mynote'

        l1 = new Label("Find The Word: ");//text in the label1
        l2 = new Label("Replace It With: ");//text in the label2
        text_find = new TextField(30);//number of columns in the textField1
        text_replace = new TextField(30);//number of columns in the textfield2
        find_btn = new JButton("Find");//initialise buttons with their names
        find_next = new JButton("Find Next");
        replace = new JButton("Replace");
        replace_all = new JButton("Replace All");
        cancel = new JButton("Cancel");

        setLayout(null);//set the width and height of the component, not just its x and y position
        int label_w = 80;//width of the label
        int label_h = 20;//height of the label
        int textfield_w    = 120;//textfield width

        l1.setBounds(10,10, label_w, label_h);//position of texts, label1
        add(l1);
        text_find.setBounds(10+label_w, 10, textfield_w, 20);//position of the text field
        add(text_find);
        l2.setBounds(10, 10+label_h+10, label_w, label_h);//position of second label
        add(l2);
        text_replace.setBounds(10+label_w, 10+label_h+10, textfield_w, 20);//position of second text field
        add(text_replace);

        find_btn.setBounds(220, 10, 100, 20);//position of "find" button
        add(find_btn);
        find_btn.addActionListener(this);
        find_next.setBounds(220, 30, 100, 20);//position of "find next" button
        add(find_next);
        find_next.addActionListener(this);
        replace.setBounds(220, 50, 100, 20);//position of "replace" button
        add(replace);
        replace.addActionListener(this);
        replace_all.setBounds(220, 70, 100, 20);//position of "replace all" button
        add(replace_all);
        replace_all.addActionListener(this);
        cancel.setBounds(220, 90, 100, 20);//position of "cancel" button
        add(cancel);
        cancel.addActionListener(this);

        int w = 340;//width of window
        int h = 150;//height of the window

        setSize(w,h);
        // set window position
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();//get the middle point of the screen
        setLocation(center.x-w/2, center.y-h/2);//x and y belong to "centre"
        setVisible(false);//do not make the "find" window appear untill the find option is chosen
    }

    public void actionPerformed(ActionEvent e)
    {
         if(e.getSource()==find_btn)//if "find" button is pressed, find the word
         {
            find();
         }
         else if(e.getSource() == find_next)//if "find_next" button is pressed, find the word where it has next appeared
         {
            find_next();
         }
         else if(e.getSource() == replace)//if "replace" button is pressed, replace the word by another in textbox2
         {
             replace();
         }
         else if(e.getSource() == replace_all)//if "replace_all" button is pressed, replace the word everywhere in the file
         {
            replace_all();
         }
         else if(e.getSource() == cancel)//if "find" button is pressed, close the dialogue box
         {
            this.setVisible(false);//make the dialogue box not visible
         }
    }

    public void find()
    {//variable t is the main text area of the editor
        int select_start = samp.t.getText().indexOf(text_find.getText());//find word from 'text_find' in t
        if(select_start == -1)//if return value is -1, no match found
        {
            startIndex = 0;//make the index to search as 0
            JOptionPane.showMessageDialog(null, "Could not find "+text_find.getText()+"!");// message on dialogue box
            return;
        }
        if(select_start == samp.t.getText().lastIndexOf(text_find.getText()))//last occurrence of a character or substring in t
        { 
            startIndex = 0;//make the index to search as 0
        }

        int select_end = select_start+text_find.getText().length();
        samp.t.select(select_start, select_end);//set the bounds to search
    }


    public void find_next()
    {

        String selection = samp.t.getSelectedText();//get the selected text from t
        try
        {
            boolean exc = selection.equals("");
        }
        catch(NullPointerException e)
        {
            selection = text_find.getText();
            try
            {
                selection.equals("");
            }
            catch(NullPointerException e2)
            {
                selection = JOptionPane.showInputDialog("Find:");
                text_find.setText(selection);
            }
        }
        try
        {
            int select_start = samp.t.getText().indexOf(selection, startIndex);
            int select_end = select_start+selection.length();
            samp.t.select(select_start, select_end);
            startIndex = select_end+1;

            if(select_start == samp.t.getText().lastIndexOf(selection))
            {
                startIndex = 0;
            }
        }
        catch(NullPointerException e)
        {}
    }

    public void replace()
    {
        try
        {
        find();//find the word
        samp.t.replaceSelection(text_replace.getText());//replaceSelection is built-in

        }
        catch(NullPointerException e)
        {
            System.out.print("Null Pointer Exception: "+e);
        }
    }

    public void replace_all()
    {

        samp.t.setText(samp.t.getText().replaceAll(text_find.getText(), text_replace.getText()));
       //replaceAll is built-in
    }
}

