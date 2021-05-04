package app.carlosisc.pokedex.domain.datasource.webservice

import app.carlosisc.pokedex.domain.models.PokemonFullInfo
import app.carlosisc.pokedex.domain.models.PokemonListResponse
import retrofit2.http.*

interface WebService {

    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int, @Query("offset") offset: Int): PokemonListResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name:String): PokemonFullInfo
}