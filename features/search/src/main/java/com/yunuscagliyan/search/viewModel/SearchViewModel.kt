package com.yunuscagliyan.search.viewModel

import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.search.ui.SearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(

) : CoreViewModel<SearchState, SearchEvent>() {
    override fun getInitialState(): SearchState = SearchState()

    override fun onEvent(event: SearchEvent) {

    }
}