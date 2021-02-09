package es.adriiiprieto.nasaapi.ui.fragment.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.adriiiprieto.nasaapi.base.BaseState
import es.adriiiprieto.nasaapi.data.NasaRepository
import es.adriiiprieto.nasaapi.data.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

class ListViewModel : ViewModel() {

    private val state = MutableLiveData<BaseState>()
    fun getState(): LiveData<BaseState> = state

    fun requestInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state.postValue(BaseState.Loading())
                val items = NasaRepository().requestNasaPictures("sun")
                state.postValue(BaseState.Normal(ListState(items)))
            } catch (e: Exception) {
                state.postValue(BaseState.Error(e))
            }
        }
    }
}