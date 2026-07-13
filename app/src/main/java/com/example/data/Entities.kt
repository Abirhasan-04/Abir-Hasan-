package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val location: String,
    val status: String,
    val date: String,
    val budget: String,
    val headline: String,
    val overview: String,
    val technicalSpecs: String, // Stringified or multiline key-value text
    val challenges: String,
    val solutions: String,
    val lessonsLearned: String,
    val timeline: String,
    val imageUrl: String,
    val isBookmarked: Boolean = false
)

@Entity(tableName = "blog_posts")
data class BlogEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val tags: String, // Comma separated tags
    val date: String,
    val readTime: String,
    val summary: String,
    val content: String, // Rich multiline technical text
    val authorName: String,
    val authorTitle: String,
    val imageUrl: String,
    val isBookmarked: Boolean = false
)

@Entity(tableName = "newsletter_subscribers")
data class NewsletterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val subscribedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "contact_submissions")
data class ContactSubmission(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val subject: String,
    val message: String,
    val submittedAt: Long = System.currentTimeMillis()
)
