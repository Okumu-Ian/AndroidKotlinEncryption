package com.sample.downloadfile

class SecurityException : Exception {
    constructor() {}
    constructor(message: String?, throwable: Throwable?) : super(message, throwable) {}
}