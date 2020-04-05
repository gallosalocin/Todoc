package com.cleanup.todoc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;
    private LiveData<List<Project>> allProjects;
    private LiveData<List<Task>> allTasks;


    public TaskViewModel(@NonNull Application application) {
        super(application);
        projectRepository = new ProjectRepository(application);
        taskRepository = new TaskRepository(application);
        allProjects = projectRepository.getAllProjects();
        allTasks = taskRepository.getAllTasks();
    }

    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }

    public void insertProject(Project project) {
        projectRepository.insertProject(project);
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void insertTask(Task task) {
        taskRepository.insertTask(task);
    }

    public void updateTask(Task task) {
        taskRepository.updateTask(task);
    }

    public void deleteTask(Task task) {
        taskRepository.deleteTask(task);
    }
}
