package com.cleanup.todoc.roomdbtest;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.database.TaskDataBase;
import com.cleanup.todoc.database.dao.ProjectDAO;
import com.cleanup.todoc.database.dao.TaskDAO;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class RoomDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ProjectDAO mProjectDAO;
    private TaskDAO mTaskDAO;
    private TaskDataBase mDataBase;

    @Before
    public void createDB() {
        Context context = ApplicationProvider.getApplicationContext();
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        mDataBase = Room.inMemoryDatabaseBuilder(context, TaskDataBase.class)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build();
        mProjectDAO = mDataBase.projectDao();
        mTaskDAO = mDataBase.taskDao();
    }

    @After
    public void closeDb() {
        mDataBase.close();
    }

    //ProjectDao Tests
    @Test
    public void getAllProjects() throws InterruptedException {
        Project project = new Project(1, "projet test 1", 0xFFEADAD1);
        mProjectDAO.insertProject(project);
        Project project2 = new Project(2, "projet test 2", 0xFFEADAD1);
        mProjectDAO.insertProject(project2);

        List<Project> allProjects = LiveDataTestUtil.getValue(mProjectDAO.getAllProject());
        assertEquals(allProjects.get(0).getName(), project.getName());
        assertEquals(allProjects.get(1).getName(), project2.getName());
    }

    @Test
    public void deleteAllProjects() throws InterruptedException {
        Project project = new Project(1, "projet test 1", 0xFFEADAD1);
        mProjectDAO.insertProject(project);
        Project project2 = new Project(2, "projet test 2", 0xFFEADAD1);
        mProjectDAO.insertProject(project2);

        mProjectDAO.deleteAll();
        List<Project> allProjects = LiveDataTestUtil.getValue(mProjectDAO.getAllProject());
        assertTrue(allProjects.isEmpty());
    }

    @Test
    public void getProjectById() throws InterruptedException {
        Project project = new Project(1, "projet test 1", 0xFFEADAD1);
        mProjectDAO.insertProject(project);
        Project project2 = new Project(2, "projet test 2", 0xFFEADAD1);
        mProjectDAO.insertProject(project2);

        Project projectId = LiveDataTestUtil.getValue(this.mDataBase.projectDao().getProject(1));
        assertTrue(projectId.getName().equals(project.getName()) && projectId.getId() == project.getId());
        assertNotEquals(projectId.getId(), project2.getId());
    }

    @Test
    public void insertProject() throws InterruptedException {
        List<Project> emptyProject = LiveDataTestUtil.getValue(mProjectDAO.getAllProject());
        assertTrue(emptyProject.isEmpty());

        Project project = new Project(1, "projet test 1", 0xFFEADAD1);
        mProjectDAO.insertProject(project);

        List<Project> allProjects = LiveDataTestUtil.getValue(mProjectDAO.getAllProject());
        assertFalse(allProjects.isEmpty());
    }


    // TaskDao Tests
    @Test
    public void getAllTasks() throws InterruptedException {
        Project project = new Project(1, "projet test 1", 0xFFEADAD1);
        mProjectDAO.insertProject(project);
        Project project2 = new Project(2, "projet test 2", 0xFFEADAD1);
        mProjectDAO.insertProject(project2);

        Task task1 = new Task(1, 1, "task 1", 1);
        mTaskDAO.insertTask(task1);
        Task task2 = new Task(2, 2, "task 2", 2);
        mTaskDAO.insertTask(task2);

        List<Task> allTasks = LiveDataTestUtil.getValue(mTaskDAO.getAllTask());
        assertEquals(allTasks.get(0).getName(), task1.getName());
        assertEquals(allTasks.get(1).getName(), task2.getName());
    }

    @Test
    public void insertTasks() throws InterruptedException {
        List<Task> emptyTasks = LiveDataTestUtil.getValue(mTaskDAO.getAllTask());
        assertTrue(emptyTasks.isEmpty());

        Project project = new Project(1, "projet test 1", 0xFFEADAD1);
        mProjectDAO.insertProject(project);

        Task task1 = new Task(1, 1, "task 1", 1);
        mTaskDAO.insertTask(task1);

        List<Task> allTasks = LiveDataTestUtil.getValue(mTaskDAO.getAllTask());
        assertFalse(allTasks.isEmpty());
    }

    @Test
    public void getTasksByForeignKey() throws InterruptedException {
        Project project = new Project(1, "projet test 1", 0xFFEADAD1);
        mProjectDAO.insertProject(project);
        Project project2 = new Project(2, "projet test 2", 0xFFEADAD1);
        mProjectDAO.insertProject(project2);
        Project project3 = new Project(3, "projet test 3", 0xFFEADAD1);
        mProjectDAO.insertProject(project3);

        Task task1 = new Task(1, 1, "task 1", 1);
        mTaskDAO.insertTask(task1);
        Task task2 = new Task(2, 2, "task 2", 2);
        mTaskDAO.insertTask(task2);
        Task taskById = new Task(3, 3, "task by id", 3);
        mTaskDAO.insertTask(taskById);

        List<Task> allTasks = LiveDataTestUtil.getValue(mTaskDAO.getAllTask());
        assertEquals(allTasks.get(0).getName(), task1.getName());
        assertEquals(allTasks.get(1).getName(), task2.getName());
        assertEquals(allTasks.get(2).getName(), taskById.getName());

        List<Task> taskId = LiveDataTestUtil.getValue(mTaskDAO.getTask(3));
        assertTrue(taskId.get(0).getName().equals(taskById.getName()) && taskId.get(0).getProjectId() == taskById.getProjectId());
        assertNotEquals(taskId.get(0).getProjectId(), task1.getProjectId());
    }

    @Test
    public void deleteTasks() throws InterruptedException {
        Project project = new Project(1, "projet test 1", 0xFFEADAD1);
        mProjectDAO.insertProject(project);
        Project project2 = new Project(2, "projet test 2", 0xFFEADAD1);
        mProjectDAO.insertProject(project2);

        Task task1 = new Task(1, 1, "task 1", 1);
        mTaskDAO.insertTask(task1);
        Task task2 = new Task(2, 2, "task 2", 2);
        mTaskDAO.insertTask(task2);

        mTaskDAO.deleteItem(task2);
        List<Task> allTasks = LiveDataTestUtil.getValue(mTaskDAO.getAllTask());
        assertEquals(allTasks.get(0).getName(), task1.getName());
        assertFalse(allTasks.contains(task2));
    }
}