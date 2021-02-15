package es.adriiiprieto.nasaapi.ui.fragment.paging

import android.app.Application
import androidx.arch.core.util.Function
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import es.adriiiprieto.nasaapi.data.model.Item
import kotlinx.coroutines.launch

class PagingViewModel(app: Application) : AndroidViewModel(app) {

    private var myList: LiveData<PagedList<Item>>
    fun getMyList(): LiveData<PagedList<Item>> = myList

    init {
        val config = PagedList.Config.Builder().setPageSize(100).build()
        myList = initPagedList(config, "sun").build()
    }

    private fun initPagedList(config: PagedList.Config, searchTerm: String) = LivePagedListBuilder(object : DataSource.Factory<Int, Item>() {
        override fun create(): DataSource<Int, Item> {
            return NasaDataSource(viewModelScope, searchTerm)
        }
    }, config)

}
