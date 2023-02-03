package com.example.myapplication.room_database.TelevisionModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.room_database.TelevisionDatabase;

import java.util.List;

public class TelevisionRepository {

    // below line is the create a variable
    // for dao and list for all courses.
    private TelevisionDao dao;
    private List<Television> allCourses;

    // creating a constructor for our variables
    // and passing the variables to it.
    public TelevisionRepository(Application application) {
        TelevisionDatabase database = TelevisionDatabase.getInstance(application);
        dao = database.televisionDao();
        allCourses = dao.getAllTelevisions();
    }

    // creating a method to insert the data to our database.
    public void insert(Television model) {
        new InsertCourseAsyncTask(dao).execute(model);
    }

    // creating a method to update data in database.
    public void update(Television model) {
        new UpdateCourseAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(Television model) {
        new DeleteCourseAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the courses.
    public void deleteAllCourses() {
        new DeleteAllCoursesAsyncTask(dao).execute();
    }

    // below method is to read all the courses.
    public List<Television> getAllCourses() {
        return allCourses;
    }

    // we are creating a async task method to insert new course.
    private static class InsertCourseAsyncTask extends AsyncTask<Television, Void, Void> {
        private TelevisionDao dao;

        private InsertCourseAsyncTask(TelevisionDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Television... model) {
            // below line is use to insert our modal in dao.
            dao.addNewTelevision(model[0]);
            return null;
        }
    }

    // we are creating a async task method to update our course.
    private static class UpdateCourseAsyncTask extends AsyncTask<Television, Void, Void> {
        private TelevisionDao dao;

        private UpdateCourseAsyncTask(TelevisionDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Television... models) {
            // below line is use to update
            // our modal in dao.
            dao.update(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete course.
    private static class DeleteCourseAsyncTask extends AsyncTask<Television, Void, Void> {
        private TelevisionDao dao;

        private DeleteCourseAsyncTask(TelevisionDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Television... models) {
            // below line is use to delete
            // our course modal in dao.
            dao.delete(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all courses.
    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void> {
        private TelevisionDao dao;

        private DeleteAllCoursesAsyncTask(TelevisionDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all courses.
            dao.deleteAllCourses();
            return null;
        }
    }
}
