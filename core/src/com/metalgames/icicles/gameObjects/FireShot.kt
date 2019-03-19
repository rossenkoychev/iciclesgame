package com.metalgames.icicles.gameObjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

class FireShot {


    internal var position = Vector2(100f, 300f)
    //private val velocity: Vector2 = Vector2(0f, 0f)
    var shot: Texture = Texture(Gdx.files.internal("Fire_Shot.png"))
    var batch: SpriteBatch = SpriteBatch()

    init {

        // position =

    }

    fun update(delta: Float) {
        //   velocity.mulAdd(ICICLE_ACCELERATION, delta)

        // position.mulAdd(velocity,delta)

        Gdx.app.debug("pos", "position = ${position.x} y=${position.y}")
    }

    fun render(shapeRenderer: ShapeRenderer) {
        // shapeRenderer.color = ICICLE_COLOR
        // shapeRenderer.set(ShapeRenderer.ShapeType.Filled)

        batch.begin()
        batch.draw(shot, position.x, position.y, SHOT_WIDTH, SHOT_HEIGHT)
        batch.end()

//        shapeRenderer.triangle(
//            position.x - ICICLE_WIDTH / 2,
//            position.y + ICICLE_HEIGHT / 2,
//            position.x + ICICLE_WIDTH / 2,
//            position.y + ICICLE_HEIGHT / 2,
//            position.x,
//            position.y - ICICLE_HEIGHT / 2
//        )
    }

    companion object {
        const val ICICLE_HEIGHT = 125
        const val ICICLE_WIDTH = 40
        val ICICLE_ACCELERATION = Vector2(0f, -100f)
        const val SHOT_WIDTH = 20f
        const val SHOT_HEIGHT = 30f
        val ICICLE_COLOR: Color = Color.BLUE
        val TAG = FireShot::class.java.simpleName
    }
}