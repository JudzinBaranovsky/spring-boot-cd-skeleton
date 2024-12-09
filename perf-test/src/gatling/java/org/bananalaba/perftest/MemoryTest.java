package org.bananalaba.perftest;

import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

import java.time.Duration;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class MemoryTest extends Simulation {

    private HttpProtocolBuilder protocol = http.baseUrl("http://localhost:8080").acceptHeader("application/json");

    public MemoryTest() {
        var calculationScenario = scenario("calculation-test")
            .feed(new IdFeeder())
            .exec(http("calculate").post("/api/v1/blob/process-image?id=#{id}"));

        setUp(calculationScenario.injectOpen(constantUsersPerSec(1).during(Duration.ofSeconds(60)))).protocols(protocol);
    }

    private static class IdFeeder implements Iterator<Map<String, Object>> {

        private final AtomicInteger id = new AtomicInteger(0);

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Map<String, Object> next() {
            return Map.of("id", id.incrementAndGet());
        }

    }

}
