package com.example.weecompanyjctest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weecompanyjctest.model.Country
import com.example.weecompanyjctest.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class  ListCountryScreenViewModel @Inject constructor(
    private val repository: CountryRepository
) : ViewModel() {

    private val _countries = MutableLiveData<List<Country>>()

    fun getNews() : LiveData<List<Country>> {
        viewModelScope.launch(Dispatchers.IO) {
            val news = repository.getContries()
            _countries.postValue(news)
        }
        return _countries
    }
}