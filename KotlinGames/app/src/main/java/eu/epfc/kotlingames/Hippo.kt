package eu.epfc.kotlingames
import eu.epfc.kotlingames.Animal
import kotlin.math.abs
import kotlin.random.Random

open class Hippo : Animal(){
    override val name = "Snake"
    override val weight : Float = 0.8f
    override val speed : Float = 0.3f
    override val agility : Float = 0.2f
    override val force : Float = 0.7f

    override fun makeSound(){
        println("héé hé hééééé héééé")
    }

    fun attack(target : Hippo){
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