package health.app.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.Model.Profile;
import health.app.R;
import health.app.Response.ProfileDataResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CustomerProfile1Activity extends BaseActivity {
    public  File file=null;
    Bitmap bm;
    String userId;
    String age,weight,height,fat,calf,thigh,chest,arm,hip,waist;
    private static final int RC_SIGN_IN = 0;
    public static byte[] data=null;
    private int REQUEST_CAMERA = 1, SELECT_FILE = 2;
    public static Profile cprofile;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile1);
        ButterKnife.bind(this);
        cprofile =new Profile();
        if (getIntent().getStringExtra("customerId")!=null)
        {
            userId=getIntent().getStringExtra("customerId");
        }
        Preferences.getInstance().getUserPhotoPath();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF7010"));
        }
        final TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = (getResources().getDrawable(R.mipmap.backicon2x));
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer"))
        {
            title.setText("View Profile");
            getCustomerData(userId);
            inputFName.setFocusable(false);
            inputFName.setClickable(false);
            inputlName.setFocusable(false);
            inputlName.setClickable(false);
            inputEmail.setFocusable(false);
            inputEmail.setClickable(false);
            inputPhoneno.setFocusable(false);
            inputPhoneno.setClickable(false);
            uploadImg.setClickable(false);
            uploadImageIcon.setVisibility(View.GONE);
        }
        else if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer")){
            title.setText("My Profile");
            getCustomerData(Preferences.getInstance().getUserId());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getCustomerData(String userId) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.getCustomerData(userId).enqueue(new Callback<ProfileDataResponse>() {
            @Override
            public void onResponse(Call<ProfileDataResponse> call, Response<ProfileDataResponse> response) {
                if (response.body().isStatus()) {
                    if (response.body().getProfileData()!=null)
                    {
                        setCustomerData(response.body().getProfileData());
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),response.body().getMsg() , Toast.LENGTH_SHORT).show();
                }
                hideProgressBar();

            }

            @Override
            public void onFailure(Call<ProfileDataResponse> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCustomerData(ProfileDataResponse.ProfileData profileData) {
        Picasso.with(profileImage.getContext()).load(profileData.getUser().getProfile_image()).into(profileImage);
        inputFName.setText(profileData.getUser().getFirst_name());
        inputlName.setText(profileData.getUser().getLast_name());
        inputEmail.setText(profileData.getUser().getEmail());
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer")) {
            inputEmail.setEnabled(false);
        }
        else {
            inputEmail.setEnabled(true);
        }
        inputPhoneno.setText(profileData.getUser().getPhone());
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer")) {
            inputGender.setText(profileData.getUser().getGender());
        }
        else {
            inputGender.setText(profileData.getUser().getGender());
            inputGender.setFocusable(false);
            inputGender.setClickable(false);
            inputGender.setFocusableInTouchMode(false);
        }
        age=profileData.getUser().getAge();
        weight=profileData.getUser().getWeight();
        height=profileData.getUser().getHeight();
        fat=profileData.getUser().getFat();
        calf=profileData.getUser().getCalf();
        thigh=profileData.getUser().getThigh();
        chest=profileData.getUser().getChest();
        arm=profileData.getUser().getArm();
        hip=profileData.getUser().getHips();
        waist=profileData.getUser().getWaist();
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CustomerProfile1Activity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    try {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CAMERA);
                    } catch (Exception e) {

                    }
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    public void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            Log.e("resolveerror", "onActivityResult");
        }
        if (requestCode == SELECT_FILE)
            try {
                onSelectFromGalleryResult(intent.getData());
            } catch (Exception e) {

            }
        else if (requestCode == REQUEST_CAMERA)
            try {
                onCaptureImageResult(intent);
            } catch (Exception e) {
            }

    }

    private void onCaptureImageResult(Intent data) {
        bm = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        profileImage.setImageBitmap(bm);
        if (bm != null) {
            file = convertStringToByteToFile(bitmapToString(bm));

        }
    }

    @SuppressWarnings("deprecation")
