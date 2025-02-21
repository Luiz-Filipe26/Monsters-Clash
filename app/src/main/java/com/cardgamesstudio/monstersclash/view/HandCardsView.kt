package com.cardgamesstudio.monstersclash.view

import android.app.AlertDialog
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.doOnLayout
import com.cardgamesstudio.monstersclash.constants.CardSize
import com.cardgamesstudio.monstersclash.constants.Direction
import com.cardgamesstudio.monstersclash.constants.HAND_CARDS_TOTAL_ANGLE
import com.cardgamesstudio.monstersclash.constants.HIGHLIGHTED_CARD_GROW_RATIO
import com.cardgamesstudio.monstersclash.constants.HIGHLIGHTED_X_OFFSET_RATIO
import com.cardgamesstudio.monstersclash.constants.HIGHLIGHTED_Y_OFFSET_RATIO
import com.cardgamesstudio.monstersclash.model.GameCard
import com.cardgamesstudio.monstersclash.viewmodel.HandCardsViewModel
import kotlin.math.sqrt

class HandCardsView(
    private val gameAreaLayout: ViewGroup,
    private val handCardsContainer: View,
    private val handCardsViewModel: HandCardsViewModel
) {
    private val cardViews: MutableList<CardView> = mutableListOf()
    private var cardWidth = CardSize.MEDIUM
    private var selectedCard = 0

    init {
        handCardsViewModel.getDisplayCardList().observeForever { cardList ->
            createHandCards(cardList)
        }
        handCardsViewModel.getDisplayCurrentCardIndex().observeForever { currentCardIndex ->
            positionHandCards(currentCardIndex)
        }

        gameAreaLayout.doOnLayout {
            val validSwipeArea = Rect().apply {
                handCardsContainer.getGlobalVisibleRect(this)
            }

            GestureHandler().detectSwipe(
                gameAreaLayout,
                gameAreaLayout.width / 10f,
                testIsValidArea = { x, y ->
                    validSwipeArea.contains(x.toInt(), y.toInt())
                }
            ) { direction ->
                when (direction) {

                    Direction.RIGHT -> handCardsViewModel.selectPreviousCard()

                    Direction.LEFT -> handCardsViewModel.selectNextCard()

                    Direction.UP -> if (getNumOfCards() - 1 > 0) {
                        val dialog: AlertDialog = createPositionDialog()
                        dialog.show()
                    }

                    else -> {}
                }
            }

        }

    }

    fun createHandCards(gameCardList: List<GameCard>) {

        cardViews.forEach {
            gameAreaLayout.removeView(it.imageView)
        }
        cardViews.clear()

        for (card in gameCardList) {
            val cardView =
                CardView(ImageView(gameAreaLayout.context), handCardsContainer, card.cardImage)
            cardView.setDimensions(cardWidth)

            gameAreaLayout.addView(cardView.imageView)
            cardViews.add(cardView)
        }

    }

    fun getNumOfCards(): Int {
        return cardViews.size
    }

    fun positionHandCards(centralCardIndex: Int) {
        gameAreaLayout.doOnLayout {
            cardViews.forEachIndexed { index, card ->
                card.setDimensions(cardWidth)
                positionInFanOfCards(centralCardIndex, index, card)

                // Z incremental baseado no índice
                card.imageView.translationZ = index + 1f

                // Se for a carta central, coloca em frente a todas as outras
                if (index == centralCardIndex) {
                    card.imageView.translationZ = cardViews.size + 2f
                }
            }
        }
    }


    private fun positionInFanOfCards(
        centralCardIndex: Int,
        currentPosition: Int,
        cardView: CardView
    ) {
        val numOfCards = getNumOfCards()
        val totalAngle = HAND_CARDS_TOTAL_ANGLE

        val hightlightedCardGrowRatio = HIGHLIGHTED_CARD_GROW_RATIO
        val xOffsetFromHighlightedCard = cardView.cardWidth * HIGHLIGHTED_X_OFFSET_RATIO

        var currentCardWidth = cardView.cardWidth
        var currentCardHeight = cardView.cardHeight

        if (currentPosition == centralCardIndex) {
            currentCardWidth = (currentCardWidth * sqrt(hightlightedCardGrowRatio)).toInt()
            currentCardHeight = (currentCardHeight * sqrt(hightlightedCardGrowRatio)).toInt()
        }

        val centerX = handCardsContainer.x + handCardsContainer.width / 2
        val centerY = handCardsContainer.y + handCardsContainer.height * 4 / 5

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

        cardView.imageView.apply {
            layoutParams = FrameLayout.LayoutParams(currentCardWidth, currentCardHeight)

            x = centerX - currentCardWidth / 2 + xOffset
            y = centerY - currentCardHeight / 2 + yOffset

            pivotX = currentCardWidth / 2f
            pivotY = currentCardHeight.toFloat() + 50

            rotation = startAngle + currentPosition * angleStep
        }
    }


    private fun createPositionDialog(): AlertDialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(gameAreaLayout.context)
        builder
            .setTitle("Posicionar em mode de:")
            .setPositiveButton("Posicionar") { dialog, which ->

                handCardsViewModel.removeCurrentCard()
            }
            .setNegativeButton("Cancelar") { dialog, which ->
                Log.d("PrintDebug", "Cancelado")
            }
            .setSingleChoiceItems(
                arrayOf("ATK", "DEF"), 0
            ) { dialog, which ->
                if (which == 0) {
                    Log.d("PrintDebug", "ATK")
                } else {
                    Log.d("PrintDebug", "DEF")
                }
            }

        return builder.create()
    }

}