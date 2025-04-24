package ee.digit25.detector.domain.device.external.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface DeviceApi {

    @GET("/devices/{mac}")
    Call<Device> get(@Header("Authorization") String token, @Path("mac") String mac);
}
