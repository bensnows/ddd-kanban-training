package ntut.csie.sslab.kanban.workflow.usecase;

import ntut.csie.sslab.ddd.usecase.cqrs.CqrsOutput;
import ntut.csie.sslab.kanban.workflow.entity.WorkFlow2;
import ntut.csie.sslab.kanban.workflow.usecase.port.in.create.CreateWorkFlowInput2;
import ntut.csie.sslab.kanban.workflow.usecase.port.in.create.CreateWorkFlowUseCase2;
import ntut.csie.sslab.kanban.workflow.usecase.port.out.WorkFlowRepository2;
import ntut.csie.sslab.kanban.workflow.usecase.service.CreateWorkFlowService2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateWorkFlowUseCase2Test {


    @Test
    public void test_create_workflow_useCase() {

        String workFlowId = UUID.randomUUID().toString();
        String boardId = UUID.randomUUID().toString();
        String flowName = "flow";

        CreateWorkFlowInput2 createWorkFlowInput = new CreateWorkFlowInput2(workFlowId, boardId, flowName);

        WorkFlowRepository2 repo = new InMemoryRepository2();

        DomainEventBus2 domainEvent = new DomainEventBus2();

        CreateWorkFlowUseCase2 useCase = new CreateWorkFlowService2(repo, domainEvent);

        CqrsOutput output = useCase.execute(createWorkFlowInput);

        assertNotNull(output.getId());

        assertNotNull(repo.getById(output.getId()));

        assertEquals(1, domainEvent.getEvents().size());

    }
}
