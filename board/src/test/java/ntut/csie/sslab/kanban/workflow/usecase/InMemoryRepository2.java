package ntut.csie.sslab.kanban.workflow.usecase;

import ntut.csie.sslab.kanban.workflow.entity.WorkFlow2;
import ntut.csie.sslab.kanban.workflow.usecase.port.out.WorkFlowRepository2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryRepository2 implements WorkFlowRepository2 {
    private List<WorkFlow2> list = new ArrayList<>();

    @Override
    public Optional<WorkFlow2> getById(String workflowId) {
        return list.stream().filter(flow -> flow.getWorkFlowId().equals(workflowId)).findFirst();
    }

    @Override
    public String save(WorkFlow2 workFlow) {
        list.add(workFlow);
        return workFlow.getWorkFlowId();
    }
}