package com.example.ui

import android.app.Application
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.R
import com.example.data.BlogEntity
import com.example.data.ContactSubmission
import com.example.data.ProjectEntity
import com.example.ui.theme.*

@Composable
fun MainLayout(
    viewModel: EngineeringViewModel = viewModel(
        factory = EngineeringViewModelFactory(LocalContext.current.applicationContext as Application)
    )
) {
    var currentScreen by remember { mutableStateOf("home") }
    val selectedProjectId by viewModel.selectedProjectId.collectAsStateWithLifecycle()
    val selectedBlogId by viewModel.selectedBlogId.collectAsStateWithLifecycle()

    val selectedProject by viewModel.selectedProject.collectAsStateWithLifecycle()
    val selectedBlog by viewModel.selectedBlog.collectAsStateWithLifecycle()

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val width = maxWidth
        val isWideScreen = width > 600.dp

        Row(modifier = Modifier.fillMaxSize()) {
            // Adaptive Navigation Component (NavigationRail for wide screens, bottom bar for compact)
            if (isWideScreen) {
                NavigationRail(
                    containerColor = MaterialTheme.colorScheme.background,
                    header = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(vertical = 16.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_app_icon),
                                contentDescription = "App Icon",
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "CivicBuild",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    modifier = Modifier.testTag("wide_nav_rail")
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    
                    NavigationRailItem(
                        selected = currentScreen == "home" && selectedProjectId == null && selectedBlogId == null,
                        onClick = {
                            currentScreen = "home"
                            viewModel.selectProject(null)
                            viewModel.selectBlog(null)
                        },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home") },
                        modifier = Modifier.testTag("nav_rail_home")
                    )
                    NavigationRailItem(
                        selected = currentScreen == "portfolio" || selectedProjectId != null,
                        onClick = {
                            currentScreen = "portfolio"
                            viewModel.selectProject(null)
                            viewModel.selectBlog(null)
                        },
                        icon = { Icon(Icons.Default.Construction, contentDescription = "Portfolio") },
                        label = { Text("Portfolio") },
                        modifier = Modifier.testTag("nav_rail_portfolio")
                    )
                    NavigationRailItem(
                        selected = currentScreen == "blog" || selectedBlogId != null,
                        onClick = {
                            currentScreen = "blog"
                            viewModel.selectProject(null)
                            viewModel.selectBlog(null)
                        },
                        icon = { Icon(Icons.AutoMirrored.Filled.Article, contentDescription = "Blog") },
                        label = { Text("Blog") },
                        modifier = Modifier.testTag("nav_rail_blog")
                    )
                    NavigationRailItem(
                        selected = currentScreen == "contact",
                        onClick = {
                            currentScreen = "contact"
                            viewModel.selectProject(null)
                            viewModel.selectBlog(null)
                        },
                        icon = { Icon(Icons.Default.Email, contentDescription = "Contact") },
                        label = { Text("Contact") },
                        modifier = Modifier.testTag("nav_rail_contact")
                    )
                    NavigationRailItem(
                        selected = currentScreen == "bookmarks",
                        onClick = {
                            currentScreen = "bookmarks"
                            viewModel.selectProject(null)
                            viewModel.selectBlog(null)
                        },
                        icon = { Icon(Icons.Default.Bookmark, contentDescription = "Saved") },
                        label = { Text("Saved") },
                        modifier = Modifier.testTag("nav_rail_bookmarks")
                    )
                    
                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            // Main Content Container
            Scaffold(
                bottomBar = {
                    if (!isWideScreen) {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.background,
                            modifier = Modifier.testTag("compact_bottom_nav")
                        ) {
                            NavigationBarItem(
                                selected = currentScreen == "home" && selectedProjectId == null && selectedBlogId == null,
                                onClick = {
                                    currentScreen = "home"
                                    viewModel.selectProject(null)
                                    viewModel.selectBlog(null)
                                },
                                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                                label = { Text("Home", fontSize = 11.sp) },
                                modifier = Modifier.testTag("nav_home")
                            )
                            NavigationBarItem(
                                selected = currentScreen == "portfolio" || selectedProjectId != null,
                                onClick = {
                                    currentScreen = "portfolio"
                                    viewModel.selectProject(null)
                                    viewModel.selectBlog(null)
                                },
                                icon = { Icon(Icons.Default.Construction, contentDescription = "Portfolio") },
                                label = { Text("Portfolio", fontSize = 11.sp) },
                                modifier = Modifier.testTag("nav_portfolio")
                            )
                            NavigationBarItem(
                                selected = currentScreen == "blog" || selectedBlogId != null,
                                onClick = {
                                    currentScreen = "blog"
                                    viewModel.selectProject(null)
                                    viewModel.selectBlog(null)
                                },
                                icon = { Icon(Icons.AutoMirrored.Filled.Article, contentDescription = "Blog") },
                                label = { Text("Blog", fontSize = 11.sp) },
                                modifier = Modifier.testTag("nav_blog")
                            )
                            NavigationBarItem(
                                selected = currentScreen == "contact",
                                onClick = {
                                    currentScreen = "contact"
                                    viewModel.selectProject(null)
                                    viewModel.selectBlog(null)
                                },
                                icon = { Icon(Icons.Default.Email, contentDescription = "Contact") },
                                label = { Text("Contact", fontSize = 11.sp) },
                                modifier = Modifier.testTag("nav_contact")
                            )
                            NavigationBarItem(
                                selected = currentScreen == "bookmarks",
                                onClick = {
                                    currentScreen = "bookmarks"
                                    viewModel.selectProject(null)
                                    viewModel.selectBlog(null)
                                },
                                icon = { Icon(Icons.Default.Bookmark, contentDescription = "Saved") },
                                label = { Text("Saved", fontSize = 11.sp) },
                                modifier = Modifier.testTag("nav_bookmarks")
                            )
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    // Screen routing logic with animations
                    AnimatedContent(
                        targetState = ScreenState(
                            screen = currentScreen,
                            projectId = selectedProjectId,
                            blogId = selectedBlogId
                        ),
                        transitionSpec = {
                            fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(200))
                        },
                        label = "screen_transitions"
                    ) { state ->
                        when {
                            state.projectId != null -> {
                                selectedProject?.let { project ->
                                    ProjectDetailScreen(
                                        project = project,
                                        onBack = { viewModel.selectProject(null) },
                                        onBookmarkToggle = { viewModel.toggleProjectBookmark(project) }
                                    )
                                } ?: LoadingState()
                            }
                            state.blogId != null -> {
                                selectedBlog?.let { blog ->
                                    BlogDetailScreen(
                                        blog = blog,
                                        onBack = { viewModel.selectBlog(null) },
                                        onBookmarkToggle = { viewModel.toggleBlogPostBookmark(blog) }
                                    )
                                } ?: LoadingState()
                            }
                            state.screen == "home" -> {
                                HomeScreen(
                                    onExploreProjects = { currentScreen = "portfolio" },
                                    onReadBlog = { blogId -> viewModel.selectBlog(blogId) },
                                    onBookConsultation = { currentScreen = "contact" },
                                    viewModel = viewModel
                                )
                            }
                            state.screen == "portfolio" -> {
                                PortfolioScreen(
                                    viewModel = viewModel,
                                    onProjectClick = { projectId -> viewModel.selectProject(projectId) }
                                )
                            }
                            state.screen == "blog" -> {
                                BlogScreen(
                                    viewModel = viewModel,
                                    onBlogClick = { blogId -> viewModel.selectBlog(blogId) }
                                )
                            }
                            state.screen == "contact" -> {
                                ContactScreen(viewModel = viewModel)
                            }
                            state.screen == "bookmarks" -> {
                                BookmarksScreen(
                                    viewModel = viewModel,
                                    onProjectClick = { projectId -> viewModel.selectProject(projectId) },
                                    onBlogClick = { blogId -> viewModel.selectBlog(blogId) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Data class to wrap our navigation composite state
data class ScreenState(
    val screen: String,
    val projectId: String?,
    val blogId: String?
)

@Composable
fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

// ==========================================
// HOME SCREEN
// ==========================================
@Composable
fun HomeScreen(
    onExploreProjects: () -> Unit,
    onReadBlog: (String) -> Unit,
    onBookConsultation: () -> Unit,
    viewModel: EngineeringViewModel
) {
    val scrollState = rememberScrollState()
    val projects by viewModel.allProjects.collectAsStateWithLifecycle()
    val blogs by viewModel.allBlogs.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Hero Section with wireframe overlay banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_hero_banner),
                contentDescription = "Engineering Blueprint Hero",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // Premium Dark Gradient overlay for Apple-like luxurious aesthetic
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0x990F172A),
                                Color(0xFF0F172A)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                // System Real-time UTC badge
                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.9f),
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Speed,
                            contentDescription = "Live",
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "LIVE SYSTEM • 2026 UTC-7",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontSize = 10.sp
                        )
                    }
                }

                Text(
                    text = "Shaping Resilient Infrastructure",
                    style = MaterialTheme.typography.displayMedium.copy(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(0f, 2f),
                            blurRadius = 4f
                        )
                    ),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Principal Structural Engineering Consultant. Solving complex geopolitical, geotechnical, and structural dynamics since 2011.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.9f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = onExploreProjects,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("hero_portfolio_button")
                    ) {
                        Text("Explore Portfolio", fontWeight = FontWeight.Bold)
                    }
                    OutlinedButton(
                        onClick = onBookConsultation,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                        border = BorderStroke(1.5.dp, Color.White),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("hero_consultation_button")
                    ) {
                        Text("Consultation Form", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        // Statistics Row (Apple/Stripe Style Minimal Cards)
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "CAREER SCALE & METRICS",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val stats = listOf(
                "$1.2B+" to "CapEx Advised",
                "15+" to "Yrs Leadership",
                "12" to "Mega Bridges",
                "0.0%" to "Defect Rate"
            )
            stats.forEach { (value, label) ->
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = value,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        // Services Preview Cards
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "CORE ENGINEERING DISCIPLINES",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            val services = listOf(
                Triple("Structural Dynamics", "Advanced finite element analysis (FEA) modeling, seismic damping mechanisms, and extreme wind-shear mitigation on high-rise structures.", Icons.Default.Construction),
                Triple("Geotechnical Systems", "Slab-compensated foundations over high-plasticity expansive clay, deep piles to bedded stone, and slurry excavation retaining structures.", Icons.Default.Info),
                Triple("Sub-surface Assets", "Tunnel boring diagnostics, steel fiber-reinforced segmented liners, and active groundwater freezing diversion frameworks.", Icons.Default.Speed),
                Triple("Infrastructure Audits", "Non-destructive testing (NDT), automated ultrasonic crack inspections, and fiber-optic telemetry installation.", Icons.Default.CheckCircle)
            )
            items(services) { (title, desc, icon) ->
                Card(
                    modifier = Modifier
                        .width(280.dp)
                        .height(180.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            maxLines = 5,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        // Engineering Process Timeline (High fidelity interactive list)
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "ENGINEERING DESIGN PROCESS",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                val steps = listOf(
                    "01. Feasibility & Geotechnical Site Survey" to "Rigorous soil boring investigations, seismic site classification, and material suitability testing to identify baseline risks.",
                    "02. Computational Modeling (FEA & FEM)" to "Developing deep 3D structural models under extreme static, dynamic wind, and spectral earthquake loading conditions.",
                    "03. Advanced Materials Specifications" to "Compounding high-performance geopolymer concretes, high-ductility steel reinforcement configurations, and carbon-negative options.",
                    "04. Structural Integrity Site Superintending" to "Active field engineering, heavy lift crane safety review, concrete slump monitoring, and fiber-optic sensor system integration."
                )
                steps.forEachIndexed { index, (stepTitle, stepDesc) ->
                    Row(modifier = Modifier.padding(bottom = if (index < steps.size - 1) 16.dp else 0.dp)) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = (index + 1).toString(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            if (index < steps.size - 1) {
                                Box(
                                    modifier = Modifier
                                        .width(2.dp)
                                        .height(50.dp)
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = stepTitle,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = stepDesc,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        }

        // Featured Portfolio Projects Slider
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "FEATURED MILESTONES",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            TextButton(onClick = onExploreProjects) {
                Text("View All Portfolio", fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(projects.take(2)) { project ->
                FeaturedProjectCard(project = project, onClick = { onExploreProjects() })
            }
        }

        // Latest Blog Articles Section
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "LATEST SCIENTIFIC BRIEFS",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            blogs.take(2).forEach { blog ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onReadBlog(blog.id) }
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                            ) {
                                Text(
                                    text = blog.category.uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = blog.readTime,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = blog.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = blog.summary,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack, // Will mirror/rotate for right chevron indicator
                        contentDescription = "Read",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(20.dp)
                            .alpha(0.7f)
                            .drawBehind {
                                // Rotate 180 degrees conceptually
                            }
                    )
                }
            }
        }

        // Experience Timeline Section (Professional Milestones)
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "CAREER EXPERIENCE TIMELINE",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                val experience = listOf(
                    Triple("Principal Structural Engineer", "Global Infrastructure Advisors (2021 - Present)", "Overseeing the technical feasibility, wind tunnel analyses, and foundation criteria for major bridges, subways, and multi-tier interchanges valued over $600 Million."),
                    Triple("Senior Bridge Designer", "Vance & Associate Consultants (2015 - 2021)", "Lead designer on various post-tensioned precast segmental box girder and cable-stayed structures. Implemented IoT telemetry monitoring models."),
                    Triple("Structural Engineering Specialist", "Bureau of Structural Safety (2011 - 2015)", "Conducted complex finite element modeling, seismic retrofitting designs, and post-disaster infrastructure health inspections.")
                )
                experience.forEachIndexed { index, (role, tenure, details) ->
                    Column(modifier = Modifier.padding(bottom = if (index < experience.size - 1) 16.dp else 0.dp)) {
                        Text(
                            text = role,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = tenure,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = details,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }

        // Testimonial Swiper
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "CLIENT & TRUSTED AUDITS TESTIMONIALS",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "“We hired them to review the structural health models for our cable bridge. Their integration of fiber-optic sensor feedback combined with real-time finite element calibration saved us weeks of inspection labor and gave our municipal bond underwriters absolute confidence.”",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("HW", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Harlan Ward, P.E.",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Director of Engineering, Metro Transit District",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Professional newsletter
        Spacer(modifier = Modifier.height(32.dp))
        NewsletterSection(viewModel = viewModel)
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
fun FeaturedProjectCard(project: ProjectEntity, onClick: () -> Unit) {
    val imageRes = when (project.imageUrl) {
        "img_project_bridge" -> R.drawable.img_project_bridge
        "img_project_tower" -> R.drawable.img_project_tower
        else -> R.drawable.img_project_tunnel
    }
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(240.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = project.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0x66000000),
                                Color(0xCC000000)
                            )
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.secondary
                ) {
                    Text(
                        text = project.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = project.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = project.location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

private data class TechnicalBrief(
    val id: String,
    val issue: String,
    val title: String,
    val date: String,
    val introduction: String,
    val formula: String,
    val detail: String
)

@Composable
fun NewsletterSection(viewModel: EngineeringViewModel) {
    var email by remember { mutableStateOf("") }
    val state by viewModel.newsletterState.collectAsStateWithLifecycle()
    var selectedBrief by remember { mutableStateOf<TechnicalBrief?>(null) }

    val briefs = remember {
        listOf(
            TechnicalBrief(
                id = "tb1",
                issue = "Quarterly Brief #42",
                title = "Wind Sways & Viscous Fluid Dampers in 150m+ Towers",
                date = "June 2026",
                introduction = "Lateral loads from high-velocity wind gusts pose severe serviceability and comfort issues for tall, slender skyscrapers. Dampers act as passive energy dissipation systems to absorb kinetic shock.",
                formula = "Fd = C * sgn(v) * |v|^α\nWhere:\n• Fd = Damping force\n• C = Damping coefficient\n• v = Relative velocity\n• α = Velocity exponent (0.3 to 1.0)",
                detail = "Wind tunnel tests are mandatory to calibrate these forces. This brief details the placement of viscous fluid dampers at the pylon-deck interfaces to reach a critical damping ratio (ξ > 0.05) under peak 100-year return period wind conditions, reducing building acceleration by up to 35%."
            ),
            TechnicalBrief(
                id = "tb2",
                issue = "Quarterly Brief #41",
                title = "Geopolymer Concrete: Ultra-low Carbon Slag Mix Design",
                date = "March 2026",
                introduction = "Ordinary Portland Cement (OPC) is responsible for approximately 8% of global CO2. Geopolymer concrete replaces OPC entirely with slag/fly-ash activated by strong alkaline solutions.",
                formula = "Mn[-(Si-O-Al-O)-]n * wH2O\nWhere:\n• Mn = Alkaline cation\n• Si-O-Al-O = Aluminosilicate chain\n• wH2O = Water of reaction",
                detail = "This polymerization reaction yields an amorphous 3D aluminosilicate framework. This results in high early compressive strength (up to 45 MPa in 24 hours), extreme resistance to acidic soils, and an incredible 80% reduction in net carbon emissions compared to traditional OPC mixes."
            ),
            TechnicalBrief(
                id = "tb3",
                issue = "Quarterly Brief #40",
                title = "Distributed Fiber-optic Telemetry on Precast segmental girders",
                date = "December 2025",
                introduction = "Traditional strain gauges are localized and prone to temporal drift. Distributed fiber-optic sensors utilize optical frequency domain reflectometry (OFDR) for continuous millimeter-scale strain readings.",
                formula = "ΔνB = CT * ΔT + Cϵ * Δϵ\nWhere:\n• ΔνB = Brillouin frequency shift\n• CT, Cϵ = Temp/Strain coefficients\n• ΔT, Δϵ = Temp/Strain variations",
                detail = "By embedding continuous fiber loops directly inside the post-tensioning duct systems of segmental concrete box girders, engineers can map dynamic strain and deformation levels down to 1 microstrain, allowing active predictive fatigue modeling and instant anomaly localization."
            )
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "SUBSCRIBE TO INFRASTRUCTURE BRIEFS",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Receive technical insights, structural calculation summaries, and research papers directly to your inbox quarterly.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
            )
            Spacer(modifier = Modifier.height(16.dp))

            when (state) {
                is FormState.Success -> {
                    Text(
                        text = (state as FormState.Success).message,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedButton(
                        onClick = { viewModel.resetNewsletterState(); email = "" },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                        border = BorderStroke(1.5.dp, Color.White),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Reset Form")
                    }
                }
                else -> {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("email@professional.com", color = Color.LightGray) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.6f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("newsletter_email_input"),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { viewModel.subscribeNewsletter(email) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("newsletter_subscribe_button"),
                        enabled = state != FormState.Loading
                    ) {
                        if (state == FormState.Loading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onSecondary)
                        } else {
                            Text("Subscribe to Briefs", color = MaterialTheme.colorScheme.onSecondary, fontWeight = FontWeight.Bold)
                        }
                    }
                    if (state is FormState.Error) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = (state as FormState.Error).message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 20.dp),
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
            )

            Text(
                text = "EXPLORE RECENT QUARTERLY ARCHIVE",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                briefs.forEach { brief ->
                    Surface(
                        onClick = { selectedBrief = brief },
                        color = Color.White.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("newsletter_brief_row_${brief.id}")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = brief.date.uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = brief.title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = brief.issue,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Read Technical Brief",
                                tint = Color.White.copy(alpha = 0.8f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    if (selectedBrief != null) {
        val brief = selectedBrief!!
        AlertDialog(
            onDismissRequest = { selectedBrief = null },
            title = {
                Column {
                    Text(
                        text = brief.issue.uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = brief.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Published: ${brief.date}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = brief.introduction,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Formula",
                                    modifier = Modifier.size(16.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "TECHNICAL SPECIFICATION & FORMULA",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = brief.formula,
                                style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = brief.detail,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 20.sp
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { selectedBrief = null },
                    modifier = Modifier.testTag("brief_dialog_close")
                ) {
                    Text("Close", fontWeight = FontWeight.Bold)
                }
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.testTag("brief_dialog")
        )
    }
}


// ==========================================
// PORTFOLIO SCREEN
// ==========================================
@Composable
fun PortfolioScreen(
    viewModel: EngineeringViewModel,
    onProjectClick: (String) -> Unit
) {
    val searchQuery by viewModel.projectSearchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedProjectCategory.collectAsStateWithLifecycle()
    val filteredProjects by viewModel.filteredProjects.collectAsStateWithLifecycle()

    val categories = listOf("All", "Bridges", "Buildings", "Sub-surface")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "ENGINEERING PORTFOLIO",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "A complete repository of landmark infrastructure projects engineered to withstanding dynamic earth, wind, and thermal strains.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setProjectSearchQuery(it) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                placeholder = { Text("Search by title, location, specs...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("portfolio_search_input"),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Category Filter Scrollable Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = category == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.setProjectCategory(category) },
                        label = { Text(category) },
                        modifier = Modifier.testTag("chip_portfolio_$category")
                    )
                }
            }
        }

        // Projects Grid/List with empty state checking
        if (filteredProjects.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Construction,
                        contentDescription = "Empty",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No matching projects found",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Text(
                        text = "Try modifying your search or filters.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredProjects) { project ->
                    PortfolioProjectListItem(project = project, onClick = { onProjectClick(project.id) })
                }
            }
        }
    }
}

@Composable
fun PortfolioProjectListItem(project: ProjectEntity, onClick: () -> Unit) {
    val imageRes = when (project.imageUrl) {
        "img_project_bridge" -> R.drawable.img_project_bridge
        "img_project_tower" -> R.drawable.img_project_tower
        else -> R.drawable.img_project_tunnel
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("project_card_${project.id}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = project.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0x66000000),
                                Color(0xCC000000)
                            )
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = MaterialTheme.colorScheme.secondary
                            ) {
                                Text(
                                    text = project.category.uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = if (project.status == "Completed") Color(0xFF10B981) else Color(0xFFF59E0B)
                            ) {
                                Text(
                                    text = project.status.uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = project.title,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = project.headline,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.85f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Text(
                            text = "BUDGET",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = project.budget,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}


// ==========================================
// PROJECT DETAIL SCREEN
// ==========================================
@Composable
fun ProjectDetailScreen(
    project: ProjectEntity,
    onBack: () -> Unit,
    onBookmarkToggle: () -> Unit
) {
    val scrollState = rememberScrollState()
    val imageRes = when (project.imageUrl) {
        "img_project_bridge" -> R.drawable.img_project_bridge
        "img_project_tower" -> R.drawable.img_project_tower
        else -> R.drawable.img_project_tunnel
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Detailed Custom Action Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("project_back_button")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Row {
                IconButton(
                    onClick = onBookmarkToggle,
                    modifier = Modifier.testTag("project_bookmark_toggle")
                ) {
                    Icon(
                        imageVector = if (project.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (project.isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        // Project Hero Image & Headline
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = project.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0x66000000), Color(0xFF0F172A))
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.secondary
                ) {
                    Text(
                        text = project.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = project.title,
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${project.location} • Budget: ${project.budget}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
        }

        // Detailed specifications table
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "TECHNICAL SPECIFICATIONS",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    project.technicalSpecs.split("\n").forEach { specLine ->
                        if (specLine.isNotBlank()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                val parts = specLine.replace("•", "").trim().split(":")
                                if (parts.size >= 2) {
                                    Text(
                                        text = parts[0].trim(),
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = parts.subList(1, parts.size).joinToString(":").trim(),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                                    )
                                } else {
                                    Text(text = specLine, style = MaterialTheme.typography.bodyLarge)
                                }
                            }
                            HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                        }
                    }
                }
            }

            // Project Overview description
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "PROJECT OVERVIEW",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = project.overview,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f)
            )

            // Dynamic challenges card block
            Spacer(modifier = Modifier.height(24.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = "Challenge",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "PRIMARY STRUCTURAL CHALLENGE",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = project.challenges,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            // Solutions card block
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "Solution",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "APPLIED ENGINEERING SOLUTION",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = project.solutions,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            // Lessons learned card block
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "LESSONS LEARNED & IMPLICATIONS",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = project.lessonsLearned,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f)
            )

            // Timeline progresses
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "CONSTRUCTION TIMELINE PROGRESS",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = project.timeline,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


// ==========================================
// BLOG SCREEN
// ==========================================
@Composable
fun BlogScreen(
    viewModel: EngineeringViewModel,
    onBlogClick: (String) -> Unit
) {
    val searchQuery by viewModel.blogSearchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedBlogCategory.collectAsStateWithLifecycle()
    val filteredBlogs by viewModel.filteredBlogs.collectAsStateWithLifecycle()

    val categories = listOf("All", "Structural", "Materials", "Infrastructure")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "ENGINEERING INSIGHTS",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "A modern scientific tech blog detailing numerical models, dynamic calculation reviews, and material science developments.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Search input bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setBlogSearchQuery(it) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                placeholder = { Text("Search by keywords, equations, authors...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("blog_search_input"),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Category scrolling chip filters
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = category == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.setBlogCategory(category) },
                        label = { Text(category) },
                        modifier = Modifier.testTag("chip_blog_$category")
                    )
                }
            }
        }

        // Blog list or empty screen state
        if (filteredBlogs.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.AutoMirrored.Filled.Article,
                        contentDescription = "Empty",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No matching research posts",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredBlogs) { blog ->
                    BlogListItemCard(blog = blog, onClick = { onBlogClick(blog.id) })
                }
            }
        }
    }
}

@Composable
fun BlogListItemCard(blog: BlogEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("blog_card_${blog.id}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = blog.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "${blog.date} • ${blog.readTime}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = blog.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = blog.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = blog.authorName.split(" ").getOrNull(1)?.take(1) ?: "A",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = blog.authorName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = blog.authorTitle,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}


// ==========================================
// BLOG DETAIL SCREEN
// ==========================================
@Composable
fun BlogDetailScreen(
    blog: BlogEntity,
    onBack: () -> Unit,
    onBookmarkToggle: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Linear-style Reading progress bar at the top!
        val progress = if (scrollState.maxValue > 0) {
            scrollState.value.toFloat() / scrollState.maxValue.toFloat()
        } else 0f

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

        // Custom action bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("blog_back_button")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Row {
                val context = LocalContext.current
                IconButton(
                    onClick = {
                        val clipboardManager = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                        val clipData = android.content.ClipData.newPlainText("Article Link", "https://civicbuild.engineering/insights/${blog.id}")
                        clipboardManager.setPrimaryClip(clipData)
                        android.widget.Toast.makeText(context, "Copied!", android.widget.Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.testTag("blog_copy_link_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Copy Link",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(
                    onClick = onBookmarkToggle,
                    modifier = Modifier.testTag("blog_bookmark_toggle")
                ) {
                    Icon(
                        imageVector = if (blog.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (blog.isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        // Article body scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
        ) {
            // Category, Date & Read Time
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = blog.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "${blog.date} • ${blog.readTime}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Article title
            Text(
                text = blog.title,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = 36.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Author Box
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = blog.authorName.split(" ").getOrNull(1)?.take(1) ?: "A",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = blog.authorName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = blog.authorTitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Render rich content with custom sections, blocks, headings
            blog.content.split("\n\n").forEach { paragraph ->
                if (paragraph.startsWith("###")) {
                    // Render Heading
                    Text(
                        text = paragraph.replace("###", "").trim(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                } else if (paragraph.startsWith("1.") || paragraph.startsWith("-")) {
                    // Render Bullet lists
                    Column(modifier = Modifier.padding(bottom = 12.dp)) {
                        paragraph.split("\n").forEach { bulletLine ->
                            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                                Text(
                                    text = "•",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = bulletLine.replace("-", "").replace("•", "").trim(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    lineHeight = 24.sp,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f)
                                )
                            }
                        }
                    }
                } else {
                    // Standard Body Paragraph
                    Text(
                        text = paragraph,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 24.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }

            // Article Tag list
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                blog.tags.split(",").forEach { tag ->
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = "#${tag.trim()}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Article Share Actions Card
            Spacer(modifier = Modifier.height(24.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                val context = LocalContext.current
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Share this insight",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Spread engineering knowledge with your professional network.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            val clipboardManager = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                            val clipData = android.content.ClipData.newPlainText("Article Link", "https://civicbuild.engineering/insights/${blog.id}")
                            clipboardManager.setPrimaryClip(clipData)
                            android.widget.Toast.makeText(context, "Copied!", android.widget.Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = Modifier.testTag("blog_bottom_copy_link_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Copy Link",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Copy Link",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}


// ==========================================
// CONTACT / FAQ SCREEN
// ==========================================
@Composable
fun ContactScreen(viewModel: EngineeringViewModel) {
    val scrollState = rememberScrollState()
    val submissions by viewModel.contactSubmissions.collectAsStateWithLifecycle()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val contactState by viewModel.contactState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "CONSULTATION & INQUIRY",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Request a structural feasibility audit, bid-underwriting calculation review, or general consultation.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Glassmorphic interactive form card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "SUBMIT CONSULTATION BRIEF",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    when (contactState) {
                        is FormState.Success -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                            ) {
                                Icon(
                                    Icons.Default.CheckCircle,
                                    contentDescription = "Success",
                                    tint = Color(0xFF10B981),
                                    modifier = Modifier.size(64.dp)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = (contactState as FormState.Success).message,
                                    style = MaterialTheme.typography.titleMedium,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = {
                                        viewModel.resetContactState()
                                        name = ""
                                        email = ""
                                        subject = ""
                                        message = ""
                                    },
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("Submit New Brief")
                                }
                            }
                        }
                        else -> {
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Your Full Name") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("contact_name_input"),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                label = { Text("Professional Email") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("contact_email_input"),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = subject,
                                onValueChange = { subject = it },
                                label = { Text("Subject of Inquiry") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("contact_subject_input"),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = message,
                                onValueChange = { message = it },
                                label = { Text("Structural / Project Details") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .testTag("contact_message_input"),
                                maxLines = 5
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { viewModel.submitContactForm(name, email, subject, message) },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("contact_submit_button"),
                                enabled = contactState != FormState.Loading
                            ) {
                                if (contactState == FormState.Loading) {
                                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                                } else {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Log Consultation Brief", fontWeight = FontWeight.Bold)
                                    }
                                }
                            }

                            if (contactState is FormState.Error) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = (contactState as FormState.Error).message,
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            // Local SQL Draft submission display (Demonstrates Room database storage!)
            if (submissions.isNotEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "LOGGED CONSULTATION SUBMISSIONS (ROOM DB)",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                submissions.forEach { submission ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "To: ${submission.name}",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Text(
                                    text = submission.email,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                            Text(
                                text = "Sub: ${submission.subject}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                            Text(
                                text = submission.message,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

            // Expanded Technical FAQ Accordions
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "TECHNICAL FREQUENT QUESTIONS",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            val faqs = listOf(
                "What is the average timeline for a structural review?" to "Preliminary reviews under FEA modeling require roughly 5-10 business days depending on structural geometry. Detailed seismic site calibration requires soil borehole reports.",
                "Do you assist in public municipal bond underwriting?" to "Yes, we regularly deliver independent, certified third-party technical feasibility and structural durability reports required for capital markets and underwriting projects.",
                "Which software applications are utilized for dynamic calculations?" to "All computational reviews are conducted using industry standard packages including SAP2000, ETABS, and custom finite element scripting interfaces.",
                "What forms of environmental compliance are evaluated?" to "We ensure structural designs align perfectly with ISO 14001 carbon guidelines, certifying slag and flyash compound ratios to meet Platinum LEED pre-specifications."
            )

            faqs.forEach { (question, answer) ->
                FaqAccordionItem(question = question, answer = answer)
            }
        }
    }
}

@Composable
fun FaqAccordionItem(question: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { expanded = !expanded }
            .testTag("faq_item_${question.take(15)}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = question,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = answer,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    )
                }
            }
        }
    }
}


// ==========================================
// BOOKMARKS (SAVED) SCREEN
// ==========================================
@Composable
fun BookmarksScreen(
    viewModel: EngineeringViewModel,
    onProjectClick: (String) -> Unit,
    onBlogClick: (String) -> Unit
) {
    val projects by viewModel.allProjects.collectAsStateWithLifecycle()
    val blogs by viewModel.allBlogs.collectAsStateWithLifecycle()

    val bookmarkedProjects = remember(projects) { projects.filter { it.isBookmarked } }
    val bookmarkedBlogs = remember(blogs) { blogs.filter { it.isBookmarked } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "SAVED BRIEFS & PORTFOLIO",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "A local off-line dashboard showing projects and technical blog posts you saved for reference.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Bookmarked Projects Section
            Text(
                text = "SAVED INFRASTRUCTURE PROJECTS",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            if (bookmarkedProjects.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No saved projects yet. Tap the bookmark icon in any project's detailed page to save.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    bookmarkedProjects.forEach { project ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onProjectClick(project.id) },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Construction,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = project.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = project.location,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                                Icon(Icons.Default.Bookmark, contentDescription = "Saved", tint = MaterialTheme.colorScheme.secondary)
                            }
                        }
                    }
                }
            }

            // Bookmarked Blogs Section
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "SAVED TECHNICAL BRIEFS",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            if (bookmarkedBlogs.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No saved briefs yet. Tap the bookmark icon in any article's detailed page to save.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    bookmarkedBlogs.forEach { blog ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onBlogClick(blog.id) },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.Article,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = blog.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${blog.category} • ${blog.readTime}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                                Icon(Icons.Default.Bookmark, contentDescription = "Saved", tint = MaterialTheme.colorScheme.secondary)
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
