package com.metalgames.icicles.gameObjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.metalgames.icicles.gameConstants.Constants

class Player(positionX: Float) {

    private var headPosition: Vector2
    var playerdDeaths = 0

    init {
        headPosition = Vector2(positionX, BODY_HEIGHT + LEGS_HEIGHT + HEAD_RADIUS / 2)
    }

    fun update(delta: Float) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            headPosition.x -= delta * MOVEMENT_SPEED
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            headPosition.x += delta * MOVEMENT_SPEED
        }


        if (headPosition.x < HEAD_RADIUS) {
            headPosition.x = HEAD_RADIUS
        }
        if (headPosition.x > Constants.WORLD_SIZE - HEAD_RADIUS) {
            headPosition.x = Constants.WORLD_SIZE - HEAD_RADIUS
        }

        val accelerometerInput = -Gdx.input.accelerometerY / (ACCELERATION * GRAVITY)
        headPosition.x += -accelerometerInput * delta * MOVEMENT_SPEED
    }

    fun render(shapeRenderer: ShapeRenderer) {

        //draw head
        // shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = COLOR
        shapeRenderer.circle(headPosition.x, BODY_HEIGHT + LEGS_HEIGHT + HEAD_RADIUS / 2, HEAD_RADIUS)
        shapeRenderer.end()

        //draw body
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        //  shapeRenderer.setColor(Color.YELLOW)
        shapeRenderer.line(headPosition.x, LEGS_HEIGHT, headPosition.x, LEGS_HEIGHT + BODY_HEIGHT)


        //draw hands
        shapeRenderer.line(
            headPosition.x,
            SHOULDER_HEIGHT,
            headPosition.x + HANDS_HORIZONTAL_REACH,
            SHOULDER_HEIGHT + HANDS_VERTICAL_REACH
        )
        shapeRenderer.line(
            headPosition.x,
            SHOULDER_HEIGHT,
            headPosition.x - HANDS_HORIZONTAL_REACH,
            SHOULDER_HEIGHT + HANDS_VERTICAL_REACH
        )

        //draw legs
        shapeRenderer.line(headPosition.x, LEGS_HEIGHT, headPosition.x + LEG_HORIZONTAL_REACH, 0f)
        shapeRenderer.line(headPosition.x, LEGS_HEIGHT, headPosition.x - LEG_HORIZONTAL_REACH, 0f)
    }

    fun isHitByIcicle(icicles: List<Icicle>): Boolean {
        for (icicle: Icicle in icicles) {
            if (icicle.impactPosition.dst(headPosition) < HEAD_RADIUS) {
                playerdDeaths++
                return true
            }
        }
        return false
    }

    companion object {
        val TAG = Player::class.java.simpleName
        val COLOR: Color = Color.CYAN
        const val HEAD_RADIUS = 60f
        const val BODY_HEIGHT = 200f
        const val LEGS_HEIGHT = 70f
        const val SHOULDER_HEIGHT = 180f
        const val HANDS_HORIZONTAL_REACH = 40f
        const val HANDS_VERTICAL_REACH = 20f
        const val LEG_HORIZONTAL_REACH = 30f

        //movement
        const val MOVEMENT_SPEED = 250f

        const val ACCELERATION = 0.5f
        const val GRAVITY = 9.8f


    }


}