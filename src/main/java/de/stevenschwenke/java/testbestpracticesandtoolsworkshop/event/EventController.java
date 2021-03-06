package de.stevenschwenke.java.testbestpracticesandtoolsworkshop.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;
    private final EventRepository eventRepository;

    @Autowired
    public EventController(EventService eventService, EventRepository eventRepository) {
        this.eventService = eventService;
        this.eventRepository = eventRepository;
    }

    @GetMapping(value = "")
    public ResponseEntity<CollectionModel<EventModel>> getAllEvents() {

        List<EventModel> eventModels = eventRepository
                .findAllWithDatetimeAfter(ZonedDateTime.now())
                .stream()
                .map((event) -> new EventResourceAssembler(this.getClass(), EventModel.class).toModel(event))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new CollectionModel<>(eventModels));
    }

    @GetMapping(value = "/statistics")
    public ResponseEntity<EventStatistics> getStatisticsForEvents() {
        return ResponseEntity.ok(eventService.calculateEventStatistics());
    }
}
