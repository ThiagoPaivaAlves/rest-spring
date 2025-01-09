package com.thiago.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UploadFileResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long fileSize;
}
