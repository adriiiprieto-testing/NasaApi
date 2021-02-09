package es.adriiiprieto.nasaapi.ui.fragment.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import es.adriiiprieto.nasaapi.base.BaseState
import es.adriiiprieto.nasaapi.base.NetworkManager
import es.adriiiprieto.nasaapi.base.NoInternetConnectivity
import es.adriiiprieto.nasaapi.data.NasaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(app: Application) : AndroidViewModel(app) {

    private val state = MutableLiveData<BaseState>()
    fun getState(): LiveData<BaseState> = state

    fun requestInformation() {
        val hasInternetConnection: Boolean = NetworkManager().isNetworkAvailable(getApplication())
        if (hasInternetConnection) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    state.postValue(BaseState.Loading())
                    val items = NasaRepository().requestNasaPictures("sun")
                    state.postValue(BaseState.Normal(ListState(items)))
                } catch (e: Exception) {
                    state.postValue(BaseState.Error(e))
                }
            }
        } else {
            state.postValue(BaseState.Error(NoInternetConnectivity()))
        }
    }
}

