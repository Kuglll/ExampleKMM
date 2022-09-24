package com.example.gettingstartedwithkmm.domain.utils

import platform.Foundation.NSUUID

actual class UUID actual constructor() {
    actual val randomNumber: String
        get() = NSUUID.UUID().UUIDString
}