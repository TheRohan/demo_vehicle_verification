package com.rohan.demovehicleverification.data.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [VehicleInfo::class],
    version = 1
)
abstract class VehicleInfoDatabase : RoomDatabase() {

    abstract val dao: VehicleInfoDAO
}