package put.io.black.java.core.app;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import static org.mockito.Mockito.*;

public class ScenarioApplicationTest {

    @Test
    public void checkRunApplication() {
        SpringApplication mock = mock(SpringApplication.class);
        when(mock.run(anyObject(), anyString())).thenReturn(null);

        String[] args = {"Mocked test"};
        verify(mock, times(1)).run(ScenarioApplication.class, args);
    }
}
