package com.justai.jaicf.template

import com.justai.jaicf.BotEngine
import com.justai.jaicf.activator.caila.CailaIntentActivator
import com.justai.jaicf.activator.caila.CailaNLUSettings
import com.justai.jaicf.activator.catchall.CatchAllActivator
import com.justai.jaicf.activator.regex.RegexActivator
import com.justai.jaicf.context.manager.InMemoryBotContextManager
import com.justai.jaicf.context.manager.mongo.MongoBotContextManager
import com.justai.jaicf.template.scenario.MainScenario
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import java.util.Properties

private fun jaicpProperty(key: String) = Properties().run {
    load(CailaNLUSettings::class.java.getResourceAsStream("/jaicp.properties"))
    getProperty(key)
}

private val contextManager = System.getenv("MONGODB_URI")?.let { url ->
    val uri = MongoClientURI(url)
    val client = MongoClient(uri)
    MongoBotContextManager(client.getDatabase(uri.database!!).getCollection("contexts"))
} ?: InMemoryBotContextManager

val accessToken: String = System.getenv("JAICP_API_TOKEN") ?: jaicpProperty("apiToken")
val gsheetId: String = System.getenv("GSHEET_ID") ?: jaicpProperty("gsheetId")

private val cailaNLUSettings = CailaNLUSettings(
    accessToken = accessToken,
    confidenceThreshold = 0.2,
    cailaUrl = "https://jaicf01-demo-htz.lab.just-ai.com/cailapub/api/caila/p"
)

val templateBot = BotEngine(
    model = MainScenario.model,
    defaultContextManager = contextManager,
    activators = arrayOf(
        CailaIntentActivator.Factory(cailaNLUSettings),
        RegexActivator,
        CatchAllActivator
    )
)
