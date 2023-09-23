package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {

    EditText ingredients;
    Button find;
    TextView recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ingredients=findViewById(R.id.ingredients);
        find=findViewById(R.id.find);
        recipe=findViewById(R.id.recipe);
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenerateRecipe();

            }
        });
    }
    private void GenerateRecipe() {

        String ingredients_var=ingredients.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Python py = Python.getInstance();
                PyObject pyObject = py.getModule("script");
                PyObject obj = pyObject.callAttr("RecipeGeneration", ingredients_var);
                Handler threadHandler = new Handler(Looper.getMainLooper());
                threadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"Recipe Generation Completed",Toast.LENGTH_SHORT).show();
                        recipe.setText(obj.toString());
                        recipe.setMovementMethod(new ScrollingMovementMethod());
                    }
                });

            }
        }).start();
    }

}