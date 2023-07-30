package ntut.csie.sslab.kanban.eventHandler;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.ddd.usecase.DomainEventBus;
import ntut.csie.sslab.kanban.board.entity.Board;
import ntut.csie.sslab.kanban.board.usecase.port.out.BoardRepository;
import ntut.csie.sslab.kanban.workflow.entity.event.WorkFlowCreatedEvent;

public class NotifyBoardService2 {

    private BoardRepository boardRepository;

    private DomainEventBus domainEventBus;

    public NotifyBoardService2(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Subscribe
    public void whenWorkFlowCommitted(WorkFlowCreatedEvent event) {
        Board board = boardRepository.findById(event.getBoardId()).get();
        board.commitWorkflow(event.getWorkFlowId());
        boardRepository.save(board);
        domainEventBus.postAll(board);
    }
}
