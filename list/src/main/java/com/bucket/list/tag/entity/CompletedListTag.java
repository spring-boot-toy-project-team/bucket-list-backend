package com.bucket.list.tag.entity;

import com.bucket.list.completList.entity.CompletedList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class CompletedListTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long completedListTagId;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "COMPLETED_LIST_ID")
    private CompletedList completedList;


    public void addTag(Tag tag){
        this.tag=tag;
    }

    public void addCompleteList(CompletedList completedList){
        this.completedList=completedList;
    }
}
