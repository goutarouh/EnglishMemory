package com.github.goutarouh.englishmemory.data.notion

import com.github.goutarouh.englishmemory.data.notion.model.BlocksChildrenResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface NotionApiService {

    @GET("v1/blocks/{block_id}/children?page_size=100")
    fun retrieveBlockChildren(
        @Path("block_id") blockId: String
    ): Call<BlocksChildrenResponse>
}