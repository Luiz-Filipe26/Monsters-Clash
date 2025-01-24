package com.example.monstersclash

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.content.ContextCompat
import com.example.monstersclash.databinding.ActivityMainBinding
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

        binding.cardsContainerImg.post {
            createCardsInHand(100, 7, 120f, 2)
        }
    }

    private fun getAspectRatio(drawableResId: Int): Float {
        val drawable = ContextCompat.getDrawable(this, drawableResId)

        drawable?.let {
            val width = it.intrinsicWidth
            val height = it.intrinsicHeight

            if (height != 0) {
                return width.toFloat() / height.toFloat()
            }
        }

        return 1.0f
    }

    private fun createCardsInHand(cardWidth: Int, cardCount: Int, totalAngle: Float, centralCardIndex: Int) {
        val centerCardGrowRatio = 2.0
        val horizontalOffsetFromCentralCard = cardWidth * 0.6f

        val aspectRatio = getAspectRatio(R.mipmap.card_heart_a)
        val cardHeight = (cardWidth / aspectRatio).toInt()

        val centerX = binding.cardsContainerImg.x + binding.cardsContainerImg.width / 2
        val centerY = binding.cardsContainerImg.y + binding.cardsContainerImg.height / 2

        val gameAreaLayout = binding.main

        val angleStep = totalAngle / (cardCount - 1)

        // Calcular o startAngle para centralizar a carta selecionada com centralCardIndex
        val startAngle = -angleStep * centralCardIndex

        for (i in 0 until cardCount) {
            // O ângulo de cada carta é ajustado para garantir que centralCardIndex tenha 0º
            val angle = startAngle + i * angleStep

            var currentCardWidth: Int
            var currentCardHeight: Int

            if (i == centralCardIndex) {
                currentCardWidth = (cardWidth * sqrt(centerCardGrowRatio)).toInt()
                currentCardHeight = (cardHeight * sqrt(centerCardGrowRatio)).toInt()
            } else {
                currentCardWidth = cardWidth
                currentCardHeight = cardHeight
            }

            val card = ImageView(this)
            card.setImageResource(R.mipmap.card_heart_a)
            card.layoutParams = FrameLayout.LayoutParams(currentCardWidth, currentCardHeight)

            // Posicionamento no eixo X (horizontal)
            card.x = centerX - currentCardWidth / 2
            card.y = centerY - currentCardHeight

            if (i < centralCardIndex) {
                card.x -= horizontalOffsetFromCentralCard
            } else if (i > centralCardIndex) {
                card.x += horizontalOffsetFromCentralCard
            } else {
                val centralCardVerticalOffset = currentCardWidth * 0.5f
                card.y -= centralCardVerticalOffset
            }

            card.pivotX = currentCardWidth / 2f
            card.pivotY = currentCardHeight.toFloat()

            card.rotation = angle

            gameAreaLayout.addView(card)
        }
    }
}