package javachi.biz.hotelmanagementsystem.repository;

import javachi.biz.hotelmanagementsystem.domain.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<HotelEntity, Integer> {
}
