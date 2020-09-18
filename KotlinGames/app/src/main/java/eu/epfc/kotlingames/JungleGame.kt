package eu.epfc.kotlingames

import kotlin.random.Random


fun createEnemmie() : List<Animal>{
    /**
     * val tiger = Tiger()
    val elephant = Elephant()
    val hippo = Hippo()
    val snake = Snake()
    val chimpanzee = Chimpanzee()
    val animaux : List<Animal> = listOf(tiger, elephant, hippo, snake, chimpanzee)
     */

    val animauxEnnemie = mutableListOf<Animal>()
    for (i in 0 ..4){
        val randomInt = Random.nextInt(4)
        val randomAnimal = when(randomInt){
            0 -> Tiger()
            1 -> Elephant()
            2 -> Hippo()
            3 -> Snake()
            4 -> Chimpanzee()
            else -> Tiger()
        }
        animauxEnnemie.add(randomAnimal)
    }
    return animauxEnnemie
}

fun playJungle() {
    val playerCurrent = Tiger()
    val ennemies: List<Animal> = createEnemmie()

    var playerDeadBeforeEnd = false
    for (ennemy: Animal in ennemies) {
        println("\nNext ennemy : ${ennemy.name}\n")
        while (playerCurrent.lifePoint > 0 && ennemy.lifePoint > 0) {
            if (readLine()!![0] == 'a') {
                playerCurrent.attack(ennemy)

            }
        }
        if (playerCurrent.lifePoint <= 0) {
            println("Game over")
            println(playerCurrent.lifePoint)
            playerDeadBeforeEnd = true
            break

        } else if (ennemy.lifePoint <= 0) {
            println(playerCurrent.lifePoint)
            createEnemmie()


        }
        if (!playerDeadBeforeEnd) {
            println("Vous avez gagner ")
        }
    }
}
