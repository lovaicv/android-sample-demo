package com.iebayirli.recyclerviewwithdatabinding.scene.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iebayirli.recyclerviewwithdatabinding.data.movie.ConstMovieData
import com.iebayirli.recyclerviewwithdatabinding.data.movie.Movie
import com.iebayirli.recyclerviewwithdatabinding.internal.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel(), MovieListener {

    val movieList = MutableLiveData<List<Movie>>()
//    val movieList: MutableLiveData<List<Movie>> = _movieList

    val showProgressBar = MutableLiveData(false)
//    val showProgressBar: LiveData<Boolean> = _showProgressBar

    val showToast = MutableLiveData<Event<Movie>>()

    init {
        submitMovieList()
    }

    private fun submitMovieList() {
        viewModelScope.launch {
            fetchMovies()
                .onStart {
                    showProgressBar.postValue(true)
                }.catch { err ->
                    showProgressBar.postValue(false)
                }
                .collect { list ->
                    showProgressBar.postValue(false)
                    movieList.value = list
                }
        }
    }

    fun addRandomMovie() {
        val rand = Random.nextInt(0, ConstMovieData.movieList.size - 1)
        val a = ConstMovieData.movieList[rand].copy().apply {
            favourite = false
            id = ConstMovieData.movieList.size.toLong()
        }
        ConstMovieData.movieList.add(a)
        submitMovieList()
    }

    private fun fetchMovies() = flow<List<Movie>> {
        delay(500)
        emit(ConstMovieData.movieList)
    }.flowOn(Dispatchers.IO)

    override fun onMovieClicked(movie: Movie) {
        showToast.value = Event(movie)
    }

    override fun onFavouriteClicked(movie: Movie) {
        val ind = ConstMovieData.movieList.indexOf(movie)
        ConstMovieData.movieList[ind].favourite = !ConstMovieData.movieList[ind].favourite
        submitMovieList()
    }
}