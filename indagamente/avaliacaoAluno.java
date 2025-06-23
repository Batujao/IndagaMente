package com.mycompany.indagamente;

import controll.Conexao;
import java.time.LocalDateTime;
import java.sql.*;
import java.util.Map;
/**
 * Representa os dados da avaliação feita por um aluno.
 */
public class avaliacaoAluno {

    private int idAluno;
    private int idAval;
    private float notaFinal;
    
    private int qtdAcertos;
    private boolean status;
    private LocalDateTime dataFim;

    public avaliacaoAluno(int idAluno, int idAval, float notaFinal,
                          int qtdAcertos, boolean status, LocalDateTime dataFim) {
        this.idAluno = idAluno;
        this.idAval = idAval;
        this.notaFinal = notaFinal;
        
        this.qtdAcertos = qtdAcertos;
        this.status = status;
        this.dataFim = dataFim;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public int getIdAval() {
        return idAval;
    }

    public void setIdAval(int idAval) {
        this.idAval = idAval;
    }

    public float getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(float notaFinal) {
        this.notaFinal = notaFinal;
    }

  
    public int getQtdAcertos() {
        return qtdAcertos;
    }

    public void setQtdAcertos(int qtdAcertos) {
        this.qtdAcertos = qtdAcertos;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }
    public static String pegarAlternativaCorreta(int idPergunta) {
        String sql = "SELECT \"textoAlternativa\" FROM \"Resposta\" WHERE \"idPergunta\" = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, idPergunta);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("alternativaCorreta");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Recebe as respostas do aluno (map <idPergunta, alternativaEscolhida>)
    public static void processarRespostasAluno(int idAluno, int idAval, Map<Integer, String> respostasAluno) {
        int qtdAcertos = 0;
        for (Map.Entry<Integer, String> entry : respostasAluno.entrySet()) {
            int idPergunta = entry.getKey();
            String alternativaAluno = entry.getValue();

            String alternativaCorreta = pegarAlternativaCorreta(idPergunta);
            if (alternativaCorreta != null && alternativaCorreta.equalsIgnoreCase(alternativaAluno)) {
                qtdAcertos++;
            }
        }

        // Exemplo: nota = qtdAcertos / totalPerguntas * 10
        int totalPerguntas = respostasAluno.size();
        float notaFinal = (float) qtdAcertos / totalPerguntas * 10;

        // Tempo gasto e status podem ser definidos conforme sua lógica
        int tempoGasto = 0; // exemplo placeholder
        boolean status = true; // exemplo placeholder
        LocalDateTime dataFim = LocalDateTime.now();

        avaliacaoAluno resultado = new avaliacaoAluno(idAluno, idAval, notaFinal,qtdAcertos, status, dataFim);

        salvarResultado(resultado);
    }

    // Método para salvar o resultado final na tabela avaliacaoAluno
    public static void salvarResultado(avaliacaoAluno resultado) {
        String sql = "INSERT INTO \"Avaliacao-Aluno\" (\"idAluno\", \"idAval\", \"notaFinal\",\"qtdAcertos\", \"Status\", \"dataFim\") " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, resultado.getIdAluno());
            stmt.setInt(2, resultado.getIdAval());
            stmt.setFloat(3, resultado.getNotaFinal());
            stmt.setInt(4, resultado.getQtdAcertos());
            stmt.setBoolean(5, resultado.isStatus());
            stmt.setTimestamp(6, Timestamp.valueOf(resultado.getDataFim()));

            stmt.executeUpdate();
            System.out.println("Resultado da avaliação salvo com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao salvar resultado da avaliação: " + e.getMessage());
        }
        
        
    }
}
