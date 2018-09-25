package moon.net.datamonitor.network

import io.reactivex.Observable
import moon.net.datamonitor.model.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

    @Headers("Accept: application/json")
    @POST("auth/register")
    abstract fun createUser(@Body user: User): Observable<Response<ResponseBody>>

    @POST("auth/login")
    abstract fun loginUser(@Body user: User): Observable<Response<ResponseBody>>

}