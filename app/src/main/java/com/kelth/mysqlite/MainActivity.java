package com.kelth.mysqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String DATABASE_NAME = "MySQLiteDatabase";

    SQLiteDatabase mSQLiteDb;
    Cursor mCursor;

    TextView mTextViewStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSQLiteDb = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        mSQLiteDb.execSQL("CREATE TABLE IF NOT EXISTS TutorialsPoint(Username VARCHAR,Password VARCHAR);");

        mTextViewStatus = findViewById(R.id.textView);

        findViewById(R.id.button_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });

        findViewById(R.id.button_fetch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCursor.close();
    }

    private void insert() {
        mSQLiteDb.execSQL("INSERT INTO TutorialsPoint VALUES('admin','1234');");
        mSQLiteDb.execSQL("INSERT INTO TutorialsPoint VALUES('Peter','1234');");

        // Check we have create the entries
        mCursor = mSQLiteDb.rawQuery("Select * from TutorialsPoint",null);
        mTextViewStatus.setText("Total num of entries: " + mCursor.getCount());
    }

    private void fetch() {
        mCursor = mSQLiteDb.rawQuery("Select * from TutorialsPoint",null);
        mCursor.moveToFirst();

        StringBuilder status = new StringBuilder();

        int numOfEntries = mCursor.getCount();
        status.append("Total entries: " + numOfEntries);

        for (int i = 0 ; i < numOfEntries ; i++) {
            String username = mCursor.getString(0);
            String password = mCursor.getString(1);
            status.append(System.getProperty("line.separator"));
            status.append("Name: " + username + ", Pwd: " + password);
            mCursor.moveToNext();
        }
        mTextViewStatus.setText(status);
    }
}