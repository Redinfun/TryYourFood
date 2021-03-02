package br.com.tryyourfood.di

import android.content.Context
import androidx.room.Room
import br.com.tryyourfood.data.database.RecipesDatabase
import br.com.tryyourfood.utils.Constants.Companion.RECIPES_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        RECIPES_DATABASE_NAME
    ).build()
}