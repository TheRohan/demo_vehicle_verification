package com.rohan.demovehicleverification.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface VehicleNetworkServices {

    @POST("vehicles")
    suspend fun getLocationInfo(@Body body: RegistrationNumberModel): Response<VehicleResponse>
}