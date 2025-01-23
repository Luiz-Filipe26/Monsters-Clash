package com.example.monstersclash

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.monstersclash.databinding.ActivityMainBinding

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
            createImages()
        }

    }

    private fun createImages() {
        // Definindo o ponto central da área onde as cartas serão dispostas
        val centerX = binding.cardsContainerImg.x + binding.cardsContainerImg.width / 2
        val centerY = binding.cardsContainerImg.y + binding.cardsContainerImg.height

        val gameAreaLayout = binding.main

        // Configurações gerais
        val cardCount = 7   // Quantidade de cartas
        val totalAngle = 45f // Abertura total (graus)
        val angleStep = totalAngle / (cardCount - 1) // Ângulo entre as cartas
        val startAngle = -totalAngle / 2 // Ângulo inicial (para centralizar o leque)

        // Configurações de pivô para todas as cartas
        val pivotX = 50f // Pivô horizontal (meio da carta)
        val pivotY = 150f // Pivô vertical (base da carta)

        // LayoutParams único para todas as cartas
        val layoutParams = FrameLayout.LayoutParams(100, 150)

        // Criação das cartas
        for (i in 0 until cardCount) {
            val angle = startAngle + i * angleStep // Calcula o ângulo de cada carta

            val card = ImageView(this)
            card.setImageResource(R.mipmap.card_heart_a)

            // Define a posição das cartas, centralizando todas no ponto (centerX, centerY)
            card.layoutParams = layoutParams
            card.x = centerX - pivotX  // Posição central horizontal
            card.y = centerY - pivotY  // Posição central vertical

            // Aplica as transformações de pivô e rotação
            card.pivotX = pivotX
            card.pivotY = pivotY
            card.rotation = angle

            // Adiciona a carta no layout
            gameAreaLayout.addView(card)
        }
    }


}