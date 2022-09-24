package com.example.gettingstartedwithkmm.ui.shared.base

import com.example.gettingstartedwithkmm.Platform
import kotlin.math.max
import kotlin.math.min

class MainViewModel(
    private val platform: Platform
) : PlatformViewModel(){

    val items: List<RowItem> = makeRowItems(platform)

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