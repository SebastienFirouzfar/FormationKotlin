package eu.epfc.kotlingames
import kotlin.math.abs
import kotlin.random.Random

open class Chimpanzee : Animal(){
    override val name = "Chimpanzee"
    override val weight : Float = 0.4f
    override val speed : Float = 0.5f
    override val agility : Float = 0.8f
    override val force : Float = 0.4f


    override fun makeSound(){
        println("héé hé hééééé héééé")
    }

    fun attack(target : Chimpanzee){
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