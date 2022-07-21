package com.bucket.list.completedList.entity;

import com.bucket.list.completedList.entity.CompletedList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Img {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
