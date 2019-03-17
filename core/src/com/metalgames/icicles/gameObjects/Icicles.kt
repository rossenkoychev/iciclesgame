package com.metalgames.icicles.gameObjects

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.metalgames.icicles.gameMaanager.Difficulty

class Icicles(var difficulty: Difficulty) {

    val iciclesList = mutableListOf<Icicle>()
    var dodgedIcicles: Int = 0


    fun update(delta: Float) {
        val rand = (0..100).random() / 100f
        if (rand < delta * difficulty.spawnRate) {
            iciclesList.add(Icicle())
        }

        val iciclesToRemove = iciclesList.filter { icicle -> icicle.position.y <= 0 }//Icicle.ICICLE_HEIGHT/2 }

        dodgedIcicles += iciclesToRemove.size
        for (icicle: Icicle in iciclesToRemove) {
            iciclesList.remove(icicle)
        }

        for (icicle: Icicle in iciclesList) {
            icicle.update(delta)
        }

    }

    fun render(renderer: ShapeRenderer) {
        for (icicle: Icicle in iciclesList) {
            icicle.render(renderer)
        }
    }

    fun reset() {
        iciclesList.clear()
        dodgedIcicles = 0
    }

    companion object {
        val TAG = Icicle::class.java.simpleName

        const val ICILES_PER_SECOND_EASY = 5
        const val ICICLES_PER_SECOND_NORMAL = 8
        const val ICICLES_PER_SECOND_HARD = 11
    }
}