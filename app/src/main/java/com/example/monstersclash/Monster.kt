package com.example.monstersclash

import android.widget.ImageView

class Monster(
    val name: String,
    val description: String,
    val ataque: Int,
    val defesa: Int,
    var cardImageView: ImageView? = null
)