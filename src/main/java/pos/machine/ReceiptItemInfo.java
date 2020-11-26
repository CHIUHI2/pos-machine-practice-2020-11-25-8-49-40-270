package pos.machine;

public class ReceiptItemInfo extends ItemInfo{
    private final long quantity;

    public ReceiptItemInfo(String barcode, String name, int unitPrice, long quantity) {
        super(barcode, name, unitPrice);
        this.quantity = quantity;
    }

    public long getQuantity() { return quantity; }

    public long getSubTotal() { return super.getPrice() * quantity; }
}
