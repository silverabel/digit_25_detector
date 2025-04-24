package ee.digit25.detector.domain.device.external.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Device {

    private String mac;
    private Boolean isBlacklisted;
}
