package Estruturas;

import Classes.Endereco;
import Classes.Paciente;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PacienteEstrutura {

    private ArrayList<Paciente> lista;

    private static final String ARQUIVO = "C:/Users/Gustavo Ribeiro/OneDrive/Desktop/Engenharia de Software/Medical-Appointment-Scheduling-System/Project_Java/Project Software Engineer/Dados/Paciente.txt";

    public PacienteEstrutura() {
        this.lista = new ArrayList<>();
        garantirPasta();
        carregar();
    }

    public void adicionar(Paciente p) {
        lista.add(p);
        salvar();
    }

    public Paciente buscarPorCpf(String cpf) {
        for (Paciente p : lista) {
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Paciente> getLista() {
        return lista;
    }

    // Formato: nome;cpf;dataNasc;plano;telefone;rua;numero;cidade;estado
    public void salvar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO, false))) {
            for (Paciente p : lista) {
                String endRua    = p.getEndereco() != null ? p.getEndereco().getRua()    : "";
                String endNum    = p.getEndereco() != null ? p.getEndereco().getNumero() : "";
                String endCidade = p.getEndereco() != null ? p.getEndereco().getCidade() : "";
                String endEstado = p.getEndereco() != null ? p.getEndereco().getEstado() : "";

                pw.println(
                    p.getNome()           + ";" +
                    p.getCpf()            + ";" +
                    p.getDataNascimento() + ";" +
                    p.getPlanoSaude()     + ";" +
                    p.getTelefone()       + ";" +
                    endRua                + ";" +
                    endNum                + ";" +
                    endCidade             + ";" +
                    endEstado
                );
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar pacientes: " + e.getMessage());
        }
    }

    private void carregar() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.isBlank()) continue;
                String[] c = linha.split(";", -1);
                if (c.length < 9) continue;

                // Ordem salva: nome;cpf;dataNasc;plano;telefone;rua;numero;cidade;estado
                Endereco end = new Endereco(c[5], c[6], c[7], c[8]);
                Paciente p   = new Paciente(end, c[4], c[0], c[1], c[2], c[3]);
                lista.add(p);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar pacientes: " + e.getMessage());
        }
    }

    private void garantirPasta() {
        File pasta = new File(ARQUIVO).getParentFile();
        if (pasta != null && !pasta.exists()) {
            pasta.mkdirs();
        }
    }
}
