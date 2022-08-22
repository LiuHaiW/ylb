package com.liu.ylb.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 投资记录表
 * @TableName b_bid_info
 */
@TableName(value ="b_bid_info")
@Data
public class Bid implements Serializable {
    /**
     * 投标记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 产品ID
     */
    @TableField(value = "prod_id")
    private Integer prodId;

    /**
     * 用户ID
     */
    @TableField(value = "uid")
    private Integer uid;

    /**
     * 投标金额
     */
    @TableField(value = "bid_money")
    private BigDecimal bidMoney;

    /**
     * 投标时间
     */
    @TableField(value = "bid_time")
    private Date bidTime;

    /**
     * 投标状态
     */
    @TableField(value = "bid_status")
    private Integer bidStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Bid other = (Bid) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProdId() == null ? other.getProdId() == null : this.getProdId().equals(other.getProdId()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getBidMoney() == null ? other.getBidMoney() == null : this.getBidMoney().equals(other.getBidMoney()))
            && (this.getBidTime() == null ? other.getBidTime() == null : this.getBidTime().equals(other.getBidTime()))
            && (this.getBidStatus() == null ? other.getBidStatus() == null : this.getBidStatus().equals(other.getBidStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProdId() == null) ? 0 : getProdId().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getBidMoney() == null) ? 0 : getBidMoney().hashCode());
        result = prime * result + ((getBidTime() == null) ? 0 : getBidTime().hashCode());
        result = prime * result + ((getBidStatus() == null) ? 0 : getBidStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", prodId=").append(prodId);
        sb.append(", uid=").append(uid);
        sb.append(", bidMoney=").append(bidMoney);
        sb.append(", bidTime=").append(bidTime);
        sb.append(", bidStatus=").append(bidStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}