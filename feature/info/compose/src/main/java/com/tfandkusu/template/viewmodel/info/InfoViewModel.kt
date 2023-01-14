package com.tfandkusu.template.viewmodel.info

import com.tfandkusu.template.viewmodel.UnidirectionalViewModel

sealed class InfoEvent {
    object OnClickOssLicense : InfoEvent()

    object OnClickAbout : InfoEvent()

    object CloseAbout : InfoEvent()
}

sealed class InfoEffect {
    object CallOssLicensesActivity : InfoEffect()
}

data class InfoState(
    val numberOfStarts: Int? = null
)

interface InfoViewModel : UnidirectionalViewModel<InfoEvent, InfoEffect, InfoState>
