public class PickupPointShipment extends ShipmentOrder {
    private String lockerSize;
    private boolean fragile;

    public PickupPointShipment(String orderNumber, String customerName, double distanceKm, double baseFee, boolean insured, String lockerSize, boolean fragile) {
        super(orderNumber, customerName, distanceKm, baseFee, insured);
        this.lockerSize = lockerSize;
        this.fragile = fragile;
    }

    @Override
    public String getShipmentType() {
        return "Pickup point";
    }

    @Override
    protected double calculateBasePrice() {
        return getBaseFee() + getDistanceKm() * 0.75;
    }

    @Override
    protected double calculateAdditionalFee() {
        double fee = 0;

        if (lockerSize.equals("S")) {
            fee += 5;
        } else if (lockerSize.equals("M")) {
            fee += 10;
        } else if (lockerSize.equals("L")) {
            fee += 18;
        }

        if (fragile) {
            fee += 12;
        }

        return fee;
    }

    @Override
    protected void validateSpecificRules() {
        if (!lockerSize.equals("S") && !lockerSize.equals("M") && !lockerSize.equals("L")) {
            throw new IllegalArgumentException("Locker size must be S, M or L");
        }
    }
}
