package cibertec.edu.pe.restcontrollers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cibertec.edu.pe.DTO.DonacionDTO;
import cibertec.edu.pe.DTO.FormularioDTO;
import cibertec.edu.pe.DTO.FormularioListadoDTO;
import cibertec.edu.pe.DTO.ProgramaResponseDTO;
import cibertec.edu.pe.DTO.ResponseDTO;
import cibertec.edu.pe.models.Donacion;
import cibertec.edu.pe.models.Formulario;
import cibertec.edu.pe.models.Programa;
import cibertec.edu.pe.models.Rol;
import cibertec.edu.pe.models.Usuario;
import cibertec.edu.pe.repositories.RolRepositorio;
import cibertec.edu.pe.services.FormularioService;
import cibertec.edu.pe.services.ProgramaService;
import cibertec.edu.pe.services.UsuarioService;

@RestController
@RequestMapping("/formulario")
public class FormularioController {
	
	@Autowired
	private ProgramaService programaServicio;

	@Autowired
	private UsuarioService usuService;
	
	@Autowired
	private FormularioService formService;
	
	@Autowired
	private RolRepositorio rolRepo;
	

	
	
	@PostMapping("/guardar")
	public ResponseEntity<ResponseDTO<FormularioDTO>> registrarFormulario(@RequestBody FormularioDTO dto) {
		
		Usuario usuario = usuService.findByEmail(dto.getEmail_usuario_postulante());
			
		if(usuario == null) {
			ResponseDTO<FormularioDTO> response = new ResponseDTO<>(dto, "El email no esta registrado, registrese antes de participar de un programa");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		Programa programa = programaServicio.getByNombre(dto.getNomre_exacto_programa());
		if(programa == null) {
			ResponseDTO<FormularioDTO> response = new ResponseDTO<>(dto, "El programa no esta registrado, busque un programa valido");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		Formulario formulario = formService.findByUsuario(usuario);
		if(formulario != null) {
			ResponseDTO<FormularioDTO> response = new ResponseDTO<>(dto, "El usuario ya tiene un formulario");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
			Formulario nuevoFormulario = new Formulario();
			nuevoFormulario.setCargoForm(dto.getCargoForm());
			nuevoFormulario.setEdadForm(dto.getEdadForm());
			nuevoFormulario.setEstado(true);
			nuevoFormulario.setPrograma(programa);
			nuevoFormulario.setUsuario(usuario);
			
			formService.save(nuevoFormulario);
			
			ResponseDTO<FormularioDTO> response = new ResponseDTO<>(dto, "Formulario registrado exitosamente");
			return new ResponseEntity<>(response, HttpStatus.OK);	
	}
	
	
	@GetMapping("/listadoXprograma/{email}")
	public ResponseEntity<ResponseDTO<List<FormularioListadoDTO>>> listadoDeFormulariosParaUnJefeDePrograma(@PathVariable String email){
		Usuario usuario = usuService.findByEmail(email);
		List<FormularioListadoDTO> formulariosResponse = new ArrayList<FormularioListadoDTO>();
		
		Collection<Rol> rol = rolRepo.findByNombre1("ROL_JEFE");
		
		if(usuario == null || !rol.equals(usuario.getRoles())) {
			ResponseDTO<List<FormularioListadoDTO>> response = new ResponseDTO<>(formulariosResponse, "El usuario no existe o no tiene el rol de Jefe de Programa");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		List<Formulario> formularios = formService.listFormsByProgram(usuario.getPrograma());
		
		List<FormularioListadoDTO> dto = formularios.stream()
				.map(formulario -> new FormularioListadoDTO(formulario.getIdForm(),
						formulario.getUsuario().getUsuarioNombre(),
						formulario.getUsuario().getUsuarioApellido(),
						formulario.getUsuario().getUsuarioEmail(),
						formulario.getUsuario().getUsuarioCelular(),
						formulario.getEdadForm(),
						formulario.getCargoForm(),
						obtenerPrograma(formulario),
						usuario.getUsuarioEmail()
						))
				.collect(Collectors.toList());
		
		if(formularios.size() > 0) {
			ResponseDTO<List<FormularioListadoDTO>> response = new ResponseDTO<>(dto, "Listado de postulantes de tu programa");
	        return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		
		ResponseDTO<List<FormularioListadoDTO>> response = new ResponseDTO<>(formulariosResponse, "No existen postulantes en tu programa");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	private String obtenerPrograma(Formulario form) {
		Programa nombrePro = form.getPrograma();
		if(nombrePro != null) {
			return nombrePro.getNombrePro();
		}
		return null;
	}
	
	@PutMapping("/actualizarEstadoFormulario/{id}")
	public String desactivarFormulario(@PathVariable Long id) {
		
		
		
		Formulario form = formService.findById(id);
		if(form == null) {
			return "Formulario no encontrado por su id";
		}
		
		form.setEstado(false);
		formService.save(form);
		return "Formulario Desactivado Correctamente";
	}
	
	
	
}
