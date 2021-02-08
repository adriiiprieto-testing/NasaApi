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

    fun requestNasaImages(pictureType: String): NasaResponseDataModel {
        loadRetrofit()
        val repos: Call<NasaResponseDataModel> = service.getNasaImages(pictureType)
        return repos.execute().body() ?: throw ApiException()
    }


}


class ApiException() : Exception()