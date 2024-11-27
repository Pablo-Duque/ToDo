package com.example.todo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.api.ToDoService;
import com.example.todo.model.ToDo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textResultado;
    private Retrofit retrofit;
    private List<ToDo> listaToDo;
    ToDoService toDoService;

    private EditText meuInput;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResultado = findViewById(R.id.textResultado);
        meuInput = findViewById(R.id.input);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/todos/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        toDoService = retrofit.create(ToDoService.class);

    }


    public int getInput(){
        try {
            return Integer.parseInt(meuInput.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void recuperarToDo(View view){


        Call<List<ToDo>> call = toDoService.recuperarToDo();

        call.enqueue(new Callback<List<ToDo>>() {
            @Override
            public void onResponse (Call<List<ToDo>> call, Response<List<ToDo>> response) {

                if (response.isSuccessful()){
                    listaToDo = response.body();

                    for (ToDo todo: listaToDo) {
                        Log.d("resultado", "resultado = " + todo.getUserId() + " / " + todo.getTitle() + " / " + todo.getCompleted()) ;
                    }
                }
            }

            @Override
            public void onFailure (Call<List<ToDo>> call, Throwable t) {

            }
        });

    }

    public void recuperarToDoId(View view){

        Call<ToDo> call = toDoService.recuperarToDoId(getInput());

        call.enqueue(new Callback<ToDo>() {
            @Override
            public void onResponse(Call<ToDo> call, Response<ToDo> response) {
                if (response.isSuccessful()) {
                    ToDo todo = response.body();

                    if (todo != null && Integer.parseInt(todo.getId()) == getInput()) {
                        Log.d("resultado", "resultado = " + todo.getUserId() + " / " + todo.getTitle() + " / " + todo.getCompleted());
                        textResultado.setText(todo.getId() +
                                " \n " + todo.getUserId() +
                                " \n " + todo.getTitle() +
                                " \n " + todo.getCompleted());
                    } else {
                        textResultado.setText("ToDo não encontrado");
                    }
                } else {
                    textResultado.setText("Erro");
                }
            }

            @Override
            public void onFailure(Call<ToDo> call, Throwable t) {
                // Aqui você pode tratar falhas, como problemas de rede
                textResultado.setText("Falha na conexão: " + t.getMessage());
            }
        });
    }


    public void salvarToDo(View view){

        ToDo novoToDo = new ToDo("4321", "Texto novo", false);

        Call<ToDo> call = toDoService.salvarToDo(novoToDo);

        call.enqueue(new Callback<ToDo>() {
            @Override
            public void onResponse (Call<ToDo> call, Response<ToDo> response) {
                if (response.isSuccessful()){
                    ToDo todo = response.body();

                    textResultado.setText(todo.getId() +
                            " \n " + todo.getUserId() +
                            " \n " + todo.getTitle() +
                            " \n " + todo.getCompleted());
                }
            }

            @Override
            public void onFailure (Call<ToDo> call, Throwable t) {

            }
        });
    }

    public void atualizarToDo(View view){

        ToDo todo = new ToDo("4321", "Texto atualizado muito foda", true);

        //Call<Postagem> call = postagemService.atualizarPostagem(1, postagem);
        Call<ToDo> call = toDoService.atualizarToDoPatch(getInput(), todo);


        call.enqueue(new Callback<ToDo>() {
            @Override
            public void onResponse (Call<ToDo> call, Response<ToDo> response) {

                if (response.isSuccessful()){

                    ToDo todo = response.body();

                    textResultado.setText(todo.getId() +
                            " \n " + todo.getUserId() +
                            " \n " + todo.getTitle() +
                            " \n " + todo.getCompleted());

                }
            }

            @Override
            public void onFailure (Call<ToDo> call, Throwable t) {

            }
        });

    }

    public void removerPostagem(View view){

        Call<Void> call = toDoService.removerToDo(getInput());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse (Call<Void> call, Response<Void> response) {
                textResultado.setText("Codigo Retorno: " + response.code());

            }

            @Override
            public void onFailure (Call<Void> call, Throwable t) {

            }
        });
    }
}