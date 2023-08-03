import java.util.HashMap;
import java.util.Map;

public class EnergyTypeStatsMap {
    private Map<String, EnergyTypeStats> energyStatsMap;

    public EnergyTypeStatsMap() {
        energyStatsMap = new HashMap<>();
        energyStatsMap.put("Water", new EnergyTypeStats());
        energyStatsMap.put("Air", new EnergyTypeStats());
        energyStatsMap.put("Solar", new EnergyTypeStats());
        energyStatsMap.put("Heat", new EnergyTypeStats());
    }

    public EnergyTypeStats getEnergyTypeStats(String energyType) {
        return energyStatsMap.get(energyType);
    }
}

