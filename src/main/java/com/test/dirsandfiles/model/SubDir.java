package com.test.dirsandfiles.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = "parentdir")
@Entity
@Table(name = "subdirs")
public class SubDir extends NamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ParentDir parentdir;

    public SubDir(Integer id, String path, String size, ParentDir parentdir) {
        super(id, path, size);
        this.parentdir = parentdir;
    }
}

