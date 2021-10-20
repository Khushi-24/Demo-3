package com.example.Demo3.Encryption;

import com.example.Demo3.Log.LogHolder;
import com.example.Demo3.Log.LogKeys;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static net.logstash.logback.marker.Markers.appendEntries;

@Component
@Slf4j
public class Converter extends AbstractHttpMessageConverter<Object> {

    private String privateKey;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${private.key}")
    private void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Converter() {
        super(MediaType.APPLICATION_JSON,
                new MediaType("application", "*+json", StandardCharsets.UTF_8));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }


    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String servletPath = request.getRequestURI();
            String path = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);

                return objectMapper.readValue(decrypt(inputMessage.getBody()), clazz);


    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String servletPath = request.getRequestURI();
            String path = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);

                outputMessage.getBody().write(encrypt(objectMapper.writeValueAsBytes(o)));


    }

    private InputStream decrypt(InputStream inputStream) {
        //this is API request params
        StringBuilder requestParamString = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c;
            while ((c = reader.read()) != -1) {
                requestParamString.append((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            JSONObject requestJsonObject = new
                    JSONObject(requestParamString.toString().replace("\n", ""));

            String decryptRequestString = Encryption.decrypt(requestJsonObject.getString("data"), privateKey);

            if (decryptRequestString != null) {

                return new ByteArrayInputStream(decryptRequestString.getBytes(StandardCharsets.UTF_8));
            } else {
                return inputStream;
            }
        } catch (JSONException err) {
            log.info(err.toString());
            return inputStream;
        }
    }

    private InputStream decryptLogging(InputStream inputStream) {
        StringBuilder requestParamString = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c;
            while ((c = reader.read()) != -1) {
                requestParamString.append((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject requestJsonObject = new
                    JSONObject(requestParamString.toString().replace("\n", ""));
            String requestString = requestJsonObject.toString();

            LogHolder logData = new LogHolder();
            logData.put(LogKeys.DECRYPTED_REQUEST, requestString);
            System.out.println(requestString);
            log.info(appendEntries(logData.getAttributes()), "after decrypt");

            return new ByteArrayInputStream(requestString.getBytes(StandardCharsets.UTF_8));
        } catch (JSONException err) {
            log.info(err.toString());
            return inputStream;
        }
    }

    private byte[] encrypt(byte[] bytesToEncrypt) {
        // do your encryption here
        String apiJsonResponse = new String(bytesToEncrypt);

        String encryptedString = Encryption.encrypt(apiJsonResponse, privateKey);
        if (encryptedString != null) {
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("data", encryptedString);
            JSONObject jsonObject = new JSONObject(hashMap);
            return jsonObject.toString().getBytes();
        } else
            return bytesToEncrypt;
    }

    private void encryptLogging(byte[] bytesToEncrypt) {
        // do your encryption here
        String requestString = new String(bytesToEncrypt);

        System.out.println(requestString);
        LogHolder logData = new LogHolder();
        logData.put(LogKeys.DECRYPTED_RESPONSE, requestString);
        System.out.println(requestString);
        log.info(appendEntries(logData.getAttributes()), "before encryption");

    }
}
