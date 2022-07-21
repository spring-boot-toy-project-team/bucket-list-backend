package com.bucket.list.comment.service;

import com.bucket.list.comment.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentsService {
  private final CommentsRepository commentsRepository;

}
