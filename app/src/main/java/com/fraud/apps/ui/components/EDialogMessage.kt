package com.fraud.apps.ui.components

import com.fraud.apps.R

enum class EDialogMessage(var title: Int, var message: Int, var positiveButton: Int, var negativeButton: Int) {
    DOWNLOAD(R.string.alert_downloading, R.string.alert_downloading_message, R.string.alert_ok_button, R.string.alert_cancel_button), NO_INTERNET(R.string.no_internet_title, R.string.no_internet_description, -1, R.string.alert_cancel_button), ERROR(R.string.no_internet_title, R.string.no_internet_description, -1, R.string.alert_cancel_button), ERROR_FETCHING_REMOTE_DATA(R.string.error_fetching_remote_data_title, R.string.error_fetching_remote_data_description, -1, R.string.alert_cancel_button);

}