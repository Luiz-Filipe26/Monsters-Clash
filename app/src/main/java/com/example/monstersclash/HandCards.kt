package com.example.monstersclash

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
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
        val monsterBitmap = BitmapFactory.decodeResource(context.resources, R.layout.game_card)
        for (i in 1..cardCount) {
            val card = Card(context, handCardsContainer, this)
            val monster: Monster = Monster(
                "Monstro",
                "Descricao do monstro",
                1000,
                200,

                )

            val monsterCardImageView = card.createMonsterCard(
                monsterBitmap,
                monster.name,
                monster.description,
                monster.ataque,
                monster.defesa
            )

            monster.cardImageView = monsterCardImageView

            card.setCardImage(R.mipmap.card_heart_a)
            gameAreaLayout.addView(monsterCardImageView)
            cards.add(card)
        }

    }

    fun getNumOfCards() : Int {
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