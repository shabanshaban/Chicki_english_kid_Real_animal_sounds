package com.farad.entertainment.kidsanimalenglish.data.manager.responce

import okhttp3.Response
import okio.IOException

class UnauthorizedException(
    val response: Response,
    override val message: String? =
        "UnauthorizedException Server 401",
) : IOException(message)