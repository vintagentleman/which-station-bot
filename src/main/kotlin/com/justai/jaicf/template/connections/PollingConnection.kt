package com.justai.jaicf.template.connections

import com.github.kittinunf.fuel.core.FuelManager
import com.justai.jaicf.channel.jaicp.JaicpPollingConnector
import com.justai.jaicf.channel.jaicp.channels.ChatWidgetChannel
import com.justai.jaicf.channel.telegram.TelegramChannel
import com.justai.jaicf.template.accessToken
import com.justai.jaicf.template.gsheetId
import com.justai.jaicf.template.templateBot

fun main() {
    FuelManager.instance.basePath = "https://sheet2bot.herokuapp.com/api"
    FuelManager.instance.baseParams = listOf("sheet" to gsheetId, "range" to "'which-station'")

    JaicpPollingConnector(
        templateBot,
        accessToken,
        url = "https://jaicf01-demo-htz.lab.just-ai.com/chatadapter",
        channels = listOf(
            ChatWidgetChannel,
            TelegramChannel
        )
    ).runBlocking()
}
