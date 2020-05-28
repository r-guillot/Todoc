package com.cleanup.todoc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    //Repository
    private final ProjectDataRepository mProjectDataRepository;
    private final TaskDataRepository mTaskDataRepository;
    private final Executor mExecutor;

    //Data
    @Nullable
    private LiveData<Project> currentProject;

    public TaskViewModel(ProjectDataRepository mProjectDataRepository, TaskDataRepository mTaskDataRepository, Executor mExecutor) {
        this.mProjectDataRepository = mProjectDataRepository;
        this.mTaskDataRepository = mTaskDataRepository;
        this.mExecutor = mExecutor;
    }

    public void init(long projectId) {
        if(this.currentProject != null) {
            return;
        }
        currentProject = mProjectDataRepository.getProject(projectId);
    }

    //For Project
    public LiveData<Project> getProject(long projectId) {
        return this.currentProject;
    }

    //For Tasks
    public LiveData<List<Task>> getTask(long projectId) {
        return mTaskDataRepository.getTask(projectId);
    }

    public void createTask(final Task task) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTaskDataRepository.createTask(task);
            }
        });
    }

    public void deleteTask(final long projectId) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTaskDataRepository.deleteTask(projectId);
            }
        });
    }

    public void updateTask(final Task task) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTaskDataRepository.updateTask(task);
            }
        });
    }
}
