package ru.alexkorrnd.cloneapp.client.readingqrcode

import android.graphics.*
import android.util.Log
import android.util.Size
import android.widget.ImageView

class RectangleAreaHighliteHelper(
    private val imageViewOverlay: ImageView
) {

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = Color.GREEN
        strokeWidth = STROKE_WIDTH
    }

    fun highlightRectangle(sourceSize: Size, rect: Rect) {
        Log.d("MainActivity", "highlightRectangle:: sourceSize = $sourceSize, rect = $rect")
        val widthAspect = imageViewOverlay.width / sourceSize.width.toFloat()
        val heightAspect = imageViewOverlay.height / sourceSize.height.toFloat()
        val overlay = Bitmap.createBitmap(imageViewOverlay.width, imageViewOverlay.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(overlay)
        canvas.drawRect(
            rect.left * widthAspect - PADDING,
            rect.top * heightAspect - PADDING,
            rect.right * widthAspect + PADDING,
            rect.bottom * heightAspect + PADDING,
            paint
        )
        imageViewOverlay.setImageBitmap(overlay)
    }

    fun clearRectangle() {
        imageViewOverlay.setImageBitmap(null)
    }

    companion object {

        private const val STROKE_WIDTH = 8F
        private const val PADDING = 10F
    }
}

