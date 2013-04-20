/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package seproject;


import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author Burhan
 */
public class About{
    static JFrame window = new JFrame("About Secure Notes");
    Notepad samp;
    JButton btn;

    public About(Notepad ref)
    {
        samp  = ref;
        Container c = window.getContentPane();
        c.setLayout(new FlowLayout());

        String about ="Secure Note version 1.1";

     // ImageIcon icon = new ImageIcon("author.jpg");
     // JLabel about_label = new JLabel("", icon, SwingConstants.LEFT);
        JLabel about_label = new JLabel("");
        about_label.setText(about);
        about_label.setVerticalTextPosition(SwingConstants.TOP);
        about_label.setIconTextGap(20);
        c.add(about_label);

        int w = 340;
        int h = 250;
        window.setSize(w,h);
        // set window position
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        window.setLocation(center.x-w/2, center.y-h/2+25);//position of 'about' window
        window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        window.setVisible(false);

    }

}
