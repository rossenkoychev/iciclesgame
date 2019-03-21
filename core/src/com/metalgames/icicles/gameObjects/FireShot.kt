package com.metalgames.icicles.gameObjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.ExtendViewport

class FireShot {


    internal var position = Vector2(300f, 300f)
    //private val velocity: Vector2 = Vector2(0f, 0f)
    var shot: Texture = Texture(Gdx.files.internal("Fire_Shot.png"))
    var batch: SpriteBatch = SpriteBatch()
    var poly = Polygon()

    init {
        poly.vertices = getClockwiseVertices()
        // position =
    }

    fun update(delta: Float) {
        //   velocity.mulAdd(ICICLE_ACCELERATION, delta)

        // position.mulAdd(velocity,delta)

        Gdx.app.debug("pos", "position = ${position.x} y=${position.y}")
    }

    fun dispose() {
        batch.dispose()
        shot.dispose()
    }

    fun render(shapeRenderer: ShapeRenderer, viewPort: ExtendViewport) {

        //draw head
        batch.projectionMatrix = viewPort.camera.combined

        batch.begin()
        batch.draw(shot, position.x-SHOT_WIDTH/2, position.y-SHOT_HEIGHT/2, SHOT_WIDTH, SHOT_HEIGHT)
        batch.end()

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.polygon(poly.vertices)
        shapeRenderer.end()
    }

    private fun getVertices(): FloatArray {
        return floatArrayOf(
            position.x - SHOT_WIDTH / 2f, position.y - SHOT_HEIGHT / 2f,
            position.x + SHOT_WIDTH / 2f, position.y - SHOT_HEIGHT / 2f,
            position.x + SHOT_WIDTH / 2f, position.y +SHOT_HEIGHT / 2f,
            position.x - SHOT_WIDTH / 2f, position.y +SHOT_HEIGHT / 2f

        )
    }

    private fun getClockwiseVertices(): FloatArray {
        return floatArrayOf(
                position.x - SHOT_WIDTH / 2f, position.y +SHOT_HEIGHT / 2f,
                position.x + SHOT_WIDTH / 2f, position.y +SHOT_HEIGHT / 2f,
                position.x + SHOT_WIDTH / 2f, position.y - SHOT_HEIGHT / 2f,
                position.x - SHOT_WIDTH / 2f, position.y - SHOT_HEIGHT / 2f

        )
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