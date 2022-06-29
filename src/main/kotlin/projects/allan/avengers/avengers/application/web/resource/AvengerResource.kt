package projects.allan.avengers.avengers.application.web.resource

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import projects.allan.avengers.avengers.application.web.resource.request.AvengerRequest
import projects.allan.avengers.avengers.application.web.resource.response.AvengerResponse
import projects.allan.avengers.avengers.domain.avenger.AvengerRepository
import java.net.URI
import javax.validation.Valid


private const val API_PATH = "/v1/api/avenger"

@RestController
@RequestMapping(API_PATH)
class AvengerResource(
    @Autowired private val avengerRepository: AvengerRepository
) {

    @GetMapping
    fun getAvengers() : ResponseEntity<List<AvengerResponse>> {
        return ResponseEntity.ok().body(avengerRepository.getAvengers()
            .map {
                AvengerResponse.from(it)
            }
        )
    }

    @GetMapping("/{id}/detail")
    fun getAvengerDetails(@PathVariable id : Long) =
        avengerRepository.getDetails(id)?.let {
            ResponseEntity.ok().body(AvengerResponse.from(it))
        } ?: ResponseEntity.notFound().build<Unit>()


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAvenger(@Valid @RequestBody avengerRequest: AvengerRequest) : ResponseEntity<AvengerResponse>{
        //avengerRepository.create(avengerRequest.toAvenger())
        return avengerRequest.toAvenger().run {
            avengerRepository.create(this)
        }.let {
            ResponseEntity.created(URI("$API_PATH/${it.id}"))
                .body(AvengerResponse.from(it))
        }
    }

    @PutMapping("/{id}")
    fun updateAvenger(@PathVariable id: Long, @RequestBody avengerRequest: AvengerRequest) =
        avengerRepository.getDetails(id)?.let {
            AvengerRequest.to(it.id, avengerRequest).apply {
                avengerRepository.update(this)
            }.let { avenger ->
                ResponseEntity.accepted().body(AvengerResponse.from(avenger))
            }
        } ?: ResponseEntity.notFound().build<Unit>()

    @DeleteMapping("/{id}")
    fun deleteAvenger(@PathVariable id: Long) =
        avengerRepository.delete(id).let {
            ResponseEntity.accepted().build<Unit>()
        }



}