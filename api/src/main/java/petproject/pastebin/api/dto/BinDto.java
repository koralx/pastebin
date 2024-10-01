package petproject.pastebin.api.dto;

import lombok.Data;

@Data
public class BinDto {
    String title;
    String text;
    String tags;
}
