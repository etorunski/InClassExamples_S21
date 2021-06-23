package algonquin.cst2335.inclassexamples_s21;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyOpenHelper extends SQLiteOpenHelper {

    public static final String name = "DatabaseFile";
    public static final int version = 1;

    public MyOpenHelper( Context context ) {
        super(context, name, null, version);
        //that's all the constructor does
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
