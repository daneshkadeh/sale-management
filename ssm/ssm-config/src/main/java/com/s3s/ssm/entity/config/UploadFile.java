package com.s3s.ssm.entity.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractIdOLObject;

@Entity
@Table(name = "s_upload_file")
public class UploadFile extends AbstractIdOLObject {
    private String title;
    private byte[] data;

    @Column(name = "title", length = 128)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "content")
    @Lob
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
