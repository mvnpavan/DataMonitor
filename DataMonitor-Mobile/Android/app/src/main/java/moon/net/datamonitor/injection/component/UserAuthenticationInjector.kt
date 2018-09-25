package moon.net.datamonitor.injection.component

import dagger.BindsInstance
import dagger.Component
import moon.net.datamonitor.base.BaseView
import moon.net.datamonitor.injection.module.ContextModule
import moon.net.datamonitor.injection.module.NetworkModule
import moon.net.datamonitor.ui.UserAuthenticationPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [(ContextModule::class), (NetworkModule::class)])
interface UserAuthenticationInjector {

    fun inject(userAuthenticationPresenter: UserAuthenticationPresenter)

    @Component.Builder
    interface Builder {
        fun build(): UserAuthenticationInjector

        fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }
}