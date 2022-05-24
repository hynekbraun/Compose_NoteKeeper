package com.hynekbraun.composenotekeeper.domain.di

import android.app.Application
import androidx.room.Room
import com.hynekbraun.composenotekeeper.data.NoteRepositoryImp
import com.hynekbraun.composenotekeeper.data.local.NoteDAO
import com.hynekbraun.composenotekeeper.data.local.NoteDatabase
import com.hynekbraun.composenotekeeper.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(db: NoteDatabase): NoteDAO {
        return db.dao
    }

    @Provides
    @Singleton
    fun provideNoteRepository(dao: NoteDAO): NoteRepository {
        return NoteRepositoryImp(dao)
    }

}