//    private void onSelectFromGalleryResult(Intent data) {
//
//        if (data == null) {
//            Log.e("sfd", "A");
//        } else {
//
//            Uri selectedImageUri = data.getData();
//            Bitmap correctBmp = null;
//            String[] projection = {MediaStore.MediaColumns.DATA};
//            Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
//                    null);
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//            cursor.moveToFirst();
//
//            String selectedImagePath = cursor.getString(column_index);
//
//            try {
//
//                ExifInterface exif = new ExifInterface(selectedImagePath);
//                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//
//                int angle = 0;
//
//                if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
//                    angle = 90;
//                } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
//                    angle = 180;
//                } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
//                    angle = 270;
//                }
//
//                Matrix mat = new Matrix();
//                mat.postRotate(angle);
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inJustDecodeBounds = true;
//                BitmapFactory.decodeFile(selectedImagePath, options);
//                final int REQUIRED_SIZE = 200;
//                int scale = 1;
//                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
//                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
//                    scale *= 2;
//                options.inSampleSize = scale;
//                options.inJustDecodeBounds = false;
//                bm = BitmapFactory.decodeFile(selectedImagePath, options);
//
//                correctBmp = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mat, true);
//            } catch (IOException e) {
//                Log.w("TAG", "-- Error in setting image");
//            } catch (OutOfMemoryError oom) {
//                Log.w("TAG", "-- OOM Error in setting image");
//            }
//            profileImage.setImageBitmap(correctBmp);
//            if (correctBmp != null) {
//                file = convertStringToByteToFile(bitmapToString(correctBmp));
//
//            }
//
//        }
//    }

    private void onSelectFromGalleryResult(Uri uri) {
        if (uri!=null) {
            ParcelFileDescriptor parcelFD = null;
            try {
                parcelFD = getContentResolver().openFileDescriptor(uri, "r");
                FileDescriptor imageSource = parcelFD.getFileDescriptor();
                // Decode image size
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(imageSource, null, o);
                // the new size we want to scale to
                final int REQUIRED_SIZE = 1024;
                // Find the correct scale value. It should be the power of 2.
                int width_tmp = o.outWidth, height_tmp = o.outHeight;
                int scale = 1;
                while (true) {
                    if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) {
                        break;
                    }
                    width_tmp /= 2;
                    height_tmp /= 2;
                    scale *= 2;
                }
                // decode with inSampleSize
                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize = scale;
                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(imageSource, null, o2);
                profileImage.setImageBitmap(bitmap);
                if (bitmap != null) {
                    file = convertStringToByteToFile(bitmapToString(bitmap));

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private File convertStringToByteToFile(String path) {
        FileOutputStream fos;
        File file_image = new File(getCacheDir(), "Sibi" + System.currentTimeMillis() + ".png");

        try {
            file_image.createNewFile();
            fos = new FileOutputStream(file_image);
            byte[] encodeByte = Base64.decode(path, Base64.DEFAULT);
            fos.write(encodeByte);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file_image;
    }

    private String bitmapToString(Bitmap in) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        in.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        return Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);
    }

    public void addValues(){
        cprofile.setCtrainerImage(file);
        cprofile.setCfName(inputFName.getText().toString());
        cprofile.setClName(inputlName.getText().toString());
        cprofile.setCemail(inputEmail.getText().toString());
        cprofile.setCphoneNum(inputPhoneno.getText().toString());
        cprofile.setCgender(inputGender.getText().toString());
    }



    public boolean validation(){
        if (inputFName.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter First Name.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        if (inputlName.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Last Name.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (inputEmail.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Email.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
//        else if (inputPhoneno.getText().toString().trim().length()==0){
//            Snackbar snackbar = Snackbar.make(container, "Please Enter Phone Number.", Snackbar.LENGTH_LONG);
//            snackbar.getView().setBackgroundColor(Color.RED);
//            snackbar.show();
//            return false;
//        }
        else if (inputGender.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Select Gender.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (profileImage.getDrawable().getConstantState() == getResources().getDrawable( R.drawable.usern).getConstantState())
        {
            Snackbar snackbar = Snackbar.make(container, "Please Add Profile Image.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }


        return true;
    }

    public static void showDialog(Context c, String title, final String[] array,final onGenderSelected target) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(title);
        builder.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                target.genderSelection(i);
                dialogInterface.dismiss();
            }
        });

        builder.show();


    }
    public interface onGenderSelected{
        public void genderSelection(int position);
    }

    @OnClick(R.id.upload_img)
    void uploadImage() {selectImage();}

    @OnClick(R.id.input_gender)
    void genderSelect(){
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer")) {
            showDialog(CustomerProfile1Activity.this, "Select Gender", getResources().getStringArray(R.array.Gender), new onGenderSelected() {
                @Override
                public void genderSelection(int position) {
                    inputGender.setText(getResources().getStringArray(R.array.Gender)[position]);
//       Toast.makeText(CustomerProfile1Activity.this,getResources().getStringArray(R.array.Gender)[position],Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {

        }
    }

    @OnClick(R.id.bt_next)
    void next(){if (validation()) {
            addValues();
            Intent intent=new Intent(getApplicationContext(),CustomerProfile2Activity.class);
            intent.putExtra("age",age);
            intent.putExtra("weight",weight);
            intent.putExtra("height",height);
            intent.putExtra("fat",fat);
            intent.putExtra("calf",calf);
            intent.putExtra("thigh",thigh);
            intent.putExtra("chest",chest);
            intent.putExtra("arm",arm);
            intent.putExtra("hip",hip);
            intent.putExtra("waist",waist);
            startActivity(intent);
        }}

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.upload_image_icon)
    ImageView uploadImageIcon;

    @BindView(R.id.input_gender)
    EditText inputGender;

    @BindView(R.id.profile_image)
    ImageView profileImage;

    @BindView(R.id.input_first_name)
    EditText inputFName;

    @BindView(R.id.input_last_name)
    EditText inputlName;

    @BindView(R.id.input_email)
    EditText inputEmail;

    @BindView(R.id.input_phoneno)
    EditText inputPhoneno;

    @BindView(R.id.upload_img)
    FrameLayout uploadImg;

    @BindView(R.id.container)
    LinearLayout container;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}

