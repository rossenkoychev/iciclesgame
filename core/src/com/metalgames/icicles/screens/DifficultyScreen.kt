package com.metalgames.icicles.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.Screen
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import com.metalgames.icicles.gameMaanager.Difficulty

class DifficultyScreen(val game: IciclesGame) : InputAdapter(), Screen {


    private var shapeRenderer = ShapeRenderer()
    private var viewPort = FitViewport(DIFFICULTY_SCREEN_SIZE, SCREEN_HEIGHT)
    private val spriteBatch = SpriteBatch()
    private val bitmapFont = BitmapFont()

    override fun show() {
        Gdx.input.inputProcessor = this
        bitmapFont.data.setScale(DIFFICULTY_LABELS_SIZE_MULTIPLIER)
        bitmapFont.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
    }

    override fun render(delta: Float) {
        viewPort.apply()

        Gdx.gl.glClearColor(
            IciclsesScreen.BACKGROUND_COLOR.r,
            IciclsesScreen.BACKGROUND_COLOR.g,
            IciclsesScreen.BACKGROUND_COLOR.b,
            1f
        )
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)

        shapeRenderer.projectionMatrix = viewPort.camera.combined

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Difficulty.EASY.color
        shapeRenderer.circle(
            EASY_CENTER.x,
            EASY_CENTER.y,
            DIFFICULTY_OPTION_RADIUS,
            40
        )

        shapeRenderer.color = Difficulty.NORMAL.color
        shapeRenderer.circle(
            NORMAL_CENTER.x,
            NORMAL_CENTER.y,
            DIFFICULTY_OPTION_RADIUS,
            40
        )

        shapeRenderer.color = Difficulty.HARD.color
        shapeRenderer.circle(
            HARD_CENTER.x,
            HARD_CENTER.y,
            DIFFICULTY_OPTION_RADIUS,
            40
        )

        shapeRenderer.end()
        spriteBatch.projectionMatrix = viewPort.camera.combined
        spriteBatch.begin()
        val easyLayout = GlyphLayout(bitmapFont, Difficulty.EASY.name)
        bitmapFont.draw(
            spriteBatch,
            Difficulty.EASY.name,
            EASY_CENTER.x,
            EASY_CENTER.y + easyLayout.height / 2,
            0f,
            Align.center,
            false
        )


        val normalLayout = GlyphLayout(bitmapFont, Difficulty.NORMAL.name)
        bitmapFont.draw(
            spriteBatch,
            Difficulty.NORMAL.name,
            NORMAL_CENTER.x,
            NORMAL_CENTER.y + normalLayout.height / 2,
            0f,
            Align.center,
            false
        )


        val hardLayout = GlyphLayout(bitmapFont, Difficulty.HARD.name)
        bitmapFont.draw(
            spriteBatch,
            Difficulty.HARD.name,
            HARD_CENTER.x,
            HARD_CENTER.y + hardLayout.height / 2,
            0f,
            Align.center,
            false
        )

        spriteBatch.end()


    }

    override fun resize(width: Int, height: Int) {
        viewPort.update(width, height, true)
        // bitmapFont.data.setScale(Math.min(width, height).toFloat() / Constants.HUD_SIZE)
    }

    override fun dispose() {

    }

    override fun hide() {
        spriteBatch.dispose()
        bitmapFont.dispose()
        shapeRenderer.dispose()
    }

    override fun pause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resume() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val touchPoint = viewPort.unproject(Vector2(screenX.toFloat(), screenY.toFloat()))

        if (touchPoint.dst(EASY_CENTER)< DIFFICULTY_OPTION_RADIUS){
            game.showIciclesScreen(Difficulty.EASY)
        }
        if (touchPoint.dst(NORMAL_CENTER)< DIFFICULTY_OPTION_RADIUS){
            game.showIciclesScreen(Difficulty.NORMAL)
        }
        if (touchPoint.dst(HARD_CENTER)< DIFFICULTY_OPTION_RADIUS){
            game.showIciclesScreen(Difficulty.HARD)
        }

        return true

    }

    companion object {
        val TAG = DifficultyScreen::class.java.simpleName

        const val DIFFICULTY_SCREEN_SIZE = 450f
        const val SCREEN_HEIGHT = 280f
        const val DIFFICULTY_OPTION_RADIUS = DIFFICULTY_SCREEN_SIZE / 8f
        const val DIFFICULTY_LABELS_SIZE_MULTIPLIER = 1.5f
        val EASY_CENTER = Vector2(1f / 6f * DIFFICULTY_SCREEN_SIZE, SCREEN_HEIGHT / 2f)
        val NORMAL_CENTER = Vector2(3f / 6f * DIFFICULTY_SCREEN_SIZE, SCREEN_HEIGHT / 2f)
        val HARD_CENTER = Vector2(5f / 6f * DIFFICULTY_SCREEN_SIZE, SCREEN_HEIGHT / 2f)


    }
}