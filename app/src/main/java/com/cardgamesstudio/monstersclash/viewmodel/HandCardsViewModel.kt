package com.cardgamesstudio.monstersclash.viewmodel

import com.cardgamesstudio.monstersclash.model.HandCards
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cardgamesstudio.monstersclash.model.Card

class HandCardsViewModel(private val handCards: HandCards){

    private val displayCardList = MutableLiveData<MutableList<Card>>()
    private val displayCurrentCardIndex = MutableLiveData(0)

    fun getDisplayCardList(): LiveData<MutableList<Card>> {
        return displayCardList
    }

    fun getDisplayCurrentCardIndex(): LiveData<Int> {
        return displayCurrentCardIndex
    }

    fun removeCurrentCard() {
        handCards.removeCurrentCard()
    }

    fun selectPreviousCard() {
        handCards.selectPreviousCard()
    }

    fun selectNextCard() {
        handCards.selectNextCard()
    }

    init {
        displayCardList.value = handCards.getCardList().value
        handCards.getCardList().observeForever { newCardList ->
            displayCardList.value = newCardList
        }

        displayCurrentCardIndex.value = handCards.getCurrentCard().value
        handCards.getCurrentCard().observeForever { newCurrentCard ->
            displayCurrentCardIndex.value = newCurrentCard
        }
    }
}
