package com.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(unique = true , nullable = false ,length = 100)
    private String categoryName;

    @Column(length = 500)
    private String categoryDescription;

   //private List<Post> posts=new ArrayList<>();

   private LocalDateTime createdAt;
   private LocalDateTime updatedAt;

   @PrePersist
   protected void onCreate() {
       createdAt = LocalDateTime.now();
       updatedAt=LocalDateTime.now();
   }

   @PrePersist
   protected void onUpdate() {
       updatedAt = LocalDateTime.now();
   }
}
