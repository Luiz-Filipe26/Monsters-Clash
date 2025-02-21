package com.cardgamesstudio.monstersclash.repository

import android.content.ContentValues
import android.content.Context
import com.cardgamesstudio.monstersclash.localdata.DatabaseSQLite

class CardRepository(context: Context) {

    lateinit var database: DatabaseSQLite

    init {
        database = DatabaseSQLite(context)
    }

//    fun registerCard(card: card): Long {
//
//        val dataBaseEdit = database.writableDatabase
//
//        val valuesFood: ContentValues = ContentValues()
//        valuesFood.put(DatabaseContract.FOOD.COLUMN_NAME_NAME, food.name)
//        valuesFood.put(DatabaseContract.FOOD.COLUMN_NAME_CALORIES, food.calories)
//        valuesFood.put(DatabaseContract.FOOD.COLUMN_NAME_DAY, food.date.time)
//
//        val idFood = dataBaseEdit.insert(
//            DatabaseContract.FOOD.TABLE_NAME,
//            null,
//            valuesFood
//        )
//
//        return idFood
//
//    }

}
