package org.bananalaba.teamsports;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {8090})
@SpringBootTest
@ContextConfiguration(classes = LocalTestConfig.class)
@TestPropertySource(properties = {
    "soccer.api.baseUrl=http://localhost:8090"
})
public abstract class AbstractTest {
}
