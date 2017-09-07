package com.test.dirsandfiles.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PathTo {
    @NotBlank
    @SafeHtml
    private String path;
}
