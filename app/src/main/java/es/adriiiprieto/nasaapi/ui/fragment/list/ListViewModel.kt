package es.adriiiprieto.nasaapi.ui.fragment.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.adriiiprieto.nasaapi.data.NasaRepository
import es.adriiiprieto.nasaapi.data.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    private val _response: MutableLiveData<List<Item>> = MutableLiveData()
    val response: LiveData<List<Item>> = _response


    fun requestInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = NasaRepository().requestNasaPictures("sun")
            _response.postValue(items)
        }
    }

}