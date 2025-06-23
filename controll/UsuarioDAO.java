/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controll;
import java.sql.*;
import com.mycompany.indagamente.Usuario;
/**
 *
 * @author joao vitor antoniel
 */
public class UsuarioDAO {
     public void salvar(Usuario usuario) {
        String sqlSalvar = "INSERT INTO \"Usuario\" (\"userName\", \"Nome\", \"Sobrenome\", \"Senha\", \"Categoria\") VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sqlSalvar)) {
            stmt.setString(1, usuario.getUserName());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getSobrenome());
            stmt.setString(4, usuario.getSenha());
            stmt.setString(5, usuario.getCategoria());
            stmt.executeUpdate();
            System.out.println("Usuário salvo com sucesso (via UsuarioDAO)!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar usuário (DAO): " + e.getMessage());
        }
    }

    public Usuario autenticar(String userName, String senha) {
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
            }
        } catch (SQLException e) {
            System.out.println("Erro ao autenticar (DAO): " + e.getMessage());
        }
        return null;
    }

    public void deletarConta(int idUsuario) {
        String sql = "DELETE FROM \"Usuario\" WHERE \"idUsuario\" = ?";
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
            System.out.println("Usuário deletado com sucesso (via UsuarioDAO)!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar usuário (DAO): " + e.getMessage());
        }
    }
    
}
