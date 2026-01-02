package com.artkotlindev.reminderbirthday_calendar.presentation.components.snowflake

import androidx.annotation.IntRange
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random
import kotlin.time.ExperimentalTime


class SnowflakeLayer(
    @IntRange(from = 1L, to = 10L) private val snowDensity: Int,
    private val color: Color
){

    class Particle{
        var x: Float = 0f
        var y: Float = 0f
        var vx: Float = 0f
        var vy: Float = 0f
        var velocity: Float = 0f
        var alpha: Float = 0f
        var lifeTime: Float = 0f
        var currentTime: Float = 0f
        var scale: Float = 0f
        var type: Int = 0

        fun draw(
            drawScope: DrawScope,
            particleBitmap: ImageBitmap,
            particleColor: Color
        ){
            with(drawScope) {
                if (type == 0){
                    drawPoints(
                        points = listOf(Offset(x, y)),
                        pointMode = PointMode.Points,
                        particleColor,
                        1.5f.dp.toPx(),
                        StrokeCap.Round,
                        null,
                        alpha,
                        null
                    )
                } else {
                    scale(
                        scale = scale,
                        Offset(x, y),
                        block = {
                            drawImage(
                                image = particleBitmap,
                                topLeft = Offset(x, y),
                                alpha = alpha
                            )
                        }
                    )
                }
            }
        }
    }

    private var particleBitmap: ImageBitmap? = null

    private var lastAnimationTime: Long = 0
    private val createPerFrame = when{
        snowDensity == 1 -> 1
        snowDensity <= 3 -> 10
        snowDensity <= 5 -> 20
        snowDensity <= 8 -> 30
        else -> 50
    }
    private val minLifetime = when {
        snowDensity == 1 -> 2000f
        snowDensity <= 3 -> 3000f
        snowDensity <= 5 -> 3200f
        snowDensity <= 8 -> 3500f
        else -> 4000f
    }
    private val lifetimeSpan = when {
        snowDensity == 1 -> 100
        snowDensity <= 3 -> 2000
        snowDensity <= 5 -> 2200
        snowDensity <= 8 -> 2500
        else -> 3000
    }

    private val particles = arrayListOf<Particle>()
    private val freeParticles = arrayListOf<Particle>()

    private val accelerateEasing: (Float) -> Float = { fraction ->
        fraction * fraction
    }

    private val decelerateEasing: (Float) -> Float = { fraction ->
        (1f - (1f - fraction) * (1f - fraction))
    }

    init {
        repeat(snowDensity * 10) {
            freeParticles.add(Particle())
        }
    }

    private fun updateParticles(dt: Long) {
        var count: Int = particles.size
        var a = 0
        while (a < count) {
            val particle = particles[a]
            if (particle.currentTime >= particle.lifeTime) {
                if (freeParticles.size < snowDensity * 20) {
                    freeParticles.add(particle)
                }
                particles.removeAt(a)
                a--
                count--
                a++
                continue
            }
            if (particle.currentTime < 200.0f) {
                particle.alpha = accelerateEasing.invoke(particle.currentTime / 200.0f)
            } else if (particle.lifeTime - particle.currentTime < lifetimeSpan) {
                particle.alpha = decelerateEasing.invoke((particle.lifeTime - particle.currentTime) / lifetimeSpan)
            }
            particle.x += particle.vx * particle.velocity * dt / 500.0f
            particle.y += particle.vy * particle.velocity * dt / 500.0f
            particle.currentTime += dt.toFloat()
            a++
        }
    }

    fun update(newTime: Long){
        val dt: Long = min(17, newTime - lastAnimationTime)
        updateParticles(dt)
        lastAnimationTime = newTime
    }

    @OptIn(ExperimentalTime::class)
    fun draw(scope: DrawScope){
        with(scope){
            if (particleBitmap == null){
                val particleThinPaint = Paint()
                particleThinPaint.isAntiAlias = true
                particleThinPaint.color = color
                particleThinPaint.strokeWidth = 0.5f.dp.toPx()
                particleThinPaint.strokeCap = StrokeCap.Round
                particleThinPaint.style = PaintingStyle.Stroke

                val angleDiff = (PI / 180 * 60).toFloat()
                particleBitmap = ImageBitmap(
                    16.dp.toPx().toInt(),
                    16.dp.toPx().toInt(),
                    ImageBitmapConfig.Argb8888
                )
                val bitmapCanvas = Canvas(particleBitmap!!)
                var angle = -PI / 2
                val px = 2.0f.dp.toPx() * 2
                val px1 = -0.57f.dp.toPx() * 2
                val py1: Float = 1.55f.dp.toPx() * 2
                repeat(6){
                    val x = 8.dp.toPx()
                    val y = 8.dp.toPx()
                    var x1 = cos(angle.toDouble()).toFloat() * px
                    var y1 = sin(angle.toDouble()).toFloat() * px
                    val cx = x1 * 0.66f
                    val cy = y1 * 0.66f
                    bitmapCanvas.drawLine(
                        Offset(x, y),
                        Offset(x + x1, y + y1),
                        particleThinPaint
                    )

                    val angle2 = angle - PI / 2
                    x1 = (cos(angle2.toDouble()) * px1 - sin(angle2.toDouble()) * py1).toFloat()
                    y1 = (sin(angle2.toDouble()) * px1 + cos(angle2.toDouble()) * py1).toFloat()
                    bitmapCanvas.drawLine(
                        Offset(x + cx, y + cy),
                        Offset(x + x1, y + y1),
                        particleThinPaint
                    )

                    x1 = (-cos(angle2.toDouble()) * px1 - sin(angle2.toDouble()) * py1).toFloat()
                    y1 = (-sin(angle2.toDouble()) * px1 + cos(angle2.toDouble()) * py1).toFloat()
                    bitmapCanvas.drawLine(
                        Offset(x + cx, y + cy),
                        Offset(x + x1, y + y1),
                        particleThinPaint
                    )

                    angle += angleDiff
                }
            }

            val count: Int = particles.size
            for (a in 0..<count) {
                val particle = particles[a]
                particle.draw(
                    scope,
                    particleBitmap!!,
                    color
                )
            }

            val maxCount = snowDensity * 100
            if (particles.size < maxCount) {
                (0..<createPerFrame).forEach { i ->
                    if (particles.size < maxCount && Random.nextFloat() > 0.7f) {
                        val cx: Float = Random.nextFloat() * size.width
                        val cy: Float = Random.nextFloat() * (size.height - 20.dp.toPx())

                        val angle: Int = Random.nextInt(40) - 20 + 90
                        val vx = cos(PI / 180.0 * angle).toFloat()
                        val vy = sin(PI / 180.0 * angle).toFloat()

                        val newParticle: Particle
                        if (!freeParticles.isEmpty()) {
                            newParticle = freeParticles[0]
                            freeParticles.removeAt(0)
                        } else {
                            newParticle = Particle()
                        }
                        newParticle.x = cx
                        newParticle.y = cy

                        newParticle.vx = vx
                        newParticle.vy = vy

                        newParticle.alpha = 0.0f
                        newParticle.currentTime = 0f

                        newParticle.scale = Random.nextFloat() * 1.2f
                        newParticle.type = Random.nextInt(2)

                        newParticle.lifeTime = minLifetime + Random.nextInt(lifetimeSpan)

                        newParticle.velocity = 20.0f + Random.nextFloat() * 4.0f
                        particles.add(newParticle)
                    }
                }
            }
        }
    }

}