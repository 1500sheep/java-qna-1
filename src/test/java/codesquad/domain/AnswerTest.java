package codesquad.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class AnswerTest {

    private Answer answer;
    private User me;
    private User other;

    @Before
    public void setUp() throws Exception {
        answer = new Answer();
        me = new User(1L, "id", "password", "name", "email");
        other = new User(2L, "id", "password", "name", "email");
        answer.setWriter(me);
    }

    @Test
    public void matchWriterSucceed() {
        assertThat(answer.matchWriter(me)).isTrue();
    }
    @Test
    public void matchWriterFail() {
        assertThat(answer.matchWriter(other)).isFalse();
    }
    @Test
    public void deleteSucceed() {
        answer.deleteByUser(me);
        assertThat(answer.isDeleted()).isTrue();
    }

    @Test(expected = RuntimeException.class)
    public void deleteFail() {
        User other = new User(2L, "id", "password", "name", "email");
        answer.deleteByUser(other);
    }

}