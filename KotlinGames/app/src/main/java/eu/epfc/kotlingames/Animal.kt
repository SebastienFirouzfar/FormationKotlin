package eu.epfc.kotlingames
import kotlin.math.abs
import kotlin.random.Random

abstract class Animal{
    abstract val name : String
    abstract val weight : Float
    abstract val speed : Float
    abstract val agility : Float
    abstract val force : Float
    var lifePoint  : Float = 3.0f

    abstract fun makeSound()

    fun attack(target : Animal){
        // (target = ennemie) un autre objet Tiger
        val attackScore = 10*weight *(speed + agility + force)+ Random.nextFloat()*3
        val ennemyScore = 10*weight *(target.speed + target.agility + target.force)+ Random.nextFloat()*3

        val damage = abs(attackScore - ennemyScore)
        if (attackScore > ennemyScore){
            target.lifePoint -= damage
        }else{
            lifePoint -= damage;
        }

    }

}