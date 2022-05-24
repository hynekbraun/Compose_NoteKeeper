package com.hynekbraun.composenotekeeper.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hynekbraun.composenotekeeper.data.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract val dao: NoteDAO

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}