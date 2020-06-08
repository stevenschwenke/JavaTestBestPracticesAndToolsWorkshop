package de.stevenschwenke.java.testbestpracticesandtoolsworkshop.admin.event;

import de.stevenschwenke.java.testbestpracticesandtoolsworkshop.event.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("junit")
@Transactional
class AdminEventServiceImplTest_01_mockRepo {

    @MockBean
    private EventRepository eventRepository;
    @Autowired
    private AdminEventServiceImpl adminEventService;


    /*
    If repos are mocked, validation annotations in entity are not tested!
     */
    @Test
    void savingInvalidEventIsPossible() {
        adminEventService.saveNewEvent(new EventUpdateDTO(null, null, null, false));
    }

}
