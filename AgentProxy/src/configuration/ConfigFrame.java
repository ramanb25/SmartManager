package configuration;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import starter.AgentStarter;
import net.miginfocom.swing.MigLayout;

import com.alee.extended.breadcrumb.WebBreadcrumbPanel;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.radiobutton.WebRadioButton;
import com.alee.utils.SwingUtils;

/**
 * @author Nikhil Chilwant
 * 
 * For input about Agents MAS container
 *
 */
public class ConfigFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private WebBreadcrumbPanel panel1;

	public ConfigFrame() {
		setLayout(new MigLayout("","65[]","10[10][]"));
		panel1 = new WebBreadcrumbPanel ( new VerticalFlowLayout() );
		add(new JLabel("Choose module to start :"),"wrap");

		final WebRadioButton GSAbtn = new WebRadioButton ( "Shop Floor Manager" );
		panel1.add ( GSAbtn );
		final WebRadioButton machineBtn = new WebRadioButton ( "Machine" ); 
		panel1.add (machineBtn);
		final WebRadioButton customerbtn = new WebRadioButton ( "Customer" ); 
		panel1.add (customerbtn);
		final WebRadioButton allAgentsbtn = new WebRadioButton ( "All" ); 
		panel1.add (allAgentsbtn);
		
		SwingUtils.groupButtons ( panel1 );
		add(panel1, "wrap");

		JButton submit = new JButton();
		submit.setText("Start");
		add(submit);
		

		ActionListener selectionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runner runObj = null;
				if(GSAbtn.isSelected()) {
					runObj = new runner(AgentToStart.GSA);
					dispose();
				}
				else if(machineBtn.isSelected()) {
					runObj = new runner(AgentToStart.Machine);
					dispose();
				}
				else if(customerbtn.isSelected()) {
					runObj = new runner(AgentToStart.customer);
					dispose();
				}
				else if(allAgentsbtn.isSelected()) {
					runObj = new runner(AgentToStart.All);
					dispose();
				}
				
				if(runObj != null) {
					new Thread(runObj).start();
				}
			}
		};
		submit.addActionListener(selectionListener);
		add(submit, "wrap");
		
		ImageIcon img = new ImageIcon("resources/smartManager.png");
		this.setIconImage(img.getImage());
		showGui();
	}

	class runner implements Runnable {

		AgentToStart agent;
		public runner(AgentToStart a) {
			this.agent = a;
		}

		@Override
		public void run() {
			if(agent == AgentToStart.GSA) {
				AgentStarter.start(agent);
			} 
			else if(agent == AgentToStart.All) {
				AgentStarter.start(AgentToStart.All);
			} 
			else if(agent == AgentToStart.customer) {

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						new ShowIPFrame(AgentToStart.customer);
					}
				});
			}
			else if(agent == AgentToStart.Machine) {

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						new ShowIPFrame(AgentToStart.Machine);
					}
				});
			}
		}
	}

	private void showGui() {
		setTitle("Smart Manager :: Configuration");
		setMinimumSize(new Dimension(325, 200));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int)screenSize.getWidth() / 2;
		int centerY = (int)screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}
}
