package com.way.suitmediamobile.data.local

import com.way.suitmediamobile.data.local.model.GuestEntity
import com.way.suitmediamobile.data.local.model.PersonEntity
import com.way.suitmediamobile.data.local.room.PersonDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val personDao: PersonDao
) {

    suspend fun insertPerson(personEntity: PersonEntity) = personDao.insertPerson(personEntity)

    fun readPerson(): Flow<List<PersonEntity>> = personDao.readPerson()

    suspend fun insertGuests(guestEntity: GuestEntity) = personDao.insertGuests(guestEntity)

    fun readGuests(): Flow<List<GuestEntity>> = personDao.readGuests()

}