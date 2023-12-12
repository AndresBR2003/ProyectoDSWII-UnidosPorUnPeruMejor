package cibertec.edu.pe.restcontrollers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cibertec.edu.pe.DTO.UsuarioDTO;
import cibertec.edu.pe.DTO.ResponseDTO;
import cibertec.edu.pe.models.Rol;
import cibertec.edu.pe.models.Usuario;
import cibertec.edu.pe.services.UsuarioService;
import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/registro")
public class RegistroController {

	@Autowired
	private UsuarioService usuarioServicio;

	@PermitAll
	@PostMapping("/Usuario")
	public ResponseEntity<ResponseDTO<UsuarioDTO>> saveUsuario(@RequestBody UsuarioDTO dto) {
		
		Usuario usuarioExistente = usuarioServicio.findByEmail(dto.getEmail());
	    
	    if (usuarioExistente != null) {
	        ResponseDTO<UsuarioDTO> response = new ResponseDTO<>(dto, "Este email ya esta registrado, pruebe iniciando sesion");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
				
		Usuario registro = usuarioServicio.guardar(dto);
		
		if (registro == null) {
			ResponseDTO<UsuarioDTO> response = new ResponseDTO<>(dto, "Rol inválido, Usuario no guardado!!!");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		ResponseDTO<UsuarioDTO> response = new ResponseDTO<>(dto, "Usuario Registrado Exitosamente");		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/ListaUsuarios")
	public ResponseEntity<List<UsuarioDTO>> getListUsuarios(){
		List<Usuario> usuarios = usuarioServicio.listarUsuarios();
		
		List<UsuarioDTO> dto = usuarios.stream()
				.map(usuario -> new UsuarioDTO(usuario.getUsuarioNombre(),
						usuario.getUsuarioApellido(),
						usuario.getDni_ce(),
						usuario.getUsuarioCelular(),
						usuario.getUsuarioEmail(),
						usuario.getUsuarioPassword(),
						obtenerNombreRol(usuario)))
				.collect(Collectors.toList());
		
		if(usuarios.size() > 0) {
			return new ResponseEntity<List<UsuarioDTO>>(dto, HttpStatus.OK);
		}
		return new ResponseEntity<List<UsuarioDTO>>(new ArrayList<UsuarioDTO>(), HttpStatus.NOT_FOUND);
	}
	
	private String obtenerNombreRol(Usuario usu) {
		Collection<Rol> roles = usu.getRoles();
		if(roles != null && !roles.isEmpty()) {
			return roles.iterator().next().getNombre();
		}
		return null;
	}
	
	
	
}
