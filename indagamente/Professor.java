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
public class Professor extends Usuario {

    // Construtor padrão vazio (se precisar)
    public Professor() {
        
    }

    // Construtor que inicializa o professor com dados, seta categoria "Professor"
    public Professor(int idUsuario, String userName, String nome, String sobrenome, String senha) {
        super(idUsuario, userName, nome, sobrenome, senha, "Professor");
    }

    

  public List<Boletim> buscarBoletim() {
    List<Boletim> boletins = new ArrayList<>();

    String sql = "SELECT u.\"idUsuario\", u.\"Nome\", u.\"Sobrenome\", a.\"tituloAval\", aa.\"notaFinal\", aa.\"Status\" " +
                 "FROM \"Avaliacao-Aluno\" aa " +
                 "JOIN \"Usuario\" u ON aa.\"idAluno\" = u.\"idUsuario\" " +
                 "JOIN \"Avaliacao\" a ON aa.\"idAval\" = a.\"idAval\" " +
                 "WHERE a.\"idProfessor\" = ? " +
                 "AND u.\"Categoria\" = 'Aluno'";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, this.getIdUsuario());
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int idAluno = rs.getInt("idUsuario");
            String nome = rs.getString("Nome");
            String sobrenome = rs.getString("Sobrenome");
            String tituloAval = rs.getString("tituloAval");
            float notaFinal = rs.getFloat("notaFinal");
            boolean statusBool = rs.getBoolean("Status");

            String status = statusBool ? "Aprovado" : "Reprovado";

            Boletim boletim = new Boletim(idAluno, nome, sobrenome, tituloAval, notaFinal, status);
            boletins.add(boletim);
        }

    } catch (SQLException e) {
        System.err.println("Erro ao buscar boletim para professor: " + e.getMessage());
    }

    return boletins;
}




}
