package ec.mjaramillo.persona.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "cuenta")
public class Cuenta implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name =  "cuenta_id", unique = true  , nullable = false)
	@TableGenerator(name="entidad_generadora",
	    table="secuencia",
	    pkColumnName="nombre",
	    valueColumnName="valor",
	    pkColumnValue="cuenta",
	    initialValue=1,
	    allocationSize=1
	)
	@GeneratedValue(strategy = GenerationType.TABLE,
			generator = "entidad_generadora")
	private Long cuentaId;
	
	@Column(name ="numero_cuenta", unique=true, nullable=false)
	private String numeroCuenta;
	
	@Column(name ="tipo_cuenta", nullable=false)
	private String tipoCuenta;
	
	@Column(name ="saldo_inicial", nullable=false)
	private Double saldoInicial;
	
	private Boolean estado;
	
	@ManyToOne
	@JoinColumn(name = "persona_id", 
		referencedColumnName = "persona_id", 
		updatable = false, nullable = false
	)
	private Persona persona;
	
	public Cuenta() {
	}
	
	public Cuenta(String numeroCuenta, String tipoCuenta, Double saldoInical, Boolean estado, Persona persona) {
		this.numeroCuenta = numeroCuenta;
		this.tipoCuenta = tipoCuenta;
		this.saldoInicial = saldoInical;
		this.estado = estado;
		this.persona = persona;
	}
	
	public Long getCuentaId() {
		return cuentaId;
	}

	public void setCuentaId(Long cuentaId) {
		this.cuentaId = cuentaId;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public Double getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(Double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	@ManyToMany(
			fetch = FetchType.LAZY,
		    cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		    }
	)
	@JoinTable(
			name = "cuenta_movimiento",
			joinColumns = @JoinColumn(name = "cuenta_id"),
			inverseJoinColumns = @JoinColumn(name = "movimiento_id")
	)
	private List<Movimiento> movimientos = new ArrayList<>();
	
	public List<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	public void agregarMovimiento(Movimiento movimiento) {
		this.movimientos.add(movimiento);
		movimiento.getCuentas().add(this);
	}
	
	public void eliminarMovimiento(long idMovimiento) {
		Movimiento movimiento = this.movimientos.stream().filter(t-> t.getMovimientoId() == idMovimiento).findFirst().orElse(null);
		if (movimiento != null) {
			this.movimientos.remove(movimiento);
			movimiento.getCuentas().remove(this);
		}
	}
}