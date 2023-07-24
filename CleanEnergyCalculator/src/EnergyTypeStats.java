public class EnergyTypeStats {
    private double totalEnergy;
    private int count;

    public EnergyTypeStats() {
        totalEnergy = 0.0;
        count = 0;
    }

    public void addEnergyValue(double energyValue) {
        totalEnergy += energyValue;
        count++;
    }

    public double getAverageEnergy() {
        return count > 0 ? totalEnergy / count : 0.0;
    }
}

