import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class Loan extends JFrame{
	private JLabel lblSelectAcc, lblSortBy, 								//panel 1
					lblAccDetails, lblAccID, lblFirst, lblMiddle, lblLast, lblBirthday, lblAddress, lblEmail, lblEmploymentTenure, lblMonthlyIncome, 	//panel 2
					lblSelectLoan, lblLoanSortBy,											//panel 3
					lblLoanDetails, lblLoanID, lblAmount, lblBalance, lblPaid, lblLoanTerm, lblTotalPayable, lblPayBack, lblPaymentEvery; 						//panel 4
	private JTextField tfAccID, tfFirst, tfMiddle, tfLast, tfAddress, tfEmail, tfMonthlyIncome, 	//panel 2
						tfLoanID, tfAmount, tfBalance, tfPaid, tfTotalPayable, tfPaymentEvery;  		//panel 4
	private JRadioButton rbByID, rbByName,
							rbLoanByID, rbLoanByAmount;
	private ButtonGroup bgSortBy, bg2SortBy;
	private JButton btnApply, btnSelAcc, btnDelAcc,			//panel1
					btnUpdate, btnAccCancel,								//panel2
					btnNewLoan, btnSelLoan, btnDelLoan,		//panel3
					btnPay, btnLoanCancel;									//panel4
	private JList listAcc, listLoan;
	private DefaultListModel<String> modelAcc, modelLoan;
	private ButtonListener btnl;

	private Connection conn;
	private Statement comm;
	private ResultSet rs;
	private JComboBox<String> cbMonth;
	private JComboBox<Integer> cbDay;
	private JComboBox<Integer> cbYear;
	private JComboBox<String> cbTenure;
	private JComboBox<String> cbLoanTerm;
	private JComboBox<String> cbPayBack;
        
	public Loan(){
		//DATABASE CONNECTION
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/loan_system?autoReconnect=true&useSSL=false", "root", "");
			comm = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		btnl = new ButtonListener();
		
		//panel1
		lblSelectAcc = new JLabel("Select account:");
		lblSortBy = new JLabel("Sort by:");
		rbByID = new JRadioButton("ID");
		rbByName = new JRadioButton("Name");
		bgSortBy = new ButtonGroup();
		bgSortBy.add(rbByID);
		bgSortBy.add(rbByName);
		btnApply = new JButton("Apply");
		btnSelAcc = new JButton("Select");
		btnDelAcc = new JButton("Delete");
		btnApply.addActionListener(btnl);
		btnSelAcc.addActionListener(btnl);
		btnDelAcc.addActionListener(btnl);
		modelAcc = new DefaultListModel<String>();
		getAccountIDs();
		listAcc = new JList(modelAcc);
		listAcc.setLayoutOrientation(JList.VERTICAL);
		listAcc.setVisibleRowCount(3);
		listAcc.setSelectedIndex(0);
		JScrollPane scrollAcc = new JScrollPane(listAcc);
		scrollAcc.setPreferredSize(new Dimension(300, 400));
		JPanel pnl1Radio = new JPanel(new FlowLayout());
		pnl1Radio.add(lblSortBy);
		pnl1Radio.add(rbByID);
		pnl1Radio.add(rbByName);
		JPanel pnl1North = new JPanel(new GridLayout(2, 1));
		pnl1North.add(lblSelectAcc);
		pnl1North.add(pnl1Radio);
		JPanel pnl1South = new JPanel(new FlowLayout());
		pnl1South.add(btnApply);
		pnl1South.add(btnSelAcc);
		pnl1South.add(btnDelAcc);
		
		//panel 2
		lblAccDetails = new JLabel("Account details:");
		lblAccID = new JLabel("Account ID:");
		lblFirst  = new JLabel("First Name:");
		lblMiddle = new JLabel("Middle Name:");
		lblLast = new JLabel("Last Name:");
		lblBirthday = new JLabel("Birthdate:");
		lblAddress = new JLabel("Address:");
		lblEmail = new JLabel("Email:");
		lblEmploymentTenure = new JLabel("Employment tenure:");
		lblMonthlyIncome = new JLabel("Monthly income (PHP):");
		tfAccID = new JTextField();
		tfFirst = new JTextField();
		tfMiddle = new JTextField();
		tfLast = new JTextField();
		tfAddress = new JTextField();
		tfEmail = new JTextField();
		tfMonthlyIncome = new JTextField();
		tfAccID.setEditable(false);
		tfFirst.setEditable(false);
		tfMiddle.setEditable(false);
		tfLast.setEditable(false);
		tfAddress.setEditable(false);
		tfEmail.setEditable(false);
		tfMonthlyIncome.setEditable(false);
		cbTenure = new JComboBox<String>(new String[] {"less than 1 year", "1 - 2 years", "2 - 3 years", "more than 3 years"});
		cbMonth = new JComboBox<String>(new String[] {"January","February","April","May","June","July","August","September","October","November","December"});
		cbDay = new JComboBox<Integer>(new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31});
		Integer[] years = new Integer[117];
		for(int i = 0; i <= 116; i++) { years[i] = 1900 + i; }
		cbYear = new JComboBox<Integer>(years);
		cbYear.setSelectedIndex(116);
		cbTenure.setEnabled(false);
		cbMonth.setEnabled(false);
		cbDay.setEnabled(false);
		cbYear.setEnabled(false);
		JPanel pnlBirth = new JPanel(new FlowLayout());
		pnlBirth.add(cbMonth);
		pnlBirth.add(cbDay);
		pnlBirth.add(cbYear);
		btnUpdate = new JButton("Update");
		btnAccCancel = new JButton("Cancel");
		btnAccCancel.setEnabled(false);
		btnAccCancel.addActionListener(btnl);
		btnUpdate.addActionListener(btnl);
		JPanel pnl2South = new JPanel(new FlowLayout());
		pnl2South.add(btnUpdate);
		pnl2South.add(btnAccCancel);
		JPanel pnl2Center = new JPanel(new GridLayout(18, 1));
		pnl2Center.add(lblAccID);
		pnl2Center.add(tfAccID);
		pnl2Center.add(lblFirst);
		pnl2Center.add(tfFirst);
		pnl2Center.add(lblMiddle);
		pnl2Center.add(tfMiddle);
		pnl2Center.add(lblLast);
		pnl2Center.add(tfLast);
		pnl2Center.add(lblBirthday);
		pnl2Center.add(pnlBirth);
		pnl2Center.add(lblAddress);
		pnl2Center.add(tfAddress);
		pnl2Center.add(lblEmail);
		pnl2Center.add(tfEmail);
		pnl2Center.add(lblEmploymentTenure);
		pnl2Center.add(cbTenure);
		pnl2Center.add(lblMonthlyIncome);
		pnl2Center.add(tfMonthlyIncome);
		pnl2Center.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		//panel 3
		rbLoanByID = new JRadioButton("ID");
		rbLoanByAmount = new JRadioButton("Amount");
		bg2SortBy = new ButtonGroup();
		bg2SortBy.add(rbLoanByID);
		bg2SortBy.add(rbLoanByAmount);
		lblSelectLoan = new JLabel("Select Loan:");
		lblLoanSortBy = new JLabel("Sort by:");
		btnNewLoan = new JButton("New");
		btnSelLoan = new JButton("Select");
		btnDelLoan = new JButton("Delete");
		btnNewLoan.addActionListener(btnl);
		btnSelLoan.addActionListener(btnl);
		btnDelLoan.addActionListener(btnl);
		modelLoan = new DefaultListModel<String>();
		listLoan = new JList(modelLoan);
		listLoan.setLayoutOrientation(JList.VERTICAL);
		listLoan.setVisibleRowCount(3);
		JScrollPane scrollLoan = new JScrollPane(listLoan);
		JPanel pnl3Radio = new JPanel(new FlowLayout());
		pnl3Radio.add(lblLoanSortBy);
		pnl3Radio.add(rbLoanByID);
		pnl3Radio.add(rbLoanByAmount);
		JPanel pnl3North = new JPanel(new GridLayout(2, 1));
		pnl3North.add(lblSelectLoan);
		pnl3North.add(pnl3Radio);
		JPanel pnl3South = new JPanel(new FlowLayout());
		pnl3South.add(btnNewLoan);
		pnl3South.add(btnSelLoan);
		pnl3South.add(btnDelLoan);
		
		//panel 4
		lblLoanDetails = new JLabel("Loan details:");
		lblLoanID = new JLabel("Loan ID:");
		lblLoanTerm = new JLabel("Loan term:");
		lblPayBack = new JLabel("Pay back:");
		lblAmount = new JLabel("Amount:");
		lblTotalPayable = new JLabel("Total amount to pay:");
		lblPaymentEvery = new JLabel("Payment :");
		lblBalance = new JLabel ("Balance:");
		lblPaid = new JLabel("Paid:");
		tfLoanID = new JTextField();
		cbLoanTerm = new JComboBox<String>(new String[] {"6 months", "9 months", "12 months", "2 years", "3 years", "5 years", "10 years"});
		cbPayBack = new JComboBox<String>(new String[] {"Every month", "Every quarter", "Every 6 months", "Every year"});
		tfAmount = new JTextField();
		tfTotalPayable = new JTextField();
		tfPaymentEvery = new JTextField();
		tfBalance = new JTextField();
		tfPaid = new JTextField();
		tfLoanID.setEditable(false);
		tfAmount.setEditable(false);
		cbLoanTerm.setEnabled(false);
		cbPayBack.setEnabled(false);
		tfTotalPayable.setEditable(false);
		tfPaymentEvery.setEditable(false);
		tfBalance.setEditable(false);
		tfPaid.setEditable(false);
		btnPay = new JButton("Pay");
		btnLoanCancel = new JButton("Cancel");
		btnLoanCancel.setEnabled(false);
		btnLoanCancel.addActionListener(btnl);
		btnPay.addActionListener(btnl);
		JPanel pnl4South = new JPanel(new FlowLayout());
		pnl4South.add(btnPay);
		pnl4South.add(btnLoanCancel);
		JPanel pnl4Center = new JPanel(new GridLayout(16, 1));
		pnl4Center.add(lblLoanID);
		pnl4Center.add(tfLoanID);
		pnl4Center.add(lblLoanTerm);
		pnl4Center.add(cbLoanTerm);
		pnl4Center.add(lblPayBack);
		pnl4Center.add(cbPayBack);
		pnl4Center.add(lblAmount);
		pnl4Center.add(tfAmount);
		pnl4Center.add(lblTotalPayable);
		pnl4Center.add(tfTotalPayable);
		pnl4Center.add(lblPaymentEvery);
		pnl4Center.add(tfPaymentEvery);
		pnl4Center.add(lblBalance);
		pnl4Center.add(tfBalance);
		pnl4Center.add(lblPaid);
		pnl4Center.add(tfPaid);
		pnl4Center.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		
		//panel 1
		JPanel pnlAccList = new JPanel(new BorderLayout());
		pnlAccList.add(pnl1North, BorderLayout.NORTH);
		pnlAccList.add(pnl1South, BorderLayout.SOUTH);
		pnlAccList.add(scrollAcc);
		//panel 2
		JPanel pnlInfo = new JPanel(new BorderLayout());
		pnlInfo.add(lblAccDetails, BorderLayout.NORTH);
		pnlInfo.add(pnl2South, BorderLayout.SOUTH);
		pnlInfo.add(pnl2Center);
		//panel 3
		JPanel pnlLoanList = new JPanel(new BorderLayout());
		pnlLoanList.add(pnl3North, BorderLayout.NORTH);
		pnlLoanList.add(pnl3South, BorderLayout.SOUTH);
		pnlLoanList.add(scrollLoan);
		//panel 4
		JPanel pnlLoanInfo = new JPanel(new BorderLayout());
		pnlLoanInfo.add(lblLoanDetails, BorderLayout.NORTH);
		pnlLoanInfo.add(pnl4South, BorderLayout.SOUTH);
		pnlLoanInfo.add(pnl4Center);
		
		
		JPanel pnlCenter = new JPanel(new GridLayout(1, 4));
		pnlCenter.add(pnlAccList);
		pnlCenter.add(pnlInfo);
		pnlCenter.add(pnlLoanList);
		pnlCenter.add(pnlLoanInfo);
		pnlCenter.setBorder(new EmptyBorder(20, 20, 20, 20));
		add(pnlCenter);
		createShowGUI();
		
		
	}
	
	private boolean onOP = false;
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			
			if(ae.getSource().equals(btnApply)) {
				System.out.println("Apply");
				JOptionPane.showMessageDialog(null,  "Please fill out the application form.", "Add new account", JOptionPane.INFORMATION_MESSAGE);
				clearAccData();
				clearLoanData();
				enableAccInput();
			} 
			
			
			else if(ae.getSource().equals(btnSelAcc)) {
				System.out.println("Select account");
				String selected = (String) listAcc.getSelectedValue();
				int selectedAccID = Integer.parseInt(selected.substring(0, selected.indexOf(" ")));
				//load account data to loan details text fields
				loadAccData(selectedAccID);
				//update loan id JList
				getLoanIDs();
				listLoan.setSelectedIndex(0);
				//clear loan details text fields
				clearLoanData();
			} 
			
			
			else if(ae.getSource().equals(btnDelAcc)) {
				System.out.println("Delect account");
			} 
			
			
			else if(ae.getSource().equals(btnUpdate)) {
				System.out.println("Update record");
				if(!onOP) {
					if(tfAccID.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Please select an account to update.","Warning", JOptionPane.INFORMATION_MESSAGE);
					else 
						enableAccInput();
				} else {
					if(tfAccID.getText().equals("")) {
						//submit for application
						//validate
						if(tfFirst.getText().equals("") || tfLast.getText().equals("") || tfAddress.getText().equals("") || tfEmail.getText().equals("") || tfMonthlyIncome.getText().equals("")) {
							JOptionPane.showMessageDialog(null,  "Please fill out every field.", "Warning", JOptionPane.INFORMATION_MESSAGE);
						} else {
							try {
								double income = Double.parseDouble(tfMonthlyIncome.getText());
								if(income < 20000 || cbTenure.getSelectedItem().toString().equals("less than 1 year")) {
									JOptionPane.showMessageDialog(null,  "Only applicants with monthly income more than 20000 PHP and is working for a year or more is eligible to apply for a loan.", "Denied!", JOptionPane.INFORMATION_MESSAGE);
								} else {
									endAccOperation();
									enableLoanInput();
								}
								
							} catch (NumberFormatException e) {
								JOptionPane.showMessageDialog(null,  "Enter valid monthly income!", "Error", JOptionPane.ERROR_MESSAGE);
							}
							
						}
						
					} else {
						//submit for update
						try {
							comm.executeUpdate("UPDATE accounts SET firstname='" + tfFirst.getText() +"', lastname='"+ tfLast.getText() + "', middlename='"
												+ tfMiddle.getText() + "', bday_month='" + cbMonth.getSelectedItem().toString() + "', bday_day=" + cbDay.getSelectedItem().toString() +
												", bday_year=" + cbYear.getSelectedItem().toString() + ", address='" + tfAddress.getText() + "', email='" + tfEmail.getText() + 
												"', tenure='" + cbTenure.getSelectedItem().toString() + "', monthly_income=" + tfMonthlyIncome.getText() + " WHERE id=" + tfAccID.getText());
							endAccOperation();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					
					
					//validate inputs
				}
				
			} 
			
			
			else if(ae.getSource().equals(btnNewLoan)) {
				System.out.println("New loan");
			} 
			
			
			else if(ae.getSource().equals(btnSelLoan)) {
				System.out.println("Select loan");
				String selected = (String) listLoan.getSelectedValue();
				int selectedLoanID = Integer.parseInt(selected.substring(0, selected.indexOf(" ")));
				//load loan data to loan details text fields
				loadLoanData(Integer.parseInt(tfAccID.getText()), selectedLoanID);
			} 
			
			
			else if(ae.getSource().equals(btnDelLoan)) {
				System.out.println("Delete loan");
			} 
			
			
			else if(ae.getSource().equals(btnPay)) {
				if(tfLoanID.getText().equals("")) {
					//validate
					try {
						double amount = Double.parseDouble(tfAmount.getText());
						if(amount >= 10000 && amount <= 5000000) {
							endLoanOperation();
							//insert to accounts
							//create loan_<id> table
							//calculate payable, paymentevery, balance, paid
						} else {
							JOptionPane.showMessageDialog(null, "Minimum loan amount is PHP 10,000 and maximum amount is PHP 5,000,000", "Warning", JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Please enter valid amount.", "Warning", JOptionPane.ERROR_MESSAGE);
					}
				}
				System.out.println("Pay");
			} 
			
			
			else if(ae.getSource().equals(btnAccCancel)) {
				System.out.println("acc cancel");
				endAccOperation();
			} 
			
			
			else if(ae.getSource().equals(btnLoanCancel)) {
				System.out.println("loan cancel");
				endLoanOperation();
			}
		}
	}
	
//	public void updateAccount() {
//		try {
//			System.out.println("UPDATE accounts SET firstname='" + tfFirst.getText() +"', lastname='"+ tfLast.getText() + "', middlename='"
//								+ tfMiddle.getText() + "', bday_month='" + cbMonth.getSelectedItem().toString() + "', bday_day=" + cbDay.getSelectedItem().toString() +
//								", bday_year=" + cbYear.getSelectedItem().toString() + ", address='" + tfAddress.getText() + "', email='" + tfEmail.getText() + 
//								"', tenure='" + cbTenure.getSelectedItem().toString() + "', monthly_income=" + tfMonthlyIncome.getText() + " WHERE id=" + tfAccID.getText());
//			comm.executeUpdate("UPDATE accounts SET firstname='" + tfFirst.getText() +"', lastname='"+ tfLast.getText() + "', middlename='"
//								+ tfMiddle.getText() + "', bday_month='" + cbMonth.getSelectedItem().toString() + "', bday_day=" + cbDay.getSelectedItem().toString() +
//								", bday_year=" + cbYear.getSelectedItem().toString() + ", address='" + tfAddress.getText() + "', email='" + tfEmail.getText() + 
//								"', tenure='" + cbTenure.getSelectedItem().toString() + "', monthly_income=" + tfMonthlyIncome.getText() + " WHERE id=" + tfAccID.getText());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	public void enableLoanInput() {
		tfAmount.setEditable(true);
		cbLoanTerm.setEnabled(true);
		cbPayBack.setEnabled(true);
		
		btnLoanCancel.setEnabled(true);
		btnPay.setText("Submit");
		listAcc.setEnabled(false);
		btnApply.setEnabled(false);
		btnSelAcc.setEnabled(false);
		btnDelAcc.setEnabled(false);
		listLoan.setEnabled(false);
		btnNewLoan.setEnabled(false);
		btnSelLoan.setEnabled(false);
		btnDelLoan.setEnabled(false);
		btnUpdate.setEnabled(false);
	}
	
	public void endLoanOperation() {
		tfAmount.setEditable(false);
		cbLoanTerm.setEnabled(false);
		cbPayBack.setEnabled(false);
		
		btnLoanCancel.setEnabled(false);
		btnPay.setText("Pay");
		listAcc.setEnabled(true);
		btnApply.setEnabled(true);
		btnSelAcc.setEnabled(true);
		btnDelAcc.setEnabled(true);
		listLoan.setEnabled(true);
		btnNewLoan.setEnabled(true);
		btnSelLoan.setEnabled(true);
		btnDelLoan.setEnabled(true);
		btnUpdate.setEnabled(true);
	}
	
	public void enableAccInput() {
		onOP = true;
		listAcc.setEnabled(false);
		btnApply.setEnabled(false);
		btnSelAcc.setEnabled(false);
		btnDelAcc.setEnabled(false);
		listLoan.setEnabled(false);
		btnNewLoan.setEnabled(false);
		btnSelLoan.setEnabled(false);
		btnDelLoan.setEnabled(false);
		tfFirst.setEditable(true);
		tfMiddle.setEditable(true);
		tfLast.setEditable(true);
		tfAddress.setEditable(true);
		tfEmail.setEditable(true);
		tfMonthlyIncome.setEditable(true);
		cbTenure.setEnabled(true);
		cbMonth.setEnabled(true);
		cbDay.setEnabled(true);
		cbYear.setEnabled(true);
		btnPay.setEnabled(false);
		btnUpdate.setText("Submit");
		btnAccCancel.setEnabled(true);
	}
	
	public void endAccOperation() {
		onOP = false;
		listAcc.setEnabled(true);
		btnApply.setEnabled(true);
		btnSelAcc.setEnabled(true);
		btnDelAcc.setEnabled(true);
		listLoan.setEnabled(true);
		btnNewLoan.setEnabled(true);
		btnSelLoan.setEnabled(true);
		btnDelLoan.setEnabled(true);
		tfFirst.setEditable(false);
		tfMiddle.setEditable(false);
		tfLast.setEditable(false);
		tfAddress.setEditable(false);
		tfEmail.setEditable(false);
		tfMonthlyIncome.setEditable(false);
		cbTenure.setEnabled(false);
		cbMonth.setEnabled(false);
		cbDay.setEnabled(false);
		cbYear.setEnabled(false);
		btnPay.setEnabled(true);
		btnUpdate.setText("Update");
		btnAccCancel.setEnabled(false);
	}
	
	public void loadLoanData(int accID, int loanID){
		try {
			rs = comm.executeQuery("SELECT * FROM loan_"+ accID +" Where id="+ loanID);
			rs.next();
			viewLoanData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void viewLoanData(){
		try {
			tfLoanID.setText(Integer.toString(rs.getInt("id")));
			tfAmount.setText(rs.getString("amount"));
			tfBalance.setText(rs.getString("balance"));
			tfPaid.setText(rs.getString("paid"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void clearLoanData() {
		tfLoanID.setText("");
		tfAmount.setText("");
		tfBalance.setText("");
		tfPaid.setText("");
	}
	
	public void loadAccData(int id){
		try {
			rs = comm.executeQuery("SELECT * FROM accounts Where id="+id);
			rs.next();
			viewAccData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void viewAccData(){
		try {
			tfAccID.setText(Integer.toString(rs.getInt("id")));
			tfFirst.setText(rs.getString("firstname"));
			tfMiddle.setText(rs.getString("middlename"));
			tfLast.setText(rs.getString("lastname"));
			cbMonth.setSelectedItem(rs.getObject("bday_month"));
			cbDay.setSelectedItem(rs.getObject("bday_day"));
			cbYear.setSelectedItem(rs.getObject("bday_year"));
			tfAddress.setText(rs.getString("address"));
			tfEmail.setText(rs.getString("email"));
			cbTenure.setSelectedItem(rs.getObject("tenure"));
			tfMonthlyIncome.setText(rs.getString("monthly_income"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void clearAccData() {
		tfAccID.setText("");
		tfFirst.setText("");
		tfMiddle.setText("");
		tfLast.setText("");
		tfAddress.setText("");
		tfEmail.setText("");
		tfMonthlyIncome.setText("");
		cbTenure.setSelectedIndex(0);
		cbMonth.setSelectedIndex(0);
		cbDay.setSelectedIndex(0);
		cbYear.setSelectedIndex(0);
		cbYear.setSelectedIndex(116);
	}
	
	private void createShowGUI(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Loan Management");
		setSize(1200, 800);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	//gets account IDs and add it to the JList model for accounts
	public void getAccountIDs(){
		try {
			modelAcc.removeAllElements();
			rs = comm.executeQuery("SELECT * FROM accounts");
			while(rs.next()){
				modelAcc.addElement(rs.getInt("id") + " - " + rs.getString("lastname") + ", " 
									+ rs.getString("firstname") + " " + rs.getString("middlename"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void getLoanIDs(){
		try {
			modelLoan.removeAllElements();
			rs = comm.executeQuery("SELECT * FROM loan_"+tfAccID.getText());
			while(rs.next()){
				modelLoan.addElement(rs.getInt("id") + " - " + rs.getDouble("Amount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
//	

//
//
//	
//	public void viewLoanData(){
//		try {
//			tfAmount.setText(Double.toString(rs.getDouble("amount")));
//			tfBalance.setText(Double.toString(rs.getDouble("Balance")));
//			tfPaid.setText(Double.toString(rs.getDouble("Paid")));
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

//	
//	private class NavigationActions implements ActionListener{
//		public void actionPerformed(ActionEvent ae){
//			try{
//				if(ae.getSource().equals(btnPrev)){
//					if(!rs.isFirst())
//						rs.previous();
//				} else {
//					if(!rs.isLast())
//						rs.next();
//				}
//            viewAccData();
//         } catch(SQLException e){
//            e.printStackTrace();
//         }
//      }
//   }
//	
//	private class ComboBox implements ActionListener{
//		public void actionPerformed(ActionEvent ae){
//			JComboBox cm = (JComboBox)ae.getSource();
//			int sourceGet = (int)cm.getSelectedItem();
//			if(cm.equals(cmbAcc)) {
//				loadAccData(sourceGet);
////				cmbLoanIDs.removeAllItems();
////				Vector<Integer> temp = new Vector<Integer>();
////				cmbLoanIDs = new JComboBox<Integer>(getLoanIDs(temp));
//
//				
////				for(int i = 0; i < temp.size(); i++) {
////					System.out.println(i + " i");
////					cmbLoanIDs.addItem(temp.get(i));
////					System.out.println(temp.get(i));
////					System.out.println(temp.size() + " size");
////				}
//			}
//			else if (cm.equals(cmbLoanIDs)) {
//				
//			}
//			
//			System.out.println(sourceGet);
//			System.out.println("Here");
//			
//		}
//	}
	
	public static void main(String[] args) {
		JFrame app = new Loan();
		
	}

}
