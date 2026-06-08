package Estruturas;

import Classes.Consulta;
import Classes.Paciente;
import Classes.Medico;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;

public class ConsultasEstruturas {
    
    private ArrayList<Consulta> lista;

    private static final String ARQUIVO = "Dados/Consulta.txt";

    private PacienteEstrutura pacienteEst;
    private MedicoEstruturas medicoest;

    public ConsultasEstruturas(PacienteEstrutura pacienteEst, MedicoEstruturas medicoest){
        this.pacienteEst = pacienteEst;
        this.medicoest = medicoest;
        lista = new ArrayList<>();
        carregar();
    }

    public void agendar(Consulta c){
        lista.add(c);
        salvar();
        System.out.println("|| Consulta agendada com sucesso! ||");
    }

    public ArrayList<Consulta> getLista(){
        return lista;
    }

    public void salvar(){
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO, false));
            for(Consulta c: lista){
                pw.println(
                    c.getPaciente().getCpf() + ";" + 
                    c.getMedico().getCrm() + ";" +
                    c.getData() + ";" +
                    c.getHorario() + ";" + 
                    c.getStatus() + ";" +
                    c.getObservacao()
                );
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar os arquivos "+e.getMessage());
        }
    }

    private void carregar(){
        try {
            File arquivo = new File(ARQUIVO);
            if(!arquivo.exists())return;

            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            String linha;
            while((linha = br.readLine()) != null){
                String[] c = linha.split(";");

                Paciente p = pacienteEst.buscarPorCpf(c[0]);
                Medico m = medicoest.buscarPorCrm(c[1]);

                if(p != null && m != null){
                    Consulta consulta = new Consulta(p, m, c[2], c[3], c[5]);
                    consulta.setStatus(c[4]);
                    lista.add(consulta);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("|| Erro ao carregar consultas! ||"+ e.getMessage());
        }
    }

    public String gerarRelatorio() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- CONSULTAS AGENDADAS ("+ lista.size() + ") ---\n");
        for(Consulta c : lista){
            sb.append(c.toString() + "\n\n");
        }
        return sb.toString();
    }
}
