package moon.net.datamonitor.base

import moon.net.datamonitor.injection.component.BaseInjector
import moon.net.datamonitor.injection.component.DaggerBaseInjector
import moon.net.datamonitor.injection.module.ContextModule
import moon.net.datamonitor.injection.module.NetworkModule
import moon.net.datamonitor.ui.UserAuthenticationPresenter

abstract class BasePresenter<out v: BaseView>(protected val view: v) {

    private val injector: BaseInjector = DaggerBaseInjector
            .builder()
            .baseView(view)
            .contextModule(ContextModule)
            .networkModule(NetworkModule)
            .build()

    init {
        inject()
    }

    open fun onViewCreated(){}

    open fun onViewDestroyed(){}

    private fun inject() {
        when(this) {
            is UserAuthenticationPresenter -> injector.inject(this)
        }
    }
}