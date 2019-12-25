package tw.teddysoft.clean.usecase.kanbanboard.workflow;

import tw.teddysoft.clean.domain.model.kanbanboard.home.Home;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.Workflow;
import tw.teddysoft.clean.domain.usecase.repository.Repository;
import tw.teddysoft.clean.domain.usecase.repository.RepositoryPeer;

import java.util.List;

public class WorkflowRepository extends Repository<Workflow> {
    public WorkflowRepository(RepositoryPeer peer) {
        super(peer);
    }
}
