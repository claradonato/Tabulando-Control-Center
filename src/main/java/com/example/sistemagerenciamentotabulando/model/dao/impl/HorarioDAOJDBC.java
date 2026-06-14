package com.example.sistemagerenciamentotabulando.model.dao.impl;

import com.example.sistemagerenciamentotabulando.db.DB;
import com.example.sistemagerenciamentotabulando.model.dao.HorarioDAO;
import com.example.sistemagerenciamentotabulando.model.entities.Horario;
import com.example.sistemagerenciamentotabulando.model.entities.Jogo;
import com.example.sistemagerenciamentotabulando.model.entities.Visitante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HorarioDAOJDBC implements HorarioDAO {
    private Connection conn;

    public HorarioDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Horario h) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("insert into horario(dia_semana, turno, hora, nome_monitor) values (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, h.getDiaSemana());
            st.setString(2, h.getTurno());
            st.setString(3, h.getHora());
            st.setString(4, h.getNomeMonitor());

            int tuplas = st.executeUpdate();
            if (tuplas != 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    h.setId_horario(rs.getInt(1));
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
    public void atualizar(Horario h) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("update horario set dia_semana = ?, turno = ?, hora = ?, nome_monitor = ? where id_horario=?");
            st.setString(1, h.getDiaSemana());
            st.setString(2, h.getTurno());
            st.setString(3, h.getHora());
            st.setString(4, h.getNomeMonitor());
            st.setInt(5, h.getId_horario());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deletarPorId(Integer id_horario) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("delete from horario where id_horario=?");
            st.setInt(1, id_horario);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Horario procurarPorId(Integer id_horario) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from horario where id_horario = ?");
            st.setInt(1, id_horario);
            rs = st.executeQuery();

            if (rs.next()) {
                Horario h = new Horario(rs.getInt("id_horario"), rs.getString("dia_semana"), rs.getString("turno"), rs.getString("hora"), rs.getString("nome_monitor"));
                return h;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Horario> listarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Horario> horarios = new ArrayList<Horario>();

        try {
            st = conn.prepareStatement("select * from horario");
            rs = st.executeQuery();
            while (rs.next()) {
                Horario h = new Horario(rs.getInt("id_horario"), rs.getString("dia_semana"), rs.getString("turno"), rs.getString("hora"), rs.getString("nome_monitor"));
                horarios.add(h);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

        return horarios;
    }

    @Override
    public void adicionarVisitanteHorario(Integer matricula, Integer id_horario) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("INSERT INTO frequencia(id_visitante, id_horario) VALUES (?, ?)");
            st.setInt(1, matricula);
            st.setInt(2, id_horario);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void adicionarJogoHorario(Integer idJogo, Integer idHorario) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("INSERT INTO uso_jogo(id_jogo, id_horario) VALUES (?, ?)");
            st.setInt(1, idJogo);
            st.setInt(2, idHorario);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Visitante> buscarVisitantesHorario(Integer idHorario) {
        List<Visitante> lista = new ArrayList<>();
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("SELECT v.* FROM visitante v INNER JOIN frequencia f ON v.matricula = f.id_visitante WHERE f.id_horario = ?");
            st.setInt(1, idHorario);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Visitante visitante = new Visitante(rs.getInt("matricula"), rs.getString("nome"), rs.getInt("idade"), rs.getString("genero"), rs.getString("nivel_ensino"), rs.getString("curso"), rs.getString("turno"), rs.getBoolean("possuiNEE"));
                lista.add(visitante);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public List<Jogo> buscarJogosHorario(Integer idHorario) {
        List<Jogo> lista = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("SELECT j.* FROM jogo j INNER JOIN uso_jogo uj ON j.id_jogo = uj.id_jogo WHERE uj.id_horario = ?");
            st.setInt(1, idHorario);
            rs = st.executeQuery();
            while(rs.next()){
                Jogo jogo = new Jogo(rs.getInt("id_jogo"), rs.getString("titulo"), rs.getString("tipo"), rs.getInt("min_numero_jogadores"), rs.getInt("max_numero_jogadores"), rs.getString("descricao"), rs.getString("marca"), rs.getInt("faixaEtaria"), rs.getInt("tempo_partida"));
                lista.add(jogo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }
}