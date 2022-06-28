package projects.allan.avengers.avengers.resource.avenger

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import projects.allan.avengers.avengers.domain.avenger.Avenger
import projects.allan.avengers.avengers.domain.avenger.AvengerRepository


@Component
class AvengerRepositoryImpl(
    private val avengerEntityRepository: AvengerEntityRepository
) : AvengerRepository {
    override fun getDetails(id: Long): Avenger? =
        avengerEntityRepository.findByIdOrNull(id)?.toAvenger()

    override fun getAvengers(): List<Avenger> =
        avengerEntityRepository.findAll().map { it.toAvenger() }

    override fun create(avenger: Avenger): Avenger =
        avengerEntityRepository.save(AvengerEntity.from(avenger)).toAvenger()

    override fun delete(id: Long) = avengerEntityRepository.deleteById(id)

    override fun update(avenger: Avenger): Avenger =
        avengerEntityRepository.save(AvengerEntity.from(avenger)).toAvenger()
}