package com.example.haushaltsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class MainActivity extends AppCompatActivity {

    private static final int MY_CAL_WRITE_REQ = 1;
    private int Storage_Permission_Code = 1;
    TextView dateSelect;
    ImageView calenderView;
    int year;
    int month;
    int day;

    public static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateSelect = findViewById(R.id.dateSelect);
        calenderView = findViewById(R.id.calenderView);

        // Hier Code für CalenderView, kein erstellen von Events, nur Auslesen von Tag,Monat,Jahr
        Calendar calendar = Calendar.getInstance();
        calenderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog g = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateSelect.setText(year + "/" + (month + 1) + "/" + dayOfMonth);              //mont +1, starts with index 0
                    }
                }, year, month, day);
                g.show();
            }
        });
    }


    //Ab hier: Erstellung, Editieren, Löschen... usw. von Events in Kalender

    public void insertEvent(String title, String location, long begin, long end) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events._ID,2)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "There is no app that can support this action",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void getEvent(View view) {
        //Check if Permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "You have already granted this permission",
                    Toast.LENGTH_SHORT).show(); }else{
            requestWritePermission();
        }

        Calendar beginTime = Calendar.getInstance();
        long begin= System.currentTimeMillis();
        long end= System.currentTimeMillis();
        insertEvent("yes", "test", begin, end);
    }

    public void viewEvent(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "You have already granted this permission",
                    Toast.LENGTH_SHORT).show(); }else{
            requestReadPermission();
        }

// View Events with particular date
        long startMillis = System.currentTimeMillis();
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, startMillis);
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(builder.build());
        startActivity(intent);

// View Events with EventId
//        long eventID = 81;
//        Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
//        Intent intent = new Intent(Intent.ACTION_VIEW)
//                .setData(uri);
//        startActivity(intent);
    }

    public void editEvent(View view) {
//Use an intent to edit an event
        long eventID = 81;
        Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        Intent intent = new Intent(Intent.ACTION_EDIT)
                .setData(uri)
                .putExtra(CalendarContract.Events.TITLE, "My New Title");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "There is no app that can support this action",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void queryCalendar(View view) {
        Cursor cursor = getContentResolver().query(Uri.parse("content://com.android.calendar/calendars"),
                new String[]{"_id", "calendar_displayName"}, null, null, null);
        // Get calendar names
        Log.i("@calendar","Cursor count " + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String[] calendarNames = new String[cursor.getCount()];
            // Get calendars id
            int calendarIds[] = new int[cursor.getCount()];
            for (int i = 0; i < cursor.getCount(); i++) {
                calendarIds[i] = cursor.getInt(0);
                calendarNames[i] = cursor.getString(1);
                Log.i("@calendar","Calendar Name : " + calendarNames[i]);
                cursor.moveToNext(); }
        } else {
            Log.e("@calendar","No calendar found in the device");
        }
    }

    public void modifyCalendar(View view) {
        //Modify a calendar
        final String DEBUG_TAG = "MyActivity";
        long calID = 2;
        ContentValues values = new ContentValues();
        // The new display name for the calendar
        values.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "Trevor's Calendar");
        Uri updateUri = ContentUris.withAppendedId(CalendarContract.Calendars.CONTENT_URI, calID);
        int rows = getContentResolver().update(updateUri, values, null, null);
        Log.i(DEBUG_TAG, "Rows updated: " + rows);
    }

    public void addEvent(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "You have already granted this permission",
                    Toast.LENGTH_SHORT).show(); }else{
            requestWritePermission();
        }

        long calID = 2;
        long startMillis = 0;
        long endMillis = 0;

        //Calendar beginTime = Calendar.getInstance();
        //beginTime.set(2012, 9, 14, 7, 30);
        //startMillis = beginTime.getTimeInMillis();
        //Calendar endTime = Calendar.getInstance();
        //endTime.set(2012, 9, 14, 8, 45);
        //endMillis = endTime.getTimeInMillis();

        startMillis = System.currentTimeMillis();
        endMillis = System.currentTimeMillis();
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, "Jazzercise");
        values.put(CalendarContract.Events.DESCRIPTION, "Group workout");
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
        long eventID = Long.parseLong(uri.getLastPathSegment());
        Toast.makeText(MainActivity.this , "Event ID = "+eventID, Toast.LENGTH_LONG).show();
    }

    public void deleteEvent(View view) {
        final String DEBUG_TAG = "MyActivity";
        long eventID = 81;
        ContentResolver cr = getContentResolver();
        Uri deleteUri = null;
        deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        int rows = cr.delete(deleteUri, null, null);
        Log.i(DEBUG_TAG, "Rows deleted: " + rows);
    }

    public void addReminders(View view) {
        long eventID = 221;
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Reminders.MINUTES, 15);
        values.put(CalendarContract.Reminders.EVENT_ID, eventID);
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        Uri uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
    }

    public void requestWritePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_CALENDAR)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_CALENDAR},Storage_Permission_Code);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR},Storage_Permission_Code);
        }
    }

    public void requestReadPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CALENDAR)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALENDAR},Storage_Permission_Code);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR},Storage_Permission_Code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Storage_Permission_Code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


