package com.example.gettingstartedwithkmm.domain.utils

import java.util.UUID

actual class UUID actual constructor(){

    actual val randomNumber: String
        get() = UUID.randomUUID().toString()

}