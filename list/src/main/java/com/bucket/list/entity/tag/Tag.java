package com.bucket.list.entity.tag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "TAG" +
        "")
public class Tag {

    @Id
    private long tagId;

    @Column(length = 200,unique = true)
    private String name;

    @OneToMany(mappedBy = "completedListTag")
    private List<CompletedListTag> completedListTagList;

    public void addCompletedListTag(CompletedListTag completedListTag){
        this.completedListTagList.add(completedListTag);
    }
}
