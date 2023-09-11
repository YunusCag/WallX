package com.yunuscagliyan.home.category.viewModel

import com.yunuscagliyan.core.data.enums.PhotoOrderBy
import com.yunuscagliyan.core.data.remote.repository.PhotoRepository
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
                    query = "Wallpapers",
                    orderBy = PhotoOrderBy.Popular
                )
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