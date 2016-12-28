package com.github.proubatsis.todo

/**
  * Created by panagiotis on 28/12/16.
  */
case class Todo(id:Int, title:String, url:String, completed:Boolean)
case class TodoItem(title: Option[String], completed: Option[Boolean])
