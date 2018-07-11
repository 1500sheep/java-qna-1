package codesquad.util;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import static org.assertj.core.api.Assertions.assertThat;


public class SessionHandlerTest {

    private MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        session = new MockHttpSession();
    }

    @Test
    public void getId() {
        session.setAttribute(SessionHandler.USER_KEY, 1L);
        assertThat(SessionHandler.getId(session).get()).isEqualTo(1L);
    }

}