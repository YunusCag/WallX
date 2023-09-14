package com.yunuscagliyan.core_ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.core_ui.event.Event
import com.yunuscagliyan.core_ui.event.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class CoreViewModel<S,E>:ViewModel() {

    abstract fun getInitialState():S

    private val _uiEvent = Channel<Event>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state:MutableStateFlow<S> = MutableStateFlow(getInitialState())
    val state:StateFlow<S> = _state.asStateFlow()


    fun sendEvent(event:Event) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }


    fun updateState(stateInvoke: S.() -> S) {
        viewModelScope.launch {
            _state.update(stateInvoke)
        }
    }

    abstract fun onEvent(event:E)

    fun popBack() {
        sendEvent(
            Event.Navigation(
                Routes.PopBack
            )
        )
    }
}