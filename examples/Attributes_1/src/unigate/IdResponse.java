package src.unigate;

import lombok.Data;

import java.io.Serializable;

@Data
public class IdResponse<T extends Serializable> {
    private final T id;
}
