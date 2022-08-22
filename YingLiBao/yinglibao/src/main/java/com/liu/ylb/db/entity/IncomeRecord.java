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
 * 收益记录表
 * @TableName b_income_record
 */
@TableName(value ="b_income_record")
@Data
public class IncomeRecord implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField(value = "uid")
    private Integer uid;

    /**
     * 产品ID
     */
    @TableField(value = "prod_id")
    private Integer prodId;

    /**
     * 投标记录ID
     */
    @TableField(value = "bid_id")
    private Integer bidId;

    /**
     * 投资金额
     */
    @TableField(value = "bid_money")
    private BigDecimal bidMoney;

    /**
     * 期到时间
     */
    @TableField(value = "income_date")
    private Date incomeDate;

    /**
     * 收益金额
     */
    @TableField(value = "income_money")
    private BigDecimal incomeMoney;

    /**
     * 收益状态（0未返，1已返）
     */
    @TableField(value = "income_status")
    private Integer incomeStatus;

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
        IncomeRecord other = (IncomeRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getProdId() == null ? other.getProdId() == null : this.getProdId().equals(other.getProdId()))
            && (this.getBidId() == null ? other.getBidId() == null : this.getBidId().equals(other.getBidId()))
            && (this.getBidMoney() == null ? other.getBidMoney() == null : this.getBidMoney().equals(other.getBidMoney()))
            && (this.getIncomeDate() == null ? other.getIncomeDate() == null : this.getIncomeDate().equals(other.getIncomeDate()))
            && (this.getIncomeMoney() == null ? other.getIncomeMoney() == null : this.getIncomeMoney().equals(other.getIncomeMoney()))
            && (this.getIncomeStatus() == null ? other.getIncomeStatus() == null : this.getIncomeStatus().equals(other.getIncomeStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getProdId() == null) ? 0 : getProdId().hashCode());
        result = prime * result + ((getBidId() == null) ? 0 : getBidId().hashCode());
        result = prime * result + ((getBidMoney() == null) ? 0 : getBidMoney().hashCode());
        result = prime * result + ((getIncomeDate() == null) ? 0 : getIncomeDate().hashCode());
        result = prime * result + ((getIncomeMoney() == null) ? 0 : getIncomeMoney().hashCode());
        result = prime * result + ((getIncomeStatus() == null) ? 0 : getIncomeStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uid=").append(uid);
        sb.append(", prodId=").append(prodId);
        sb.append(", bidId=").append(bidId);
        sb.append(", bidMoney=").append(bidMoney);
        sb.append(", incomeDate=").append(incomeDate);
        sb.append(", incomeMoney=").append(incomeMoney);
        sb.append(", incomeStatus=").append(incomeStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}