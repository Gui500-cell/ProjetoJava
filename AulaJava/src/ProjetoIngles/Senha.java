import java.io.*;            // Importa classes para trabalhar com arquivos
import java.nio.file.*;     // Importa utilidades modernas para leitura de arquivos
import java.util.*;         // Importa listas, Scanner, etc.

public class Senha {       

    public static void main(String[] args) throws Exception { // Método principal

        Scanner sc = new Scanner(System.in); // Cria leitor para entrada do usuário
        System.out.print("Digite uma senha: "); // Pede a senha
        String s = sc.nextLine(); // Lê a senha digitada
        sc.close(); // Fecha o Scanner

        // Validação da senha
        if (s.length() < 8 ||                 // Verifica se tem menos de 8 caracteres
            !s.matches(".*[0-9].*") ||        // Verifica se NÃO tem número
            !s.matches(".*[a-zA-Z].*")) {     // Verifica se NÃO tem letra

            System.out.println("Senha fraca! Use pelo menos 8 caracteres, letras e números.");
            return; // Encerra o programa se a senha for fraca
        }

        List<String> senhas = new ArrayList<>(); // Lista para guardar as senhas

        File f = new File("senha.json"); // Cria referência para o arquivo senha.json

        // Verifica se o arquivo já existe
        if (f.exists()) {

            // Lê todo o conteúdo do arquivo como texto
            String c = new String(Files.readAllBytes(f.toPath()));

            // Verifica se existe lista de senhas dentro do arquivo
            if (c.contains("[")) {

                // Pega o conteúdo dentro dos colchetes [ ... ]
                String dentro = c.substring(c.indexOf("[") + 1, c.indexOf("]"));

                // Separa as senhas por vírgula
                for (String i : dentro.split(",")) {

                    i = i.replace("\"", "").trim(); // Remove aspas e espaços

                    if (!i.isEmpty()) { // Se não estiver vazio
                        senhas.add(i); // Adiciona na lista
                    }
                }
            }
        }

        senhas.add(s); // Adiciona a nova senha na lista

        // Abre o arquivo para escrita (sobrescreve o conteúdo)
        try (FileWriter w = new FileWriter(f)) {

            // Escreve no formato JSON
            w.write("{\"senhas\":[\"" + String.join("\",\"", senhas) + "\"]}");
        }

        System.out.println("Senha salva em senha.json"); // Mensagem final
    }
}