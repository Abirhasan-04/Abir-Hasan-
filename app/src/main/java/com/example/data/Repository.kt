package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class EngineeringRepository(private val dao: EngineeringDao) {

    val projectsFlow: Flow<List<ProjectEntity>> = dao.getAllProjectsFlow()
    val blogsFlow: Flow<List<BlogEntity>> = dao.getAllBlogPostsFlow()
    val contactSubmissionsFlow: Flow<List<ContactSubmission>> = dao.getAllContactSubmissionsFlow()

    suspend fun getProjectById(id: String): ProjectEntity? {
        return dao.getProjectById(id)
    }

    suspend fun getBlogPostById(id: String): BlogEntity? {
        return dao.getBlogPostById(id)
    }

    suspend fun toggleProjectBookmark(id: String, currentVal: Boolean) {
        dao.updateProjectBookmark(id, !currentVal)
    }

    suspend fun toggleBlogPostBookmark(id: String, currentVal: Boolean) {
        dao.updateBlogPostBookmark(id, !currentVal)
    }

    suspend fun subscribeNewsletter(email: String): Boolean {
        val existingCount = dao.getSubscriberCount(email)
        if (existingCount > 0) return false
        dao.insertSubscriber(NewsletterEntity(email = email))
        return true
    }

    suspend fun submitContactForm(name: String, email: String, subject: String, message: String) {
        dao.insertContactSubmission(
            ContactSubmission(name = name, email = email, subject = subject, message = message)
        )
    }

    suspend fun prepopulateDatabaseIfEmpty() {
        val currentProjects = dao.getAllProjectsFlow().first()
        if (currentProjects.isEmpty()) {
            val initialProjects = listOf(
                ProjectEntity(
                    id = "p1",
                    title = "The Neoteric Cable-Stayed Bridge",
                    category = "Bridges",
                    location = "Metropolis, USA",
                    status = "Completed",
                    date = "2025-10-15",
                    budget = "$320 Million",
                    headline = "Smart Structural Monitoring Meets Structural Brilliance",
                    overview = "Spanning 1.2 kilometers across the industrial river delta, this landmark modern cable-stayed bridge utilizes advanced high-performance concrete, lightweight post-tensioned deck segments, and real-time fiber-optic structural health monitoring. It serves as a vital economic lifeline, accommodating 85,000 vehicles daily while reducing transit bottlenecks by 35%.",
                    technicalSpecs = "• Span Length: 1,200m\n• Pylon Height: 185m\n• Stay Cable Tension: up to 12,000 kN\n• Concrete Strength: 65 MPa (HPC)\n• Steel Grade: Grade 60 (A615)\n• Monitoring Sensors: 240+ fiber-optics",
                    challenges = "Severe tidal fluctuations, deep soft alluvial soil profiles with low bearing capacity, and extreme seismic vulnerability (PGA of 0.45g).",
                    solutions = "Engineered deep 2.5m diameter drilled shafts (bored piles) down to bedded sandstone at 68m depth. Implemented viscous fluid dampers at pylon-deck interfaces and continuous fiber-optic strain/vibration sensor networks.",
                    lessonsLearned = "Dynamic soil-structure interaction model must be coupled with structural designs from Day 1 to optimize pile configuration, saving approximately 12% in concrete volume.",
                    timeline = "Jan 2023 - Oct 2025",
                    imageUrl = "img_project_bridge"
                ),
                ProjectEntity(
                    id = "p2",
                    title = "Obsidian Sustainable Eco-Tower",
                    category = "Buildings",
                    location = "Seattle, USA",
                    status = "Under Construction",
                    date = "2026-02-10",
                    budget = "$180 Million",
                    headline = "Carbon-Negative Structural High-Rise Architecture",
                    overview = "A 45-story commercial skyscraper establishing new benchmarks in structural ecology. Featuring a structural frame made of geopolymer carbon-negative concrete, cross-laminated timber (CLT) floor plates, and integrated vertical gardens, the Obsidian Tower actively captures carbon while maintaining extreme wind-drift rigidity.",
                    technicalSpecs = "• Building Height: 192m (45 stories)\n• Structural System: Outrigger & Core\n• Concrete Mix: Geopolymer slag-flyash\n• Wind Design: 3s gust 140 mph\n• Slab Type: Hybrid Steel-CLT Panels\n• LEED Level: Platinum (Pre-certified)",
                    challenges = "Excessive lateral wind drift on a high aspect ratio tower and significant structural load transfer requirements over soft clay subgrade.",
                    solutions = "Utilized high-efficiency structural outriggers at floors 15 and 30 to connect the reinforced core with exterior columns. Placed a 4.5m thick compensated mat foundation over 150 high-capacity friction piles.",
                    lessonsLearned = "Hybrid CLT-steel floor panels reduced the building's dead weight by 22%, which directly reduced the seismic shear demands and pile foundation dimensions.",
                    timeline = "Mar 2024 - Dec 2026",
                    imageUrl = "img_project_tower"
                ),
                ProjectEntity(
                    id = "p3",
                    title = "Aquila Stormwater Diversion Tunnel",
                    category = "Sub-surface",
                    location = "Houston, USA",
                    status = "Design Phase",
                    date = "2026-06-20",
                    budget = "$450 Million",
                    headline = "Resilient Sub-surface Flood Mitigation Engineering",
                    overview = "An ultra-deep 8.5m diameter stormwater diversion tunnel designed to intercept and convey up to 1,200 cubic meters per second of floodwater safely beneath Houston's highly urbanized zones. Built using advanced earth-pressure balance (EPB) tunnel boring machines.",
                    technicalSpecs = "• Tunnel Length: 9.6 km\n• Finished Diameter: 8.5m\n• Lining Type: Steel fiber-reinforced segments\n• Depth Range: 22m to 42m\n• TBM Tech: Earth-Pressure Balance (EPB)\n• Design Flow: 1,200 m³/s",
                    challenges = "Micro-tunneling under dense historic brick buildings, dealing with high groundwater tables and unstable sandy-silt soils.",
                    solutions = "Utilized ground freezing at key critical curves to solidify soil prior to excavation. Employed a double-gasketed precast concrete segment lining system to guarantee 100% water exclusion.",
                    lessonsLearned = "Continuous settlement monitoring with automated robotic total stations prevented structural damage to overlying historical buildings.",
                    timeline = "Sep 2025 - Jun 2028",
                    imageUrl = "img_project_tunnel"
                )
            )
            dao.insertProjects(initialProjects)
        }

        val currentBlogs = dao.getAllBlogPostsFlow().first()
        if (currentBlogs.isEmpty()) {
            val initialBlogs = listOf(
                BlogEntity(
                    id = "b1",
                    title = "Seismic Dampers in Modern Skyscrapers",
                    category = "Structural",
                    tags = "Seismic Design,High-rise,Dynamics,Safety",
                    date = "2026-07-01",
                    readTime = "6 min read",
                    summary = "An in-depth technical analysis of tuned mass dampers (TMD) vs fluid viscous dampers (FVD) in mitigating extreme wind and seismic induced dynamic oscillations.",
                    content = "Modern skyscraper design is an ongoing battle against dynamic loads, particularly wind forces and seismic ground motions. As buildings grow taller and more slender, their fundamental natural frequencies decrease, making them susceptible to excessive sway. This post explores the engineering behind Tuned Mass Dampers (TMDs) and Fluid Viscous Dampers (FVDs), the two premier technologies deployed today.\n\n### 1. Tuned Mass Dampers (TMDs)\nA TMD is a massive inertial block suspended inside the upper floors of a skyscraper. It is tuned to the building's fundamental modal frequency. When wind or seismic events cause the tower to deflect, the TMD oscillates out of phase with the structure, dissipating kinetic energy through hydraulic pistons.\n\n### 2. Fluid Viscous Dampers (FVDs)\nFVDs operate on the principle of fluid flow resistance. Comprising a stainless-steel piston head moving through a chamber filled with silicone fluid, FVDs convert mechanical shock energy into thermal energy. Installed diagonally within bracing elements, they reduce structural shears and bending moments by up to 45%.\n\n### Conclusion & Design Choice\nFor ultra-tall buildings with slender profiles, a hybrid system—deploying a massive TMD at the summit for wind comfort and distributed FVDs along the vertical trusses for seismic survival—represents the pinnacle of current structural engineering design.",
                    authorName = "Dr. Elena Vance, Ph.D., P.E.",
                    authorTitle = "Principal Structural Advisor",
                    imageUrl = "img_project_tower"
                ),
                BlogEntity(
                    id = "b2",
                    title = "Sustainability: The Geopolymer Revolution",
                    category = "Materials",
                    tags = "Concrete,Materials,Sustainability,CO2",
                    date = "2026-06-15",
                    readTime = "5 min read",
                    summary = "Replacing traditional Portland cement with industrial fly ash and blast furnace slag reduces concrete's carbon footprint by 80% while enhancing acid resistance.",
                    content = "Concrete is the second most consumed substance on Earth after water, and the production of ordinary Portland cement (OPC) accounts for roughly 8% of global CO2 emissions. Geopolymer concrete represents a revolutionary chemistry shift, substituting traditional calcium-silicate-hydrate (C-S-H) binders with an inorganic aluminosilicate polymer network.\n\n### Chemistry of Geopolymers\nInstead of hydraulic hydration, geopolymerization relies on the chemical reaction of silica-rich and alumina-rich source materials (such as fly ash from coal plants or ground granulated blast-furnace slag from steel plants) with an alkaline activator (sodium silicate and sodium hydroxide).\n\n### Performance Advantages\n1. **Carbon Footprint**: Up to 80% lower net greenhouse gas emissions.\n2. **Chemical Durability**: Outstanding resistance to sulfuric acids and sulfate-rich soil environments.\n3. **Fire Resistance**: Stable up to 1000°C due to its ceramic-like ceramic structure.\n\n### Challenges to Mass Adoption\nWhile performance is superior, curing geopolymers requires heat or specialized chemical compounding, which complicates on-site ready-mix operations. However, for pre-cast tunnels and subgrade foundation elements, geopolymer concrete represents the immediate future of resilient infrastructure.",
                    authorName = "Marcus Thorne, M.S., S.E.",
                    authorTitle = "Director of Materials Engineering",
                    imageUrl = "img_project_tunnel"
                ),
                BlogEntity(
                    id = "b3",
                    title = "Smart Bridges: Real-time Health Monitoring",
                    category = "Infrastructure",
                    tags = "IoT,Bridges,Sensors,Asset-Management",
                    date = "2026-05-22",
                    readTime = "8 min read",
                    summary = "How distributed fiber-optic Bragg grating sensors and AI-driven strain models identify structural fatigue and prevent failures before they occur.",
                    content = "Aging civil infrastructure demands smart, automated asset-management strategies. Conventional bridge inspections are visual and periodic, meaning fatigue cracks or soil settlement can worsen between cycles. Fiber-optic Structural Health Monitoring (SHM) changes this paradigm, providing real-time, sub-millimeter strain telemetry directly from critical tension elements.\n\n### Fiber Bragg Grating (FBG) Technology\nFBG sensors are embedded inside the reinforcing rebar or bonded directly to the steel stay cables. A broadband light is pulsed through the fiber-optic cable; changes in strain shift the reflected wavelength of light. By measuring this shift, engineers can record temperature-compensated strain levels down to 1 microstrain.\n\n### Real-time Deflection Modeling\nBy correlating strain data from hundreds of sensor nodes across a bridge deck with traffic load profiles, an engineering model computes the structure's real-time deflected shape. If strain limits are breached (due to overweight vehicles or seismic shocks), the system automatically triggers alerts and camera logging.\n\n### Cost Benefit Analysis\nWhile adding SHM adds 1.5% to the initial bridge budget, it reduces routine visual inspection costs by 60% and increases the overall service life of major structures from 75 to 120 years, making it an indispensable asset choice.",
                    authorName = "Sarah Jenkins, Ph.D., P.E.",
                    authorTitle = "Lead Smart Systems Architect",
                    imageUrl = "img_project_bridge"
                )
            )
            dao.insertBlogPosts(initialBlogs)
        }
    }
}
