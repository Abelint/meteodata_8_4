package com.lekciya.meteodata_8_4

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomGraphView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paintLine = Paint().apply {
        color = Color.BLUE
        strokeWidth = 5f
        isAntiAlias = true
    }

    private val paintPoint = Paint().apply {
        color = Color.RED
        strokeWidth = 10f
        isAntiAlias = true
    }

    private val paintText = Paint().apply {
        color = Color.BLACK
        textSize = 40f
        isAntiAlias = true
    }

    private var dataPoints: List<Double> = emptyList()

    // Метод для обновления данных
    fun setData(points: List<Double>) {
        dataPoints = points
        invalidate()  // Перерисовываем график
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (dataPoints.isEmpty()) return  // Если нет данных, ничего не рисуем

        val width = width.toFloat()
        val height = height.toFloat()

        // Вычисляем минимальное и максимальное значение для масштабирования графика
        val minValue = dataPoints.minOrNull() ?: 0.0
        val maxValue = dataPoints.maxOrNull() ?: 0.0
        val range = maxValue - minValue

        // Отступы для осей
        val padding = 100f

        // Шаг между точками по оси X
        val stepX = (width - padding * 2) / (dataPoints.size - 1)

        // Проходим по всем точкам и рисуем линии
        for (i in 1 until dataPoints.size) {
            val startX = padding + (i - 1) * stepX
            val startY = height - padding - ((dataPoints[i - 1] - minValue) / range * (height - padding * 2)).toFloat()

            val stopX = padding + i * stepX
            val stopY = height - padding - ((dataPoints[i] - minValue) / range * (height - padding * 2)).toFloat()

            // Рисуем линию между точками
            canvas?.drawLine(startX, startY, stopX, stopY, paintLine)

            // Рисуем точку на каждой позиции
            canvas?.drawCircle(startX, startY, 5f, paintPoint)
        }

        // Рисуем последнюю точку
        val lastX = padding + (dataPoints.size - 1) * stepX
        val lastY = height - padding - ((dataPoints.last() - minValue) / range * (height - padding * 2)).toFloat()
        canvas?.drawCircle(lastX, lastY, 5f, paintPoint)

        // Отрисовка осей
        canvas?.drawLine(padding, padding, padding, height - padding, paintText)  // ось Y
        canvas?.drawLine(padding, height - padding, width - padding, height - padding, paintText)  // ось X

        // Подписи осей
        canvas?.drawText(minValue.toString(), 0f, height - padding, paintText)
        canvas?.drawText(maxValue.toString(), 0f, padding, paintText)
    }
}