package com.tfandkusu.template.viewmodel.info

sealed class InfoAction {
    object CallOssLicensesActivity : InfoAction()

    data class ShowAbout(val numberOfStarts: Int) : InfoAction()

    object CloseAbout : InfoAction()
}
