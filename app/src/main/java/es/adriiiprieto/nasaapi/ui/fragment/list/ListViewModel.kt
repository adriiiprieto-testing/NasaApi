package es.adriiiprieto.nasaapi.ui.fragment.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    private val _response: MutableLiveData<String> = MutableLiveData()
    val response: LiveData<String> = _response


    fun requestInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            _response.postValue("djshgfkh")
        }
    }

}