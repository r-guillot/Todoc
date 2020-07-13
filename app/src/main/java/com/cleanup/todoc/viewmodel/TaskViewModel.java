package com.cleanup.todoc.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import androidx.annotation.Nullable;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    //Repository
    private ProjectRepository mProjectRepository;
    private TaskRepository mTaskRepository;

    //Data
    @Nullable
    private LiveData<List<Project>> mAllProject;
    private LiveData<List<Task>> mAllTask;

    public TaskViewModel(Application application) {
        super(application);
        mProjectRepository = new ProjectRepository(application);
        mAllProject = mProjectRepository.getAllProject();

        mTaskRepository = new TaskRepository(application);
        mAllTask = mTaskRepository.getAllTask();
    }

    //For Project
    public void getProject(long id) {
        mProjectRepository.getProject(id);
    }


    //For Tasks
    public LiveData<List<Task>> getAllTask() {
        return mAllTask;
    }

    public void createTask(Task task) {
        mTaskRepository.createTask(task);
    }

    public void deleteTask(Task task) {
        mTaskRepository.deleteTask(task);
    }
}
