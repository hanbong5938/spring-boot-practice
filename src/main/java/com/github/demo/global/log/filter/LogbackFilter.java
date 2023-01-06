package com.github.demo.global.log.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LogbackFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(final ILoggingEvent event) {
        final String loggerName = event.getLoggerName();
        return loggerName == null ? FilterReply.DENY : FilterReply.ACCEPT;
    }
}
