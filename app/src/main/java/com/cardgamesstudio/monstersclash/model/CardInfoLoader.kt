package com.cardgamesstudio.monstersclash.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.cardgamesstudio.monstersclash.R
import com.cardgamesstudio.monstersclash.localdata.CardRepository
import com.cardgamesstudio.monstersclash.localdata.LocalCard
import com.cardgamesstudio.monstersclash.model.repository_data.CardEntity
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CardInfoLoader(private val supabaseClient: SupabaseClient, private val cardRepository: CardRepository, private val gameCard: ViewGroup) {

    private val nameToGameCard = mutableMapOf<String, GameCard>()

    suspend fun loadAllCards() {
        val latestCardTime = cardRepository.getLatestUpdatedAt()

        var cardList = listOf<CardEntity>()

        try {
            withContext(Dispatchers.IO) {
                cardList = supabaseClient
                    .from("cards")
                    .select(Columns.ALL) {
                        if(latestCardTime != null) {
                            filter {
                                CardEntity::updatedAt gt latestCardTime
                            }
                        }
                    }
                    .decodeList<CardEntity>()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }


        if(cardList.isNotEmpty()) {
            val cardsBucket = supabaseClient.storage.from("cards")
            val cardToBytesOfImage = mutableMapOf<CardEntity, ByteArray>()
            cardList.forEach {
                val bytes = cardsBucket.downloadPublic(it.monsterImageRef)
                cardToBytesOfImage[it] = bytes
            }

            val cardList = cardToBytesOfImage.map { (card, bytesOfImage) ->
                val monsterImageBitMap = BitmapFactory.decodeByteArray(bytesOfImage, 0, bytesOfImage.size)
                val monster = Monster(card.name, card.description, card.attack, card.defense, monsterImageBitMap)
                val cardImage = createCardImage(monster, gameCard)
                val gameCard = GameCard(monster, cardImage)

                nameToGameCard[monster.name] = gameCard
            }

        }


    }

    fun getNameToCardData(): MutableMap<String, GameCard> {
        return nameToGameCard
    }

    private fun createCardImage(
        monster: Monster,
        gameCard: ViewGroup
    ): Bitmap {
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
        val bitmap = Bitmap.createBitmap(
            layout.measuredWidth,
            layout.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)

        layout.draw(canvas)

        return bitmap
    }
}