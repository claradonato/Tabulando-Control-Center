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
import java.time.DayOfWeek;
import java.time.LocalDate;
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
            st = conn.prepareStatement("insert into horario(data_horario, turno, hora, nome_monitor, status_horario) values (?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            st.setDate(1, java.sql.Date.valueOf(h.getDataHorario()));
            st.setString(2, h.getTurno());
            st.setString(3, h.getHora());
            st.setString(4, h.getNomeMonitor());
            st.setString(5, h.getStatusHorario());

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
            st = conn.prepareStatement("update horario set data_horario = ?, turno = ?, hora = ?, nome_monitor = ?, status_horario = ? where id_horario=?");
            st.setString(1, String.valueOf(h.getDataHorario()));
            st.setString(2, h.getTurno());
            st.setString(3, h.getHora());
            st.setString(4, h.getNomeMonitor());
            st.setString(5, h.getStatusHorario());
            st.setInt(6, h.getId_horario());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Horario> listarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Horario> horarios = new ArrayList<Horario>();

        try {
            st = conn.prepareStatement("select * from horario order by data_horario asc");
            rs = st.executeQuery();
            while (rs.next()) {
                Horario h = new Horario(rs.getInt("id_horario"), rs.getDate("data_horario").toLocalDate(), rs.getString("turno"), rs.getString("hora"), rs.getString("nome_monitor"), rs.getString("status_horario"));
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
    public List<Horario> buscarPorSemana(LocalDate dataReferencia) {
        List<Horario> lista = new ArrayList<>();
        LocalDate inicioSemana = dataReferencia.with(DayOfWeek.MONDAY);
        LocalDate fimSemana = dataReferencia.with(DayOfWeek.FRIDAY);

        try{
            PreparedStatement st = conn.prepareStatement("SELECT * FROM horario WHERE data_horario BETWEEN ? AND ? ORDER BY data_horario");
            st.setDate(1, java.sql.Date.valueOf(inicioSemana));
            st.setDate(2, java.sql.Date.valueOf(fimSemana));
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Horario h = new Horario(rs.getInt("id_horario"), rs.getDate("data_horario").toLocalDate(), rs.getString("turno"), rs.getString("hora"), rs.getString("nome_monitor"), rs.getString("status_horario"));
                lista.add(h);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public List<String> listarMonitores() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<String> lista = new ArrayList<>();

        try {
            st = conn.prepareStatement("select distinct nome_monitor from horario order by nome_monitor");
            rs = st.executeQuery();
            while(rs.next()){
                lista.add(rs.getString("nome_monitor"));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        return lista;
    }

    @Override
    public List<Horario> buscarPorMonitor(String monitor) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Horario> lista = new ArrayList<>();

        try {
            st = conn.prepareStatement("select * from horario where nome_monitor = ? order by data_horario");
            st.setString(1, monitor);
            rs = st.executeQuery();

            while(rs.next()){
                Horario h = new Horario(rs.getInt("id_horario"), rs.getDate("data_horario").toLocalDate(), rs.getString("turno"), rs.getString("hora"), rs.getString("nome_monitor"), rs.getString("status_horario"));
                lista.add(h);
            }

            return lista;

        } catch(SQLException e){
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Horario> buscarPorSemanaEMonitor(LocalDate data, String monitor){
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Horario> lista = new ArrayList<>();
        LocalDate inicioSemana = data.with(DayOfWeek.MONDAY);
        LocalDate fimSemana = data.with(DayOfWeek.FRIDAY);

        try {
            st = conn.prepareStatement("select * from horario where data_horario between ? and ? and nome_monitor = ? ORDER BY data_horario order by data_horario;");
            st.setDate(1, java.sql.Date.valueOf(inicioSemana));
            st.setDate(2, java.sql.Date.valueOf(fimSemana));
            st.setString(3, monitor);
            rs = st.executeQuery();

            while(rs.next()){
                Horario h = new Horario(rs.getInt("id_horario"), rs.getDate("data_horario").toLocalDate(), rs.getString("turno"), rs.getString("hora"), rs.getString("nome_monitor"), rs.getString("status_horario"));
                lista.add(h);
            }

            return lista;

        } catch(SQLException e){
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Horario> buscarPorStatus(String status) {
        List<Horario> lista = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement("select * from horario where status_horario = ? order by data_horario");
            st.setString(1, status);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                lista.add(new Horario(rs.getInt("id_horario"), rs.getDate("data_horario").toLocalDate(), rs.getString("turno"), rs.getString("hora"), rs.getString("nome_monitor"), rs.getString("status_horario")));
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public List<Horario> buscarPorSemanaEStatus(LocalDate data, String status) {
        List<Horario> lista = new ArrayList<>();
        LocalDate inicioSemana = data.with(DayOfWeek.MONDAY);
        LocalDate fimSemana = data.with(DayOfWeek.FRIDAY);

        try {
            PreparedStatement st = conn.prepareStatement("select * from horario where status_horario = ? and data_horario between ? and ? order by data_horario");
            st.setString(1, status);
            st.setDate(2, java.sql.Date.valueOf(inicioSemana));
            st.setDate(3, java.sql.Date.valueOf(fimSemana));

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                lista.add(new Horario(rs.getInt("id_horario"), rs.getDate("data_horario").toLocalDate(), rs.getString("turno"), rs.getString("hora"), rs.getString("nome_monitor"), rs.getString("status_horario")));
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public List<Horario> buscarPorMonitorEStatus(String monitor, String status) {
        List<Horario> lista = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement("select * from horario where status_horario = ? and nome_monitor = ? order by data_horario");
            st.setString(1, status);
            st.setString(2, monitor);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                lista.add(new Horario(rs.getInt("id_horario"), rs.getDate("data_horario").toLocalDate(), rs.getString("turno"), rs.getString("hora"), rs.getString("nome_monitor"), rs.getString("status_horario")));
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public List<Horario> buscarPorSemanaMonitorEStatus(LocalDate data, String monitor, String status) {
        List<Horario> lista = new ArrayList<>();
        LocalDate inicioSemana = data.with(DayOfWeek.MONDAY);
        LocalDate fimSemana = data.with(DayOfWeek.FRIDAY);

        try {
            PreparedStatement st = conn.prepareStatement("select * from horario where status_horario = ? and nome_monitor = ? and data_horario between ? and ? order by data_horario");
            st.setString(1, status);
            st.setString(2, monitor);
            st.setDate(3, java.sql.Date.valueOf(inicioSemana));
            st.setDate(4, java.sql.Date.valueOf(fimSemana));

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                lista.add(new Horario(rs.getInt("id_horario"), rs.getDate("data_horario").toLocalDate(), rs.getString("turno"), rs.getString("hora"), rs.getString("nome_monitor"), rs.getString("status_horario")));
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return lista;
    }

    // Métodos relacionados com outras tabelas do banco -------------------------------------------------------------

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

    @Override
    public void removerVisitanteHorario(Integer matricula, Integer idHorario) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("delete from frequencia where id_visitante = ? and id_horario = ?");
            st.setInt(1, matricula);
            st.setInt(2, idHorario);
            st.executeUpdate();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removerJogoHorario(Integer idJogo, Integer idHorario) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("delete from uso_jogo where id_jogo = ? and id_horario = ?");
            st.setInt(1,idJogo);
            st.setInt(2,idHorario);
            st.executeUpdate();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int contarVisitantesHorario(Integer idHorario) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("select count(*) from frequencia where id_horario = ?");
            st.setInt(1,idHorario);
            ResultSet rs = st.executeQuery();

            if(rs.next()){
                return rs.getInt(1);
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int contarJogosHorario(Integer idHorario) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("select count(*) from uso_jogo where id_horario = ?");
            st.setInt(1,idHorario);
            ResultSet rs = st.executeQuery();

            if(rs.next()){
                return rs.getInt(1);
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }


}