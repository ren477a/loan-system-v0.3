import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class Loan extends JFrame{
	
	JTextArea taAcc, taLog;
	
	JLabel lblName, lblLoanID, lblAmount, lblBalance, lblPaid;
	
	JTextField tfName, tfLoanID, tfAmount, tfBalance, tfPaid;
	
	JButton btnMore, btnNext, btnPrev;
	
	
	public Loan(String title) {
		taAcc = new JTextArea(2, 15);
		taLog = new JTextArea();
		taAcc.append("asaaaaaaaaaaaaaaaaaaaaaaaaaaaad\nsdf\nasd\nasd\nsdf\nasd\nasd\nsdf\nasd\nasd\nsdf\nasd\nasd\nsdf\nasd\n");
		JScrollPane spAcc = new JScrollPane(taAcc);
		JScrollPane spLog = new JScrollPane(taLog);
		
		lblName = new JLabel("Name");
		lblLoanID = new JLabel("Loan ID");
		lblAmount = new JLabel("Amount");
		lblBalance = new JLabel("Balance");
		lblPaid = new JLabel("Paid");

		tfName = new JTextField();
		tfLoanID = new JTextField();
		tfAmount = new JTextField();
		tfBalance = new JTextField();
		tfPaid = new JTextField();
		
		btnMore = new JButton("More Details");
		btnNext = new JButton("Next");
		btnPrev = new JButton("Previous");
		
		JPanel pCenter = new JPanel(new GridLayout(5, 2));
		pCenter.add(lblName);
		pCenter.add(tfName);
		pCenter.add(lblLoanID);
		pCenter.add(tfLoanID);
		pCenter.add(lblAmount);
		pCenter.add(tfAmount);
		pCenter.add(lblBalance);
		pCenter.add(tfBalance);
		pCenter.add(lblPaid);
		pCenter.add(tfPaid);
		
		add(spAcc, BorderLayout.WEST);
		add(pCenter);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(title);
		setSize(500, 500);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		JFrame app = new Loan("Loan System");

	}

}
