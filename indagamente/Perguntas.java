/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.indagamente;
import java.sql.*;
/**
 *
 * @author joao vitor antoniel
 */
public class Perguntas {
    private int idPergunta;
    private int idAval;
    private String Enunciado;
    private Integer idResposta;

    public Perguntas(int idPergunta, int idAval, String Enunciado, int idResposta) {
        this.idPergunta = idPergunta;
        this.idAval = idAval;
        this.Enunciado = Enunciado;
        this.idResposta = null;
    }
    public Perguntas(int idAval, String enunciado) {
    this.idAval = idAval;
    this.Enunciado = enunciado;
    }
 

    public int getIdPergunta() {
        return idPergunta;
    }

    public void setIdPergunta(int idPergunta) {
        this.idPergunta = idPergunta;
    }

    public int getIdAval() {
        return idAval;
    }

    public void setIdAval(int idAval) {
        this.idAval = idAval;
    }

    public String getEnunciado() {
        return Enunciado;
    }

    public void setEnunciado(String Enunciado) {
        this.Enunciado = Enunciado;
    }

    public int getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(int idResposta) {
        this.idResposta = idResposta;
    }
  

 public boolean inserirPergunta(Connection conn) {
    
    String sql = "INSERT INTO \"Perguntas\" (\"idAval\", \"Enunciado\") " +
                 "VALUES (?, ?) RETURNING \"idPergunta\"";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idAval);
        stmt.setString(2, Enunciado);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                // 'idPergunta' aqui deve bater exatamente com o RETURNING acima
                this.idPergunta = rs.getInt("idPergunta");
                return true;
            }
        }
    } catch (SQLException e) {
        System.err.println("Erro ao inserir pergunta: " + e.getMessage());
        e.printStackTrace();
    }
    return false;
}

    /** Atualiza apenas a coluna idresposta da pergunta já existente */
   public boolean atualizarIdResposta(Connection conn) {
    // Assumimos que idPergunta e idResposta já estejam preenchidos
    String sql = "UPDATE \"Perguntas\" SET \"idResposta\" = ? WHERE \"idPergunta\" = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, this.idResposta);
        stmt.setInt(2, this.idPergunta);
        return stmt.executeUpdate() == 1;
    } catch (SQLException e) {
        System.err.println("Erro ao atualizar idResposta: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}
}
