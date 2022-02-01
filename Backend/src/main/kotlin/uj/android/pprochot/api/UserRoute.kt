package uj.android.pprochot.api

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import uj.android.pprochot.models.dto.user.UserRequest
import uj.android.pprochot.service.UserService

fun Route.userRoute(endpoint:String, userService: UserService) {
    route(endpoint) {
        post {
            val obj = call.receive<UserRequest>()
            val response = userService.create(obj)
            call.respond(response)
        }

        get {
            call.respond(userService.getAll())
        }

        route("/{id}") {
            get {
                val id = call.parameters["id"]!!.toIntOrNull()
                    ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid id format!")
                call.respond(userService.getById(id))
            }

            put {
                val id = call.parameters["id"]!!.toIntOrNull()
                    ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid id format!")
                val obj = call.receive<UserRequest>()
                call.respond(userService.update(id, obj))
            }

            delete {
                val id = call.parameters["id"]!!.toIntOrNull()
                    ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid id format!")
                userService.delete(id)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}