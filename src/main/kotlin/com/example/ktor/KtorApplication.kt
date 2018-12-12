package com.example.ktor

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing

fun Application.main() {
  install(DefaultHeaders)
  install(CallLogging)
  install(ContentNegotiation) {
    jackson {
      configure(SerializationFeature.INDENT_OUTPUT, true)
    }
  }
  // https://ktor.io/servers/features/compression.html#configuration
  install(Compression) {
    gzip {
      priority = 1.0
    }
    deflate {
      priority = 10.0
      minimumSize(1024)
    }
  }
  // https://ktor.io/servers/features/https-redirect.html#configuration
//  install(HttpsRedirect) {
//    sslPort = 443
//    permanentRedirect = true
//  }
  routing {
    get("/text") {
      call.respond("Hello ktor !!")
    }
    get("/json") {
      call.respond(JsonData(System.currentTimeMillis(), "Hello ktor !!"))
    }
  }
}

data class JsonData(val id: Long, val name: String)
