package com.example.gettingstartedwithkmm.ui.shared.base

import com.example.gettingstartedwithkmm.Platform
import com.russhwolf.settings.Settings
import kotlin.math.max
import kotlin.math.min
import kotlinx.datetime.Clock

class MainViewModel(
    platform: Platform,
    settings: Settings
) : PlatformViewModel(){

    val items: List<RowItem> = makeRowItems(platform)
    val firstOpening: String

    init {
        val timestampKey = "FIRST_OPENING_TIMESTAMP"
        val savedValue = settings.getLongOrNull(timestampKey)

        firstOpening = if (savedValue == null) {
            val time = Clock.System.now().epochSeconds - 1
            settings.putLong(timestampKey, time)

            time.toString()
        } else {
            savedValue.toString()
        }
    }


    data class RowItem(
        val title: String,
        val subtitle: String
    )

    private fun makeRowItems(platform: Platform): List<RowItem> {
        val rowItems = mutableListOf(
            RowItem("Operating System", "${platform.osName} ${platform.osVersion}"),
            RowItem("Device", platform.deviceModel),
            RowItem("CPU", platform.cpuType),
        )
        platform.screen.let {
            rowItems.add(
                RowItem(
                    "Display",
                    "${
                        max(it.width, it.height)
                    }×${
                        min(it.width, it.height)
                    } @${it.density}x"
                ),
            )
        }
        return rowItems
    }

}