package tw.teddysoft.clean.domain.model.kanbanboard.old_stage.event;

import tw.teddysoft.clean.domain.model.AbstractDomainEvent;

public class StageCreated extends AbstractDomainEvent {

    public StageCreated(String id, String name) {
        super(id, name);
    }
}