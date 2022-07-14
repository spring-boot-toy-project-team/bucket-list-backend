package com.bucket.list.tag.entity;

import com.bucket.list.completedList.entity.CompletedList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class CompletedListTag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long completedListTagId;

  @ManyToOne
  @JoinColumn(name = "COMPLETED_LIST_ID")
  private CompletedList completedList;

  @ManyToOne
  @JoinColumn(name = "TAG_ID")
  private Tag tag;

  public void addCompletedList(CompletedList completedList) {
    this.completedList =completedList;
  }

  public void addTag(Tag tag) {
    this.tag = tag;
  }
}
