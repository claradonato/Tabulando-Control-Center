package com.example.sistemagerenciamentotabulando.model.dao;

import com.example.sistemagerenciamentotabulando.db.DB;
import com.example.sistemagerenciamentotabulando.model.dao.impl.HorarioDAOJDBC;

public interface DAOFactory {
    public static HorarioDAO createHorarioDAO(){
        return new HorarioDAOJDBC(DB.getConnection());
    }
}
