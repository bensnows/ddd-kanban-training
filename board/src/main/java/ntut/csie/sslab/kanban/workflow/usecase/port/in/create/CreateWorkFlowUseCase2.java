package ntut.csie.sslab.kanban.workflow.usecase.port.in.create;

import ntut.csie.sslab.ddd.usecase.cqrs.CqrsOutput;

public interface CreateWorkFlowUseCase2 {
    CqrsOutput execute(CreateWorkFlowInput2 createWorkFlowInput);
}
