package ee.digit25.detector.domain.person;

import ee.digit25.detector.domain.person.external.PersonRequester;
import ee.digit25.detector.domain.person.external.api.Person;
import ee.digit25.detector.domain.transaction.external.api.Transaction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Getter
@Service
public class PersonValidator {

    private final Set<String> validPersons = new HashSet<>();

    PersonValidator(PersonRequester requester) {
        int page = 0;
        while (true) {
            List<Person> persons = requester.get(page, 1000);

            if (!persons.isEmpty()) {
                persons.stream().filter(PersonValidator::isValid).map(Person::getPersonCode).forEach(validPersons::add);
                page++;
            } else {
                break;
            }
        }
    }

    private static boolean isValid(Person person) {
        return !person.getWarrantIssued() && person.getHasContract() && !person.getBlacklisted();
    }
}
