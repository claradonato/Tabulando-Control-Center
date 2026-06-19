package com.example.sistemagerenciamentotabulando.model.dao.impl;

import com.example.sistemagerenciamentotabulando.db.DB;
import com.example.sistemagerenciamentotabulando.model.dao.JogoDAO;
import com.example.sistemagerenciamentotabulando.model.entities.Horario;
import com.example.sistemagerenciamentotabulando.model.entities.Jogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JogoDAOJDBC implements JogoDAO {
    private Connection conn;

    public JogoDAOJDBC(Connection conn){ this.conn = conn; }

    @Override
    public void inserir(Jogo j) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("insert into jogo(titulo, tipo, min_numero_jogadores, max_numero_jogadores, descricao, marca, faixaEtaria, tempo_partida, disponibilidade) values (?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, j.getTitulo());
            st.setString(2, j.getTipo());
            st.setInt(3, j.getMinimoNumeroJogadores());
            st.setInt(4, j.getMaximoNumeroJogadores());
            st.setString(5, j.getDescricao());
            st.setString(6, j.getMarca());
            st.setInt(7, j.getFaixaEtaria());
            st.setInt(8, j.getTempoPartida());
            st.setBoolean(9, j.getDisponibilidade());

            int tuplas = st.executeUpdate();
            if(tuplas!=0){
                rs =st.getGeneratedKeys();
                if(rs.next()){
                    j.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void atualizar(Jogo j) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("update jogo set titulo = ?, tipo = ?, min_numero_jogadores = ?, max_numero_jogadores = ?, descricao = ?, marca = ?, faixaEtaria = ?, tempo_partida = ?, disponibilidade = ? where id_jogo=?");
            st.setString(1, j.getTitulo());
            st.setString(2, j.getTipo());
            st.setInt(3, j.getMinimoNumeroJogadores());
            st.setInt(4, j.getMaximoNumeroJogadores());
            st.setString(5, j.getDescricao());
            st.setString(6, j.getMarca());
            st.setInt(7, j.getFaixaEtaria());
            st.setInt(8, j.getTempoPartida());
            st.setBoolean(9, j.getDisponibilidade());
            st.setInt(10, j.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deletarPorId(Integer id_jogo) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("delete from jogo where id_jogo=?");
            st.setInt(1, id_jogo);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Jogo procurarPorId(Integer id_jogo) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("select * from jogo where id_jogo = ?");
            st.setInt(1, id_jogo);
            rs = st.executeQuery();

            if(rs.next()){
                Jogo j = new Jogo(rs.getInt("id_jogo"), rs.getString("titulo"), rs.getString("tipo"), rs.getInt("min_numero_jogadores"), rs.getInt("max_numero_jogadores"), rs.getString("descricao"), rs.getString("marca"), rs.getInt("faixaEtaria"), rs.getInt("tempo_partida"), rs.getBoolean("disponibilidade"));
                return j;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Jogo> listarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Jogo> jogos = new ArrayList<Jogo>();

        try {
            st = conn.prepareStatement("select * from jogo");
            rs = st.executeQuery();
            while(rs.next()){
                Jogo j = new Jogo(rs.getInt("id_jogo"), rs.getString("titulo"), rs.getString("tipo"), rs.getInt("min_numero_jogadores"), rs.getInt("max_numero_jogadores"), rs.getString("descricao"), rs.getString("marca"), rs.getInt("faixaEtaria"), rs.getInt("tempo_partida"), rs.getBoolean("disponibilidade"));
                jogos.add(j);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

        return jogos;
    }
}
