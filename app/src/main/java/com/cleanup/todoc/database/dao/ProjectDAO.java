package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDAO {

    @Query("SELECT * FROM project_table")
    LiveData<List<Project>> getAllProject();

    @Query("SELECT * FROM project_table WHERE project_id= :id;")
    LiveData<Project> getProject(long id);

    @Insert
    long insertProject(Project project);

    @Query("DELETE FROM project_table")
    void deleteAll();

}
