package com.example.model

/**
 * Exact B.Tech (1st Semester) GROUP-A Batch (2026-2030) syllabus extracted from the
 * attached syllabus PDF, plus starter timetable and unit-wise advanced math/physics/BEEE questions
 * containing integrals, derivatives, Greek symbols (α, β, Γ, λ, ψ), diagrams, and marking schemes.
 */
object SyllabusSeedData {

    fun defaultGroupASyllabus(): List<SubjectDoc> = listOf(
        SubjectDoc(
            id = "sub_100102",
            courseCode = "100102",
            title = "Engineering Mathematics - I",
            credits = 3,
            category = "THEORY",
            sortOrder = 1,
            units = listOf(
                UnitItem(
                    unitId = "u_100102_1",
                    unitNumber = "Unit 1.0",
                    title = "Basic Linear Algebra",
                    hours = 8,
                    topics = listOf(
                        TopicItem("t_100102_1_1", "1.1", "Elementary transformations and Rank of Matrix, Echelon & Normal form, Linear dependence in Rⁿ"),
                        TopicItem("t_100102_1_2", "1.2", "Consistency of System of Linear Equations"),
                        TopicItem("t_100102_1_3", "1.3", "Eigenvalues & Eigenvectors, Cayley-Hamilton Theorem"),
                        TopicItem("t_100102_1_4", "1.4", "Similarity of Matrices & Diagonalization, Quadratic & Canonical Form")
                    )
                ),
                UnitItem(
                    unitId = "u_100102_2",
                    unitNumber = "Unit 2.0",
                    title = "Differential Calculus: Single Variable",
                    hours = 8,
                    topics = listOf(
                        TopicItem("t_100102_2_1", "2.1", "Higher order derivatives; Successive differentiation and Leibnitz's Theorem"),
                        TopicItem("t_100102_2_2", "2.2", "Mean Value Theorems (Rolle's, Lagrange's, Cauchy)"),
                        TopicItem("t_100102_2_3", "2.3", "Indeterminate Forms; L' Hopital Rule"),
                        TopicItem("t_100102_2_4", "2.4", "Tangents & Normal of Algebraic & Polar Curves, Curvature")
                    )
                ),
                UnitItem(
                    unitId = "u_100102_3",
                    unitNumber = "Unit 3.0",
                    title = "Differential Calculus: Several Variables",
                    hours = 9,
                    topics = listOf(
                        TopicItem("t_100102_3_1", "3.1", "Partial differentiations; Euler's theorems"),
                        TopicItem("t_100102_3_2", "3.2", "Limit, Continuity and differentiability of functions of Several Variables"),
                        TopicItem("t_100102_3_3", "3.3", "Taylor's theorem with remainders, Maclaurin's theorem & problems"),
                        TopicItem("t_100102_3_4", "3.4", "Maxima & Minima for several variables; Lagrange's Multiplier Method")
                    )
                ),
                UnitItem(
                    unitId = "u_100102_4",
                    unitNumber = "Unit 4.0",
                    title = "Integral Calculus",
                    hours = 9,
                    topics = listOf(
                        TopicItem("t_100102_4_1", "4.1", "Differentiation under the integral sign; Leibnitz's rule"),
                        TopicItem("t_100102_4_2", "4.2", "Double integral; Change order of integration; Polar coordinates"),
                        TopicItem("t_100102_4_3", "4.3", "Triple Integral; Spherical Polar & Cylindrical Polar coordinates"),
                        TopicItem("t_100102_4_4", "4.4", "Beta, Gamma & Elliptic integrals; Relation & General Properties")
                    )
                ),
                UnitItem(
                    unitId = "u_100102_5",
                    unitNumber = "Unit 5.0",
                    title = "Vector Calculus",
                    hours = 8,
                    topics = listOf(
                        TopicItem("t_100102_5_1", "5.1", "Gradient, divergence, curl, tangent plane & directional derivative"),
                        TopicItem("t_100102_5_2", "5.2", "Line, surface & volume integrals — Green's, Stokes' & Gauss divergence theorems")
                    )
                )
            )
        ),
        SubjectDoc(
            id = "sub_100104",
            courseCode = "100104",
            title = "Engineering Physics",
            credits = 3,
            category = "THEORY",
            sortOrder = 2,
            units = listOf(
                UnitItem(
                    unitId = "u_100104_1",
                    unitNumber = "Unit 1.0",
                    title = "Wave Optics",
                    hours = 8,
                    topics = listOf(
                        TopicItem("t_100104_1_1", "1.1", "Interference, Division of amplitude, Newton's Ring & Michelson interferometer"),
                        TopicItem("t_100104_1_2", "1.2", "Fraunhofer diffraction: Single, double slit & Circular aperture"),
                        TopicItem("t_100104_1_3", "1.3", "Diffraction Grating"),
                        TopicItem("t_100104_1_4", "1.4", "Rayleigh criterion, resolving power of telescope"),
                        TopicItem("t_100104_1_5", "1.5", "Polarization, double-refracting crystal, Nicol prism")
                    )
                ),
                UnitItem(
                    unitId = "u_100104_2",
                    unitNumber = "Unit 2.0",
                    title = "Lasers and Optical Fiber",
                    hours = 8,
                    topics = listOf(
                        TopicItem("t_100104_2_1", "2.1", "Laser characteristics, Einstein's A & B coefficients, Population inversion, Resonator"),
                        TopicItem("t_100104_2_2", "2.2", "Ruby laser, He-Ne laser, and Semiconductor laser"),
                        TopicItem("t_100104_2_3", "2.3", "Optical fibre structure, TIR, Acceptance angle, Numerical Aperture, Types"),
                        TopicItem("t_100104_2_4", "2.4", "Propagation mechanism & Attenuation in optical fibres"),
                        TopicItem("t_100104_2_5", "2.5", "Applications of optical fibres, Fibre optic sensors")
                    )
                ),
                UnitItem(
                    unitId = "u_100104_3",
                    unitNumber = "Unit 3.0",
                    title = "Electromagnetism & Propagation of Waves",
                    hours = 8,
                    topics = listOf(
                        TopicItem("t_100104_3_1", "3.1", "Electric fields, Gauss' Law, Dielectrics and Capacitors"),
                        TopicItem("t_100104_3_2", "3.2", "Magnetic fields & Magnetic Materials (Dia, Para, Ferro)"),
                        TopicItem("t_100104_3_3", "3.3", "Maxwell's Equations"),
                        TopicItem("t_100104_3_4", "3.4", "Electromagnetic waves & Poynting Theorem")
                    )
                ),
                UnitItem(
                    unitId = "u_100104_4",
                    unitNumber = "Unit 4.0",
                    title = "Quantum Mechanics",
                    hours = 8,
                    topics = listOf(
                        TopicItem("t_100104_4_1", "4.1", "Introduction to Quantum Mechanics"),
                        TopicItem("t_100104_4_2", "4.2", "De-Broglie hypothesis, Heisenberg uncertainty principle"),
                        TopicItem("t_100104_4_3", "4.3", "Wave function ψ and its physical characteristics"),
                        TopicItem("t_100104_4_4", "4.4", "Schrödinger's equation (time-dependent and independent)"),
                        TopicItem("t_100104_4_5", "4.5", "Particle in a 1D box: Energy Eigenvalues and Eigenfunctions"),
                        TopicItem("t_100104_4_6", "4.6", "Potential barrier and Quantum Tunneling")
                    )
                ),
                UnitItem(
                    unitId = "u_100104_5",
                    unitNumber = "Unit 5.0",
                    title = "Semiconductors & Nano-materials",
                    hours = 10,
                    topics = listOf(
                        TopicItem("t_100104_5_1", "5.1", "Band structures: Metal, semiconductor and insulator"),
                        TopicItem("t_100104_5_2", "5.2", "Intrinsic & extrinsic semiconductors, Drift, Diffusion & Fermi level"),
                        TopicItem("t_100104_5_3", "5.3", "Photodiode, P-N junction transistor, LED"),
                        TopicItem("t_100104_5_4", "5.4", "Hall effect"),
                        TopicItem("t_100104_5_5", "5.5", "Solar cell and its characteristics"),
                        TopicItem("t_100104_5_6", "5.6", "Nanoscience & nanotechnology, significance of nanoscale"),
                        TopicItem("t_100104_5_7", "5.7", "Bulk vs nanomaterials: surface-to-volume ratio & band gap shift"),
                        TopicItem("t_100104_5_8", "5.8", "Classification of Nanostructures: 0D Quantum Dot, 1D Nanowire")
                    )
                )
            )
        ),
        SubjectDoc(
            id = "sub_100105",
            courseCode = "100105",
            title = "Introduction to AI",
            credits = 3,
            category = "THEORY",
            sortOrder = 3,
            units = listOf(
                UnitItem(
                    unitId = "u_100105_1",
                    unitNumber = "Unit 1.0",
                    title = "Introduction to Artificial Intelligence",
                    hours = 8,
                    topics = listOf(
                        TopicItem("t_100105_1_1", "1.1", "What is AI? History and evolution of AI"),
                        TopicItem("t_100105_1_2", "1.2", "Foundations: philosophy, mathematics, cognitive-science"),
                        TopicItem("t_100105_1_3", "1.3", "Intelligent agents & PEAS description"),
                        TopicItem("t_100105_1_4", "1.4", "Types of environments (observable, deterministic, episodic, static)"),
                        TopicItem("t_100105_1_5", "1.5", "Types of agents: simple reflex, model, goal, utility, learning"),
                        TopicItem("t_100105_1_6", "1.6", "AI application domains across engineering disciplines")
                    )
                ),
                UnitItem(
                    unitId = "u_100105_2",
                    unitNumber = "Unit 2.0",
                    title = "Problem Solving by Search",
                    hours = 9,
                    topics = listOf(
                        TopicItem("t_100105_2_1", "2.1", "Problem formulation and state-space representation"),
                        TopicItem("t_100105_2_2", "2.2", "Uninformed search: BFS, DFS, UCS, DLS, IDS"),
                        TopicItem("t_100105_2_3", "2.3", "Informed search: Greedy Best-First Search, A* algorithm"),
                        TopicItem("t_100105_2_4", "2.4", "Heuristic function design, admissibility & consistency"),
                        TopicItem("t_100105_2_5", "2.5", "Local search: Hill Climbing, Simulated Annealing"),
                        TopicItem("t_100105_2_6", "2.6", "Adversarial search: Minimax, Alpha-Beta (α-β) pruning"),
                        TopicItem("t_100105_2_7", "2.7", "Constraint Satisfaction Problems (CSP) & backtracking")
                    )
                ),
                UnitItem(
                    unitId = "u_100105_3",
                    unitNumber = "Unit 3.0",
                    title = "Knowledge Representation and Reasoning",
                    hours = 8,
                    topics = listOf(
                        TopicItem("t_100105_3_1", "3.1", "KR issues; Propositional logic syntax & semantics"),
                        TopicItem("t_100105_3_2", "3.2", "Inference in propositional logic: Resolution"),
                        TopicItem("t_100105_3_3", "3.3", "First-order predicate logic — syntax & quantifiers"),
                        TopicItem("t_100105_3_4", "3.4", "FOL Inference: Forward & backward chaining, resolution"),
                        TopicItem("t_100105_3_5", "3.5", "Structured KR: Semantic networks & frames"),
                        TopicItem("t_100105_3_6", "3.6", "Rule-based (production) systems")
                    )
                ),
                UnitItem(
                    unitId = "u_100105_4",
                    unitNumber = "Unit 4.0",
                    title = "Reasoning Under Uncertainty",
                    hours = 7,
                    topics = listOf(
                        TopicItem("t_100105_4_1", "4.1", "Sources of uncertainty & basic probability theory"),
                        TopicItem("t_100105_4_2", "4.2", "Bayes' theorem and application to AI reasoning"),
                        TopicItem("t_100105_4_3", "4.3", "Bayesian networks: representation & manual inference"),
                        TopicItem("t_100105_4_4", "4.4", "Introduction to fuzzy logic and fuzzy sets"),
                        TopicItem("t_100105_4_5", "4.5", "Certainty factors and rule-based uncertainty")
                    )
                ),
                UnitItem(
                    unitId = "u_100105_5",
                    unitNumber = "Unit 5.0",
                    title = "Introduction to Machine Learning & AI Applications",
                    hours = 10,
                    topics = listOf(
                        TopicItem("t_100105_5_1", "5.1", "ML paradigms: supervised, unsupervised, reinforcement"),
                        TopicItem("t_100105_5_2", "5.2", "Regression & classification: linear regression, k-NN, decision trees"),
                        TopicItem("t_100105_5_3", "5.3", "Clustering concept (k-means)"),
                        TopicItem("t_100105_5_4", "5.4", "Artificial Neural Networks: perceptron & basic architecture"),
                        TopicItem("t_100105_5_5", "5.5", "Natural Language Processing (NLP): concept & applications"),
                        TopicItem("t_100105_5_6", "5.6", "Computer Vision: concept & applications"),
                        TopicItem("t_100105_5_7", "5.7", "Expert systems: architecture & applications"),
                        TopicItem("t_100105_5_8", "5.8", "Robotics and AI: intelligent robotic systems"),
                        TopicItem("t_100105_5_9", "5.9", "Ethics, bias, responsible AI & Sustainable Development Goals")
                    )
                )
            )
        ),
        SubjectDoc(
            id = "sub_100108",
            courseCode = "100108",
            title = "Computer Fundamentals & Emerging Tech",
            credits = 3,
            category = "THEORY",
            sortOrder = 4,
            units = listOf(
                UnitItem(
                    unitId = "u_100108_1",
                    unitNumber = "Unit 1.0",
                    title = "Fundamentals of Computer Systems",
                    hours = 8,
                    topics = listOf(
                        TopicItem("t_100108_1_1", "1.1", "Classification of computers, Functional units"),
                        TopicItem("t_100108_1_2", "1.2", "Central Processing Unit (CPU) organization and working"),
                        TopicItem("t_100108_1_3", "1.3", "Memory Architecture, Representation & Hierarchy"),
                        TopicItem("t_100108_1_4", "1.4", "CPU Registers & Impact of Cache Memory"),
                        TopicItem("t_100108_1_5", "1.5", "Motherboard and its components"),
                        TopicItem("t_100108_1_6", "1.6", "The Von-Neumann architecture"),
                        TopicItem("t_100108_1_7", "1.7", "Machine, assembly, high-level languages; Compiler vs Interpreter")
                    )
                ),
                UnitItem(
                    unitId = "u_100108_2",
                    unitNumber = "Unit 2.0",
                    title = "Data Representation",
                    hours = 8,
                    topics = listOf(
                        TopicItem("t_100108_2_1", "2.1", "Data Representation & Number Systems"),
                        TopicItem("t_100108_2_2", "2.2", "Conversion between decimal and binary systems"),
                        TopicItem("t_100108_2_3", "2.3", "Conversion among binary, octal, and hexadecimal"),
                        TopicItem("t_100108_2_4", "2.4", "Binary arithmetic (addition, subtraction)"),
                        TopicItem("t_100108_2_5", "2.5", "Signed number representation (1's complement)"),
                        TopicItem("t_100108_2_6", "2.6", "Signed number representation (2's complement)"),
                        TopicItem("t_100108_2_7", "2.7", "ASCII & Unicode data representation in memory")
                    )
                ),
                UnitItem(
                    unitId = "u_100108_3",
                    unitNumber = "Unit 3.0",
                    title = "Computer Software & Operating Systems",
                    hours = 9,
                    topics = listOf(
                        TopicItem("t_100108_3_1", "3.1", "Introduction & Types of Software"),
                        TopicItem("t_100108_3_2", "3.2", "System Software & Application Software"),
                        TopicItem("t_100108_3_3", "3.4", "Operating System (OS) objectives & types (Batch, Time-sharing, Real-time)"),
                        TopicItem("t_100108_3_4", "3.6", "Functions of OS (Process & Memory Management)"),
                        TopicItem("t_100108_3_5", "3.7", "CLI vs GUI & File system basics (Windows, Linux, macOS)")
                    )
                ),
                UnitItem(
                    unitId = "u_100108_4",
                    unitNumber = "Unit 4.0",
                    title = "Computer Networks & Internet Basics",
                    hours = 9,
                    topics = listOf(
                        TopicItem("t_100108_4_1", "4.1", "Computer Networks, Transmission Media & Data Transmission"),
                        TopicItem("t_100108_4_2", "4.5", "Network Types (LAN, WAN, MAN) & Topologies (Star, Bus, Ring, Mesh)"),
                        TopicItem("t_100108_4_3", "4.7", "Network Devices (Hub, Switch, Router, Modem, Gateway)"),
                        TopicItem("t_100108_4_4", "4.8", "IP Address, TCP/UDP, HTTP, FTP, DNS, URL & Client-Server")
                    )
                ),
                UnitItem(
                    unitId = "u_100108_5",
                    unitNumber = "Unit 5.0",
                    title = "Emerging Technologies",
                    hours = 8,
                    topics = listOf(
                        TopicItem("t_100108_5_1", "5.1", "Internet of Things (IoT): Concept, architecture, Smart homes/cities"),
                        TopicItem("t_100108_5_2", "5.2", "Cyber Security: CIA Triad, cyber threats & countermeasures"),
                        TopicItem("t_100108_5_3", "5.3", "AI & Machine Learning overview"),
                        TopicItem("t_100108_5_4", "5.5", "Blockchain: Distributed ledger, blocks, hashing, decentralization"),
                        TopicItem("t_100108_5_5", "5.6", "Data Science: Lifecycle & role in decision making")
                    )
                )
            )
        ),
        SubjectDoc(
            id = "sub_100111",
            courseCode = "100111",
            title = "Basics of Electrical & Electronics Engg",
            credits = 3,
            category = "THEORY",
            sortOrder = 5,
            units = listOf(
                UnitItem(
                    unitId = "u_100111_1",
                    unitNumber = "Unit 1.0",
                    title = "Basic Electrical Circuits",
                    hours = 8,
                    topics = listOf(
                        TopicItem("t_100111_1_1", "1.1", "R, L, C as active & passive elements; Unilateral/bilateral & linear/nonlinear"),
                        TopicItem("t_100111_1_2", "1.3", "Ohm's Law & Kirchhoff's Current and Voltage Laws (KCL, KVL)"),
                        TopicItem("t_100111_1_3", "1.4", "Series/parallel circuits, voltage/current sources & source transformation"),
                        TopicItem("t_100111_1_4", "1.5", "Mesh analysis and Nodal analysis techniques"),
                        TopicItem("t_100111_1_5", "1.6", "Current/Voltage divider rules & Star-Delta (Y-Δ) transformation")
                    )
                ),
                UnitItem(
                    unitId = "u_100111_2",
                    unitNumber = "Unit 2.0",
                    title = "A.C. Circuits",
                    hours = 8,
                    topics = listOf(
                        TopicItem("t_100111_2_1", "2.1", "Electrical signals, waveforms; Peak, average & RMS values"),
                        TopicItem("t_100111_2_2", "2.3", "Peak factor, form factor & Phasors (rectangular & polar)"),
                        TopicItem("t_100111_2_3", "2.5", "Impedance (Z), Admittance (Y), Power factor & Power triangle"),
                        TopicItem("t_100111_2_4", "2.6", "Three-Phase Systems: phase sequence, balanced/unbalanced loads")
                    )
                ),
                UnitItem(
                    unitId = "u_100111_3",
                    unitNumber = "Unit 3.0",
                    title = "Basic Electrical Machines",
                    hours = 8,
                    topics = listOf(
                        TopicItem("t_100111_3_1", "3.1", "Electromagnetism, self-inductance & mutual inductance"),
                        TopicItem("t_100111_3_2", "3.2", "DC Machines: Principle, construction, classification & applications"),
                        TopicItem("t_100111_3_3", "3.3", "Single-Phase Transformers: EMF equation, ratios & voltage regulation"),
                        TopicItem("t_100111_3_4", "3.5", "Single & Three-phase Induction Motors & fractional HP motors")
                    )
                ),
                UnitItem(
                    unitId = "u_100111_4",
                    unitNumber = "Unit 4.0",
                    title = "Semiconductor Devices",
                    hours = 8,
                    topics = listOf(
                        TopicItem("t_100111_4_1", "4.1", "Intrinsic & extrinsic semiconductors; p-n Junction Diode characteristics"),
                        TopicItem("t_100111_4_2", "4.3", "Diode temperature effects & breakdown mechanisms"),
                        TopicItem("t_100111_4_3", "4.4", "Half-wave/Full-wave rectifiers, filters & Zener voltage regulator"),
                        TopicItem("t_100111_4_4", "4.5", "BJT (PNP/NPN) fundamentals, CE amplifier & transistor as switch")
                    )
                ),
                UnitItem(
                    unitId = "u_100111_5",
                    unitNumber = "Unit 5.0",
                    title = "Digital, Analog Electronics & Electrical Safety",
                    hours = 10,
                    topics = listOf(
                        TopicItem("t_100111_5_1", "5.1", "Binary, octal, hex number systems & binary addition"),
                        TopicItem("t_100111_5_2", "5.2", "Basic logic gates, universal gates (NAND/NOR) & Boolean algebra"),
                        TopicItem("t_100111_5_3", "5.3", "LEDs, 7-segment display, IC 7805/7912, Multimeter & CRO"),
                        TopicItem("t_100111_5_4", "5.5", "Sensors, transducers & electrical power generation/distribution"),
                        TopicItem("t_100111_5_5", "5.6", "Electrical safety: Fuse, MCB, MCCB & Earthing methods")
                    )
                )
            )
        ),
        SubjectDoc(
            id = "sub_100109",
            courseCode = "100109",
            title = "Universal Human Values",
            credits = 2,
            category = "THEORY",
            sortOrder = 6,
            units = listOf(
                UnitItem("u_100109_1", "Unit 1.0", "Self-Exploration, Happiness & Prosperity", 5, listOf(
                    TopicItem("t_100109_1_1", "1.1", "Self-Exploration as Value Education"),
                    TopicItem("t_100109_1_2", "1.2", "Continuous Happiness, Prosperity & Right Understanding")
                )),
                UnitItem("u_100109_2", "Unit 2.0", "Relationship, Physical Facility & Human Being", 5, listOf(
                    TopicItem("t_100109_2_1", "2.1", "Right Understanding, Relationship and Physical Facility"),
                    TopicItem("t_100109_2_2", "2.3", "Co-existence of sentient 'Self' and material 'Body'")
                )),
                UnitItem("u_100109_3", "Unit 3.0", "Values in Human Relationships & Society", 5, listOf(
                    TopicItem("t_100109_3_1", "3.1", "Values in human-human relationship"),
                    TopicItem("t_100109_3_2", "3.2", "Harmony in the Society")
                )),
                UnitItem("u_100109_4", "Unit 4.0", "Harmony in Nature and Existence", 6, listOf(
                    TopicItem("t_100109_4_1", "4.1", "Harmony in Nature & interconnectedness among four orders"),
                    TopicItem("t_100109_4_2", "4.3", "Existence as Co-existence in all-pervasive space")
                )),
                UnitItem("u_100109_5", "Unit 5.0", "Holistic Understanding & Professional Ethics", 7, listOf(
                    TopicItem("t_100109_5_1", "5.1", "Natural acceptance of human values & ethical conduct"),
                    TopicItem("t_100109_5_2", "5.4", "Competence in professional ethics & Universal Human Order")
                ))
            )
        ),
        SubjectDoc(
            id = "sub_100110",
            courseCode = "100110",
            title = "Essence of Indian Constitution",
            credits = 0,
            category = "THEORY",
            sortOrder = 7,
            units = listOf(
                UnitItem("u_100110_1", "Unit 1.0", "Constitution and Preamble", 8, listOf(
                    TopicItem("t_100110_1_1", "1.1", "Meaning & Historical perspective of Constitution of India"),
                    TopicItem("t_100110_1_2", "1.3", "Salient features, characteristics & Preamble")
                )),
                UnitItem("u_100110_2", "Unit 2.0", "Fundamental Rights and Duties", 8, listOf(
                    TopicItem("t_100110_2_1", "2.1", "Fundamental Rights under Part-III"),
                    TopicItem("t_100110_2_2", "2.2", "Fundamental duties and their significance")
                )),
                UnitItem("u_100110_3", "Unit 3.0", "Directive Principles", 8, listOf(
                    TopicItem("t_100110_3_1", "3.1", "Relevance & articles of Directive Principles (Part-IV)")
                )),
                UnitItem("u_100110_4", "Unit 4.0", "Governance", 9, listOf(
                    TopicItem("t_100110_4_1", "4.1", "Governance under Part-VI; Legislative Assembly & Council")
                )),
                UnitItem("u_100110_5", "Unit 5.0", "Amendments of Constitution", 9, listOf(
                    TopicItem("t_100110_5_1", "5.1", "Amendment powers, procedure & Major Amendments (42nd, 44th, 74th, 76th, 86th, 91st)")
                ))
            )
        ),
        SubjectDoc(
            id = "sub_100104P",
            courseCode = "100104P",
            title = "Engineering Physics Lab",
            credits = 1,
            category = "LAB",
            sortOrder = 8,
            units = listOf(
                UnitItem("u_100104P_1", "Lab Set A", "Optics & Fiber Experiments (LE-1 to LE-6)", 12, listOf(
                    TopicItem("t_100104P_1", "LE-1", "Diffraction Grating — Wavelength measurement"),
                    TopicItem("t_100104P_2", "LE-2", "Newton's Ring Experiment — λ & Radius of Curvature"),
                    TopicItem("t_100104P_3", "LE-3", "Resolving Power of Telescope"),
                    TopicItem("t_100104P_4", "LE-4", "Polarimeter — Specific rotation of cane sugar"),
                    TopicItem("t_100104P_5", "LE-5", "Numerical Aperture of Optical Fiber (Virtual Lab)"),
                    TopicItem("t_100104P_6", "LE-6", "Dielectric Constant Measurement")
                )),
                UnitItem("u_100104P_2", "Lab Set B", "Quantum & Semiconductor Experiments (LE-7 to LE-12)", 12, listOf(
                    TopicItem("t_100104P_7", "LE-7", "Measurement of Planck's Constant via Photocell"),
                    TopicItem("t_100104P_8", "LE-8", "Stern-Gerlach Experiment (Virtual Lab)"),
                    TopicItem("t_100104P_9", "LE-9", "p-n Junction I-V Characteristics"),
                    TopicItem("t_100104P_10", "LE-10", "Hall Effect Experiment — Carrier Density"),
                    TopicItem("t_100104P_11", "LE-11", "Energy Band Gap Measurement (Four-Probe)"),
                    TopicItem("t_100104P_12", "LE-12", "Nanoparticle Size & Light Absorption")
                ))
            )
        ),
        SubjectDoc(
            id = "sub_100111P",
            courseCode = "100111P",
            title = "BEEE Lab",
            credits = 1,
            category = "LAB",
            sortOrder = 9,
            units = listOf(
                UnitItem("u_100111P_1", "Lab Set A", "Circuits, Machines & Rectifiers (LE-1 to LE-8)", 16, listOf(
                    TopicItem("t_100111P_1", "LE-1", "Active/passive components & Ohm's Law verification"),
                    TopicItem("t_100111P_2", "LE-2", "Kirchhoff's Laws (KVL/KCL) in series & parallel circuits"),
                    TopicItem("t_100111P_3", "LE-3", "Power factor improvement using a capacitor"),
                    TopicItem("t_100111P_4", "LE-4", "Single-phase transformer voltage & turns ratio"),
                    TopicItem("t_100111P_5", "LE-5", "Cut-section models of DC & Induction machines"),
                    TopicItem("t_100111P_6", "LE-6", "V-I characteristics of P-N junction diode"),
                    TopicItem("t_100111P_7", "LE-7", "Half-wave & full-wave rectifiers with/without filters"),
                    TopicItem("t_100111P_8", "LE-8", "Zener diode voltage regulator analysis")
                )),
                UnitItem("u_100111P_2", "Lab Set B", "BJT, Logic Gates, CRO & Safety (LE-9 to LE-16)", 16, listOf(
                    TopicItem("t_100111P_9", "LE-9", "Common Emitter (CE) BJT Input/Output characteristics"),
                    TopicItem("t_100111P_10", "LE-10", "Logic gates (AND, OR, NOT, XOR) via Universal gates"),
                    TopicItem("t_100111P_11", "LE-11", "Measurement of signal parameters using CRO"),
                    TopicItem("t_100111P_12", "LE-12", "Identification of cables, wires & fuses"),
                    TopicItem("t_100111P_13", "LE-13", "Operation of Fuse, MCB & MCCB"),
                    TopicItem("t_100111P_14", "LE-14", "Earthing techniques & electrical safety practices"),
                    TopicItem("t_100111P_15", "LE-15", "Domestic wiring of lamp & fan with fuse/MCB"),
                    TopicItem("t_100111P_16", "LE-16", "Megger handling & earth resistance measurement")
                ))
            )
        ),
        SubjectDoc(
            id = "sub_100112P",
            courseCode = "100112P",
            title = "Programming for Problem Solving Lab",
            credits = 1,
            category = "LAB",
            sortOrder = 10,
            units = listOf(
                UnitItem("u_100112P_1", "C Programming", "Structured C Foundations (LE-1 to LE-10)", 20, listOf(
                    TopicItem("t_100112P_1", "LE-1", "Algorithmic Logic, Flowchart & Basic I/O (Area of shapes)"),
                    TopicItem("t_100112P_2", "LE-2", "Decision Making Systems (if-else & switch-case grading)"),
                    TopicItem("t_100112P_3", "LE-3", "Iterative Problem Solving (Primes 1..N & Fibonacci)"),
                    TopicItem("t_100112P_4", "LE-4", "1-D Array Operations (Min/Max Linear Search & Average)"),
                    TopicItem("t_100112P_5", "LE-5", "2-D Arrays: Matrix Multiplication & Transpose"),
                    TopicItem("t_100112P_6", "LE-6", "Pointer Arithmetic & Strings (Reverse & Vowel count)"),
                    TopicItem("t_100112P_7", "LE-7", "Recursion vs Iteration (Factorial & GCD)"),
                    TopicItem("t_100112P_8", "LE-8", "Call-by-Value vs Call-by-Reference (Swap & Array)"),
                    TopicItem("t_100112P_9", "LE-9", "Structures & Unions (Employee Payroll System)"),
                    TopicItem("t_100112P_10", "LE-10", "File Handling Operations (Student records R/W)")
                )),
                UnitItem("u_100112P_2", "C++ OOP", "Object-Oriented C++ (LE-11 to LE-17)", 14, listOf(
                    TopicItem("t_100112P_11", "LE-11", "Classes, Objects & Encapsulation (Bank Account)"),
                    TopicItem("t_100112P_12", "LE-12", "Constructors & Destructor (Dynamic String class)"),
                    TopicItem("t_100112P_13", "LE-13", "Static Members & Friend Functions"),
                    TopicItem("t_100112P_14", "LE-14", "Implementation of 5 Inheritance types in C++"),
                    TopicItem("t_100112P_15", "LE-15", "Polymorphism, Abstract Shape class & Operator Overloading"),
                    TopicItem("t_100112P_16", "LE-16", "Robust Error Management (try, catch, throw)"),
                    TopicItem("t_100112P_17", "LE-17", "Function Templates & Class Templates (Pair)")
                ))
            )
        )
    )

