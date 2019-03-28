package com.metalgames.icicles.gameObjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
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
    private var shipShootingPoints: List<Vector2>

    private var shootingTimeOut = SHOOT_TIMEOUT

    private var shotSpeed=10f
    private  var shotTexture: Texture = Texture(Gdx.files.internal("hero_shot.png"))

    //private var vertices: FloatArray

    init {
        shipPosition = Vector2(positionX, SHIP_HEIGHT)
        poly.vertices = getClockwiseVertices()
        shipShootingPoints = initializeShipShootingPoints()
        shipShootingPoints=getShootingPoints()
    }

    private fun initializeShipShootingPoints(): List<Vector2> {
        val shootingPointsList = arrayListOf<Vector2>()
        val centralFront = Vector2(shipPosition.x, shipPosition.y + SHIP_HEIGHT / 2f)
        val frontLeft = Vector2(shipPosition.x - SHIP_BODY_WIDTH / 2f, shipPosition.y + SHIP_HEIGHT / 2)
        val frontRight = Vector2(shipPosition.x + SHIP_BODY_WIDTH / 2f, shipPosition.y + SHIP_HEIGHT / 2)

        shootingPointsList.add(centralFront)
        shootingPointsList.add(frontLeft)
        shootingPointsList.add(frontRight)

        return shootingPointsList
    }


    fun update(delta: Float, playerTouch: Vector2?): Collection<HeroShot> {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            shipPosition.x -= delta * MOVEMENT_FACTOR * MOVEMENT_SPEED
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            shipPosition.x += delta * MOVEMENT_FACTOR * MOVEMENT_SPEED
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            shipPosition.y += delta * MOVEMENT_FACTOR * MOVEMENT_SPEED
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            shipPosition.y -= delta * MOVEMENT_FACTOR * MOVEMENT_SPEED
        }

        if (shipPosition.x < SHIP_WIDTH / 2f) {
            shipPosition.x = SHIP_WIDTH / 2f
        }
        if (shipPosition.x > Constants.WORLD_WIDTH - SHIP_WIDTH / 2f) {
            shipPosition.x = Constants.WORLD_WIDTH - SHIP_WIDTH / 2f
        }

        if (shipPosition.y < SHIP_HEIGHT / 2f) {
            shipPosition.y = SHIP_HEIGHT / 2f
        }

        if (playerTouch != null) {
            moveDirection = Vector2(playerTouch.x - shipPosition.x, playerTouch.y - shipPosition.y)
            Gdx.app.debug("pos", "position = ${moveDirection.x} y=${moveDirection.y}")
            if (Math.abs(moveDirection.x) < MIN_MOVEMENT_DISTANCE && Math.abs(moveDirection.y) < MIN_MOVEMENT_DISTANCE) {
                //moving is not pixel perfect because it causes a shimmering bug
            }
            else {
                moveDirection.scaleToShipSpeed(MOVEMENT_SPEED)
                shipPosition.mulAdd(moveDirection, delta * MOVEMENT_FACTOR)
            }
        }

        //updateShooting
        shootingTimeOut -= delta
        if (shootingTimeOut <= 0) {
            return produceShots()
        }
        return emptyList()
    }


    //TODO SHOTS SHOULD NOT BE CREATED BUT RATHER REUSED FOR PERFORMANCE GAINS
    //either everyship reuses its shots, or one list is for the entire level
    private fun produceShots(): List<HeroShot> {

        var shotVelocity=getShotVelocity(shotSpeed)
        var shot = HeroShot(updateShootingPoints(shipShootingPoints)[0],shotTexture,shotVelocity)


        return listOf(shot)
    }

    private fun getShotVelocity(shotSpeed: Float): Vector2 {
        //depending on ship position(rotation)
        //hero shot upwards, so y decreases, enemies will be reverse
       return (Vector2(0f,-shotSpeed))
    }

    fun dispose() {
        batch.dispose()
        ship.dispose()
    }

    fun render(shapeRenderer: ShapeRenderer, viewPort: ExtendViewport) {
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

        poly.vertices = updateClockwiseVertices(poly.vertices, shipPosition)

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

    /**
     * Scales down a vector to a given maximum size
     * Make unit tests for this function
     */
    private fun Vector2.scaleToShipSpeed(maxLength: Float) {
        val scale: Float
        val dist = Math.sqrt((this.x * this.x + this.y * this.y).toDouble())
        scale = maxLength / dist.toFloat()
        this.x = scale * this.x
        this.y = scale * this.y
    }

    /**
     * This method creates an object so it should not be invoked for every frame,
     * instead make a method that accepts floatArray and vector2
     */
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

    //this method should be for all ships, ally and enemy
    private fun getShootingPoints(): List<Vector2> {
        return arrayListOf(
            Vector2(shipPosition.x, shipPosition.y + SHIP_HEIGHT / 2f), //the middle in front
            Vector2(shipPosition.x - SHIP_BODY_WIDTH / 2f, shipPosition.y + SHIP_HEIGHT / 2f),
            Vector2(shipPosition.x + SHIP_BODY_WIDTH / 2f, shipPosition.y + SHIP_HEIGHT / 2f)
        )
    }

    //this method should be for all ships, ally and enemy
    private fun updateShootingPoints(currentPoints: List<Vector2>): List<Vector2> {
        //the middle in front
        currentPoints[0].x = shipPosition.x
        currentPoints[0].y = shipPosition.y + SHIP_HEIGHT / 2f

        //front left
        currentPoints[1].x = shipPosition.x - SHIP_BODY_WIDTH / 2f
        currentPoints[1].y = shipPosition.y + SHIP_HEIGHT / 2f

        //front right
        currentPoints[2].x = shipPosition.x + SHIP_BODY_WIDTH / 2f
        currentPoints[2].y = shipPosition.y + SHIP_HEIGHT / 2f

        return currentPoints
    }

    private fun updateClockwiseVertices(vertices: FloatArray, position: Vector2): FloatArray {
        vertices[0] = position.x - SHIP_WIDTH / 2f
        vertices[1] = shipPosition.y - SHIP_HEIGHT / 2f
        vertices[2] = shipPosition.x - SHIP_WIDTH / 2f
        vertices[3] = shipPosition.y
        vertices[4] = shipPosition.x - SHIP_BODY_WIDTH / 2f
        vertices[5] = shipPosition.y + SHIP_HEIGHT / 2f - SHIP_WING_START_POSITION
        vertices[6] = shipPosition.x - SHIP_BODY_WIDTH / 2f
        vertices[7] = shipPosition.y + SHIP_HEIGHT / 2f
        vertices[8] = shipPosition.x + SHIP_BODY_WIDTH / 2f
        vertices[9] = shipPosition.y + SHIP_HEIGHT / 2f
        vertices[10] = shipPosition.x + SHIP_BODY_WIDTH / 2f
        vertices[11] = shipPosition.y + SHIP_HEIGHT / 2f - SHIP_WING_START_POSITION
        vertices[12] = shipPosition.x + SHIP_WIDTH / 2f
        vertices[13] = shipPosition.y
        vertices[14] = shipPosition.x + SHIP_WIDTH / 2f
        vertices[15] = shipPosition.y - SHIP_HEIGHT / 2f
        vertices[16] = shipPosition.x - SHIP_WIDTH / 2f
        vertices[17] = shipPosition.y - SHIP_HEIGHT / 2f

        return vertices
    }


    companion object {
        val TAG = Player::class.java.simpleName
        val COLOR: Color = Color.CYAN

        const val SHIP_WIDTH = 106f
        const val SHIP_HEIGHT = 86f
        const val SHIP_BODY_WIDTH = 30f
        const val SHIP_WING_START_POSITION = 20f

        //movement
        const val MOVEMENT_FACTOR = 5f
        const val MOVEMENT_SPEED = 20f


        const val ACCELERATION = 0.5f
        const val GRAVITY = 9.8f

        const val MIN_MOVEMENT_DISTANCE = 5f
        const val SHOOT_TIMEOUT = 1f

    }


}