package cibertec.edu.pe.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "formularios")
@Data
@AllArgsConstructor
public class Formulario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "formulario_id")
	private Long idForm;
	
	@Column(name = "formulario_edad")
	private int edadForm;
	
	@Column(name = "formulario_estudiosocargo")
	private String cargoForm;	
	
	@Column(name = "formulario_estado")
	private Boolean estado;
	
	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_pro")
	private Programa programa;
}
