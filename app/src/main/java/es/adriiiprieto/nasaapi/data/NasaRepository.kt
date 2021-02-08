package es.adriiiprieto.nasaapi.data

import es.adriiiprieto.nasaapi.data.model.Item
import es.adriiiprieto.nasaapi.data.network.NasaNetwork

class NasaRepository {

    fun requestNasaPictures(pictureType: String): List<Item>{
        return NasaNetwork().requestNasaImages(pictureType).collection.items
    }

}