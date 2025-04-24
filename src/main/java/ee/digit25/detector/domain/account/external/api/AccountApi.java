package ee.digit25.detector.domain.account.external.api;

import retrofit2.Call;
import retrofit2.http.*;

public interface AccountApi {

    @GET("/accounts/{number}")
    Call<Account> get(@Header("Authorization") String token, @Path("number") String accountNumber);
}
