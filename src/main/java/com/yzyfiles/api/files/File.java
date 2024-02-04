package com.yzyfiles.api.files;

import org.springframework.data.annotation.Id;

public class File {
    @Id
    public String id; // the mongodb id

    public String uploadId;
    public String fileName;

    // most likely not needed as we have id
    //public String path;
    // not yet
    //public String uploaderId;

}
