package rut.miit.simplemessanger.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
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
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(NETWORK_BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(ApiService::class.java)

    override suspend fun getCharacters(page: Int, pageSize: Int): List<Character> =
        retrofit.getCharacters(page, pageSize)
}