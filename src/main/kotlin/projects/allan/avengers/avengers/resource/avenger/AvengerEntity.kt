package projects.allan.avengers.avengers.resource.avenger

import projects.allan.avengers.avengers.domain.avenger.Avenger
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "avenger")
data class AvengerEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long?,
    @Column(nullable = false)
    val nick : String,
    @Column(nullable = false)
    val person:  String,
    val description:  String?,
    val history:  String?
){
    fun toAvenger() = Avenger(
         id,
         nick,
         person,
         description,
         history
    )

    companion object{
        fun from(avenger: Avenger) = AvengerEntity(
            id = avenger.id,
            nick = avenger.nick,
            person = avenger.person,
            description = avenger.description,
            history = avenger.history
        )
    }
}