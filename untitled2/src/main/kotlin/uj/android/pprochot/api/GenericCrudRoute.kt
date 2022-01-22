package uj.android.pprochot.api

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import uj.android.pprochot.service.CrudService

inline fun <reified Request : Any, reified Response : Any> Route.genericCrudRoute(endpoint:String, service: CrudService<Request, Response>) {
    route(endpoint) {
        post {
            val obj = call.receive<Request>()
            val response = service.create(obj)
            call.respond(response)
        }

        get {
            call.respond(service.getAll())
        }

        route("/{id}") {
            get {
                val id = call.parameters["id"]!!.toIntOrNull()
                    ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid id format!")
                call.respond(service.getById(id))
            }

            put {
                val id = call.parameters["id"]!!.toIntOrNull()
                    ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid id format!")
                val obj = call.receive<Request>()
                call.respond(service.update(id, obj))
            }

            delete {
                val id = call.parameters["id"]!!.toIntOrNull()
                    ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid id format!")
                service.delete(id)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}