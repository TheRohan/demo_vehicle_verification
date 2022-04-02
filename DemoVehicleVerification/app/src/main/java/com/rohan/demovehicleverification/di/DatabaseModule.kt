package com.rohan.demovehicleverification.di

import android.content.Context
import androidx.room.Room
import com.rohan.demovehicleverification.data.db.VehicleInfoDAO
import com.rohan.demovehicleverification.data.db.VehicleInfoDatabase
import com.rohan.demovehicleverification.other.Constants
import com.rohan.demovehicleverification.repositories.DBRepository
import com.rohan.demovehicleverification.repositories.DBRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ): VehicleInfoDatabase = Room.databaseBuilder(
        app,
        VehicleInfoDatabase::class.java,
        Constants.DISTANCE_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db: VehicleInfoDatabase): VehicleInfoDAO = db.dao


    @Singleton
    @Provides
    fun provideDBRepository(db: VehicleInfoDatabase): DBRepository {
        return DBRepositoryImpl(db.dao)
    }
}