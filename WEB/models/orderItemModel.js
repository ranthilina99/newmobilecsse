class OrderItem{
    constructor(id,orderId,itemId,qty,subTotal){
        this.id = id;
        this.orderId = orderId;
        this.itemId = itemId;
        this.qty = qty;
        this.subTotal = subTotal;
    }
}

module.exports = OrderItem;