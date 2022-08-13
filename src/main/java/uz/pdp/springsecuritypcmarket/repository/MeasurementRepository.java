package uz.pdp.springsecuritypcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.springsecuritypcmarket.entity.Measurement;
import uz.pdp.springsecuritypcmarket.projection.AbsEntityProjection;

@RepositoryRestResource(path = "measurement", excerptProjection = AbsEntityProjection.class)
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}
