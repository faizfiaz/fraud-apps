package com.fitness.tracking.common

import com.fitness.tracking.domain.models.Message

object DummyData {

    fun getDummyMessage(): ArrayList<Message> {
        var arrayData = arrayListOf(
                Message("PT Brilian Putra", "Direktur Utama Joko Widodo", "10/20"),
                Message("PT Brilian Putri", "Joko Susilo", "12/16"),
                Message("PT Segar Jiwa", "Derry Santoso", "9/16"),
                Message("PT Harapan Bangsa", "Galih Purnomo", "10/16")
        )
        var arrayTemp: ArrayList<Message> = ArrayList()
        arrayTemp.addAll(arrayData)
        arrayTemp.addAll(arrayData)
        arrayTemp.addAll(arrayData)
        arrayTemp.addAll(arrayData)
        arrayTemp.addAll(arrayData)
        arrayTemp.addAll(arrayData)
        return arrayTemp
    }
}