package health.app.Activities;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
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

import com.dinuscxj.progressbar.CircleProgressBar;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import health.app.Model.Profile;
import health.app.R;
import health.app.Response.ProfileDataResponse;
import health.app.Retrofit.HealthAppService;
import health.app.Retrofit.RestClient;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.HealthApp;
import health.app.Utilities.MultiSpinner;
import health.app.Utilities.Preferences;
import health.app.Utilities.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TrainerProfile1Activity extends BaseActivity implements MultiSpinner.OnMultipleItemsSelectedListener{
    public  File file;
    private static final int RC_SIGN_IN = 0;
    private String userChoosenTask;
    Bitmap bm;
    int pStatus = 0;
    private Handler handler = new Handler();
    TextView tv;
    String age,bio;
    String training;
    CircleProgressBar mCustomProgressBar5;
    public static byte[] data=null;
    private int REQUEST_CAMERA = 1, SELECT_FILE = 2;
    public static Profile profile;
    String userId;
    public static Activity fa;
    String [] training_type={"Squat","Leg press","Lunge","Deadlift","Leg extension","Leg curl","Standing calf raise","Seated calf raise","Hip adductor","Chest fly","Push-up","Pull-down","Pull-up","Bent-over row","Upright row","Shoulder press","Shoulder fly","Lateral raise","Shoulder shrug","Pushdown","Triceps extension","Biceps curl","Crunch","Russian twist","Leg raise","Back extension"};
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_profile1);
        ButterKnife.bind(this);
        fa = this;
        if (getIntent().getStringExtra("UserId")!=null)
        {
            userId=getIntent().getStringExtra("UserId");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF7010"));
        }
        multiselectSpinner.setItems(training_type);
        multiselectSpinner.setListener(this);
        profile =new Profile();
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        mCustomProgressBar5 = (CircleProgressBar) toolbar.findViewById(R.id.custom_progress5);
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
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Customer"))
        {
            title.setText("View Profile");
            getTrainerData(userId);
            fName.setFocusable(false);
            fName.setClickable(false);
            lName.setFocusable(false);
            lName.setClickable(false);
            emailAddress.setFocusable(false);
            emailAddress.setClickable(false);
            phoneNumber.setFocusable(false);
            phoneNumber.setClickable(false);
            trainingType.setFocusable(false);
            trainingType.setClickable(false);
            multiselectSpinner.setEnabled(false);
            multiselectSpinner.setVisibility(View.GONE);
            trainingType.setFocusableInTouchMode(false);
            uploadImg.setClickable(false);
            uploadImageIcon.setVisibility(View.GONE);
        }
       else if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer")){
            title.setText("My Profile");
            getTrainerData(Preferences.getInstance().getUserId());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getTrainerData(String userId) {
        final HealthAppService healthAppService = RestClient.getClient().create(HealthAppService.class);
        showProgressbar("Loading", "Please wait...");
        healthAppService.getTrainerData(userId).enqueue(new Callback<ProfileDataResponse>() {
            @Override
            public void onResponse(Call<ProfileDataResponse> call, Response<ProfileDataResponse> response) {
                if (response.body().isStatus()) {
                    if (response.body().getProfileData()!=null)
                    {
                        Picasso.with(profileImage.getContext()).load(response.body().getProfileData().getUser().getProfile_image()).into(profileImage);
                        fName.setText(response.body().getProfileData().getUser().getFirst_name());
                        lName.setText(response.body().getProfileData().getUser().getLast_name());
                        tvTrainerId.setText(response.body().getProfileData().getUser().getUniqueId());
                        emailAddress.setText(response.body().getProfileData().getUser().getEmail());
                        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer")) {
                            emailAddress.setEnabled(false);
                        }
                        else {
                            emailAddress.setEnabled(true);
                        }
                        phoneNumber.setText(response.body().getProfileData().getUser().getPhone());
                        training=response.body().getProfileData().getUser().getTraining_type();
                        String[] ary = training.split(",");
                        Log.d("ary","ary"+ary.length);
                        multiselectSpinner.setSelection(ary);
                        trainingType.setText(training);
                        Log.d("trainingType","trainingType"+trainingType.getText().toString());
                        age=response.body().getProfileData().getUser().getAge();
                        bio=response.body().getProfileData().getUser().getShort_bio();
                        Log.d("data","data"+training+age+bio);
                        simulateProgress(trainingType.getText().toString(),age,bio);
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
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

    private void simulateProgress(String training,String age, String bio) {
        if (training.length()==0 && age.length()==0 && bio.length()==0) {
            ValueAnimator animator = ValueAnimator.ofInt(0, 70);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int progress = (int) animation.getAnimatedValue();
                    mCustomProgressBar5.setProgress(progress);
                }
            });

            animator.setDuration(2000);
            animator.start();
        }
        else if (training.length()==0 && age.length()!=0 && bio.length()==0) {
            ValueAnimator animator = ValueAnimator.ofInt(0, 80);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int progress = (int) animation.getAnimatedValue();
                    mCustomProgressBar5.setProgress(progress);
                }
            });

            animator.setDuration(2000);
            animator.start();
        }
        else if (training.length()==0 && age.length()==0 && bio.length()!=0) {
            ValueAnimator animator = ValueAnimator.ofInt(0, 80);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int progress = (int) animation.getAnimatedValue();
                    mCustomProgressBar5.setProgress(progress);
                }
            });

            animator.setDuration(2000);
            animator.start();
        }
        else if (training.length()!=0 && age.length()==0 && bio.length()==0) {
            ValueAnimator animator = ValueAnimator.ofInt(0, 80);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int progress = (int) animation.getAnimatedValue();
                    mCustomProgressBar5.setProgress(progress);
                }
            });

            animator.setDuration(2000);
            animator.start();
        }
        else if (age.length()==0 && bio.length()==0) {
            ValueAnimator animator = ValueAnimator.ofInt(0, 80);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int progress = (int) animation.getAnimatedValue();
                    mCustomProgressBar5.setProgress(progress);
                }
            });

            animator.setDuration(2000);
            animator.start();
        }
        else if (age.length()==0 || bio.length()==0) {
            ValueAnimator animator = ValueAnimator.ofInt(0, 90);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int progress = (int) animation.getAnimatedValue();
                    mCustomProgressBar5.setProgress(progress);
                }
            });

            animator.setDuration(2000);
            animator.start();
        }
        else if (training.length()==0 && age.length()!=0 && bio.length()!=0) {
            ValueAnimator animator = ValueAnimator.ofInt(0, 90);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int progress = (int) animation.getAnimatedValue();
                    mCustomProgressBar5.setProgress(progress);
                }
            });

            animator.setDuration(2000);
            animator.start();
        }
        else if (training.length()!=0 && age.length()!=0 && bio.length()!=0) {
            ValueAnimator animator = ValueAnimator.ofInt(0, 100);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int progress = (int) animation.getAnimatedValue();
                    mCustomProgressBar5.setProgress(progress);
                }
            });

            animator.setDuration(2000);
            animator.start();
        }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {

                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(TrainerProfile1Activity.this);
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
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
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


    public void addValues(){
        profile.setTtrainerImage(file);
        profile.setTfirstName(fName.getText().toString());
        profile.setTlastName(lName.getText().toString());
        profile.setTemail(emailAddress.getText().toString());
        profile.setTphoneNum(phoneNumber.getText().toString());
        profile.setTtrainingType(trainingType.getText().toString());
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
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

    public boolean validation(){
        if (fName.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter First Name.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (lName.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Last Name.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (emailAddress.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Email Address.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
//        else if (phoneNumber.getText().toString().trim().length()==0){
//            Snackbar snackbar = Snackbar.make(container, "Please Enter Phone Number.", Snackbar.LENGTH_LONG);
//            snackbar.getView().setBackgroundColor(Color.RED);
//            snackbar.show();
//            return false;
//        }
        else if (profileImage.getDrawable().getConstantState() == getResources().getDrawable( R.drawable.usern).getConstantState())
        {
            Snackbar snackbar = Snackbar.make(container, "Please Add Profile Image.", Snackbar.LENGTH_LONG);
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

    @BindView(R.id.upload_img)
    FrameLayout uploadImg;

    @OnClick(R.id.bt_next)
    void next(){
        if (validation()) {
            addValues();
            Intent intent=new Intent(getApplicationContext(),TrainerProfile2Activity.class);
            intent.putExtra("age",age);
            intent.putExtra("bio",bio);
            intent.putExtra("trainingType",trainingType.getText().toString());
            startActivity(intent);
        }
    }

    @BindView(R.id.multiselectSpinner)
    MultiSpinner multiselectSpinner;

    @OnClick(R.id.input_training_type)
    void trainingType(){
        if (Preferences.getInstance().getUserType().equalsIgnoreCase("Trainer")) {
            multiselectSpinner.performClick();
            multiselectSpinner.setVisibility(View.VISIBLE);
        }
        else {

        }
    }

    @BindView(R.id.upload_image_icon)
    ImageView uploadImageIcon;

    @BindView(R.id.profile_image)
    CircleImageView profileImage;

    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_trainerId)
    TextView tvTrainerId;

    @BindView(R.id.input_first_name)
    EditText fName;

    @BindView(R.id.input_last_name)
    EditText lName;

    @BindView(R.id.input_email)
    EditText emailAddress;

    @BindView(R.id.input_phoneno)
    EditText phoneNumber;

    @BindView(R.id.input_training_type)
    EditText trainingType;

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        StringBuilder sb = new StringBuilder();
        if (strings.size()!=0) {
            for (String s : strings) {
                sb.append(s);
                sb.append(",");
            }
            String train = removeLastChar(sb.toString());
            trainingType.setText(train);
        }
        else {
            trainingType.setText("");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
    }
}
