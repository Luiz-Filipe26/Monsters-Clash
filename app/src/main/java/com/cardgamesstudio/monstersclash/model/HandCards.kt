package com.cardgamesstudio.monstersclash.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HandCards(initialCardList: MutableList<Card>, currentCardIndex: Int) {

    private val cardList = MutableLiveData(initialCardList)
    private val currentCardIndex = MutableLiveData(currentCardIndex)

    fun getCardList(): LiveData<MutableList<Card>> {
        return cardList
    }

    fun getCurrentCard(): LiveData<Int> {
        return currentCardIndex
    }

    fun selectNextCard() {
        currentCardIndex.value?.let { index ->
            if (index + 1 < (cardList.value?.size ?: 0)) {
                currentCardIndex.value = index + 1
            }
        }
    }

    fun selectPreviousCard() {
        currentCardIndex.value?.let { index ->
            if (index > 0) {
                currentCardIndex.value = index - 1
            }
        }
    }

    fun addCard(card: Card) {
        cardList.value?.apply {
            add(card)
        }
        cardList.value = cardList.value
    }

    fun removeCurrentCard() {
        cardList.value?.let { list ->
            val index = currentCardIndex.value ?: return
            if (index in list.indices) {
                list.removeAt(index)
                cardList.value = list
                currentCardIndex.value = index.coerceAtMost(list.lastIndex)
            }
        }
    }

    fun clearCards() {
        cardList.value = mutableListOf()
    }
}