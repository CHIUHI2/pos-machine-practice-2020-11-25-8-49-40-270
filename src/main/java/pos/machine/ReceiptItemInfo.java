package pos.machine;

public class ReceiptItemInfo extends ItemInfo{
    private final int quantity;
    private final int subTotal;


    public ReceiptItemInfo(String barcode, String name, int unitPrice, int quantity) {
        super(barcode, name, unitPrice);
        this.quantity = quantity;
        this.subTotal = unitPrice * quantity;
    }

    public int getQuantity() { return quantity; }

    public int getSubTotal() { return subTotal; }
}
