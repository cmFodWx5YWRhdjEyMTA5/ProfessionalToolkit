package health.app.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import health.app.Model.Profile;
import health.app.R;
import health.app.Response.SignUpResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.HealthApp;
import health.app.Utilities.LocationActivity;
import health.app.Utilities.LocationUpdater;
import health.app.Utilities.Preferences;
import health.app.Utilities.ValidationUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TrainerSignUpActivity extends LocationActivity implements LocationUpdater {

    MultipartBody.Part profileImgBody1;
    public static Profile profile;
    public static byte[] data=null;
    Bitmap bm;
    File file=null;
    Uri uriProfileImage = null;
    int REQUEST_CAMERA = 2, SELECT_FILE = 1;
    private static final int RC_SIGN_IN = 0;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    String deviceToken;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_sign_up);
        ButterKnife.bind(this);
        final Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/Poppins-SemiBold.ttf");
        btSubmit.setTypeface(font);
        Typeface typeface_temp = inputPassword.getTypeface();
        inputPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        inputPassword.setTypeface(typeface_temp);
        passwordLayout.setTypeface(typeface_temp);
        Typeface typeface_temp1 = inputConPassword.getTypeface();
        inputConPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        inputConPassword.setTypeface(typeface_temp1);
        passwordConLayout.setTypeface(typeface_temp1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF7010"));
        }
        profile =new Profile();
        if (deviceToken==null) {
            deviceToken = FirebaseInstanceId.getInstance().getToken();
        }
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Trainer SignUp");
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
    }

    @Override
    public void onCurrentLocationAvailaible(LatLng latLng) {
        double latitude=latLng.latitude;
        double longitude=latLng.longitude;
        Preferences.getInstance().setLongitude(String.valueOf(longitude));
        Preferences.getInstance().setLatitude(String.valueOf(latitude));
        Log.d("LatLng","LatLng"+latitude+"v"+longitude);
        HealthApp.getInstance().setCurrentLatng(latLng);
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
//                    Intent intent = new Intent(
//                            Intent.ACTION_PICK,
//                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    intent.setType("image/*");
//                    startActivityForResult(
//                            Intent.createChooser(intent, "Select File"),
//                            SELECT_FILE);
                    Intent gallery = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    gallery.setType("image/*");
                    startActivityForResult(gallery, SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


//    private void onSelectFromGalleryResult(Intent data) {
//        if (data == null) {
//            Log.e("sfd", "A");
//        } else {
//
//            Uri selectedImageUri = data.getData();
//            Bitmap correctBmp = null;
//            String[] projection = {MediaStore.MediaColumns.DATA};
//            Cursor cursor = this.managedQuery(selectedImageUri, projection, null, null,
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

    private void onCaptureImageResult(Intent data) {
        bm = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

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

    private File convertStringToByteToFile(String path) {
        FileOutputStream fos;
        File file_image = new File(getApplicationContext().getCacheDir(), "Sibi" + System.currentTimeMillis() + ".png");

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

    public boolean validation() {
        if (profileImage.getDrawable().getConstantState() == getResources().getDrawable( R.drawable.usern).getConstantState())
        {
            Snackbar snackbar = Snackbar.make(container, "Please Add Profile Image.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (inputFirstName.getText().toString().trim().length() == 0) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter First Name.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (inputLastName.getText().toString().trim().length() == 0) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Last Name.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (inputEmail.getText().toString().trim().length() == 0) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Email.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (!ValidationUtils.isValidEmail(inputEmail.getText())) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Valid Email.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
//        else if (inputPhoneno.getText().toString().trim().length() == 0) {
//            Snackbar snackbar = Snackbar.make(container, "Please Enter Phone Number.", Snackbar.LENGTH_LONG);
//            snackbar.getView().setBackgroundColor(Color.RED);
//            snackbar.show();
//            return false;
//        }
        else if (inputPassword.getText().toString().trim().length() == 0) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Password.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (inputPassword.getText().toString().trim().length()<6) {
            Snackbar snackbar = Snackbar.make(container, "Your password must be 6 characters long.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (inputConPassword.getText().toString().trim().length() == 0) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Confirm Password.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
//        else if (inputConPassword.getText().toString().trim().length()<6) {
//            Snackbar snackbar = Snackbar.make(container, "Your password must be 6 characters long.", Snackbar.LENGTH_LONG);
//            snackbar.getView().setBackgroundColor(Color.RED);
//            snackbar.show();
//            return false;
//        }
        else if(!inputPassword.getText().toString().trim().matches(inputConPassword.getText().toString().trim())){
            Snackbar snackbar = Snackbar.make(container, "Your confirm password not match with new password.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if(inputTrainerId.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Trainer ID.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }

        return true;
    }

    @OnClick(R.id.upload_img)
    void uploadImage() {
         selectImage();
    }

    @BindView(R.id.input_trainer_id)
    EditText inputTrainerId;

    @BindView(R.id.bt_submit)
    Button btSubmit;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_submit)
    void next() {
        if (validation()) {
            trainerSignUp();
        }
    }

    public RequestBody requestBody(String name) {
        return RequestBody.create(MediaType.parse("text/plain"), name);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void trainerSignUp(){
        //File file1 = HealthApp.getInstance().reduceImageSize(file);
        Log.d("FILE","FILE"+file);
        final RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        profileImgBody1 = MultipartBody.Part.createFormData("profile_image", file.getName(), requestBody1);
        final Map<String, RequestBody> field = new HashMap<String, RequestBody>();
        field.put("first_name", requestBody(inputFirstName.getText().toString()));
        field.put("last_name", requestBody(inputLastName.getText().toString()));
        field.put("email", requestBody(inputEmail.getText().toString()));
        field.put("phone", requestBody(inputPhoneno.getText().toString()));
        field.put("password", requestBody(inputPassword.getText().toString()));
        field.put("UniqueId",requestBody(inputTrainerId.getText().toString()));
        if(Preferences.getInstance().getDevice_token()!=null) {
            field.put("token", requestBody(Preferences.getInstance().getDevice_token()));
        }
        field.put("token_type",requestBody("android_token"));
        if(Preferences.getInstance().getLongitude()!=null) {
            field.put("longitude", requestBody(Preferences.getInstance().getLongitude()));
        }
        if(Preferences.getInstance().getLatitude()!=null) {
            field.put("latitude", requestBody(Preferences.getInstance().getLatitude()));
        }
        field.put("user_type", requestBody("Trainer"));
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Sending request...");
        healthAppService.signUpTrainer(profileImgBody1,field).
                enqueue(new Callback<SignUpResponse>() {
                    @Override
                    public void onResponse(Call<SignUpResponse> call, final Response<SignUpResponse> response) {
                        if (response.body().isStatus()) {
                            setPreferences(response.body().getData());
                            startActivity(new Intent(getApplicationContext(),TrainerDashboardActivity.class));
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            finish();
                        }
                        else {
                            alertUser(response.body().getMsg());
                        }

                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<SignUpResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), HealthApp.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void alertUser(String msg) {
        final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(TrainerSignUpActivity.this);
        alertDialogBuilder.setTitle(getString(R.string.app_name));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setIcon(R.drawable.ptlogo);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void setPreferences(SignUpResponse.SignUpData data) {
        Preferences.getInstance().setLogIn(true);
        Preferences.getInstance().setUserId(data.getUser().getId());
        Preferences.getInstance().setLongitude(data.getUser().getLongitude());
        Preferences.getInstance().setLatitude(data.getUser().getLatitude());
        Preferences.getInstance().setFirstName(data.getUser().getFirst_name());
        Preferences.getInstance().setLastName(data.getUser().getLast_name());
        Preferences.getInstance().setMobile(data.getUser().getPhone());
        Preferences.getInstance().setEmail(data.getUser().getEmail());
        Preferences.getInstance().setGender(data.getUser().getGender());
        Preferences.getInstance().setAge(data.getUser().getAge());
        Preferences.getInstance().setWeight(data.getUser().getWeight());
        Preferences.getInstance().setHeight(data.getUser().getHeight());
        Preferences.getInstance().setBodyFat(data.getUser().getFat());
        Preferences.getInstance().setUserPhotoPath(data.getUser().getProfile_image_path());
        Preferences.getInstance().setUserType(data.getUser().getUser_type());
        Preferences.getInstance().setTrainingType(data.getUser().getTraining_type());
        Preferences.getInstance().setBioData(data.getUser().getShort_bio());
        Preferences.getInstance().setUniqueId(data.getUser().getUniqueId());
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.profileImage)
    CircleImageView profileImage;

    @BindView(R.id.input_layout_pwd)
    TextInputLayout passwordLayout;

    @BindView(R.id.input_layout_con_pwd)
    TextInputLayout passwordConLayout;

    @BindView(R.id.first_name)
    EditText inputFirstName;

    @BindView(R.id.last_name)
    EditText inputLastName;

    @BindView(R.id.input_phoneno)
    EditText inputPhoneno;

    @BindView(R.id.input_email)
    EditText inputEmail;

    @BindView(R.id.input_pwd)
    EditText inputPassword;

    @BindView(R.id.input_con_pwd)
    EditText inputConPassword;

    @Override
    public void locationUpdate(LatLng latLng) {
        double latitude=latLng.latitude;
        double longitude=latLng.longitude;
        Preferences.getInstance().setLongitude(String.valueOf(longitude));
        Preferences.getInstance().setLatitude(String.valueOf(latitude));
        HealthApp.getInstance().setCurrentLatng(latLng);
        if ((HealthApp.getInstance()).getAppLatLng() == null)
            (HealthApp.getInstance()).setAppLatLng(latLng);
    }
}