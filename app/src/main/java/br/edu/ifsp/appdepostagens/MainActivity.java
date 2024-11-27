package br.edu.ifsp.appdepostagens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsp.appdepostagens.api.ToDoService;
import br.edu.ifsp.appdepostagens.model.ToDo;
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
            System.out.println("O texto não é um número válido.");
        }
        return 0;
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
                        textResultado.setText(todo.getId() +
                                " \n " + todo.getUserId() +
                                " \n " + todo.getTitle() +
                                " \n " + todo.getCompleted());
                    }
                }
            }

            @Override
            public void onFailure (Call<List<ToDo>> call, Throwable t) {

            }
        });

    }

    public void recuperarToDoId(View view){
        Call<List<ToDo>> call = toDoService.recuperarToDoId(getInput());

        call.enqueue(new Callback<List<ToDo>>() {
            @Override
            public void onResponse (Call<List<ToDo>> call, Response<List<ToDo>> response) {

                if (response.isSuccessful()){
                    listaToDo = response.body();

                    for (ToDo todo: listaToDo) {
                        if(Integer.parseInt(todo.getId()) == getInput()) {
                            Log.d("resultado", "resultado = " + todo.getUserId() + " / " + todo.getTitle() + " / " + todo.getCompleted());
                            textResultado.setText(todo.getId() +
                                    " \n " + todo.getUserId() +
                                    " \n " + todo.getTitle() +
                                    " \n " + todo.getCompleted());
                        }
                    }
                }
                else {
                    textResultado.setText("Erro");
                }
            }

            @Override
            public void onFailure (Call<List<ToDo>> call, Throwable t) {

            }
        });

    }

    public void salvarToDo(View view){

        ToDo novoToDo = new ToDo("1234", "Titulo da nova postagem", false);

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

        ToDo todo = new ToDo("1234", null, false);

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