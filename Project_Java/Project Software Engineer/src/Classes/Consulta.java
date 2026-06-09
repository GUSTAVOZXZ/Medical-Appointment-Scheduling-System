package Classes;

public class Consulta {

    private Paciente paciente;
    private Medico medico;
    private String data;
    private String horario;
    private String status;
    private String observacao;

    public Consulta(Paciente paciente, Medico medico, String data, String horario, String observacao) {
        this.paciente = paciente;
        this.medico = medico;
        this.data = data;
        this.horario = horario;
        this.observacao = observacao;
        this.status = "Agendada";
    }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    @Override
    public String toString() {
        return "=== CONSULTA ==="
                + "\n Paciente: "      + paciente.getNome()        + " | CPF: "  + paciente.getCpf()
                + "\n Médico: "        + medico.getNome()          + " | CRM: "  + medico.getCrm()
                + "\n Especialidade: " + medico.getEspecialidade()
                + "\n Data: "          + data
                + "\n Horário: "       + horario
                + "\n Status: "        + status
                + "\n Obs: "           + observacao;
    }
}
