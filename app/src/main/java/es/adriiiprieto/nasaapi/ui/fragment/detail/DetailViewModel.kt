package es.adriiiprieto.nasaapi.ui.fragment.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.adriiiprieto.nasaapi.base.BaseState
import es.adriiiprieto.nasaapi.data.model.Item

class DetailViewModel : ViewModel() {

    private val state = MutableLiveData<BaseState>()
    fun getState(): LiveData<BaseState> = state

    fun setupParams(item: Item) {
        state.postValue(BaseState.Normal(DetailState(item)))
    }


}