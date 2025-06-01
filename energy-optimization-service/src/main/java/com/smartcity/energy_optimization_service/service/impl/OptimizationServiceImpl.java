package com.smartcity.energy_optimization_service.service.impl;

import com.smartcity.energy_optimization_service.domain.entity.BuildingEntity;
import com.smartcity.energy_optimization_service.domain.entity.EnergySourceEntity;
import com.smartcity.energy_optimization_service.domain.entity.EnergyTypeEntity;
import com.smartcity.energy_optimization_service.repository.BuildingRepository;
import com.smartcity.energy_optimization_service.repository.EnergySourceRepository;
import com.smartcity.energy_optimization_service.repository.EnergyTypeRepository;
import com.smartcity.energy_optimization_service.service.OptimizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Service
public class OptimizationServiceImpl implements OptimizationService {

	private final Logger logger = LoggerFactory.getLogger(OptimizationServiceImpl.class);

	@Autowired
	private BuildingRepository buildingRepository;

	@Autowired
	private EnergySourceRepository energySourceRepository;

	@Autowired
	private EnergyTypeRepository energyTypeRepository;

	private static final double EFFICIENCY_THRESHOLD = 250.0; // kWh/m²/year

	public String generateRecommendation(String ownerEmail) {
		StringBuilder recommendations = new StringBuilder();
		try {
			List<BuildingEntity> buildings = buildingRepository.findByOwnerEmail(ownerEmail);
			if (buildings.isEmpty()) {
				logger.info("No buildings found for owner email: ownerEmail");
				return ("No buildings found for owner email: " + ownerEmail);
			}
			for (BuildingEntity building : buildings) {
				recommendations.append("🏠 Building at ").append(building.getAddress()).append("\n");
				List<EnergySourceEntity> sources = energySourceRepository.findByBuilding(building);
				if (sources.isEmpty()) {
					logger.info("No energy sources found");
					recommendations.append("  - No energy sources found.\n\n");
					continue;
				}
				for (EnergySourceEntity source : sources) {
					EnergyTypeEntity type = source.getEnergyType();
					double cost = source.getConsumption() * type.getPricePerUnit();
					recommendations.append("  🔌 Energy type: ").append(type.getEnergyType()).append(", cost: €")
							.append(String.format("%.2f", cost)).append("\n");
					// Rule 1: Cheaper energy type available?
					List<EnergyTypeEntity> allTypes = energyTypeRepository.findAll();
					for (EnergyTypeEntity other : allTypes) {
						if (!other.equals(type) && other.getPricePerUnit() < type.getPricePerUnit()) {
							recommendations.append("  💡 Tip: Consider switching to ").append(other.getEnergyType())
									.append(" (price: €").append(other.getPricePerUnit()).append("/unit).\n");
							break;
						}
					}
					// Rule 2: High consumption per m²/year
					long days = ChronoUnit.DAYS.between(source.getStartDate(), source.getEndDate());
					if (days == 0)
						days = 1; // avoid division by zero
					double annualizedConsumption = (source.getConsumption() / days) * 365;
					double consumptionPerM2 = annualizedConsumption / building.getFloorArea();
					if (consumptionPerM2 > EFFICIENCY_THRESHOLD) {
						recommendations.append("  ⚠️ Your consumption is ")
								.append(String.format("%.0f", consumptionPerM2)).append(" kWh/m²/year, which is ")
								.append(String.format("%.0f", consumptionPerM2 - EFFICIENCY_THRESHOLD))
								.append(" above the recommended level.\n");
					} else {
						// Rule 4: Congratulate if efficient
						recommendations.append("  ✅ Good job! Your energy usage is efficient at ")
								.append(String.format("%.0f", consumptionPerM2)).append(" kWh/m²/year.\n");
					}
					// Rule 3: Suggest renewable energy
					String energyName = type.getEnergyType().toLowerCase();
					Set<String> renewableTypes = Set.of("solar", "heat pump"); // Updated to match your team's energy
																				// types
					if (renewableTypes.stream().noneMatch(energyName::contains)) {
						recommendations.append(
								"  🌱 Switching to Solar or Heat Pump can save you money in the long run while helping the planet. It's a smart, sustainable choice! 🌍\n");
					}
					recommendations.append("\n");
				}
			}
			return recommendations.toString();
		} catch (Exception e) {
			logger.error("Exception occured while processing optimization request: " + e.getMessage());
		}
		return null;
	}
}
