package com.github.proubatsis.todo

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.{BadRequest, NotFound, ScalatraServlet}
import org.scalatra.json.JacksonJsonSupport

/**
  * Created by panagiotis on 28/12/16.
  */
class JsonServlet extends ScalatraServlet with JacksonJsonSupport {
  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  notFound {
    NotFound("\"Not Found\"")
  }
}