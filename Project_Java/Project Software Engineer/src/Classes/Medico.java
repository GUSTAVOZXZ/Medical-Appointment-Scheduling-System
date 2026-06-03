package Classes;

public class Medico extends Pessoa{
    private int crm;
    private String especialidade;

    public Medico(int crm, String especialidade, String nome, String cpf, String dataNascimento) {
        super(nome, cpf, dataNascimento);
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public int getCrm() {return crm;}
    public void setCrm(int crm) {this.crm = crm;}

    public String getEspecialidade() {return especialidade;}
    public void setEspecialidade(String especialidade) {this.especialidade = especialidade;}

    @Override
    public String getTipo(){return "Médico";}

    @Override
    public String toString() {
        return super.toString()+" CRM= " + crm + ", Especialidade= " + especialidade + "]";
    }

    

}
