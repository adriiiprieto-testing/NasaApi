package es.adriiiprieto.nasaapi.data.network

import es.adriiiprieto.nasaapi.data.model.NasaResponseDataModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception


class NasaNetwork {

    lateinit var service: NasaService

    private fun loadRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://images-api.nasa.gov/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(NasaService::class.java)
    }

    suspend fun requestNasaImages(pictureType: String): NasaResponseDataModel {
        loadRetrofit()
        return service.getNasaImages(pictureType)
    }
}

