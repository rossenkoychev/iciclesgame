package com.metalgames.icicles.gameMaanager

import com.badlogic.gdx.graphics.Color


//Enum class for difficulties, containing the spawn rate  per second for each difficulty
enum class Difficulty(val spawnRate: Int, val color: Color) {
    EASY(5, Color(0.2f, 0.2f, 1f, 1f)),
    NORMAL(8, Color(0.5f, 0.5f, 1f, 1f)),
    HARD(11, Color(0.7f, 0.7f, 1f, 1f))


}
