import java.awt.Color;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class StudentTable extends JFrame {
	private Container c;
	private JLabel titleLabel,fnLabel,lnLabel,phoneLabel,gpaLabel;
	private JTextField fnTf,lnTf,phoneTf,gpaTf;
	private JButton addButton,updateButton,deleteButton,clearButton;
	
	
	StudentTable(){
		initcomponents();
	}
	public void initcomponents() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,700);
		this.setLocationRelativeTo(null);
		this.setTitle("student table");
		
		c=this.getContentPane();
		c.setLayout(null);
		c.setBackground(Color.lightGray);
		
		
		titleLabel = new JLabel("STUDENT  REGISTRATION");
		titleLabel.setBounds(300,10,250,50);
		c.add(titleLabel);
		
		fnLabel =new JLabel("First Name:  ");
		fnLabel.setBounds(20,80,140,30);
		c.add(fnLabel);
		
		fnTf =new JTextField();
		fnTf.setBounds(100,80,140,30);
		c.add(fnTf);
		
		addButton =new JButton("Add");
		addButton.setBounds(300,80,100,30);
		c.add(addButton);
		
		lnLabel =new JLabel("Last Name: ");
		lnLabel.setBounds(20,120,140,30);
		c.add(lnLabel);
		
		lnTf =new JTextField();
		lnTf.setBounds(100,120,140,30);
		c.add(lnTf);
		

		updateButton =new JButton("Update");
		updateButton.setBounds(300,120,100,30);
		c.add(updateButton);
		
		
		phoneLabel =new JLabel("Phone Num: ");
		phoneLabel.setBounds(20,160,140,30);
		c.add(phoneLabel);
		
		phoneTf =new JTextField();
		phoneTf.setBounds(100,160,140,30);
		c.add(phoneTf);
		

		deleteButton =new JButton("Delete");
		deleteButton.setBounds(300,160,100,30);
		c.add(deleteButton);
		
		gpaLabel =new JLabel("CGPA: ");
		gpaLabel.setBounds(20,200,140,30);
		c.add(gpaLabel);
		
		gpaTf =new JTextField();
		gpaTf.setBounds(100,200,140,30);
		c.add(gpaTf);
		
		clearButton =new JButton("Clear");
		clearButton.setBounds(300,200,100,30);
		c.add(clearButton);
		
		
	}
	
		
public static void main(String[]args) {
	 StudentTable frame=new StudentTable();
	 frame.setVisible(true);
}
}