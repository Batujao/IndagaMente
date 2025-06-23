/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.indagamente;

/**
 *
 * @author joao vitor antoniel
 */
public class Boletim {
    private int idAluno;
    private String nome;
    private String sobrenome;
    private String tituloAval;
    private float notaFinal;
    private String status;

   public Boletim(int idAluno, String nome, String sobrenome, String tituloAval, float notaFinal, String status) {
    this.idAluno = idAluno;
    this.nome = nome;
    this.sobrenome = sobrenome;
    this.tituloAval = tituloAval;
    this.notaFinal = notaFinal;
    this.status = status;
}
   public Boletim(String tituloAval, float notaFinal, String status) {
    this.tituloAval = tituloAval;
    this.notaFinal = notaFinal;
    this.status = status;
}

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTituloAval() {
        return tituloAval;
    }

    public void setTituloAval(String tituloAval) {
        this.tituloAval = tituloAval;
    }

    public float getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(float notaFinal) {
        this.notaFinal = notaFinal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   
    
}
