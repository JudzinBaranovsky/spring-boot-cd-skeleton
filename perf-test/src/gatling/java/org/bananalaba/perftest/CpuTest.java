package org.bananalaba.perftest;

import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

import java.time.Duration;

import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class CpuTest extends Simulation {

    private HttpProtocolBuilder protocol = http.baseUrl("http://localhost:8080").acceptHeader("application/json");

    public CpuTest() {
        var calculationScenario = scenario("calculation-test")
            .exec(http("calculate").get("/api/v1/calculation?p=10"));

        setUp(calculationScenario.injectOpen(constantUsersPerSec(2).during(Duration.ofSeconds(60)))).protocols(protocol);
    }

}
