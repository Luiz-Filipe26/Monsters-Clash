package com.cardgamesstudio.monstersclash.view

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.cardgamesstudio.monstersclash.constants.CardSize

class CardView(
    val imageView : ImageView,
    private val handCardsContainer: View,
    cardImage: Bitmap
) {
    var cardWidth = 0
    var cardHeight = 0
    private var aspectRatio = 1.0f

    init {
        imageView.setImageBitmap(cardImage)
    }

    fun setDimensions(cardSize: CardSize) {
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        aspectRatio = bitmap.width.toFloat() / bitmap.height.toFloat()

        cardWidth = when (cardSize) {
            CardSize.MEDIUM -> handCardsContainer.width / 4
            CardSize.SMALL -> handCardsContainer.width / 4 - handCardsContainer.width / 10
            CardSize.BIG -> handCardsContainer.width / 4 + handCardsContainer.width / 10
        }

        cardHeight = (cardWidth / aspectRatio).toInt()

        imageView.layoutParams = FrameLayout.LayoutParams(cardWidth, cardHeight)
    }

}
