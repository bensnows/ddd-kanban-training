package ntut.csie.sslab.kanban.workflow.entity.event;


import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

public class WorkFlowCreatedEvent extends DomainEvent {

    private String workFlowId;
    private String boardId;

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

    public String getWorkFlowName() {
        return workFlowName;
    }

    public void setWorkFlowName(String workFlowName) {
        this.workFlowName = workFlowName;
    }

    private String workFlowName;

    public WorkFlowCreatedEvent(String workFlowId, String boardId, String workFlowName) {
        super(DateProvider.now());
        this.workFlowId = workFlowId;
        this.boardId = boardId;
        this.workFlowName = workFlowName;

    }
}
