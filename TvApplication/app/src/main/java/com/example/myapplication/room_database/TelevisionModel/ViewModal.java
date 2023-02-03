package com.example.myapplication.room_database.TelevisionModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModal extends AndroidViewModel {

    // creating a new variable for course repository.
    private TelevisionRepository repository;

    // below line is to create a variable for live
    // data where all the courses are present.
    private List<Television> allCourses;

    // constructor for our view modal.
    public ViewModal(@NonNull Application application) {
        super(application);
        repository = new TelevisionRepository(application);
        allCourses = repository.getAllCourses();
    }

    // below method is use to insert the data to our repository.
    public void insert(Television model) {
        repository.insert(model);
    }

    // below line is to update data in our repository.
    public void update(Television model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(Television model) {
        repository.delete(model);
    }

    // below method is to delete all the courses in our list.
    public void deleteAllCourses() {
        repository.deleteAllCourses();
    }

    // below method is to get all the courses in our list.
    public List<Television> getAllCourses() {
        return allCourses;
    }
}
