package cibertec.edu.pe.models;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rol implements GrantedAuthority{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rol_id")
	private Long id;
	
	@Column(name = "rol_nombre")
	private String nombre;
	

	public Rol(String nombre) {
		super();
		this.nombre = nombre;
	}


	@Override
	public String getAuthority() {
		return this.nombre;
	}
}
