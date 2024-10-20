package javachi.biz.hotelmanagementsystem.domain;

import jakarta.persistence.*;
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
    @Column(unique = true)
    private Integer roomNumber;
    private Integer roomCapacity;
    private Double roomPrice;
    private Boolean roomStatus;
    private Integer hotelId;
}
