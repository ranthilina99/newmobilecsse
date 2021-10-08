class Order{
    constructor(id,orderName,supplierName,status,deliveryStatus,confirmation,Total){
        this.id = id;
        this.orderName = orderName;
        this.supplierName = supplierName;
        this.status = status;
        this.deliveryStatus = deliveryStatus;
        this.confirmation = confirmation;
        this.Total = Total;
    }
}

module.exports = Order;