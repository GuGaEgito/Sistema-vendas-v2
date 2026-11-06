package br.com.aweb.sistema_de_vendas.controller;

import java.net.URI;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.aweb.sistema_de_vendas.model.dto.DadosAgendamentoConsulta;
import br.com.aweb.sistema_de_vendas.model.dto.DadosCancelamentoConsulta;
import br.com.aweb.sistema_de_vendas.model.dto.DadosDetalhamentoConsulta;
import br.com.aweb.sistema_de_vendas.model.entity.Consulta;
import br.com.aweb.sistema_de_vendas.service.ConsultaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados,
            UriComponentsBuilder uriBuilder) {
        Consulta consulta = consultaService.agendar(dados);
        URI uri = uriBuilder.path("/consultas/{id}").buildAndExpand(consulta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoConsulta(consulta));
    }

    @GetMapping
    public PagedModel<DadosDetalhamentoConsulta> listar(
            @PageableDefault(size = 10, sort = { "dataHora" }) Pageable paginacao) {
        return new PagedModel<>(consultaService.listar(paginacao).map(DadosDetalhamentoConsulta::new));
    }

    @GetMapping("/{id}")
    public DadosDetalhamentoConsulta detalhar(@PathVariable Long id) {
        return new DadosDetalhamentoConsulta(consultaService.buscarPorId(id));
    }

    @PutMapping("/{id}/cancelar")
    public DadosDetalhamentoConsulta cancelar(@PathVariable Long id,
            @RequestBody(required = false) DadosCancelamentoConsulta dados) {
        String motivo = dados != null ? dados.motivo() : null;
        return new DadosDetalhamentoConsulta(consultaService.cancelar(id, motivo));
    }
}
