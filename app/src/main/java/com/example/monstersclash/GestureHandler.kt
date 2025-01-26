package com.example.monstersclash

import android.view.MotionEvent
import android.view.View
import kotlin.math.abs
import kotlin.math.atan2

class GestureHandler {

    private var originalSwipeX = 0f
    private var originalSwipeY = 0f

    companion object {
        private val touchListeners = mutableListOf<(MotionEvent) -> Unit>()

        fun addListener(listener: (MotionEvent) -> Unit) {
            touchListeners.add(listener)
        }

        fun removeTouchListener(listener: (MotionEvent) -> Unit) {
            touchListeners.remove(listener)
        }
    }

    fun detectSwipe(
        view: View,
        minDeltaDirection: Float,
        testIsValidArea: (x: Float, y: Float) -> Boolean,
        finishedSwipeHandler: (direction: Direction) -> Unit
    ) {
        addListener { event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    originalSwipeX = event.rawX
                    originalSwipeY = event.rawY
                }

                MotionEvent.ACTION_MOVE -> {
                    // Lógica de movimento, se necessário
                }

                MotionEvent.ACTION_UP -> {
                    if (testIsValidArea(originalSwipeX, originalSwipeY)) {
                        val deltaX = event.rawX - originalSwipeX
                        val deltaY = event.rawY - originalSwipeY

                        // Calcular o ângulo do swipe e convertê-lo para graus
                        val angle = Math.toDegrees(atan2(deltaY, deltaX).toDouble())

                        // Ajustar o ângulo para o intervalo [0, 360), garantindo que 0º seja à direita.
                        val normalizedAngle = (angle + 360) % 360

                        // Verificar as direções com base no ângulo e delta significativo.
                        when {
                            // Direção para a direita (0º, ou próximo de 360º).
                            (normalizedAngle in 345.0..360.0) || (normalizedAngle in 0.0..15.0) -> {
                                if (abs(deltaX) > minDeltaDirection) finishedSwipeHandler(Direction.RIGHT)
                            }

                            // Direção para a esquerda (180º).
                            normalizedAngle in 165.0..195.0 -> {
                                if (abs(deltaX) > minDeltaDirection) finishedSwipeHandler(Direction.LEFT)
                            }

                            // Direção para cima (90º).
                            normalizedAngle in 75.0..105.0 -> {
                                if (abs(deltaY) > minDeltaDirection) finishedSwipeHandler(Direction.UP)
                            }

                            // Direção para baixo (270º).
                            normalizedAngle in 255.0..285.0 -> {
                                if (abs(deltaY) > minDeltaDirection) finishedSwipeHandler(Direction.DOWN)
                            }
                        }
                    }
                }
            }
        }

        @Suppress("ClickableViewAccessibility")
        view.setOnTouchListener { _, event ->
            touchListeners.forEach { it(event) }
            true
        }
    }
}