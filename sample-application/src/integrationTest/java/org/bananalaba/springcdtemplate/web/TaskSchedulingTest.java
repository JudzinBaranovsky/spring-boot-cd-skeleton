package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.bananalaba.springcdtemplate.service.TaskService;
import org.bananalaba.springcdtemplate.task.Counter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    properties = "node.ip=192.168.0.1"
)
@AutoConfigureMockMvc
public class TaskSchedulingTest {

    @Autowired
    private Counter counter;
    @Autowired
    private TaskService service;

    @BeforeEach
    public void reset() {
        counter.reset();
        service.cancelTasks();
    }

    @Test
    public void shouldRunOneSequentialAndTwoConcurrentTasks() throws Exception {
        Thread.sleep(200);

        assertThat(counter.getData()).hasSize(3)
            .containsEntry("concurrent-dedicated-1", 1)
            .containsEntry("concurrent-dedicated-2", 1)
            .containsEntry("sequential-taskExecutor-1", 2);
    }

    @Test
    public void shouldScheduleNewTask() throws Exception {
        var newCounter = mock(Counter.class);
        service.schedule("new", newCounter, 100);

        Thread.sleep(150);

        verify(newCounter).increment("new");
    }

}
