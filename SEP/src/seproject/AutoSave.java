/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package seproject;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JTextArea;
import java.io.FileWriter;
import java.io.IOException;

class AutoSave extends TimerTask {
    //times member represent calling times.
    private int times = 0;
	 public JTextArea t;
	 String content;
AutoSave(JTextArea t,String content)
 {
 this.t=t;
 this.content=content;
 }
    public void run() {
  		while(times==0)
		  try
        {
            FileWriter fw = new FileWriter("C:/SecureNote/AutoSave.txt");
            fw.write(t.getText());

            fw.close();
        }
        catch(IOException i)
        {

        }

            //Stop Timer.
            this.cancel();


}
}
