package com.fatec.serieshankbookcompose.ui.screen.seriedetailscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.serieshankbookcompose.data.remote.TVDetailWrapper
import com.fatec.serieshankbookcompose.repository.FirebaseRepository
import com.fatec.serieshankbookcompose.repository.TMDBApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SerieDetailViewModel @Inject constructor(
    private val apiRepo: TMDBApiRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    val detail = mutableStateOf<TVDetailWrapper?>(null)

    val favorite = mutableStateOf<Boolean>(false)


    fun getDetail(id: Int) {
        viewModelScope.launch {
            val res = apiRepo.getTvDetail(id = id.toInt())
            if (res.error.isNullOrEmpty()) {
                detail.value = res.data
                val list = firebaseRepository.getAllFavSerie()

                list.forEach { idS ->

                    if (idS.toInt() == id) {
                        favorite.value = true
                    }
                }
            }
        }
    }

    fun setFavorite(id: Int) {
        viewModelScope.launch {
            firebaseRepository.setFavSerie(id)
            favorite.value = !favorite.value
        }
    }

    fun setLater(id: Int) {
        viewModelScope.launch {
            firebaseRepository.setLaterSerie(id)
        }
    }

    fun setWatched(id: Int) {

    }
}