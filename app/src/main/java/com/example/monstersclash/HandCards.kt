package com.example.monstersclash

import android.content.Context
import android.view.View
import android.view.ViewGroup

class HandCards(
    private val gameAreaLayout: ViewGroup,
    private val context: Context,
    private val cardsContainer: View
) {
    private val cards: MutableList<Card> = mutableListOf()
    val totalAngle = 150.0f

    fun createHandCards(cardCount: Int, cardWidth: Int) {

        for (i in 1..cardCount) {
            val card = Card(context, cardsContainer, this)
            card.createCardImage(R.mipmap.card_heart_a, cardWidth)
            gameAreaLayout.addView(card.imageView)
            cards.add(card)
        }

    }

    fun getNumOfCards() : Int {
        return cards.size
    }

    fun positionHandCards(centralCardIndex: Int) {
        cards.forEachIndexed { index, card ->
            card.positionInFanOfCards(centralCardIndex, index)
        }
    }
}