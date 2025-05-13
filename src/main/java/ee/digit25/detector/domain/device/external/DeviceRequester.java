package ee.digit25.detector.domain.device.external;

import ee.bitweb.core.retrofit.RetrofitRequestExecutor;
import ee.digit25.detector.domain.device.external.api.Device;
import ee.digit25.detector.domain.device.external.api.DeviceApi;
import ee.digit25.detector.domain.device.external.api.DeviceApiProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceRequester {

    private final DeviceApi api;
    private final DeviceApiProperties properties;

    public Device get(String mac) {
        log.info("Requesting device with mac({})", mac);

        return RetrofitRequestExecutor.executeRaw(api.get(properties.getToken(), mac));
    }

    public List<Device> get(List<String> macs) {
        log.info("Requesting devices with macs {}", macs);

        return RetrofitRequestExecutor.executeRaw(api.get(properties.getToken(), macs));
    }

    public List<Device> get(int pageNumber, int pageSize) {
        log.info("Requesting devices page {} of size {}", pageNumber, pageSize);

        return RetrofitRequestExecutor.executeRaw(api.get(properties.getToken(), pageNumber, pageSize));
    }
}
