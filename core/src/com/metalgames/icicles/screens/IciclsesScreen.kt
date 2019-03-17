package com.metalgames.icicles.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.Screen
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.metalgames.icicles.gameConstants.Constants
import com.metalgames.icicles.gameMaanager.Difficulty
import com.metalgames.icicles.gameObjects.Icicle
import com.metalgames.icicles.gameObjects.Icicles
import com.metalgames.icicles.gameObjects.Player
import kotlin.math.max

class IciclsesScreen(private val difficulty: Difficulty, private val game: IciclesGame) : InputAdapter(), Screen {

    private val shapeRenderer = ShapeRenderer()
    private val viewPort = ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE)
    //private var icicle = Icicle()
    private lateinit var icicles: Icicles
    private lateinit var player: Player

    //HUD
    val hudViewPort = ScreenViewport()
    val spriteBatch = SpriteBatch()
    val bitmapFont = BitmapFont()

    //GAME objects
    var highScore: Int = 0

    override fun show() {
        Gdx.input.inputProcessor = this
        shapeRenderer.setAutoShapeType(true)
        icicles = Icicles(difficulty)
        player = Player(Constants.WORLD_SIZE / 2)
        bitmapFont.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
    }


    override fun resize(width: Int, height: Int) {
        viewPort.update(width, height, true)

        hudViewPort.update(width, height, true)
        bitmapFont.data.setScale(Math.min(width, height).toFloat() / Constants.HUD_SIZE)
    }

    override fun dispose() {

    }

    override fun hide() {
        shapeRenderer.dispose()
        spriteBatch.dispose()
        bitmapFont.dispose()
    }

    override fun pause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resume() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun render(delta: Float) {
        viewPort.apply()

        Gdx.gl.glClearColor(BACKGROUND_COLOR.r, BACKGROUND_COLOR.g, BACKGROUND_COLOR.b, 1f)
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)

        shapeRenderer.projectionMatrix = viewPort.camera.combined
        //icicle.update(delta)
        icicles.update(delta)
        player.update(delta)

        if (player.isHitByIcicle(icicles.iciclesList)) {
            icicles.reset()
        }

        shapeRenderer.begin()
        //  icicle.render(shapeRenderer)
        icicles.render(shapeRenderer)
        player.render(shapeRenderer)
        shapeRenderer.end()

        //update hud
        highScore = max(icicles.dodgedIcicles, highScore)

        hudViewPort.apply()
        spriteBatch.projectionMatrix = hudViewPort.camera.combined
        spriteBatch.begin()
        bitmapFont.draw(
            spriteBatch,
            "Deaths ${player.playerdDeaths}",
            Constants.HUD_MARGIN,
            hudViewPort.worldHeight - Constants.HUD_MARGIN
        )

        bitmapFont.draw(
            spriteBatch,
            "Score ${icicles.dodgedIcicles} \nTop score $highScore",
            hudViewPort.worldWidth - Constants.HUD_MARGIN,
            hudViewPort.worldHeight - Constants.HUD_MARGIN,
            0f,
            Align.right,
            false
        )

        spriteBatch.end()


    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {

        game.showDifficultyScreen()
        return true
    }


    companion object {
        val TAG = IciclsesScreen::class.java.simpleName
        val BACKGROUND_COLOR: Color = Color.LIGHT_GRAY
    }
}