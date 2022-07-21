package com.bucket.list.completedList.entity;

import com.bucket.list.completedList.entity.CompletedList;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Img {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long imgId;

  private LocalDateTime createdAt = LocalDateTime.now();

  private String fileDir;

  @ManyToOne
  @JoinColumn(name = "COMPLETED_LIST_ID")
  private CompletedList completedList;

  public void addCompletedList(CompletedList completedList) {
    this.completedList = completedList;
  }
}
