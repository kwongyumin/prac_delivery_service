package org.delivery.storeadmin.domain.sse.connection.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoolIfs;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;


@Getter
@ToString
@EqualsAndHashCode
public class UserSseConnection {

    private final String uniquekey;

    private final SseEmitter sseEmitter;

    private final ConnectionPoolIfs<String,UserSseConnection> connectionPoolIfs;

    private final ObjectMapper objectMapper;

    private UserSseConnection(String uniquekey, ConnectionPoolIfs<String,UserSseConnection> connectionPoolIfs,ObjectMapper objectMapper) {
        this.uniquekey = uniquekey;
        this.sseEmitter = new SseEmitter(60 * 1000L);
        this.connectionPoolIfs = connectionPoolIfs;
        this.objectMapper = objectMapper;

        // on completion 처리
        this.sseEmitter.onCompletion(() ->{
            // connection pool remove
            this.connectionPoolIfs.onCompletionCallback(this);

        });
        // on timeout 처리
        this.sseEmitter.onTimeout(() ->{
            this.sseEmitter.complete();
        });

        // onopen 메시지
        sendMessage("onopen","connect");
    }

    public static UserSseConnection connect(String uniquekey, ConnectionPoolIfs<String,UserSseConnection> connectionPoolIfs, ObjectMapper objectMapper) {
        return new UserSseConnection(uniquekey,connectionPoolIfs,objectMapper);
    }


    public void sendMessage(String eventName,Object data) {
        try {
            var json = this.objectMapper.writeValueAsString(data);
            var event = SseEmitter.event()
                    .name(eventName)
                    .data(json);

            this.sseEmitter.send(event);

        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }

    public void sendMessage(Object data) {
        try {
            var json = this.objectMapper.writeValueAsString(data);
            var event = SseEmitter.event()
                    .data(json);

            this.sseEmitter.send(event);

        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }
}
