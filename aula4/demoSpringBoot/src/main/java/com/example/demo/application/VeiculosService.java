package com.example.demo.application;

import com.example.demo.domain.model.Veiculo;
import com.example.demo.domain.repository.VeiculosRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.util.List;

@Service
public class VeiculosService {

    private final VeiculosRepository veiculosRepository;

    public VeiculosService(VeiculosRepository veiculosRepository) {
        this.veiculosRepository = veiculosRepository;
    }

    public Optional<Veiculo> buscarPorPlaca(String placa) {
        return veiculosRepository.listar().stream()
                .filter(veiculo -> veiculo.getPlaca().equals(placa))
                .findFirst();
    }


    public List<Veiculo> listar() {
        return veiculosRepository.listar();
    }

    public Veiculo cadastrar(Veiculo veiculo) {
        return veiculosRepository.cadastrar(veiculo);
    }

    public Veiculo atualizar(Veiculo veiculo) {
        return veiculosRepository.atualizar(veiculo);
    }

    public void deletar(String placa) {
        veiculosRepository.deletar(placa);
    }
}
