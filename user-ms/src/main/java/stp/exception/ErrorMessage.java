package stp.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorMessage {
    private Map<String, String> message;
    private String timestamp;
    private String path;
    private int status;
}
