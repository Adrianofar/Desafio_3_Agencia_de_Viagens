package com.agenciaviagens.controller;

import com.agenciaviagens.model.Destino;
import com.agenciaviagens.service.DestinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/destinos")
public class DestinoController {
    
    @Autowired
    private DestinoService service;
    
    // cadastrar destino
    @PostMapping
    public ResponseEntity<Destino> cadastrar(@RequestBody Destino destino) {
        try {
            Destino novoDestino = service.cadastrarDestino(destino);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoDestino);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // listar todos os destinos
    @GetMapping
    public ResponseEntity<List<Destino>> listarTodos() {
        List<Destino> destinos = service.listarTodos();
        return ResponseEntity.ok(destinos);
    }
    
    // pesquisar destinos
    @GetMapping("/pesquisar")
    public ResponseEntity<List<Destino>> pesquisar(@RequestParam(required = false) String termo) {
        List<Destino> destinos = service.pesquisar(termo);
        return ResponseEntity.ok(destinos);
    }
    
    // ver detalhes de um destino
    @GetMapping("/{id}")
    public ResponseEntity<Destino> buscarPorId(@PathVariable String id) {
        Destino destino = service.buscarPorId(id);
        
        if (destino == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(destino);
    }
    
    // avaliar destino
    @PatchMapping("/{id}/avaliar")
    public ResponseEntity<Destino> avaliar(@PathVariable String id, @RequestBody Map<String, Integer> body) {
        try {
            Integer nota = body.get("avaliacao");
            
            if (nota == null) {
                return ResponseEntity.badRequest().build();
            }
            
            Destino destino = service.avaliar(id, nota);
            return ResponseEntity.ok(destino);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // atualizar destino
    @PutMapping("/{id}")
    public ResponseEntity<Destino> atualizar(@PathVariable String id, @RequestBody Destino destino) {
        try {
            Destino destinoAtualizado = service.atualizar(id, destino);
            return ResponseEntity.ok(destinoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // excluir destino
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        boolean excluido = service.excluir(id);
        
        if (excluido) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }
}