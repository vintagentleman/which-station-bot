package com.justai.jaicf.template.scenario

import com.justai.jaicf.channel.telegram.telegram
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.models.Sheet2Bot

object MainScenario : Scenario() {
    private val api = Sheet2Bot()

    init {
        state("/Start") {
            globalActivators {
                regex("/start")
            }

            action {
                val pair = listOf(api.randomRow, api.randomRow)

                reactions.say("Привет! Хочу сыграть с тобой в игру на знание питерской подземки.\nВот послушай.")
                reactions.telegram?.sendAudio(pair.first().longAudio)
                reactions.telegram?.sendAudio(pair.last().longAudio)
                reactions.say("${pair.first().name} и ${pair.last().name}, не так ли?")

                context.result = listOf(pair.first().shortAudio, pair.last().shortAudio)
            }

            fallback {
                reactions.say("А теперь послушай ещё раз, но без названий самих станций.")
                if (context.result is List<*>) {
                    reactions.telegram?.sendAudio((context.result as List<*>).first() as String)
                    reactions.telegram?.sendAudio((context.result as List<*>).last() as String)
                }
                reactions.say("Твоя задача — угадать станцию по тому, как диктор произносит слова «следующая станция». Всё.\nНачнём?")
            }
        }

        state("/Help") {
            globalActivators {
                regex("/help")
            }
            action {
                reactions.say(
                    """
                    Мы ездим в метро каждый день и постоянно слышим привычный голос Михаила Быкова. «Во избежание травм держитесь за поручни», «осторожно, двери закрываются», вот это всё.
                    А знаешь ли ты, что слова «следующая станция такая-то» в его исполнении всегда звучат по-разному?
                    Причём в объявлениях не только разных станций, но даже одной той же станции в зависимости от направления перегона!
                    Сможешь угадать, какая объявляется станция, только по словам «следующая станция»?
                    """.trimIndent()
                )
            }
        }

        fallback {
            reactions.say("Прости, я тебя не понял.")
        }
    }
}
