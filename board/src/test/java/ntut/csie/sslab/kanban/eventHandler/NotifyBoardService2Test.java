package ntut.csie.sslab.kanban.eventHandler;

import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBusAdapter;
import ntut.csie.sslab.ddd.usecase.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsOutput;
import ntut.csie.sslab.kanban.board.entity.Board;
import ntut.csie.sslab.kanban.board.entity.CommittedWorkflow;
import ntut.csie.sslab.kanban.board.usecase.port.in.create.CreateBoardInput;
import ntut.csie.sslab.kanban.board.usecase.port.in.create.CreateBoardUseCase;
import ntut.csie.sslab.kanban.board.usecase.port.out.BoardRepository;
import ntut.csie.sslab.kanban.board.usecase.service.CreateBoardService;
import ntut.csie.sslab.kanban.workflow.usecase.DomainEventBus2;
import ntut.csie.sslab.kanban.workflow.usecase.InMemoryBoardRepo;
import ntut.csie.sslab.kanban.workflow.usecase.InMemoryRepository2;
import ntut.csie.sslab.kanban.workflow.usecase.port.in.create.CreateWorkFlowInput2;
import ntut.csie.sslab.kanban.workflow.usecase.port.in.create.CreateWorkFlowUseCase2;
import ntut.csie.sslab.kanban.workflow.usecase.port.out.WorkFlowRepository2;
import ntut.csie.sslab.kanban.workflow.usecase.service.CreateWorkFlowService2;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NotifyBoardService2Test {

    private BoardRepository boardRepository = new InMemoryBoardRepo();

    public DomainEventBus domainEventBus = new GoogleEventBusAdapter();

    @Test
    public void notify_board_when_workflow_created() {
        // Create notifyboardService
        NotifyBoardService2 notifyService = new NotifyBoardService2(boardRepository, domainEventBus);

        // Register notifyBoardService
        domainEventBus.register(notifyService);

        // Create board
        String boardId = createBoardUseCase().getId();

        // Create workflow
        String workFlowId = create_workflow_useCase(boardId).getId();

        Board board = boardRepository.findById(boardId).get();
        CommittedWorkflow committedWorkflow = board.getCommittedWorkflow(workFlowId).get();
        assertNotNull(committedWorkflow);

    }


    private CqrsOutput createBoardUseCase() {

        CreateBoardUseCase createBoardUseCase = new CreateBoardService(boardRepository, domainEventBus);
        CreateBoardInput input = new CreateBoardInput();
        input.setTeamId(UUID.randomUUID().toString());
        input.setName("boardName");
        input.setUserId("userId");

        return createBoardUseCase.execute(input);
    }


    private CqrsOutput create_workflow_useCase(String boardId) {

        String workFlowId = UUID.randomUUID().toString();
        String flowName = "flow";

        CreateWorkFlowInput2 createWorkFlowInput = new CreateWorkFlowInput2(workFlowId, boardId, flowName);

        WorkFlowRepository2 repo = new InMemoryRepository2();

//        DomainEventBus2 domainEvent = new DomainEventBus2();

        CreateWorkFlowUseCase2 useCase = new CreateWorkFlowService2(repo, domainEventBus);

        return useCase.execute(createWorkFlowInput);
    }

}
