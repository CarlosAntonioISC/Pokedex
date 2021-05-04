package com.example.pokedex.domain.datasource.database.daos

import androidx.room.*
import com.example.pokedex.domain.models.entities.PokemonEntity

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