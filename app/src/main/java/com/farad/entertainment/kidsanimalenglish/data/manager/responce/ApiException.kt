package com.farad.entertainment.kidsanimalenglish.data.manager.responce

import okhttp3.Response
import okio.IOException

class ApiException(
    val response: Response,
    val messageServer: MessageServer,
    override val message: String? =
        "ApiException Server",
) : IOException(message)
