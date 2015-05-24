package mas.globalSchedulingproxy.plan;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import mas.globalSchedulingproxy.database.BatchDataBase;
import mas.globalSchedulingproxy.database.CustomerBatches;
import mas.globalSchedulingproxy.database.UnitBatchInfo;
import mas.jobproxy.jobOperation;
import mas.util.ID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import bdi4jade.core.BeliefBase;
import bdi4jade.plan.PlanBody;
import bdi4jade.plan.PlanInstance;
import bdi4jade.plan.PlanInstance.EndState;
import jade.core.behaviours.Behaviour;
/**
 * Loads details like number of operations to be done on batch
 * @author NikhilChilwant
 *
 */
public class LoadBatchOperationDetailsPlan extends Behaviour implements PlanBody{

	private static final long serialVersionUID = 1L;
	private BeliefBase bfBase;
	private Logger log;
	private boolean done = false;
	private String path;
	private BatchDataBase db;
	private String customerID;

	@Override
	public EndState getEndState() {
		return (done ? EndState.SUCCESSFUL : null);
	}

	@Override
	public void init(PlanInstance pInstance) {
		log = LogManager.getLogger();
		bfBase = pInstance.getBeliefBase();
		db = new BatchDataBase();
		path = "resources/GSA/database/";
	}

	@Override
	public void action() {

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		XSSFWorkbook wb;

		for (File file : listOfFiles) {
			if (file.isFile()) {
				try {
					FileInputStream fileIs = new FileInputStream(file);	
					wb = new XSSFWorkbook(fileIs);

					int NumJobs = wb.getNumberOfSheets();
					String fName = file.getName().split("\\.")[0];
					customerID=fName;
					XSSFSheet localSheet;
					for(int i = 0 ; i < NumJobs ; i++) {
						localSheet = wb.getSheetAt(i);
						db.put(fName, readSheet(localSheet));
						log.info("Database loaded in gsa for : " + fName);
					}
					log.info("Database fully loaded !!");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		bfBase.updateBelief(ID.GlobalScheduler.BeliefBaseConst.batchDatabase, db);
		done = true;
	}

	private CustomerBatches readSheet(XSSFSheet currSheet) {
		int priority=-1;
		CustomerBatches allBatches = new CustomerBatches();

		Iterator<Row> rows = currSheet.rowIterator();
		
		XSSFRow row = (XSSFRow) rows.next();
		Iterator<Cell> cellsitr = row.cellIterator();
		int count=0;
		while(cellsitr.hasNext()){
			XSSFCell cell = (XSSFCell) cellsitr.next();

			if(count == 1) {
				priority=(int)cell.getNumericCellValue();
			}
			count ++;
		}
		HashMap<String, Integer> priorityArray=(HashMap<String, Integer>)bfBase.
				getBelief(ID.GlobalScheduler.BeliefBaseConst.customerPriority).getValue();
		
		priorityArray.put(customerID, priority);
		bfBase.updateBelief(ID.GlobalScheduler.BeliefBaseConst.customerPriority, priorityArray);
		
		 priorityArray=(HashMap<String, Integer>)bfBase.
					getBelief(ID.GlobalScheduler.BeliefBaseConst.customerPriority).getValue();
		
		while(rows.hasNext()) {
     	    row = (XSSFRow) rows.next();
		    Iterator<Cell> cells = row.cellIterator();
		    
			count = 0; 
			String jobId = null;
			ArrayList<jobOperation> jobOpsList = new ArrayList<jobOperation>();

			while(cells.hasNext()) {
				XSSFCell cell = (XSSFCell) cells.next();

				if(count == 0) {
					jobId = cell.getStringCellValue();
				} else {
					jobOperation currOp = new jobOperation();
					String op = cell.getStringCellValue();
					currOp.setJobOperationType(op);
					jobOpsList.add(currOp);
				}
				count ++;
			}
			UnitBatchInfo batch = new UnitBatchInfo();
			batch.setOperations(jobOpsList);
			allBatches.put(jobId, batch);
		}

		return allBatches;
	}

	@Override
	public boolean done() {
		return done;
	}
}
