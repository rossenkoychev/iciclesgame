package com.metalgames.icicles.gameObjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.metalgames.icicles.gameConstants.Constants

class Icicle {

    var impactPosition:Vector2
    internal var position: Vector2
    private val velocity: Vector2 = Vector2(0f, 0f)

    init {
        val randomXPosition = (0 + ICICLE_WIDTH / 2..Constants.WORLD_HEIGHT.toInt() - ICICLE_WIDTH / 2).random().toFloat()
        position = Vector2(randomXPosition, Constants.WORLD_HEIGHT + ICICLE_HEIGHT)
        impactPosition=position.add(0f,-ICICLE_HEIGHT/2f)
    }

    fun update(delta: Float) {
        velocity.mulAdd(ICICLE_ACCELERATION, delta)

        position.mulAdd(velocity,delta)

        Gdx.app.debug("pos", "position = ${position.x} y=${position.y}")
    }

    fun render(shapeRenderer: ShapeRenderer) {
        shapeRenderer.color = ICICLE_COLOR
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled)



        shapeRenderer.triangle(
            position.x - ICICLE_WIDTH / 2,
            position.y + ICICLE_HEIGHT / 2,
            position.x + ICICLE_WIDTH / 2,
            position.y + ICICLE_HEIGHT / 2,
            position.x,
            position.y - ICICLE_HEIGHT / 2
        )
    }

    companion object {
        const val ICICLE_HEIGHT = 125
        const val ICICLE_WIDTH = 40
        val ICICLE_ACCELERATION = Vector2(0f, -100f)

        val ICICLE_COLOR: Color = Color.BLUE
        val TAG = Icicle::class.java.simpleName
    }
}