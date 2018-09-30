package moon.net.datamonitor.network

import io.reactivex.Observable
import moon.net.datamonitor.model.UserForAuth
import moon.net.datamonitor.ui.UserAuthenticationActivity
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {

    @Headers("Accept: application/json")
    @POST("auth/register")
    abstract fun createUser(@Body userForAuth: UserForAuth): Observable<Response<ResponseBody>>

    @POST("auth/login")
    abstract fun loginUser(@Body userForAuth: UserForAuth): Observable<Response<ResponseBody>>

    @GET("users")
    abstract fun fetchMembers(@HeaderMap map: Map<String, String>): Observable<Response<ResponseBody>>

}