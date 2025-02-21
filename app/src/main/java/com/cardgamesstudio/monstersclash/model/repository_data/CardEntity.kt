package com.cardgamesstudio.monstersclash.model.repository_data

import kotlinx.serialization.Serializable

@Serializable
data class CardEntity(
    val id: Long,
    val description: String,
    val attack: Int,
    val defense: Int,
    val type: String,
    val monsterImageRef: String
)