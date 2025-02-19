package com.cardgamesstudio.monstersclash.model

class DrawPile(nameToCard : MutableMap<String, Card>) {
    private val cardList = createCardList(nameToCard)
    private var currentCard = 0

    private fun createCardList(nameToCard: MutableMap<String, Card>) : MutableList<Card> {
        val newCardList = mutableListOf<Card>()
        val card = nameToCard.iterator().next().value

        for(i in 1..10) {
            newCardList.add(card)
        }

        newCardList.shuffle()

        return newCardList
    }

    fun take(numOfCards: Int): List<Card> {
        val availableCards = cardList.size - currentCard
        val numToTake = minOf(numOfCards, availableCards)

        return List(numToTake) { cardList[currentCard++] }
    }

    fun getCard(): Card? {
        if (currentCard >= cardList.size) return null
        return cardList[currentCard++]
    }

    fun hasCard(): Boolean {
        return cardList.isNotEmpty()
    }
}