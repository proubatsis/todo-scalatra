package com.github.proubatsis.todo

class TodoServlet extends JsonServlet {

  get("/api/todos") {
    TodoService.getAll()
  }

  get("/api/todos/:id") {
    val id = params("id").toInt
    TodoService.get(id)
  }

  post("/api/todos") {
    val todo = parsedBody.extract[TodoItem]

    if (todo.title.isEmpty)
      Nil
    else
      TodoService.create(todo.title.head)
  }

  patch("/api/todos/:id") {
    val id = params("id").toInt
    val todo = parsedBody.extract[TodoItem]

    if(todo.title.isEmpty && todo.completed.isEmpty)
      Nil
    else
      TodoService.edit(id, todo)
  }

  delete("/api/todos") {
    TodoService.deleteAll()
  }

}
