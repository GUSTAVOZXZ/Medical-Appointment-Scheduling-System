package Estruturas;

import Classes.Medico;
import java.util.ArrayList;

public class MedicoEstruturas {
    private ArrayList<Medico> lista;

    public MedicoEstruturas() {
        this.lista = new ArrayList<>();
    }

    public void adicionar(Medico m) {
        lista.add(m);
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
}