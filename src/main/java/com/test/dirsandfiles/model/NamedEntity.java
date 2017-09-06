package com.test.dirsandfiles.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=true)
public class NamedEntity extends BaseEntity {

    @Column(nullable = false)
    @NotBlank
    private String path;

    @Column(nullable = false)
    @NotNull
    private String size; //<DIR>, 4,28Kb, 5Mb ...

    public NamedEntity(Integer id, String path, String size) {
        super(id);
        this.path = path;
        this.size = size;
    }
}
