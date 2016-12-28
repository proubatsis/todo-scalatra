package com.github.proubatsis.todo

import scala.collection.mutable

/**
  * Created by panagiotis on 28/12/16.
  */
object TodoService {
  val BASE_URL = "http://localhost:8080/api/todos"
  var todos:List[Todo] = List()

  def getAll(): List[Todo] = {
    todos
  }

  def get(id:Int): Option[Todo] = {
    todos.find(t => t.id == id)
  }

  def create(title:String): Todo = {
    val id = if (todos.nonEmpty) todos.maxBy(t => t.id).id + 1 else 0
    val todo = Todo(id, title, BASE_URL + "/" + id, false)
    todos = todo::todos
    todo
  }

  def edit(id: Int, changes: TodoItem): Option[Todo] = {
    val todo = get(id)

    todo match {
      case Some(t) => {
        val changed = editTodo(t, changes)
        todos = replace(changed, todos)
        Some(changed)
      }
      case None => None
    }
  }

  def deleteAll(): Unit = {
    todos = List()
  }

  // Helpers

  /**
    * Return new list with the todo with the given id replaced with the new todo.
    * @param todo New todo (with existing id)
    * @param todos todos list with the todo replaced
    * @return List of todos with replaced item
    */
  def replace(todo: Todo, todos: List[Todo]): List[Todo] = {
    todos match {
      case Todo(todo.id, _, _, _)::tail => todo::tail
      case h::t => h::replace(todo, t)
      case _ => todos
    }
  }

  /**
    * Return new todo edited with the given changes
    * @param todo todo to change
    * @param changes changes to make to existing Todo
    * @return edited Todo
    */
  def editTodo(todo: Todo, changes: TodoItem): Todo = {
    val editTitle = (title: String) => Todo(todo.id, title, todo.url, todo.completed)
    val editCompleted = (completed: Boolean) => Todo(todo.id, todo.title, todo.url, completed)
    val editTitleCompleted = (title: String, completed: Boolean) => Todo(todo.id, title, todo.url, completed)

    changes match {
      case TodoItem(Some(title), None) => editTitle(title)
      case TodoItem(None, Some(completed)) => editCompleted(completed)
      case TodoItem(Some(title), Some(completed)) => editTitleCompleted(title, completed)
    }
  }
}
