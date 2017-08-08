package com.example.francisco.homeworkcontacts;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class ActivityContact extends AppCompatActivity implements View.OnClickListener {
    public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;
    private static final String TAG = "Main";
    private WebView webView;
    ImageButton img;
    Button btnSave, btnDel;
    EditText etname, etlastname, etphone, etemail, company, address;
    Bitmap bitmap;
    String filepath = "";
    String source = "";
    DatabaseHelper databaseHelper;
    String id = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        etname = (EditText) findViewById(R.id.etname);
        etlastname = (EditText) findViewById(R.id.etlastname);
        etphone = (EditText) findViewById(R.id.etphone);
        etemail = (EditText) findViewById(R.id.etemail);
        company = (EditText) findViewById(R.id.company);
        address = (EditText) findViewById(R.id.address);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDel);
        img = (ImageButton) findViewById(R.id.img);

        btnSave.setOnClickListener(this);
        btnDel.setOnClickListener(this);

        img.setOnClickListener(this);

        databaseHelper = new DatabaseHelper(this);

        try {
            Intent intent = getIntent();
            id = intent.getStringExtra(getString(R.string.ID));
            if(Integer.parseInt(id)>=0){
                btnSave.setText("Edit");
                btnSave.setBackgroundColor(Color.parseColor("#F0AD4E"));
            }
            if(!id.equals("")) {
                ArrayList<MyContact> contacts = databaseHelper.getContacts(id);
                if (contacts.size() > 0) {
                    etname.setText(contacts.get(0).getName());
                    etlastname.setText(contacts.get(0).getLast_name());
                    etphone.setText(contacts.get(0).getPhone());
                    etemail.setText(contacts.get(0).getEmail());
                    company.setText(contacts.get(0).getCompany());
                    address.setText(contacts.get(0).getAddress());
                    byte[] b = contacts.get(0).getBitmap();
                    bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                    img.setImageBitmap(bitmap);
                }
            }
        }catch(Exception ex){}

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(bitmap!=null) {
            outState.putString("img", "uploaded");
            outState.putString("source", source);
            outState.putString("filepath", filepath);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        try {
            if (savedInstanceState.getString("img").equals("uploaded")) {
                filepath = savedInstanceState.getString("filepath");
                File file = new File(filepath);
                source = savedInstanceState.getString("source");
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    //Landscape do some stuff

                    if(!savedInstanceState.getString("source").equals("landscape")){
                        bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 768, 1024);
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        img.setImageBitmap(bitmap2);
                        img.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                    else {
                        bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1024, 768);
                        img.setImageBitmap(bitmap);
                        img.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                } else {
                    //portrait

                    //rotates img
                    if(savedInstanceState.getString("source").equals("portrait")) {
                        bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 768, 1024);
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        img.setImageBitmap(bitmap2);
                        img.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                    else {
                        bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1024 , 768);
                        img.setImageBitmap(bitmap);
                        img.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                }
            }
        }catch(Exception ex){}
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE) {
            //Get our saved file into a bitmap object:

            File file = new File(Environment.getExternalStorageDirectory() + File.separator +
                    "image.jpg");
            filepath = file.getAbsolutePath();
            Log.d(TAG, "onActivityResult: "+file.getAbsolutePath());

            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                //Landscape do some stuff
                source = "landscape";
                bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 768, 1024);
                img.setImageBitmap(bitmap);
                img.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            else{
                //portrait
                bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1024 , 768);
                //rotates img
                source = "portrait";
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                img.setImageBitmap(bitmap2);
                img.setScaleType(ImageView.ScaleType.FIT_XY);
            }



        }
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
    { // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight)
        {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth)
        {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnSave:
                if(etname.getText().toString().equals("") || etlastname.getText().toString().equals("") || etphone.getText().toString().equals("")){
                    Toast.makeText(this, "Name, last name, and phone are required", Toast.LENGTH_SHORT).show();
                }
                else {
                    byte[] b = null;
                    if (bitmap != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                        b = stream.toByteArray();
                    }
                    MyContact contact = new MyContact(-1, etname.getText().toString(), etlastname.getText().toString(),
                            etphone.getText().toString(), etemail.getText().toString(), company.getText().toString(), address.getText().toString(), b);
                    Log.d(TAG, "onClick: " + contact.getName() + " " + contact.getPhone());
                    try {
                        if (Integer.parseInt(id) >= 0) {
                            databaseHelper.uploadNewContact(contact, id);
                            Toast.makeText(this, "Element updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            databaseHelper.saveNewContact(contact);
                            Toast.makeText(this, "Element created successfully", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        databaseHelper.saveNewContact(contact);
                        Toast.makeText(this, "Element created successfully", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.btnDel:
                try {
                    if (Integer.parseInt(id) >= 0) {
                        databaseHelper.DeleteContact(id);
                        bitmap = null;
                        etname.setText("");
                        etlastname.setText("");
                        etphone.setText("");
                        etemail.setText("");
                        company.setText("");
                        address.setText("");
                        img.setImageResource(R.drawable.ic_person);
                        Toast.makeText(this, "Element deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception ex){}

                break;
            case R.id.img:
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
                break;
        }
    }
}
