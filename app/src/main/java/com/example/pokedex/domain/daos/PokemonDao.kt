package com.example.pokedex.domain.daos

import androidx.room.*
import com.example.pokedex.domain.entities.PokemonEntity
import retrofit2.http.GET

@Dao
interface PokemonDao {

    @Query("SELECT * FROM PokemonEntity")
    suspend fun getFavoritesPokemon(): MutableList<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritePokemon(pokemon: PokemonEntity)

    @Query("DELETE FROM PokemonEntity WHERE id=:id")
    suspend fun deleteFavoritePokemon(id: Int)

    @Query("SELECT * FROM PokemonEntity WHERE id=:id")
    suspend fun getFavoritePokemonById(id: Int): PokemonEntity

}