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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this,"Item 1 Selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this,"Item 2 Selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this,"Item 3 Selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem1:
                Toast.makeText(this,"Subitem 1 Selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem2:
                Toast.makeText(this,"Subitem 2 Selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item4:
                Intent switchActivityIntent = new Intent(this, Calendar.class);
                // If data is need to be sent between activities
                //      EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
                //      String message = editText.getText().toString();
                //      intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(switchActivityIntent);
                return true;

            case R.id.item5:
                Toast.makeText(this,"Item 5 Selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item6:
                Toast.makeText(this,"Item 6 Selected",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}