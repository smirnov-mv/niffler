package niffler.api;

import niffler.model.UserJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NifflerUserDataApi {
    @GET("currentUser")
    Call<UserJson> getUserData(@Query("username") String username);

    @POST("updateUserInfo")
    Call<UserJson> updateUserData(@Body UserJson userJson);
}
