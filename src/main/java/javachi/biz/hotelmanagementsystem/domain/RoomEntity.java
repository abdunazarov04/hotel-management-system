package javachi.biz.hotelmanagementsystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rooms")
public class RoomEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    private String roomNumber;
    private Integer roomCapacity;
    private Double roomPrice;
    private Boolean roomStatus;
}
