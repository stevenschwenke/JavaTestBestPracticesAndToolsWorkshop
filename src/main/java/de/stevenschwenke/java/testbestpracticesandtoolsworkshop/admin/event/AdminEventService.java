package de.stevenschwenke.java.testbestpracticesandtoolsworkshop.admin.event;

import de.stevenschwenke.java.testbestpracticesandtoolsworkshop.event.Event;
import org.springframework.stereotype.Service;

@Service
public interface AdminEventService {

    Event saveNewEvent(EventUpdateDTO eventUpdateDTO);

    void editEvent(EventUpdateDTO eventUpdateDTO);

    void deleteEvent(Event newValue);
}
