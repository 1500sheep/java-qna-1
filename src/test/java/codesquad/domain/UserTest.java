package codesquad.domain;

import codesquad.exception.ForbiddenException;
import codesquad.exception.InvalidPasswordException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class UserTest {

    private User me;
    private User other;

    @Before
    public void setUp() throws Exception {
        me = new User(1L, "me", "pass", "me", "email@me.com");
        other = new User(2L, "other", "word", "other", "email@other.com");
    }

    @Test
    public void loginSucceed() {
        User anonymous = new User(1L, "me", "pass", "me", "email@me.com");
        anonymous.login("pass"); //?
        assertThat(anonymous).isEqualTo(me);
    }

    @Test(expected = InvalidPasswordException.class)
    public void loginFailed() {
        me.login(other.getPassword());
    }

    @Test
    public void updateSucceed() {
        User updatedMe = new User(1L, "me", "pass", "updatedMe", "email@me.com");
        me.update(updatedMe);
        assertThat(me).isEqualTo(updatedMe);
    }

    @Test
    public void passwordMatch() {
        assertThat(me.passwordMatch("pass")).isTrue();
    }

    @Test
    public void passwordMatchUser() {
        assertThat(me.passwordMatch(other)).isFalse();
    }

    @Test(expected = ForbiddenException.class)
    public void checkId() {
        me.checkId(2L);
    }

}