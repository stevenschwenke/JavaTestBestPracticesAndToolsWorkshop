package de.stevenschwenke.java.testbestpracticesandtoolsworkshop.event;

import de.stevenschwenke.java.testbestpracticesandtoolsworkshop.group.GroupController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class EventResourceAssembler extends RepresentationModelAssemblerSupport<Event, EventModel> {

    public EventResourceAssembler(Class<?> controllerClass, Class<EventModel> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public EventModel toModel(Event entity) {
        EventModel eventModel = super.createModelWithId(entity.getId(), entity);
        eventModel.setId(entity.getId());
        eventModel.setDatetime(entity.getDatetime().toEpochSecond());
        eventModel.setName(entity.getName());
        eventModel.setUrl(entity.getUrl());
        eventModel.setGeneralPublic(entity.getGeneralPublic());

        if (entity.getGroup() != null) {
            Link linkToGroup = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GroupController.class).getGroup(entity.getGroup().getId())).withRel("group");
            eventModel.add(linkToGroup);
        }

        return eventModel;
    }
}
