package cibertec.edu.pe.DTO;

import cibertec.edu.pe.models.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

	private String nombres;
	private String apellidos;
	private String dni_ce;
	private String celular;
	private String email;
	private String password;
	private String rol;
	
	public UsuarioDTO(String nombres, String apellidos, String dni_ce, String celular, String email, String password) {
		super();
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.dni_ce = dni_ce;
		this.celular = celular;
		this.email = email;
		this.password = password;
	}
	
	
}
