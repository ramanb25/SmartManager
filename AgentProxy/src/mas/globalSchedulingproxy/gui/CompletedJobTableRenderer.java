/*package mas.globalSchedulingproxy.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.Format;
import java.text.SimpleDateFormat;
import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.miginfocom.swing.MigLayout;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CompletedJobTableRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{
	private static final long serialVersionUID = 1L;
	//same as cyrrentJobTileCell without dynamic menu
	private JLabel batchID,dueDate,startDate,priorityText,batchNo;
	private final JPanel tile;
//	private JButton more;
	private JobTile jobTileInCell;
	private String PriorityNoText;
	private final Format formatter = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
	protected Logger log=LogManager.getLogger();
    
	public CompletedJobTableRenderer(){
		tile=new JPanel(new MigLayout("",
				"[]10",
				""
				));
		batchID=new JLabel();
		batchID.setName("JobID");
		dueDate=new JLabel();
		dueDate.setName("Due date by customer");
		startDate=new JLabel();
		startDate.setName("Start date by customer");
		
		priorityText=new JLabel();
		priorityText.setName("prioityText");
		batchNo=new JLabel();
		batchNo.setName("BatchNoOfJob");
		


	}
	
	private void updateDataEditor(JobTile feed, boolean isSelected, JTable table) {
		log.info("called updateDataEditor");
		tile.addMouseListener ( new MouseAdapter ()
        {
            @Override
            public void mousePressed ( final MouseEvent e )
            {
     			WebLafGSA.unloadCompletedJobInfoPanel();
     			WebLafGSA.creatCompletedJobInfoPanel(jobTileInCell);
            }
        }
         );
		 
		
		if (isSelected) {
			tile.setBackground(table.getSelectionBackground());
			Component[] comps=tile.getComponents();
			for(int i=0;i<comps.length;i++){
					comps[i].setForeground(Color.WHITE);
			}
			
			
		}else{
			tile.setBackground(table.getSelectionForeground());
			Component[] comps=tile.getComponents();
			for(int i=0;i<comps.length;i++){
					comps[i].setForeground(Color.BLACK);
			}
		}
		
		fireEditingStopped();
	}
	
	@Override
	public Object getCellEditorValue() {
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		//returns component in editatble format. So button will get clicked
		WebLafGSA.unloadCompletedJobInfoPanel();
		JobTile feed = (JobTile)value;
		
		updateDataEditor(feed, true, table);//updateData(feed, isSelected, table); is wrong
		//as isSelected=false initially. So, even if u click, background colour will not change
//		table.setRowHeight(tile.getHeight()); //table row height changed as per height of job tile
		return tile;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		//only renders component. So button will not be clicked
//		WebLafGSA.unloadCurrentJobInfoPanel();
		JobTile feed = (JobTile)value;
		updateDataRenderer(feed, isSelected, table);
		return tile;	
		}

	private void updateDataRenderer(JobTile feed, boolean isSelected,
			JTable table) {
		log.info("called updateDataRenderer");
		this.jobTileInCell = feed;
		
		batchID.setText("Batch ID : "+jobTileInCell.getBatchID());
		dueDate.setText("Due date : "+formatter.format(jobTileInCell.getCustDueDate()));
//		startDate.setText("Start Date : "+formatter.format(jobTileInCell.getCustStartDate()));
//		more.setText("more");
		priorityText.setText("Priority : "+ Integer.toString((int)jobTileInCell.getPriority()));

		PriorityNoText="<html><b>"+Integer.toString((int)jobTileInCell.getPriority())+"</b></html>";
		batchNo.setText("Batch No.: "+jobTileInCell.getBatch().getBatchNumber());
		
		tile.add(batchID);
//		tile.add(more, "align right, wrap");
		tile.add(batchNo,"wrap");
		tile.add(dueDate,"wrap");
//		tile.add(startDate);
		tile.add(priorityText);	
		
		if (isSelected) {
			tile.setBackground(table.getSelectionBackground());
			Component[] comps=tile.getComponents();
			for(int i=0;i<comps.length;i++){
					comps[i].setForeground(Color.WHITE);
			}
			
			
		}else{
			tile.setBackground(table.getSelectionForeground());
			Component[] comps=tile.getComponents();
			for(int i=0;i<comps.length;i++){
					comps[i].setForeground(Color.BLACK);
			}
		}			
	}

}

*/

