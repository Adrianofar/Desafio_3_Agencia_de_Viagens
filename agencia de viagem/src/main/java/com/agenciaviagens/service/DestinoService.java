package com.agenciaviagens.service;

import com.agenciaviagens.model.Destino;
import com.agenciaviagens.repository.DestinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DestinoService {
    
    @Autowired
    private DestinoRepository destinoRepository;  // Agora usa o repository!
    
    // Cadastrar novo destino
    @Transactional  // Garante que salva tudo certinho no banco
    public Destino cadastrarDestino(Destino destino) {
        if (destino.getNome() == null || destino.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (destino.getLocalizacao() == null || destino.getLocalizacao().isEmpty()) {
            throw new IllegalArgumentException("Localização é obrigatória");
        }
        
        return destinoRepository.save(destino);  // Salva no banco
    }
    
    // Listar todos os destinos
    public List<Destino> listarTodos() {
        return destinoRepository.findAll();  // Busca tudo do banco
    }
    
    // Pesquisar destinos por nome ou localização
    public List<Destino> pesquisar(String termo) {
        if (termo == null || termo.isEmpty()) {
            return destinoRepository.findAll();
        }
        
        return destinoRepository.pesquisar(termo);  // Usa a query customizada
    }
    
    // Buscar destino por ID
    public Destino buscarPorId(String id) {
        return destinoRepository.findById(id).orElse(null);
    }
    
    // Avaliar destino
    @Transactional
    public Destino avaliar(String id, int nota) {
        if (nota < 1 || nota > 10) {
            throw new IllegalArgumentException("A nota deve ser entre 1 e 10");
        }
        
        Destino destino = destinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destino não encontrado"));
        
        destino.adicionarAvaliacao(nota);
        return destinoRepository.save(destino);  // Atualiza no banco
    }
    
    // Atualizar destino
    @Transactional
    public Destino atualizar(String id, Destino destinoAtualizado) {
        Destino destino = destinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destino não encontrado"));
        
        if (destinoAtualizado.getNome() != null && !destinoAtualizado.getNome().isEmpty()) {
            destino.setNome(destinoAtualizado.getNome());
        }
        if (destinoAtualizado.getLocalizacao() != null && !destinoAtualizado.getLocalizacao().isEmpty()) {
            destino.setLocalizacao(destinoAtualizado.getLocalizacao());
        }
        if (destinoAtualizado.getDescricao() != null) {
            destino.setDescricao(destinoAtualizado.getDescricao());
        }
        
        return destinoRepository.save(destino);
    }
    
    // Excluir destino
    @Transactional
    public boolean excluir(String id) {
        if (destinoRepository.existsById(id)) {
            destinoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}