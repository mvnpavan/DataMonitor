package moon.net.datamonitor.ui

import moon.net.datamonitor.base.BaseView

interface MemberListView : BaseView {

    fun loadMembers()

    fun error(throwable: Throwable)

    fun showLoading()

    fun hideLoading()
}