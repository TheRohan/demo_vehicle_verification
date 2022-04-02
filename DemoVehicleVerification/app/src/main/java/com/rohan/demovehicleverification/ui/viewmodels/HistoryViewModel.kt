package com.rohan.demovehicleverification.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.rohan.demovehicleverification.other.Constants.APPROVED_SORT_KEY
import com.rohan.demovehicleverification.other.Constants.CORRECT_SORT_KEY
import com.rohan.demovehicleverification.other.Constants.INCORRECT_SORT_KEY
import com.rohan.demovehicleverification.other.Constants.NAME_SORT_KEY
import com.rohan.demovehicleverification.other.Constants.TIME_SORT_KEY
import com.rohan.demovehicleverification.other.SortType
import com.rohan.demovehicleverification.repositories.DBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    dbRepository: DBRepository
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.DATE)
    val sortType = _sortType.asStateFlow()


    @ExperimentalCoroutinesApi
    val vehicleInfoList = sortType.flatMapLatest {
        when (it) {
            SortType.DATE -> dbRepository.filterBy(TIME_SORT_KEY)
            SortType.NAME -> dbRepository.filterBy(NAME_SORT_KEY)
            SortType.APPROVED -> dbRepository.filterBy(APPROVED_SORT_KEY)
            SortType.CORRECT -> dbRepository.filterBy(CORRECT_SORT_KEY)
            SortType.INCORRECT -> dbRepository.filterBy(INCORRECT_SORT_KEY)
        }
    }

    fun sortVehicleInfoList(sortType: SortType) {
        this._sortType.value = sortType
    }
}