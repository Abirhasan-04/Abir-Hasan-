package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EngineeringDao {
    // Projects
    @Query("SELECT * FROM projects ORDER BY date DESC")
    fun getAllProjectsFlow(): Flow<List<ProjectEntity>>

    @Query("SELECT * FROM projects WHERE id = :id")
    suspend fun getProjectById(id: String): ProjectEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProjects(projects: List<ProjectEntity>)

    @Query("UPDATE projects SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateProjectBookmark(id: String, isBookmarked: Boolean)

    // Blog posts
    @Query("SELECT * FROM blog_posts ORDER BY date DESC")
    fun getAllBlogPostsFlow(): Flow<List<BlogEntity>>

    @Query("SELECT * FROM blog_posts WHERE id = :id")
    suspend fun getBlogPostById(id: String): BlogEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlogPosts(posts: List<BlogEntity>)

    @Query("UPDATE blog_posts SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBlogPostBookmark(id: String, isBookmarked: Boolean)

    // Newsletter Subscribers
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSubscriber(subscriber: NewsletterEntity)

    @Query("SELECT COUNT(*) FROM newsletter_subscribers WHERE email = :email")
    suspend fun getSubscriberCount(email: String): Int

    // Contact Submissions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContactSubmission(submission: ContactSubmission)

    @Query("SELECT * FROM contact_submissions ORDER BY submittedAt DESC")
    fun getAllContactSubmissionsFlow(): Flow<List<ContactSubmission>>
}
