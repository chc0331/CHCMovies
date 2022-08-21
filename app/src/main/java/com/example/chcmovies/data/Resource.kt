package com.example.chcmovies.data

import retrofit2.Response

class Resource<out T> private constructor(
    val status: Status,
    val data: T?, val message: String?
) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(msg: String, data: T? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data, msg
            )
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }

        fun <T> create(status: Status, data: T?, message: String?): Resource<T> {
            return Resource(status, data, message)
        }

        fun <T> create(throwable: Throwable): Resource<T> {
            return Resource(
                Status.ERROR,
                null,
                throwable.message ?: "unknown error"
            )
        }


        fun <T> create(response: Response<T>, isNeedDeepCheck: Boolean = false): Resource<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    Resource<T>(
                        Status.SUCCESS,
                        null,
                        "No content"
                    )
                } else {
                    if (isNeedDeepCheck) {
                        Resource<T>(
                            Status.ERROR,
                            body,
                            "Deepcheck not possible"
                        )
                    } else {
                        Resource<T>(
                            Status.SUCCESS,
                            body,
                            "success"
                        )
                    }
                }
            } else {
                error("error")
            }
        }
    }

}
