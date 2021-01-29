package com.metis.entity;

/**
 * @Description: Monitor
 * @Author: YiHui
 * @Date: 2021-01-27 14:44
 * @Version: ing
 */
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
@Getter
@Setter
public class Monitor{
    private static final long serialVersionUID = 1L;

    /** 进程ID */
    private Long traceId;
    /** Advice --> method body, span around invoked method */
    private Long spanId;

    /**
     * Format MULTI_LINE_STYLE output
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("traceId", getTraceId())
                .append("spanId", getSpanId())
                .toString();
    }
}
