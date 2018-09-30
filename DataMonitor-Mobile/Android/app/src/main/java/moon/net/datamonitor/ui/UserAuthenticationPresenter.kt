package moon.net.datamonitor.ui

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moon.net.datamonitor.base.BasePresenter
import moon.net.datamonitor.model.UserForAuth
import moon.net.datamonitor.network.AuthApi
import javax.inject.Inject

class UserAuthenticationPresenter(userAuthenticationView: UserAuthenticationView)
    : BasePresenter<UserAuthenticationView>(userAuthenticationView) {

    @Inject
    lateinit var authApi: AuthApi

    private var subscription: Disposable? = null

    override fun onViewCreated() {
        super.onViewCreated()
    }

    override fun onViewDestroyed() {
        super.onViewDestroyed()
    }

    fun loginUser(userForAuthData: UserForAuth) {
        view.showLoading()
        subscription = authApi.loginUser(userForAuthData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { resp -> view.loginSuccess(resp) },
                        { throwable -> view.error(throwable) }
                )
    }

    fun registerUser(userForAuthData: UserForAuth) {
        view.showLoading()
        subscription = authApi.createUser(userForAuthData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { resp -> view.registerSuccess(resp) },
                        { throwable -> view.error(throwable) }
                )
    }

    fun fetchMembers() {
        val map = HashMap<String, String>()
        map.put("Authorization", "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiI2IiwidW5pcXVlX25hbWUiOiJzdGFyayIsIm5iZiI6MTUzODMwMzk4NiwiZXhwIjoxNTM4MzkwMzg2LCJpYXQiOjE1MzgzMDM5ODZ9.DNmAtxJKea7izMxCGX-6JyKiUq7C1uGCojgEotgSH98U4ye3BeKzQ7QbNtJ0se6m0AxXgUcNmZMxJs_yaRhLkA")
        subscription = authApi.fetchMembers(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { resp -> view.registerSuccess(resp) },
                        { throwable -> view.error(throwable) }
                )
    }

}