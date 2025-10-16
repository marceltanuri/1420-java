
public class CepRestController {

    private final CepRepository cepRepository;

    public CepRestController(CepRepository cepRepository) {
        this.cepRepository = cepRepository;
    }

    public String getEnderecoByCep(String cep) {
        Endereco endereco = cepRepository.findByCep(cep);
        if (endereco != null) {
            return toJson(endereco);
        } else {
            return "{\"error\": \"CEP not found\"}";
        }
    }

    private String toJson(Endereco endereco) {
        return "{\"cep\":\"" + endereco.getCep() + "\",\"logradouro\":\"" + endereco.getLogradouro() + "\",\"complemento\":\"" + endereco.getComplemento() + "\",\"unidade\":\"" + endereco.getUnidade() + "\",\"bairro\":\"" + endereco.getBairro() + "\",\"localidade\":\"" + endereco.getLocalidade() + "\",\"uf\":\"" + endereco.getUf() + "\",\"estado\":\"" + endereco.getEstado() + "\",\"regiao\":\"" + endereco.getRegiao() + "\",\"ibge\":\"" + endereco.getIbge() + "\",\"gia\":\"" + endereco.getGia() + "\",\"ddd\":\"" + endereco.getDdd() + "\",\"siafi\":\"" + endereco.getSiafi() + "\",\"geolocalizacao\":{\"lat\":\"" + endereco.getGeolocalizacao().getLat() + "\",\"lng\":\"" + endereco.getGeolocalizacao().getLng() + "\"}}";
    }
}
