package cibertec.edu.pe.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonacionDTO {
	private Long id;
	private String numero_de_tarjeta;
	private String nombre_del_titular;
	private String cvc;
	private String caducidad;
	private Double total_a_donar;
	private String nomre_exacto_programa;
	private String email_usuario_donador;
	
}
