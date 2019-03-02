import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TrainSeatsMaster extends JApplet implements ActionListener{
	
	JLabel ltrnid, lAc1, lAc2, lAc3, lgeneral, lsleeper, lcoach, lseatAv, lseatAc1, lseatAc2, lseatAc3, lseatgeneral, lseatsleeper;
	JTextField tAc1, tAc2, tAc3, tgeneral, tsleeper,tseatAc1, tseatAc2, tseatAc3, tseatgeneral, tseatsleeper;
	JButton btinsert;
	JComboBox cbtrnid;
	JPanel p1, p2, p3, p4, p5, p6;
	
	public void init()
	{
		
		ltrnid=new JLabel(" Train Id: ");   
		lcoach=new JLabel("No. Of Coach");
		lAc1=new JLabel("Ac1: ");
		lAc2=new JLabel("Ac2: ");
		lAc3=new JLabel("Ac3: ");
		lgeneral=new JLabel("Sleeper: ");
		lsleeper=new JLabel("General: ");
		lseatAv=new JLabel("No of Seats Available");
		lseatAc1=new JLabel("Ac1: ");
		lseatAc2=new JLabel("Ac2: ");
		lseatAc3=new JLabel("Ac3: ");
		lseatgeneral=new JLabel("Sleeper: ");
		lseatsleeper=new JLabel("General: ");
		
				
	cbtrnid=new JComboBox();
	cbtrnid.addItem("Select Train Id");
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
			Statement stmt=con.createStatement();
			stmt.execute("Use RailwayDb"); 
			ResultSet rs=stmt.executeQuery("Select train_id from TrainMasterTb");
			while(rs.next())
			{
				cbtrnid.addItem(rs.getString(1));
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	tAc1=new JTextField(20);
	tAc2=new JTextField(20);
	tAc3=new JTextField(20);
	tgeneral=new JTextField(20);
	tsleeper=new JTextField(20);
	tseatAc1=new JTextField(20);
	tseatAc2=new JTextField(20);
	 tseatAc3=new JTextField(20);
	 tseatgeneral=new JTextField(20);
	 tseatsleeper=new JTextField(20);
	 
	 btinsert=new JButton("Insert");

	 p1=new JPanel();
	p1.setLayout(new GridLayout(1,2));
	 p1.add(ltrnid);
	 p1.add(cbtrnid);
	 
	 p2=new JPanel();
	 p2.setLayout(new GridLayout(1,1));
	 p2.add(lcoach);
	 
	 p3=new JPanel();
	 p3.setLayout(new GridLayout(5,2));
	 p3.add(lAc1);
	 p3.add(tAc1);
	 
	 p3.add(lAc2);
	 p3.add(tAc2);
	 
	 p3.add(lAc3);
	 p3.add(tAc3);
	 
	 p3.add(lgeneral);
	 p3.add(tgeneral);
	
	 p3.add(lsleeper);
	 p3.add(tsleeper);
	 
	 p4=new JPanel();
	 p4.setLayout(new GridLayout(1,1));
	 p4.add(lseatAv);
	
	 p5=new JPanel();
	 p5.setLayout(new GridLayout(5,2));
	 p5.add(lseatAc1);
	 p5.add(tseatAc1);
	
	 p5.add(lseatAc2);
	 p5.add(tseatAc2);
	
	 p5.add(lseatAc3);
	 p5.add(tseatAc3);

	 p5.add(lseatgeneral);
	 p5.add(tseatgeneral);
	
	 p5.add(lseatsleeper);
	 p5.add(tseatsleeper);
	
	 p6=new JPanel();
	 p6.setLayout(new GridLayout(1,1));
	 p6.add(btinsert);
	
		setLayout(new GridLayout(6,1));
		add(p1);
		add(p2);
		add(p3);
		add(p4);
		add(p5);
		add(p6);
	
	 
	 btinsert.addActionListener(this);
	 
}


	@Override
	public void actionPerformed(ActionEvent AE) {
		Object src=AE.getSource();
		if(src==btinsert)
		{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
				Statement stmt=con.createStatement();
				stmt.executeUpdate("Create database if not exists RailwayDb");
				stmt.execute("Use RailwayDb");   //database selection or open
				stmt.executeUpdate("create table if not exists TrainSeatsMasterTb("
						+"train_id varchar(100),CAc1 int(10), CAc2 int(10), CAc3 int(10), Cgeneral int(10), CSleeper int(10),"
						+ "NoAc1 int(10),NoAc2 int(10),NoAc3 int(10),NoGeneral int(10),NoSleeper int(10), primary key(train_id))");
				
				
				PreparedStatement pstmt=con.prepareStatement("Insert into TrainSeatsMasterTb(train_id,CAc1, CAc2, CAc3 , Cgeneral , CSleeper, NoAc1,NoAc2 ,NoAc3,NoGeneral ,NoSleeper)  values(?,?,?,?,?,?,?,?,?,?,?)");
				
				pstmt.setString(1, cbtrnid.getSelectedItem().toString());
				pstmt.setString(2, tAc1.getText());
				pstmt.setString(3, tAc2.getText());
				pstmt.setString(4, tAc3.getText());
				pstmt.setString(5, tgeneral.getText());
				pstmt.setString(6, tsleeper.getText());
				pstmt.setString(7, tseatAc1.getText());
				pstmt.setString(8, tseatAc2.getText());
				pstmt.setString(9, tseatAc3.getText());
				pstmt.setString(10, tseatgeneral.getText());
				pstmt.setString(11, tseatsleeper.getText());
				
				pstmt.executeUpdate();
				
				stmt.executeUpdate("create table if not exists TrainidBookingTb"
						+"(train_id varchar(255), journeyDate date, Ac1 int, Ac2 int, Ac3 int, General int, Sleeper int)");
				
				
				
				con.close();
			} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}	
			catch (SQLException e) 
		{
			e.printStackTrace();
	}
		}
	}
}