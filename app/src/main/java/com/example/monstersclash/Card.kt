package com.example.monstersclash

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlin.math.sqrt

class Card(
    private val context: Context,
    private val cardsContainer: View,
    private val handCards: HandCards
) {
    private var cardWidth = 0
    private var cardHeight = 0
    val imageView = ImageView(context)
    private var aspectRatio = 1.0f

    init {
        //TODO("Create dummy card if not setted")
        //createCardImage(5, CardWidth.MEDIUM.value)
    }

    fun createCardImage(imageResId: Int, cardWidth: Int) {
        imageView.setImageResource(imageResId)

        val drawable = ContextCompat.getDrawable(context, imageResId)

        aspectRatio = if (drawable != null && drawable.intrinsicHeight > 0) {
            drawable.intrinsicWidth.toFloat() / drawable.intrinsicHeight
        } else {
            1.0f
        }
        this.cardWidth = cardWidth
        this.cardHeight = (cardWidth / aspectRatio).toInt()

        imageView.layoutParams = FrameLayout.LayoutParams(cardWidth, cardHeight)
    }

    fun positionInFanOfCards(
        centralCardIndex: Int,
        currentPosition: Int
    ) {
        val numOfCards = handCards.getNumOfCards()
        val totalAngle = handCards.totalAngle

        val centerCardGrowRatio = 2.0
        val horizontalOffsetFromCentralCard = cardWidth * 0.6f

        var currentCardWidth = cardWidth
        var currentCardHeight = cardHeight

        if (currentPosition == centralCardIndex) {
            currentCardWidth = (cardWidth * sqrt(centerCardGrowRatio)).toInt()
            currentCardHeight = (cardHeight * sqrt(centerCardGrowRatio)).toInt()
        }

        val centerX = cardsContainer.x + cardsContainer.width / 2
        val centerY = cardsContainer.y + cardsContainer.height / 2

        // Calcular para destacar a carta central com grau 0
        val angleStep = totalAngle / (numOfCards - 1)
        val startAngle = -angleStep * centralCardIndex

        val xOffset = when {
            currentPosition < centralCardIndex -> -horizontalOffsetFromCentralCard
            currentPosition > centralCardIndex -> horizontalOffsetFromCentralCard
            else -> 0f
        }

        var yOffset = 0f
        if (currentPosition == centralCardIndex) {
            yOffset = -currentCardWidth * 0.5f
        }

        imageView.apply {
            layoutParams = FrameLayout.LayoutParams(currentCardWidth, currentCardHeight)

            x = centerX - currentCardWidth / 2 + xOffset
            y = centerY - currentCardHeight + yOffset

            pivotX = currentCardWidth / 2f
            pivotY = currentCardHeight.toFloat()

            rotation = startAngle + currentPosition * angleStep
        }
    }
}
