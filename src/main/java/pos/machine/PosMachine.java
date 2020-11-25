package pos.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PosMachine {
    /*
    P : 1 mins
    D : 1 mins
    C : check if method returns receipt content according to requirements -> result passed
    A : bug fix -> N/A, enhancement -> N/A
     */
    public String printReceipt(List<String> barcodes) {
        Map<String, Integer> itemQuantityMapping = this.getItemQuantityMapping(barcodes);

        return this.generateReceipt(itemQuantityMapping);
    }

    /*
    P : 1 mins
    D : 1 mins
    C : check if method calculates quantity of each item correctly -> result failed as default count of each item is set as 1 and the sorting of key is different with requirements
    A : bug fix -> set default count of each item to 0 and use treeMap instead of hashMap, enhancement -> N/A
     */
    private Map<String, Integer> getItemQuantityMapping(List<String> barcodes) {
        Map<String, Integer> itemQuantityMapping = new TreeMap<>();

        for(String barcode : barcodes) {
            itemQuantityMapping.putIfAbsent(barcode, 0);
            Integer count = itemQuantityMapping.get(barcode);
            itemQuantityMapping.put(barcode, count+1);
        }

        return  itemQuantityMapping;
    }

    /*
    P : 5 mins
    D : 6 mins
    C : check if method returns receipt content according to requirements -> result failed as link breaking symbol should be \n instead of \r\n
    A : bug fix -> line breaking with \n, enhancement -> think of combining calculateTotal inside this method to reduce the loop
     */
    private String generateReceipt(Map<String, Integer> itemQuantityMapping) {
        StringBuilder receipt = new StringBuilder("***<store earning no money>Receipt***");

        List<ReceiptItemInfo> receiptItemInfoList = this.getReceiptItemDetailList(itemQuantityMapping);
        for(ReceiptItemInfo receiptItemInfo : receiptItemInfoList) {
            receipt.append("\nName: ").append(receiptItemInfo.getName()).append(", Quantity: ").append(receiptItemInfo.getQuantity()).append(", Unit price: ").append(receiptItemInfo.getPrice()).append(" (yuan), Subtotal: ").append(receiptItemInfo.getSubTotal()).append(" (yuan)");
        }

        receipt.append("\n----------------------");

        receipt.append("\nTotal: ").append(this.calculateTotal(receiptItemInfoList)).append(" (yuan)");

        receipt.append("\n**********************");

        return receipt.toString();
    }

    /*
    P : 3 mins
    D : 4 mins
    C : check if method returns receipt item detail correctly -> result passed
    A : bug fix -> N/A, enhancement -> N/A
     */
    private List<ReceiptItemInfo> getReceiptItemDetailList(Map<String, Integer> itemQuantityMapping) {
        List<ReceiptItemInfo> receiptItemInfoList = new ArrayList<>();
        Map<String, ItemInfo> itemInfoMapping =  this.getItemInfoMapping();

        for(Map.Entry<String, Integer> itemQuantityEntry : itemQuantityMapping.entrySet()) {
            String barCode = itemQuantityEntry.getKey();
            Integer quantity = itemQuantityEntry.getValue();
            ItemInfo itemInfo = itemInfoMapping.get(barCode);
            if(itemInfo == null) {
                continue;
            }

            ReceiptItemInfo receiptItemInfo = new ReceiptItemInfo(barCode, itemInfo.getName(), itemInfo.getPrice(), quantity);
            receiptItemInfoList.add(receiptItemInfo);
        }

        return receiptItemInfoList;
    }

    /*
    P : 1 mins
    D : 1 mins
    C : check if method summarize all subtotal correctly -> result passed
    A : bug fix -> N/A, enhancement -> N/A
     */
    private Integer calculateTotal(List<ReceiptItemInfo> receiptItemInfoList) {
        int total = 0;

        for(ReceiptItemInfo receiptItemInfo : receiptItemInfoList) {
            total += receiptItemInfo.getSubTotal();
        }

        return total;
    }

    /*
    P : 1 mins
    D : 1 mins
    C : check if method returns correct mapping of barcode and respective ItemInfo
    A : review current logic and testing result, and fix bug
     */
    private Map<String, ItemInfo> getItemInfoMapping() {
        Map<String, ItemInfo> itemInfoMapping = new HashMap<>();

        List<ItemInfo> itemInfoList = ItemDataLoader.loadAllItemInfos();
        for(ItemInfo itemInfo: itemInfoList) {
            itemInfoMapping.put(itemInfo.getBarcode(), itemInfo);
        }

        return itemInfoMapping;
    }
}
