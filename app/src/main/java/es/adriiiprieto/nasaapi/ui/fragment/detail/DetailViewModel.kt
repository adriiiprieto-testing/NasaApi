package es.adriiiprieto.nasaapi.ui.fragment.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.adriiiprieto.nasaapi.base.BaseState
import es.adriiiprieto.nasaapi.data.model.Item
import java.lang.Exception

class DetailViewModel : ViewModel() {

    private val state = MutableLiveData<BaseState>()
    fun getState(): LiveData<BaseState> = state

    fun setupParams(item: Item) {
        if(item.href == null){
            state.postValue(BaseState.Error(NoUrlException("No hay url disponible")))
        }else{
            state.postValue(BaseState.Normal(DetailState(item)))
        }

    }
}

class NoUrlException(val msg: String) : Exception()