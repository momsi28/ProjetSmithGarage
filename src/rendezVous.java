import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JFormattedTextField;

import Utils.ETLFunctions;

import com.toedter.calendar.JDateChooser;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;


public class rendezVous extends JFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = 504011202239575866L;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}
	private String nom;
	private String prenom;
	private String phone;
	private int clientID;
	private JPanel contentPane;
	private JTextField txtNom;
	private JTextField txtPrenom;
	private JFormattedTextField txtPhone;
	private JComboBox cbMarque;
	private JDateChooser dateChooser;
	private JComboBox cbHeureDebut;
	private JButton btnEffacer;
	private boolean initial = true 	;
	private JButton btnModifier;
	boolean newClient = false;
	Vector<Voitures> car = new Vector<>();
	List<HoraireRV> horaireRV = new ArrayList<HoraireRV>();
	int year = Calendar.getInstance().get(Calendar.YEAR);
	private JTextField txtModele;
	private JTextField txtAnnee;
	private JTextField txtReparations;
	private int voitureID;
	private JComboBox cbDuree;
	ArrayList<String>listeHeureNA = new ArrayList<String>();
	
		/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					rendezVous frame = new rendezVous(0,"","","");
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void Initialiser()
	{
		txtPhone.setText(phone);
		txtNom.setText(nom);
		txtPrenom.setText(prenom);
		cbDuree.setModel(new DefaultComboBoxModel(new String[] {"0.5", "1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5", "5", "5.5", "6", "6.5", "7", "7.5", "8", "8.5", "9"}));
		cbDuree.setSelectedIndex(0);
		try {
			Connection con = ETLFunctions.getConnection();
			Statement stCar = con.createStatement();
			ResultSet rsCar = stCar.executeQuery("select * from tblVoitures WHERE CLIENTID = " + clientID);
		
			
			while(rsCar.next())
			{
				ArrayList<Integer> listeVoitures = new ArrayList<Integer>();
				initial = true;
				listeVoitures.add(rsCar.getInt("VOITUREID"));
				
				car.add(new Voitures(rsCar.getInt("VOITUREID"),rsCar.getString("Marque"),rsCar.getString("Modele"),rsCar.getString("Annee"),clientID));
			//	cbModele.addItem(rsCar.getString("Modele"));
				
			//	cbAnnee.addItem(rsCar.getString("Annee"));
				initial = false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public boolean validateSaveClient()
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

	public boolean validateRV()
	{
		//Verifier que à la date D et à l'heure H le RV est possible
		//On va chercher la liste des RV à cette Date ainsique les heures disponibles
		//Suggerer un bonne heure
		
		return false;
		
		
	}
	
	public boolean validateSaveCar()
	{
		if(cbMarque.getSelectedItem() != null )
			{	
				return true;
			}
		else
			return false;
	}
	
	public boolean validateDateRV()
	{
		if(dateChooser.getDate() == null )
		{
			return false;
		}
		return true;
	}
	
	public void enregistreReparations()
	{
		String dateRV = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String nomFichier = String.valueOf(clientID) + "_" + txtNom.getText() + "_"+ txtPrenom.getText() + ".txt";

		try {
			File fichier = new File(nomFichier);
			FileWriter fileF = new FileWriter(fichier,true);
			BufferedWriter bw = new BufferedWriter(fileF);
			
			if(fichier.exists())
			{
				bw.newLine();
			}
			bw.write(dateChooser.getDate() + " ");	
			txtReparations.write(bw);
			bw.newLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public int convertirMS(Time time)
	{
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		int ms = (hour * 3600 + minute * 60 + second) * 1000;
		return ms;
	}
	

//	public static String convertMillis(long milliseconds)
//	{
//
//		long seconds, minutes, hours;
//		seconds = milliseconds / 1000;
//		minutes = seconds / 60;
//		seconds = seconds % 60;
//		hours = minutes / 60;
//		minutes = minutes % 60;
//
//		return( hours + ":" + minutes);
//
//	}
	public void prendreRV()
	{
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = sdf.format(dateChooser.getDate());
			java.sql.Date sqlDate = new java.sql.Date(sdf.parse(dateString).getTime());
			System.out.println(sqlDate.getTime());

			Connection con = ETLFunctions.getConnection();
			PreparedStatement stRV = con.prepareStatement("select * from tblPlanning WHERE dateP = ? ORDER BY HeureDebut ASC");
			stRV.setDate(1, sqlDate);
			ResultSet rsRV = stRV.executeQuery();

			while(rsRV.next())
			{
				//Save information in a structure
				horaireRV.add(new HoraireRV(rsRV.getDouble("Duree"),rsRV.getDate("DateP"),rsRV.getTime("heureDebut"),rsRV.getTime("heureFin")));
				//Compute information to get the duration in mns.
				long  duree = (horaireRV.get(0).getHeureFin().getTime() - horaireRV.get(0).getHeureDebut().getTime())/60000;
				System.out.println(duree);
			}
			
			//normalize the combobox value to get proper time
				String str =  (String) cbHeureDebut.getSelectedItem();
				String strTrans = str+":00";

			//Check what are the non available starting hours and get the duration they can
			for (int i=0; i< horaireRV.size(); i++)
			{
			//	if (Time.valueOf(strTrans).after(horaireRV.get(0).getHeureDebut()) && Time.valueOf(strTrans).before(horaireRV.get(0).getHeureFin()))
			//	{
					//How many starting hours are not available based on the duration
					double nbrDelete = (horaireRV.get(i).getDuree() *2);
					for (int j=1;j< nbrDelete;j++)
					{
						long momar = convertirMS(horaireRV.get(i).getHeureDebut()) + (1800000*j); 
						String tempH = DurationFormatUtils.formatDuration(momar, "H:mm ");//
						listeHeureNA.add(tempH);
						cbHeureDebut.removeItem(tempH.trim());								
					}
			}
			System.out.println(listeHeureNA);
			isRVAvailable();
		} catch (SQLException | ParseException  e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	
	public boolean isRVAvailable()
	{
		boolean retour = true;
		ArrayList<String>listeDuree = new ArrayList<String>();
		Time dateRV = Time.valueOf(cbHeureDebut.getSelectedItem().toString() + ":00");
		//Add the Duration
		double dureeMS = Double.parseDouble(cbDuree.getSelectedItem().toString())*3600000;
		
		for (double i =  Double.parseDouble(cbDuree.getSelectedItem().toString()); i >0 ; i=i-0.5 )
		{
	
			long dateRVms = (long) (convertirMS(dateRV) + dureeMS);
			String tempH = DurationFormatUtils.formatDuration(dateRVms, "H:mm ");
			if(listeHeureNA.contains(tempH))
			{
				//Remove all >= duree
				cbDuree.removeAllItems();
				
				listeDuree.clear();
				retour = false;
			}
			else
			{
				listeDuree.add(String.valueOf(dureeMS/3600000));
			}
			dureeMS = dureeMS - 1800000;
		}
		
		if ((listeDuree.size() != 0) && retour == false)
		{
			cbDuree.removeAllItems();
			for (int j=listeDuree.size()-1; j >=0;j--)
			{
				cbDuree.addItem(listeDuree.get(j));
			}
		}
		else if(listeDuree.size() == 0)
		{
			listeHeureNA.add(String.valueOf(dateRV.getTime()));
		}
		return retour;	
	}
	
	public rendezVous(int _clientID, String _phone, String _nom, String _prenom)
	{
		this.clientID =_clientID;
		this.nom=_nom;
		this.prenom=_prenom;
		this.phone=_phone;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 705, 761);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setForeground(new Color(176, 224, 230));
		panel_2.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "INFORMATION CLIENT", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(10, 35, 673, 287);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBackground(SystemColor.menu);
		panel_3.setBounds(10, 47, 315, 175);
		panel_2.add(panel_3);
		
		JLabel label_4 = new JLabel("No T\u00E9l:");
		label_4.setFont(new Font("Times New Roman", Font.BOLD, 14));
		label_4.setBounds(15, 17, 46, 14);
		panel_3.add(label_4);
		
		txtNom = new JTextField();
		txtNom.setEditable(false);
		txtNom.setColumns(10);
		txtNom.setBounds(97, 70, 175, 29);
		panel_3.add(txtNom);
		
		JLabel label_5 = new JLabel("Nom:");
		label_5.setFont(new Font("Times New Roman", Font.BOLD, 14));
		label_5.setBounds(28, 76, 33, 14);
		panel_3.add(label_5);
		
		JLabel label_6 = new JLabel("Pr\u00E9nom:");
		label_6.setFont(new Font("Times New Roman", Font.BOLD, 14));
		label_6.setBounds(10, 137, 61, 14);
		panel_3.add(label_6);
		
		txtPrenom = new JTextField();
		txtPrenom.setEditable(false);
		txtPrenom.setColumns(10);
		txtPrenom.setBounds(97, 130, 175, 29);
		panel_3.add(txtPrenom);
		
		 MaskFormatter mf1;
		 try {
			 mf1 = new MaskFormatter("###-###-####");
			 mf1.setPlaceholderCharacter('_');
			 txtPhone = new JFormattedTextField(mf1);
			 txtPhone.setEditable(false);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		txtPhone.setBounds(97, 10, 175, 29);
		panel_3.add(txtPhone);
		
			
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBackground(SystemColor.menu);
		panel_4.setBounds(357, 47, 306, 175);
		panel_2.add(panel_4);
		
		cbMarque = new JComboBox (car);
	
		cbMarque.setEditable(false);
		cbMarque.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				
				if(ie.getStateChange() == ItemEvent.SELECTED)
				{
					if(!initial)
					{
					    	int selectedItem = cbMarque.getSelectedIndex();
					    	txtModele.setText(car.get(selectedItem).getModele());
					    	txtAnnee.setText(car.get(selectedItem).getAnnee());
					    	voitureID = car.get(selectedItem).getId();
					}
				}
			}
		});
		
		cbMarque.setBounds(101, 8, 195, 20);
		panel_4.add(cbMarque);
		
		JLabel label_7 = new JLabel("Marque:\r\n");
		label_7.setFont(new Font("Times New Roman", Font.BOLD, 14));
		label_7.setBounds(10, 14, 61, 14);
		panel_4.add(label_7);
		
		JLabel label_8 = new JLabel("Mod\u00E8le:");
		label_8.setFont(new Font("Times New Roman", Font.BOLD, 14));
		label_8.setBounds(10, 73, 61, 14);
		panel_4.add(label_8);
		
		JLabel label_9 = new JLabel("Ann\u00E9e:");
		label_9.setFont(new Font("Times New Roman", Font.BOLD, 14));
		label_9.setBounds(10, 133, 46, 14);
		panel_4.add(label_9);
		
		JLabel lblClients = new JLabel("CLIENTS");
		lblClients.setBounds(135, 25, 86, 22);
		panel_2.add(lblClients);
		lblClients.setFont(new Font("Times New Roman", Font.BOLD, 12));
		
		JLabel lblVoiture_1 = new JLabel("VOITURE");
		lblVoiture_1.setBounds(478, 24, 107, 23);
		panel_2.add(lblVoiture_1);
		lblVoiture_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		
		btnModifier = new JButton("Modifier Client");
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			clientScreen cs = new clientScreen();
			cs.btnModifier.doClick();
			setVisible(false);
			cs.setVisible(true);
			}
		});
		btnModifier.setEnabled(false);
		btnModifier.setBounds(262, 252, 159, 22);
		panel_2.add(btnModifier);
		
		JButton btnEnregistrer = new JButton("ENREGISTRER");
		btnEnregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(validateSaveClient())
				{
					if(validateSaveCar())
					{
						if(validateDateRV())
						{
							
							//Verifier la dsponibilite et ajuster l'horaire
							//Enregistrer les réparations
							prendreRV();
							enregistreReparations();
							
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Veuillez vérifier que une date de R.V. est entrée !");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Veuillez vérifier que la Marque ou le modèle de la voiture est entré !");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Veuillez vérifier que le Téléphone et le Nom ou prénom sont entrés !");
				}
			}
		});
		btnEnregistrer.setBounds(373, 686, 140, 23);
		contentPane.add(btnEnregistrer);
		
		JButton btnAnnuler = new JButton("ANNULER");
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnEffacer.doClick();
				dispose();
			}
		});
		btnAnnuler.setBounds(529, 686, 148, 23);
		contentPane.add(btnAnnuler);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "RENDEZ-VOUS", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(14, 335, 669, 154);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label_1 = new JLabel("Date du R.V.:");
		label_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		label_1.setBounds(35, 71, 90, 14);
		panel.add(label_1);
		
		JLabel lblHeureDuRv = new JLabel("D\u00E9but du R.V.:");
		lblHeureDuRv.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblHeureDuRv.setBounds(360, 47, 103, 14);
		panel.add(lblHeureDuRv);
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		//Can't choose in the past
		dateChooser.getJCalendar().setMinSelectableDate(new Date());
		dateChooser.getDateEditor().addPropertyChangeListener(
			    new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e) {
						// TODO Auto-generated method stub

						if ("date".equals(e.getPropertyName())) {
			              

					}
				}
			   });
		
		dateChooser.setBounds(137, 69, 136, 20);
		panel.add(dateChooser);
		
		cbHeureDebut = new JComboBox();
		cbHeureDebut.setModel(new DefaultComboBoxModel(new String[] {"9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00"}));
		cbHeureDebut.setEditable(true);
		cbHeureDebut.setBounds(471, 45, 79, 20);
		panel.add(cbHeureDebut);
		
		JLabel lblHeureFinDu = new JLabel("Dur\u00E9e:");
		lblHeureFinDu.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblHeureFinDu.setBounds(405, 96, 50, 14);
		panel.add(lblHeureFinDu);
		
		JLabel lblHeures = new JLabel("heure(s)");
		lblHeures.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblHeures.setBounds(561, 96, 96, 14);
		panel.add(lblHeures);
		
		cbDuree = new JComboBox();
		cbDuree.setModel(new DefaultComboBoxModel(new String[] {"0.5", "1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5", "5", "5.5", "6", "6.5", "7", "7.5", "8", "8.5", "9"}));
		cbDuree.setBounds(471, 93, 79, 20);
		panel.add(cbDuree);
		
		btnEffacer = new JButton("Effacer");
		btnEffacer.setBounds(10, 686, 115, 23);
		contentPane.add(btnEffacer);
		btnEffacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				txtPhone.setText("");
				txtNom.setText("");
				txtPrenom.setText("");
				
				cbMarque.removeAllItems();
				cbHeureDebut.setSelectedIndex(0);
				dateChooser.setCalendar(null);
				cbDuree.setModel(new DefaultComboBoxModel(new String[] {"0.5", "1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5", "5", "5.5", "6", "6.5", "7", "7.5", "8", "8.5", "9"}));
				cbDuree.setSelectedIndex(0);
			}
		});
		
		Initialiser();
		cbMarque.setSelectedItem(car.get(0).getMarque());
		
		txtModele = new JTextField();
		txtModele.setEditable(false);
		txtModele.setBounds(101, 69, 195, 22);
		panel_4.add(txtModele);
		txtModele.setColumns(10);
		
		txtAnnee = new JTextField();
		txtAnnee.setEditable(false);
		txtAnnee.setColumns(10);
		txtAnnee.setBounds(101, 129, 195, 22);
		panel_4.add(txtAnnee);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "R\u00C9PARATIONS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(20, 502, 657, 160);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		txtReparations = new JTextField();
		txtReparations.setBounds(0, 23, 645, 124);
		panel_1.add(txtReparations);
		txtReparations.setColumns(2);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtPhone, txtNom, txtPrenom, cbMarque, btnEffacer, dateChooser, cbHeureDebut, btnEnregistrer, btnAnnuler, dateChooser.getCalendarButton()}));
	}
}
