package ee.digit25.detector.domain.device;

import ee.digit25.detector.domain.device.external.DeviceRequester;
import ee.digit25.detector.domain.device.external.api.Device;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Getter
@Service
public class DeviceValidator {

    private final Set<String> validDevices = new HashSet<>();

    DeviceValidator(DeviceRequester requester) {
        int page = 0;
        while (true) {
            List<Device> devices = requester.get(page, 1000);

            if (!devices.isEmpty()) {
                devices.stream().filter(device -> !device.getIsBlacklisted()).map(Device::getMac).forEach(validDevices::add);
                page++;
            } else {
                break;
            }
        }
    }
}
