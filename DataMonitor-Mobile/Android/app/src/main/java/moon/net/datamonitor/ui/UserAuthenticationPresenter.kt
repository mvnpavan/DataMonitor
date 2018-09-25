package moon.net.datamonitor.ui

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moon.net.datamonitor.base.BasePresenter
import moon.net.datamonitor.model.User
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

    fun loginUser(userData: User) {
        view.showLoading()
        subscription = authApi.loginUser(userData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { resp -> view.loginSuccess(resp) },
                        { throwable -> view.error(throwable) }
                )
    }

    fun registerUser(userData: User) {
        view.showLoading()
        subscription = authApi.createUser(userData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { resp -> view.registerSuccess(resp) },
                        { throwable -> view.error(throwable) }
                )
    }

}