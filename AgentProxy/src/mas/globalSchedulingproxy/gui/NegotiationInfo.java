package mas.globalSchedulingproxy.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import mas.globalSchedulingproxy.agent.GlobalSchedulingAgent;
import mas.jobproxy.Batch;
import mas.jobproxy.job;
import mas.jobproxy.jobOperation;
import mas.util.AgentUtil;
import mas.util.DateLabelFormatter;
import mas.util.TableUtil;
import mas.util.formatter.doubleformatter.FormattedDoubleField;
import mas.util.formatter.integerformatter.FormattedIntegerField;
import mas.util.formatter.stringformatter.FormattedStringField;
import net.miginfocom.swing.MigLayout;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import uiconstants.Labels;

import com.alee.extended.label.WebHotkeyLabel;
import com.alee.laf.panel.WebPanel;
/**
 * Shows  negotiation details 
 * @author NikhilChilwant
 *
 */
public class NegotiationInfo {

	private static final long serialVersionUID = 1L;
	private GlobalSchedulingAgent gAgent;

	private WebPanel panel1, panel2, mainPanel;
	private JPanel operationPanel;
	private JPanel btnPanel;
	private JButton confirmJob;
	private JButton negotiateJob;
	public UtilDateModel dateModel;
	public Properties dateProperties;
	private JDatePanelImpl datePanel ;
	private JDatePickerImpl datePicker;
	private JSpinner timeSpinner;

	private JLabel lblHeading, lblOrderDetailsHeading;
	private JLabel lblJobID;
	private JLabel lblJobNo;
	private JLabel lblCPN;
	private JLabel lblDueDate;
	private JLabel lblOpsHeading;
	private JLabel lblPenalty;
	private JLabel lblWaitingTimeHeading;
	private JLabel lblBatchSize;
	private JLabel lblCustomerIdHeading;

	private WebHotkeyLabel lblCustomerId;
	private FormattedStringField txtJobID;
	private FormattedIntegerField txtJobNo;
	private FormattedDoubleField txtCPN;
	private FormattedIntegerField txtNumOps;
	private JTextField txtWaitingTime;
	private FormattedIntegerField txtBatchSize;
	private FormattedDoubleField txtPenaltyRate;

	private Batch populatingBatch;
	private boolean dataOk = true;

	private Logger log;
	private job generatedJob;

	public NegotiationInfo(GlobalSchedulingAgent cAgent, Batch passedBatch) {

		log = LogManager.getLogger();

		this.populatingBatch = passedBatch;
		if(populatingBatch != null) {
			generatedJob = populatingBatch.getFirstJob();
		}

		this.panel1 = new WebPanel(new MigLayout("","[]50[]","[]10[][][]"));
		this.panel2 = new WebPanel(new MigLayout("","[]45[]","[]10[][][][]"));
		
		this.mainPanel = new WebPanel(new MigLayout("","","[]20[]20[]"));
		
		btnPanel = new JPanel(new FlowLayout());
		operationPanel = new JPanel(new MigLayout());
		this.gAgent = cAgent;
		this.negotiateJob = new JButton("Send");

		dateModel = new UtilDateModel();

		dateProperties = new Properties();
		dateProperties.put("text.today", "Today");
		dateProperties.put("text.month", "Month");
		dateProperties.put("text.year", "Year");

		if(populatingBatch != null) {
			Calendar dudate = Calendar.getInstance();
			dudate.setTime(populatingBatch.getDueDateByCustomer());

			dateModel.setDate(dudate.get(Calendar.YEAR),
					dudate.get(Calendar.MONDAY),
					dudate.get(Calendar.DAY_OF_MONTH));

			dateModel.setSelected(true);
		}

		datePanel = new JDatePanelImpl(dateModel, dateProperties);

		datePicker = new JDatePickerImpl(datePanel,
				new DateLabelFormatter());

		timeSpinner = new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
		timeSpinner.setEditor(timeEditor);
		timeSpinner.setValue(new Date(populatingBatch.getExpectedDueDate()));

		//		try {
		//			plusButtonIcon = ImageIO.read(new File("resources/plusbutton.png"));
		//			btnOperationPlus = new JButton(new ImageIcon(plusButtonIcon));
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}

