package com.example.sistemagerenciamentotabulando.model.dao;

import com.example.sistemagerenciamentotabulando.db.DB;
import com.example.sistemagerenciamentotabulando.model.dao.impl.HorarioDAOJDBC;
import com.example.sistemagerenciamentotabulando.model.dao.impl.JogoDAOJDBC;
import com.example.sistemagerenciamentotabulando.model.dao.impl.VisitanteDAOJDBC;

public interface DAOFactory {
    public static HorarioDAO createHorarioDAO(){
        return new HorarioDAOJDBC(DB.getConnection());
    }

    public static JogoDAO createJogoDAO(){
        return new JogoDAOJDBC(DB.getConnection());
    }

    public static VisitanteDAO createVisitanteDAO(){ return new VisitanteDAOJDBC(DB.getConnection());}
}
