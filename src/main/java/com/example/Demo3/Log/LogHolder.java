package com.example.Demo3.Log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LogHolder {

    private Long startTime;

    private Long endTime;

    private Map<String, Object> attributes;

    @JsonIgnore
    private String action;

    @JsonIgnore
    private String message;

    @JsonIgnore
    private StringJoiner buildMessage;

    public LogHolder() {
        attributes = new HashMap<>();
        buildMessage = new StringJoiner(",\n");
    }

    public LogHolder put(String key, Object value) {
        attributes.put(key, value);
        return this;
    }

    public Object get(String key) {
        return attributes.get(key);
    }

    public Long executionStartTime() {
        if (Objects.isNull(startTime)) {
            startTime = currentTimeMillis();
        }
        return startTime;
    }

    public Long executionEndTime() {
        if (Objects.isNull(endTime)) {
            endTime = currentTimeMillis();
            attributes.put(LogKeys.ELAPSE_TIME, endTime - startTime);
        }
        return endTime;
    }

    private Long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAction(String action) {
        put(LogKeys.LogEvent.ACTION, action);
    }

    public void setMessage(String message) {
        put(LogKeys.LogEvent.MESSAGE, message);
    }

    public void buildMessage(Object message) {
        if (Objects.nonNull(message)) {
            buildMessage.add(message.toString());
            setMessage(buildMessage.toString());
        }
    }

    /**
     * It will format message as key.toString() : value.toString()
     */
    public void buildMessage(Object key, Object value) {
        if (Objects.nonNull(key) && Objects.nonNull(value)) {
            buildMessage(String.format("%s : %s", key.toString(), value.toString()));
        }
    }
}
