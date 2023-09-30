package com.yunuscagliyan.search.viewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.yunuscagliyan.core.data.enums.PhotoOrderBy
import com.yunuscagliyan.core.data.repository.PhotoRepository
import com.yunuscagliyan.core.util.Constant
import com.yunuscagliyan.core.util.Constant.StringParameter.DEFAULT_SEARCH_VALUE
import com.yunuscagliyan.core_ui.event.Event
import com.yunuscagliyan.core_ui.event.NavArgument
import com.yunuscagliyan.core_ui.event.Routes
import com.yunuscagliyan.core_ui.event.ScreenRoutes
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.search.ui.SearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: PhotoRepository
) : CoreViewModel<SearchState, SearchEvent>() {
    override fun getInitialState(): SearchState = SearchState()

    init {
        searchPhotosAndCollections(
            query = DEFAULT_SEARCH_VALUE
        )
    }

    override fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnBackPress -> {
                popBack()
            }

            is SearchEvent.OnQuerySearch -> {
                updateState {
                    copy(
                        search = event.text
                    )
                }
            }

            is SearchEvent.OnSearch -> {
                if (state.value.search.isNotEmpty()) {
                    Timber.e("Search:${state.value.search}")
                    searchPhotosAndCollections(
                        query = state.value.search
                    )
                }
            }

            is SearchEvent.OnCollectionClick -> {
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

            is SearchEvent.OnPhotoClick -> {
                sendEvent(
                    Event.Navigation(
                        Routes.NavigateToRoute(
                            ScreenRoutes.PhotoDetailScreen.route,
                            navArgument = NavArgument(
                                key = Constant.NavigationArgumentKey.PHOTO_KEY,
                                data = event.photoModel
                            )
                        )
                    )
                )
            }

        }
    }

    private fun searchPhotosAndCollections(query: String) {
        updateState {
            copy(
                photos = repository.getSearchPhotos(
                    query = query,
                    orderBy = PhotoOrderBy.Relevant
                ).cachedIn(viewModelScope)
            )
        }
    }
}