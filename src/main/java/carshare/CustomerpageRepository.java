package carshare;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerpageRepository extends CrudRepository<Customerpage, Long> {

    List<Customerpage> findByOrderId(Long orderId);


}