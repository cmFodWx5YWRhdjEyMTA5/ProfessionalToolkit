package health.app.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import health.app.Model.SignUp;
import health.app.R;
import health.app.Utilities.BaseActivity;
import health.app.Utilities.Utility;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterOneActivity extends BaseActivity {

    public  File file =null;
    FileInputStream bin1 = null;
    private String userChoosenTask;
    public static SignUp signUp;
    Bitmap bm;
    public static byte[] data=null;
    private int REQUEST_CAMERA = 1, SELECT_FILE = 2;
    String deviceToken;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        ButterKnife.bind(this);
        final Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/Poppins-SemiBold.ttf");
        btNext.setTypeface(font);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF7010"));
        }
        signUp=new SignUp();
        if (deviceToken==null) {
            deviceToken = FirebaseInstanceId.getInstance().getToken();
        }
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("Client SignUp");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data.getData());
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
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
//                //correctBmp = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mat, true);
//            } catch (IOException e) {
//                Log.w("TAG", "-- Error in setting image");
//            } catch (OutOfMemoryError oom) {
//                Log.w("TAG", "-- OOM Error in setting image");
//            }
//            profileImage.setImageBitmap(bm);
//            if (bm != null) {
//                file = convertStringToByteToFile(bitmapToString(bm));
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


    public void getValue(){
        signUp.setcImagePath(file);
        signUp.setcFirstName(inputFName.getText().toString().trim());
        signUp.setcLastName(inputLName.getText().toString().trim());
        signUp.setcGender(inputGender.getText().toString().trim());
        signUp.setcPhoneNo(inputPhoneno.getText().toString().trim());
        signUp.setcAge(inputAge.getText().toString().trim());
        signUp.setcWeight(inputWeight.getText().toString().trim());
        signUp.setcHeight(inputHeight.getText().toString().trim());
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

    public boolean validation(){
        if (profileImage.getDrawable().getConstantState() == getResources().getDrawable( R.drawable.usern).getConstantState())
        {
            Snackbar snackbar = Snackbar.make(container, "Please Add Profile Image.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (inputFName.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter First Name.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (inputLName.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Last Name.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (inputGender.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Select Gender.", Snackbar.LENGTH_LONG);
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
        else if (inputAge.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Age.", Snackbar.LENGTH_LONG);
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
    void uploadImage() {
        selectImage();
    }

    @OnClick(R.id.bt_next)
    void next(){
        if (validation()){
            getValue();
            startActivity(new Intent(getApplicationContext(),RegisterTwoActivity.class));
        }
    }


    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.bt_next)
    Button btNext;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.input_gender)
    EditText inputGender;

    @OnClick(R.id.input_gender)
    void genderSelect(){
        showDialog(RegisterOneActivity.this, "Select Gender", getResources().getStringArray(R.array.Gender),new onGenderSelected() {
            @Override
            public void genderSelection(int position) {
                inputGender.setText(getResources().getStringArray(R.array.Gender)[position]);
//                Toast.makeText(RegisterOneActivity.this,getResources().getStringArray(R.array.Gender)[position],Toast.LENGTH_SHORT).show();
            }
        });
    }

    @BindView(R.id.profile_image)
    ImageView profileImage;

    @BindView(R.id.input_first_name)
    EditText inputFName;

    @BindView(R.id.input_last_name)
    EditText inputLName;

    @BindView(R.id.input_phoneno)
    EditText inputPhoneno;

    @BindView(R.id.input_age)
    EditText inputAge;

    @BindView(R.id.input_weight)
    EditText inputWeight;

    @BindView(R.id.input_height)
    EditText inputHeight;

}
