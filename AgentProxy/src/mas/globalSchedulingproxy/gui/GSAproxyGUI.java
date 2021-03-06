package mas.globalSchedulingproxy.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import mas.globalSchedulingproxy.agent.GlobalSchedulingAgent;
import mas.jobproxy.Batch;
import mas.util.BatchQueryObject;
import mas.util.TableUtil;
import net.miginfocom.swing.MigLayout;
/**
 * Most of the part is not used. Only few methods are used.
 * @author NikhilChilwant
 *
 */
@SuppressWarnings("serial")
public class GSAproxyGUI extends JFrame{

	private GlobalSchedulingAgent gAgent;

	private JPanel queryJobsPanel;
	private JPanel completedJobsPanel;

	private JTabbedPane tPanes;
	private String[] tabTitles = {"Jobs in the System","Completed Jobs"};
	private JPanel[] panelsForTab;

	private JTable jobsInSystemTable;
	private tableModel jobsQueryTableModel;
	private String[] tableHeaders = {"Job No","Job ID" , "CPN" , "Penalty Rate",
			"Due Date", "Operations"};

	private JTable completedJobsTable;
	private CompletedJobsTableModel completedJobsTableModel;

	private Vector<String> tableHeadersVector;

	private Vector<Object> acceptedJobVector;
	private Vector<Object> completedJobVector; 

	// menu items here
	private JMenuItem menuItemQuery ;
	private int currentSelecetdQueryJob = -1;

	public GSAproxyGUI(GlobalSchedulingAgent gAgent) {

		this.gAgent = gAgent;
		menuItemQuery = new JMenuItem("Query Order");
		menuItemQuery.addActionListener(new menuItemClickListener());

		this.tPanes = new JTabbedPane(JTabbedPane.TOP);
		this.panelsForTab = new JPanel[tabTitles.length];

		for (int i = 0, n = tabTitles.length; i < n; i++ ) {
			panelsForTab[i] = new JPanel(new MigLayout());
		}

		this.queryJobsPanel = new JPanel(new BorderLayout());

		acceptedJobVector = new Vector<Object>();
		tableHeadersVector = new Vector<String>();
		Collections.addAll(tableHeadersVector, tableHeaders);

		this.jobsQueryTableModel = new tableModel();
		this.jobsInSystemTable = new JTable(this.jobsQueryTableModel);

		jobsInSystemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jobsInSystemTable.addMouseListener(new rightClickListener());

		TableUtil.setColumnWidths(jobsInSystemTable);
		this.jobsInSystemTable.setRowHeight(30);
		this.jobsInSystemTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		queryJobsPanel.add(new JScrollPane(jobsInSystemTable),BorderLayout.CENTER);
		panelsForTab[0].add(queryJobsPanel,BorderLayout.CENTER);

		this.tPanes.addTab(tabTitles[0],panelsForTab[0] );

		//---------------------------------------------------------
		initCompletedJobPane();
		panelsForTab[1].add(completedJobsPanel,BorderLayout.CENTER);
		this.tPanes.addTab(tabTitles[1], panelsForTab[1]);

		add(this.tPanes);

		showGui();
	}

	private void initCompletedJobPane() {
		completedJobsPanel = new JPanel(new BorderLayout());
		completedJobVector = new Vector<Object>();

		completedJobsTableModel = new CompletedJobsTableModel();
		completedJobsTable = new JTable(completedJobsTableModel);

		TableUtil.setColumnWidths(completedJobsTable);
		this.completedJobsTable.setRowHeight(30);
		this.completedJobsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		completedJobsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		completedJobsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		completedJobsPanel.add(new JScrollPane(completedJobsTable),BorderLayout.CENTER);
	}

	/**
	 * shows the result of the query for the job from table
	 */
	public static void showQueryResult(BatchQueryObject response) {
		JobQueryReplyFrame reply = new JobQueryReplyFrame(response);
	}

	/**
	 * Runs on EDT
	 * @param j
	 */
	public void addAcceptedJobToList(Batch j) {
		acceptedJobVector.addElement(j);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				TableUtil.setColumnWidths(jobsInSystemTable);
				jobsInSystemTable.revalidate();
				jobsInSystemTable.repaint();
			}
		});
	}

	/** Runs on EDT
	 *  @param j
	 */
	public void addCompletedJob(Batch j) {

		if(acceptedJobVector.contains(j)) {
			acceptedJobVector.removeElement(j);
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					jobsInSystemTable.revalidate();
					jobsInSystemTable.repaint();
				}
			});
		}

		completedJobVector.addElement(j);
		acceptedJobVector.removeElement(j);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				TableUtil.setColumnWidths(completedJobsTable);
				completedJobsTable.revalidate();
				completedJobsTable.repaint();
			}
		});
	}

	class menuItemClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			JMenuItem menu = (JMenuItem) event.getSource();
			if (menu == menuItemQuery) {
				currentSelecetdQueryJob = jobsInSystemTable.getSelectedRow();
				//				gAgent.queryJob((Batch) acceptedJobVector.get(currentSelecetdQueryJob));
			} 
		}
	}

	class rightClickListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			int r = jobsInSystemTable.rowAtPoint(e.getPoint());
			if (r >= 0 && r < jobsInSystemTable.getRowCount()) {
				jobsInSystemTable.setRowSelectionInterval(r, r);
			} else {
				jobsInSystemTable.clearSelection();
			}

			int rowindex = jobsInSystemTable.getSelectedRow();
			if (rowindex < 0)
				return;
			if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
				JPopupMenu popup = createPopUpMenu();

				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	private JPopupMenu createPopUpMenu(){
		JPopupMenu menu = new JPopupMenu();

		menu.add(menuItemQuery);

		return menu;
	}

	private void showGui() {
		setTitle("Smart Manager :: "+"Shop Floor Manager");
		setResizable(false);
		setResizable(false);
		setPreferredSize(new Dimension(800,600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int)screenSize.getWidth() / 2;
		int centerY = (int)screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}

	class tableModel extends AbstractTableModel {

		@Override
		public int getColumnCount() {
			return tableHeadersVector.size();
		}

		@Override
		public int getRowCount() {
			return acceptedJobVector.size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			Object value;
			Batch j = (Batch) acceptedJobVector.get(row);
			switch(col) {
			case 0:
				value =j.getBatchNumber();
				break;
			case 1:
				value = j.getBatchId();
				break;
			case 2:
				value = j.getCPN();
				break;
			case 3:
				value = j.getPenaltyRate();
				break;
			case 4:
				value = j.getDueDateByCustomer();
				break;
			case 5:
				value = j.getFirstJob().getOperations();
				break;
			default:
				value = "not_found";
				break;
			}
			return value;
		}

		@Override
		public String getColumnName(int column) {
			return tableHeadersVector.get(column);
		}
	}

	class CompletedJobsTableModel extends AbstractTableModel {

		@Override
		public int getColumnCount() {
			return tableHeadersVector.size();
		}

		@Override
		public int getRowCount() {
			return completedJobVector.size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			Object value;
			Batch j = (Batch) completedJobVector.get(row);
			switch(col) {
			case 0:
				value = j.getBatchNumber();
				break;
			case 1:
				value = j.getBatchId();
				break;
			case 2:
				value = j.getCPN();
				break;
			case 3:
				value = j.getPenaltyRate();
				break;
			case 4:
				value = j.getDueDateByCustomer();
				break;
			case 5:
				value = j.getFirstJob().getOperations();
				break;
			default:
				value = "null";
				break;
			}
			return value;
		}

		@Override
		public String getColumnName(int column) {
			return tableHeadersVector.get(column);
		}
	}

}
