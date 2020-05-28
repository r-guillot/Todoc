package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.database.dao.ProjectDAO;
import com.cleanup.todoc.database.dao.TaskDAO;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class SaveTaskDataBase extends RoomDatabase {

    //SINGLETON
    private static volatile SaveTaskDataBase INSTANCE;

    //DAO
    public abstract ProjectDAO projectDao();
    public abstract TaskDAO taskDao();

    //INSTANCE
    public static SaveTaskDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SaveTaskDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SaveTaskDataBase.class, "MyDatabase.db")
//                            .addCallback(prepopulateDataBase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

//    private static Callback prepopulateDataBase() {
//        return new Callback() {
//            @Override
//            public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                super.onCreate(db);
//
//                ContentValues contentValues = new ContentValues();
//                contentValues.put("id", 1L);
//                contentValues.put("name", "Projet Tartampion");
//                contentValues.put("color", 0xFFEADAD1);
//
//                db.insert("Project", OnConflictStrategy.IGNORE, contentValues);
//            }
//        };
//    }
}
