package ntut.csie.sslab.kanban.workflow.usecase.port.in.create;

public class CreateWorkFlowOutput2 {
    private String workflowId;

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public CreateWorkFlowOutput2(String workFlowId) {
        this.workflowId = workFlowId;
    }
}
