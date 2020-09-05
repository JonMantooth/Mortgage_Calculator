
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class Mortgage_GUI implements ActionListener{
	
	//frames
	JFrame frame;

	//panels
	JPanel radioPanel;
	
	//radio button groups
	ButtonGroup radioButtons;
	
	//radio buttons
	JRadioButton maturityDate;
	JRadioButton monthlyPayment;
	
	//labels
	JLabel label;
	
	//buttons
	JButton selectButton;
	
	public Mortgage_GUI() {
		//build frame
		frame=new JFrame();
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Mortgage Calculator");
		frame.setSize(600, 400);
		
		//build radio button group
		radioButtons=new ButtonGroup();
		
		//build radio buttons
		maturityDate=new JRadioButton("Calculate Maturity Date");
		maturityDate.setBounds(90, 70, 250, 40);
		monthlyPayment = new JRadioButton("Calculate Monthly Payment");
		monthlyPayment.setBounds(300, 70, 250, 40);
		
		//add radio buttons to radio button group
		radioButtons.add(maturityDate);
		radioButtons.add(monthlyPayment);
		
		//build buttons
		selectButton = new JButton("Select");
		selectButton.setBounds(200, 180, 150, 60);
		selectButton.addActionListener(this);
	
		frame.add(monthlyPayment);
		frame.add(maturityDate);
		frame.add(selectButton);
		frame.setVisible(true);

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Mortgage_GUI();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (maturityDate.isSelected()) {
			new FindMaturityDate_GUI();
			frame.dispose();
		}
		else if (monthlyPayment.isSelected()) {
			new FindMonthlyRate_GUI();
			frame.dispose();
		}
		else {
			
		}
			
			
	}

}
