package es.adriiiprieto.nasaapi.ui.fragment.detail

import es.adriiiprieto.nasaapi.data.model.Item
import java.io.Serializable

data class DetailState(
    val item: Item? = null
) : Serializable
