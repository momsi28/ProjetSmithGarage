import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.JFormattedTextField;
import javax.swing.ListSelectionModel;

import org.hsqldb.jdbc.JDBCPreparedStatement;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import Utils.ETLFunctions;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class clientScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNom;
	private JTextField txtPrenom;
	private JTextArea txtInfo;
	JButton btnAjouter;
	JButton btnModifier;
	JButton btnEnregistrer;
	JButton btnAnnuler;
	JButton btnSupprimer;
	JButton btnAddCar;
	JButton btnDeleteCar;
	JButton btnReparations;
	JButton btnFirst;
	JButton btnLast;
	JButton btnPrevious;
	JButton btnNext;
	
	private static boolean listeNulle = true;
	int position = 0;
	private JTable table;
	JPanel pNavigation ;
	private JFormattedTextField txtPhone;
	JLabel lblClientID = new JLabel();
	DefaultTableModel model = new DefaultTableModel();
	private static ArrayList<Integer> listeCarID = new ArrayList<Integer>();
	private String typeRequete;
	private String typeSubRequete;

	ETLFunctions etlfunc = new ETLFunctions();
	private JLabel lblVoitures;
	private JButton btnRV;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					clientScreen frame = new clientScreen();
					frame.setVisible(true);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public static ArrayList<Clients> BindList()
	{
		try {
			Connection con = ETLFunctions.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from tblClients");
			ArrayList<Clients> listeClients = new ArrayList<Clients>();
			
			while(rs.next())
			{
				Clients clients = new Clients(rs.getInt("ClientID"), 
												rs.getString("Nom"), 
												rs.getString("Prenom"), 
												rs.getString("Telephone"), 
												rs.getString("Information"));
				listeClients.add(clients);
				
			}
			return listeClients;			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public boolean validateInsert()
	{
		if(txtPhone.getText().equalsIgnoreCase("___-___-____"))
		{	
			return false;
		}
	else if(txtPrenom.getText().equalsIgnoreCase("") && txtNom.getText().equalsIgnoreCase(""))
		return false;
	else
		return true;
		
	}
	
	public static ArrayList<Voitures> ListeCar(int clientID)
	{
		try {
			Connection con = ETLFunctions.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from tblVoitures WHERE clientID = " + clientID);
			ArrayList<Voitures> listeVoitures = new ArrayList<Voitures>();
			listeCarID.clear();
			
			while(rs.next())
			{
				Voitures voitures = new Voitures(rs.getInt("VoitureID"), 
												rs.getString("Marque"), 
												rs.getString("Modele"), 
												rs.getString("Annee"),
												clientID); 
				listeVoitures.add(voitures);
				listeCarID.add(rs.getInt("VoitureID"));
				listeNulle = false;
			}
			return listeVoitures;			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public boolean validateUniqueClient()
	{
		try {
		Connection con = ETLFunctions.getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from tblClients WHERE TELEPHONE= '"+ txtPhone.getText()+ "'");
		if (rs.next())
		{
			return false;
		}
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public void PositionInfo(int index)
	{

			ArrayList<Clients> listeClients = BindList();	
			if(!listeClients.isEmpty())
			{
				lblClientID.setText(String.valueOf(listeClients.get(index).getId()));
				txtNom.setText(listeClients.get(index).getNom());
				txtPrenom.setText(listeClients.get(index).getPrenom());
				txtPhone.setText(listeClients.get(index).getPhone());
				txtInfo.setText(listeClients.get(index).getInformation());
				
				ArrayList<Voitures> listeVoitures = ListeCar(Integer.parseInt(lblClientID.getText()));
				if(!listeClients.isEmpty())
				{
					//So it won't add up 
					model.setRowCount(0);
					
					for(int  i = 0; i < listeVoitures.size(); i++)
					{
						int numCols = table.getModel().getColumnCount();
						Object [] car = new Object[numCols];
						car[0] = listeVoitures.get(i).getMarque();
						car[1] =  listeVoitures.get(i).getModele();
						car[2] = listeVoitures.get(i).getAnnee();
						
						model.addRow(car);
					}

			}	
		}
	}
	
	
	public void Initialize()
	{
		txtPhone.setEditable(false);
		txtNom.setEditable(false);
		txtPrenom.setEditable(false);
		txtInfo.setEditable(false);
		
		btnAjouter.setEnabled(true);
		btnModifier.setEnabled(true);
		btnSupprimer.setEnabled(true);
		btnRV.setEnabled(true);
		btnEnregistrer.setEnabled(false);
		btnAnnuler.setEnabled(true);
		btnReparations.setEnabled(true);
		btnAddCar.setEnabled(false);
		btnDeleteCar.setEnabled(false);
		 table.setEnabled(false);
		listeNulle = true;
	}
	/**
	 * Create the frame.
	 */
	public clientScreen() {


		setTitle("CLIENTS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 744, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "CLIENTS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 30, 641, 256);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("No T\u00E9l:");
		label.setFont(new Font("Times New Roman", Font.BOLD, 14));
		label.setBounds(15, 54, 46, 14);
		panel.add(label);
		
		txtNom = new JTextField();
		txtNom.setEditable(false);
		txtNom.setColumns(10);
		txtNom.setBounds(97, 95, 175, 29);
		panel.add(txtNom);
		
		JLabel label_1 = new JLabel("Nom:");
		label_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		label_1.setBounds(28, 101, 33, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Pr\u00E9nom:");
		label_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		label_2.setBounds(10, 157, 61, 14);
		panel.add(label_2);
		
		txtPrenom = new JTextField();
		txtPrenom.setEditable(false);
		txtPrenom.setColumns(10);
		txtPrenom.setBounds(97, 150, 175, 29);
		panel.add(txtPrenom);
		
		txtInfo = new JTextArea();
		txtInfo.setEditable(false);
		txtInfo.setBounds(359, 82, 255, 100);
		panel.add(txtInfo);
		
		JLabel lblInformationAdditionnelle = new JLabel("Information additionnelle:");
		lblInformationAdditionnelle.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblInformationAdditionnelle.setBounds(359, 54, 198, 14);
		panel.add(lblInformationAdditionnelle);
		
		MaskFormatter mf1;
		try {
			mf1 = new MaskFormatter("###-###-####");
			mf1.setPlaceholderCharacter('_');
			txtPhone = new JFormattedTextField(mf1);
			txtPhone.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent keyEv) {
				
					if(keyEv.getKeyCode() == KeyEvent.VK_ENTER)
					{
						if(!(ETLFunctions.validateUniqueClient(txtPhone.getText())))
						{
							JOptionPane.showMessageDialog(null, "Ce numero appartient déjà à un aure Client!");
							txtPhone.setText("");
							txtPhone.requestFocus();
						}
					}
				}
			});
			txtPhone.setEditable(false);
			txtPhone.setBounds(97, 50, 175, 29);
			panel.add(txtPhone);
			
			lblClientID = new JLabel("New label");
			lblClientID.setVisible(false);
			lblClientID.setFont(new Font("Times New Roman", Font.BOLD, 16));
			lblClientID.setBounds(554, 13, 75, 28);
			panel.add(lblClientID);
			
			btnAjouter = new JButton("Ajouter");
			btnAjouter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					//Clean tout les fields
					txtInfo.setText("");
					txtNom.setText("");
					txtPrenom.setText("");
					txtPhone.setText("");
					txtPhone.requestFocus();
					txtInfo.setEditable(true);
					txtNom.setEditable(true);
					txtPrenom.setEditable(true);
					txtPhone.setEditable(true);		

					btnModifier.setEnabled(false);
					btnAjouter.setEnabled(false);
					btnSupprimer.setEnabled(false);
					btnReparations.setEnabled(false);
					btnEnregistrer.setEnabled(true);
					btnRV.setEnabled(false);
//					btnFirst.setEnabled(false);
//					btnPrevious.setEnabled(false);
//					btnLast.setEnabled(false);
//					btnNext.setEnabled(false);
					
					btnAddCar.setEnabled(true);
					btnDeleteCar.setEnabled(true);
					table.setEnabled(true);
					typeRequete = "INSERT";

					model.setRowCount(0);
	
					
				}
			});
			btnAjouter.setBounds(146, 218, 97, 25);
			panel.add(btnAjouter);
			
			btnModifier = new JButton("Modifier");
			btnModifier.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					//Mettre les textField en mode editable
					txtInfo.setEditable(true);
					txtNom.setEditable(true);
					txtPrenom.setEditable(true);
					txtPhone.setEditable(true);		
					btnEnregistrer.setEnabled(true);
					btnAddCar.setEnabled(true);
					btnDeleteCar.setEnabled(true);
					
					table.setEnabled(true);
					typeRequete = "UPDATE";
				}
			});
			btnModifier.setBounds(274, 218, 97, 25);
			panel.add(btnModifier);
			
			btnSupprimer = new JButton("Supprimer");
			btnSupprimer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					typeRequete = "DELETE";
					int dialogResult = JOptionPane.showConfirmDialog (null, "Voulez vous supprimer ce client?","Warning",JOptionPane.YES_NO_OPTION);
					if(dialogResult == JOptionPane.YES_OPTION){
						try {
							Connection con = ETLFunctions.getConnection();
							PreparedStatement ps = con.prepareStatement("DELETE FROM TBLCLIENTS WHERE CLIENTID=?");
							ps.setString(1,lblClientID.getText());
							ps.execute();
							con.close();
							position = BindList().size()-1;
							PositionInfo(position);
					
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
					Initialize();
				}
			});
			btnSupprimer.setBounds(394, 218, 97, 25);
			panel.add(btnSupprimer);
			panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtPhone, txtNom, txtPrenom, txtInfo, label_1, label, label_2, lblInformationAdditionnelle, lblClientID, btnAjouter, btnModifier, btnSupprimer}));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		pNavigation = new JPanel();
		pNavigation.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pNavigation.setBounds(17, 561, 634, 47);
		contentPane.add(pNavigation);
		pNavigation.setLayout(null);
		
		btnFirst = new JButton("<<");
		btnFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Initialize();
				
				position = 0;
				PositionInfo(position);
			}
		});
		btnFirst.setBounds(116, 13, 60, 25);
		pNavigation.add(btnFirst);
		
		btnPrevious = new JButton("<");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				Initialize();
				
				position--;
				if (position > 0)
				{
					PositionInfo(position);
				}
				else if (position == 0)
				{
					position = 0;
					PositionInfo(position);
					
				}
				else
				{
					position = 0;
					JOptionPane.showMessageDialog(null, "Début de la liste");
				}
			}
		});
		btnPrevious.setBounds(188, 13, 60, 25);
		pNavigation.add(btnPrevious);
		
		btnNext = new JButton(">");
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				Initialize();
				
				position ++;
				if(position <BindList().size())
				{
					PositionInfo(position);
				}
				else
				{
					position = BindList().size()-1;
					PositionInfo(position);
					JOptionPane.showMessageDialog(null,"Fin de la liste des clients");
				}
			}
		});
		btnNext.setBounds(404, 13, 60, 25);
		pNavigation.add(btnNext);
		
		btnLast = new JButton(">>");
		btnLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Initialize();
				
				position = BindList().size()-1;
				PositionInfo(position);
			}
		});
		btnLast.setBounds(476, 13, 60, 25);
		pNavigation.add(btnLast);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(17, 358, 634, 176);
		contentPane.add(scrollPane);
		
		Object [] columnsTable = {"Marque", "Mod\u00E8le", "Ann\u00E9e"};
		table = new JTable();
		table.setCellSelectionEnabled(true);
		table.setSurrendersFocusOnKeystroke(true);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		model.setColumnIdentifiers(columnsTable);
		
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		btnAddCar = new JButton("+");
		btnAddCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int numCols = table.getModel().getColumnCount();
				Object [] car = new Object[numCols];
				
				car[0] = "";
				car[1] = "";
				car[2] = "";
				
				model.addRow(car);

				typeSubRequete = "VOITURE";
			}
		});
		btnAddCar.setForeground(Color.RED);
		btnAddCar.setBackground(Color.BLACK);
		btnAddCar.setFont(new Font("Tahoma", Font.BOLD, 19));
		btnAddCar.setBounds(661, 397, 55, 26);
		contentPane.add(btnAddCar);
		
		btnDeleteCar = new JButton("-");
		btnDeleteCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				typeSubRequete = "VOITURE";
				if(row>=0)
				{
					model.removeRow(row);
				}
			}
		});
		btnDeleteCar.setBackground(Color.BLACK);
		btnDeleteCar.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnDeleteCar.setForeground(Color.RED);
		btnDeleteCar.setBounds(661, 436, 55, 26);
		contentPane.add(btnDeleteCar);
		
		btnReparations = new JButton("R\u00E9parations");
		btnReparations.setBounds(10, 643, 115, 25);
		contentPane.add(btnReparations);
		
		btnEnregistrer = new JButton("Enregistrer");
		btnEnregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				Connection con = ETLFunctions.getConnection();
				PreparedStatement psClient, psCar = null;
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Calendar cal = Calendar.getInstance();

			if(validateInsert() == true)
				{	

				try{	
					if(typeRequete.equalsIgnoreCase("INSERT")){
					
						//Add the cars but first need to validate that a phone and name has been listed
						if(ETLFunctions.validateUniqueClient(txtPhone.getText()))
						{
							psClient = con.prepareStatement("INSERT INTO TBLCLIENTS(NOM,PRENOM,TELEPHONE,INFORMATION,CREATED) VALUES (?,?,?,?,?)");
							psClient.setString(1,txtNom.getText());
							psClient.setString(2,txtPrenom.getText());
							psClient.setString(3,txtPhone.getText());
							psClient.setString(4,txtInfo.getText());
							psClient.setString(5,dateFormat.format(cal.getTime()));
					
							//Remove focus from the cell in the Table
							 table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
							 table.getCellEditor(0,2).stopCellEditing();
							 
						
							psClient.executeUpdate();
	
									
							for(int i =0; i < model.getRowCount(); i++)
							{
									psCar = con.prepareStatement("INSERT INTO TBLVOITURES(MARQUE,MODELE,ANNEE,CLIENTID) VALUES (?,?,?,(SELECT CLIENTID FROM TBLCLIENTS WHERE NOM=? AND PRENOM=? AND TELEPHONE=?))");
									psCar.setString(1,model.getValueAt(i, 0).toString());
									psCar.setString(2,model.getValueAt(i, 1).toString());
									psCar.setInt(3, Integer.parseInt( model.getValueAt(i, 2).toString()));
									psCar.setString(4,txtNom.getText());
									psCar.setString(5,txtPrenom.getText());
									psCar.setString(6,txtPhone.getText());
									
									psCar.executeUpdate();
							}
							txtInfo.setEditable(false);
							txtNom.setEditable(false);
							txtPrenom.setEditable(false);
							txtPhone.setEditable(false);		
							btnModifier.setEnabled(true);
							btnAjouter.setEnabled(true);
							btnSupprimer.setEnabled(true);
							btnReparations.setEnabled(true);
							btnFirst.setEnabled(true);
							btnPrevious.setEnabled(true);
							btnLast.setEnabled(true);
							btnNext.setEnabled(true);
							btnRV.setEnabled(true);
							position = BindList().size()-1;
							PositionInfo(position);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Ce numero appartient déjà à un aure Client!");
							txtPhone.requestFocus();
							txtPhone.setText("");

						}
					}
				
					else if(typeRequete.equalsIgnoreCase("UPDATE"))
					{
						//Add the cars but first need to validate that a phone and name has been listed
						
						psClient = con.prepareStatement("UPDATE TBLCLIENTS SET NOM =?,PRENOM=?,TELEPHONE=?,INFORMATION=? WHERE CLIENTID=?");
						psClient.setString(1,txtNom.getText());
						psClient.setString(2,txtPrenom.getText());
						psClient.setString(3,txtPhone.getText());
						psClient.setString(4,txtInfo.getText());
						psClient.setInt(5,Integer.parseInt(lblClientID.getText()));
				
						//Remove focus from the cell in the Table
						 table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
						 table.getCellEditor(0,2).stopCellEditing();
						psClient.executeUpdate();
						
						if(typeSubRequete == "VOITURE")
						{
							
							//On efface toutes les voitures
							psCar = con.prepareStatement("DELETE FROM TBLVOITURES WHERE CLIENTID = (SELECT CLIENTID FROM TBLCLIENTS WHERE NOM=? AND PRENOM=? AND TELEPHONE=?)");
							psCar.setString(1,txtNom.getText());
							psCar.setString(2,txtPrenom.getText());
							psCar.setString(3,txtPhone.getText());
							psCar.executeUpdate();
							
							for(int i =0; i < model.getRowCount(); i++)
							{
								//On rajoute toutes les voitures
								psCar = con.prepareStatement("INSERT INTO TBLVOITURES(MARQUE,MODELE,ANNEE,CLIENTID) VALUES (?,?,?,(SELECT CLIENTID FROM TBLCLIENTS WHERE NOM=? AND PRENOM=? AND TELEPHONE=?))");
								psCar.setString(1,model.getValueAt(i, 0).toString());
								psCar.setString(2,model.getValueAt(i, 1).toString());
								psCar.setInt(3, Integer.parseInt( model.getValueAt(i, 2).toString()));
								psCar.setString(4,txtNom.getText());
								psCar.setString(5,txtPrenom.getText());
								psCar.setString(6,txtPhone.getText());
								
								psCar.executeUpdate();
							}
						}

						else
						{
							for(int i =0; i < model.getRowCount(); i++)
							{
									psCar = con.prepareStatement("UPDATE TBLVOITURES SET MARQUE=?,MODELE=?,ANNEE=? WHERE CLIENTID=? AND VOITUREID=?");
									psCar.setString(1,model.getValueAt(i, 0).toString());
									psCar.setString(2,model.getValueAt(i, 1).toString());
									psCar.setInt(3, Integer.parseInt( model.getValueAt(i, 2).toString()));
									psCar.setInt(4,Integer.parseInt(lblClientID.getText()));
									psCar.setInt(5,listeCarID.get(i));
									
									psCar.executeUpdate();
							}
							
							txtInfo.setEditable(false);
							txtNom.setEditable(false);
							txtPrenom.setEditable(false);
							txtPhone.setEditable(false);		
							btnModifier.setEnabled(true);
							btnAjouter.setEnabled(true);
							btnSupprimer.setEnabled(true);
							btnReparations.setEnabled(true);
							btnFirst.setEnabled(true);
							btnPrevious.setEnabled(true);
							btnLast.setEnabled(true);
							btnNext.setEnabled(true);
							
							position = BindList().size()-1;
							PositionInfo(position);
						}

					}
					con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			else
			{
				JOptionPane.showMessageDialog(null, "Veuillez vérifier que le Téléphone et le Nom ou prénom sont entrés !");
				txtPhone.setText("");
				}
			}
		});
		btnEnregistrer.setBounds(404, 643, 127, 25);
		contentPane.add(btnEnregistrer);
		
		btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Initialize();
				//position = BindList().size()-1;
				PositionInfo(position);
				
				//Remove focus from the cell in the Table So after Clicking Annuler you still can't modify a cell
				 table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
				 table.getCellEditor(0,2).stopCellEditing();
				 
				 
			}
		});
		btnAnnuler.setBounds(543, 643, 97, 25);
		contentPane.add(btnAnnuler);
		
		lblVoitures = new JLabel("VOITURES");
		lblVoitures.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblVoitures.setBounds(17, 331, 89, 26);
		contentPane.add(lblVoitures);
		
		btnRV = new JButton("Prendre R.V.");
		btnRV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Transfere toutes les information necessaires
				//Verifier que le Client a une voiture au moins
				if(listeNulle != true)
				{
					setVisible(false);
					rendezVous rv = new rendezVous(Integer.parseInt(lblClientID.getText()),txtPhone.getText(),txtNom.getText(),txtPrenom.getText() );
					rv.setVisible(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Veuillez vérifier que le client a une voiture au moins !");
				}


			}
		});
		btnRV.setBounds(137, 643, 127, 25);
		contentPane.add(btnRV);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtPhone, txtNom, txtPrenom, txtInfo, panel, btnAddCar, btnDeleteCar, btnEnregistrer, btnAjouter, btnModifier, pNavigation, btnSupprimer, btnFirst, btnPrevious, btnNext, btnLast, scrollPane, table, btnReparations, btnAnnuler}));
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtPhone, txtNom, txtPrenom, txtInfo, btnAjouter, btnModifier, btnSupprimer, table, btnAddCar, btnDeleteCar, btnFirst, btnPrevious, btnNext, btnLast, btnReparations, btnEnregistrer, btnAnnuler}));
		
		Initialize();
		PositionInfo(0);
	}
}
