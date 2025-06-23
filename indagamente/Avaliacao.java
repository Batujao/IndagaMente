package com.mycompany.indagamente;

import controll.Conexao;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma Avaliacao no sistema.
 * Contém métodos para manipulação e consulta no banco de dados.
 * 
 * @author joao vitor antoniel
 */
public class Avaliacao {

    private int idAval;
    private String tituloAval;
    private int idProfessor;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String senhaAcesso;

    public Avaliacao() {
    }

    public Avaliacao(int idAval, String tituloAval, int idProfessor,
                     LocalDateTime dataInicio, LocalDateTime dataFim, String senhaAcesso) {
        this.idAval = idAval;
        this.tituloAval = tituloAval;
        this.idProfessor = idProfessor;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.senhaAcesso = senhaAcesso;
    }

    // Getters e Setters
    public int getIdAval() {
        return idAval;
    }

    public void setIdAval(int idAval) {
        this.idAval = idAval;
    }

    public String getTituloAval() {
        return tituloAval;
    }

    public void setTituloAval(String tituloAval) {
        this.tituloAval = tituloAval;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public String getSenhaAcesso() {
        return senhaAcesso;
    }

    public void setSenhaAcesso(String senhaAcesso) {
        this.senhaAcesso = senhaAcesso;
    }

    /**
     * Verifica se uma avaliação com o id especificado existe no banco.
     * @param idAval id da avaliação
     * @return true se existir, false caso contrário
     */
    @SuppressWarnings("javadoc")
    public static boolean existeIdAval(int idAval) {
        String sql = "SELECT COUNT(*) FROM \"Avaliacao\" WHERE \"idAval\" = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAval);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar idAval: " + e.getMessage());
        }
        return false;
    }

    /**
     * Valida se a senha informada para uma avaliação está correta.
     * @param idAval id da avaliação
     * @param senha senha informada
     * @return true se a senha estiver correta, false caso contrário
     */
    @SuppressWarnings("javadoc")
    public static boolean validarSenha(int idAval, String senha) {
        String sql = "SELECT \"senhaAcesso\" FROM \"Avaliacao\" WHERE \"idAval\" = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAval);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String senhaCorreta = rs.getString("senhaAcesso");
                return senhaCorreta.equals(senha);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao validar senha: " + e.getMessage());
        }
        return false;
    }

    /**
     * Insere uma nova avaliação no banco de dados.
     * @param aval objeto Avaliacao com os dados a serem inseridos
     */
    @SuppressWarnings("javadoc")
    public static void inserirAvaliacao(Avaliacao aval) {
        String sql = "INSERT INTO \"Avaliacao\" (\"idAval\", \"tituloAval\", \"idProfessor\", \"dataInicio\", \"dataFim\", \"senhaAcesso\") VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, aval.getIdAval());
            stmt.setString(2, aval.getTituloAval());
            stmt.setInt(3, aval.getIdProfessor());
            stmt.setTimestamp(4, Timestamp.valueOf(aval.getDataInicio()));
            stmt.setTimestamp(5, Timestamp.valueOf(aval.getDataFim()));
            stmt.setString(6, aval.getSenhaAcesso());

            stmt.executeUpdate();
            System.out.println("Avaliação inserida com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir avaliação: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carrega a lista de enunciados das perguntas de uma avaliação.
     * @param idAval id da avaliação
     * @return lista de enunciados em ordem crescente de idPergunta
     */
    @SuppressWarnings("javadoc")
    public static List<String> carregarEnunciados(int idAval) {
        List<String> enunciados = new ArrayList<>();
        String sql = "SELECT \"Enunciado\" FROM \"Perguntas\" WHERE \"idAval\" = ? ORDER BY \"idPergunta\" ASC";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAval);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                enunciados.add(rs.getString("Enunciado"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao carregar enunciados: " + e.getMessage());
        }
        return enunciados;
    }

    /**
     * Carrega as alternativas corretas das perguntas de uma avaliação.
     * @param idAval id da avaliação
     * @return lista das alternativas corretas em ordem crescente de idPergunta
     */
    @SuppressWarnings("javadoc")
    public static List<String> carregarAlternativasCorretas(int idAval) {
    List<String> alternativas = new ArrayList<>();
    String sql = "SELECT r.\"textoAlternativa\" " +
                 "FROM \"Perguntas\" p " +
                 "JOIN \"Resposta\" r ON p.\"idResposta\" = r.\"idResposta\" " +
                 "WHERE p.\"idAval\" = ? " +
                 "ORDER BY p.\"idPergunta\" ASC";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idAval);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            alternativas.add(rs.getString("textoAlternativa"));
        }

    } catch (SQLException e) {
        System.err.println("Erro ao carregar alternativas corretas: " + e.getMessage());
    }
    return alternativas;
}
}
