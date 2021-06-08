package ru.ubunterro.servicehelper.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.Toast;

import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import ru.ubunterro.servicehelper.R;
import ru.ubunterro.servicehelper.engine.DBAgent;
import ru.ubunterro.servicehelper.engine.SettingsManager;
import ru.ubunterro.servicehelper.engine.Storage;
import ru.ubunterro.servicehelper.engine.VolleyMultipartRequest;
import ru.ubunterro.servicehelper.models.Part;

public class WarehouseItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_item);

        Intent intent = getIntent();
        int partId = intent.getExtras().getInt("id");

        EditText editTextTextPartName = findViewById(R.id.editTextTextPartName);
        ImageView imageViewPartBig = findViewById(R.id.imageViewPartBig);
        EditText editTextPartAmount = findViewById(R.id.editTextPartAmount);
        EditText editTextPartDesc = findViewById(R.id.editTextPartDesc);
        EditText editTextTextPartSN = findViewById(R.id.editTextTextPartSN);

        Part part = Storage.getPart(partId);

        editTextTextPartName.setText(part.getName());
        editTextPartAmount.setText(part.getFormattedAmount());
        editTextPartDesc.setText(part.getDescription());
        editTextTextPartSN.setText(part.getSerialNumber());

        editTextPartDesc.setScroller(new Scroller(getApplicationContext()));
        editTextPartDesc.setVerticalScrollBarEnabled(true);
        editTextPartDesc.setMinLines(10);
        editTextPartDesc.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_FLAG_MULTI_LINE |
                InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        // тупой хак чтобы не позволить загрузку файла при создании
        if (partId == -1)  {
            imageViewPartBig.setVisibility(View.GONE);
        } else {
            if (part.getPhoto().isEmpty()){
                Glide.with(getApplicationContext()).load(
                        SettingsManager.getServer(getApplicationContext()) + "/storage/partImg" + part.getId() + ".png")
                        .into(imageViewPartBig);
            } else {
                Glide.with(getApplicationContext()).load(part.getPhoto()).into(imageViewPartBig);
            }
        }




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intentSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intentSettings);
            return;
        }

        imageViewPartBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if everything is ok we will open image chooser
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView imageViewPartBig = findViewById(R.id.imageViewPartBig);
        Intent intent = getIntent();
        int partId = intent.getExtras().getInt("id");

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                //displaying selected image to imageview
                imageViewPartBig.setImageBitmap(bitmap);

                //calling the method uploadBitmap to upload image
                //DBAgent.uploadBitmap(bitmap, partId);
                Storage.uploadingImage = bitmap;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void saveButton(View view){
        EditText editTextTextPartName = findViewById(R.id.editTextTextPartName);
        ImageView imageViewPartBig = findViewById(R.id.imageViewPartBig);
        EditText editTextPartAmount = findViewById(R.id.editTextPartAmount);
        EditText editTextPartDesc = findViewById(R.id.editTextPartDesc);
        EditText editTextTextPartSN = findViewById(R.id.editTextTextPartSN);

        Intent intent = getIntent();
        int partId = intent.getExtras().getInt("id");

        Part part = Storage.getPart(partId);

        try {
            part.setAmount(Double.parseDouble(editTextPartAmount.getText().toString()));
        } catch (NumberFormatException e){
            Log.d("SHLP", "Double parsing error");
        }

        part.setDescription(editTextPartDesc.getText().toString());
        part.setName(editTextTextPartName.getText().toString());
        part.setSerialNumber(editTextTextPartSN.getText().toString());

        Log.d("SHLP", "PartId " + Integer.toString(partId));

        if(Storage.uploadingImage != null) {
            DBAgent.uploadBitmap(Storage.uploadingImage, partId);
            Storage.uploadingImage = null;
            part.setPhoto(SettingsManager.getServer(getApplicationContext()) + "/storage/partImg/" + part.getId() + ".png");


        }


        if (partId == -1){
            DBAgent.createPart(part);
        } else {
            DBAgent.setPartInfo(part);
        }





        /*Glide.with(getApplicationContext()).load(
                SettingsManager.getServer(getApplicationContext()) + "/storage/partImg" + part.getId() + ".png")
                .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
                .into(imageViewPartBig);
       */



        Toast.makeText(getApplicationContext(), "Сохранено", Toast.LENGTH_LONG).show();

        finish();



    }
}