package ee.digit25.detector.domain.device.external;

import ee.bitweb.core.retrofit.RetrofitRequestExecutor;
import ee.digit25.detector.domain.device.external.api.Device;
import ee.digit25.detector.domain.device.external.api.DeviceApi;
import ee.digit25.detector.domain.device.external.api.DeviceApiProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceRequester {

    private final DeviceApi deviceApi;
    private final DeviceApiProperties properties;

    public Device get(String mac) {
        log.info("Requesting device with mac({})", mac);

        return RetrofitRequestExecutor.executeRaw(deviceApi.get(properties.getToken(), mac));
    }
}
