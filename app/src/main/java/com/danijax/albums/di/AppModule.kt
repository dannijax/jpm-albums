package com.danijax.albums.di

import android.content.Context
import com.danijax.albums.data.datasource.DataSource
import com.danijax.albums.data.db.AlbumDao
import com.danijax.albums.data.db.AlbumsDatabase
import com.danijax.albums.data.model.Album
import com.danijax.albums.data.repository.AlbumsRepository
import com.danijax.albums.service.AlbumsApiService
import com.danijax.albums.service.HttpProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideHttpProvider(): AlbumsApiService{
        return HttpProvider.client()
    }

    @Provides
    fun provideAlbumDao(@ApplicationContext context: Context): AlbumDao{
        return AlbumsDatabase.getInstance(context)?.albumDao()!!
    }

}