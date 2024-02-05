package com.ssafy.tranvel.domain.repository

import com.ssafy.tranvel.data.model.response.HistoryResponse
import retrofit2.Response

interface HistoryRepository {
    suspend fun getAllHistories() : Response<HistoryResponse>
}