		this.lblHeading = new JLabel(Labels.CustomerLabels.customerDetailsHeading);
		this.lblOrderDetailsHeading = new JLabel(Labels.CustomerLabels.orderDetailsHeading);
		this.lblCPN = new JLabel(Labels.CustomerLabels.jobPriority);
		this.lblDueDate = new JLabel(Labels.CustomerLabels.jobDueDate);
		this.lblJobID = new JLabel(Labels.CustomerLabels.OrderID);
		this.lblJobNo = new JLabel(Labels.CustomerLabels.OrderNo);
		this.lblOpsHeading = new JLabel(Labels.CustomerLabels.jobOperationHeading);
		this.lblPenalty = new JLabel(Labels.CustomerLabels.jobPenalty);
		this.lblBatchSize = new JLabel(Labels.CustomerLabels.batchSize);
		this.lblCustomerIdHeading = new JLabel("Customer Id  ");
		this.lblCustomerId = new WebHotkeyLabel();

		this.lblWaitingTimeHeading = new JLabel("Due date by customer");
		this.txtWaitingTime = new JTextField(Labels.defaultJTextSize*2);

		this.txtCPN = new FormattedDoubleField();
		txtCPN.setColumns(Labels.defaultJTextSize);

		this.txtJobID = new FormattedStringField();
		txtJobID.setColumns(Labels.defaultJTextSize);

		//		this.txtJobNo = new FormattedIntegerField();
		//		txtJobNo.setColumns(Labels.defaultJTextSize);

		this.txtNumOps = new FormattedIntegerField();
		txtNumOps.setColumns(Labels.defaultJTextSize/2);

		this.txtPenaltyRate = new FormattedDoubleField();
		txtPenaltyRate.setColumns(Labels.defaultJTextSize);

		this.txtBatchSize = new FormattedIntegerField();
		txtBatchSize.setColumns(Labels.defaultJTextSize/2);

		this.lblHeading.setFont(TableUtil.headings);
		this.lblHeading.setForeground(Color.decode("#3B5998"));
		
		this.lblOrderDetailsHeading.setFont(TableUtil.headings);
		this.lblOrderDetailsHeading.setForeground(Color.decode("#3B5998"));
		
		panel1.add(lblHeading,"wrap");

		panel1.add(lblCustomerIdHeading);
		panel1.add(lblCustomerId,"wrap");

		panel1.add(lblCPN);
		panel1.add(txtCPN,"wrap");

	

/*		myPanel.add(lblDueDate);
		myPanel.add(datePicker);
		myPanel.add(timeSpinner,"wrap");

		//		operationPanel.add(txtNumOps);
		//		operationPanel.add(btnOperationPlus,"wrap");
		//		btnOperationPlus.addActionListener(new AddOperationListener());
		myPanel.add(lblOpsHeading,"wrap");
		myPanel.add(operationPanel,"wrap");

//		btnPanel.add(confirmJob);
		btnPanel.add(n	egotiateJob);

		myPanel.add(btnPanel);*/
		Border borderLine=BorderFactory.createLineBorder(Color.decode("#3B5998"));
		panel1.setBorder(borderLine);
		mainPanel.add(panel1,"span, growx, wrap");
		
		panel2.add(lblOrderDetailsHeading,"wrap");
		
		panel2.add(lblJobID);
		panel2.add(txtJobID,"wrap");

		panel2.add(lblBatchSize);
		panel2.add(txtBatchSize,"wrap");
		
		panel2.add(lblPenalty);
		panel2.add(txtPenaltyRate,"wrap");

		panel2.add(lblOpsHeading);
		panel2.add(operationPanel,"wrap");

		panel2.add(lblWaitingTimeHeading);
		panel2.add(txtWaitingTime,"span, growx, wrap");

		panel2.add(lblWaitingTimeHeading);
		panel2.add(txtWaitingTime,"wrap");

		panel2.add(lblDueDate);
		panel2.add(datePicker, "split 2");
		panel2.add(timeSpinner,"wrap");
		
		panel2.setBorder(borderLine);
//		btnPanel.add(confirmJob);
		btnPanel.add(negotiateJob);

		mainPanel.add(panel2,"wrap");
		mainPanel.add(btnPanel);
		
		buttonListener clickListener = new buttonListener();
//		confirmJob.addMouseListener(clickListener);
		negotiateJob.addMouseListener(clickListener);

