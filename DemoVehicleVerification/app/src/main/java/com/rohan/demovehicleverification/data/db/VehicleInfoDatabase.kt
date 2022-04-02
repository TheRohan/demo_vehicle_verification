package com.rohan.demovehicleverification.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [VehicleInfo::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class VehicleInfoDatabase : RoomDatabase() {

    abstract val dao: VehicleInfoDAO
}