package uj.android.pprochot.service

import uj.android.pprochot.models.dto.ListResponse

interface CrudService<RequestT, ResponseT> {

    fun create(request: RequestT): ResponseT

    fun getAll(): ListResponse<ResponseT>

    fun getById(id: Int): ResponseT

    fun update(id: Int, request: RequestT): ResponseT

    fun delete(id: Int)
}