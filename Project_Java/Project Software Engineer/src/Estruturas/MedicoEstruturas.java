package Estruturas;

import Classes.Medico;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MedicoEstruturas {

    private ArrayList<Medico> lista;

    private static final String ARQUIVO = "C:/Users/Gustavo Ribeiro/OneDrive/Desktop/Engenharia de Software/Medical-Appointment-Scheduling-System/Project_Java/Project Software Engineer/Dados/Medico.txt";

    public MedicoEstruturas() {
        this.lista = new ArrayList<>();
        garantirPasta();
        carregar();
    }

    public void adicionar(Medico m) {
        lista.add(m);
        salvar();
    }

    public Medico buscarPorCrm(String crm) {
        int crmBusca = Integer.parseInt(crm);
        for (Medico m : lista) {
            if (m.getCrm() == crmBusca) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Medico> getLista() {
        return lista;
    }

    // Formato: nome;cpf;dataNasc;crm;especialidade
    public void salvar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO, false))) {
            for (Medico m : lista) {
                pw.println(
                    m.getNome()           + ";" +
                    m.getCpf()            + ";" +
                    m.getDataNascimento() + ";" +
                    m.getCrm()            + ";" +
                    m.getEspecialidade()
                );
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar médicos: " + e.getMessage());
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
                if (c.length < 5) continue;

                // Ordem salva: nome;cpf;dataNasc;crm;especialidade
                Medico m = new Medico(Integer.parseInt(c[3]), c[4], c[0], c[1], c[2]);
                lista.add(m);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar médicos: " + e.getMessage());
        }
    }

    private void garantirPasta() {
        File pasta = new File(ARQUIVO).getParentFile();
        if (pasta != null && !pasta.exists()) {
            pasta.mkdirs();
        }
    }
}