		_populate();
	}

	private void _populate() {
		if(populatingBatch != null) {
			lblCustomerId.setText(populatingBatch.getCustomerId());

			txtJobID.setText(populatingBatch.getBatchId());
			txtJobID.setEnabled(false);

			//			txtJobNo.setText(String.valueOf(populatingBatch.getBatchNumber()));
			//			txtJobNo.setEnabled(false);

			txtWaitingTime.setText(String.valueOf(populatingBatch.getDueDateByCustomer())) ;
			txtWaitingTime.setEnabled(false);

			txtCPN.setText(AgentUtil.showPriority((int)populatingBatch.getCPN()));
			txtCPN.setEnabled(false);
			txtPenaltyRate.setText(String.valueOf(populatingBatch.getPenaltyRate()));
			txtPenaltyRate.setEnabled(false);

			txtNumOps.setText(String.valueOf(populatingBatch.getFirstJob().getOperations().size()));

			Calendar c1 = Calendar.getInstance();
			c1.setTime(new Date());


			datePicker.getModel().
			setDate(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH));

			txtBatchSize.setText(String.valueOf(populatingBatch.getBatchCount()));
			txtBatchSize.setEnabled(false);
			
			ArrayList<jobOperation> ops = populatingBatch.getFirstJob().getOperations();
			operationPanel.removeAll();
			for(int i = 0; i < ops.size(); i++ ) {
				WebHotkeyLabel lblOp = new WebHotkeyLabel(ops.get(i).getJobOperationType());
				operationPanel.add(lblOp,"span " + ops.size());
			}
		}
	}

	private void createJobFromParams() {
		boolean x2 = true;
		x2 = checkDueDate();
		dataOk = x2;

		if(dataOk) {
			dataOk = dataOk & checkBatchSize();
		}
	}

	private boolean checkBatchSize() {
		boolean status = true;
		if(! txtBatchSize.getText().matches("-?\\d+?") ) {

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					JOptionPane.showMessageDialog(null,
							"Invalid input for batch size.",  "Error" , JOptionPane.ERROR_MESSAGE );
				}
			});

			status = false;
		}else {
			populatingBatch.setBatchId(populatingBatch.getBatchId());
			populatingBatch.clearAllJobs();
			int bSize = Integer.parseInt(txtBatchSize.getText());
			ArrayList<job> jobs = new ArrayList<job>();
			for(int i = 0; i < bSize ; i++ ) {
				job j = new job(generatedJob);
				jobs.add(j);
			}
			populatingBatch.setJobsInBatch(jobs);
		}

		return status;
	}

	private boolean checkDueDate() {
		boolean status = true;
		Date time = (Date) timeSpinner.getValue();
		Date jobDueDate = (Date) datePicker.getModel().getValue();

		if(time == null || jobDueDate == null) {

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					JOptionPane.showMessageDialog(null,
							"Invalid input for due date !!", "Error" , JOptionPane.ERROR_MESSAGE );
				}
			});

			status = false;
		} else {

			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(time);
			c2.setTime(jobDueDate);

			Calendar calTime = Calendar.getInstance();
			calTime.set(
					c2.get(Calendar.YEAR), c2.get(Calendar.MONTH),c2.get(Calendar.DAY_OF_MONTH),
					c1.get(Calendar.HOUR_OF_DAY), c1.get(Calendar.MINUTE), c1.get(Calendar.SECOND));

			if(calTime.getTimeInMillis() < System.currentTimeMillis()) {

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JOptionPane.showMessageDialog(null,
								"Please enter a due date after current Date.", "Error" , JOptionPane.ERROR_MESSAGE );
					}
				});

				status = false;
			}else {
				populatingBatch.setExpectedDueDate(calTime.getTime().getTime());
			}
		}
		return status;
	}

	class buttonListener extends MouseAdapter {

/*		@Override
		public void actionPerformed(ActionEvent e) {
			log.info("Anand party jaa raha hai");
			// handle create job button pressed event
			if(e.getSource().equals(negotiateJob)) {

				createJobFromParams();
				if(dataOk) {
					gAgent.negotiateJob(populatingBatch);
					NegotiationJobTileTableModel negotiationRenderer=
							(NegotiationJobTileTableModel)(WebLafGSA.
									getNegotiationJobListTable().getModel());
					negotiationRenderer.removeJob(populatingBatch);
					WebLafGSA.getNegotiationJobListTable().revalidate();
					WebLafGSA.getNegotiationJobListTable().repaint();
					WebLafGSA.unloadNegotiationInfoPanel();
				}
			}
		}*/

		@Override
		public void mouseClicked(MouseEvent e) {
			log.info(e);

			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// handle create job button pressed event
			if(e.getSource().equals(negotiateJob)) {

				createJobFromParams();
				if(dataOk) {
					gAgent.negotiateBatch(populatingBatch);
					NegotiationJobTileTableModel negotiationTableModel=
							(NegotiationJobTileTableModel)(WebLafGSA.
									getNegotiationJobListTable().getModel());
					negotiationTableModel.removeBatch(populatingBatch);
					WebLafGSA.getNegotiationJobListTable().revalidate();
					WebLafGSA.getNegotiationJobListTable().repaint();
					WebLafGSA.unloadNegotiationInfoPanel();
				}
			}
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}
	}

	public WebPanel getPanel() {
		return mainPanel;
	};

}
