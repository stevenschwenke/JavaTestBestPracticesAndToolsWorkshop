package de.stevenschwenke.java.testbestpracticesandtoolsworkshop.event;

import lombok.Value;

@Value
public class EventStatistics {

    Long totalNumberOfEvents;
    Integer numberOfFutureEvents;
    Double averageNumberOfEventsPerMonth;

}
