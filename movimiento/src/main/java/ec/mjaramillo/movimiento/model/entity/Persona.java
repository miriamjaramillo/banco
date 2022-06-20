package ec.mjaramillo.movimiento.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@Table(name="persona")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ 
  @Type(value = Cliente.class, name = "cliente")
})
public abstract class Persona implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name =  "persona_id", unique = true  , nullable = false)
	@TableGenerator(name="entidad_generadora",
	    table="secuencia",
	    pkColumnName="nombre",
	    valueColumnName="valor",
	    pkColumnValue="persona",
	    initialValue=1,
	    allocationSize=1
	)
	@GeneratedValue(strategy = GenerationType.TABLE,
			generator = "entidad_generadora")
	private Long personaId;
	
	@Column(name ="identificacion", unique=true, nullable=false)
	private String identificacion;
	
	private String nombre;
	private String genero;
	private Integer edad;
	private String direccion;
	private String telefono;
	
	public Persona(String identificacion, String nombre, String genero, Integer edad, String direccion, String telefono) {
		super();
		this.identificacion = identificacion;
		this.nombre = nombre;
		this.genero = genero;
		this.edad = edad;
		this.direccion = direccion;
		this.telefono = telefono;
	}
	
	public Persona() {
		super();
	}
	
	public Long getPersonaId() {
		return personaId;
	}
	
	public void setPersonaId(Long idPersona) {
		this.personaId = idPersona;
	}
	
	public String getIdentificacion() {
		return identificacion;
	}
	
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getGenero() {
		return genero;
	}
	
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	public Integer getEdad() {
		return edad;
	}
	
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
}

