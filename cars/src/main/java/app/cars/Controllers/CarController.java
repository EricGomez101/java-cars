package app.cars.Controllers;

import app.cars.CarNotFoundException;
import app.cars.CarsApplication;
import app.cars.Message;
import app.cars.models.Car;
import app.cars.repositorys.CarRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController
{
    private final CarRepository CARREPO;
    private final RabbitTemplate RABBIT_TEMPLATE;

    public CarController(CarRepository CARREPO, RabbitTemplate RABBIT_TEMPLATE)
    {
        this.CARREPO = CARREPO;
        this.RABBIT_TEMPLATE = RABBIT_TEMPLATE;
    }
    @GetMapping("/id/{id}")
    public Car findOneById(@PathVariable Long id)
    {
        return CARREPO.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    @GetMapping("/year/{year}")
    public List<Car> findByYear(@PathVariable int year)
    {
        return CARREPO.findByYear(year);
    }

    @GetMapping("/brand/{brand}")
    public List<Car> findByBrand(@PathVariable String brand)
    {
        Message message = new Message(("Search for " + brand));
        RABBIT_TEMPLATE.convertAndSend(CarsApplication.QUEUE_NAME, message.toString());
        return CARREPO.findByBrandIgnoreCase(brand);
    }

    @PostMapping("/upload")
    public List<Car> newCar(@RequestBody List<Car> newCars)
    {
        Message message = new Message("Data loaded");
        RABBIT_TEMPLATE.convertAndSend(CarsApplication.QUEUE_NAME, message.toString());
        return CARREPO.saveAll(newCars);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id)
    {
        Message message = new Message(id + " Data deleted");
        RABBIT_TEMPLATE.convertAndSend(CarsApplication.QUEUE_NAME, message.toString());
        CARREPO.deleteById(id);
    }
}
