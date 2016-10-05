import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class Loan extends JFrame{
	
	JTextArea taAcc, taLog;
	JLabel lblFirstName, lblMiddleName, lblLastName, lblLoanID, lblAmount, lblBalance, lblPaid;
	JTextField tfFirstName, tfMiddleName, tfLastName, tfLoanID, tfAmount, tfBalance, tfPaid;
	JButton btnMore, btnNext, btnPrev;
	
	private final String dbUser = "root", dbPass = "", 
								dbUrl = "jdbc:mysql://localhost/loan_system?autoReconnect=true&useSSL=false", 
																dbDriver = "com.mysql.jdbc.Driver";
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	
	public Loan(String title) {
		
		
		taAcc = new JTextArea(2, 15);
		taLog = new JTextArea();
		taAcc.append("asaaaaaaaaaaaaaaaaaaaaaaaaaaaad\nsdf\nasd\nasd\nsdf\nasd\nasd\nsdf\nasd\nasd\nsdf\nasd\nasd\nsdf\nasd\n");
		JScrollPane spAcc = new JScrollPane(taAcc);
		JScrollPane spLog = new JScrollPane(taLog);

		lblFirstName = new JLabel("First Name");
		lblMiddleName = new JLabel("Middle Name");
		lblLastName = new JLabel("Last Name");
		lblLoanID = new JLabel("Loan ID");
		lblAmount = new JLabel("Amount");
		lblBalance = new JLabel("Balance");
		lblPaid = new JLabel("Paid");

		tfFirstName = new JTextField();
		tfMiddleName = new JTextField();
		tfLastName = new JTextField();
		tfLoanID = new JTextField();
		tfAmount = new JTextField();
		tfBalance = new JTextField();
		tfPaid = new JTextField();
		
		btnMore = new JButton("More Details");
		btnNext = new JButton("Next");
		btnPrev = new JButton("Previous");
		
		JPanel pCenter = new JPanel(new GridLayout(8, 2));
		pCenter.add(lblLoanID);
		pCenter.add(tfLoanID);
		pCenter.add(lblFirstName);
		pCenter.add(tfFirstName);
		pCenter.add(lblMiddleName);
		pCenter.add(tfMiddleName);
		pCenter.add(lblLastName);
		pCenter.add(tfLastName);
		pCenter.add(lblAmount);
		pCenter.add(tfAmount);
		pCenter.add(lblBalance);
		pCenter.add(tfBalance);
		pCenter.add(lblPaid);
		pCenter.add(tfPaid);
		pCenter.add(btnPrev);
		pCenter.add(btnNext);
		pCenter.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		add(spAcc, BorderLayout.WEST);
		add(pCenter);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(title);
		setSize(600, 300);
		setVisible(true);
		
		try {
			Class.forName(dbDriver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			stmt = conn.createStatement();
			loadData();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void loadData() {
		try {
			rs = stmt.executeQuery("SELECT * FROM loans");
			rs.next();
			tfLoanID.setText(Integer.toString(rs.getInt("id")));
			tfFirstName.setText(rs.getString("firstname"));
			tfMiddleName.setText(rs.getString("middlename"));
			tfLastName.setText(rs.getString("lastname"));
			tfAmount.setText(Double.toString(rs.getDouble("amount")));
			tfBalance.setText(Double.toString(rs.getDouble("balance")));
			tfPaid.setText(Double.toString(rs.getDouble("paid")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		JFrame app = new Loan("Loan System");

	}

}
