import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.toedter.calendar.JDateChooser;

import javax.swing.JTextField;
import javax.swing.JLabel;


public class Principale extends JFrame {

	private JPanel contentPane;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTable table;
	int action = 10;
	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	int clicks = 0;
	private JTextField textField;
	clientScreen cs = new clientScreen();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principale frame = new Principale();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principale() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(15,15, (int)screenSize.getWidth()-75, (int)screenSize.getHeight()-55);
		contentPane = new JPanel();
		contentPane.setFont(new Font("Arial Black", Font.BOLD, 13));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Date currentDate = new Date();
		
		
		
		table = new JTable();
	//	table.setSize(scrollPane.getWidth()-200, scrollPane.getHeight()-200);
		table.setRowHeight(45);
		table.setCellSelectionEnabled(true);
		table.setFillsViewportHeight(true);
		table.setModel(new MyTableModel(
			new String[][] {
				{"09H00\nMomar", null, null, null, null, null, null},
				{"09H30", null, null, null, null, null, null},
				{"10H00", null, null, null, null, null, null},
				{"10H30", null, null, null, null, null, null},
				{"11H00", null, null, null, null, null, null},
				{"11H30", null, null, null, null, null, null},
				{"12H00", null, null, null, null, null, null},
				{"12H30", null, null, null, null, null, null},
				{"13H00", null, null, null, null, null, null},
				{"13H30", null, null, null, null, null, null},
				{"14H00", null, null, null, null, null, null},
				{"14H30", null, null, null, null, null, null},
				{"15H00", null, null, null, null, null, null},
				{"15H30", null, null, null, null, null, null},
				{"16H00", null, null, null, null, null, null},
				{"16H30", null, null, null, null, null, null},
				{"17H00", null, null, null, null, null, null},
				{"17H30", null, null, null, null, null, null},
				{"18H00", null, null, null, null, null, null},
			},
			new String[] {
				"Horaires", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"
			}
		){	private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				true, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {
					 JTable rowTable = (JTable)e.getComponent();
					 int y = rowTable.columnAtPoint(e.getPoint());
					 int x = rowTable.rowAtPoint(e.getPoint());
					 System.out.println("la colonne est " +x+ " la ligne est "+y);
					 
						if(table.getModel().getValueAt(x, y) == null)
						{
							cs.setVisible(true);
						}
						else
						{
							
						}
				}
				
			
			}
		});
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(25);
		table.setDefaultRenderer(String.class, new MultiLineCellRenderer());
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(15, 55, (int)screenSize.getWidth()-120, (int)screenSize.getHeight()-201);
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane);


		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(915, 11, 121, 23);
		dateChooser.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		dateChooser.setDate(currentDate);
		
		// Update after choosing a date in JDate
		// increment Clicks when JDateChooser,s button is used
		dateChooser.getCalendarButton().addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evtDateChooser){
					action=10;
					clicks++;
				}});
		// handler of event on JdateChosser 
		dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evtDateChooserP) {
				
			if ((currentDate != (dateChooser.getDate()))& (clicks !=0))
			updateField(action,dateChooser);
			clicks=0;}
		});
		contentPane.add(dateChooser);
		
		
		
		JButton semainePrec = new JButton("<");
		semainePrec.setBounds(858, 11, 47, 23);
		semainePrec.setFont(new Font("Arial Black", Font.BOLD, 9));
		semainePrec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				action = 0;
				updateField(action,dateChooser);		
			}
		});
		contentPane.add(semainePrec);
		
		JButton semaineSuiv = new JButton(">");
		semaineSuiv.setBounds(1211, 11, 41, 23);
		semaineSuiv.setFont(new Font("Arial Black", Font.BOLD, 9));
		semaineSuiv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 action = 1;
				updateField(action,dateChooser);		
			}
		});
		contentPane.add(semaineSuiv);

		JLabel label = new JLabel("\u00E0");
		label.setBounds(1052, 15, 25, 14);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(1078, 11, 123, 23);
		textField.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		textField.setBackground(new Color(255, 255, 255));
		textField.setEditable(false);
		textField.setFont(new Font("Verdana", Font.PLAIN, 13));
		contentPane.add(textField);
		textField.setColumns(10);
		
		updateField(action, dateChooser);
		
		JButton btnClose = new JButton("Fermer");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		btnClose.setBounds(1629, 944, 186, 31);
		contentPane.add(btnClose);
		
		JButton button = new JButton("Clients");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				cs.setVisible(true);
			}
		});
		button.setBounds(15, 945, 142, 31);
		contentPane.add(button);
	}
	
	public void updateField (int actionUPD, JDateChooser dateChooser){

		
		 actionUPD = action;
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(dateChooser.getDate());
		 calendar.getTime();
		 calendar.set(Calendar.DAY_OF_WEEK, 2);
		 
		 // "+" is pressed
		 if (actionUPD== 1) calendar.add(calendar.DATE, 7);
		 // "-" is pressed
		 if (actionUPD == 0) calendar.add(calendar.DATE, -7);
		 
		 SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String dateInsert = calendar.getDisplayName(calendar.DAY_OF_WEEK, calendar.LONG, Locale.CANADA_FRENCH);
		 dateInsert = dateInsert + "\n" + dateFormat.format(calendar.getTime());
		 dateChooser.setDate(calendar.getTime());
		 
		 for (int i = 1; i <= 6; i++) {
		 		table.getColumnModel().getColumn(i).setHeaderValue(dateInsert);
		 		calendar.add(calendar.DATE, 1);
		 		dateInsert = calendar.getDisplayName(calendar.DAY_OF_WEEK, calendar.LONG, Locale.CANADA_FRENCH);
				dateInsert = dateInsert + "  " + dateFormat.format(calendar.getTime());
				if (i==5)textField.setText(dateInsert.substring(7)); 
		 					}
		table.updateUI();
	}
}

