package ntut.csie.sslab.kanban.workflow.usecase.port.in.create;

public class CreateWorkFlowInput2 {


    private String workFlowId;
    private String boardId;
    private String flowName;

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public CreateWorkFlowInput2(String workFlowId, String boardId, String flowName) {

        this.workFlowId = workFlowId;

        this.boardId = boardId;

        this.flowName = flowName;
    }
}
