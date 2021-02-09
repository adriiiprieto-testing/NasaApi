package es.adriiiprieto.nasaapi.ui.fragment.list

import es.adriiiprieto.nasaapi.data.model.Item
import java.io.Serializable

data class ListState(
    val picturesList: List<Item> = listOf()
) : Serializable
