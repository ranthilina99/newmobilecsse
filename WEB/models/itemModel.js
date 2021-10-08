class Item{
    constructor(id,itemSupplierId,itemName,itemPrice,itemPic,itemPolicyFlag){
        this.id = id;
        this.itemSupplierId = itemSupplierId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemPic = itemPic;
        this.itemPolicyFlag = itemPolicyFlag;
    }
}

module.exports = Item;