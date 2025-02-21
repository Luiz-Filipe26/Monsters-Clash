package com.cardgamesstudio.monstersclash.model

class DrawPile(nameToGameCard : MutableMap<String, GameCard>) {
    private val cardList = createCardList(nameToGameCard)
    private var currentCard = 0

    private fun createCardList(nameToGameCard: MutableMap<String, GameCard>) : MutableList<GameCard> {
        val newGameCardList = mutableListOf<GameCard>()
        val card = nameToGameCard.iterator().next().value

        for(i in 1..10) {
            newGameCardList.add(card)
        }

        newGameCardList.shuffle()

        return newGameCardList
    }

    fun take(numOfCards: Int): List<GameCard> {
        val availableCards = cardList.size - currentCard
        val numToTake = minOf(numOfCards, availableCards)

        return List(numToTake) { cardList[currentCard++] }
    }

    fun getCard(): GameCard? {
        if (currentCard >= cardList.size) return null
        return cardList[currentCard++]
    }

    fun hasCard(): Boolean {
        return cardList.isNotEmpty()
    }
}