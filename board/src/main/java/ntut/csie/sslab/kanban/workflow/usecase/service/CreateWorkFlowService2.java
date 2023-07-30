package ntut.csie.sslab.kanban.workflow.usecase.service;

import ntut.csie.sslab.ddd.usecase.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.workflow.entity.WorkFlow2;
import ntut.csie.sslab.kanban.workflow.entity.event.WorkFlowCreatedEvent;
import ntut.csie.sslab.kanban.workflow.usecase.port.in.create.CreateWorkFlowInput2;
import ntut.csie.sslab.kanban.workflow.usecase.port.in.create.CreateWorkFlowUseCase2;
import ntut.csie.sslab.kanban.workflow.usecase.port.out.WorkFlowRepository2;

public class CreateWorkFlowService2 implements CreateWorkFlowUseCase2 {

    private WorkFlowRepository2 repository;

    private DomainEventBus domainEvent;

    public CreateWorkFlowService2(WorkFlowRepository2 repo, DomainEventBus domainEvent) {
        this.repository = repo;
        this.domainEvent = domainEvent;
    }

    public CqrsOutput execute(CreateWorkFlowInput2 createWorkFlowInput) {
        WorkFlow2 workFlow = new WorkFlow2(createWorkFlowInput.getWorkFlowId(), createWorkFlowInput.getBoardId(), createWorkFlowInput.getFlowName());

        repository.save(workFlow);
        domainEvent.postAll(workFlow);

        return CqrsOutput.create().setId(workFlow.getId()).setExitCode(ExitCode.SUCCESS);

    }
}
