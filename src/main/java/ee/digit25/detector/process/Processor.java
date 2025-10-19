package ee.digit25.detector.process;

import ee.digit25.detector.domain.transaction.TransactionValidator;
import ee.digit25.detector.domain.transaction.external.TransactionRequester;
import ee.digit25.detector.domain.transaction.external.TransactionVerifier;
import ee.digit25.detector.domain.transaction.external.api.Transaction;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class Processor {

    private final Task task;

    @PostConstruct
    public void postConstruct() {
        for (int i = 0; i < 5; i++) {
            task.execute(i);
        }
    }

    @Service
    @RequiredArgsConstructor
    public static class Task {

        private final TransactionRequester requester;
        private final TransactionValidator validator;
        private final TransactionVerifier verifier;
        private static Timer timer = new Timer();

        @Async
        public void execute(int i) {
            while (true) {
                try {
                    List<Transaction> transactions = requester.getUnverified(1000);
                    TransactionValidator.Result result = validator.isLegitimate(transactions);
                    verifier.verify(result.getLegit());
                    verifier.reject(result.getRej());

                    timer.count++;

                    if (i == 0) {
                        log.info(String.valueOf(1000000 * timer.count / ChronoUnit.MILLIS.between(timer.start, LocalTime.now())));
                        timer.count2++;

                        if (timer.count2 % 100 == 0) {
                            timer = new Timer();
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    public static class Timer {

        private long count;
        private long count2;
        private LocalTime start = LocalTime.now();
    }
}
