package com.rohan.demovehicleverification.data.db

import androidx.room.*
import com.rohan.demovehicleverification.other.Constants.APPROVED_SORT_KEY
import com.rohan.demovehicleverification.other.Constants.CORRECT_SORT_KEY
import com.rohan.demovehicleverification.other.Constants.INCORRECT_SORT_KEY
import com.rohan.demovehicleverification.other.Constants.NAME_SORT_KEY
import com.rohan.demovehicleverification.other.Constants.TIME_SORT_KEY
import kotlinx.coroutines.flow.Flow


@Dao
interface VehicleInfoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDistanceInfo(vehicleInfo: VehicleInfo)

    @Delete
    suspend fun deleteDistanceInfo(vehicleInfo: VehicleInfo)

    @Query(
        """
        SELECT * FROM vehicle_info_table
        ORDER BY
        CASE WHEN :column = '$TIME_SORT_KEY'  THEN createdDate END DESC,
        CASE WHEN :column = '$NAME_SORT_KEY' THEN registrationNumber END DESC,
        CASE WHEN :column = '$APPROVED_SORT_KEY'  THEN approved = 1 END DESC,
        CASE WHEN :column = '$CORRECT_SORT_KEY'  THEN found = 1 END DESC,
        CASE WHEN :column = '$INCORRECT_SORT_KEY'  THEN found = 0 END DESC

    """
    )
    fun filterBy(column: String): Flow<List<VehicleInfo>>

    @Query(
        """
        SELECT * FROM vehicle_info_table WHERE id = :id
    """
    )
    fun getById(id: Int): Flow<VehicleInfo?>
}