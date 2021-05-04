package app.carlosisc.pokedex.domain.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.carlosisc.pokedex.domain.datasource.database.daos.PokemonDao
import app.carlosisc.pokedex.domain.models.entities.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun pokemonDAO(): PokemonDao

    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            INSTANCE = INSTANCE
                ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "Pokedex").build()

            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}