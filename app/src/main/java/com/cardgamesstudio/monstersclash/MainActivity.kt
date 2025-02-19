package com.cardgamesstudio.monstersclash

import com.cardgamesstudio.monstersclash.model.HandCards
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.core.view.doOnLayout
import com.cardgamesstudio.monstersclash.constants.Direction
import com.cardgamesstudio.monstersclash.databinding.ActivityMainBinding
import com.cardgamesstudio.monstersclash.model.CardInfoLoader
import com.cardgamesstudio.monstersclash.model.DrawPile
import com.cardgamesstudio.monstersclash.view.GestureHandler
import com.cardgamesstudio.monstersclash.view.HandCardsView
import com.cardgamesstudio.monstersclash.viewmodel.HandCardsViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var handCardsView: HandCardsView
    private lateinit var handCards: HandCards
    private var gestureHandler = GestureHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val inflater = LayoutInflater.from(this)
        val gameCard = inflater.inflate(R.layout.game_card, null) as ViewGroup

        val cardInfoLoader = CardInfoLoader(gameCard)
        cardInfoLoader.loadFromDatabase()
        val drawPile = DrawPile(cardInfoLoader.getNameToCardData())
        val cardList = drawPile.take(7).toMutableList()

        handCards = HandCards(cardList, 4)
        val handCardsViewModel = HandCardsViewModel(handCards)
        handCardsView = HandCardsView(binding.main, binding.handCardsContainer, handCardsViewModel)

        handCardsView.createHandCards(cardList)



        fillManaBar(3)
        updateHealthBar(60, 180)
    }

    private fun fillManaBar(manaPoints: Int) {
        val manaBar = binding.manaBar.root

        manaBar.children.forEachIndexed { index, child ->
            val manaPointImg = child as ImageView
            if (index < manaPoints) {
                manaPointImg.colorFilter =
                    PorterDuffColorFilter(Color.parseColor("#206080"), PorterDuff.Mode.SRC_IN)
            } else {
                manaPointImg.clearColorFilter()
            }
        }
    }

    private fun updateHealthBar(hpAtual: Int, hpTotal: Int) {
        val healthBarContainer = binding.healthBarContainer.root
        val progressBar = healthBarContainer.findViewById<ProgressBar>(R.id.hpProgressBar)

        val progressPercentage = (hpAtual * 100) / hpTotal
        progressBar.progress = progressPercentage
        binding.healthBarTxt.text = "HP: $hpAtual/$hpTotal"
    }
}