    package com.example.shiftroster;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import static android.content.ContentValues.TAG;

    public class AdminActivity extends AppCompatActivity {

    Button btn_logout, select_file, btn_upload;
    TextView txt_file_path;
    Intent mytFileIntent;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btn_logout = findViewById(R.id.btn_logout);
        btn_upload = findViewById(R.id.upload_to_FB);
        select_file = findViewById(R.id.btn_file_select);
        txt_file_path = findViewById(R.id.file_path);

        firebaseAuth =FirebaseAuth.getInstance();

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingBar loadingBar = new LoadingBar(AdminActivity.this);
                loadingBar.showAlertDialog();
                firebaseAuth.signOut();
                startActivity(new Intent(AdminActivity.this, LoginPage.class));
                finish();
            }
        });

        select_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mytFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                mytFileIntent.setType("*/*");
                startActivityForResult(mytFileIntent, 10);

            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminActivity.this, "Your doc will be uploaded soon...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case 10:

                if (resultCode == RESULT_OK) {

//                    Uri selectedImageURI = data.getData();
//                    String realPath = getRealPathFromURI(getApplicationContext(),selectedImageURI);

//                    Uri selectedImageUri = data.getData();
//                    String s = getRealPathFromURI(selectedImageUri);
//                    Toast.makeText(this, "lococo     ->"+selectedImageUri.getPath()+"<-", Toast.LENGTH_SHORT).show();
//                    txt_file_path.setText(s);
                   String realPath = data.getData().getPath();
                    txt_file_path.setText(realPath);
                }
                else{
                    Toast.makeText(this, "Helloo Erororororo", Toast.LENGTH_SHORT).show();
                }
                break;
//            default:
//                Toast.makeText(this, "Defaultttttttttttttttttt", Toast.LENGTH_SHORT).show();
        }

    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if(cursor!= null) {
                cursor.moveToFirst();
            }
            return cursor.getString(column_index);
        } catch (Exception e) {
            Log.e(TAG, "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

//    private String getRealPathFromURI(Uri selectedImageUri) {
//        String filePath;
//        Cursor cursor = getContentResolver().query(selectedImageUri, null, null, null, null);
//        if (cursor == null) {
//            filePath = selectedImageUri.getPath();
//        } else {
//            cursor.moveToFirst();
//            int idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
//            filePath = cursor.getString(idx);
//            cursor.close();
//        }
//        return filePath;
//    }

//    private String getRealPathFromURI(Uri selectedImageUri) {
//
//        String[] projection = { MediaStore.Images.Media.DATA };
//        @SuppressWarnings("deprecation")
//        Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
//        int column_index = cursor
//                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }
}