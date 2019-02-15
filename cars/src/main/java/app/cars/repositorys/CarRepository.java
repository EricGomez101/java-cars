package app.cars.repositorys;

import app.cars.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long>
{
    List<Car> findByYear(int year);
    List<Car> findByBrandIgnoreCase(String brand);
}
