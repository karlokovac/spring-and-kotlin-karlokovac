package com.infinum.academy.restserver.assemblers

import com.infinum.academy.restserver.controlers.CarCheckUpController
import com.infinum.academy.restserver.controlers.CarController
import com.infinum.academy.restserver.models.CarCheckUpEntity
import com.infinum.academy.restserver.models.CarCheckUpResource
import com.infinum.academy.restserver.models.toResourceModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component

@Component
class CheckUpResourceAssembler :
    RepresentationModelAssemblerSupport<CarCheckUpEntity, CarCheckUpResource>(
        CarCheckUpController::class.java, CarCheckUpResource::class.java
    ) {

    override fun toModel(entity: CarCheckUpEntity): CarCheckUpResource {
        return createModelWithId(entity.id, entity).apply {
            add(
                linkTo<CarController> {
                    fetchCar(entity.carId)
                }.withRel("car")
            )
        }
    }

    override fun instantiateModel(entity: CarCheckUpEntity): CarCheckUpResource {
        return entity.toResourceModel()
    }
}
