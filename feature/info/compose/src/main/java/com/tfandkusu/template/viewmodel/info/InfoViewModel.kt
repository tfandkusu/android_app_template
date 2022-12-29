package com.tfandkusu.template.viewmodel.info

import com.tfandkusu.template.viewmodel.UnidirectionalViewModel

sealed class InfoEvent {
    object OnClickAbout : InfoEvent()

    object CloseAbout : InfoEvent()
}

sealed class InfoEffect {
    data class ShowAbout(val numberOfStarts: Int) : InfoEffect()
}

data class InfoState(
    val numberOfStarts: Int? = null
)

interface InfoViewModel : UnidirectionalViewModel<InfoEvent, InfoEffect, InfoState>