package mas.globalSchedulingproxy.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import mas.util.AgentUtil;
import net.miginfocom.swing.MigLayout;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CompletedJobTableRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{
	private static final long serialVersionUID = 1L;
	//same as cyrrentJobTileCell without dynamic menu
	private JLabel batchID,dueDate,/*startDate,*/priorityText,batchNo;
	private JPanel tile;
//	private JButton more;
	private JobTile jobTileInCell;
	private String PriorityNoText;
	private final Format formatter = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
	protected Logger log=LogManager.getLogger();
    
	public CompletedJobTableRenderer(){
		tile=new JPanel(new MigLayout("",
				"[]10",
				""
				));
		batchID=new JLabel();
		batchID.setName("JobID");
		dueDate=new JLabel();
		dueDate.setName("Due date by customer");
		/*startDate=new JLabel();
		startDate.setName("Start date by customer");*/
		
		priorityText=new JLabel();
		priorityText.setName("prioityText");
		batchNo=new JLabel();
		batchNo.setName("BatchNoOfJob");
		


	}
	
	private void updateData(JobTile feed, boolean isSelected, JTable table) {
		log.info("updateDataCalled");
		this.jobTileInCell = feed;
		
		batchID.setText("Batch ID : "+jobTileInCell.getBatchID());
		dueDate.setText("Due date : "+formatter.format(jobTileInCell.getCustDueDate()));
//		startDate.setText("Start Date : "+formatter.format(jobTileInCell.getCustStartDate()));
//		more.setText("more");
		priorityText.setText("Priority : "+ AgentUtil.showPriority((int)jobTileInCell.getPriority()));

		PriorityNoText="<html><b>"+AgentUtil.showPriority((int)jobTileInCell.getPriority())+"</b></html>";
		batchNo.setText("Batch No.: "+jobTileInCell.getBatch().getBatchNumber());
		
		tile.add(batchID);
//		tile.add(more, "align right, wrap");
		tile.add(batchNo,"wrap");
		tile.add(dueDate,"wrap");
//		tile.add(startDate);
		tile.add(priorityText);
		
		
	   tile.addMouseListener ( new MouseAdapter ()
        {
            @Override
            public void mousePressed ( final MouseEvent e )
            {
            	log.info("mouse pressed");
     			WebLafGSA.unloadCompletedJobInfoPanel();
     			WebLafGSA.creatCompletedJobInfoPanel(jobTileInCell);
            }
        }
         );
		 
	
		if (isSelected) {
			tile.setBackground(table.getSelectionBackground());
			Component[] comps=tile.getComponents();
			for(int i=0;i<comps.length;i++){
					comps[i].setForeground(Color.WHITE);
			}
			
			
		}else{
			tile.setBackground(table.getSelectionForeground());
			Component[] comps=tile.getComponents();
			for(int i=0;i<comps.length;i++){
					comps[i].setForeground(Color.BLACK);
			}
		}		
	}
	
	@Override
	public Object getCellEditorValue() {
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		//returns component in editatble format. So button will get clicked
//		WebLafGSA.unloadCurrentJobInfoPanel();
		JobTile feed = (JobTile)value;
		log.info("called from editor");
		updateData(feed, true, table);//updateData(feed, isSelected, table); is wrong
		fireEditingStopped();
		//as isSelected=false initially. So, even if u click, background colour will not change
//		table.setRowHeight(tile.getHeight()); //table row height changed as per height of job tile
		return tile;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		//only renders component. So button will not be clicked
		log.info("called from renderer");
		JobTile feed = (JobTile)value;
		updateData(feed, isSelected, table);
		return tile;	
	}

}
