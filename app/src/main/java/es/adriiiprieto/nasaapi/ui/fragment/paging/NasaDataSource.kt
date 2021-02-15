package es.adriiiprieto.nasaapi.ui.fragment.paging

import androidx.paging.PageKeyedDataSource
import es.adriiiprieto.nasaapi.data.NasaRepository
import es.adriiiprieto.nasaapi.data.model.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class NasaDataSource(private val scope: CoroutineScope, private val searchTerm: String): PageKeyedDataSource<Int, Item> (){

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Item>) {
        scope.launch {
            val items = NasaRepository().requestNasaPictures(searchTerm, 1)
            if(items.isNotEmpty()) {
                callback.onResult(items, 1, 2)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {}

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        scope.launch {
            val items = NasaRepository().requestNasaPictures(searchTerm, params.key)
            if(items.isNotEmpty()) {
                callback.onResult(items, params.key + 1)
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}