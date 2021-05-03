package com.example.pokedex.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokedex.domain.models.*

@Entity
data class PokemonEntity(
    @PrimaryKey
    var id: Int,
    val name: String,
    val url: String,
    var image: String,
    var color: Int = 0,
    )