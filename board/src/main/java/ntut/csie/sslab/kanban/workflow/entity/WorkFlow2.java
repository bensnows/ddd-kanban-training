package ntut.csie.sslab.kanban.workflow.entity;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.kanban.workflow.entity.event.WorkFlowCreatedEvent;

public class WorkFlow2 extends AggregateRoot<String> {
    private String workFlowId;
    private String boardId;
    private String flowName;

    public WorkFlow2(String workFlowId, String boardId, String flowName) {
        super(workFlowId);
        this.workFlowId = workFlowId;
        this.boardId = boardId;
        this.flowName = flowName;

        addDomainEvent(new WorkFlowCreatedEvent(workFlowId, boardId, boardId));
    }

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

}
