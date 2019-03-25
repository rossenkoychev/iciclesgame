package com.metalgames.icicles.gameObjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.ExtendViewport

class HeroShot(var position: Vector2) {


    var shot: Texture = Texture(Gdx.files.internal("HeroShot.png"))
    var batch: SpriteBatch = SpriteBatch()
    var poly = Polygon()

    init {
        poly.vertices = getClockwiseVertices()

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
        batch.draw(shot, position.x - SHOT_WIDTH / 2, position.y - SHOT_HEIGHT / 2, SHOT_WIDTH, SHOT_HEIGHT)
        batch.end()

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.polygon(poly.vertices)
        shapeRenderer.end()
    }

    private fun getVertices(): FloatArray {
        return floatArrayOf(
            position.x - SHOT_WIDTH / 2f, position.y - SHOT_HEIGHT / 2f,
            position.x + SHOT_WIDTH / 2f, position.y - SHOT_HEIGHT / 2f,
            position.x + SHOT_WIDTH / 2f, position.y + SHOT_HEIGHT / 2f,
            position.x - SHOT_WIDTH / 2f, position.y + SHOT_HEIGHT / 2f

        )
    }

    private fun getClockwiseVertices(): FloatArray {
        return floatArrayOf(
            position.x - SHOT_WIDTH / 2f, position.y + SHOT_HEIGHT / 2f,
            position.x + SHOT_WIDTH / 2f, position.y + SHOT_HEIGHT / 2f,
            position.x + SHOT_WIDTH / 2f, position.y - SHOT_HEIGHT / 2f,
            position.x - SHOT_WIDTH / 2f, position.y - SHOT_HEIGHT / 2f

        )
    }

    private fun updateVerticlesPosition(currentPoisition: FloatArray): FloatArray {
        currentPoisition[0] = position.x - SHOT_WIDTH / 2f
        currentPoisition[1] = position.y + SHOT_HEIGHT / 2f
        currentPoisition[2] = position.x + SHOT_WIDTH / 2f
        currentPoisition[3] = position.y + SHOT_HEIGHT / 2f
        currentPoisition[4] = position.x + SHOT_WIDTH / 2f
        currentPoisition[5] = position.y - SHOT_HEIGHT / 2f
        currentPoisition[6] = position.x - SHOT_WIDTH / 2f
        currentPoisition[7] = position.y - SHOT_HEIGHT / 2f


    }


    companion object {
        const val SHOT_WIDTH = 8f
        const val SHOT_HEIGHT = 15f

        val TAG = HeroShot::class.java.simpleName
    }
}
