package de.stevenschwenke.java.testbestpracticesandtoolsworkshop.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


class DomainUserDetailsServiceTest {

    @Test
    void encodeMe() {
        System.out.println(new BCryptPasswordEncoder().encode("steven"));
        // $2a$10$UoSI1GfEiU1MTsqd/NL2Gur2nKU7Yrvxsce0GohoNiv91NcJvNCea
        // insert into users(id, username, password) values (1, 'steven', '$2a$10$UoSI1GfEiU1MTsqd/NL2Gur2nKU7Yrvxsce0GohoNiv91NcJvNCea');
    }
}
