package ee.digit25.detector.domain.person.external.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface PersonApi {

    @GET("/persons/{id}")
    Call<Person> get(@Header("Authorization") String token, @Path("id") String personCode);

}
