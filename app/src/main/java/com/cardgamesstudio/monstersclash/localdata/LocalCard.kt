package com.cardgamesstudio.monstersclash.localdata

data class LocalCard(
    val id: Long = 0L,
    val name: String = "",
    val description: String = "",
    val attack: Int = 0,
    val defense: Int = 0,
    val type: String = "",
    val monsterImage: ByteArray?,
    val cardImage: ByteArray?,
    val updatedAt: Long? = null
)
