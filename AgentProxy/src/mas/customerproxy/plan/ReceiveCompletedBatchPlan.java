package mas.customerproxy.plan;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import mas.customerproxy.agent.CustomerAgent;
import mas.customerproxy.gui.CustomerProxyGUI;
import mas.jobproxy.Batch;
import mas.util.ID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bdi4jade.core.BeliefBase;
import bdi4jade.message.MessageGoal;
import bdi4jade.plan.PlanBody;
import bdi4jade.plan.PlanInstance;
import bdi4jade.plan.PlanInstance.EndState;

/**
 * This plan receives the completed batch from GSA.
 */
public class ReceiveCompletedBatchPlan extends Behaviour implements PlanBody{

	private static final long serialVersionUID = 1L;
	private Logger log;
	private Batch completedBatch;
	private BeliefBase bfBase;
	
	@Override
	public EndState getEndState() {
		return EndState.SUCCESSFUL;
	}

	@Override
	public void init(PlanInstance pInstance) {
		log = LogManager.getLogger();
		bfBase = pInstance.getBeliefBase();
		ACLMessage msg = ( (MessageGoal)pInstance.getGoal() ).getMessage();
		try {
			completedBatch = (Batch) msg.getContentObject();

		} catch (UnreadableException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void action() {
//		log.info(arg0);
		if(completedBatch != null && completedBatch.getCustomerId().equals(myAgent.getLocalName())) {

			((CustomerProxyGUI)bfBase.getBelief(ID.Customer.BeliefBaseConst.CUSTOMER_GUI)
				.getValue()).addCompletedBatch(completedBatch);
		}
	}

	@Override
	public boolean done() {
		return true;
	}

}
