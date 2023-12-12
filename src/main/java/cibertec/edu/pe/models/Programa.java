package cibertec.edu.pe.models;

import java.util.Collection;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "programas")
@Data
@AllArgsConstructor
public class Programa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "programa_id")
	private Long idPro;
	
	@Column(name = "programa_nombre")
	private String nombrePro;
	
	@Column(name = "programa_descripcion")
	private String descripcionPro;
	
	@Column(name = "programa_actividades")
	private String actividadesPro;
	
	@Column(name = "programa_horario")
	private String horarioPro;
	
	@Column(name = "programa_estado")
	private boolean estadoPro;
	
	@OneToMany(mappedBy = "programa", cascade = CascadeType.ALL)
    private Set<Donacion> donaciones;
	
	@OneToMany(mappedBy = "programa", cascade = CascadeType.ALL)
	private Set<Formulario> formularios;
	
	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "jefe_id")
	private Usuario jefePro;
	

	public Programa() {
		super();
		this.estadoPro = true;
	}
}
