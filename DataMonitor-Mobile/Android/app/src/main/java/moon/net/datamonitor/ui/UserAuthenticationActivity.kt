package moon.net.datamonitor.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import moon.net.datamonitor.R
import moon.net.datamonitor.base.BaseActivity
import moon.net.datamonitor.model.UserForAuth
import okhttp3.ResponseBody
import retrofit2.Response

class UserAuthenticationActivity : BaseActivity<UserAuthenticationPresenter>(),
        UserAuthenticationView {

    private var username: EditText? = null
    private var password: EditText? = null
    public lateinit var token : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        username = findViewById(R.id.register_username)
        password = findViewById(R.id.register_pswd)
    }

    override fun loginSuccess(response: Response<ResponseBody>) {
        Log.d("", "")
    }

    override fun registerSuccess(response: Response<ResponseBody>) {
        Log.d("", "")
    }

    override fun error(throwable: Throwable) {
        Log.d("", "")
    }

    override fun showLoading() {
        Log.d("", "")
    }

    override fun hideLoading() {
        Log.d("", "")
    }

    override fun instantiatePresenter(): UserAuthenticationPresenter {
        return UserAuthenticationPresenter(this)
    }

    fun register(view: View) {
        presenter.registerUser(UserForAuth(username = username!!.text.toString(), password = password!!.text.toString()))
    }

    fun login(view: View) {
//        presenter.loginUser(UserForAuth(username = username!!.text.toString(), password = password!!.text.toString()))
        presenter.fetchMembers()
    }
}