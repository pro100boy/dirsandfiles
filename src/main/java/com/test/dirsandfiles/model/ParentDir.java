package com.test.dirsandfiles.model;

import com.test.dirsandfiles.util.DateTimeUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = "subdirs")
@Entity
@Table(name = "dirs")
public class ParentDir extends NamedEntity {

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime date;

    @Column(nullable = false)
    @NotNull
    private Integer dircount;

    @Column(nullable = false)
    @NotNull
    private Integer filescount;

    // https://stackoverflow.com/a/4329723/7203956
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentdir", cascade = CascadeType.ALL)
    //@OrderBy("dateTime DESC") TODO тут подставить нужную сортировку
    protected List<SubDir> subdirs;
}
