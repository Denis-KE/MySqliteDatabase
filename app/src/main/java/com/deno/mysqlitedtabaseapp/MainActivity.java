package com.deno.mysqlitedtabaseapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText mEdtName,mEdtEmail,mEdtIdNumber;
    private Button mBtnSave,mBtnView,mBtnDelete;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEdtName =findViewById(R.id.edtName);
        mEdtEmail =findViewById(R.id.edtMail);
        mEdtIdNumber =findViewById(R.id.edtIdNumber);
        mBtnSave =findViewById(R.id.btnSave);
        mBtnSave =findViewById(R.id.btnSave);
        mBtnDelete =findViewById(R.id.btnDelete);

        //Create the database
        db =openOrCreateDatabase("Voters_db",MODE_PRIVATE,null);
        //Create table in our db
        db.execSQL("CREATE TABLE IF NOT EXISTS voterreg(name VARCHAR, email VARCHAR, idNo VARCHAR)");

        //Set the onclick listener to save button to implement the saving process
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sTART GETTING Data from the user
                String name =mEdtName.getText().toString().trim();
                String email =mEdtEmail.getText().toString().trim();
                String id_number =mEdtIdNumber.getText().toString().trim();

                //Check if the user is submitting empty field
                if (name.isEmpty()){
                    //Display the message telling the user to fill the name input field
                    mEdtName.setError("Enter name");
                }else if (email.isEmpty()){
                    //Display the message telling the user to fill the email input field
                    mEdtEmail.setError("Enter Email");
                    messages("EMAIL ERROR","Please fill the email input ");
                }else if(id_number.isEmpty()){
                    mEdtIdNumber.setError("Enter id number");
                }else {
                    db.execSQL("INSERT INTO voterreg VALUES('"+name+"','"+email+"','"+id_number+"')");
                    messages("Data Query","Saved Successfully");
                }


            }
        });
        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start by getting the id from the user
                String id_number =mEdtIdNumber.getText().toString().trim();
                //Check if the user hitting delete without filling the id input field
                if (id_number.isEmpty()){
                    mEdtIdNumber.setError("Enter the ID Number");
                }else {
                    //Proceed to delete using the  received id
                    //Use the cursor to select the data to be deleted
                    Cursor cursor =db.rawQuery("SELECT * FROM voterreg  WHERE idNo ='"+id_number+"'",null );
                    if (cursor.moveToFirst()) {
                        db.execSQL("DELETE FROM votereg WHERE iDNo ='"+id_number+"'");
                        messages("DELETED","Record Deleted Successfully");
                        clear();

                    }
                }

            }
        });

        mBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Use the cursor to select the data from db
                Cursor cursor =db.rawQuery("SELECT * FROM voterreg",null);

                //Check if there are any records in the db
                if (cursor.getCount()==0){
                }else {
                }
                }

        });
    }
    public void  messages(String tittle, String message){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle(tittle);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
    //This function clears the input fields for the user to  enter new records
    public void clear(){
        mEdtName.setText("");
        mEdtEmail.setText("");
        mEdtIdNumber.setText("");

    }
}
