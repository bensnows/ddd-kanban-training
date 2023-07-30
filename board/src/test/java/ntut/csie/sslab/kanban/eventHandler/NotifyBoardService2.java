package ntut.csie.sslab.kanban.eventHandler;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.ddd.usecase.DomainEventBus;
import ntut.csie.sslab.kanban.board.entity.Board;
import ntut.csie.sslab.kanban.board.usecase.port.out.BoardRepository;
import ntut.csie.sslab.kanban.workflow.entity.event.WorkFlowCreatedEvent;
import ntut.csie.sslab.kanban.workflow.entity.event.WorkflowCreated;

public class NotifyBoardService2 {

    private BoardRepository boardRepository;

    private DomainEventBus domainEventBus;

    public NotifyBoardService2(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Subscribe
    public void whenWorkFlowCommitted(WorkflowCreated event) {
        Board board = boardRepository.findById(event.boardId()).get();
        board.commitWorkflow(event.workflowId());
        boardRepository.save(board);
        domainEventBus.postAll(board);
    }
}
