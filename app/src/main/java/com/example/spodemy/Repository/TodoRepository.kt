package com.example.spodemy.Repository

import androidx.lifecycle.LiveData
import com.example.spodemy.TodoDatabase.Todo
import com.example.spodemy.TodoDatabase.TodoDao

class TodoRepository(val todoDao: TodoDao){
//    fun getTodo(): LiveData<List<Todo>>
//    {
//        return todoDao.getTodo()
//    }
    val planslist:LiveData<List<Todo>> =todoDao.getTodo()
    suspend fun insertTodo(todo:Todo)
    {
        todoDao.insertTodo(todo)
    }
    suspend fun deleteTodo(todo:Todo)
    {
        todoDao.deleteTodo(todo)
    }
    suspend fun updateTodo(todo:Todo)
    {
        todoDao.updateTodo(todo)
    }
}