/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.indagamente;
import controll.Conexao;
import com.mycompany.indagamente.Boletim;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author joao vitor antoniel
 */
public class Aluno extends Usuario{

    public Aluno(int idUsuario, String userName, String Nome, String Sobrenome, String Senha, String Categoria) {
        super(idUsuario, userName, Nome, Sobrenome, Senha, "Aluno");
    }
    
    public List<Boletim> buscarBoletimAluno() {
    List<Boletim> boletins = new ArrayList<>();

    String sql = "SELECT a.\"tituloAval\", aa.\"notaFinal\", aa.\"Status\" " +
                 "FROM \"Avaliacao-Aluno\" aa " +
                 "JOIN \"Avaliacao\" a ON aa.\"idAval\" = a.\"idAval\" " +
                 "WHERE aa.\"idAluno\" = ?";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, this.getIdUsuario());  // ID do aluno (vindo da tabela Usuario)

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String titulo = rs.getString("tituloAval");
            float nota = rs.getFloat("notaFinal");
            boolean statusBool = rs.getBoolean("Status");

            String status = statusBool ? "Aprovado" : "Reprovado";

            Boletim boletim = new Boletim(titulo, nota, status);
            boletins.add(boletim);
        }

    } catch (SQLException e) {
        System.err.println("Erro ao buscar boletim para aluno: " + e.getMessage());
    }

    return boletins;
}


}
