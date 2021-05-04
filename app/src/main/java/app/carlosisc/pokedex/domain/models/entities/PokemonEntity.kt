package app.carlosisc.pokedex.domain.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity(
    @PrimaryKey
    var id: Int,
    val name: String,
    val url: String,
    var image: String,
    var color: Int = 0,
    )