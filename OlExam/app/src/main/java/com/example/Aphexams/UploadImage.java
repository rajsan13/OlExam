package com.example.Aphexams;

import com.parse.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.AlertDialog;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.AlertDialog;

/**
 * Created by Sandeep on 30-05-2018.
 */
public class UploadImage extends Activity {
    public static final int TAKE_PIC_REQUEST_CODE = 0;
    public static final int CHOOSE_PIC_REQUEST_CODE = 1;
    public static final int MEDIA_TYPE_IMAGE = 2;
    public static final int MEDIA_TYPE_VIDEO = 3;

    private Button mRetrieveButton;
    private Button mAddImageButton;
    private Button mUploadImageButton;
    private Uri mMediaUri;
    private ImageView mPreviewImageView;
    public void queryImagesFromParse(){
        ParseQuery<ParseObject> imagesQuery=new ParseQuery<ParseObject>("Uploads");
        imagesQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Toast.makeText(getApplicationContext(), "There was an error", Toast.LENGTH_LONG).show();
                } else {
                    ParseFile imageFile = (ParseFile) object.get("imageContent");
                    //FlexLogger.createLog("Image file :: " + imageFile);
                    imageFile.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            mPreviewImageView.setImageBitmap(bitmap);
                        }
                    });


                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image);
        mAddImageButton = (Button) findViewById(R.id.image_add);
        mUploadImageButton = (Button) findViewById(R.id.image_upload);
        mRetrieveButton=(Button)findViewById(R.id.retrieve_image);
        mPreviewImageView = (ImageView) findViewById(R.id.image_view);

        mRetrieveButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
              queryImagesFromParse();
            }
        });
        mAddImageButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        UploadImage.this);

                // set title
                alertDialogBuilder.setTitle("Your Title");

                // set dialog message
                alertDialogBuilder
                        .setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                //MainActivity.this.finish();
                                Intent choosePictureContent = new Intent(Intent.ACTION_GET_CONTENT);
                                choosePictureContent.setType("image/+");
                                startActivityForResult(choosePictureContent, CHOOSE_PIC_REQUEST_CODE);


                            }
                        })
                        .setNegativeButton("TakePhoto", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                                if (mMediaUri == null) {
                                    Toast.makeText(getApplicationContext(), "There was an error", Toast.LENGTH_LONG).show();

                                } else {
                                    takePicture.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                                    startActivityForResult(takePicture, TAKE_PIC_REQUEST_CODE);

                                }

                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


            }
        });
        mUploadImageButton = (Button)findViewById(R.id.image_upload);
        mUploadImageButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                final ParseObject imageUpload = new ParseObject("Uploads");
                try {
                    byte[] fileBytes = FileHelper.getByteArrayFromFile(UploadImage.this, mMediaUri);
                    if (fileBytes == null) {
                        Toast.makeText(getApplicationContext(), "There was an error", Toast.LENGTH_LONG).show();
                    } else {
                        String fileName = FileHelper.getFileName(UploadImage.this, mMediaUri, "image");
                        final ParseFile file = new ParseFile(fileName, fileBytes);
                        imageUpload.saveEventually(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                    imageUpload.put("imageContent", file);
                                    imageUpload.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            Toast.makeText(getApplicationContext(), "Success Uploading image", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        if (resultcode == RESULT_OK) {
            if (requestcode == CHOOSE_PIC_REQUEST_CODE) {
                if (data == null) {
                    Toast.makeText(getApplicationContext(), "Image cannot be NULL", Toast.LENGTH_LONG).show();

                } else {
                  mMediaUri=data.getData();
                    mPreviewImageView.setImageURI(mMediaUri);
                }
            } else {
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(mMediaUri);
                sendBroadcast(mediaScanIntent);
                mPreviewImageView.setImageURI(mMediaUri);


            }
        } else if (resultcode!=RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(),"Cancelled",Toast.LENGTH_LONG).show();

        }
    }


    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.


        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
}
