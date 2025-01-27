package com.example.monstersclash

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import com.example.monstersclash.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var handCards: HandCards
    private var selectedCard = 0
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

        handCards = HandCards(binding.main, this, binding.handCardsContainer)
        selectedCard = 3

        handCards.createHandCards(7, CardSize.MEDIUM)

        binding.main.post {
            val validSwipeArea = Rect().apply {
                binding.handCardsContainer.getGlobalVisibleRect(this)
            }

            gestureHandler.detectSwipe(
                binding.main,
                binding.main.width / 10f,
                testIsValidArea = { x, y ->
                    validSwipeArea.contains(x.toInt(), y.toInt())
                }
            ) { direction ->
                when (direction) {
                    Direction.RIGHT -> if (selectedCard - 1 >= 0) {
                        handCards.positionHandCards(--selectedCard)
                    }

                    Direction.LEFT -> if (selectedCard + 1 < handCards.getNumOfCards()) {
                        handCards.positionHandCards(++selectedCard)
                    }

                    else -> {}
                }
            }

            handCards.positionHandCards(selectedCard)

            fillManaBar(3)
        }
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


}