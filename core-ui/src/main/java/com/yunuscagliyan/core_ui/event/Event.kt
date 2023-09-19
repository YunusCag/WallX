package com.yunuscagliyan.core_ui.event

sealed class Event {
    data class Navigation(val state: Routes) : Event()
}
