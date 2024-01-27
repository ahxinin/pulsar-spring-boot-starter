package com.ahxinin.pulsar;

import java.io.Serializable;
import lombok.Data;

/**
 * @description: 翻译消息
 */
@Data
public class TranslateMessage implements Serializable {

    private String	taskId;
}
