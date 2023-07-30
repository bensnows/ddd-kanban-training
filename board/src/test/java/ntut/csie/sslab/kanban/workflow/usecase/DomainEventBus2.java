package ntut.csie.sslab.kanban.workflow.usecase;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.usecase.DomainEventBus;

import java.util.ArrayList;
import java.util.List;

public class DomainEventBus2 implements DomainEventBus {

    private List<DomainEvent> events = new ArrayList<>();

    public void post(DomainEvent event) {
        events.add(event);
    }

    @Override
    public void postAll(AggregateRoot aggregateRoot) {

    }

    @Override
    public void register(Object object) {

    }

    @Override
    public void unregister(Object object) {

    }

    public List<DomainEvent> getEvents() {
        return events;
    }
}
