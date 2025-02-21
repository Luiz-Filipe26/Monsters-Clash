package com.cardgamesstudio.monstersclash.localdata

import android.content.ContentValues
import android.content.Context

class CardRepository(context: Context) {

    private val database: DatabaseSQLite = DatabaseSQLite(context)

    fun getLatestUpdatedAt(): String? {
        val db = database.readableDatabase
        val query = "SELECT MAX(updated_at) FROM card"

        db.rawQuery(query, null).use { cursor ->
            if (cursor.moveToFirst()) {
                return cursor.getString(0) ?: return null
            }
        }
        return null
    }

    fun getAllCards(): List<LocalCard> {
        val db = database.readableDatabase
        val cursor = db.query(
            DatabaseContract.CARD.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val cards = mutableListOf<LocalCard>()
        while (cursor.moveToNext()) {
            val card = DBUtils.lineToObject(cursor, LocalCard::class.java)
            cards.add(card)
        }

        return cards
    }

    fun registerCard(card: LocalCard): Long {
        val db = database.writableDatabase
        return db.insert(DatabaseContract.CARD.TABLE_NAME, null, fillValuesForCard(card))
    }

    fun registerCards(cards: List<LocalCard>) {
        val db = database.writableDatabase
        db.beginTransaction()
        try {
            for (card in cards) {
                db.insert(DatabaseContract.CARD.TABLE_NAME, null, fillValuesForCard(card))
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    private fun fillValuesForCard(card: LocalCard): ContentValues {
        return ContentValues().apply {
            put(DatabaseContract.CARD.COLUMN_NAME_NAME, card.name)
            put(DatabaseContract.CARD.COLUMN_NAME_DESCRIPTION, card.description)
            put(DatabaseContract.CARD.COLUMN_NAME_ATTACK, card.attack)
            put(DatabaseContract.CARD.COLUMN_NAME_DEFENSE, card.defense)
            put(DatabaseContract.CARD.COLUMN_NAME_TYPE, card.type)
            put(DatabaseContract.CARD.COLUMN_NAME_MONSTER_IMAGE, card.monsterImage)
            put(DatabaseContract.CARD.COLUMN_NAME_CARD_IMAGE, card.cardImage)
        }
    }
}