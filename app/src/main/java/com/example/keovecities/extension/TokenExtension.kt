package com.example.keovecities.extension

import com.example.keovecities.Defaults


fun isValidToken(): Boolean {

    val lastTime = System.currentTimeMillis() - Defaults.TOKEN_EXPIRE_TIME!!

    return lastTime < 600000
}

