package es.adriiiprieto.nasaapi.data.network

import es.adriiiprieto.nasaapi.data.model.NasaResponseDataModel
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NasaService {
    @GET("search")
    suspend fun getNasaImages(@Query("q") pictureType: String): NasaResponseDataModel
}