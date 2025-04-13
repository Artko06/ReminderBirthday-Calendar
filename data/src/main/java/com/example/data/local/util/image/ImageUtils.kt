package com.example.data.local.util.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import java.io.ByteArrayOutputStream


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

// Given a byte array containing an image, return the corresponding bitmap
fun ByteArray.toBitmap(): Bitmap {
    // If the string is empty, just return an empty white bitmap
    if (this.isEmpty()) return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}

// Transform a square bitmap in a circular bitmap, useful for notification
fun getCircularBitmap(bitmap: Bitmap): Bitmap {
    val output = Bitmap.createBitmap(
        bitmap.width,
        bitmap.height,
        Bitmap.Config.ARGB_8888,
    )
    val canvas = Canvas(output)
    val color: Int = Color.GRAY
    val paint = Paint()
    val rect = Rect(0, 0, bitmap.width, bitmap.height)
    val rectF = RectF(rect)
    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = color
    canvas.drawOval(rectF, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(bitmap, rect, rect, paint)
    return output
}

// Loop an animated vector drawable indefinitely
fun ImageView.applyLoopingAnimatedVectorDrawable(
    @DrawableRes animatedVector: Int,
    endDelay: Long = 0,
    disableLooping: Boolean = false
) {
    val animated = AnimatedVectorDrawableCompat.create(context, animatedVector)
    // Ability to disable the loop, for a future option
    if (!disableLooping) {
        animated?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                Handler(Looper.getMainLooper()).postDelayed({
                    this@applyLoopingAnimatedVectorDrawable.post { animated.start() }
                }, endDelay)
            }
        })
    }
    this.setImageDrawable(animated)
    animated?.start()
}

// Extension function to convert bitmap to byte array
fun Bitmap.toByteArray(): ByteArray {
    ByteArrayOutputStream().apply {
        compress(Bitmap.CompressFormat.JPEG, 100, this)
        return toByteArray()
    }
}