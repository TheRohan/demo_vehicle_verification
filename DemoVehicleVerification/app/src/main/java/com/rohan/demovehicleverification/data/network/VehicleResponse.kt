package com.rohan.demovehicleverification.data.network

import androidx.annotation.Keep

@Keep
data class VehicleResponse(
    var createdDate: Long? = 0L,
    var registrationNumber: String? = "",
    var co2Emissions: Int? = 0,
    var engineCapacity: Int? = 0,
    var markedForExport: Boolean? = false,
    var fuelType: String? = "",
    var motStatus: String? = "",
    var colour: String? = "",
    var make: String? = "",
    var typeApproval: String? = "",
    var yearOfManufacture: Int? = 0,
    var taxDueDate: String? = "",
    var taxStatus: String? = "",
    var dateOfLastV5CIssued: String? = "",
    var motExpiryDate: String? = "",
    var wheelplan: String? = "",
    var monthOfFirstRegistration: String? = "",
    )


