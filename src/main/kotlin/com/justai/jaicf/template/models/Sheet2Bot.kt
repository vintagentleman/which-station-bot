package com.justai.jaicf.template.models

import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import java.lang.RuntimeException

class Sheet2Bot {
    private val rows
        get(): List<StationRecording> {
            val (_, _, result) = Fuel.get("/rows").responseString()

            return when (result) {
                is Result.Success ->
                    Klaxon()
                        .parseArray<ArrayList<String>>(result.component1()!!)
                        ?.map { StationRecording(it[0], it[1], it[2], it[3], it[4]) }
                        ?: listOf()
                else ->
                    throw RuntimeException("Empty response from sheet2bot API")
            }
        }

    val randomRow
        get(): StationRecording = this.rows.random()
}
