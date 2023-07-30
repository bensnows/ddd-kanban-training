package ntut.csie.sslab.kanban.workflow.entity.event;


import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

public class WorkFlowCreatedEvent extends DomainEvent {

    private String workFlowId;
    private String boardId;
    private String workFlowName;

    public WorkFlowCreatedEvent(String workFlowId, String boardId, String workFlowName) {
        super(DateProvider.now());
        this.workFlowId = workFlowId;
        this.boardId = boardId;
        this.workFlowName = workFlowName;

    }
}
