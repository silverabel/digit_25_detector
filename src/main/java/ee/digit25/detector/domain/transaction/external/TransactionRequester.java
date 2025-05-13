package ee.digit25.detector.domain.transaction.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.bitweb.core.retrofit.RetrofitRequestExecutor;
import ee.digit25.detector.domain.transaction.external.api.Transaction;
import ee.digit25.detector.domain.transaction.external.api.TransactionApiProperties;
import ee.digit25.detector.domain.transaction.external.api.TransactionsApi;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionRequester {

    private final TransactionsApi api;
    private final TransactionApiProperties properties;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public List<Transaction> getUnverified(int amount) {
//        log.info("Requesting a batch of unverified transactions of size {}", amount);
//        return RetrofitRequestExecutor.executeRaw(api.getUnverified(properties.getToken(), amount));

        String response = restTemplate.exchange(properties.getBaseUrl() + "/transactions/unverified?amount=1000", HttpMethod.GET, new HttpEntity<>(new HttpHeaders(MultiValueMap.fromSingleValue(Map.of(
                "Authorization", properties.getToken()
        )))), String.class).getBody();

        return objectMapper.readValue(response, new TypeReference<List<Transaction>>() {});
    }

}
