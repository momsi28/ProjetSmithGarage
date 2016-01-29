import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;

import javax.swing.LayoutStyle.ComponentPlacement;

import com.toedter.calendar.JDateChooser;

import javax.swing.JSeparator;
import javax.swing.border.LineBorder;

import java.awt.Color;



import java.util.Properties;

import com.toedter.calendar.JYearChooser;

import javax.swing.JTextPane;

//import org.eclipse.wb.swing.FocusTraversalOnArray;


import java.awt.Component;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;



public class MainInterface<l> extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTable table;
	int action = 10;
	int clicks = 0;
	private JTextPane textPane;
	// jDatechooser variable
	
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
				if (i==5)textPane.setText(dateInsert.substring(7)); 
		 					}
		 
		 
		 
		table.updateUI();
}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("rawtypes")
					MainInterface frame = new MainInterface();
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
	public MainInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(15,15, (int)screenSize.getWidth()-75, (int)screenSize.getHeight()-65);
		contentPane = new JPanel();
		contentPane.setFont(new Font("Arial Black", Font.BOLD, 13));
		contentPane.setForeground(Color.MAGENTA);
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 65, (int)screenSize.getWidth()-90, (int)screenSize.getHeight()-230);

		//pick date
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		Date currentDate = new Date();
		dateChooser.setDate(currentDate);
		@SuppressWarnings("unused")
		Date choosingDate = dateChooser.getDate();
		
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

		textPane = new JTextPane();
		textPane.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		textPane.setToolTipText("");
		textPane.setBackground(new Color(255, 255, 255));
		textPane.setEditable(false);
		textPane.setFont(new Font("Verdana", Font.PLAIN, 13));
		textPane.setText("lastDayOfWeek");
		
		
		// button d'incrementation
		JButton btnNewButton = new JButton("+");
		btnNewButton.setFont(new Font("Arial Black", Font.BOLD, 9));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 action = 1;
				updateField(action,dateChooser);		
			}
		});
		
		JButton button = new JButton("-");
		button.setFont(new Font("Arial Black", Font.BOLD, 9));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				action = 0;
				updateField(action,dateChooser);		
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 1005, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(button, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
							.addGap(34)
							.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 535, GroupLayout.PREFERRED_SIZE)
					.addGap(53)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
							.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addComponent(button, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(128, Short.MAX_VALUE))
		);
//		dateChooser.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{dateChooser.getCalendarButton()}));

        
       // constructs the table
       String[] columnNames = new String[] {"Horaires", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
       Object[][] rowData =  new Object[][] {};
/**       
       table2 = new JTable(rowData, columnNames);
       table2.getTableHeader().setDefaultRenderer(new HeaderRenderer());
       add(new JScrollPane(table2));
		*/
		
		//table = new JTable();
		table = new JTable(rowData, columnNames);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {
					 JTable rowTable = (JTable)e.getComponent();
					 int x = rowTable.columnAtPoint(e.getPoint());
					 int y = rowTable.rowAtPoint(e.getPoint());
				 System.out.println("la colonne est " +x+ " la ligne est "+y);}
			
			}
		});
		
		table.setToolTipText("");
		table.setRowHeight(45);
		table.setCellSelectionEnabled(true);
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"09H00", null, null, null, null, null, null},
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
				{"18H30", null, null, null, null, null, null},
				{"19H00", null, null, null, null, null, null},
			},
			new String[] {
				"Horaires", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"
			}
		) 
		
		{	private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				true, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		updateField(action, dateChooser);
		
		
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);

	}
}
