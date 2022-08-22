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
 * 充值记录表
 * @TableName b_recharge_record
 */
@TableName(value ="b_recharge_record")
@Data
public class Recharge implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField(value = "uid")
    private Integer uid;

    /**
     * 充值订单号
     */
    @TableField(value = "recharge_no")
    private String rechargeNo;

    /**
     * 充值订单状态（0充值中，1充值成功，2充值失败）
     */
    @TableField(value = "recharge_status")
    private Integer rechargeStatus;

    /**
     * 充值金额
     */
    @TableField(value = "recharge_money")
    private BigDecimal rechargeMoney;

    /**
     * 充值时间
     */
    @TableField(value = "recharge_time")
    private Date rechargeTime;

    /**
     * 充值描述
     */
    @TableField(value = "recharge_desc")
    private String rechargeDesc;

    /**
     * 
     */
    @TableField(value = "channel")
    private String channel;

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
        Recharge other = (Recharge) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getRechargeNo() == null ? other.getRechargeNo() == null : this.getRechargeNo().equals(other.getRechargeNo()))
            && (this.getRechargeStatus() == null ? other.getRechargeStatus() == null : this.getRechargeStatus().equals(other.getRechargeStatus()))
            && (this.getRechargeMoney() == null ? other.getRechargeMoney() == null : this.getRechargeMoney().equals(other.getRechargeMoney()))
            && (this.getRechargeTime() == null ? other.getRechargeTime() == null : this.getRechargeTime().equals(other.getRechargeTime()))
            && (this.getRechargeDesc() == null ? other.getRechargeDesc() == null : this.getRechargeDesc().equals(other.getRechargeDesc()))
            && (this.getChannel() == null ? other.getChannel() == null : this.getChannel().equals(other.getChannel()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getRechargeNo() == null) ? 0 : getRechargeNo().hashCode());
        result = prime * result + ((getRechargeStatus() == null) ? 0 : getRechargeStatus().hashCode());
        result = prime * result + ((getRechargeMoney() == null) ? 0 : getRechargeMoney().hashCode());
        result = prime * result + ((getRechargeTime() == null) ? 0 : getRechargeTime().hashCode());
        result = prime * result + ((getRechargeDesc() == null) ? 0 : getRechargeDesc().hashCode());
        result = prime * result + ((getChannel() == null) ? 0 : getChannel().hashCode());
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
        sb.append(", rechargeNo=").append(rechargeNo);
        sb.append(", rechargeStatus=").append(rechargeStatus);
        sb.append(", rechargeMoney=").append(rechargeMoney);
        sb.append(", rechargeTime=").append(rechargeTime);
        sb.append(", rechargeDesc=").append(rechargeDesc);
        sb.append(", channel=").append(channel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}