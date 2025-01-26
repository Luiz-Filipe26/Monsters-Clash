package com.example.monstersclash

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlin.math.sqrt

class Card(
    private val context: Context,
    private val handCardsContainer: View,
    private val handCards: HandCards
) {
    private var cardWidth = 0
    private var cardHeight = 0
    val imageView = ImageView(context)
    private var aspectRatio = 1.0f
    private var imageResId = 0

    init {
        //TODO("Create dummy card if not setted")
        //createCardImage(5, CardWidth.MEDIUM.value)
    }

    fun setCardImage(imageResId: Int) {
        imageView.setImageResource(imageResId)
        this.imageResId = imageResId
    }

    fun setDimensions(cardSize: CardSize) {
        val drawable = ContextCompat.getDrawable(context, imageResId)
        aspectRatio = if (drawable != null && drawable.intrinsicHeight > 0) {
            drawable.intrinsicWidth.toFloat() / drawable.intrinsicHeight
        } else {
            1.0f
        }
        cardWidth = when(cardSize) {
            CardSize.MEDIUM -> handCardsContainer.width / 5
            CardSize.SMALL -> handCardsContainer.width / 5 - handCardsContainer.width / 10
            CardSize.BIG -> handCardsContainer.width / 5 + handCardsContainer.width / 10
        }


        cardHeight = (cardWidth / aspectRatio).toInt()

        imageView.layoutParams = FrameLayout.LayoutParams(cardWidth, cardHeight)
    }

    fun positionInFanOfCards(
        centralCardIndex: Int,
        currentPosition: Int
    ) {

        val numOfCards = handCards.getNumOfCards()
        val totalAngle = HAND_CARDS_TOTAL_ANGLE

        val hightlightedCardGrowRatio = HIGHLIGHTED_CARD_GROW_RATIO
        val xOffsetFromHighlightedCard = cardWidth * HIGHLIGHTED_X_OFFSET_RATIO

        var currentCardWidth = cardWidth
        var currentCardHeight = cardHeight

        if (currentPosition == centralCardIndex) {
            currentCardWidth = (cardWidth * sqrt(hightlightedCardGrowRatio)).toInt()
            currentCardHeight = (cardHeight * sqrt(hightlightedCardGrowRatio)).toInt()
        }

        val centerX = handCardsContainer.x + handCardsContainer.width / 2
        val centerY = handCardsContainer.y + handCardsContainer.height * 4/5

        // Calcular para destacar a carta central com grau 0
        val angleStep = totalAngle / (numOfCards - 1)
        val startAngle = -angleStep * centralCardIndex

        val xOffset = when {
            currentPosition < centralCardIndex -> -xOffsetFromHighlightedCard
            currentPosition > centralCardIndex -> xOffsetFromHighlightedCard
            else -> 0f
        }

        var yOffset = 0f
        if (currentPosition == centralCardIndex) {
            yOffset = -currentCardHeight * HIGHLIGHTED_Y_OFFSET_RATIO
        }

        imageView.apply {
            layoutParams = FrameLayout.LayoutParams(currentCardWidth, currentCardHeight)

            x = centerX - currentCardWidth / 2 + xOffset
            y = centerY - currentCardHeight / 2 + yOffset

            pivotX = currentCardWidth / 2f
            pivotY = currentCardHeight.toFloat() + 50

            rotation = startAngle + currentPosition * angleStep
        }
    }
}
