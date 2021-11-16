package com.example.haushaltsplaner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overview_menu, menu);
        return true;
    }

    // get a passed Item and check which one was clicked
    @Override

    //Methode zum Aufrufen des Overview-Menus
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.item1:
                Toast.makeText(this,"Transaktionen ausgewählt",Toast.LENGTH_SHORT).show();
                //Erstellung Intent mit Empfänger, hier Transaktion Klasse
                //Intent switchActivityIntent = new Intent(this, Transaktion.class);
                // If data is need to be sent between activities
                //      EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
                //      String message = editText.getText().toString();
                //      intent.putExtra(EXTRA_MESSAGE, message);
                //Rufe Activity "Transaktion" auf
                //startActivity(switchActivityIntent);
                return true;
            case R.id.subitem1:
                Toast.makeText(this,"Einnahmen ausgewählt",Toast.LENGTH_SHORT).show();
                //Intent switchActivityIntent = new Intent(this, Einnahmen.class);
                // If data is need to be sent between activities
                //      EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
                //      String message = editText.getText().toString();
                //      intent.putExtra(EXTRA_MESSAGE, message);
                //startActivity(switchActivityIntent);
                return true;
            case R.id.subitem2:
                Toast.makeText(this,"Ausgaben ausgewählt",Toast.LENGTH_SHORT).show();
                //Intent switchActivityIntent = new Intent(this, Ausgaben.class);
                // If data is need to be sent between activities
                //      EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
                //      String message = editText.getText().toString();
                //      intent.putExtra(EXTRA_MESSAGE, message);
                //startActivity(switchActivityIntent);
                return true;
            case R.id.item2:
                Toast.makeText(this,"Budget Limit ausgewählt",Toast.LENGTH_SHORT).show();
                //Erstellung Intent mit Empfänger, hier BudgetLimit Klasse
                //Intent switchActivityIntent = new Intent(this, BudgetLimit.class);
                // If data is need to be sent between activities
                //      EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
                //      String message = editText.getText().toString();
                //      intent.putExtra(EXTRA_MESSAGE, message);
                //Rufe Activity "Budget Limit" auf
                //startActivity(switchActivityIntent);
                // return true;

            case R.id.item3:
                Toast.makeText(this,"Diagrammansicht ausgewählt",Toast.LENGTH_SHORT).show();
                //Intent switchActivityIntent = new Intent(this, Diagramm.class);
                // If data is need to be sent between activities
                //      EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
                //      String message = editText.getText().toString();
                //      intent.putExtra(EXTRA_MESSAGE, message);
                //Rufe Activity "Diagramm" auf
                //startActivity(switchActivityIntent);
                return true;

            case R.id.item4:
                //Erstellung Intent mit Empfänger, hier Calender Klasse
                Intent switchActivityIntent = new Intent(this, Calendar.class);
                // If data is need to be sent between activities
                //      EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
                //      String message = editText.getText().toString();
                //      intent.putExtra(EXTRA_MESSAGE, message);
                //Rufe Activity "Kalender" auf
                startActivity(switchActivityIntent);
                return true;
            case R.id.item5:
                //Erstellung Intent mit Empfänger, hier To-Do Liste Klasse
                Toast.makeText(this,"To-Do Liste ausgewählt",Toast.LENGTH_SHORT).show();
                //Rufe Klasse "To-Do Liste" auf
                //Intent switchActivityIntent = new Intent(this, ToDoList.class);
                // If data is need to be sent between activities
                //      EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
                //      String message = editText.getText().toString();
                //      intent.putExtra(EXTRA_MESSAGE, message);
                //Rufe Activity "To-Do Liste" auf
                //startActivity(switchActivityIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}