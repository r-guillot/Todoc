package com.cleanup.todoc.injection;

import android.content.Context;

import com.cleanup.todoc.database.SaveTaskDataBase;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DI {

    public static ProjectDataRepository provideProjectDataSource(Context context) {
        SaveTaskDataBase dataBase = SaveTaskDataBase.getInstance(context);
        return new ProjectDataRepository(dataBase.projectDao());
    }

    public static TaskDataRepository provideTaskDataSource(Context context) {
        SaveTaskDataBase dataBase = SaveTaskDataBase.getInstance(context);
        return new TaskDataRepository(dataBase.taskDao());
    }

    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
       ProjectDataRepository projectDataSource = provideProjectDataSource(context);
       TaskDataRepository taskDataSource = provideTaskDataSource(context);
       Executor executor = provideExecutor();
       return new ViewModelFactory(projectDataSource, taskDataSource, executor);
    }
}
