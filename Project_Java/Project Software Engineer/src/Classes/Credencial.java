package Classes;

import java.util.ArrayList;

public class Credencial {

    private String usuario;
    private String senha;
    // Guarda as últimas 3 senhas para impedir reutilização
    private ArrayList<String> historicoSenhas;

    public Credencial(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
        this.historicoSenhas = new ArrayList<>();
    }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getSenha() { return senha; }

    // Ao trocar a senha: guarda a atual no histórico antes de substituir
    public void setSenha(String novaSenha) {
        if (historicoSenhas.size() >= 3) {
            historicoSenhas.remove(0); // remove a mais antiga para manter só 3
        }
        historicoSenhas.add(this.senha);
        this.senha = novaSenha;
    }

    // Verifica se a nova senha já foi usada nas últimas 3
    public boolean senhaJaUsada(String novaSenha) {
        return historicoSenhas.contains(novaSenha) || this.senha.equals(novaSenha);
    }

    public ArrayList<String> getHistoricoSenhas() { return historicoSenhas; }
    public void setHistoricoSenhas(ArrayList<String> historico) { this.historicoSenhas = historico; }

    @Override
    public String toString() {
        return "Credencial[Usuario= " + usuario + ", Historico de senhas= " + historicoSenhas.size() + " registro(s)]";
    }
}
