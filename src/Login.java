import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.*;
import javax.imageio.*;

public class Login extends JFrame {
	
	JLabel lblUsername, lblPassword, lblName;
	JTextField txtUsername;
	JPasswordField psdPassword;
	JButton btnLogin,btnExit;
	JPanel pnlLogin,pnlText,pnlButtons,pnlUser,pnlPass;
	private Connection conn;
	private Statement comm;
	private ResultSet rs;
	String user,pass;
	private ButtonListener btn1;
	private static JFrame app ;
	
	public Login(){
		btn1 = new ButtonListener();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/loan_system?autoReconnect=true&useSSL=false", "root", "");
			comm = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		lblUsername = new JLabel("Username");
		lblPassword = new JLabel("Password");
		lblName = new JLabel ("Taisho Lending Company", JLabel.CENTER);
		txtUsername = new JTextField();
		psdPassword = new JPasswordField();
		btnLogin = new JButton("Login");
		btnExit = new JButton("Exit");
		btnLogin.addActionListener(btn1);
		btnExit.addActionListener(btn1);
		pnlText = new JPanel(new GridLayout(4,1,5,5));
		pnlText.setBorder(new EmptyBorder(0, 75, 30,75));
		pnlUser = new JPanel(new GridLayout(2,1));
		pnlUser.add(lblUsername);
		pnlUser.add(txtUsername);
		pnlPass = new JPanel(new GridLayout(2,1));
		pnlPass.add(lblPassword);
		pnlPass.add(psdPassword);
		pnlButtons = new JPanel(new GridLayout(2,1,5,5));
		pnlButtons.add(btnLogin);
		pnlButtons.add(btnExit);
		pnlText.add(lblName);
		pnlText.add(pnlUser);
		pnlText.add(pnlPass);
		pnlText.add(pnlButtons);
		pnlUser.setBackground(new Color(173, 216, 230));
		pnlPass.setBackground(new Color(173, 216, 230));
		pnlText.setBackground(new Color(173, 216, 230));
		pnlButtons.setBackground(new Color(173, 216, 230));
		add(pnlText);

		
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if(ae.getSource().equals(btnLogin)){
					try {
						rs = comm.executeQuery("Select * from adminaccount");
						while(rs.next()){
							user = rs.getString("username");
							pass = rs.getString("password");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(user);
					System.out.println(pass);
					if(txtUsername.getText().equals(user) && psdPassword.getText().equals(pass)){
						JFrame connector = new Loan();
						connector.setVisible(true);
						app.setVisible(false);
						
					}
					else{
						JOptionPane.showMessageDialog(null, "Wrong Account", "WARNING", JOptionPane.ERROR_MESSAGE);
					}
			}
			else 
				if(ae.getSource().equals(btnExit)){
					System.out.println("exit");
					System.exit(0);
					
				}
		}
		}
	
	public static void main(String[] args){
		app = new Login();
		app.setDefaultCloseOperation(EXIT_ON_CLOSE);
		app.setTitle("Taisho Lending Company");
		app.setSize(350, 400);
		app.setLocationRelativeTo(null);
		app.setVisible(true);
		app.setBackground(new Color(173, 216, 230));
		
		
	}
}