    const val CSE_A_TIMETABLE_SENTINEL_ID = "tt_csea_mon_1"

    /**
     * Official Government Engineering College, Sheikhpura
     * Branch CSE-A | Semester 1 | Batch 2K26 (Effective 24-08-2026 | Primary Room: 104)
     * Recess daily: 13:00–14:00
     */
    fun starterTimetableSlots(): List<TimetableSlot> = listOf(
        // Monday (1)
        TimetableSlot(id = "tt_csea_mon_1", dayOfWeek = 1, startTime = "10:00", endTime = "11:00", subjectId = "sub_100104", subjectTitle = "Engineering Physics (EP 100104)", unitHint = "Dr. Basuki Nath Mishra (AP) • Lecture", room = "Room 104", slotType = "LECTURE"),
        TimetableSlot(id = "tt_csea_mon_2", dayOfWeek = 1, startTime = "11:00", endTime = "12:00", subjectId = "sub_100111", subjectTitle = "BEEE (100111)", unitHint = "Mr. Shambhu Kumar (AP) • Lecture", room = "Room 104", slotType = "LECTURE"),
        TimetableSlot(id = "tt_csea_mon_3", dayOfWeek = 1, startTime = "12:00", endTime = "13:00", subjectId = "sub_100102", subjectTitle = "Engineering Mathematics - I (M-I 100102)", unitHint = "Dr. Nida Fatima (Guest AP) • Lecture", room = "Room 104", slotType = "LECTURE"),
        TimetableSlot(id = "tt_csea_mon_4", dayOfWeek = 1, startTime = "14:00", endTime = "15:00", subjectId = "sub_100105", subjectTitle = "Introduction to AI (100105)", unitHint = "Mr. Ankit Kumar (AP) • Lecture", room = "Room 104", slotType = "LECTURE"),
        TimetableSlot(id = "tt_csea_mon_5", dayOfWeek = 1, startTime = "15:00", endTime = "16:00", subjectId = "sub_100108", subjectTitle = "CFET (100108)", unitHint = "Mr. Niraj Nirmal (AP) • Lecture", room = "Room 104", slotType = "LECTURE"),
        TimetableSlot(id = "tt_csea_mon_6", dayOfWeek = 1, startTime = "16:00", endTime = "17:00", subjectId = "sub_100110", subjectTitle = "Essence of Indian Constitution (EIC 100110)", unitHint = "Mr. Binay Kumar (AP) • Lecture", room = "Room 104", slotType = "LECTURE"),

        // Tuesday (2)
        TimetableSlot(id = "tt_csea_tue_1", dayOfWeek = 2, startTime = "10:00", endTime = "11:00", subjectId = "sub_100105", subjectTitle = "Introduction to AI (100105)", unitHint = "Mr. Ankit Kumar (AP) • Lecture", room = "Room 104", slotType = "LECTURE"),
        TimetableSlot(id = "tt_csea_tue_2", dayOfWeek = 2, startTime = "11:00", endTime = "13:00", subjectId = "sub_100112P", subjectTitle = "PPS Lab (100112P)", unitHint = "Mr. Md. Zaki Anwer (AP) • 2 hrs", room = "Room 109", slotType = "LAB"),
        TimetableSlot(id = "tt_csea_tue_3", dayOfWeek = 2, startTime = "14:00", endTime = "16:00", subjectId = "sub_100111P", subjectTitle = "BEEE Lab (100111P) / MM", unitHint = "Ms. Ankita Kumari (AP) • 2 hrs", room = "Lab / Others", slotType = "LAB"),
        TimetableSlot(id = "tt_csea_tue_4", dayOfWeek = 2, startTime = "16:00", endTime = "17:00", subjectId = "sub_100110", subjectTitle = "Essence of Indian Constitution (EIC 100110)", unitHint = "Mr. Binay Kumar (AP) • Lecture", room = "Room 104", slotType = "LECTURE"),

        // Wednesday (3)
        TimetableSlot(id = "tt_csea_wed_1", dayOfWeek = 3, startTime = "10:00", endTime = "11:00", subjectId = "sub_100111", subjectTitle = "BEEE (100111)", unitHint = "Ms. Ankita Kumari (AP) • Lecture", room = "Room 104", slotType = "LECTURE"),
        TimetableSlot(id = "tt_csea_wed_2", dayOfWeek = 3, startTime = "11:00", endTime = "12:00", subjectId = "sub_100109", subjectTitle = "Universal Human Values (UHV 100109)", unitHint = "Dr. Avdhesh Kumar Suman (AP) • Lecture", room = "Room 104", slotType = "LECTURE"),
        TimetableSlot(id = "tt_csea_wed_3", dayOfWeek = 3, startTime = "12:00", endTime = "13:00", subjectId = "sub_100104", subjectTitle = "Engineering Physics (EP 100104)", unitHint = "Dr. Basuki Nath Mishra (AP) • Lecture", room = "Room 104", slotType = "LECTURE"),
        TimetableSlot(id = "tt_csea_wed_4", dayOfWeek = 3, startTime = "14:00", endTime = "16:00", subjectId = "sub_100111P", subjectTitle = "BEEE Lab (100111P) / MM", unitHint = "Mr. Shambhu Kumar (AP) • 2 hrs", room = "Lab / Others", slotType = "LAB"),
        TimetableSlot(id = "tt_csea_wed_5", dayOfWeek = 3, startTime = "16:00", endTime = "17:00", subjectId = "", subjectTitle = "Sports", unitHint = "CSE-A Batch 2K26 • 1 hr", room = "Campus Ground", slotType = "SELF_STUDY"),

        // Thursday (4)
        TimetableSlot(id = "tt_csea_thu_1", dayOfWeek = 4, startTime = "10:00", endTime = "11:00", subjectId = "sub_100109", subjectTitle = "Universal Human Values (UHV 100109)", unitHint = "Dr. Avdhesh Kumar Suman (AP) • Lecture", room = "Room 104", slotType = "LECTURE"),
        TimetableSlot(id = "tt_csea_thu_2", dayOfWeek = 4, startTime = "11:00", endTime = "12:00", subjectId = "sub_100111", subjectTitle = "BEEE (100111)", unitHint = "Mr. Shambhu Kumar (AP) • Lecture", room = "Room 104", slotType = "LECTURE"),
        TimetableSlot(id = "tt_csea_thu_3", dayOfWeek = 4, startTime = "12:00", endTime = "13:00", subjectId = "sub_100102", subjectTitle = "Engineering Mathematics - I (M-I 100102)", unitHint = "Dr. Nida Fatima (Guest AP) • Lecture", room = "Room 104", slotType = "LECTURE"),
        TimetableSlot(id = "tt_csea_thu_4", dayOfWeek = 4, startTime = "14:00", endTime = "16:00", subjectId = "", subjectTitle = "Mentor Meeting", unitHint = "CSE-A Batch 2K26 Mentorship • 2 hrs", room = "Room 104", slotType = "SELF_STUDY"),
        TimetableSlot(id = "tt_csea_thu_5", dayOfWeek = 4, startTime = "16:00", endTime = "17:00", subjectId = "", subjectTitle = "Sports", unitHint = "CSE-A Batch 2K26 • 1 hr", room = "Campus Ground", slotType = "SELF_STUDY"),

        // Friday (5)
        TimetableSlot(id = "tt_csea_fri_1", dayOfWeek = 5, startTime = "10:00", endTime = "11:00", subjectId = "sub_100105", subjectTitle = "Introduction to AI (100105)", unitHint = "Mr. Ankit Kumar (AP) • Lecture", room = "Room 104", slotType = "LECTURE"),
        TimetableSlot(id = "tt_csea_fri_2", dayOfWeek = 5, startTime = "11:00", endTime = "12:00", subjectId = "sub_100108", subjectTitle = "CFET (100108)", unitHint = "Mr. Niraj Nirmal (AP) • Lecture", room = "Room 104", slotType = "LECTURE"),
        TimetableSlot(id = "tt_csea_fri_3", dayOfWeek = 5, startTime = "12:00", endTime = "13:00", subjectId = "sub_100110", subjectTitle = "Essence of Indian Constitution (EIC 100110)", unitHint = "Mr. Binay Kumar (AP) • Lecture", room = "Room 104", slotType = "LECTURE"),
        TimetableSlot(id = "tt_csea_fri_4", dayOfWeek = 5, startTime = "14:00", endTime = "16:00", subjectId = "sub_100104P", subjectTitle = "Engineering Physics Lab (EP Lab 100104P)", unitHint = "Dr. Basuki Nath Mishra (AP) • 2 hrs", room = "Lab / Others", slotType = "LAB"),
        TimetableSlot(id = "tt_csea_fri_5", dayOfWeek = 5, startTime = "16:00", endTime = "17:00", subjectId = "", subjectTitle = "Sports", unitHint = "CSE-A Batch 2K26 • 1 hr", room = "Campus Ground", slotType = "SELF_STUDY"),

        // Saturday (6)
        TimetableSlot(id = "tt_csea_sat_1", dayOfWeek = 6, startTime = "10:00", endTime = "11:00", subjectId = "sub_100102", subjectTitle = "Engineering Mathematics - I (M-I 100102)", unitHint = "Dr. Nida Fatima (Guest AP) • Lecture", room = "Room 104", slotType = "LECTURE"),
        TimetableSlot(id = "tt_csea_sat_2", dayOfWeek = 6, startTime = "11:00", endTime = "12:00", subjectId = "sub_100104", subjectTitle = "Engineering Physics (EP 100104)", unitHint = "Dr. Basuki Nath Mishra (AP) • Lecture", room = "Room 104", slotType = "LECTURE"),
        TimetableSlot(id = "tt_csea_sat_3", dayOfWeek = 6, startTime = "12:00", endTime = "13:00", subjectId = "sub_100108", subjectTitle = "CFET (100108)", unitHint = "Mr. Niraj Nirmal (AP) • Lecture", room = "Room 104", slotType = "LECTURE"),
        TimetableSlot(id = "tt_csea_sat_4", dayOfWeek = 6, startTime = "14:00", endTime = "16:00", subjectId = "", subjectTitle = "Club Activities", unitHint = "CSE-A Batch 2K26 • 2 hrs", room = "Room 104 / Campus", slotType = "SELF_STUDY"),
        TimetableSlot(id = "tt_csea_sat_5", dayOfWeek = 6, startTime = "16:00", endTime = "17:00", subjectId = "", subjectTitle = "Sports", unitHint = "CSE-A Batch 2K26 • 1 hr", room = "Campus Ground", slotType = "SELF_STUDY")
    )

