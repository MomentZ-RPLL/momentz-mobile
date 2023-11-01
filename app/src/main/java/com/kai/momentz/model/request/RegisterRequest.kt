package com.kai.momentz.model.request

import okhttp3.MultipartBody

data class RegisterRequest (
    var username : String,
    var password : String,
    var name : String,
    var email : String,
    var file : MultipartBody.Part
)