package com.cleanup.todoc.repository;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDAO;
import com.cleanup.todoc.model.Project;

public class ProjectDataRepository {
    private final ProjectDAO mProjectDAO;

    public ProjectDataRepository(ProjectDAO mProjectDAO) {
        this.mProjectDAO = mProjectDAO;
    }

    public LiveData<Project> getProject(long projectId) {
        return this.mProjectDAO.getProject(projectId);
    }
}
