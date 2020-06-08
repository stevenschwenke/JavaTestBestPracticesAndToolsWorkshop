package de.stevenschwenke.java.testbestpracticesandtoolsworkshop.event;

import org.springframework.stereotype.Service;

@Service
public interface EventService {
    EventStatistics calculateEventStatistics();
}
