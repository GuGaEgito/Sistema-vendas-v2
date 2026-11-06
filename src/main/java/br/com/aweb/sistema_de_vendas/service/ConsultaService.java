package br.com.aweb.sistema_de_vendas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.aweb.sistema_de_vendas.exception.EntidadeNaoEncontradaException;
import br.com.aweb.sistema_de_vendas.exception.RegraNegocioException;
import br.com.aweb.sistema_de_vendas.model.dto.DadosAgendamentoConsulta;
import br.com.aweb.sistema_de_vendas.model.entity.Consulta;
import br.com.aweb.sistema_de_vendas.model.entity.Medico;
import br.com.aweb.sistema_de_vendas.model.entity.Paciente;
import br.com.aweb.sistema_de_vendas.repository.ConsultaRepository;
import br.com.aweb.sistema_de_vendas.repository.MedicoRepository;
import br.com.aweb.sistema_de_vendas.repository.PacienteRepository;
import jakarta.transaction.Transactional;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaService(ConsultaRepository consultaRepository, MedicoRepository medicoRepository,
            PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public Consulta agendar(DadosAgendamentoConsulta dados) {
        Medico medico = medicoRepository.findById(dados.idMedico())
                .orElseThrow(() -> new RegraNegocioException("Médico não encontrado com id: " + dados.idMedico()));
        Paciente paciente = pacienteRepository.findById(dados.idPaciente())
                .orElseThrow(() -> new RegraNegocioException("Paciente não encontrado com id: " + dados.idPaciente()));
        return consultaRepository.save(new Consulta(medico, paciente, dados.dataHora()));
    }

    public Page<Consulta> listar(Pageable paginacao) {
        return consultaRepository.findAll(paginacao);
    }

    public Consulta buscarPorId(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Consulta não encontrada com id: " + id));
    }

    @Transactional
    public Consulta cancelar(Long id, String motivo) {
        Consulta consulta = buscarPorId(id);
        consulta.cancelar(motivo);
        return consulta;
    }
}
