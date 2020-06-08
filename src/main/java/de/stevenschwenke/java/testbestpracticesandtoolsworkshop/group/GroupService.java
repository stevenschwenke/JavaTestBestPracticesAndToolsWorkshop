package de.stevenschwenke.java.testbestpracticesandtoolsworkshop.group;

import org.springframework.stereotype.Service;

@Service
public interface GroupService {

    Integer calculateTotalNumberOfEvents(Group group);

    long calculateDaysPassedSinceFirstKnownEvent(Group group);

    long calculateDaysPassedSinceLastKnownEvent(Group group);

    Double calculateAverageNumberOfEventsPerMonth(Group group);

    GroupStatistics calculateGroupStatistics();
}
