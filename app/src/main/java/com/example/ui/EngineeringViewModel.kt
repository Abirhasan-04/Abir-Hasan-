package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class EngineeringViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = EngineeringRepository(database.engineeringDao())

    // All database flows
    val allProjects = repository.projectsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    val allBlogs = repository.blogsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    val contactSubmissions = repository.contactSubmissionsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Search and Filters
    private val _projectSearchQuery = MutableStateFlow("")
    val projectSearchQuery = _projectSearchQuery.asStateFlow()

    private val _selectedProjectCategory = MutableStateFlow("All")
    val selectedProjectCategory = _selectedProjectCategory.asStateFlow()

    private val _blogSearchQuery = MutableStateFlow("")
    val blogSearchQuery = _blogSearchQuery.asStateFlow()

    private val _selectedBlogCategory = MutableStateFlow("All")
    val selectedBlogCategory = _selectedBlogCategory.asStateFlow()

    // Filtered lists
    val filteredProjects = combine(allProjects, projectSearchQuery, selectedProjectCategory) { projects, query, cat ->
        projects.filter { project ->
            val matchesQuery = project.title.contains(query, ignoreCase = true) || 
                               project.headline.contains(query, ignoreCase = true) ||
                               project.location.contains(query, ignoreCase = true)
            val matchesCategory = cat == "All" || project.category.equals(cat, ignoreCase = true)
            matchesQuery && matchesCategory
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredBlogs = combine(allBlogs, blogSearchQuery, selectedBlogCategory) { blogs, query, cat ->
        blogs.filter { blog ->
            val matchesQuery = blog.title.contains(query, ignoreCase = true) || 
                               blog.summary.contains(query, ignoreCase = true) ||
                               blog.content.contains(query, ignoreCase = true)
            val matchesCategory = cat == "All" || blog.category.equals(cat, ignoreCase = true)
            matchesQuery && matchesCategory
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active detail states
    private val _selectedProjectId = MutableStateFlow<String?>(null)
    val selectedProjectId = _selectedProjectId.asStateFlow()

    val selectedProject = selectedProjectId.flatMapLatest { id ->
        if (id == null) flowOf(null)
        else allProjects.map { list -> list.find { it.id == id } }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _selectedBlogId = MutableStateFlow<String?>(null)
    val selectedBlogId = _selectedBlogId.asStateFlow()

    val selectedBlog = selectedBlogId.flatMapLatest { id ->
        if (id == null) flowOf(null)
        else allBlogs.map { list -> list.find { it.id == id } }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Form submission states
    private val _newsletterState = MutableStateFlow<FormState>(FormState.Idle)
    val newsletterState = _newsletterState.asStateFlow()

    private val _contactState = MutableStateFlow<FormState>(FormState.Idle)
    val contactState = _contactState.asStateFlow()

    init {
        // Initialize database with premium technical assets
        viewModelScope.launch {
            repository.prepopulateDatabaseIfEmpty()
        }
    }

    // Set filters
    fun setProjectSearchQuery(query: String) {
        _projectSearchQuery.value = query
    }

    fun setProjectCategory(category: String) {
        _selectedProjectCategory.value = category
    }

    fun setBlogSearchQuery(query: String) {
        _blogSearchQuery.value = query
    }

    fun setBlogCategory(category: String) {
        _selectedBlogCategory.value = category
    }

    // Select items
    fun selectProject(id: String?) {
        _selectedProjectId.value = id
    }

    fun selectBlog(id: String?) {
        _selectedBlogId.value = id
    }

    // Bookmarking
    fun toggleProjectBookmark(project: ProjectEntity) {
        viewModelScope.launch {
            repository.toggleProjectBookmark(project.id, project.isBookmarked)
        }
    }

    fun toggleBlogPostBookmark(blog: BlogEntity) {
        viewModelScope.launch {
            repository.toggleBlogPostBookmark(blog.id, blog.isBookmarked)
        }
    }

    // Form actions
    fun subscribeNewsletter(email: String) {
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _newsletterState.value = FormState.Error("Please enter a valid professional email address.")
            return
        }

        _newsletterState.value = FormState.Loading
        viewModelScope.launch {
            val success = repository.subscribeNewsletter(email)
            if (success) {
                _newsletterState.value = FormState.Success("Thank you! You have successfully subscribed to the newsletter.")
            } else {
                _newsletterState.value = FormState.Error("This email is already subscribed to our engineering briefs.")
            }
        }
    }

    fun resetNewsletterState() {
        _newsletterState.value = FormState.Idle
    }

    fun submitContactForm(name: String, email: String, subject: String, message: String) {
        if (name.isBlank() || email.isBlank() || subject.isBlank() || message.isBlank()) {
            _contactState.value = FormState.Error("All fields are mandatory. Please fill in the missing details.")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _contactState.value = FormState.Error("Please enter a valid professional email address.")
            return
        }

        _contactState.value = FormState.Loading
        viewModelScope.launch {
            repository.submitContactForm(name, email, subject, message)
            _contactState.value = FormState.Success("Your consultation request has been logged successfully. We will get back to you within 24 hours.")
        }
    }

    fun resetContactState() {
        _contactState.value = FormState.Idle
    }
}

sealed interface FormState {
    object Idle : FormState
    object Loading : FormState
    data class Success(val message: String) : FormState
    data class Error(val message: String) : FormState
}

class EngineeringViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EngineeringViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EngineeringViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
