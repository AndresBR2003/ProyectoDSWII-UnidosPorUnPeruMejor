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

import cibertec.edu.pe.DTO.DonacionDTO;
import cibertec.edu.pe.DTO.ProgramaRequestDTO;
import cibertec.edu.pe.DTO.ProgramaResponseDTO;
import cibertec.edu.pe.DTO.ResponseDTO;
import cibertec.edu.pe.models.Donacion;
import cibertec.edu.pe.models.Programa;
import cibertec.edu.pe.models.Rol;
import cibertec.edu.pe.models.Usuario;
import cibertec.edu.pe.services.DonacionesService;
import cibertec.edu.pe.services.ProgramaService;
import cibertec.edu.pe.services.UsuarioService;

@RestController
@RequestMapping("/donacion")
public class DonacionController {
	
	@Autowired
	private ProgramaService programaServicio;

	@Autowired
	private UsuarioService usuService;

	@Autowired
	private DonacionesService donaService;
	
	@PostMapping("/donar")
	public ResponseEntity<ResponseDTO<DonacionDTO>> registrarDonacion(@RequestBody DonacionDTO dto) {
		
		Usuario usuario = usuService.findByEmail(dto.getEmail_usuario_donador());
			
		if(usuario == null) {
			ResponseDTO<DonacionDTO> response = new ResponseDTO<>(dto, "El email no esta registrado, registrese antes de donar");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		Programa programa = programaServicio.getByNombre(dto.getNomre_exacto_programa());
		if(programa == null) {
			ResponseDTO<DonacionDTO> response = new ResponseDTO<>(dto, "El programa no esta registrado, busque un programa valido");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getTotal_a_donar() == null || dto.getTotal_a_donar() < 5.0) {
			ResponseDTO<DonacionDTO> response = new ResponseDTO<>(dto, "El valor minimo a donar es de s/5.00, intentelo denuevo");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
			Donacion nuevaDonacion = new Donacion();
			nuevaDonacion.setNroTarjetaDona(dto.getNumero_de_tarjeta());
			nuevaDonacion.setNombreDelTitular(dto.getNombre_del_titular());
	        nuevaDonacion.setCVC(dto.getCvc());
	        nuevaDonacion.setCaducidad(dto.getCaducidad());
	        nuevaDonacion.setTotalDona(dto.getTotal_a_donar());
	        nuevaDonacion.setPrograma(programa);
	        nuevaDonacion.setUsuario(usuario);
			
			donaService.save(nuevaDonacion);
			
			ResponseDTO<DonacionDTO> response = new ResponseDTO<>(dto, "Donacion Realizada correctamente Correctamente");
			return new ResponseEntity<>(response, HttpStatus.OK);	
	}
	
	@GetMapping("/listado")
	public ResponseEntity<List<DonacionDTO>> getListDonaciones(){
		List<Donacion> donaciones = donaService.findAll();
		
		List<DonacionDTO> dto = donaciones.stream()
				.map(donacion -> new DonacionDTO(donacion.getIdDona(),
						donacion.getNroTarjetaDona(),
						donacion.getNombreDelTitular(),
						donacion.getCVC(),
						donacion.getCaducidad(),
						donacion.getTotalDona(),
						obtenerNombrePrograma(donacion),
						obtenerNombreUsuario(donacion)))
				.collect(Collectors.toList());
		
		if(donaciones.size() > 0) {
			return new ResponseEntity<List<DonacionDTO>>(dto, HttpStatus.OK);
		}
		return new ResponseEntity<List<DonacionDTO>>(new ArrayList<DonacionDTO>(), HttpStatus.NOT_FOUND);
	}
	
	private String obtenerNombrePrograma(Donacion dona) {
		Programa pro = dona.getPrograma();
		
		return pro.getNombrePro();
	}
	
	private String obtenerNombreUsuario(Donacion dona) {
		Usuario usu = dona.getUsuario();
		
		return usu.getUsuarioEmail();
	}
}
