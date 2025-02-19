package com.cardgamesstudio.monstersclash

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.cardsgamestudio.monstersclash.R

class CardsLoader(private val viewGroup: ViewGroup) {

    private val nameToCardData = mutableMapOf<String, CardData>()

    fun loadFromDatabase() {

        val monsterImageBitmap =
            BitmapFactory.decodeResource(viewGroup.context.resources, R.mipmap.monster_1)

        val monster = Monster(
            "Monstro",
            "Descricao do monstro",
            80,
            100,
            monsterImageBitmap
        )

        val cardImage = createCardImage(monster, viewGroup)

        nameToCardData[monster.name] = CardData(monster, cardImage)
    }

    fun getNameToCardData(): MutableMap<String, CardData> {
        return nameToCardData
    }

    private fun createCardImage(
        monster: Monster,
        viewGroup: ViewGroup
    ) : Bitmap {
        viewGroup.findViewById<ImageView>(R.id.card_monster_img).setImageBitmap(monster.monsterImage)
        viewGroup.findViewById<TextView>(R.id.card_title_txt).text = monster.name
        viewGroup.findViewById<TextView>(R.id.card_desc_txt).text = monster.description
        viewGroup.findViewById<TextView>(R.id.card_atk_txt).text = "${monster.attack}"
        viewGroup.findViewById<TextView>(R.id.card_def_txt).text = "${monster.defense}"

        viewGroup.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )

        viewGroup.layout(0, 0, viewGroup.measuredWidth, viewGroup.measuredHeight)

        val imageBitmap = convertLayoutToBitmap(viewGroup)
        return imageBitmap
    }


    private fun convertLayoutToBitmap(layout: View): Bitmap {
        val bitmap = Bitmap.createBitmap(layout.measuredWidth, layout.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        layout.draw(canvas)

        return bitmap
    }
}