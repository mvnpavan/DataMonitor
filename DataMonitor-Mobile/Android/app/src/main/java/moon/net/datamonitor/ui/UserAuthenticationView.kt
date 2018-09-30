package moon.net.datamonitor.ui

import moon.net.datamonitor.base.BaseView
import okhttp3.ResponseBody
import retrofit2.Response

interface UserAuthenticationView : BaseView {

    fun loginSuccess(response: Response<ResponseBody>)

    fun registerSuccess(response: Response<ResponseBody>)

    fun error(throwable: Throwable)

    fun showLoading()

    fun hideLoading()

}