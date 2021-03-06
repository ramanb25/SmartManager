package mas.maintenanceproxy.classes;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Anand Prajapati
 * <p>
 * Class to represent a preventive maintenance activity.
 * It contains an ID, Activity code (which tells what activities need were performed in this activity),
 * expected and actual start and finish times and status. 
 * </p>
 *
 */
public class PMaintenance implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String maintId;
	private String activityCode;
	private Date expectedStartTime;
	private Date expectedFinishTime;
	private Date actualStartTime;
	private Date actualFinishTime;
	
	private MaintStatus maintStatus;
	
	public PMaintenance(String id) {
		this.maintId = id;
	}
	
	public Date getExpectedStartTime() {
		return expectedStartTime;
	}
	public void setExpectedStartTime(Date expectedStartTime) {
		this.expectedStartTime = expectedStartTime;
	}
	public Date getExpectedFinishTime() {
		return expectedFinishTime;
	}
	public void setExpectedFinishTime(Date expectedFinishTime) {
		this.expectedFinishTime = expectedFinishTime;
	}
	public Date getActualStartTime() {
		return actualStartTime;
	}
	public void setActualStartTime(Date actualStartTime) {
		this.actualStartTime = actualStartTime;
	}
	public Date getActualFinishTime() {
		return actualFinishTime;
	}
	public void setActualFinishTime(Date actualFinishTime) {
		this.actualFinishTime = actualFinishTime;
	}
	
	public String getMaintId() {
		return maintId;
	}

	public MaintStatus getMaintStatus() {
		return maintStatus;
	}

	public void setMaintStatus(MaintStatus maintStatus) {
		this.maintStatus = maintStatus;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

}
