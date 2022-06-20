package ec.mjaramillo.cuenta.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.mjaramillo.cuenta.model.entity.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, Long>{
	public Optional<Cuenta> findOneByNumeroCuenta(String numeroCuenta);
}
