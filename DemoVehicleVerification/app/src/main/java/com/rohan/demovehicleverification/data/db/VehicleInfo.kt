package com.rohan.demovehicleverification.data.db

import android.graphics.Bitmap
import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Keep
@Entity(tableName = "vehicle_info_table")
data class VehicleInfo(

    var img: Bitmap? = null,

    var createdDate: Long = 0L,

    var approved: Boolean = false,
    var found: Boolean = false,

    var registrationNumber: String = "",
    var co2Emissions: Int = 0,
    var engineCapacity: Int = 0,
    var markedForExport: Boolean = false,
    var fuelType: String = "",
    var motStatus: String = "",
    var colour: String = "",
    var make: String = "",
    var typeApproval: String = "",
    var yearOfManufacture: Int = 0,
    var taxDueDate: String = "",
    var taxStatus: String = "",
    var dateOfLastV5CIssued: String = "",
    var motExpiryDate: String = "",
    var wheelplan: String = "",
    var monthOfFirstRegistration: String = "",

    ) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
