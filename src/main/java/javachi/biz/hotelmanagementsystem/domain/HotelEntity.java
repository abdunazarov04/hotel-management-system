package javachi.biz.hotelmanagementsystem.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hotel")
public class HotelEntity extends BaseEntity {

    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CommentsEntity> comments;

    @NotBlank(message = "Hotel phone cannot be blank or null")
    private String phone;

    @NotBlank(message = "Hotel address cannot be blank or null")
    private String address;
}
