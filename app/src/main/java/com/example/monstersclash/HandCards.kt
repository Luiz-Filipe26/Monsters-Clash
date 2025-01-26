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
    private var cardWidth = CardSize.MEDIUM


    fun createHandCards(cardCount: Int, cardWidth: CardSize) {
        this.cardWidth = cardWidth

        for (i in 1..cardCount) {
            val card = Card(context, cardsContainer, this)
            card.setCardImage(R.mipmap.card_heart_a)
            gameAreaLayout.addView(card.imageView)
            cards.add(card)
        }

    }

    fun getNumOfCards() : Int {
        return cards.size
    }

    fun positionHandCards(centralCardIndex: Int) {
        cards.forEachIndexed { index, card ->
            card.setDimensions(cardWidth)
            card.positionInFanOfCards(centralCardIndex, index)

            // Z incremental baseado no índice
            card.imageView.translationZ = index + 1f

            // Se for a carta central, coloca em frente a todas as outras
            if (index == centralCardIndex) {
                card.imageView.translationZ = cards.size + 2f
            }
        }
    }
}