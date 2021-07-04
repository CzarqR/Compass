package com.myniprojects.compass.model

import androidx.annotation.StringRes

data class SnackbarData(
    @StringRes val message: Int,
    @StringRes val buttonText: Int? = null,
    val action: () -> Unit = {}
)