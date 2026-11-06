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
import br.com.aweb.sistema_de_vendas.model.dto.DadosAtualizacaoPaciente;
import br.com.aweb.sistema_de_vendas.model.dto.DadosCadastroPaciente;
import br.com.aweb.sistema_de_vendas.model.dto.DadosDetalhamentoPaciente;
import br.com.aweb.sistema_de_vendas.model.dto.DadosListagemPaciente;
import br.com.aweb.sistema_de_vendas.model.entity.Paciente;
import br.com.aweb.sistema_de_vendas.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(@RequestBody @Valid DadosCadastroPaciente dados,
            UriComponentsBuilder uriBuilder) {
        Paciente paciente = pacienteRepository.save(new Paciente(dados));
        URI uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    public PagedModel<DadosListagemPaciente> listar(@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
        return new PagedModel<>(pacienteRepository.findAll(paginacao).map(DadosListagemPaciente::new));
    }

    @GetMapping("/{id}")
    public DadosDetalhamentoPaciente detalhar(@PathVariable Long id) {
        return new DadosDetalhamentoPaciente(buscar(id));
    }

    @PutMapping("/{id}")
    @Transactional
    public DadosDetalhamentoPaciente atualizar(@PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoPaciente dados) {
        Paciente paciente = buscar(id);
        paciente.atualizarInformacoes(dados);
        return new DadosDetalhamentoPaciente(paciente);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        pacienteRepository.delete(buscar(id));
        pacienteRepository.flush();
        return ResponseEntity.noContent().build();
    }

    private Paciente buscar(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Paciente não encontrado com id: " + id));
    }
}
