package com.github.proubatsis.todo

import org.scalatra.{BadRequest, NotFound, Ok}

class TodoServlet extends JsonServlet {

  get("/api/todos") {
    TodoService.getAll()
  }

  get("/api/todos/:id") {
    val id = params("id").toInt
    TodoService.get(id) match {
      case Some(todo) => Ok(todo)
      case None => NotFound()
    }
  }

  post("/api/todos") {
    val todo = parsedBody.extract[TodoItem]

    todo.title match {
      case Some(title) => TodoService.create(title)
      case None => BadRequest()
    }
  }

  patch("/api/todos/:id") {
    val id = params("id").toInt
    val todo = parsedBody.extract[TodoItem]

    todo match {
      case TodoItem(Some(_), Some(_)) => Ok(TodoService.edit(id, todo))
      case TodoItem(_, Some(_)) => Ok(TodoService.edit(id, todo))
      case TodoItem(Some(_), _) => Ok(TodoService.edit(id, todo))
      case _ => BadRequest()
    }
  }

  delete("/api/todos") {
    TodoService.deleteAll()
  }

}
