import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;


public class loginScreen extends JFrame {

	private JPanel contentPane;
	private JPasswordField txtPassword;
	private JTextField txtUsername;
	static String usernameDB;
	static String passwordDB;
	static loginScreen frame = new loginScreen();
	int failLogin = 1;
	clientScreen csFrame = new clientScreen();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
					
					Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Momar\\Documents\\ProjetSmithGarageBD.mdb");

					Statement st = con.createStatement();
					String requete = "select * from tblUtilisateurs";
					ResultSet rs = st.executeQuery(requete);
					
					while (rs.next())
					{
						 usernameDB = rs.getString("Username");
						 passwordDB = rs.getString("Password");
						
					}
					

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public loginScreen() {
		setTitle("LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 329, 216);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(usernameDB.equals(txtUsername.getText()))
				{
					frame.setVisible(false);
					csFrame.setVisible(true);
					failLogin =0;
				}
				else
				{
					if(failLogin == 3)
					{
						System.exit(0);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Mauvais username/Password, il vous reste " + (3-failLogin) + " essai(s)", "",JOptionPane.INFORMATION_MESSAGE);
				    }
						failLogin ++;	
					
				}
			}
		});
		btnNewButton.setBounds(51, 145, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("CANCEL");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
		
			}
		});
		btnNewButton_1.setBounds(165, 145, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setBounds(25, 51, 89, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(25, 85, 89, 23);
		contentPane.add(lblPassword);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(93, 86, 161, 22);
		contentPane.add(txtPassword);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(93, 52, 161, 23);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
	}
}
