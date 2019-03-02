import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserInsert extends JPanel implements ActionListener{
	
	JLabel luserTb, lusernm, lpassword, lquestion, lanswer;
	JTextField tusernm, tpassword, tanswer;
	JButton btsubmit;
	JComboBox cbquestion;
	
	//public void init()
	public UserInsert()
	{
		luserTb=new JLabel("User Table ");
		lusernm=new JLabel("User Name: ");
		lpassword=new JLabel("Password: ");
		lquestion=new JLabel("Question: ");
		lanswer=new JLabel("Answer: ");
		
		tusernm=new JTextField(20);
		tpassword=new JTextField(20);
		tanswer=new JTextField(20);
		
		btsubmit=new JButton("Submit");
		
		cbquestion=new JComboBox();
		cbquestion.addItem("Select Question");
		cbquestion.addItem("What your Favourite Color?");
		cbquestion.addItem("What is your Hobby?");
		cbquestion.addItem("Where is your Birth place? ");
		cbquestion.addItem("What is your Favourite Teacher name? ");
		cbquestion.addItem("What is your Kindergarden School name? ");
		cbquestion.addItem("What is your BestFriend Favourite Color? ");
		
		setLayout(new FlowLayout());
		add(luserTb);
		add(lusernm);
		add(tusernm);
		
		add(lpassword);
		add(tpassword);
		
		add(lquestion);
		add(cbquestion);
		
		add(lanswer);
		add(tanswer);
		
		add(btsubmit);
		btsubmit.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
				Statement stmt=con.createStatement();
				stmt.executeUpdate("Create database if not exists StudentDb");
				stmt.execute("Use RailwayDb");   //database selection or open
				stmt.executeUpdate("create table if not exists UserTb(username varchar(50),password varchar(50),question varchar(50), answer varchar(100), primary key(username))");
				
				PreparedStatement pstmt=con.prepareStatement("Insert into UserTb(username,password,question,answer) values(?,?,?,?)");
				
				pstmt.setString(1, tusernm.getText());
				pstmt.setString(2,tpassword.getText());
				pstmt.setString(3,cbquestion.getSelectedItem().toString());
				pstmt.setString(4,tanswer.getText());
				
				pstmt.executeUpdate();
				
				
				String tbname=tusernm.getText()+"Tb";
				stmt.executeUpdate("create table if not exists "+tbname
						+"(PnrNo int , name varchar(255),age int, gender varchar(10), category varchar(50))");
		
				stmt.executeUpdate("create table if not exists UseridBookingTb(username varchar(50), Pnr_No int auto_increment, DateOfBooking date, source varchar(100), destination varchar(100), DateOfJourney date, train_id varchar(100), train_name varchar(100), class varchar(100), Totalseats int, fare int, primary key(Pnr_No) )");
		
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e1) {
	

	}

		
	}

	
}
