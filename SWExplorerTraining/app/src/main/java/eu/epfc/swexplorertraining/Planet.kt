package eu.epfc.swexplorertraining

import java.io.Serializable

class Planet (val name : String,
              val climate : String,
              val terrain: String,
              val population : Long,
              val diameter: Int,
              val orbitalPeriod: Int,
              val rotationPeriod: Int ): Serializable {

}