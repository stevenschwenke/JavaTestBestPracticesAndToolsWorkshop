package de.stevenschwenke.java.testbestpracticesandtoolsworkshop.admin.event;

import de.stevenschwenke.java.testbestpracticesandtoolsworkshop.authentication.TokenProvider;
import de.stevenschwenke.java.testbestpracticesandtoolsworkshop.event.Event;
import de.stevenschwenke.java.testbestpracticesandtoolsworkshop.event.EventRepository;
import de.stevenschwenke.java.testbestpracticesandtoolsworkshop.user.User;
import de.stevenschwenke.java.testbestpracticesandtoolsworkshop.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("junit")
public class AdminEventControllerTest_02_springmvctest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private EventRepository eventRepository;
    @MockBean
    private AdminEventService adminEventService;

/*
Outline test-cases (better version):

- getting all events will return "OK"
- creating event will create event and "OK"
- edit event will edit event and "OK"
- deleting event will delete event and "OK"

- getting all events without proper auth will return 403
- creating valid event without proper auth will return 403
- creating invalid event with proper auth will return 422
- creating valid event edit without proper auth will return 403
- creating invalid event edit with proper auth will return 422
- creating valid event deletion without proper auth will return 403
- creating invalid event deletion with proper auth will return 422
 */

    @Test
    void gettingAllEventsViaAdminEndpointWillReturnEventsAndHTTP200() throws Exception {

        Event event = new Event("name", ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 19, 0), ZoneId.of("Europe/Berlin")), "url", false);
        event.setId(42L);
        doReturn(List.of(event)).when(eventRepository).findAllByOrderByDatetimeDesc();

        String jwt = registerUserAndReturnJWT();

        this.mockMvc.perform(get("/api/admin/events")
                .header("Authorization", jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.eventModelList[0].id").isNumber())
                .andExpect(jsonPath("$._embedded.eventModelList[0].name").value("name"))
                .andExpect(jsonPath("$._embedded.eventModelList[0].url").value("url"))
                .andExpect(jsonPath("$._embedded.eventModelList[0].datetime").value("1577901600"));
    }


    /**
     * @return JSON Web Token for user "steven" with password "steven", which has been registered in Spring Security.
     */
    private String registerUserAndReturnJWT() {
        doReturn(Optional.of(new User("steven", new BCryptPasswordEncoder().encode("steven")))).when(userRepository).findUserByUsername("steven");
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken("steven", "steven", grantedAuthorities);

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.createToken(authentication);
    }

}
