package com.example.demo.infrastructure.repository;

import com.example.demo.domain.model.Veiculo;
import com.example.demo.domain.repository.VeiculosRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VeiculosRepositoryImpl implements VeiculosRepository {

    private static final Map<String, Veiculo> veiculos = new HashMap<>();

    static {
        veiculos.put("ABC-1234", new Veiculo("Fiat", "Uno", "ABC-1234"));
        veiculos.put("DEF-5678", new Veiculo("Ford", "Ka", "DEF-5678"));
        veiculos.put("GHI-9012", new Veiculo("Chevrolet", "Onix", "GHI-9012"));
        veiculos.put("JKL-3456", new Veiculo("Volkswagen", "Gol", "JKL-3456"));
        veiculos.put("MNO-7890", new Veiculo("Hyundai", "HB20", "MNO-7890"));
        veiculos.put("PQR-1234", new Veiculo("Toyota", "Corolla", "PQR-1234"));
        veiculos.put("STU-5678", new Veiculo("Honda", "Civic", "STU-5678"));
        veiculos.put("VWX-9012", new Veiculo("Renault", "Kwid", "VWX-9012"));
        veiculos.put("YZA-3456", new Veiculo("Jeep", "Renegade", "YZA-3456"));
        veiculos.put("BCD-7890", new Veiculo("Nissan", "Kicks", "BCD-7890"));
    }

    @Override
    public List<Veiculo> listar() {
        return new ArrayList<>(veiculos.values());
    }

    @Override
    public Veiculo cadastrar(Veiculo veiculo) {
        veiculos.put(veiculo.getPlaca(), veiculo);
        return veiculo;
    }

    @Override
    public Veiculo atualizar(Veiculo veiculo) {
        veiculos.put(veiculo.getPlaca(), veiculo);
        return veiculo;
    }

    @Override
    public void deletar(String placa) {
        veiculos.remove(placa);
    }
}
