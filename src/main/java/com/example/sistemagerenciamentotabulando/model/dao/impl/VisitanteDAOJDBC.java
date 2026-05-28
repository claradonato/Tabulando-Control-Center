package com.example.sistemagerenciamentotabulando.model.dao.impl;

import com.example.sistemagerenciamentotabulando.db.DB;
import com.example.sistemagerenciamentotabulando.model.dao.VisitanteDAO;
import com.example.sistemagerenciamentotabulando.model.entities.Jogo;
import com.example.sistemagerenciamentotabulando.model.entities.Visitante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VisitanteDAOJDBC implements VisitanteDAO {
    private Connection conn;

    public VisitanteDAOJDBC(Connection conn){ this.conn = conn; }

    @Override
    public void inserir(Visitante v) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("insert into visitante(matricula, nome, idade, genero, nivel_ensino, curso, turno, possuiNEE) values (?, ?, ?, ?, ?, ?, ?, ?)");
            st.setInt(1, v.getMatricula());
            st.setString(2, v.getNome());
            st.setInt(3, v.getIdade());
            st.setString(4, v.getGenero());
            st.setString(5, v.getNivel_ensino());
            st.setString(6, v.getCurso());
            st.setString(7, v.getTurno());
            st.setBoolean(8, v.getPossuiNEE());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void atualizar(Visitante v) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("update visitante set matricula = ?, nome = ?, idade = ?, genero = ?, nivel_ensino = ?, curso = ?, turno = ?, possuiNEE = ? where matricula = ?");
            st.setInt(1, v.getMatricula());
            st.setString(2, v.getNome());
            st.setInt(3, v.getIdade());
            st.setString(4, v.getGenero());
            st.setString(5, v.getNivel_ensino());
            st.setString(6, v.getCurso());
            st.setString(7, v.getTurno());
            st.setBoolean(8, v.getPossuiNEE());
            st.setInt(9, v.getMatricula());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deletarPorMatricula(Integer matricula) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("delete from visitante where matricula=?");
            st.setInt(1, matricula);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Visitante procurarPorMatricula(Integer matricula) {
        return null;
    }

    @Override
    public List<Visitante> listarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Visitante> visitantes = new ArrayList<Visitante>();

        try {
            st = conn.prepareStatement("select * from visitante");
            rs = st.executeQuery();
            while(rs.next()){
                Visitante v = new Visitante(rs.getInt("matricula"), rs.getString("nome"), rs.getInt("idade"), rs.getString("genero"), rs.getString("nivel_ensino"), rs.getString("curso"), rs.getString("turno"), rs.getBoolean("possuiNEE"));
                visitantes.add(v);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

        return visitantes;
    }
}
