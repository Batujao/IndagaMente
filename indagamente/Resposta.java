/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.indagamente;
import controll.Conexao;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author joao vitor antoniel
 */
public class Resposta {
    private int idResposta;
    private int idPergunta;
    private String alternativaCorreta;
     
    
 

    public Resposta(int idResposta, int idPergunta, String alternativaCorreta) {
        this.idResposta = idResposta;
        this.idPergunta = idPergunta;
        this.alternativaCorreta = alternativaCorreta;
    }
    public Resposta(int idPergunta, String alternativaCorreta) {
    this.idPergunta = idPergunta;
    this.alternativaCorreta = alternativaCorreta;
}

    public int getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(int idResposta) {
        this.idResposta = idResposta;
    }

    public int getIdPergunta() {
        return idPergunta;
    }

    public void setIdPergunta(int idPergunta) {
        this.idPergunta = idPergunta;
    }

    public String getAlternativaCorreta() {
        return alternativaCorreta;
    }

    public void setAlternativaCorreta(String alternativaCorreta) {
        this.alternativaCorreta = alternativaCorreta;
    }


   
    
    
    public void salvarRespostaCorreta(int idPergunta, String alternativaCorreta) {
    String sql = "INSERT INTO resposta (id_pergunta, alternativa) VALUES (?, ?)";

    try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idPergunta);
        stmt.setString(2, alternativaCorreta);
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Resposta correta salva com sucesso!");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao salvar a resposta correta: " + e.getMessage());
    }
}

    public boolean inserirResposta(Connection conn) {
    String sql = "INSERT INTO \"Resposta\" (\"idPergunta\", \"textoAlternativa\") VALUES (?, ?) RETURNING \"idResposta\"";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idPergunta);
        stmt.setString(2, alternativaCorreta);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                this.idResposta = rs.getInt("idResposta");
                return true;
            }
        }
    } catch (SQLException e) {
        System.err.println("Erro ao inserir resposta: " + e.getMessage());
        e.printStackTrace();
    }
    return false;
}

    
}
