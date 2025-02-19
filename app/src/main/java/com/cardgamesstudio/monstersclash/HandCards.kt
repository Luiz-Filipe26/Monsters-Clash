package com.cardgamesstudio.monstersclash

import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup

class HandCards(
    private val gameAreaLayout: ViewGroup,
    private val context: Context,
    private val handCardsContainer: View
) {
    private val cards: MutableList<Card> = mutableListOf()
    private var cardWidth = CardSize.MEDIUM


    fun createHandCards(cardCount: Int, cardWidth: CardSize) {
        this.cardWidth = cardWidth

        for (i in 1..cardCount) {
            val card = Card(context, handCardsContainer, this)
            val monsterImageBitmap =
                BitmapFactory.decodeResource(context.resources, R.mipmap.monster_1)

            val monster = Monster(
                "Monstro",
                "Descricao do monstro",
                80,
                100,
                monsterImageBitmap
            )

            card.createMonsterCard(monster)

            gameAreaLayout.addView(card.imageView)
            cards.add(card)
        }

    }

    fun getNumOfCards(): Int {
        return cards.size
    }

    fun removeCardFromHand(cardId: Int) {
        val card: Card = cards[cardId]
        gameAreaLayout.removeView(card.imageView)
        cards.removeAt(cardId)
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