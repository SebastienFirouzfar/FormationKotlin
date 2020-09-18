package eu.epfc.kotlingames
import kotlin.math.abs
import kotlin.random.Random

open class Elephant : Animal(){
    override val name = "Elephant"
    override val weight : Float = 0.1f
    override val speed : Float = 0.3f
    override val agility : Float = 0.3f
    override val force : Float = 0.7f

    override fun makeSound(){
        println("hééééééé")
    }

    fun attack(target : Elephant){
        // (target = ennemie) un autre objet Elephant
        val attackScore = 10*weight *(speed + agility + force)+ Random.nextFloat()*3
        val ennemyScore = 10*weight *(target.speed + target.agility + target.force)+ Random.nextFloat()*3

        val damage = abs(attackScore - ennemyScore)
        if (attackScore > ennemyScore){
            target.lifePoint -= damage
        }else{
            lifePoint -= damage
        }

    }

}