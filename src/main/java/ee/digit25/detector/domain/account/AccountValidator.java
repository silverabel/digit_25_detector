package ee.digit25.detector.domain.account;

import ee.digit25.detector.domain.account.external.AccountRequester;
import ee.digit25.detector.domain.account.external.api.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Service
public class AccountValidator {

    private final Map<String, Account> notClosedAccounts = new HashMap<>();

    AccountValidator(AccountRequester requester) {
        int page = 0;
        while (true) {
            List<Account> devices = requester.get(page, 1000);

            if (!devices.isEmpty()) {
                notClosedAccounts.putAll(devices.stream().filter(a -> !a.getClosed()).collect(Collectors.toMap(Account::getNumber, Function.identity())));
                page++;
            } else {
                break;
            }
        }
    }

    public static boolean isValidSenderAccount(Account account, BigDecimal amount, String senderPersonCode) {
        return senderPersonCode.equals(account.getOwner())
               && account.getBalance().compareTo(amount) >= 0
                ;
    }

    public static boolean isValidRecipientAccount(Account account, String recipientPersonCode) {
        return recipientPersonCode.equals(account.getOwner());
    }
}
