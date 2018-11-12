package edu.osucascades.choprakrishansqlitedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editFirst, editLast, editGrade;
    Button addData, viewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editFirst = (EditText) findViewById(R.id.editTextFirst);
        editLast = (EditText) findViewById(R.id.editTextLast);
        editGrade = (EditText) findViewById(R.id.editTextGrade);
        addData = (Button) findViewById(R.id.addDataButton);
        viewAll = (Button) findViewById(R.id.viewAllButton);

        addData();
        viewAll();

    }

    public void addData() {
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(
                        editFirst.getText().toString(),
                        editLast.getText().toString(),
                        editGrade.getText().toString()
                );
                if(isInserted) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Successfully added",
                            Toast.LENGTH_SHORT);

                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "No success",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }

            }
        });
    }

    public void viewAll() {
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = myDb.getAllData();
                if (cursor.getCount() == 0) {
                    showMessage("Error", "Nothing Found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    buffer.append("Id :" + cursor.getString(0) + "\n");
                    buffer.append("First :" + cursor.getString(1) +"\n");
                    buffer.append("Last :" + cursor.getString(2) +"\n");
                    buffer.append("Grade :" + cursor.getString(3) +"\n\n");
                }

                showMessage("Data", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }
}
