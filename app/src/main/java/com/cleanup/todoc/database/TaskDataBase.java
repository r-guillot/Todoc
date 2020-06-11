package com.cleanup.todoc.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDAO;
import com.cleanup.todoc.database.dao.TaskDAO;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class TaskDataBase extends RoomDatabase {

    //SINGLETON
    private static volatile TaskDataBase INSTANCE;

    //DAO
    public abstract ProjectDAO projectDao();

    public abstract TaskDAO taskDao();

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //INSTANCE
    public static TaskDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDataBase.class, "task_database")
                            .addCallback(sRoomDatabaseCallback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                ProjectDAO PDao = INSTANCE.projectDao();


                if (PDao.getAllProject() == null) {
                    Project project = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
                    PDao.insertProject(project);
                    project = new Project(2L, "Projet Lucidia", 0xFFB4CDBA);
                    PDao.insertProject(project);
                    project = new Project(3L, "Projet Circus", 0xFFA3CED2);
                    PDao.insertProject(project);
                }
            });
        }
    };
}
