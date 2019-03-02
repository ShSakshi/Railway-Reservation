import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SummaryChart extends JPanel implements ActionListener{

	JComboBox cbtrnid,dd,mm,yy,cbcoach;
	JButton btgetchart;
	JPanel p1,p2,p3;
	JTable table;
	JScrollPane jsp;

	//public void init()
	public SummaryChart()
	{
	setLayout(new FlowLayout());
	btgetchart=new JButton("Get Chart");
	
	dd=new JComboBox();
	dd.addItem("DD");
	for(int i=1;i<=31;i++)
		dd.addItem(""+i);
	
	mm=new JComboBox();
	mm.addItem("MM");
	mm.addItem("January");
	mm.addItem("Febuary");
	mm.addItem("March");
	mm.addItem("April");
	mm.addItem("May");
	mm.addItem("June");
	mm.addItem("July");
	mm.addItem("August");
	mm.addItem("September");
	mm.addItem("October");
	mm.addItem("November");
	mm.addItem("December");

	yy=new JComboBox();
	yy.addItem("YY");
	for(int i=2017;i<=2022;i++)
		yy.addItem(""+i);
	
	cbcoach=new JComboBox();
	cbcoach.addItem("select coach");
	cbcoach.addItem("ac1");
	cbcoach.addItem("ac2");
	cbcoach.addItem("ac3");
	cbcoach.addItem("sleeper");
	cbcoach.addItem("general");
	
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
			
			e.printStackTrace();
		}
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}

	p1=new JPanel();
	p1.setLayout(new GridLayout(1,3));
	p1.add(dd);
	p1.add(mm);
	p1.add(yy);
	
	p2=new JPanel();
	p2.add(cbtrnid);
	p2.add(cbcoach);
	p2.add(p1);
	p2.add(btgetchart);
	
	add(p2);
	
	btgetchart.addActionListener(this);

	
	}
	
	@Override
	public void actionPerformed(ActionEvent AE) {
		Object src=AE.getSource();
		
		Date dt=new Date(Integer.parseInt(yy.getSelectedItem().toString())-1900,mm.getSelectedIndex()+1,Integer.parseInt(dd.getSelectedItem().toString()));
		
		if(src==btgetchart)
		{
			
			Object[]colHeads= {"Name","source","destination","age","gender","category"};
			Object[][]rowData=null;
			
			try 
			{
				Class.forName("com.mysql.jdbc.Driver");
				try 
				{
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
					Statement stmt=con.createStatement();
					stmt.execute("use RailwayDb");

					
					PreparedStatement pstmt2=con.prepareStatement("Select count(*) from UseridbookingTb where train_id=? and DateOfJourney=? and class=?");
					pstmt2.setString(1, cbtrnid.getSelectedItem().toString());
					pstmt2.setDate(2, dt);
					pstmt2.setString(3, cbcoach.getSelectedItem().toString());
					ResultSet rs2=pstmt2.executeQuery();
					
					int c=0;
					
					while(rs2.next())
					{
						c=rs2.getInt(1);
					}
					
					if(c==0)
					{
						JOptionPane.showMessageDialog(null, "No Chart Found");
					}
					else
					{
					
						PreparedStatement pstmt1=con.prepareStatement("Select * from UseridbookingTb where train_id=? and DateOfJourney=? and class=?");
						pstmt1.setString(1, cbtrnid.getSelectedItem().toString());
						pstmt1.setDate(2, dt);
						pstmt1.setString(3, cbcoach.getSelectedItem().toString());
						ResultSet rs1=pstmt1.executeQuery();
						
						while(rs1.next())
						{
							String userid=rs1.getString(1);
							int pnr=rs1.getInt(2);
							
							String usrtb=userid+"Tb";
							
							PreparedStatement pstmt3=con.prepareStatement("Select count(*) from "+usrtb+" where pnr=?");
							pstmt3.setInt(1, pnr);
							ResultSet rs3=pstmt3.executeQuery();
							
							
							while(rs3.next())
							{
								c+=rs3.getInt(1);
							}
						}
						rowData=new Object[c][6];
						
						pstmt1=con.prepareStatement("Select * from UseridbookingTb where train_id=? and DateOfJourney=? and class=?");
						pstmt1.setString(1, cbtrnid.getSelectedItem().toString());
						pstmt1.setDate(2, dt);
						pstmt1.setString(3, cbcoach.getSelectedItem().toString());
						rs1=pstmt1.executeQuery();
						
						int i=0;
						
						while(rs1.next())
						{
							String userid=rs1.getString(1);
							int pnr=rs1.getInt(2);
							String source=rs1.getString(4);
							String dest=rs1.getString(5);
							String coach=rs1.getString(9);
							
							String usrtb=userid+"Tb";
							
							PreparedStatement pstmt3=con.prepareStatement("select * from "+usrtb+" where PnrNo=?");
							pstmt3.setInt(1, pnr);
							ResultSet rs3=pstmt3.executeQuery();
							
								while(rs3.next())
							{

									rowData[i][0]=rs3.getString(2);
									rowData[i][1]=source;
									rowData[i][2]=dest;
									rowData[i][3]=rs3.getInt(3);
									rowData[i][4]=rs3.getString(4);
									rowData[i][5]=rs3.getString(5);
									i++;
									
							}
							
						}
						
						table=new JTable(rowData,colHeads);
						
						jsp=new JScrollPane(table);
						add(jsp);
						
						validate();
						
					}	
					
					con.close();			
				 
				}
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
				
			}
		}
	}
	

		
	}
