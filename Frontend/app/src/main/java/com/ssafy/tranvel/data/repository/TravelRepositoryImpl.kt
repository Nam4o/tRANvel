package com.ssafy.tranvel.data.repository

import com.ssafy.tranvel.data.model.dto.Room
import com.ssafy.tranvel.data.model.response.DataResponse
import com.ssafy.tranvel.data.remote.datasource.travel.TravelDataSource
import com.ssafy.tranvel.data.utils.DataState
import com.ssafy.tranvel.domain.repository.TravelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TravelRepositoryImpl @Inject constructor(
    private val travelDataSource: TravelDataSource
) : TravelRepository {
    override suspend fun createRoom(roomPassword: String): Flow<DataState<DataResponse<Room<Boolean>>>> {
        return travelDataSource.createRoom(roomPassword)
    }

    override suspend fun enterRoom(
        roomCode: String,
        roomPassword: String
    ): Flow<DataState<DataResponse<Room<Boolean>>>> {
        return travelDataSource.enterRoom(roomCode, roomPassword)
    }

}