package cibertec.edu.pe.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "usuario_email"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usuario_id")
	private Long usuarioId;
	
	@Column(name = "usuario_name")
	private String usuarioNombre;
	
	@Column(name = "usuario_lastname")
	private String usuarioApellido;
	
	@Column(name = "usuario_dni_ce")
	private String dni_ce;
	
	@Column(name = "usuario_celular")
	private String usuarioCelular;
	
	@Column(name = "usuario_email", unique = true)
	private String usuarioEmail;
	
	@Column(name = "usuario_password")
	private String usuarioPassword;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private Set<Donacion> donaciones;
	
	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
	private Formulario formulario;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(
			name = "usuarios_roles",
			joinColumns = @JoinColumn(name = "usuario_id",referencedColumnName = "usuario_id"),
			inverseJoinColumns = @JoinColumn(name = "rol_id",referencedColumnName = "rol_id")
			)
	private Collection<Rol> roles;
	
	@OneToOne(mappedBy = "jefePro", cascade = CascadeType.ALL)
	private Programa programa ;

	public Usuario(String usuarioNombre, String usuarioApellido, String dni_ce, String usuarioCelular,
			String usuarioEmail, String usuarioPassword, Collection<Rol> roles) {
		super();
		this.usuarioNombre = usuarioNombre;
		this.usuarioApellido = usuarioApellido;
		this.dni_ce = dni_ce;
		this.usuarioCelular = usuarioCelular;
		this.usuarioEmail = usuarioEmail;
		this.usuarioPassword = usuarioPassword;
		this.roles = roles;
	}

	
	
	
	
}