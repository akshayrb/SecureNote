/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package seproject;


import javax.swing.*;
import java.awt.event.*;
import java.awt.print.*;
import java.text.*;

class TestPreview extends JFrame implements ActionListener
{
PrinterJob pj = PrinterJob.getPrinterJob();
javax.print.attribute.HashPrintRequestAttributeSet att =
new javax.print.attribute.HashPrintRequestAttributeSet();
JEditorPane tp = null;
JTable tab = null;

public TestPreview() {
super("Print Preview");
JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
sp.setTopComponent(createTextPane());

java.awt.Dimension d = this.getToolkit().getScreenSize();
this.setSize(d.width/2, d.height);
this.getContentPane().add(sp);
this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
this.setVisible(true);
sp.setDividerLocation(0.5);
this.validate();
}
private JPanel createTextPane() {
tp = new JEditorPane("text/html","");
tp.setEditable(false);
try {tp.setPage(new java.io.File("C:/SecureNote/testing.txt").toURI().toURL()); } catch(Exception ex) {}
JButton ps = new JButton("Page Setup"), b = new JButton("Preview Text");
ps.addActionListener(this);
b.addActionListener(this);
JPanel p = new JPanel(new java.awt.BorderLayout()), top = new JPanel(new java.awt.FlowLayout());
top.add(ps);
top.add(b);
p.add(top, "North");
p.add(new JScrollPane(tp), "Center");
return p;
}

public void actionPerformed(ActionEvent ae) {
String com = ae.getActionCommand();
if(com.equals("Page Setup"))
pj.pageDialog(att);
else if(com.equals("Preview Text"))
new PrintPreview(tp.getPrintable(new MessageFormat("{0}"),
new MessageFormat("{0}")), pj.getPageFormat(att));
else if(com.equals("Preview Table"))
new PrintPreview(tab.getPrintable(javax.swing.JTable.PrintMode.FIT_WIDTH,
new MessageFormat("{0}"), new MessageFormat("{0}")), pj.getPageFormat(att));
}
public static void main(String arg[]) {
new TestPreview();
}
}