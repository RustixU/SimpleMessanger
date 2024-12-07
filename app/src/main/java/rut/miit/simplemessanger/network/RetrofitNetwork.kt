package rut.miit.simplemessanger.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import rut.miit.simplemessanger.entity.Character


interface ApiService {
    @GET(value = "characters")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<Character>
}

private const val NETWORK_BASE_URL = "https://www.anapioficeandfire.com/api/"

class RetrofitNetwork : ApiService {
    private val retrofit = Retrofit.Builder()
        .baseUrl(NETWORK_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    override suspend fun getCharacters(page: Int, pageSize: Int): List<Character> =
        retrofit.getCharacters(page, pageSize)
}