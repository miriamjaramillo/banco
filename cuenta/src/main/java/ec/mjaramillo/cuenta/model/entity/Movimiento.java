package ec.mjaramillo.cuenta.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "movimiento")
public class Movimiento implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name =  "movimiento_id", unique = true  , nullable = false)
	@TableGenerator(name="entidad_generadora",
	    table="secuencia",
	    pkColumnName="nombre",
	    valueColumnName="valor",
	    pkColumnValue="movimiento",
	    initialValue=1,
	    allocationSize=1
	)
	@GeneratedValue(strategy = GenerationType.TABLE,
			generator = "entidad_generadora")
	private Long movimientoId;
	@Column(name ="fecha", nullable=false)
	private Date fecha;
	@Column(name ="tipo_movimiento", nullable=false)
	private String tipoMovimiento;
	private Double valor;
	private Double saldo;
	
	public Movimiento() {
	}
	
	public Movimiento(String tipoMovimiento, Double valor) {
		Calendar calendar = Calendar.getInstance();
		
		this.tipoMovimiento = tipoMovimiento;
		this.valor = valor;
		this.fecha = calendar.getTime();
		
	}
	
	public Long getMovimientoId() {
		return movimientoId;
	}
	public void setMovimientoId(Long movimientoId) {
		this.movimientoId = movimientoId;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getTipoMovimiento() {
		return tipoMovimiento;
	}
	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	@ManyToMany(
			fetch = FetchType.LAZY,
		    cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		    },
			mappedBy = "movimientos"
	)
	@JsonIgnore
	private List<Cuenta> cuentas = new ArrayList<>();

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
	
	public void agregarCuenta(Cuenta cuenta) {
		cuentas.add(cuenta);
	}
	
}
