package com.example.demo.interfaces.web;

import com.example.demo.application.VeiculosService;
import com.example.demo.domain.model.Veiculo;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculosController {

    private final VeiculosService veiculosService;

    public VeiculosController(VeiculosService veiculosService) {
        this.veiculosService = veiculosService;
    }

    @GetMapping
    public List<Veiculo> getVeiculos() {
        return veiculosService.listar();
    }

    @GetMapping("/{placa}")
    public ResponseEntity<Veiculo> getVeiculo(@PathVariable String placa) {
        Optional<Veiculo> veiculo = veiculosService.buscarPorPlaca(placa);

        if (veiculo.isPresent()) {
            return ResponseEntity.ok(veiculo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/marca/{marca}")
    public List<Veiculo> listarPorMarca(@PathVariable String marca) {
        return veiculosService.listar().stream()
                .filter(veiculo -> veiculo.getMarca().equalsIgnoreCase(marca))
                .toList();
    }


    @PostMapping
    public Veiculo criar(@RequestBody Veiculo veiculo) {
        return veiculosService.cadastrar(veiculo);
    }

    @PutMapping("/{placa}")
    public Veiculo atualizar(@PathVariable String placa, @RequestBody Veiculo veiculo) {
        return veiculosService.atualizar(veiculo);
    }

    @DeleteMapping("/{placa}")
    public void deletar(@PathVariable String placa) {
        veiculosService.deletar(placa);
    }
}
