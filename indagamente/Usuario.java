package com.mycompany.indagamente;

import controll.Conexao;
import java.sql.*;

public class Usuario {
    private int idUsuario;
    private String userName;
    private String Nome;
    private String Sobrenome;
    private String Senha;
    private String Categoria;

    
    public Usuario() {}

    public Usuario(int idUsuario, String userName, String Nome, String Sobrenome, String Senha, String Categoria) {
        this.idUsuario = idUsuario;
        this.userName = userName;
        this.Nome = Nome;
        this.Sobrenome = Sobrenome;
        this.Senha = Senha;
        this.Categoria = Categoria;
    }

    // Getters e setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getNome() { return Nome; }
    public void setNome(String Nome) { this.Nome = Nome; }

    public String getSobrenome() { return Sobrenome; }
    public void setSobrenome(String Sobrenome) { this.Sobrenome = Sobrenome; }

    public String getSenha() { return Senha; }
    public void setSenha(String Senha) { this.Senha = Senha; }

    public String getCategoria() { return Categoria; }
    public void setCategoria(String Categoria) { this.Categoria = Categoria; }

    public void salvar() {
        String sqlSalvar = "INSERT INTO \"Usuario\" (\"userName\", \"Nome\", \"Sobrenome\", \"Senha\", \"Categoria\") VALUES(?,?,?,?,?)";
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sqlSalvar)) {
            stmt.setString(1, this.userName);
            stmt.setString(2, this.Nome);
            stmt.setString(3, this.Sobrenome);
            stmt.setString(4, this.Senha);
            stmt.setString(5, this.Categoria);
            stmt.executeUpdate();
            System.out.println("Usuário salvo com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir os dados: " + e.getMessage());
        }
    }

    public static Usuario autenticar(String userName, String senha) {
    String sql = "SELECT * FROM \"Usuario\" WHERE \"userName\" = ? AND \"Senha\" = ?";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, userName);
        stmt.setString(2, senha);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setUserName(rs.getString("userName"));
            usuario.setNome(rs.getString("Nome"));
            usuario.setSobrenome(rs.getString("Sobrenome"));
            usuario.setSenha(rs.getString("Senha"));
            usuario.setCategoria(rs.getString("Categoria"));
            return usuario;
        } else {
            return null;
        }

    } catch (SQLException e) {
        System.out.println("Erro ao fazer login: " + e.getMessage());
        return null;
    }
}
    
    public void deletarConta(){
        String sql = "DELETE FROM \"Usuario\" WHERE \"idUsuario\" = ?";
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
return this.idUsuario;
    }
   public static Usuario buscarPorId(int id) {
    Usuario usuario = null;

    try (Connection con = Conexao.conectar()) {
        String sql = "SELECT \"idUsuario\", \"userName\", \"Nome\", \"Sobrenome\", \"Senha\", \"Categoria\" FROM \"Usuario\" WHERE \"idUsuario\" = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setUserName(rs.getString("userName"));
            usuario.setNome(rs.getString("nome"));
            usuario.setSobrenome(rs.getString("sobrenome"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setCategoria(rs.getString("categoria"));
        }

        rs.close();
        ps.close();
    } catch (SQLException e) {
        System.out.println("Erro ao buscar usuário por id: " + e.getMessage());
    }

    return usuario;
}

}
