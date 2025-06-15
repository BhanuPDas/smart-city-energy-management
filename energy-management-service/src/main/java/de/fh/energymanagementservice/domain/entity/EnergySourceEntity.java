package de.fh.energymanagementservice.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "energy_source", schema = "rbh_mgmt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnergySourceEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer consumption;
	@Column(name = "start_date")
	private LocalDate startDate;
	@Column(name = "end_date")
	private LocalDate endDate;
	@ManyToOne
	@JoinColumn(name = "building_id", referencedColumnName = "id")
	private BuildingEntity building;
	@ManyToOne
	@JoinColumn(name = "energy_type_id", referencedColumnName = "id")
	private EnergyTypeEntity energyType;

}
