import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Booking extends JPanel implements ActionListener{

	JLabel lbooking;
	JButton  btreservation,  btcancel;
	JPanel pn1, pn2, Pn, pmain;
	CardLayout clo;
	
	public Booking()
	//public void init()
	{
		
		lbooking=new JLabel("Booking Section");
		
		btreservation=new JButton("Reservation");
		btcancel=new JButton("Cancel Ticket");
		
		pn1=new JPanel();
		pn1.setBackground(Color.green);
		pn1.add(lbooking);
		
		pn2=new JPanel();
		pn2.setLayout(new GridLayout(1, 2));
		pn2.add(btreservation);
		pn2.add(btcancel);
		
		Pn=new JPanel();
		Pn.setLayout(new GridLayout(2,1));
		Pn.add(pn1);
		Pn.add(pn2);
		
		clo=new CardLayout();

		pmain=new JPanel();
		pmain.setLayout(clo);
		pmain.add("Reservation", new  Reservation());
		pmain.add("Cancel Ticket", new TicketCancel());
		
		setLayout(new BorderLayout());
		add(Pn,BorderLayout.NORTH);
		add(pmain,BorderLayout.CENTER);
		
		btreservation.addActionListener(this);
		btcancel.addActionListener(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent AE) {
		JButton src=(JButton) AE.getSource();
		clo.show(pmain, src.getLabel());
	
		validate();		
	}

}
