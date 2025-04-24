package ee.digit25.detector.domain.person.external;

import ee.bitweb.core.retrofit.RetrofitRequestExecutor;
import ee.digit25.detector.domain.person.external.api.Person;
import ee.digit25.detector.domain.person.external.api.PersonApi;
import ee.digit25.detector.domain.person.external.api.PersonApiProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonRequester {

    private final PersonApi api;
    private final PersonApiProperties properties;

    public Person get(String personCode) {
        log.info("Requesting person with personCode {}", personCode);

        return RetrofitRequestExecutor.executeRaw(api.get(properties.getToken(), personCode));
    }
}
