package ec.mjaramillo.cuenta.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.mjaramillo.cuenta.model.entity.Cuenta;

public interface ClienteRepository extends JpaRepository<Cuenta, Long>{

}
