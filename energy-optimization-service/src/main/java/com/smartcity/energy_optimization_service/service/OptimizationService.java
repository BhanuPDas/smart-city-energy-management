package com.smartcity.energy_optimization_service.service;

import com.smartcity.energy_optimization_service.model.Building;
import com.smartcity.energy_optimization_service.model.EnergySource;
import com.smartcity.energy_optimization_service.model.EnergyType;
import com.smartcity.energy_optimization_service.repository.BuildingRepository;
import com.smartcity.energy_optimization_service.repository.EnergySourceRepository;
import com.smartcity.energy_optimization_service.repository.EnergyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Service
public class OptimizationService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private EnergySourceRepository energySourceRepository;

    @Autowired
    private EnergyTypeRepository energyTypeRepository;

    private static final double EFFICIENCY_THRESHOLD = 250.0; // kWh/m²/year

    public String generateRecommendation(String ownerEmail) {
        StringBuilder recommendations = new StringBuilder();

        List<Building> buildings = buildingRepository.findByOwnerEmail(ownerEmail);
        if (buildings.isEmpty()) {
            return "No buildings found for owner email: " + ownerEmail;
        }

        for (Building building : buildings) {
            recommendations.append("🏠 Building at ").append(building.getAddress()).append("\n");

            List<EnergySource> sources = energySourceRepository.findByBuilding(building);
            if (sources.isEmpty()) {
                recommendations.append("  - No energy sources found.\n\n");
                continue;
            }

            for (EnergySource source : sources) {
                EnergyType type = source.getEnergyType();
                double cost = source.getConsumption() * type.getPricePerUnit();

                recommendations.append("  🔌 Energy type: ").append(type.getEnergyType())
                        .append(", cost: €").append(String.format("%.2f", cost)).append("\n");

                // Rule 1: Cheaper energy type available?
                List<EnergyType> allTypes = energyTypeRepository.findAll();
                for (EnergyType other : allTypes) {
                    if (!other.equals(type) && other.getPricePerUnit() < type.getPricePerUnit()) {
                        recommendations.append("  💡 Tip: Consider switching to ")
                                .append(other.getEnergyType())
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
                Set<String> renewableTypes = Set.of("solar", "heat pump"); // Updated to match your team's energy types
                if (renewableTypes.stream().noneMatch(energyName::contains)) {
                    recommendations.append(
                            "  🌱 Switching to Solar or Heat Pump can save you money in the long run while helping the planet. It's a smart, sustainable choice! 🌍\n");
                }

                recommendations.append("\n");
            }
        }

        return recommendations.toString();
    }
}
