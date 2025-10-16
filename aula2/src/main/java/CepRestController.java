import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.util.Optional;



public class CepRestController {

    private final CepRepository cepRepository;

    public CepRestController(CepRepository cepRepository) {
        this.cepRepository = cepRepository;
    }

    public Optional<String> getEnderecoByCep(String cep) {
        return Optional.ofNullable(cepRepository.findByCep(cep)).map(this::toJson);
    }

    private String toJson(Endereco endereco) {
        return new ObjectMapper().valueToTree(endereco).toString();
    }
}
