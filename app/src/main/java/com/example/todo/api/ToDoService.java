package com.example.todo.api;

import java.util.List;

import com.example.todo.model.ToDo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ToDoService {

    @GET("/todos")
    Call<List<ToDo>> recuperarToDo();

    @GET("/todos/{id}")
    Call<ToDo> recuperarToDoId(@Path("id") int id);

    @POST("/todos")
    Call<ToDo> salvarToDo(@Body ToDo todo);

    @PUT("/todos/{id}")
    Call<ToDo> atualizarToDo (@Path("id") int id, @Body ToDo todo);

    @PATCH("/todos/{id}")
    Call<ToDo> atualizarToDoPatch (@Path("id") int id, @Body ToDo todo);

    @DELETE("/todos/{id}")
    Call<Void> removerToDo(@Path("id") int id);


}
