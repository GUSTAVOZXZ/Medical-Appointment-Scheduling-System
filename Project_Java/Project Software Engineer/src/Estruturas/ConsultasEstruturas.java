package Estruturas;

import Classes.Consulta;
import Classes.Medico;
import Classes.Paciente;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ConsultasEstruturas {

    private ArrayList<Consulta> lista;

    private static final String ARQUIVO = "C:/Users/Gustavo Ribeiro/OneDrive/Desktop/Engenharia de Software/Medical-Appointment-Scheduling-System/Project_Java/Project Software Engineer/Dados/Consulta.txt";

    private PacienteEstrutura pacienteEst;
    private MedicoEstruturas medicoEst;

    public ConsultasEstruturas(PacienteEstrutura pacienteEst, MedicoEstruturas medicoEst) {
        this.pacienteEst = pacienteEst;
        this.medicoEst   = medicoEst;
        lista = new ArrayList<>();
        garantirPasta();
        carregar();
    }

    public void agendar(Consulta c) {
        lista.add(c);
        salvar();
        System.out.println("|| Consulta agendada com sucesso! ||");
    }

    private void garantirPasta() {
        File pasta = new File(ARQUIVO).getParentFile();
        if (pasta != null && !pasta.exists()) {
            pasta.mkdirs();
        }
    }

    public ArrayList<Consulta> getLista() {
        return lista;
    }

    // Formato: cpfPaciente;crmMedico;data;horario;status;observacao
    // Observacao: ";" substituido por "|" para nao quebrar o split
    public void salvar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO, false))) {
            for (Consulta c : lista) {
                String obsSegura = c.getObservacao().replace(";", "|");
                pw.println(
                    c.getPaciente().getCpf() + ";" +
                    c.getMedico().getCrm()   + ";" +
                    c.getData()              + ";" +
                    c.getHorario()           + ";" +
                    c.getStatus()            + ";" +
                    obsSegura
                );
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar consultas: " + e.getMessage());
        }
    }

    private void carregar() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.isBlank()) continue;
                // Limite 6: garante que observacao (ultimo campo) nao seja partido
                String[] c = linha.split(";", 6);
                if (c.length < 6) continue;

                Paciente p = pacienteEst.buscarPorCpf(c[0]);
                Medico m   = medicoEst.buscarPorCrm(c[1]);

                if (p != null && m != null) {
                    Consulta consulta = new Consulta(p, m, c[2], c[3], c[5]);
                    consulta.setStatus(c[4]);
                    lista.add(consulta);
                } else {
                    System.out.println("Aviso: consulta ignorada (CPF: " + c[0] + " | CRM: " + c[1] + " nao encontrado)");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar consultas: " + e.getMessage());
        }
    }

    public String gerarRelatorio() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- CONSULTAS AGENDADAS (" + lista.size() + ") ---\n");
        for (Consulta c : lista) {
            sb.append(c.toString()).append("\n\n");
        }
        return sb.toString();
    }
}
