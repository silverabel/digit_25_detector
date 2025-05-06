package ee.digit25.detector.domain.person.external.api;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface PersonApi {

    @GET("/persons/{id}")
    Call<Person> get(@Header("Authorization") String token, @Path("id") String personCode);

    @POST("/persons/by-person-codes")
    Call<List<Person>> get(@Header("Authorization") String token, @Body List<String> personCodes);

    @GET("/persons")
    Call<List<Person>> get(@Header("Authorization") String token, @Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize);
}
