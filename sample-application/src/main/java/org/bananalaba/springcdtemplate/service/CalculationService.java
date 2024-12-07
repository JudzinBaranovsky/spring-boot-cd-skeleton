package org.bananalaba.springcdtemplate.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import lombok.AllArgsConstructor;
import org.bananalaba.springcdtemplate.client.DataServiceClient;
import org.bananalaba.springcdtemplate.model.DataItemDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CalculationService {

    @Value("${async.task.timeoutMs:2000}")
    private final long asyncTaskTimeoutMs;
    private final AsyncCalculation asyncCalculation;

    private final SyncCalculation syncCalculation;

    private final DataServiceClient dataServiceClient;

    public DataItemDto calculate(final String parameter) {
        var asyncResult = asyncCalculation.calculate();
        syncCalculation.calculate();
        var data = dataServiceClient.getData(parameter);

        try {
            asyncResult.get(asyncTaskTimeoutMs, TimeUnit.MILLISECONDS);
        } catch (TimeoutException | ExecutionException | InterruptedException e) {
            throw new CalculationException("async task failed", e);
        }

        return data;
    }

}
