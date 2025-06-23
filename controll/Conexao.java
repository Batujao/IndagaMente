package controll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // Configurações do banco
    private static final String URL = "jdbc:postgresql://localhost:5432/IndagaMente";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "2811";

    // Método para conectar ao banco
    public static Connection conectar() {
        try {
            // Carrega o driver (opcional com JDBC atual)
            Class.forName("org.postgresql.Driver");
            System.out.println("Foi conectado com sucesso!");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver PostgreSQL não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro na conexão: " + e.getMessage());
        }
        return null;
    }
}
