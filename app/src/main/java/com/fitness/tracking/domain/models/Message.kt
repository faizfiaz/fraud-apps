package com.fitness.tracking.domain.models

import java.io.Serializable

data class Message(
        val companyName: String,
        val name: String,
        val date: String
) : Serializable