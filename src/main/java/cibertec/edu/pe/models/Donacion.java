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
@Table(name = "donaciones")
@Data
@AllArgsConstructor
public class Donacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "donacion_id")
	private Long idDona;
	
	@Column(name = "donacion_numero_tarjeta")
	private String nroTarjetaDona;
	
	@Column(name = "donacion_nombre_titular_tarjeta")
	private String nombreDelTitular;
	
	@Column(name = "donacion_cvc_tarjeta")
	private String CVC;
	
	@Column(name = "donacion_caducidad_tarjeta")
	private String caducidad;
	
	@Column(name = "donacion_total")
	private double totalDona;
	
	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

	@ManyToOne 
    @JoinColumn(name = "idPro")
    private Programa programa; 

}
