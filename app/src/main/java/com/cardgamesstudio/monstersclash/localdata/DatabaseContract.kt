package com.cardgamesstudio.monstersclash.localdata

class DatabaseContract {

    companion object{
        val DATABASE_NAME:String = "monsters_clash.db"
        val DATABASE_VERSION = 1

        val SQL_CREATE_TABLES = CARD.SQL_CREATE
        val SQL_DROP_TABLES = CARD.SQL_DROP
    }

    object CARD{
        val TABLE_NAME = "card"

        val COLUMN_NAME_ID = "id"
        val COLUMN_NAME_NAME = "name"
        val COLUMN_NAME_DESCRIPTION = "description"
        val COLUMN_NAME_ATTACK = "attack"
        val COLUMN_NAME_DEFENSE = "defense"
        val COLUMN_NAME_MONSTER_IMAGE = "monster_image"

        val SQL_CREATE = "CREATE TABLE IF NOT EXISTS ${TABLE_NAME} (" +
                "${COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${COLUMN_NAME_NAME} TEXT," +
                "${COLUMN_NAME_DESCRIPTION} TEXT," +
                "${COLUMN_NAME_ATTACK} INTEGER," +
                "${COLUMN_NAME_DEFENSE} INTEGER," +
                "${COLUMN_NAME_MONSTER_IMAGE} BLOB);"

        val SQL_DROP = "DROP TABLE IF EXISTS ${TABLE_NAME};"
    }
}