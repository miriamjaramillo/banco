package ec.mjaramillo.cuenta.exception;

import java.util.Date;

public class ErrorMessage {
  private int estadoCodigo;
  private Date fecha;
  private String mensaje;
  private String descripcion;

  public ErrorMessage(int estadoCodigo, Date fecha, String mensaje, String descripcion) {
    this.estadoCodigo = estadoCodigo;
    this.fecha = fecha;
    this.mensaje = mensaje;
    this.descripcion = descripcion;
  }

	public int getEstadoCodigo() {
		return estadoCodigo;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

}