package com.infinum.academy.restserver.assemblers

import com.infinum.academy.restserver.controlers.CarController
import com.infinum.academy.restserver.models.CarCheckUpEntity
import com.infinum.academy.restserver.models.CarEntity
import com.infinum.academy.restserver.models.CarResource
import com.infinum.academy.restserver.models.toResourceModel
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component

private const val PAGE_SIZE = 10

@Component
class CarResourceAssembler :
    RepresentationModelAssemblerSupport<CarEntity, CarResource>(
        CarController::class.java, CarResource::class.java
    ) {
    private val nullAssembler = PagedResourcesAssembler<CarCheckUpEntity>(null, null)

    override fun toModel(entity: CarEntity): CarResource {
        return createModelWithId(entity.id, entity).apply {
            add(
                linkTo<CarController> {
                    getCarCheckUpsForCarId(entity.id, Pageable.ofSize(PAGE_SIZE), nullAssembler)
                }.withRel("checkups")
            )
        }
    }

    override fun instantiateModel(entity: CarEntity): CarResource {
        return entity.toResourceModel()
    }
}
