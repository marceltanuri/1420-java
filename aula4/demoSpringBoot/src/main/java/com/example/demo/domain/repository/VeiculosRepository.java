package com.example.demo.domain.repository;

import com.example.demo.domain.model.Veiculo;
import java.util.List;

public interface VeiculosRepository {

    List<Veiculo> listar();

    Veiculo cadastrar(Veiculo veiculo);

    Veiculo atualizar(Veiculo veiculo);

    void deletar(String placa);

}
