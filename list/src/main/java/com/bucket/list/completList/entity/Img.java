package com.bucket.list.completList.entity;

import com.bucket.list.completList.entity.CompletedList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "IMG")
public class Img {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imgId;

    @ManyToOne
    @JoinColumn(name = "COMPLETED_LIST_ID")
    private CompletedList completedList;

    @Column(length = 500)
    private String fileDir;

    private LocalDateTime createdAt = LocalDateTime.now();


    public void addCompletedList(CompletedList completedList){
        this.completedList=completedList;
    }
}
