package com.cleanup.todoc.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class TodocRoomDatabase extends RoomDatabase {

    public abstract ProjectDao projectDao();

    public abstract TaskDao taskDao();

    private static volatile TodocRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TodocRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodocRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TodocRoomDatabase.class,
                            "meeting_database").addCallback(populateDatabase).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback populateDatabase = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                ProjectDao projectDao = INSTANCE.projectDao();
                projectDao.insertProject(new Project(1L, "Projet Tartampion", 0xFFEADAD1));
                projectDao.insertProject(new Project(2L, "Projet Lucidia", 0xFFB4CDBA));
                projectDao.insertProject(new Project(3L, "Projet Circus", 0xFFA3CED2));

                TaskDao taskDao = INSTANCE.taskDao();
                taskDao.deleteAllTasks();
                taskDao.insertTask(new Task(1L, 1L, "Nicolas", 311080L));
            });
        }
    };
}