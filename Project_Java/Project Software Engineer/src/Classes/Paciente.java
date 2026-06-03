package Classes;

public class Paciente extends Pessoa{
    private String planoSaude;
    private String telefone;
    private Endereco endereco;

    public Paciente(Endereco endereco, String telefone, String nome, String cpf, String dataNascimento, String planoSaude) {
        super(nome, cpf, dataNascimento);
        this.planoSaude = planoSaude;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public String getTelefone() {return telefone;}
    public void setTelefone(String telefone) {this.telefone = telefone;}

    public Endereco getEndereco() {return endereco;}
    public void setEndereco(Endereco endereco) {this.endereco = endereco;}

    public String getPlanoSaude() {return planoSaude;}
    public void setPlanoSaude(String planoSaude) {this.planoSaude = planoSaude;}

    @Override
    public String getTipo(){return "Paciente";}

    @Override
    public String toString() {
        return super.toString()+", Plano de Saúde: " + planoSaude + ", Telefone= " + telefone + ", Endereço= " + endereco + "]";
    }

   

    


}
