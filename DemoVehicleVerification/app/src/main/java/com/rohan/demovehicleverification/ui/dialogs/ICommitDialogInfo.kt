package com.rohan.demovehicleverification.ui.dialogs

import com.rohan.demovehicleverification.data.db.VehicleInfo

interface ICommitDialogInfo {
    fun dialogClosedWithStatus(status: Boolean, vi: VehicleInfo)
}