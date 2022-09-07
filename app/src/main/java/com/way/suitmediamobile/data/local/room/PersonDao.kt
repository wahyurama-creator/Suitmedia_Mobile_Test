package com.way.suitmediamobile.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.way.suitmediamobile.data.local.model.GuestEntity
import com.way.suitmediamobile.data.local.model.PersonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(personEntity: PersonEntity)

    @Query("SELECT * FROM person_table")
    fun readPerson(): Flow<List<PersonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuests(guestEntity: GuestEntity)

    @Query("SELECT * FROM guest_table ORDER BY id ASC")
    fun readGuests(): Flow<List<GuestEntity>>
}