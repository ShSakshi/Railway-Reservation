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

public class SignUp extends JPanel implements ActionListener{

	JLabel lsignup;
	JButton btlogin, btregister;
	JPanel pn1, pn2, Pn, pmain;
	CardLayout clo;
	
	//public void init()
	public SignUp()
	{
		
		lsignup=new JLabel("Signing Up Section");
		
		btregister=new JButton("Register");
		btlogin=new JButton("Login");
		
		pn1=new JPanel();
		pn1.setBackground(Color.cyan);
		pn1.add(lsignup);
		
		pn2=new JPanel();
		pn2.setLayout(new GridLayout(1, 2));
		pn2.add(btregister);
		pn2.add(btlogin);
		
		Pn=new JPanel();
		Pn.setLayout(new GridLayout(2,1));
		Pn.add(pn1);
		Pn.add(pn2);
		
		clo=new CardLayout();

		pmain=new JPanel();
		pmain.setLayout(clo);
		pmain.add("Register", new UserInsert());
		pmain.add("Login",new Login());
		
		setLayout(new BorderLayout());
		add(Pn,BorderLayout.NORTH);
		add(pmain,BorderLayout.CENTER);
		
		btregister.addActionListener(this);
		btlogin.addActionListener(this);
	
		
	}
	@Override
	public void actionPerformed(ActionEvent AE) {
		
		JButton src=(JButton) AE.getSource();
		clo.show(pmain, src.getLabel());
	
		validate();
	}

}
