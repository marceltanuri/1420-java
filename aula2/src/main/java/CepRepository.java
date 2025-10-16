
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CepRepository {

    private Map<String, Endereco> enderecos = new HashMap<>();

    public CepRepository() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/cep.csv"))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                Endereco endereco = new Endereco();
                endereco.setCep(values[0].replaceAll("\"", "").replaceAll("-", ""));
                endereco.setLogradouro(values[1].replaceAll("\"", ""));
                endereco.setComplemento(values[2].replaceAll("\"", ""));
                endereco.setUnidade(values[3].replaceAll("\"", ""));
                endereco.setBairro(values[4].replaceAll("\"", ""));
                endereco.setLocalidade(values[5].replaceAll("\"", ""));
                endereco.setUf(values[6].replaceAll("\"", ""));
                endereco.setEstado(values[7].replaceAll("\"", ""));
                endereco.setRegiao(values[8].replaceAll("\"", ""));
                endereco.setIbge(values[9].replaceAll("\"", ""));
                endereco.setGia(values[10].replaceAll("\"", ""));
                endereco.setDdd(values[11].replaceAll("\"", ""));
                endereco.setSiafi(values[12].replaceAll("\"", ""));
                endereco.setGeolocalizacao(new Geolocalizacao(values[13].replaceAll("\"", ""), values[14].replaceAll("\"", "")));
                enderecos.put(endereco.getCep(), endereco);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Endereco findByCep(String cep) {
        return enderecos.get(cep);
    }
}
