package javachi.biz.hotelmanagementsystem.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpApiResponse<T> {

    private boolean success;
    private String message;
    private int code;
    private T content;
    private List<ErrorDto> errorList;

}
