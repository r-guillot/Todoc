package com.cleanup.todoc.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.TaskDataBase;
import com.cleanup.todoc.database.dao.ProjectDAO;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {
    private final ProjectDAO mProjectDAO;
    private LiveData<List<Project>> mAllProject;

    public ProjectRepository(Application application) {
        TaskDataBase db = TaskDataBase.getDatabase(application);
        mProjectDAO = db.projectDao();
        mAllProject = mProjectDAO.getAllProject();
    }

    public LiveData<List<Project>> getAllProject() {
        return mAllProject;
    }

    public LiveData<Project> getProject(long projectId) {
        return mProjectDAO.getProject(projectId);
    }

    public void createProject(Project project) {
        TaskDataBase.databaseWriteExecutor.execute(() -> {
            mProjectDAO.insertProject(project);
        });
    }

    //Delete
    public void deleteProject(Project project) {
        TaskDataBase.databaseWriteExecutor.execute(() -> {
            mProjectDAO.deleteAll();
        });
    }

    //Update Color
    public int updateColor (long projectId, int set_color) {
        return mProjectDAO.updateColor(projectId, set_color);
    }
}
