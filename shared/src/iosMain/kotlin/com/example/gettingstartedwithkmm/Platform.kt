package com.example.gettingstartedwithkmm

import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.CoreGraphics.CGRectGetHeight
import platform.CoreGraphics.CGRectGetWidth
import platform.Foundation.NSLog
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithCString
import platform.UIKit.UIDevice
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceIdiomPad
import platform.UIKit.UIUserInterfaceIdiomPhone
import platform.posix.uname
import platform.posix.utsname

actual class Platform actual constructor() {
    //1
    actual val osName = when (UIDevice.currentDevice.userInterfaceIdiom) {
        UIUserInterfaceIdiomPhone -> "iOS"
        UIUserInterfaceIdiomPad -> "iPadOS"
        else -> kotlin.native.Platform.osFamily.name
    }

    //2
    actual val osVersion = UIDevice.currentDevice.systemVersion

    //3
    actual val deviceModel = "???"

    //4
    actual val cpuType = kotlin.native.Platform.cpuArchitecture.name

    //5
    actual val screen = ScreenInfo()

    //6
    actual fun logSystemInfo() {
        NSLog(
            "($osName; $osVersion; $deviceModel; ${screen!!.width}x${screen!!.height}@${screen!!.density}x; $cpuType)"
        )
    }
}

actual class ScreenInfo actual constructor() {
    //7
    actual val width = CGRectGetWidth(UIScreen.mainScreen.nativeBounds).toInt()
    actual val height = CGRectGetHeight(UIScreen.mainScreen.nativeBounds).toInt()
    actual val density = UIScreen.mainScreen.scale.toInt()
}