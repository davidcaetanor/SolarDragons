package service;

import model.Endereco;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViaCEP {

    public static Endereco buscarEnderecoPorCEP(String cep) throws Exception {
        cep = cep.replaceAll("[^0-9]", "");

        if (cep.length() != 8) {
            throw new IllegalArgumentException("CEP inválido. Deve conter exatamente 8 números.");
        }

        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        HttpURLConnection conexao = (HttpURLConnection) new URL(url).openConnection();
        conexao.setRequestMethod("GET");

        int responseCode = conexao.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Erro ao conectar ao ViaCEP. Código: " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream(), "UTF-8"));
        StringBuilder resposta = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            resposta.append(inputLine);
        }
        in.close();

        JSONObject json = new JSONObject(resposta.toString());

        if (json.has("erro") && json.getBoolean("erro")) {
            throw new RuntimeException("CEP não encontrado.");
        }

        String logradouro = json.optString("logradouro", "");
        String bairro = json.optString("bairro", "");
        String localidade = json.optString("localidade", ""); // cidade
        String uf = json.optString("uf", "");                 // estado

        return new Endereco(cep, logradouro, bairro, localidade, uf);
    }
}
