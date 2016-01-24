import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class Testing extends JFrame
{
  Vendor[] vendors = {new Vendor("Store Kraft Mfg","123"),new Vendor("KMart","456"),new Vendor("Store Kraft Mfg","789")};
  JComboBox cbo1 = new JComboBox();
  JComboBox cbo2 = new JComboBox(vendors);
  public Testing()
  {
    setLocation(400,300);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    JPanel jp = new JPanel();
    for(int x = 0; x < vendors.length; x++) cbo1.addItem(vendors[x].vendorName);
    jp.add(cbo1);
    jp.add(cbo2);
    getContentPane().add(jp);
    pack();
    cbo1.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
    	  int a = cbo1.getSelectedIndex();
        JOptionPane.showMessageDialog(null,"Vendor ID = "+vendors[cbo1.getSelectedIndex()].vendorID);}});
    cbo2.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        JOptionPane.showMessageDialog(null,"Vendor ID = "+((Vendor)cbo2.getSelectedItem()).vendorID);}});
  }
  public static void main(String args[]){new Testing().setVisible(true);}
}
class Vendor
{
  String vendorName;
  String vendorID;
  public Vendor(String vn, String vi){vendorName = vn; vendorID = vi;}
  public String toString(){return vendorName;}
}