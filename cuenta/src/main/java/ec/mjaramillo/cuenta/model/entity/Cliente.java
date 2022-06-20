package ec.mjaramillo.cuenta.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "cliente")
public class Cliente extends Persona implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String contrasena;
	private String estado;

	public Cliente(Persona persona, String contrasena, String estado) {
		super(persona.getIdentificacion(), persona.getNombre(), persona.getGenero(), persona.getEdad(), 
				persona.getDireccion(), persona.getTelefono());
		this.estado = estado;
		this.contrasena = contrasena;
	}
	
	public Cliente(Persona persona) {
		super(persona.getIdentificacion(), persona.getNombre(), persona.getGenero(), persona.getEdad(), 
				persona.getDireccion(), persona.getTelefono());
	}
	
	public Cliente() {
		
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	@OneToMany(
			cascade = CascadeType.ALL
	)
	@JoinColumn(name="cuenta_id")
	private List<Cuenta> cuentas = new ArrayList<>();

	public void agregarCuenta(Cuenta cuenta) {
		cuentas.add(cuenta);
	}
	
	public void eliminarCuenta(Cuenta cuenta) {
		cuentas.remove(cuenta);
	}
}
