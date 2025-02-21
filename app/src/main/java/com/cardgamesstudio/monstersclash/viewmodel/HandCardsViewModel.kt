package com.cardgamesstudio.monstersclash.viewmodel

import com.cardgamesstudio.monstersclash.model.HandCards
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cardgamesstudio.monstersclash.model.GameCard

class HandCardsViewModel(private val handCards: HandCards){

    private val displayGameCardList = MutableLiveData<MutableList<GameCard>>()
    private val displayCurrentCardIndex = MutableLiveData(0)

    fun getDisplayCardList(): LiveData<MutableList<GameCard>> {
        return displayGameCardList
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
        displayGameCardList.value = handCards.getCardList().value
        handCards.getCardList().observeForever { newCardList ->
            displayGameCardList.value = newCardList
        }

        displayCurrentCardIndex.value = handCards.getCurrentCard().value
        handCards.getCurrentCard().observeForever { newCurrentCard ->
            displayCurrentCardIndex.value = newCurrentCard
        }
    }
}
