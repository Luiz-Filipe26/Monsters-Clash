package com.cardgamesstudio.monstersclash.localdata

import android.database.Cursor
import com.fasterxml.jackson.databind.ObjectMapper

class DBUtils {
    companion object {
        private val objectMapper = ObjectMapper()

        fun <T> lineToObject(cursor: Cursor, clazz: Class<T>): T {
            val fieldMap = mutableMapOf<String, Any?>()

            for (columnIndex in 0 until cursor.columnCount) {
                val columnName = cursor.getColumnName(columnIndex)
                val value = when (cursor.getType(columnIndex)) {
                    Cursor.FIELD_TYPE_INTEGER -> cursor.getLong(columnIndex)
                    Cursor.FIELD_TYPE_FLOAT -> cursor.getDouble(columnIndex)
                    Cursor.FIELD_TYPE_STRING -> cursor.getString(columnIndex)
                    Cursor.FIELD_TYPE_BLOB -> cursor.getBlob(columnIndex)
                    else -> null
                }
                fieldMap[columnName] = value
            }

            return objectMapper.convertValue(fieldMap, clazz)
        }

//        // caso precise
//        fun timeStampToInstant(timeStamp: String) : Instant {
//            return Instant.parse(timeStamp.replace(" ", "T"))
//        }
    }
}