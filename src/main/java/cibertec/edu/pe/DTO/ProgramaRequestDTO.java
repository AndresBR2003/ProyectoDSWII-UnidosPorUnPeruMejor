package cibertec.edu.pe.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramaRequestDTO {

	private String titulo;
	private String descripcion;
	private String actividades;
	private String horario;
	private String emailJefe;
}
