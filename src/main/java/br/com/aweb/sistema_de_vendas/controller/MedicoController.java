package br.com.aweb.sistema_de_vendas.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.aweb.sistema_de_vendas.exception.EntidadeNaoEncontradaException;
import br.com.aweb.sistema_de_vendas.model.dto.DadosAtualizacaoMedico;
import br.com.aweb.sistema_de_vendas.model.dto.DadosCadastroMedico;
import br.com.aweb.sistema_de_vendas.model.dto.DadosDetalhamentoMedico;
import br.com.aweb.sistema_de_vendas.model.dto.DadosListagemMedico;
import br.com.aweb.sistema_de_vendas.model.entity.Medico;
import br.com.aweb.sistema_de_vendas.repository.MedicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> cadastrar(@RequestBody @Valid DadosCadastroMedico dados,
            UriComponentsBuilder uriBuilder) {
        Medico medico = medicoRepository.save(new Medico(dados));
        URI uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public PagedModel<DadosListagemMedico> listar(@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
        return new PagedModel<>(medicoRepository.findAll(paginacao).map(DadosListagemMedico::new));
    }

    @GetMapping("/{id}")
    public DadosDetalhamentoMedico detalhar(@PathVariable Long id) {
        return new DadosDetalhamentoMedico(buscar(id));
    }

    @PutMapping("/{id}")
    @Transactional
    public DadosDetalhamentoMedico atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoMedico dados) {
        Medico medico = buscar(id);
        medico.atualizarInformacoes(dados);
        return new DadosDetalhamentoMedico(medico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        medicoRepository.delete(buscar(id));
        medicoRepository.flush();
        return ResponseEntity.noContent().build();
    }

    private Medico buscar(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Médico não encontrado com id: " + id));
    }
}
