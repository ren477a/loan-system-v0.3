import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class Loan extends JFrame{
	private JLabel lblSelectAcc, lblSortBy, 								//panel 1
					lblAccDetails, lblAccID, lblFirst, lblMiddle, lblLast, 	//panel 2
					lblSelectLoan,											//panel 3
					lblAmount, lblBalance, lblPaid; 						//panel 4
	private JTextField tfAccID, tfFirst, tfMiddle, tfLast, 	//panel 2
						tfAmount, tfBalance, tfPaid;  		//panel 4
	private JRadioButton rbByID, rbByName;
	private ButtonGroup bgSortBy;
	private JButton btnApply, btnSelAcc, btnDelAcc,			//panel1
					btnUpdate,								//panel2
					btnNewLoan, btnSelLoan, btnDelLoan,		//panel3
					btnPay;									//panel4
	private JList listAcc;
	private DefaultListModel<String> modelAcc;
	private JComboBox<String> cmbAcc, cmbLoanIDs;
	private Vector<Integer> accIDs, loanIDs;
	private DefaultComboBoxModel modelAccIDs, modelloanIDs;

	private Connection conn;
	private Statement comm;
	private ResultSet rs;
        
	public Loan(){
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
		JPanel pnl1Radio = new JPanel(new FlowLayout());
		pnl1Radio.add(lblSortBy);
		pnl1Radio.add(rbByID);
		pnl1Radio.add(rbByName);
		JPanel pnl1North = new JPanel(new GridLayout(2, 1));
		pnl1North.add(lblSelectAcc);
		pnl1North.add(pnl1Radio);
		JPanel pnl1South = new JPanel(new GridLayout(1, 3));
		pnl1South.add(btnApply);
		pnl1South.add(btnSelAcc);
		pnl1South.add(btnDelAcc);
		
		//panel 2
		lblAccDetails = new JLabel("Account details:");
		lblAccID = new JLabel("ID");
		lblFirst  = new JLabel("First Name");
		lblMiddle = new JLabel("Middle Name");
		lblLast = new JLabel("Last Name");
		tfAccID = new JTextField();
		tfFirst = new JTextField();
		tfMiddle = new JTextField();
		tfLast = new JTextField();
		btnUpdate = new JButton("Update");
		JPanel pnl2Center = new JPanel(new GridLayout(4, 1));
		pnl2Center.add(lblAccID);
		pnl2Center.add(tfAccID);
		pnl2Center.add(lblFirst);
		pnl2Center.add(tfFirst);
		pnl2Center.add(lblMiddle);
		pnl2Center.add(tfMiddle);
		pnl2Center.add(lblLast);
		pnl2Center.add(tfLast);
		
		//panel 3
		lblSelectLoanIDs = new JLabel("Select Loan:");
		
		//panel 4
		lblAmount = new JLabel("Amount");
		lblBalance = new JLabel ("Balance");
		lblPaid = new JLabel("Paid");
		tfAmount = new JTextField();
		tfBalance = new JTextField();
		tfPaid = new JTextField();
//		btnNext = new JButton("Next");
//		btnPrev = new JButton("Previous");
		//DATABASE CONNECTION
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/loan_system?autoReconnect=true&useSSL=false", "root", "");
			comm = conn.createStatement();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		modelAcc = new DefaultListModel<String>();
		getAccountIDs();
		listAcc = new JList(modelAcc);
		listAcc.setLayoutOrientation(JList.VERTICAL);
		listAcc.setVisibleRowCount(3);
		JScrollPane scrollAcc = new JScrollPane(listAcc);
		scrollAcc.setPreferredSize(new Dimension(300, 400));
		
		
		loadAccData(-1);
		
		//panel 1
		JPanel pnlAccList = new JPanel(new BorderLayout());
		pnlAccList.add(pnl1North, BorderLayout.NORTH);
		pnlAccList.add(pnl1South, BorderLayout.SOUTH);
		pnlAccList.add(scrollAcc);
		//panel 2
		JPanel pnlInfo = new JPanel(new BorderLayout());
		pnlInfo.add(lblAccDetails, BorderLayout.NORTH);
		pnlInfo.add(btnUpdate, BorderLayout.SOUTH);
		pnlInfo.add(pnl2Center);
		
//		pnlInfo.add(lblSelectLoanIDs);
//		pnlInfo.add(cmbLoanIDs);
//		pnlInfo.add(lblAmount);
//		pnlInfo.add(tfAmount);
//		pnlInfo.add(lblBalance);
//		pnlInfo.add(tfBalance);
//		pnlInfo.add(lblPaid);
//		pnlInfo.add(tfPaid);
//		pnlInfo.add(btnPrev);
//		pnlInfo.add(btnNext);
		//panel 3
		JPanel pnlLoanList = new JPanel();
		//panel 4
		JPanel pnlLoanInfo = new JPanel();
		
		JPanel pnlCenter = new JPanel(new GridLayout(1, 4));
		pnlCenter.add(pnlAccList);
		pnlCenter.add(pnlInfo);
//		pnlCenter.add(pnlLoanList);
//		pnlCenter.add(pnlLoanInfo);
		add(pnlCenter);
		createShowGUI();
		
		
		loadAccData(-1);
	}
	
	public void loadAccData(int id){
		try {
			if(id == -1)
				rs = comm.executeQuery("SELECT * FROM accounts");
			else
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
			System.out.println("ASDASD");
			tfFirst.setText(rs.getString("firstname"));
			tfMiddle.setText(rs.getString("middlename"));
			tfLast.setText(rs.getString("lastname"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createShowGUI(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Loan Management");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	//gets account IDs and add it to the JList for accounts
	public void getAccountIDs(){
		try {
			rs = comm.executeQuery("SELECT * FROM accounts");
			while(rs.next()){
				modelAcc.addElement(rs.getInt("id") + " - " + rs.getString("lastname") + ", " 
									+ rs.getString("firstname") + " " + rs.getString("middlename"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
//	public Vector<Integer> getLoanIDs(){
//		try {
//			loanIDs.removeAllElements();
//			rs = comm.executeQuery("SELECT * FROM loan_"+tfAccID.getText());
//			while(rs.next()){
//				loanIDs.add(rs.getInt("id"));
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return loanIDs;
//	}
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
