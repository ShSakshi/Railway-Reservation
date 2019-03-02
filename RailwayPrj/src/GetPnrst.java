
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class GetPnrst extends JPanel implements ActionListener{

	JLabel lgtPnrst, lPnr;
	JTextField tPnr;
	JButton btget, btclear;
	JPanel p1, p2, p3, p4;
	JTable table;
	JScrollPane jsp;
	
	public GetPnrst() 
		//public void t()
		{
		
		lgtPnrst=new JLabel("Get PNR Status");
		lPnr=new JLabel("PNR");
		
		tPnr=new JTextField(20);
		
		btget=new JButton("Get");
		
		p1=new JPanel();
		p1.add(lgtPnrst);
		
		p2=new JPanel();
		p2.setLayout(new GridLayout(1,2));
		p2.add(lPnr);
		p2.add(tPnr);
		
		p3=new JPanel();
		p3.add(btget);
		
		setLayout(new GridLayout(3,1));
		add(p1,BorderLayout.NORTH);
		add(p2);
		add(p3,BorderLayout.SOUTH);
		
		tPnr.requestFocus();
		btget.addActionListener(this);

	}
	@Override
	public void actionPerformed(ActionEvent AE) {
Object src=AE.getSource();
		
		String txt=tPnr.getText().toString();
		
		if(src==btget)
		{
			Object[]colHeads= {"username","Pnr_no","DateOfBooking","source","destination","DateOfJourney","train_id","train_name","class","Toatalseats","fare"};
			Object[][]rowData=null;
				
			String pnr=tPnr.getText().toString();
					
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con;
				try
				{
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
					Statement stmt=con.createStatement();
					stmt.execute("use RailwayDb");
					
					PreparedStatement pstmt=con.prepareStatement("Select count(*) from UseridbookingTb where Pnr_No="+txt+"");
					ResultSet rs1=pstmt.executeQuery();
					int c=0;
					while(rs1.next())
						c=rs1.getInt(1);
					
					if(c==0)
						JOptionPane.showMessageDialog(null, "Informmation Not found");
					
					else
					{
						PreparedStatement pstmt1=con.prepareStatement("Select * from UseridbookingTb where Pnr_No="+txt+"");
						ResultSet rs2=pstmt1.executeQuery();
						
					int i=0;
					rowData=new Object[1][11];
					while(rs2.next())
					{
						
						
						
						rowData[i][0]=rs2.getString(1);
						rowData[i][1]=rs2.getInt(2);
						rowData[i][2]=rs2.getDate(3);
						rowData[i][3]=rs2.getString(4);
						rowData[i][4]=rs2.getString(5);
						rowData[i][5]=rs2.getDate(6);
						rowData[i][6]=rs2.getString(7);
						rowData[i][7]=rs2.getString(8);
						rowData[i][8]=rs2.getString(9);
						rowData[i][9]=rs2.getInt(10);
						rowData[i][10]=rs2.getInt(11);
						
						i++;
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


