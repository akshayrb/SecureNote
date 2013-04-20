
package seproject;

import javax.swing.JFrame;

public class Main {
    public static void main(String args[]){

        Notepad mynote = new Notepad();//create an object of the class Notepad
        mynote.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Exit from the app when 'close' is pressed
    }
}
