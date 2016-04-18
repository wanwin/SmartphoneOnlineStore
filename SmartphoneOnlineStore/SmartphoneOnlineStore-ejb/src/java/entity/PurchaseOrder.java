/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alumno
 */
@Entity
@Table(name = "PURCHASE_ORDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PurchaseOrder.findAll", query = "SELECT p FROM PurchaseOrder p"),
    @NamedQuery(name = "PurchaseOrder.findByOrderNum", query = "SELECT p FROM PurchaseOrder p WHERE p.orderNum = :orderNum"),
    @NamedQuery(name = "PurchaseOrder.findByQuantity", query = "SELECT p FROM PurchaseOrder p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "PurchaseOrder.findByPurchaseCost", query = "SELECT p FROM PurchaseOrder p WHERE p.purchaseCost = :purchaseCost"),
    @NamedQuery(name = "PurchaseOrder.findBySalesDate", query = "SELECT p FROM PurchaseOrder p WHERE p.salesDate = :salesDate"),
    @NamedQuery(name = "PurchaseOrder.findByShippingDate", query = "SELECT p FROM PurchaseOrder p WHERE p.shippingDate = :shippingDate"),
    @NamedQuery(name = "PurchaseOrder.findByFreightCompany", query = "SELECT p FROM PurchaseOrder p WHERE p.freightCompany = :freightCompany")})
public class PurchaseOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ORDER_NUM")
    private Integer orderNum;
    @Column(name = "QUANTITY")
    private Short quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PURCHASE_COST")
    private Double purchaseCost;
    @Column(name = "SALES_DATE")
    @Temporal(TemporalType.DATE)
    private Date salesDate;
    @Column(name = "SHIPPING_DATE")
    @Temporal(TemporalType.DATE)
    private Date shippingDate;
    @Size(max = 30)
    @Column(name = "FREIGHT_COMPANY")
    private String freightCompany;
    @Lob
    @Column(name = "PRODUCTS")
    private Serializable products;
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID")
    @ManyToOne(optional = false)
    private Customer customerId;

    public PurchaseOrder() {
    }

    public PurchaseOrder(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    public Double getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(Double purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getFreightCompany() {
        return freightCompany;
    }

    public void setFreightCompany(String freightCompany) {
        this.freightCompany = freightCompany;
    }

    public Serializable getProducts() {
        return products;
    }

    public void setProducts(Serializable products) {
        this.products = products;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderNum != null ? orderNum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PurchaseOrder)) {
            return false;
        }
        PurchaseOrder other = (PurchaseOrder) object;
        if ((this.orderNum == null && other.orderNum != null) || (this.orderNum != null && !this.orderNum.equals(other.orderNum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PurchaseOrder[ orderNum=" + orderNum + " ]";
    }
    
}
