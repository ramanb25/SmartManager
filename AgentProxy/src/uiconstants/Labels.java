package uiconstants;

/**
 * Contains constants for labels used within the GUI
 */
public class Labels {
	public static final int defaultJTextSize = 12;
	public static final String createJobButton = "Create";
	public static final String sendJobButton = "Send to manufacturer";

	/**
	 * UI labels for customer UI
 	 */
	public class CustomerLabels {
		public static final String customerDetailsHeading = "Customer details ";
		public static final String orderDetailsHeading = "Order details ";
		public static final String jobPriority = "Priority ";
		public static final String jobPenalty = "Penalty Rate (Rs./s)";
		public static final String jobDueDate = "Desired delivery date";
		public static final String OrderID = "Order ID";
		public static final String jobDimension = "Dimensions";
		public static final String jobOperationHeading = "Operations ";
		public static final String jobOpeationsDoneButton = "Done";
		public static final String OrderNo = "Order No";
		public static final String batchSize = "Batch Size";
	}

	/**
	 * UI Labels for machine ui
	 */
	public class MachineLabels {
	}

	/**
	 * UI labels for GSA UI
	 */
	public class GSLabels {
		public static final String queryForJobLabel = "Query Job";
	}

	/**
	 * UI labels for maintenance agent UI
	 */
	public class MaintenanceLabels {
		public static final String repairTimeLabel = "Repair Time (in minutes)";
	}
}
