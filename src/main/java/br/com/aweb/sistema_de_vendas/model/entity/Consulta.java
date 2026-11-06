package br.com.aweb.sistema_de_vendas.model.entity;

import java.time.LocalDateTime;

import br.com.aweb.sistema_de_vendas.exception.RegraNegocioException;
import br.com.aweb.sistema_de_vendas.model.enums.StatusConsulta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "consultas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private StatusConsulta status;

    @Column(name = "motivo_cancelamento")
    private String motivoCancelamento;

    public Consulta(Medico medico, Paciente paciente, LocalDateTime dataHora) {
        this.medico = medico;
        this.paciente = paciente;
        this.dataHora = dataHora;
        this.status = StatusConsulta.AGENDADA;
    }

    public void cancelar(String motivo) {
        if (status == StatusConsulta.CANCELADA) {
            throw new RegraNegocioException("Consulta já está cancelada");
        }
        if (status == StatusConsulta.REALIZADA) {
            throw new RegraNegocioException("Não é possível cancelar uma consulta já realizada");
        }
        this.status = StatusConsulta.CANCELADA;
        this.motivoCancelamento = motivo;
    }
}
