package com.example.data.local.util.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import androidx.core.graphics.scale


// Given a bitmap, convert it to a byte array
fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
    return try {
        bitmap.toByteArray()
    } catch (_: Exception) {
        byteArrayOf()
    }
}

// Get the smallest dimension in a non-square image to crop and resize it
fun getBitmapSquareSize(bitmap: Bitmap): Int {
    return bitmap.width.coerceAtMost(bitmap.height)
}

fun Uri.toByteArray(context: Context): ByteArray? {
    return try {
        context.contentResolver.openInputStream(this)?.use { inputStream ->
            inputStream.readBytes()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// Extension function to convert bitmap to byte array
fun Bitmap.toByteArray(): ByteArray {
    ByteArrayOutputStream().apply {
        compress(Bitmap.CompressFormat.JPEG, 100, this)
        return toByteArray()
    }
}

fun Bitmap.cropCenterAndScale(targetWidth: Int, targetHeight: Int): Bitmap {
    val srcRatio = width.toFloat() / height
    val targetRatio = targetWidth.toFloat() / targetHeight

    val cropWidth: Int
    val cropHeight: Int
    val xOffset: Int
    val yOffset: Int

    if (srcRatio > targetRatio) {
        // Crop Width
        cropHeight = height
        cropWidth = (height * targetRatio).toInt()
        xOffset = (width - cropWidth) / 2
        yOffset = 0
    } else {
        // Crop Height
        cropWidth = width
        cropHeight = (width / targetRatio).toInt()
        xOffset = 0
        yOffset = (height - cropHeight) / 2
    }

    val cropped = Bitmap.createBitmap(this, xOffset, yOffset, cropWidth, cropHeight)
    return cropped.scale(targetWidth, targetHeight)
}


fun ByteArray.compressImageWithResize(
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
): ByteArray? {
    val maxSizeKB = 300
    val width = 450
    val height = 450
    var quality = 100

    try {
        val bitmap = BitmapFactory.decodeByteArray(this, 0, this.size)
            ?: return null

        val resized = bitmap.cropCenterAndScale(width, height)

        var outputStream: ByteArrayOutputStream
        do {
            outputStream = ByteArrayOutputStream()
            resized.compress(format, quality, outputStream)
            println("Size photo: ${outputStream.size() / 1024}KB")
            quality -= 10
        } while (outputStream.size() > maxSizeKB * 1024 && quality > 10)

        if (outputStream.size() > maxSizeKB * 1024) throw Exception("Big size for load")
        return outputStream.toByteArray()
    } catch (e: Exception){
        e.printStackTrace()
        return null
    }
}
