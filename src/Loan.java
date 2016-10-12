import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Formatter;

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
					btnPay, btnCalculate, btnLoanCancel;									//panel4
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
		rbByID.setSelected(true);
		btnApply = new JButton("Apply");
		btnSelAcc = new JButton("Select");
		btnDelAcc = new JButton("Delete");
		btnApply.addActionListener(btnl);
		btnSelAcc.addActionListener(btnl);
		btnDelAcc.addActionListener(btnl);
		modelAcc = new DefaultListModel<String>();
		listAcc = new JList(modelAcc);
		listAcc.setLayoutOrientation(JList.VERTICAL);
		listAcc.setVisibleRowCount(3);
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
		btnUpdate.setEnabled(false);
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
		rbLoanByID.setSelected(true);
		lblSelectLoan = new JLabel("Select Loan:");
		lblLoanSortBy = new JLabel("Sort by:");
		btnNewLoan = new JButton("New");
		btnSelLoan = new JButton("Select");
		btnDelLoan = new JButton("Delete");
		btnNewLoan.addActionListener(btnl);
		btnSelLoan.addActionListener(btnl);
		btnDelLoan.addActionListener(btnl);
		btnNewLoan.setEnabled(false);
		btnSelLoan.setEnabled(false);
		btnDelLoan.setEnabled(false);
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
		cbLoanTerm = new JComboBox<String>(new String[] {"12 months", "2 years", "3 years", "5 years", "10 years"});
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
		btnPay.setEnabled(false);
		btnCalculate = new JButton("Calculate");
		btnCalculate.setEnabled(false);
		btnCalculate.addActionListener(btnl);
		btnLoanCancel = new JButton("Cancel");
		btnLoanCancel.setEnabled(false);
		btnLoanCancel.addActionListener(btnl);
		btnPay.addActionListener(btnl);
		JPanel pnl4South = new JPanel(new FlowLayout());
		pnl4South.add(btnPay);
		pnl4South.add(btnCalculate);
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
		

		getAccountIDs();
		listAcc.setSelectedIndex(0);
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
				modelLoan.removeAllElements();
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
				String selected = listAcc.getSelectedValue().toString();
				int accID = Integer.parseInt(selected.substring(0, selected.indexOf(" ")));
				System.out.println(accID);
				try {
					comm.executeUpdate("DELETE FROM accounts WHERE id=" + accID);
					comm.executeUpdate("DROP TABLE loan_" + accID);
					clearAccData();
					clearLoanData();
					modelLoan.removeAllElements();
					getAccountIDs();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			
			
			else if(ae.getSource().equals(btnUpdate)) {
				System.out.println("Update record");
				if(!onOP) {
					//enter update mode
					enableAccInput();
					clearLoanData();
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
						//UPDATE MODE
						//submit for update
						if(tfFirst.getText().equals("") || tfLast.getText().equals("") || tfAddress.getText().equals("") || tfEmail.getText().equals("") || tfMonthlyIncome.getText().equals("")) {
							JOptionPane.showMessageDialog(null,  "Please fill out every field.", "Warning", JOptionPane.INFORMATION_MESSAGE);
						} else {
							try {
								double income = Double.parseDouble(tfMonthlyIncome.getText());
								if(income < 20000 || cbTenure.getSelectedItem().toString().equals("less than 1 year")) {
									JOptionPane.showMessageDialog(null,  "Only applicants with monthly income more than 20000 PHP and is working for a year or more is eligible to apply for a loan.", "Denied!", JOptionPane.INFORMATION_MESSAGE);
								} else {
								
									comm.executeUpdate("UPDATE accounts SET firstname='" + tfFirst.getText() +"', lastname='"+ tfLast.getText() + "', middlename='"
											+ tfMiddle.getText() + "', bday_month='" + cbMonth.getSelectedItem().toString() + "', bday_day=" + cbDay.getSelectedItem().toString() +
											", bday_year=" + cbYear.getSelectedItem().toString() + ", address='" + tfAddress.getText() + "', email='" + tfEmail.getText() + 
											"', tenure='" + cbTenure.getSelectedItem().toString() + "', monthly_income=" + tfMonthlyIncome.getText() + " WHERE id=" + tfAccID.getText());
									endAccOperation();
								}
							} catch (SQLException e) {
								JOptionPane.showMessageDialog(null, "Enter valid data only.", "Invalid input!", JOptionPane.ERROR_MESSAGE);
							}
						}
						
					}
					
					
					//validate inputs
				}
				
			} 
			
			
			else if(ae.getSource().equals(btnNewLoan)) {
				System.out.println("New loan");
				clearLoanData();
				enableLoanInput();
			} 
			
			
			else if(ae.getSource().equals(btnSelLoan)) {
				System.out.println("Select loan");
				String selected = (String) listLoan.getSelectedValue();
				int selectedLoanID = Integer.parseInt(selected.substring(0, selected.indexOf(" ")));
				//load loan data to loan details text fields
				loadLoanData(Integer.parseInt(tfAccID.getText()), selectedLoanID);
				if(Double.parseDouble(tfBalance.getText()) == 0)
					btnPay.setEnabled(false);
				else
					btnPay.setEnabled(true);
				
			} 
			
			
			else if(ae.getSource().equals(btnDelLoan)) {
				System.out.println("Delete loan");
				String selectedLoan = listLoan.getSelectedValue().toString();
				int loanID = Integer.parseInt(selectedLoan.substring(0, selectedLoan.indexOf(" ")));
				String selectedAcc = listAcc.getSelectedValue().toString();
				int accID = Integer.parseInt(selectedAcc.substring(0, selectedAcc.indexOf(" ")));
				System.out.println(accID);
				try {
					comm.executeUpdate("DELETE FROM loan_"+ accID +" WHERE id=" + loanID);
					getLoanIDs();
					listLoan.setSelectedIndex(0);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			
			
			else if(ae.getSource().equals(btnPay)) {
				if(tfAccID.getText().isEmpty()) {
					
					try {
						comm.executeUpdate("INSERT INTO accounts " 
							+ "VALUES(null, '" + tfFirst.getText() + "', "
							+ "'" + tfMiddle.getText() + "', "
							+ "'" + tfLast.getText() + "', "
							+ "'" + cbMonth.getSelectedItem().toString() + "', "
							+ "" + cbDay.getSelectedItem().toString() + ", "
							+ "" + cbYear.getSelectedItem().toString() + ", "
							+ "'" + tfAddress.getText() + "', "
							+ "'" + tfEmail.getText() + "', "
							+ "'" + cbTenure.getSelectedItem().toString() + "', "
							+ "" + tfMonthlyIncome.getText() + ")");
						rs = comm.executeQuery("SELECT id FROM accounts WHERE firstname='" + tfFirst.getText() + "' AND "
								+ "middlename='" + tfMiddle.getText() + "' AND "
								+ "lastname='" + tfLast.getText() + "' AND "
								+ "bday_month='" + cbMonth.getSelectedItem().toString() + "' AND "
								+ "bday_day=" + cbDay.getSelectedItem().toString() + " AND "
								+ "bday_year=" + cbYear.getSelectedItem().toString() + " AND "
								+ "address='" + tfAddress.getText() + "' AND "
								+ "email='" + tfEmail.getText() + "' AND "
								+ "tenure='" + cbTenure.getSelectedItem().toString() + "' AND "
								+ "monthly_income=" + tfMonthlyIncome.getText());
						rs.next();
						int accID = rs.getInt("id");

						comm.executeUpdate("CREATE TABLE loan_" + accID
								+ "(id INT PRIMARY KEY AUTO_INCREMENT, "
								+ "term VARCHAR(50) NOT NULL, payback VARCHAR(50) NOT NULL, "
								+ "amount DOUBLE NOT NULL, total DOUBLE NOT NULL, periodical DOUBLE NOT NULL," 
								+ "balance DOUBLE NOT NULL, paid DOUBLE NOT NULL)");

						
						comm.executeUpdate("INSERT INTO loan_" + accID
								+ " VALUES(null, '" + cbLoanTerm.getSelectedItem().toString() + "', "
								+ "'" + cbPayBack.getSelectedItem().toString() + "', "
								+ "" + tfAmount.getText() + ", "
								+ "" + tfTotalPayable.getText() + ", "
								+ "" + tfPaymentEvery.getText() + ", "
								+ "" + tfBalance.getText() + ", "
								+ "" + tfPaid.getText() + ")");
						getAccountIDs();
						clearAccData();
						endLoanOperation();
						btnUpdate.setEnabled(false);
						btnNewLoan.setEnabled(false);
						btnSelLoan.setEnabled(false);
						btnDelLoan.setEnabled(false);
						clearLoanData();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("Application submitted");

				} else {
					if(tfLoanID.getText().isEmpty()) {
						try {
							comm.executeUpdate("INSERT INTO loan_" + tfAccID.getText()
								+ " VALUES(null, '" + cbLoanTerm.getSelectedItem().toString() + "', "
								+ "'" + cbPayBack.getSelectedItem().toString() + "', "
								+ "" + tfAmount.getText() + ", "
								+ "" + tfTotalPayable.getText() + ", "
								+ "" + tfPaymentEvery.getText() + ", "
								+ "" + tfBalance.getText() + ", "
								+ "" + tfPaid.getText() + ")");
							modelLoan.removeAllElements();
						} catch(SQLException e) {
							e.printStackTrace();
						}
						System.out.println("New loan submitted");
						getLoanIDs();
						listLoan.setSelectedIndex(0);
						clearLoanData();
						endLoanOperation();
					} else {
						System.out.println("PAY");
						double payment = Double.parseDouble(tfPaymentEvery.getText());
						double balance = Double.parseDouble(tfBalance.getText());
						double paid = Double.parseDouble(tfPaid.getText());
						balance -= payment;
						if(balance < 1)
							balance = 0;
						
						if(balance >= 0) {
							paid += payment;
							tfBalance.setText(Double.toString(balance));
							tfPaid.setText(Double.toString(paid));
							try {
								comm.executeUpdate("UPDATE loan_" + tfAccID.getText() + " SET balance=" + balance
										+ ", paid=" + paid + " WHERE id=" + tfLoanID.getText());
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(balance == 0)
								JOptionPane.showMessageDialog(null, "Selected loan is now fully paid.", "Notice", JOptionPane.INFORMATION_MESSAGE);
							if(balance == 0)
								btnPay.setEnabled(false);
							
						}
					}
					
				}
				
			} 
			
			
			else if(ae.getSource().equals(btnCalculate)) {
				System.out.println("calculate");
				try {
					double amount = Double.parseDouble(tfAmount.getText());
					if(amount >= 10000 && amount <= 20000000) {
						//insert to accounts
						//create loan_<id> table
						//calculate payable, paymentevery, balance, paid
						String loanTerm = cbLoanTerm.getSelectedItem().toString();
						int dividend = 0, divisor = 0;
						double interest = 0;
						if(loanTerm.equals("12 months")) {
							dividend = 12;
							interest = 0.125;
						} 
						else if(loanTerm.equals("2 years")) {
							dividend = 24;
							interest = 0.15;
						} 
						else if(loanTerm.equals("3 years")) {
							dividend = 36;
							interest = 0.175;
						} 
						else if(loanTerm.equals("5 years")) {
							dividend = 60;
							interest = 0.20;
						} 
						else if(loanTerm.equals("10 years")) {
							dividend = 120;
							interest = 0.25;
						} 
						String payBack = cbPayBack.getSelectedItem().toString();
						if(payBack.equals("Every month"))
							divisor = 1;
						else if(payBack.equals("Every quarter"))
							divisor = 3;
						else if(payBack.equals("Every 6 months"))
							divisor = 6;
						else if(payBack.equals("Every year"))
							divisor = 12;
						double totalWithInterest = amount + amount * interest;
						double periodical = totalWithInterest / (dividend / divisor);
						tfTotalPayable.setText(Double.toString(totalWithInterest));
						tfPaymentEvery.setText(Double.toString(periodical));
						lblPaymentEvery.setText("Payment "+payBack);
						tfBalance.setText(Double.toString(totalWithInterest));
						tfPaid.setText("0");
						String message = "Please verify:\n" 
								+ "Loan amount: " + String.format("%.2f", amount) + "\n"
								+ "Interest: " + (interest*100) + "%\n"
								+ "Total payable: " + String.format("%.2f", totalWithInterest) + "\n"
								+ "Payment every " + payBack + ": " + String.format("%.2f", periodical) + "\n"
								+ "\nClick YES to proceed and NO to edit.";
						int reply = JOptionPane.showConfirmDialog(null, message, "Apply loan", JOptionPane.YES_NO_OPTION);
						if(reply == JOptionPane.YES_OPTION) {
							btnCalculate.setEnabled(false);
							btnPay.setEnabled(true);
							tfAmount.setEditable(false);
							cbLoanTerm.setEnabled(false);
							cbPayBack.setEnabled(false);
							JOptionPane.showMessageDialog(null, "Click SUBMIT to proceed or click CANCEL to discard.", "Loan application", JOptionPane.INFORMATION_MESSAGE);
						}
							
					} else {
						JOptionPane.showMessageDialog(null, "Minimum loan amount is PHP 10,000 and maximum amount is PHP 20,000,000", "Warning", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Please enter valid amount.", "Warning", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			
			else if(ae.getSource().equals(btnAccCancel)) {
				System.out.println("acc cancel");
				if(!tfAccID.getText().isEmpty()){
					loadAccData(Integer.parseInt(tfAccID.getText()));
					endAccOperation();
				} else {
					endAccOperation();
					getAccountIDs();
				}
					
				
			} 
			
			
			else if(ae.getSource().equals(btnLoanCancel)) {
				System.out.println("loan cancel");
				modelLoan.removeAllElements();
				endLoanOperation();
				clearLoanData();
				clearAccData();
				getAccountIDs();
			}
		}
	}
	

	
	public void enableLoanInput() {
		tfAmount.setEditable(true);
		cbLoanTerm.setEnabled(true);
		cbPayBack.setEnabled(true);
		btnCalculate.setEnabled(true);
		btnPay.setEnabled(true);
		btnLoanCancel.setEnabled(true);
		btnPay.setText("Submit");
		btnPay.setEnabled(false);
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
		btnCalculate.setEnabled(false);
		btnLoanCancel.setEnabled(false);
		btnPay.setText("Pay");
		btnPay.setEnabled(false);
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
		btnUpdate.setEnabled(true);
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
		btnUpdate.setText("Update");
		btnAccCancel.setEnabled(false);
	}
	
	public void loadLoanData(int accID, int loanID){
		try {
			rs = comm.executeQuery("SELECT * FROM loan_"+ accID +" Where id="+ loanID);
			rs.next();
			viewLoanData();
			btnPay.setEnabled(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void viewLoanData(){
		try {
			tfLoanID.setText(Integer.toString(rs.getInt("id")));
			cbLoanTerm.setSelectedItem(rs.getObject("term"));
			cbPayBack.setSelectedItem(rs.getObject("payback"));
			tfAmount.setText(rs.getString("amount"));
			tfTotalPayable.setText(rs.getString("total"));
			tfPaymentEvery.setText(rs.getString("periodical"));
			tfBalance.setText(rs.getString("balance"));
			tfPaid.setText(rs.getString("paid"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void clearLoanData() {
		tfLoanID.setText("");
		tfAmount.setText("");
		tfTotalPayable.setText("");
		tfPaymentEvery.setText("");
		lblPaymentEvery.setText("Payment :");
		tfBalance.setText("");
		tfPaid.setText("");
		btnPay.setEnabled(false);
	}
	
	public void loadAccData(int id){
		try {
			rs = comm.executeQuery("SELECT * FROM accounts Where id="+id);
			rs.next();
			viewAccData();
			btnUpdate.setEnabled(true);
			btnNewLoan.setEnabled(true);
			btnSelLoan.setEnabled(true);
			btnDelLoan.setEnabled(true);
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
			rs = comm.executeQuery("SELECT * FROM accounts ORDER BY id");
			while(rs.next()){
				modelAcc.addElement(rs.getInt("id") + " - " + rs.getString("lastname") + ", " 
									+ rs.getString("firstname") + " " + rs.getString("middlename"));
			}
			listAcc.setSelectedIndex(0);
			btnUpdate.setEnabled(false);
			btnNewLoan.setEnabled(false);
			btnSelLoan.setEnabled(false);
			btnDelLoan.setEnabled(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void getLoanIDs(){
		try {
			modelLoan.removeAllElements();
			rs = comm.executeQuery("SELECT * FROM loan_"+tfAccID.getText() + " ORDER BY id");
			while(rs.next()){
				modelLoan.addElement(rs.getInt("id") + " - " + rs.getDouble("Amount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		JFrame app = new Loan();
		
	}

}
