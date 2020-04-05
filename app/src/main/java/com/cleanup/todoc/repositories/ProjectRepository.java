package com.cleanup.todoc.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.ProjectDao;
import com.cleanup.todoc.database.TodocRoomDatabase;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    private ProjectDao projectDao;
    private LiveData<List<Project>> allProjects;

    public ProjectRepository(Application application) {
        TodocRoomDatabase db = TodocRoomDatabase.getDatabase(application);
        projectDao = db.projectDao();
        allProjects = projectDao.getAllProjects();
    }

    public void insertProject(Project project) {
        TodocRoomDatabase.databaseWriteExecutor.execute(() -> {
            projectDao.insertProject(project);
        });
    }

    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }
}
