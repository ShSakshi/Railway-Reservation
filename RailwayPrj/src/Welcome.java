import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Welcome extends JApplet implements ActionListener{

	JLabel lwelcome;
	JButton btinfo, btenquiry, btsignup;
	static JButton btbooking;
	JPanel pn1, pn2, pn,pmain;
	CardLayout clo;
	
	public void init()
	{
		
		lwelcome=new JLabel("Welcome To Indian Railway Reservation");
		
		btinfo=new JButton("Information");
		btenquiry=new JButton("Enquiry");
		btbooking=new JButton("Booking");
		btsignup=new JButton("Sign Up");
		
		Font F1=new Font("arial",Font.BOLD,50);
		
		lwelcome.setFont(F1);
		lwelcome.setPreferredSize(new Dimension(1000,30));
		
		pn1=new JPanel();
		pn1.setBackground(Color.orange);
		pn1.add(lwelcome);
		
		pn2=new JPanel();
		pn2.setBackground(Color.cyan);
		pn2.setLayout(new GridLayout(1, 4));
		pn2.setPreferredSize(new Dimension(1000,30));
		pn2.add(btinfo);
		pn2.add(btenquiry);
		pn2.add(btbooking);
		pn2.add(btsignup);
		
		pn=new JPanel();
		pn.setLayout(new GridLayout(2,1));
		pn.add(pn1);
		pn.add(pn2);
		
		btbooking.setEnabled(false);
		
		clo=new CardLayout();
		pmain=new JPanel();
		pmain.setBackground(Color.magenta);
		pmain.setLayout(clo);
		pmain.add("Information", new Info());
		pmain.add("Enquiry", new Enquiry());
		pmain.add("Booking", new Booking());
		pmain.add("Sign Up", new SignUp());
		
		setLayout(new BorderLayout());
		add(pn,BorderLayout.NORTH);
		add(pmain,BorderLayout.CENTER);
		
		btbooking.setIcon(new ImageIcon("images.jpg"));
		
		
		btinfo.addActionListener(this);
		btenquiry.addActionListener(this);
		btbooking.addActionListener(this);
		btsignup.addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent AE) {
		
		JButton src=(JButton) AE.getSource();
		clo.show(pmain, src.getLabel());
			

				validate();
	}

}
