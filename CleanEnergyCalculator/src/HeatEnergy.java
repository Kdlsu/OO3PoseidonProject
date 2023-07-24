public class HeatEnergy {
    private double heatTemperature;
    private int time;

    public HeatEnergy(double heatTemperature, int time) {
        this.heatTemperature = heatTemperature;
        this.time = time;
    }

    public double calculateEnergy(double heatGeneratedOverTime) {
        // Implement heat energy calculation based on the heat temperature, time, and heat generated over time
        // For simplicity, let's assume a simple formula
        return heatTemperature * time * heatGeneratedOverTime;
    }
}


