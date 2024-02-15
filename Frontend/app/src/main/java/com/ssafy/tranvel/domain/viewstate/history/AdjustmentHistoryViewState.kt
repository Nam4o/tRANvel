package com.ssafy.tranvel.domain.viewstate.history

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.ssafy.tranvel.data.model.AdjustmentHistoryResult
import com.ssafy.tranvel.data.model.dto.AdjustmentHistoryDto
import com.ssafy.tranvel.domain.viewstate.IViewState
import kotlinx.coroutines.flow.Flow

@Stable
data class AdjustmentHistoryViewState(
    val isLoading: Boolean = false,
    val pagedData: Flow<PagingData<AdjustmentHistoryDto>>? = null,
    val data: List<AdjustmentHistoryResult>? = null
) : IViewState