    fun starterAdvancedQuestions(): List<TestQuestion> = listOf(
        // Engineering Mathematics - I | Unit 4.0: Integral Calculus
        TestQuestion(
            id = "q_math_u4_1",
            subjectId = "sub_100102",
            subjectTitle = "Engineering Mathematics - I",
            unitId = "u_100102_4",
            unitTitle = "Unit 4.0: Integral Calculus",
            topicCode = "4.4",
            topicTitle = "Beta & Gamma Functions",
            questionType = "MCQ",
            prompt = "Using the Beta-Gamma identity, evaluate the definite trigonometric integral below for α = 2, β = 1:",
            formulaExpr = "I = ∫₀^(π/2) sin^(2α-1)(θ) · cos^(2β-1)(θ) dθ = ½ B(α, β)",
            diagramKind = "INTEGRAL_AREA",
            options = listOf(
                "Γ(2)Γ(1) / [2·Γ(3)] = 1/4",
                "Γ(2)Γ(1) / Γ(3) = 1/2",
                "π / 4",
                "Γ(3) / [2·Γ(2)] = 1"
            ),
            correctOptionIndex = 0,
            maxMarks = 2,
            modelAnswer = "I = ½ B(2,1) = ½ · Γ(2)Γ(1)/Γ(3) = ½ · (1·1)/2 = 1/4.",
            markingScheme = listOf("2M: Correct selection of 1/4 via Beta-Gamma relation")
        ),
        TestQuestion(
            id = "q_math_u4_2",
            subjectId = "sub_100102",
            subjectTitle = "Engineering Mathematics - I",
            unitId = "u_100102_4",
            unitTitle = "Unit 4.0: Integral Calculus",
            topicCode = "4.1",
            topicTitle = "Differentiation under the Integral Sign",
            questionType = "MCQ",
            prompt = "Apply Leibnitz's rule for differentiation under the integral sign to compute dI/dα where:",
            formulaExpr = "I(α) = ∫₀^∞ (e^(-x) - e^(-αx)) / x dx,   α > 0",
            diagramKind = "NONE",
            options = listOf(
                "dI/dα = 1 / α",
                "dI/dα = -1 / α²",
                "dI/dα = e^(-α)",
                "dI/dα = ln(α)"
            ),
            correctOptionIndex = 0,
            maxMarks = 2,
            modelAnswer = "dI/dα = ∫₀^∞ ∂/∂α [(e^(-x) - e^(-αx))/x] dx = ∫₀^∞ e^(-αx) dx = [-e^(-αx)/α]₀^∞ = 1/α.",
            markingScheme = listOf("2M: Correct differentiation ∂/∂α and exponential evaluation = 1/α")
        ),
        TestQuestion(
            id = "q_math_u4_3",
            subjectId = "sub_100102",
            subjectTitle = "Engineering Mathematics - I",
            unitId = "u_100102_4",
            unitTitle = "Unit 4.0: Integral Calculus",
            topicCode = "4.2",
            topicTitle = "Double Integral in Polar Coordinates",
            questionType = "SUBJECTIVE",
            prompt = "Evaluate the double integral over the first-quadrant circular region R: x² + y² ≤ a² by changing into polar coordinates (x = r cos θ, y = r sin θ). Show all steps clearly.",
            formulaExpr = "∬_R √(a² - x² - y²) dx dy   over x ≥ 0, y ≥ 0, x² + y² ≤ a²",
            diagramKind = "INTEGRAL_AREA",
            options = emptyList(),
            correctOptionIndex = -1,
            maxMarks = 6,
            modelAnswer = "Set x = r cos θ, y = r sin θ, dx dy = r dr dθ. Limits: r ∈ [0, a], θ ∈ [0, π/2]. I = ∫₀^(π/2) dθ ∫₀ᵃ r√(a² - r²) dr. Let u = a² - r², du = -2r dr ⇒ inner integral = [-⅓(a² - r²)^(3/2)]₀ᵃ = a³/3. Multiplying by π/2 gives I = πa³/6.",
            markingScheme = listOf(
                "1M: Correctly substitute x = r cos θ, y = r sin θ with Jacobian dx dy = r dr dθ",
                "1M: State exact polar limits r: 0 → a and θ: 0 → π/2",
                "2M: Evaluate inner radial integral ∫₀ᵃ r√(a² - r²) dr = a³/3 via substitution",
                "2M: Integrate over θ ∈ [0, π/2] to obtain final result πa³/6"
            )
        ),
        // Engineering Physics | Unit 1.0: Wave Optics
        TestQuestion(
            id = "q_phy_u1_1",
            subjectId = "sub_100104",
            subjectTitle = "Engineering Physics",
            unitId = "u_100104_1",
            unitTitle = "Unit 1.0: Wave Optics",
            topicCode = "1.1",
            topicTitle = "Newton's Ring Experiment",
            questionType = "MCQ",
            prompt = "In Newton's Ring experiment with reflected monochromatic light of wavelength λ and plano-convex lens of radius R, the diameter Dₙ of the n-th dark ring satisfies:",
            formulaExpr = "Dₙ² = 4nλR / μ   (for air film, μ = 1)",
            diagramKind = "NEWTON_RINGS",
            options = listOf(
                "Dₙ ∝ √n (proportional to square root of natural numbers)",
                "Dₙ ∝ n (linearly proportional to ring order)",
                "Dₙ ∝ 1/√n (inversely proportional to √n)",
                "Dₙ ∝ n² (proportional to square of ring order)"
            ),
            correctOptionIndex = 0,
            maxMarks = 2,
            modelAnswer = "Since Dₙ² = 4nλR, taking square roots yields Dₙ = 2√(nλR) ⇒ Dₙ ∝ √n.",
            markingScheme = listOf("2M: Identify Dₙ ∝ √n from interference path difference 2t + λ/2 = (2n+1)λ/2")
        ),
        TestQuestion(
            id = "q_phy_u1_2",
            subjectId = "sub_100104",
            subjectTitle = "Engineering Physics",
            unitId = "u_100104_1",
            unitTitle = "Unit 1.0: Wave Optics",
            topicCode = "1.4",
            topicTitle = "Rayleigh Criterion & Resolving Power",
            questionType = "SUBJECTIVE",
            prompt = "State Rayleigh's criterion for just-resolved spectral lines and derive the expression for wavelength λ using diameters Dₘ and Dₘ₊ₚ in Newton's Rings.",
            formulaExpr = "λ = (D²ₘ₊ₚ - D²ₘ) / (4 p R)",
            diagramKind = "NEWTON_RINGS",
            options = emptyList(),
            correctOptionIndex = -1,
            maxMarks = 5,
            modelAnswer = "Rayleigh's criterion: two point sources are just resolved when the central diffraction maximum of one coincides with the first minimum of the other. For Newton's rings, D²ₘ = 4mλR and D²ₘ₊ₚ = 4(m+p)λR. Subtracting eliminates central-spot contact error: D²ₘ₊ₚ - D²ₘ = 4pλR ⇒ λ = (D²ₘ₊ₚ - D²ₘ)/(4pR).",
            markingScheme = listOf(
                "2M: Accurate statement of Rayleigh's resolution criterion",
                "1M: Write D²ₘ = 4mλR and D²ₘ₊ₚ = 4(m+p)λR",
                "2M: Subtract equations and justify elimination of zero/contact error to get λ = (D²ₘ₊ₚ - D²ₘ)/(4pR)"
            )
        ),
        // Engineering Physics | Unit 4.0: Quantum Mechanics
        TestQuestion(
            id = "q_phy_u4_1",
            subjectId = "sub_100104",
            subjectTitle = "Engineering Physics",
            unitId = "u_100104_4",
            unitTitle = "Unit 4.0: Quantum Mechanics",
            topicCode = "4.5",
            topicTitle = "Particle in a 1D Infinite Potential Well",
            questionType = "SUBJECTIVE",
            prompt = "Solve the time-independent Schrödinger wave equation for a particle of mass m trapped in a 1D infinite potential well of width L (0 < x < L) to derive its quantized energy eigenvalues Eₙ and normalized eigenfunction ψₙ(x).",
            formulaExpr = "d²ψ/dx² + (2mE/ℏ²)ψ = 0,   ψ(0) = ψ(L) = 0",
            diagramKind = "QUANTUM_WELL",
            options = emptyList(),
            correctOptionIndex = -1,
            maxMarks = 6,
            modelAnswer = "General solution ψ(x) = A sin(kx) + B cos(kx) where k² = 2mE/ℏ². Boundary ψ(0)=0 ⇒ B=0; ψ(L)=0 ⇒ kL = nπ (n=1,2,3...). Thus Eₙ = n²h²/(8mL²). Normalization ∫₀ᴸ |ψₙ(x)|² dx = 1 ⇒ A²(L/2) = 1 ⇒ A = √(2/L), so ψₙ(x) = √(2/L) sin(nπx/L).",
            markingScheme = listOf(
                "1M: Write Schrödinger equation inside well (V = 0) with k² = 2mE/ℏ²",
                "2M: Apply boundary conditions ψ(0)=0, ψ(L)=0 to obtain k = nπ/L",
                "1M: Derive quantized energy Eₙ = n²h² / (8mL²)",
                "2M: Normalize ∫₀ᴸ |ψₙ(x)|² dx = 1 to obtain ψₙ(x) = √(2/L) sin(nπx/L)"
            )
        ),
        // BEEE | Unit 1.0: Basic Electrical Circuits
        TestQuestion(
            id = "q_beee_u1_1",
            subjectId = "sub_100111",
            subjectTitle = "Basics of Electrical & Electronics Engg",
            unitId = "u_100111_1",
            unitTitle = "Unit 1.0: Basic Electrical Circuits",
            topicCode = "1.6",
            topicTitle = "Star-Delta (Y-Δ) Transformation",
            questionType = "MCQ",
            prompt = "Three equal resistors each of resistance R = 12 Ω are connected in a Delta (Δ) network. What is the equivalent resistance R_Y in each branch of the Star (Y) network?",
            formulaExpr = "R_Y = (R_AB · R_CA) / (R_AB + R_BC + R_CA) = R_Δ / 3",
            diagramKind = "RLC_CIRCUIT",
            options = listOf(
                "4 Ω",
                "36 Ω",
                "6 Ω",
                "12 Ω"
            ),
            correctOptionIndex = 0,
            maxMarks = 2,
            modelAnswer = "For a symmetric Delta-to-Star conversion, R_Y = R_Δ / 3 = 12 / 3 = 4 Ω.",
            markingScheme = listOf("2M: Apply R_Y = R_Δ / 3 = 4 Ω")
        ),
        // Introduction to AI | Unit 2.0: Problem Solving by Search
        TestQuestion(
            id = "q_ai_u2_1",
            subjectId = "sub_100105",
            subjectTitle = "Introduction to AI",
            unitId = "u_100105_2",
            unitTitle = "Unit 2.0: Problem Solving by Search",
            topicCode = "2.6",
            topicTitle = "Alpha-Beta (α-β) Pruning",
            questionType = "MCQ",
            prompt = "In adversarial game tree search using Minimax with Alpha-Beta (α-β) pruning, when does a subtree get pruned immediately?",
            formulaExpr = "Prune condition: α ≥ β   (where α = MAX best, β = MIN best)",
            diagramKind = "LOGIC_GATE",
            options = listOf(
                "Whenever α ≥ β at the current node",
                "Whenever α < β at a leaf node",
                "Only when heuristic h(n) > g(n)",
                "Only when depth limit d = 0"
            ),
            correctOptionIndex = 0,
            maxMarks = 2,
            modelAnswer = "Alpha-Beta pruning cuts off branches whenever the lower bound α of MAX meets or exceeds the upper bound β of MIN (α ≥ β).",
            markingScheme = listOf("2M: Identify cutoff condition α ≥ β")
        )
    )
}
