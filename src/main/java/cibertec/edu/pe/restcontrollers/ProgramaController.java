package cibertec.edu.pe.restcontrollers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cibertec.edu.pe.DTO.ProgramaRequestDTO;
import cibertec.edu.pe.DTO.ProgramaResponseDTO;
import cibertec.edu.pe.DTO.ResponseDTO;
import cibertec.edu.pe.DTO.UsuarioDTO;
import cibertec.edu.pe.models.Programa;
import cibertec.edu.pe.models.Rol;
import cibertec.edu.pe.models.Usuario;
import cibertec.edu.pe.repositories.RolRepositorio;
import cibertec.edu.pe.serviceImpl.ProgramaServicioImpl;
import cibertec.edu.pe.services.ProgramaService;
import cibertec.edu.pe.services.UsuarioService;


@RestController
@RequestMapping("/programa")
public class ProgramaController {
	@Autowired
	private ProgramaService programaServicio;

	@Autowired
	private UsuarioService usuService;
	
	@Autowired
	private RolRepositorio rolRepo;
	
	
	@GetMapping("/listadoActivos")
	public ResponseEntity<List<ProgramaResponseDTO>> getListProgamasActivos(){
		List<Programa> programas = programaServicio.listAllActivos();
		
		List<ProgramaResponseDTO> dto = programas.stream()
				.map(programa -> new ProgramaResponseDTO(programa.getIdPro(),
						programa.getNombrePro(),
						programa.getDescripcionPro(),
						programa.getActividadesPro(),
						programa.getHorarioPro(),
						obtenerEmail(programa)))
				.collect(Collectors.toList());
		
		if(programas.size() > 0) {
			return new ResponseEntity<List<ProgramaResponseDTO>>(dto, HttpStatus.OK);
		}
		return new ResponseEntity<List<ProgramaResponseDTO>>(new ArrayList<ProgramaResponseDTO>(), HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/listadoAll")
	public ResponseEntity<List<ProgramaResponseDTO>> getListProgamasAll(){
		List<Programa> programas = programaServicio.listAll();
		
		List<ProgramaResponseDTO> dto = programas.stream()
				.map(programa -> new ProgramaResponseDTO(programa.getIdPro(),
						programa.getNombrePro(),
						programa.getDescripcionPro(),
						programa.getActividadesPro(),
						programa.getHorarioPro(),
						obtenerEmail(programa)))
				.collect(Collectors.toList());
		
		if(programas.size() > 0) {
			return new ResponseEntity<List<ProgramaResponseDTO>>(dto, HttpStatus.OK);
		}
		return new ResponseEntity<List<ProgramaResponseDTO>>(new ArrayList<ProgramaResponseDTO>(), HttpStatus.NOT_FOUND);
	}
	
	private String obtenerEmail(Programa pro) {
		Usuario jefePro = pro.getJefePro();
		if(jefePro != null) {
			return jefePro.getUsuarioEmail();
		}
		return null;
	}
	
	@PostMapping("/registrar")
	public ResponseEntity<ResponseDTO<ProgramaRequestDTO>> registrarPrograma(@RequestBody ProgramaRequestDTO dto) {
		
		Usuario usuario = usuService.findByEmail(dto.getEmailJefe());
		
		
		
		if(usuario == null) {
			ResponseDTO<ProgramaRequestDTO> response = new ResponseDTO<>(dto, "El email no esta registrado ");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		Collection<Rol> rol = rolRepo.findByNombre1("ROL_JEFE");
		
		if(rol.equals(usuario.getRoles())) {
			Programa nuevoPrograma = new Programa();
			nuevoPrograma.setNombrePro(dto.getTitulo());
	        nuevoPrograma.setDescripcionPro(dto.getDescripcion());
	        nuevoPrograma.setActividadesPro(dto.getActividades());
	        nuevoPrograma.setHorarioPro(dto.getHorario());
	        nuevoPrograma.setJefePro(usuario);
			
			programaServicio.save(nuevoPrograma);
			
			ResponseDTO<ProgramaRequestDTO> response = new ResponseDTO<>(dto, "Programa Guardado Correctamente");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		
		
		ResponseDTO<ProgramaRequestDTO> response = new ResponseDTO<>(dto, "El email no tiene el rol de Jefe");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	
	@PutMapping("/actualizar")
	public ResponseEntity<ResponseDTO<ProgramaResponseDTO>> actualizarDto(@RequestBody ProgramaRequestDTO dto, @RequestParam Long id){
		Programa programa = programaServicio.get(id);
		
		programa.setNombrePro(dto.getTitulo());
		programa.setDescripcionPro(dto.getDescripcion());
		programa.setActividadesPro(dto.getActividades());
		programa.setHorarioPro(dto.getHorario());
		
		programaServicio.save(programa);
		
		ProgramaResponseDTO responseDTO = new ProgramaResponseDTO();
		responseDTO.setId(programa.getIdPro());
		responseDTO.setTitulo(programa.getNombrePro());
		responseDTO.setDescripcion(programa.getDescripcionPro());
		responseDTO.setActividades(programa.getActividadesPro());
		responseDTO.setHorario(programa.getHorarioPro());
		responseDTO.setEmailJefe(obtenerEmail(programa));
		
		ResponseDTO<ProgramaResponseDTO> response = new ResponseDTO<>(responseDTO, "El Programa ha sido guardado exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@PutMapping("/desactivar")
	public ResponseEntity<ResponseDTO<ProgramaResponseDTO>> desactivarPrograma(@RequestParam Long id){
		Programa programa = programaServicio.get(id);
		
		programa.setEstadoPro(false);
		programaServicio.save(programa);
		
		ProgramaResponseDTO responseDTO = new ProgramaResponseDTO();
		responseDTO.setId(programa.getIdPro());
		responseDTO.setTitulo(programa.getNombrePro());
		responseDTO.setDescripcion(programa.getDescripcionPro());
		responseDTO.setActividades(programa.getActividadesPro());
		responseDTO.setHorario(programa.getHorarioPro());
		responseDTO.setEmailJefe(obtenerEmail(programa));
		
		ResponseDTO<ProgramaResponseDTO> response = new ResponseDTO<>(responseDTO, "El Programa ha sido desactivado exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
		
		
		
	}
	
}
