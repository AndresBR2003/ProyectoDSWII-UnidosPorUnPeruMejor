package cibertec.edu.pe.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormularioListadoDTO {

	private Long id;
	private String nombres;
	private String apellidos;
	private String email;
	private String celular;
	private int edadForm;
	private String cargoForm;
	private String nomre_exacto_programa;
	private String email_usuario_postulante;
}
