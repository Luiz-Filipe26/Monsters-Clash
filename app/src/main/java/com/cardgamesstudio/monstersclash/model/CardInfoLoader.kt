package com.cardgamesstudio.monstersclash.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.cardgamesstudio.monstersclash.R

class CardInfoLoader(private val gameCard: ViewGroup) {

    private val nameToCard = mutableMapOf<String, Card>()

    fun loadFromDatabase() {
        // just a stub method

        val monsterImageBitmap =
            BitmapFactory.decodeResource(gameCard.context.resources, R.mipmap.monster_1)

        val monster = Monster(
            "Monstro",
            "Descricao do monstro",
            80,
            100,
            monsterImageBitmap
        )

        val cardImage = createCardImage(monster, gameCard)

        nameToCard[monster.name] = Card(monster, cardImage)
    }

    fun getNameToCardData(): MutableMap<String, Card> {
        return nameToCard
    }

    private fun createCardImage(
        monster: Monster,
        gameCard: ViewGroup
    ) : Bitmap {
        gameCard.findViewById<ImageView>(R.id.card_monster_img).setImageBitmap(monster.monsterImage)
        gameCard.findViewById<TextView>(R.id.card_title_txt).text = monster.name
        gameCard.findViewById<TextView>(R.id.card_desc_txt).text = monster.description
        gameCard.findViewById<TextView>(R.id.card_atk_txt).text = "${monster.attack}"
        gameCard.findViewById<TextView>(R.id.card_def_txt).text = "${monster.defense}"

        gameCard.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )

        gameCard.layout(0, 0, gameCard.measuredWidth, gameCard.measuredHeight)

        val imageBitmap = convertLayoutToBitmap(gameCard)
        return imageBitmap
    }


    private fun convertLayoutToBitmap(layout: View): Bitmap {
        val bitmap = Bitmap.createBitmap(layout.measuredWidth, layout.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        layout.draw(canvas)

        return bitmap
    }
}