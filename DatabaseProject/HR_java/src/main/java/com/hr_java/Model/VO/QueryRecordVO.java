package com.hr_java.Model.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryRecordVO implements Serializable {
    @Nullable
    private String fid;
    @Nullable
    private String jtId;
    @Nullable
    private String pid;
    @Nullable
    private String time1;
    @Nullable
    private String time2;
}
