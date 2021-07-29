package com.example.adduserwithpagerview;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddUserFragment extends Fragment {
    View view;
    EditText etAddname,etAddmobile,etAddob;
    CircleImageView addImage;
    Spinner addSpinner;
    Button btUserSave;
    int CAM_REQUEST=111,genderpos=0;
    Bitmap captureImage;
    DatePickerDialog datepicker;
    int birthDate,birthMonth,birthYear,genderPosition=0;
    byte[] saveUserImage=null;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.adduser_fragment,container,false);

        etAddname=view.findViewById(R.id.etfrg_addusr_name);
        etAddmobile=view.findViewById(R.id.etfrg_addusr_mobile);
        etAddob=view.findViewById(R.id.etfrg_addusr_dob);
        addImage=view.findViewById(R.id.etadd_user_image);
        addSpinner=view.findViewById(R.id.etspfrg_add_usr);
        btUserSave=view.findViewById(R.id.bt_add_usr);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getActivity(),R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addSpinner.setAdapter(adapter);
        addSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                genderPosition=i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camintent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camintent,CAM_REQUEST);

            }
        });

       etAddob.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Calendar calendar=Calendar.getInstance();
                birthDate=calendar.get(Calendar.DATE);
                birthMonth=calendar.get(Calendar.MONTH);
                birthYear=calendar.get(Calendar.YEAR);
               datepicker=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker datePicker, int year, int moth, int date) {
                       moth=moth+1;
                       String selDate=date + "/" + moth + "/" + year;
                        etAddob.setText(selDate);
                   }
               },birthDate,birthMonth,birthYear);

                datepicker.show();
           }
       });

       btUserSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               saveuser();
           }
       });

       return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==111){
            captureImage=(Bitmap) data.getExtras().get("data");
            addImage.setImageBitmap(captureImage);
        }
    }

    public void saveuser(){
        String saveUserName=etAddname.getText().toString().trim();
        String saveUserMobile=etAddmobile.getText().toString().trim();
        String saveUserDob=etAddob.getText().toString().trim();
        String saveUserGender=addSpinner.getSelectedItem().toString().trim();
        if (captureImage!=null) {
            saveUserImage = ImageConverter.imageToByteArray(captureImage);
        }

        if (saveUserName.isEmpty()){
            etAddname.setError(getString(R.string.name_missing_warning));
        }
        else if (saveUserMobile.isEmpty()){
            etAddmobile.setError(getString(R.string.mobno_missing_warning));}

        else if (saveUserDob.isEmpty()){
            etAddob.setError(getString(R.string.name_missing_warning));}

//        else if (genderpos==0){
//            Snackbar snackbar=Snackbar.make(view,getString(R.string.gen_missing_warning),Snackbar.LENGTH_LONG);
//           snackbar.show();
//       }
        else {
            Saveuser user=new Saveuser(saveUserName,saveUserMobile,saveUserDob,saveUserGender,saveUserImage);
            user.execute();
        }
        }

    class Saveuser extends AsyncTask<Void,Void,Void>{

        String saveAddusername,saveAdduserMobile,saveaddUserdob,saveUserGender;
        byte[] saveUserImage;

        public Saveuser(String saveAddusername, String saveAdduserMobile, String saveaddUserdob, String saveUserGender, byte[] saveUserImage) {
            this.saveAddusername = saveAddusername;
            this.saveAdduserMobile = saveAdduserMobile;
            this.saveaddUserdob = saveaddUserdob;
            this.saveUserGender = saveUserGender;
            this.saveUserImage = saveUserImage;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            UserEntity entity= new UserEntity();
            entity.setUser_name(saveAddusername);
            entity.setUser_mobileno(saveAdduserMobile);
            entity.setUser_dob(saveaddUserdob);
            entity.setGender(saveUserGender);
            entity.setImage(saveUserImage);
            UserRoomDatabaseClient.getInstance(getContext()).getUserDatabase().userDao().insert(entity);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            Snackbar snackbar=Snackbar.make(view,getString(R.string.use_added),Snackbar.LENGTH_LONG);
            snackbar.show();
            etAddname.setText("");
            etAddmobile.setText("");
            etAddob.setText("");
            addSpinner.setSelection(0);
            addImage.setImageResource(R.drawable.ic_baseline_person_24);
        }
    }
}

