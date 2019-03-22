package com.metalgames.icicles.gameObjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.metalgames.icicles.gameConstants.Constants


class Player(positionX: Float) {
    var ship: Texture = Texture(Gdx.files.internal("Ship_LVL_1.png"))
    var batch: SpriteBatch = SpriteBatch()

    var playerDeaths = 0
    var poly = Polygon()
    lateinit var moveDirection: Vector2
    private var shipPosition: Vector2

    init {
        shipPosition = Vector2(positionX, SHIP_HEIGHT)
        poly.vertices = getClockwiseVertices()
    }


    fun update(delta: Float, playerTouch: Vector2?) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            shipPosition.x -= delta * MOVEMENT_SPEED
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            shipPosition.x += delta * MOVEMENT_SPEED
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            shipPosition.y += delta * MOVEMENT_SPEED
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            shipPosition.y -= delta * MOVEMENT_SPEED
        }

        if (shipPosition.x < SHIP_WIDTH / 2f) {
            shipPosition.x = SHIP_WIDTH / 2f
        }
        if (shipPosition.x > Constants.WORLD_WIDTH - SHIP_WIDTH / 2f) {
            shipPosition.x = Constants.WORLD_WIDTH - SHIP_WIDTH / 2f
        }

        if (shipPosition.y <  SHIP_HEIGHT / 2f) {
            shipPosition.y =  SHIP_HEIGHT / 2f
        }

        // val accelerometerInput = -Gdx.input.accelerometerY / (ACCELERATION * GRAVITY)
        if (playerTouch != null) {
            moveDirection = playerTouch.sub(shipPosition)

            shipPosition.x += delta * MOVEMENT_SPEED * (if(moveDirection.x<0)  -1 else 1)
            shipPosition.y += delta * MOVEMENT_SPEED *(if(moveDirection.y<0)  -1 else 1)
        }

        Gdx.app.debug("pos", "x = ${shipPosition.x}  y=${ shipPosition.y} ")

    }

    fun dispose() {
        batch.dispose()
        ship.dispose()
    }

    fun render(shapeRenderer: ShapeRenderer, viewPort: ExtendViewport) {

        //draw head
        batch.projectionMatrix = viewPort.camera.combined
        batch.begin()
        batch.draw(
                ship,
                shipPosition.x - SHIP_WIDTH / 2f,
                shipPosition.y - SHIP_HEIGHT / 2f,
                SHIP_WIDTH,
                SHIP_HEIGHT
        )

        batch.end()
        poly.vertices = getClockwiseVertices()

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.polygon(poly.vertices)
        shapeRenderer.end()
    }

    private fun getVertices(): FloatArray {
        return floatArrayOf(
                shipPosition.x - SHIP_WIDTH / 2f, shipPosition.y - SHIP_HEIGHT / 2f,
                shipPosition.x + SHIP_WIDTH / 2f, shipPosition.y - SHIP_HEIGHT / 2f,
                shipPosition.x + SHIP_WIDTH / 2f, shipPosition.y,
                shipPosition.x + SHIP_BODY_WIDTH / 2f, shipPosition.y + SHIP_HEIGHT / 2f - SHIP_WING_START_POSITION,
                shipPosition.x + SHIP_BODY_WIDTH / 2f, shipPosition.y + SHIP_HEIGHT / 2f,
                shipPosition.x - SHIP_BODY_WIDTH / 2f, shipPosition.y + SHIP_HEIGHT / 2f,
                shipPosition.x - SHIP_BODY_WIDTH / 2f, shipPosition.y + SHIP_HEIGHT / 2f - SHIP_WING_START_POSITION,
                shipPosition.x - SHIP_WIDTH / 2f, shipPosition.y,
                shipPosition.x - SHIP_WIDTH / 2f, shipPosition.y - SHIP_HEIGHT / 2f

        )
    }

    private fun getClockwiseVertices(): FloatArray {
        return floatArrayOf(
                shipPosition.x - SHIP_WIDTH / 2f, shipPosition.y - SHIP_HEIGHT / 2f,
                shipPosition.x - SHIP_WIDTH / 2f, shipPosition.y,
                shipPosition.x - SHIP_BODY_WIDTH / 2f, shipPosition.y + SHIP_HEIGHT / 2f - SHIP_WING_START_POSITION,
                shipPosition.x - SHIP_BODY_WIDTH / 2f, shipPosition.y + SHIP_HEIGHT / 2f,
                shipPosition.x + SHIP_BODY_WIDTH / 2f, shipPosition.y + SHIP_HEIGHT / 2f,
                shipPosition.x + SHIP_BODY_WIDTH / 2f, shipPosition.y + SHIP_HEIGHT / 2f - SHIP_WING_START_POSITION,
                shipPosition.x + SHIP_WIDTH / 2f, shipPosition.y,
                shipPosition.x + SHIP_WIDTH / 2f, shipPosition.y - SHIP_HEIGHT / 2f,
                shipPosition.x - SHIP_WIDTH / 2f, shipPosition.y - SHIP_HEIGHT / 2f

        )
    }

    companion object {
        val TAG = Player::class.java.simpleName
        val COLOR: Color = Color.CYAN

        const val SHIP_WIDTH = 106f
        const val SHIP_HEIGHT = 86f
        const val SHIP_BODY_WIDTH = 50f
        const val SHIP_WING_START_POSITION = 20f

        //movement
        const val MOVEMENT_SPEED = 250f

        const val ACCELERATION = 0.5f
        const val GRAVITY = 9.8f


    }


}