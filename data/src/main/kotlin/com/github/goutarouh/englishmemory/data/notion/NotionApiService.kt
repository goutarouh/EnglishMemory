package com.github.goutarouh.englishmemory.data.notion

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NotionApiService {

    @GET("v1/blocks/{block_id}/children?page_size=100")
    fun retrieveBlockChildren(
        @Path("block_id") blockId: String
    ): Call<Int>
}