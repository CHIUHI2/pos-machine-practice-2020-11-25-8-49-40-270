package pos.machine;

public class ReceiptItemInfo extends ItemInfo{
    private final int quantity;

    public ReceiptItemInfo(String barcode, String name, int unitPrice, int quantity) {
        super(barcode, name, unitPrice);
        this.quantity = quantity;
    }

    public int getQuantity() { return quantity; }

    public int getSubTotal() { return super.getPrice() * quantity; }
}
