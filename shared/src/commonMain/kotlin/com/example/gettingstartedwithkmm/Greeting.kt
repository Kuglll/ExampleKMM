package com.example.gettingstartedwithkmm

import com.example.gettingstartedwithkmm.models.RocketLaunch
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class Greeting {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    private val platform: Platform = Platform()

    fun startMessage() = "OS version: ${platform.osVersion} + \n Cpu type: ${platform.cpuType} \n device model: ${platform.deviceModel}"

    suspend fun greeting(): String {

        val rockets: List<RocketLaunch> = httpClient.get("https://api.spacexdata.com/v4/launches").body()
        val lastSuccessLaunch = rockets.last { it.launchSuccess == true }

        return "Guess what it is! > ${platform.osVersion}!" +
            "\nThe last successful launch was ${lastSuccessLaunch.launchDateUTC} 🚀"
    }
}