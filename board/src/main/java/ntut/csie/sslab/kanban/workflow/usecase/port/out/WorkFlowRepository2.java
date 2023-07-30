package ntut.csie.sslab.kanban.workflow.usecase.port.out;

import ntut.csie.sslab.kanban.workflow.entity.SwimLane;
import ntut.csie.sslab.kanban.workflow.entity.WorkFlow2;

import java.util.Optional;

public interface WorkFlowRepository2 {
    Optional<WorkFlow2> getById(String workflowId);

    String save(WorkFlow2 workFlow);
}
