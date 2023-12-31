package com.yunuscagliyan.home.category.viewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.yunuscagliyan.core.data.enums.PhotoOrderBy
import com.yunuscagliyan.core.data.repository.PhotoRepository
import com.yunuscagliyan.core.util.Constant
import com.yunuscagliyan.core.util.Constant.StringParameter.DEFAULT_SEARCH_VALUE
import com.yunuscagliyan.core_ui.event.Event
import com.yunuscagliyan.core_ui.event.Routes
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.home.category.ui.CategoryEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : CoreViewModel<CategoryState, CategoryEvent>() {
    override fun getInitialState(): CategoryState = CategoryState()

    init {
        getCollections()
    }

    private fun getCollections() {
        updateState {
            copy(
                colletions = photoRepository.getSearchCollections(
                    query = DEFAULT_SEARCH_VALUE,
                    orderBy = PhotoOrderBy.Popular
                ).cachedIn(viewModelScope)
            )
        }
    }

    override fun onEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.OnCollectionClick -> {
                sendEvent(
                    Event.Navigation(
                        Routes.NavigateToRoute(
                            pageRoute = ScreenRoutes.PhotoListScreen.navigate(
                                collectionId = event.collectionModel.id,
                                collectionName = event.collectionModel.title
                            )
                        )
                    )
                )
            }
        }

    }

}