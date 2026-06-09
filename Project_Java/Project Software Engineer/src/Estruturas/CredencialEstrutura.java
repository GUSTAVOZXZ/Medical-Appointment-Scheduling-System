package Estruturas;

import Classes.Credencial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CredencialEstrutura {

    private Credencial credencial;

    private static final String ARQUIVO        = "C:/Users/Gustavo Ribeiro/OneDrive/Desktop/Engenharia de Software/Medical-Appointment-Scheduling-System/Project_Java/Project Software Engineer/Dados/Credencial.txt";
    private static final String USUARIO_PADRAO = "admin";
    private static final String SENHA_PADRAO   = "1234";
    private static final int    MAX_TENTATIVAS = 3;

    public CredencialEstrutura() {
        garantirPasta();
        carregar();
    }

    // Retorna true se login bem-sucedido, false após 3 erros
    public boolean realizarLogin(java.util.Scanner scanner) {
        int tentativas = 0;

        while (tentativas < MAX_TENTATIVAS) {
            System.out.print("Usuário: ");
            String usuario = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            if (usuario.equals(credencial.getUsuario()) && senha.equals(credencial.getSenha())) {
                System.out.println("\n|| Login realizado com sucesso! Bem-vindo, " + usuario + "! ||");
                return true;
            }

            tentativas++;
            int restantes = MAX_TENTATIVAS - tentativas;

            if (restantes > 0) {
                System.out.println("Usuário ou senha incorretos. Tentativas restantes: " + restantes);
            }
        }

        System.out.println("\n!! Número máximo de tentativas atingido !!");
        return false;
    }

    // Fluxo de redefinição com validação das últimas 3 senhas
    public void redefinirSenha(java.util.Scanner scanner) {
        System.out.println("\n--- REDEFINIR SENHA ---");
        System.out.print("Informe o usuário: ");
        String usuario = scanner.nextLine();

        if (!usuario.equals(credencial.getUsuario())) {
            System.out.println("Usuário não reconhecido. Redefinição cancelada.");
            return;
        }

        System.out.print("Nova senha: ");
        String novaSenha = scanner.nextLine();
        System.out.print("Confirme a nova senha: ");
        String confirmacao = scanner.nextLine();

        if (!novaSenha.equals(confirmacao)) {
            System.out.println("As senhas não coincidem. Redefinição cancelada.");
            return;
        }

        if (credencial.senhaJaUsada(novaSenha)) {
            System.out.println("Essa senha já foi utilizada recentemente. Escolha uma senha diferente das últimas 3.");
            return;
        }

        credencial.setSenha(novaSenha);
        salvar();
        System.out.println("|| Senha redefinida com sucesso! ||");
    }

    // Formato: usuario;senhaAtual;hist1;hist2;hist3
    // Campos de histórico podem estar vazios se ainda não houver 3 trocas
    private void salvar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO, false))) {
            StringBuilder linha = new StringBuilder();
            linha.append(credencial.getUsuario()).append(";");
            linha.append(credencial.getSenha());

            for (String h : credencial.getHistoricoSenhas()) {
                linha.append(";").append(h);
            }

            pw.println(linha.toString());
        } catch (IOException e) {
            System.out.println("Erro ao salvar credencial: " + e.getMessage());
        }
    }

    private void carregar() {
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            // Primeiro acesso: cria credencial padrão
            credencial = new Credencial(USUARIO_PADRAO, SENHA_PADRAO);
            salvar();
            System.out.println("Credencial padrão criada. Usuário: " + USUARIO_PADRAO + " | Senha: " + SENHA_PADRAO);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha = br.readLine();
            if (linha != null && !linha.isBlank()) {
                // Mínimo: usuario;senhaAtual (2 campos)
                String[] c = linha.split(";", -1);
                if (c.length >= 2) {
                    credencial = new Credencial(c[0], c[1]);

                    // Recarrega histórico (campos 2 em diante, até 3 entradas)
                    ArrayList<String> historico = new ArrayList<>();
                    for (int i = 2; i < c.length && i < 5; i++) {
                        if (!c[i].isBlank()) {
                            historico.add(c[i]);
                        }
                    }
                    credencial.setHistoricoSenhas(historico);
                    return;
                }
            }
            // Arquivo corrompido: recria com padrão
            credencial = new Credencial(USUARIO_PADRAO, SENHA_PADRAO);
            salvar();
        } catch (IOException e) {
            System.out.println("Erro ao carregar credencial: " + e.getMessage());
            credencial = new Credencial(USUARIO_PADRAO, SENHA_PADRAO);
        }
    }

    private void garantirPasta() {
        File pasta = new File(ARQUIVO).getParentFile();
        if (pasta != null && !pasta.exists()) {
            pasta.mkdirs();
        }
    }
}
