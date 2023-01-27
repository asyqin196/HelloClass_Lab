package ftmk.bitp3453.helloclass;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StudentDB extends SQLiteOpenHelper {
    public static final String dbName = "dbStudent";
    public static final String tblStudents = "students";
    public static final String colStudName = "student_name";
    public static final String colStudNum = "student_num";
    public static final String colStudGender = "student_gender";
    public static final String colStudState = "student_state";
    public static final String colStudDob = "student_dob";

    public static final String strCrtTblStudents = "CREATE TABLE "+ tblStudents + " ("+ colStudNum +
            " INTEGER PRIMARY KEY, " + colStudName +" TEXT, " + colStudGender +" TEXT, " +colStudState +" TEXT, "
             +colStudDob +" DATE)";

    public static  final String strDropTblStudents = "DROP TABLE IF EXISTS "+ tblStudents;


    public StudentDB(Context context) {
        super(context, dbName, null, 10);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(strCrtTblStudents );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL(strDropTblStudents);
        onCreate(db);

    }

    public float fnInsert(Student student)
    {
        float retResult = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colStudName, student.getStrFullName());
        values.put(colStudNum, student.getStrStudNo());
        values.put(colStudGender, student.getStrGender());
        values.put(colStudState, student.getStrState());
        values.put(colStudDob, student.getStrBirthdate());

        retResult = db.insert(tblStudents, null, values);
        return  retResult;
    }

    @SuppressLint("Range")
    public Student fnGetExpenses(int intExpId) {
        Student student = new Student();

        String strSelQuery = "Select * from " + tblStudents + "where " + colStudNum
                + "= " + intExpId;
        Cursor cursor = this.getReadableDatabase().rawQuery(strSelQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        student.setStrFullName(cursor.getString(cursor.getColumnIndex(colStudName)));
        student.setStrStudNo(cursor.getString(cursor.getColumnIndex(colStudNum)));
        student.setStrGender(cursor.getString(cursor.getColumnIndex(colStudGender)));
        student.setStrBirthdate(cursor.getString(cursor.getColumnIndex(colStudState)));
        student.setStrState(cursor.getString(cursor.getColumnIndex(colStudDob)));

        return student;
    }

    @SuppressLint("Range")
    public List<Student> fnGetAllExpenses()
    {
        List<Student> listExp = new ArrayList<Student>();

        String strSelAll = "Select * from " + tblStudents;

        Cursor cursor = this.getReadableDatabase().rawQuery(strSelAll,null);
        if(cursor.moveToFirst())
        {
            do {
                Student student = new Student();

                student.setStrFullName(cursor.getString(cursor.getColumnIndex(colStudName)));
                student.setStrStudNo(cursor.getString(cursor.getColumnIndex(colStudNum)));
                student.setStrGender(cursor.getString(cursor.getColumnIndex(colStudGender)));
                student.setStrState(cursor.getString(cursor.getColumnIndex(colStudState)));
                student.setStrBirthdate(cursor.getString(cursor.getColumnIndex(colStudDob)));

                listExp.add(student);

            }while(cursor.moveToNext());
        }
        return listExp;
    }

    public int fnUpdateExpenses(Student student) {
        int retResult = 0;

        ContentValues values = new ContentValues();
        values.put(colStudName, student.getStrFullName());
        values.put(colStudNum, student.getStrStudNo());
        values.put(colStudGender, student.getStrGender());
        values.put(colStudDob, student.getStrBirthdate());
        values.put(colStudState, student.getStrState());

        String[] argg = {String.valueOf(student.getStrStudNo())};

        this.getWritableDatabase().update(tblStudents, values, colStudNum + " = ?", argg);
        return retResult;
    }
}
