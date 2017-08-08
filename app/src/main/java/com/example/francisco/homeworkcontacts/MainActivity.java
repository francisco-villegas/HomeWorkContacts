package com.example.francisco.homeworkcontacts;

/**
 * Created by FRANCISCO on 07/08/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    private static final String TAG = "MA";
    ListView list;
    String[] itemname;
    String[] id;
    String[] phone;

    byte[][] imgid ;

    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);


        try {
            ArrayList<MyContact> contacts = databaseHelper.getContacts("-1");

            itemname = new String[contacts.size()];
            imgid = new byte[contacts.size()][];
            id = new String[contacts.size()];
            phone = new String[contacts.size()];
            for (int i = 0; i < contacts.size(); i++) {
                itemname[i] = contacts.get(i).getName() + " " + contacts.get(i).getLast_name();
                imgid[i] = contacts.get(i).getBitmap();
                id[i] = "" + contacts.get(i).getID();
                phone[i] = contacts.get(i).getPhone();
            }

            CustomListAdapter adapter = new CustomListAdapter(this, itemname, imgid, id, phone);
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(adapter);

            list.setOnItemClickListener(this);
        }catch(Exception ex){}

        Button btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ActivityContact.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(TAG, "onItemClick: ");
        //Toast.makeText(getApplicationContext(), itemname[i] + " " + id[i], Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ActivityContact.class);
        intent.putExtra(getString(R.string.ID),id[i]);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);


        try {
            ArrayList<MyContact> contacts = databaseHelper.getContacts("-1");

            itemname = new String[contacts.size()];
            imgid = new byte[contacts.size()][];
            id = new String[contacts.size()];
            phone = new String[contacts.size()];
            for (int i = 0; i < contacts.size(); i++) {
                itemname[i] = contacts.get(i).getName() + " " + contacts.get(i).getLast_name();
                imgid[i] = contacts.get(i).getBitmap();
                id[i] = "" + contacts.get(i).getID();
                phone[i] = contacts.get(i).getPhone();
            }

            CustomListAdapter adapter = new CustomListAdapter(this, itemname, imgid, id, phone);
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(adapter);

            list.setOnItemClickListener(this);
        }catch(Exception ex){}
    }
}