package com.metalgames.icicles.screens

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.metalgames.icicles.gameMaanager.Difficulty

class IciclesGame : Game() {


    init {

    }
    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        //setScreen(IciclsesScreen())
        showDifficultyScreen()
    }

    fun showDifficultyScreen() {
        setScreen(DifficultyScreen(this))
    }

    fun showIciclesScreen(difficulty:Difficulty){
        setScreen(IciclsesScreen(difficulty,this))
    }
}
