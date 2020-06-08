package de.stevenschwenke.java.testbestpracticesandtoolsworkshop.admin.event;

import de.stevenschwenke.java.testbestpracticesandtoolsworkshop.event.Event;
import de.stevenschwenke.java.testbestpracticesandtoolsworkshop.event.EventModel;
import de.stevenschwenke.java.testbestpracticesandtoolsworkshop.event.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class AdminEventControllerTest_01_methodCalls {

/*
Outline test-cases (naive version):

- getting all events will return "OK"
- creating event will create event and "OK"
- edit event will edit event and "OK"
- deleting event will delete event and "OK"

 */

    @Test
    void gettingAllEventsWillReturnEventsAndHTTP200() {

        EventRepository eventRepository = mock(EventRepository.class);

        Event event = new Event("name", ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 19, 0), ZoneId.of("Europe/Berlin")), "url", false);
        event.setId(42L);
        doReturn(List.of(event)).when(eventRepository).findAllByOrderByDatetimeDesc();

        AdminEventController controller = new AdminEventController(eventRepository, mock(AdminEventService.class));

        ResponseEntity<CollectionModel<EventModel>> response = controller.getAllEvents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getContent().size());
    }
}
