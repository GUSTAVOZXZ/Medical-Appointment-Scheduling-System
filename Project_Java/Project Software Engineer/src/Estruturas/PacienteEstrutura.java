package Estruturas;

import Classes.Paciente;
import java.util.ArrayList;

public class PacienteEstrutura {
    private ArrayList<Paciente> lista;

    public PacienteEstrutura() {
        this.lista = new ArrayList<>();
    }

    public Paciente buscarPorCpf(String cpf) {
        for (Paciente p : lista) {
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }
        return null;
    }

    public void adicionar(Paciente p) {
        lista.add(p);
    }
}
