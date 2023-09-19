package com.yunuscagliyan.home.main.viewModel

import com.yunuscagliyan.core_ui.event.Event
import com.yunuscagliyan.core_ui.event.Routes
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.home.main.ui.MainEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

):CoreViewModel<MainState,MainEvent>() {
    override fun getInitialState(): MainState  = MainState()

    override fun onEvent(event: MainEvent) {
        when(event){
            is MainEvent.OnSearchClick ->{
                sendEvent(
                    Event.Navigation(
                        Routes.NavigateToRoute(
                            pageRoute = ScreenRoutes.SearchScreen.route
                        )
                    )
                )
            }
        }
    }
}