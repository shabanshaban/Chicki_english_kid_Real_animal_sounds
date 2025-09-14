package com.farad.entertainment.kidsanimalenglish.data.apiService

import androidx.annotation.Keep
import kotlinx.coroutines.Job

@Keep
data class ApiConfig(
    var id: Long,
    val tag: String,
    val isHandleMessage: Boolean,
    val isHandleProgress: Boolean,
    val isForceOnline: Boolean,
    val messageApiType: MessageApiType,
    val progressApiType: ProgressApiType,
    val job: Job,
) {
    class Builder {
        private var id: Long = System.nanoTime()
        private var tag: String = ""
        private var handleMessage: Boolean = true
        private var handleProgress: Boolean = true
        private var forceOnline: Boolean = true
        private var messageApiType: MessageApiType = MessageApiType.MAIN
        private var progressApiType: ProgressApiType = ProgressApiType.MAIN
        private var job: Job = Job()

        fun setId(id: Long): Builder {
            this.id = id
            return this
        }

        fun setTag(tag: String): Builder {
            this.tag = tag
            return this
        }

        fun setHandleMessage(handleMessage: Boolean): Builder {
            this.handleMessage = handleMessage
            return this
        }

        fun setHandleProgress(handleProgress: Boolean): Builder {
            this.handleProgress = handleProgress
            return this
        }

        fun setForceOnline(forceOnline: Boolean): Builder {
            this.forceOnline = forceOnline
            return this
        }

        fun setMessageType(messageApiType: MessageApiType): Builder {
            this.messageApiType = messageApiType
            return this
        }

        fun setProgressType(progressApiType: ProgressApiType): Builder {
            this.progressApiType = progressApiType
            return this
        }

        fun setJob(job: Job): Builder {
            this.job = job
            return this
        }

        fun build(): ApiConfig {
            return ApiConfig(
                id,
                tag,
                handleMessage,
                handleProgress,
                forceOnline,
                messageApiType,
                progressApiType,
                job
            )
        }
    }

    override fun hashCode(): Int {
        return id.toInt()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        } else if (other is ApiConfig) {
            return other.id == id
        }
        return false
    }
}


@Keep
enum class ProgressApiType(var id:Long) {
    MAIN(0),
    CUSTOM(2),
    None(-1),
}

@Keep
enum class MessageApiType {
    MAIN,
    CUSTOM
}