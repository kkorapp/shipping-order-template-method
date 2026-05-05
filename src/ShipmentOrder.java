
public abstract class ShipmentOrder implements SummaryPrintable {
    private String orderNumber;
    private String customerName;
    private double distanceKm;
    private double baseFee;
    private boolean insured;
    private double lastCalculatedPrice;

    public ShipmentOrder(String orderNumber, String customerName, double distanceKm, double baseFee, boolean insured) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.distanceKm = distanceKm;
        this.baseFee = baseFee;
        this.insured = insured;
    }

    public String getOrderNumber() { return orderNumber; }
    public String getCustomerName() { return customerName; }
    public double getDistanceKm() { return distanceKm; }
    public double getBaseFee() { return baseFee; }
    public boolean isInsured() { return insured; }

    public final void processOrder() {
        validateOrder();
        validateSpecificRules();

        double price = calculateBasePrice();
        price += calculateAdditionalFee();
        price = applyInsurance(price);
        price = applyBusinessDiscount(price);

        lastCalculatedPrice = round(price);
        printProcessingResult();
    }

    private void validateOrder() {
        if (orderNumber == null || orderNumber.isEmpty())
            throw new IllegalArgumentException("Order number empty");

        if (customerName == null || customerName.isEmpty())
            throw new IllegalArgumentException("Customer empty");

        if (distanceKm <= 0)
            throw new IllegalArgumentException("Distance invalid");
    }

    protected void validateSpecificRules() {
    }

    private double applyInsurance(double price) {
        if (insured) return price * 1.07;
        return price;
    }

    protected double applyBusinessDiscount(double price) {
        return price;
    }

    private void printProcessingResult() {
        System.out.println("Processed: " + orderNumber);
        System.out.println("Type: " + getShipmentType());
        System.out.println("Final price: " + lastCalculatedPrice);
    }

    @Override
    public String buildSummaryLine() {
        return orderNumber + " | " + customerName + " | " + getShipmentType() + " | " + lastCalculatedPrice;
    }

    protected double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    protected abstract double calculateBasePrice();
    protected abstract double calculateAdditionalFee();
    public abstract String getShipmentType();
}
