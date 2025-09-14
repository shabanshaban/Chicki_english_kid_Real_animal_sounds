package com.farad.entertainment.kidsanimalenglish.data.manager.responce

class OfflineException(
    override val message: String? =
        "no internet connection",
) : Throwable(message)