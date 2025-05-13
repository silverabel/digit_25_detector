package ee.digit25.detector.domain.transaction;

import ee.digit25.detector.domain.account.AccountValidator;
import ee.digit25.detector.domain.device.DeviceValidator;
import ee.digit25.detector.domain.person.PersonValidator;
import ee.digit25.detector.domain.transaction.external.api.Transaction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionValidator {

    private final PersonValidator personValidator;
    private final DeviceValidator deviceValidator;
    private final AccountValidator accountValidator;

    public Result isLegitimate(List<Transaction> transactions) {
        var result = new Result();
        for (Transaction transaction : transactions) {
            if (personValidator.getValidPersons().contains(transaction.getRecipient())
                && personValidator.getValidPersons().contains(transaction.getSender())
                && deviceValidator.getValidDevices().contains(transaction.getDeviceMac())
                && accountValidator.getNotClosedAccounts().containsKey(transaction.getSenderAccount())
                && accountValidator.getNotClosedAccounts().containsKey(transaction.getRecipientAccount())
                && AccountValidator.isValidSenderAccount(accountValidator.getNotClosedAccounts().get(transaction.getSenderAccount()), transaction.getAmount(), transaction.getSender())
                && AccountValidator.isValidRecipientAccount(accountValidator.getNotClosedAccounts().get(transaction.getRecipientAccount()), transaction.getRecipient())
            ) {
                result.legit.add(transaction.getId());
            } else {
                result.rej.add(transaction.getId());
            }
        }

        return result;
    }

    @Getter
    public static final class Result {

        private final List<String> legit = new ArrayList<>();
        private final List<String> rej = new ArrayList<>();
    }
}
