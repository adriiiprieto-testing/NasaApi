package es.adriiiprieto.nasaapi.ui.fragment.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    fun requestInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            
        }
    }

}