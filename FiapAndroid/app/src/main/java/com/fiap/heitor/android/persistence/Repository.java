package com.fiap.heitor.android.persistence;

import com.fiap.heitor.android.exception.FiapDatabaseException;
import com.fiap.heitor.android.model.Place;

import java.util.List;


public interface Repository {
    void save(Place place) throws FiapDatabaseException;

    int delete(String id);

    Place show(String name);

    List<Place> listAll();

    void update(Place place) throws FiapDatabaseException;
}

