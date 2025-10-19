package ee.digit25.detector.process;

import ee.digit25.detector.domain.transaction.TransactionValidator;
import ee.digit25.detector.domain.transaction.external.TransactionRequester;
import ee.digit25.detector.domain.transaction.external.TransactionVerifier;
import ee.digit25.detector.domain.transaction.external.api.Transaction;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class Processor {

    private final TransactionRequester requester;
    private final TransactionValidator validator;
    private final TransactionVerifier verifier;
    private final Executor executor = Executors.newVirtualThreadPerTaskExecutor();

    @PostConstruct
    public void execute() {
        while (true) {
            LocalTime now = LocalTime.now();
            List<CompletableFuture<List<Transaction>>> futures = IntStream.range(0, 10)
                    .mapToObj(i -> CompletableFuture.supplyAsync(() -> requester.getUnverified(1000), executor))
                    .toList();

            List<Transaction> transactions = futures.stream().map(CompletableFuture::join).flatMap(Collection::stream).toList();
            TransactionValidator.Result result = validator.isLegitimate(transactions);

            CompletableFuture.allOf(
                    CompletableFuture.runAsync(() -> verifier.verify(result.getLegit()), executor),
                    CompletableFuture.runAsync(() -> verifier.reject(result.getRej()), executor)
            ).join();

            log.info(String.valueOf(ChronoUnit.MILLIS.between(now, LocalTime.now())));
        }
    }
}
