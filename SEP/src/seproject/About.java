
package seproject;


import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class About{
    static JFrame window = new JFrame("About Secure Notes");
    Notepad samp;
    JButton btn;

    public About(Notepad ref)
    {
        samp  = ref;
        Container c = window.getContentPane();
        c.setLayout(new FlowLayout());

        String about ="SecureNote";
        ImageIcon icon = new ImageIcon("C://about.png");

        JLabel about_label = new JLabel("", icon, SwingConstants.LEFT);
     // JLabel about_label = new JLabel("");

        about_label.setText(about);

        about_label.setVerticalTextPosition(SwingConstants.TOP);
        about_label.setIconTextGap(20);

        JLabel version = new JLabel("");
        version.setText("version : 1.1");
        version.setVerticalTextPosition(SwingConstants.TOP);

        JLabel vendor = new JLabel("");
        vendor.setText("Team MASHUP");
        vendor.setVerticalTextPosition(SwingConstants.TOP);


        //JLabel version = new

        c.add(about_label);
        c.add(version);
        c.add(vendor);


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
