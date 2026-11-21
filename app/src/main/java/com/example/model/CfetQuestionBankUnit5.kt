package com.example.model

/**
 * CFET Unit 5.0: Emerging Technologies
 * 55 Objective (1M) + 20 Short (5M) + 7 Long (10–12M) = 82 questions across Topics T1–T6.
 */
internal fun cfetUnit5Questions(): List<TestQuestion> {
    val uId = "u_100108_5"
    val uTitle = "Unit 5.0: Emerging Technologies"

    val t1 = "T1. Internet of Things (IoT)"
    val t2 = "T2. Cyber Security & CIA Triad"
    val t3 = "T3. Artificial Intelligence & Agents"
    val t4 = "T4. Machine Learning Paradigms"
    val t5 = "T5. Blockchain & Hashing"
    val t6 = "T6. Data Science Lifecycle"

    fun q(n: Int, tn: Int, tt: String, p: String, opts: List<String>, ans: Int, f: String = "", exp: String = "") =
        CfetQuestionBank.mcq(5, uId, uTitle, n, tn, tt, p, opts, ans, f, "NONE", exp)

    fun s(n: Int, tn: Int, tt: String, p: String, ans: String, sc: List<String>, f: String = "") =
        CfetQuestionBank.shortQ(5, uId, uTitle, n, tn, tt, p, ans, sc, f)

    fun l(n: Int, tn: Int, tt: String, m: Long, p: String, ans: String, sc: List<String>, f: String = "") =
        CfetQuestionBank.longQ(5, uId, uTitle, n, tn, tt, m, p, ans, sc, f)

    return listOf(
        // T1: Q1–Q9, S1–S3, L1
        q(1, 1, t1, "IoT stands for:", listOf("Internet of Things", "Input Output Technology", "Internal Operating Terminal", "Integrated Online Tools"), 0),
        q(2, 1, t1, "In IoT, the component that detects physical changes (temperature, motion, light) is the:", listOf("Actuator", "Sensor", "Router", "Cloud"), 1),
        q(3, 1, t1, "A component that converts a control signal into physical action (e.g., turning on a motor) is a(n):", listOf("Sensor", "Actuator", "Gateway", "Database"), 1),
        q(4, 1, t1, "Which of the following is a lightweight messaging protocol widely used in IoT?", listOf("MQTT", "SMTP", "FTP", "POP3"), 0),
        q(5, 1, t1, "The term 'Internet of Things' was coined by:", listOf("Tim Berners-Lee", "Kevin Ashton", "Vint Cerf", "Alan Turing"), 1),
        q(6, 1, t1, "Which of the following is an example of an IoT device?", listOf("Smart thermostat", "Typewriter", "Analog watch", "Calculator without chip"), 0),
        q(7, 1, t1, "The bottom-most layer of the IoT architecture is the:", listOf("Application layer", "Processing layer", "Network layer", "Perception (sensing) layer"), 3),
        q(8, 1, t1, "Smart street lights, waste management and traffic control are applications of IoT in:", listOf("Smart cities", "Typing", "Compilers", "Printing"), 0),
        q(9, 1, t1, "Which technology helps IoT scale to billions of devices by giving each a unique address?", listOf("IPv6", "FAT32", "BIOS", "ASCII"), 0),
        s(1, 1, t1, "Define IoT. List its main characteristics. ★",
            "IoT is a network of physical objects ('things') embedded with sensors, software & connectivity that collect and exchange data over the Internet with minimal human intervention.\nCharacteristics: Sensing, Connectivity (Wi-Fi/BT/Zigbee/5G/LoRa), Data processing & intelligence (edge/cloud analytics), Automation (actuators), Scalability & unique identity (IPv6, RFID). Examples: smartwatch, smart bulb, connected car, smart meter.",
            listOf("1.5M: Clear IoT definition & examples", "3.5M: Five key characteristics")),
        s(2, 1, t1, "Explain the layered architecture of IoT with a diagram. ★",
            "4-Layer IoT Architecture (bottom to top):\n1. Perception/Sensing Layer: sensors, actuators, RFID, cameras, microcontrollers collect physical data.\n2. Network/Transport Layer: gateways & Wi-Fi/BT/Zigbee/LoRa/5G/MQTT/IPv6 transmit data.\n3. Processing/Middleware Layer: cloud storage, analytics, AI/ML & decision making.\n4. Application Layer: user-facing services (smart home, smart city, healthcare, agriculture).",
            listOf("2M: 4-layer stack diagram", "3M: Role of all 4 layers")),
        s(3, 1, t1, "List applications of IoT in smart homes and smart cities.",
            "Smart Home: smart lighting & thermostats, voice assistants (Alexa), CCTV & smart door locks, energy meters/smart plugs, smart appliances.\nSmart City: smart traffic lights & parking, smart street lighting, waste & water management, air-quality/pollution monitoring, public safety & smart grids.",
            listOf("2.5M: Five Smart Home applications", "2.5M: Five Smart City applications")),
        l(1, 1, t1, 12L, "Explain the concept and architecture of IoT. Describe its applications in smart homes and smart cities and mention the challenges. ★",
            "Concept & working: Sensor → Microcontroller (Arduino/ESP32/RPi) → Gateway/Network → Cloud AI analytics → App/Dashboard → Command back → Actuator (switch/motor/valve).\n4-Layer Architecture: Perception, Network (MQTT/CoAP/HTTP; Wi-Fi/Zigbee/LoRaWAN/NB-IoT/5G), Processing/Middleware, Application.\nApplications: Smart home (lighting, locks, thermostat), Smart city (adaptive traffic, smart parking, waste/water/air monitoring), Healthcare (wearables), Agriculture (soil moisture, drones), Industry IIoT (predictive maintenance), Transport (connected cars, FASTag).\nChallenges: security & privacy, interoperability/standards, battery/power consumption, scalability/big data, cost & reliability.",
            listOf("2.5M: Concept & Sensor→Cloud→Actuator working flow", "3.5M: 4-layer IoT architecture diagram & protocols", "3.5M: Smart home, smart city & domain applications table", "2.5M: Five major IoT challenges & conclusion")),

        // T2: Q10–Q19, S4–S6, L2–L3
        q(10, 2, t2, "CIA triad stands for:", listOf("Confidentiality, Integrity, Availability", "Control, Information, Access", "Cyber, Internet, Authentication", "Confidentiality, Identity, Authorisation"), 0),
        q(11, 2, t2, "Phishing is:", listOf("Fraudulent e-mails/sites tricking users to reveal sensitive information", "A hardware failure", "A type of firewall", "A compression method"), 0),
        q(12, 2, t2, "Malware that encrypts the victim's files and demands payment is:", listOf("Spyware", "Ransomware", "Adware", "Worm"), 1),
        q(13, 2, t2, "Which malware replicates itself across networks without needing a host file?", listOf("Virus", "Worm", "Trojan", "Spyware"), 1),
        q(14, 2, t2, "A DDoS attack aims to violate:", listOf("Confidentiality", "Integrity", "Availability", "Non-repudiation"), 2),
        q(15, 2, t2, "A firewall is used to:", listOf("Filter incoming and outgoing network traffic", "Speed up Internet", "Compress files", "Increase RAM"), 0),
        q(16, 2, t2, "Converting plain text into unreadable cipher text is called:", listOf("Decryption", "Encryption", "Hashing only", "Spoofing"), 1),
        q(17, 2, t2, "A malicious program disguised as legitimate software is a:", listOf("Trojan horse", "Worm", "Firewall", "Patch"), 0),
        q(18, 2, t2, "Two-factor authentication requires:", listOf("Two passwords of same type", "Two different kinds of proof (e.g., password + OTP)", "Two computers", "Two antiviruses"), 1),
        q(19, 2, t2, "Which of the following ensures that data has not been altered?", listOf("Integrity", "Availability", "Authorisation", "Accounting"), 0),
        s(4, 2, t2, "Explain the CIA triad with a diagram. ★",
            "Triangle around DATA with vertices C, I, A:\n1. Confidentiality: only authorized users read data — controls: encryption, passwords, access control, 2FA.\n2. Integrity: data remains accurate & unaltered — controls: hashing, checksums, digital signatures, backups.\n3. Availability: data/services accessible on demand — controls: backups, redundancy, DDoS protection, UPS.",
            listOf("1.5M: CIA triangle diagram", "3.5M: Meaning & security controls for Confidentiality, Integrity & Availability")),
        s(5, 2, t2, "Explain basic cyber security terminologies. ★",
            "Threat (potential danger), Vulnerability (system weakness), Exploit (code/method using vulnerability), Attack (deliberate attempt to harm), Risk (probability × impact), Malware (virus/worm/trojan/ransomware/spyware), Authentication (identity verification), Authorisation (granting permissions), Encryption (keyed ciphertext), Firewall (traffic filtering), Hacker (White/Black/Grey hat).",
            listOf("5M: Clear definitions of at least 8–10 core terms")),
        s(6, 2, t2, "Differentiate between virus, worm and trojan horse.",
            "Virus: attaches to a host file/program, spreads when infected file runs, corrupts/deletes files.\nWorm: standalone self-replicating, spreads automatically across networks, consumes bandwidth/resources.\nTrojan: disguised as useful software, does not self-replicate (user installs it), opens back-door & steals data.",
            listOf("5M: Structured comparison across attachment, propagation & damage")),
        l(2, 2, t2, 12L, "What is cyber security? Explain the CIA triad and the common types of cyber threats with their countermeasures. ★",
            "Cyber security protects systems, networks & data from unauthorized access, attack or theft via CIA Triad (Confidentiality, Integrity, Availability).\nThreats & Countermeasures: Malware (antivirus, patching), Ransomware (offline 3-2-1 backups, updates), Phishing (awareness, URL check, 2FA), DoS/DDoS (firewalls, load balancers, rate limiting, CDN), Man-in-the-Middle (HTTPS/TLS, VPN), SQL Injection (input validation, prepared statements), Social engineering (training), Password attacks (strong passwords, MFA), Insider threat (least privilege, audits). Also IT Act 2000 (India) & defence-in-depth.",
            listOf("2.5M: Definition & CIA triad diagram", "6.5M: Detailed table of 7–9 cyber threats with exact countermeasures", "3M: General defence-in-depth practices, 3-2-1 backup & IT Act 2000")),
        l(3, 2, t2, 10L, "Explain basic security terminologies and the CIA triad with examples.",
            "Terminologies: Asset, Threat, Vulnerability, Risk (likelihood × impact), Attack, Exploit, AAA (Authentication, Authorisation, Accounting), Encryption/Decryption, Hashing, Digital signature, Firewall, VPN, IDS, Non-repudiation.\nCIA Triad with banking examples: Confidentiality (balance visible only to owner → encryption/access control; broken by eavesdropping), Integrity (transfer amount unchanged in transit → hash/digital signature; broken by tampering), Availability (net-banking up 24×7 → redundant servers/DDoS protection; broken by DDoS).",
            listOf("4.5M: Core security terms including AAA & non-repudiation", "2M: CIA triad diagram", "3.5M: Concrete banking examples & pillar-wise attack mapping")),

        // T3: Q20–Q28, S7–S9, L4
        q(20, 3, t3, "Artificial Intelligence is the ability of machines to:", listOf("Store large data only", "Simulate human intelligence such as learning, reasoning and problem solving", "Print documents", "Transfer files"), 1),
        q(21, 3, t3, "The term 'Artificial Intelligence' was coined by:", listOf("Alan Turing", "John McCarthy", "Charles Babbage", "Tim Berners-Lee"), 1, exp = "At the Dartmouth Conference, 1956."),
        q(22, 3, t3, "The test proposed by Alan Turing to check machine intelligence is called:", listOf("Turing test", "Bell test", "Beta test", "Stress test"), 0),
        q(23, 3, t3, "An intelligent agent perceives its environment through ______ and acts through ______.", listOf("Actuators, sensors", "Sensors, actuators", "Memory, CPU", "Input, ROM"), 1),
        q(24, 3, t3, "Which of the following is NOT a type of AI agent?", listOf("Simple reflex agent", "Goal-based agent", "Utility-based agent", "Compiler agent"), 3),
        q(25, 3, t3, "Siri, Alexa and Google Assistant are examples of:", listOf("Virtual assistants (AI applications)", "Compilers", "Firewalls", "Operating systems"), 0),
        q(26, 3, t3, "AI that performs a single specific task (e.g., chess, spam filter) is:", listOf("Narrow (weak) AI", "General AI", "Super AI", "None"), 0),
        q(27, 3, t3, "NLP in AI stands for:", listOf("Natural Language Processing", "Network Layer Protocol", "Neural Logic Programming", "New Logic Programming"), 0),
        q(28, 3, t3, "Computer vision enables computers to:", listOf("Understand images and videos", "Print documents", "Compile code", "Increase RAM"), 0),
        s(7, 3, t3, "Define Artificial Intelligence. Explain its types and goals. ★",
            "AI builds machines/software capable of human-like intelligence (learning, reasoning, perception, language, decision making).\nTypes: Narrow/Weak AI (single task — spam filter, chess, Siri), General/Strong AI (human-level across tasks — not yet achieved), Super AI (surpasses human intelligence — theoretical).\nGoals: automate tasks, improve accuracy/decisions, handle huge data, assist humans.",
            listOf("1.5M: Definition", "2.5M: Narrow, General & Super AI comparison", "1M: Key goals")),
        s(8, 3, t3, "What is an AI agent? Explain the types of agents. ★",
            "An agent perceives its environment via sensors and acts via actuators; a rational agent maximizes performance measure.\n5 types: (1) Simple reflex (condition-action rules — thermostat), (2) Model-based reflex (internal world state — lane keeping), (3) Goal-based (plans toward goal — GPS route planner), (4) Utility-based (maximizes utility/cost — ride-sharing optimization), (5) Learning agent (improves from experience — recommendation engine).",
            listOf("1.5M: Agent–environment diagram (Percepts/Sensors & Actions/Actuators)", "3.5M: All 5 agent types with examples")),
        s(9, 3, t3, "List the applications of AI.",
            "Healthcare (X-ray/MRI diagnosis, drug discovery), Finance (fraud detection, credit scoring), Transport (self-driving cars, traffic prediction), Education (intelligent tutors, auto-grading), Retail/Entertainment (Amazon/Netflix recommendations), NLP (chatbots, translation, generative AI), Agriculture, Robotics, Cyber security & Smart cities.",
            listOf("5M: At least 6 distinct domains with concrete examples")),
        l(4, 3, t3, 12L, "Explain Artificial Intelligence, AI agents and the major applications of AI. ★",
            "1. Intro: John McCarthy (1956), Turing Test (1950).\n2. Branches: ML & Deep Learning, NLP, Computer Vision, Expert Systems, Robotics, Speech recognition, Generative AI (LLMs).\n3. AI Agents & PEAS (Performance, Environment, Actuators, Sensors): Agent–Environment loop diagram + 5 agent types (Simple reflex, Model-based, Goal-based, Utility-based, Learning). PEAS example — self-driving taxi: P=safe/fast trip, E=roads/traffic, A=steering/brake/accel, S=camera/GPS/lidar.\n4. Applications: Healthcare, Banking, Transport, Education, E-commerce, Agriculture, Security.\n5. Benefits (speed, 24×7, big data) & Concerns (bias, job shift, privacy, deepfakes).",
            listOf("2M: Intro (McCarthy/Turing) & branches of AI", "4.5M: Agent–Environment diagram, 5 agent types & PEAS self-driving example", "3.5M: Sector-wise applications table", "2M: Benefits & ethical concerns")),

        // T4: Q29–Q37, S10–S13, L5
        q(29, 4, t4, "Machine Learning is a subset of:", listOf("Artificial Intelligence", "Operating systems", "Networking", "Databases only"), 0),
        q(30, 4, t4, "Supervised learning uses:", listOf("Unlabelled data", "Labelled data (input with known output)", "Only rewards", "No data"), 1),
        q(31, 4, t4, "Clustering of customers into groups without predefined labels is an example of:", listOf("Supervised learning", "Unsupervised learning", "Reinforcement learning", "Rule-based system"), 1),
        q(32, 4, t4, "In reinforcement learning an agent learns by:", listOf("Labelled examples", "Rewards and penalties from interaction with environment", "Clustering", "Memorising"), 1),
        q(33, 4, t4, "Predicting house prices (a continuous value) is a ______ problem.", listOf("Classification", "Regression", "Clustering", "Association"), 1),
        q(34, 4, t4, "Classifying e-mails as spam or not-spam is a ______ problem.", listOf("Classification", "Regression", "Clustering", "Compression"), 0),
        q(35, 4, t4, "Which of the following is an unsupervised learning algorithm?", listOf("Linear regression", "K-means clustering", "Decision tree classifier", "Logistic regression"), 1),
        q(36, 4, t4, "When a model fits the training data too well but performs poorly on new data, it is called:", listOf("Underfitting", "Overfitting", "Good fit", "Normalisation"), 1),
        q(37, 4, t4, "Netflix/YouTube suggesting videos is an application of:", listOf("Recommendation systems (ML)", "Compilers", "Firewalls", "Hubs"), 0),
        s(10, 4, t4, "Explain the types of machine learning (learning paradigms). ★",
            "1. Supervised (labelled data; classification/regression; Linear/Logistic Reg, Decision Tree, SVM, KNN, Random Forest — spam detection, price prediction).\n2. Unsupervised (unlabelled data; hidden patterns; K-means, Hierarchical, PCA, Apriori — customer segmentation).\n3. Semi-supervised (few labelled + many unlabelled; self-training — image tagging).\n4. Reinforcement (reward signal via trial & error; Q-learning, DQN — game playing, robotics).",
            listOf("1.25M each for Supervised, Unsupervised, Semi-supervised & Reinforcement with algorithms & examples")),
        s(11, 4, t4, "Differentiate between supervised and unsupervised learning. ★",
            "Supervised: labelled data, known target output, classification & regression tasks, higher accuracy/needs labelling (email spam, loan default).\nUnsupervised: unlabelled data, discovers hidden structure, clustering/association/dimensionality reduction, harder to evaluate (market-basket analysis).",
            listOf("2.5M: Data, output & task differences", "2.5M: Evaluation/accuracy & examples")),
        s(12, 4, t4, "List real-world applications of machine learning.",
            "Spam filtering & banking fraud detection; Product/video recommendations (Amazon, Netflix); Speech recognition & translation; Face recognition & medical image diagnosis; Self-driving cars & predictive maintenance; Stock/weather forecasting & sentiment analysis.",
            listOf("5M: Six concrete ML applications")),
        s(13, 4, t4, "Differentiate between overfitting and underfitting.",
            "Overfitting: model too complex & memorizes noise; high training accuracy, low test accuracy; fix via more data, regularization, simpler model, cross-validation.\nUnderfitting: model too simple & misses true pattern; low accuracy on both train & test; fix via more features, complex model, longer training.",
            listOf("2.5M: Cause & train/test accuracy behavior", "2.5M: Remedies for both")),
        l(5, 4, t4, 12L, "Explain machine learning and its types of learning paradigms with algorithms and real-world applications. ★",
            "ML (Arthur Samuel, 1959) learns patterns from data without explicit programming (AI ⊃ ML ⊃ Deep Learning).\nWorkflow: Collect → Pre-process → Split (train/test) → Train → Evaluate (accuracy/precision/recall) → Tune → Deploy.\nParadigms: (1) Supervised (Classification: Logistic Reg, Tree, SVM, KNN, Naïve Bayes; Regression: Linear Reg). (2) Unsupervised (K-means, Apriori, PCA). (3) Semi-supervised (medical imaging). (4) Reinforcement (agent, environment, reward/penalty; Q-learning, AlphaGo, robotics).\nApplications: Healthcare, Finance, E-commerce/OTT, Transport, Agriculture, NLP. Issues: overfitting/underfitting, bias, privacy, compute cost.",
            listOf("2.5M: Definition, AI⊃ML⊃DL & ML workflow pipeline", "5M: All 4 learning paradigms with algorithms & summary table", "3M: Real-world applications across 6 sectors", "1.5M: Key ML challenges")),

        // T5: Q38–Q46, S14–S17, L6
        q(38, 5, t5, "A blockchain is basically a:", listOf("Centralised database", "Distributed, tamper-resistant digital ledger", "Programming language", "Type of firewall"), 1),
        q(39, 5, t5, "Bitcoin, the first blockchain-based cryptocurrency, was introduced by:", listOf("Vitalik Buterin", "Satoshi Nakamoto", "Elon Musk", "Bill Gates"), 1),
        q(40, 5, t5, "Which of the following is stored in each block?", listOf("Data/transactions, its hash and the previous block's hash", "Only the user's password", "Only a picture", "Source code of OS"), 0),
        q(41, 5, t5, "The first block of a blockchain is called the:", listOf("Genesis block", "Root block", "Master block", "Base block"), 0),
        q(42, 5, t5, "SHA-256 produces a hash of:", listOf("64 bits", "128 bits", "256 bits", "512 bits"), 2),
        q(43, 5, t5, "If the data in a block is changed, its hash:", listOf("Remains the same", "Changes completely", "Becomes zero", "Is deleted"), 1),
        q(44, 5, t5, "A blockchain has ______ control.", listOf("Central", "Decentralised", "Single-company", "No"), 1),
        q(45, 5, t5, "Which of the following is a consensus mechanism?", listOf("Proof of Work", "Proof of Delivery", "Proof of Print", "Proof of Boot"), 0),
        q(46, 5, t5, "A self-executing contract whose terms are written in code on a blockchain is a:", listOf("Smart contract", "Paper contract", "Firewall rule", "Patent"), 0),
        s(14, 5, t5, "Define blockchain. State its key features. ★",
            "Blockchain is a distributed, append-only ledger grouping transactions into cryptographically linked blocks replicated across nodes.\nKey features: Decentralised (no central authority), Immutable (cannot alter past blocks), Transparent (verifiable by participants), Secure (hashing + consensus), Distributed (every node holds a copy).",
            listOf("1.5M: Definition", "3.5M: Five key features")),
        s(15, 5, t5, "Explain the structure of a block and how blocks are chained. ★",
            "Block structure: Header (previous block hash, timestamp, nonce, Merkle root, difficulty), Body (list of transactions), Hash (unique SHA-256 fingerprint).\nChaining: Block 1 (Prev: 0000, Hash: H1) → Block 2 (Prev: H1, Hash: H2) → Block N (Prev: H_{N-1}, Hash: H_N). Altering any block changes its hash and invalidates all subsequent links, exposing tampering.",
            listOf("2M: Chain-of-blocks diagram (Prev Hash → Hash link)", "2M: Header, Body & Hash components", "1M: Tamper-detection mechanism")),
        s(16, 5, t5, "What is hashing? State the properties of a cryptographic hash function.",
            "A hash function maps arbitrary-size input to a fixed-size digest (e.g., SHA-256 outputs 256 bits / 64 hex chars).\n5 properties: Deterministic (same input → same hash), Fixed output length, One-way / pre-image resistant, Avalanche effect (tiny input tweak → completely different hash), Collision resistant.",
            listOf("1.5M: Definition & SHA-256 example", "3.5M: All 5 cryptographic properties")),
        s(17, 5, t5, "Differentiate between centralised and decentralised systems.",
            "Centralised: single authority controls data, single point of failure, less transparent, faster/easier to manage (bank database).\nDecentralised (blockchain): control shared across nodes, no SPOF, transparent & verifiable, slower due to consensus (Bitcoin network).",
            listOf("5M: Five comparison criteria with examples")),
        l(6, 5, t5, 12L, "Explain blockchain technology: distributed ledger, blocks, hashing and decentralisation. How is a transaction added to the blockchain? ★",
            "Blockchain (Satoshi Nakamoto, 2008–09): shared immutable P2P ledger.\n1. Distributed ledger: every node keeps identical copy updated via consensus.\n2. Blocks & Chain: header (prev hash, timestamp, nonce, Merkle root) + transactions; first block = Genesis block.\n3. Hashing: SHA-256 creates immutability via hash linking.\n4. Decentralisation & Consensus: PoW (Bitcoin miners find nonce), PoS (Ethereum staked validators), >51% majority agreement.\n5. Adding a transaction (6 steps): User signs with private key → broadcast to P2P network → nodes validate into pool → miners/validators form block & run consensus → winner broadcasts, peers verify & append → permanent confirmation.\nTypes: Public, Private, Consortium, Hybrid. Apps: crypto, supply-chain, land/health records, smart contracts, voting. Limits: throughput, PoW energy, storage growth.",
            listOf("2.5M: Distributed ledger & block-chain diagram (Genesis + prev hash)", "2.5M: SHA-256 hashing & PoW/PoS consensus", "4M: 6-step transaction addition lifecycle", "3M: Types, applications & limitations")),

        // T6: Q47–Q55, S18–S20, L7
        q(47, 6, t6, "Data Science is the field that:", listOf("Extracts knowledge and insights from data using statistics, programming and domain knowledge", "Designs hardware", "Builds only websites", "Repairs computers"), 0),
        q(48, 6, t6, "The first step of the data science lifecycle is usually:", listOf("Deployment", "Problem/business understanding", "Visualisation", "Model tuning"), 1),
        q(49, 6, t6, "Removing duplicates and handling missing values is called:", listOf("Data cleaning (pre-processing)", "Data hiding", "Encryption", "Compilation"), 0),
        q(50, 6, t6, "EDA stands for:", listOf("Exploratory Data Analysis", "External Data Access", "Estimated Data Average", "Electronic Data Array"), 0),
        q(51, 6, t6, "Which of the following is NOT one of the 5 V's of big data?", listOf("Volume", "Velocity", "Variety", "Visibility"), 3, exp = "5 Vs: Volume, Velocity, Variety, Veracity, Value."),
        q(52, 6, t6, "Which programming language is most popular in data science?", listOf("Python", "COBOL", "Assembly", "Fortran only"), 0),
        q(53, 6, t6, "Predicting future outcomes using historical data is:", listOf("Descriptive analytics", "Predictive analytics", "Diagnostic only", "None"), 1),
        q(54, 6, t6, "Charts, dashboards and graphs are tools of:", listOf("Data visualisation", "Data encryption", "Booting", "Paging"), 0),
        q(55, 6, t6, "Data-driven decision making means decisions based on:", listOf("Intuition only", "Analysis of data and evidence", "Guesswork", "Rumours"), 1),
        s(18, 6, t6, "Explain the data science lifecycle with a diagram. ★",
            "7 iterative stages (with feedback loop back to Step 1):\n1. Problem Definition (business goal), 2. Data Collection (DBs, sensors, web, surveys), 3. Data Cleaning (missing values, duplicates, outliers), 4. Exploratory Data Analysis / EDA (stats & visualization), 5. Model Building (ML/statistical algorithms), 6. Evaluation & Tuning (accuracy, precision, recall), 7. Deployment & Monitoring (production tracking).",
            listOf("1.5M: 7-stage lifecycle flow diagram with feedback arrow", "3.5M: Concise explanation of all 7 stages")),
        s(19, 6, t6, "Explain the 5 V's of big data.",
            "1. Volume: huge data scale (petabytes of social-media data).\n2. Velocity: high generation/processing speed (stock ticks, streaming).\n3. Variety: structured, semi-structured & unstructured (text, images, logs, video).\n4. Veracity: quality & trustworthiness (noisy sensor data).\n5. Value: actionable insights driving business decisions.",
            listOf("1M each for Volume, Velocity, Variety, Veracity & Value with examples")),
        s(20, 6, t6, "What is the role of data in decision making? Explain the types of analytics. ★",
            "Data replaces guesswork with evidence for faster, less biased decisions.\n4 Analytics types:\n1. Descriptive — What happened? (monthly sales report).\n2. Diagnostic — Why did it happen? (reason for sales drop).\n3. Predictive — What will happen? (next-month demand forecast).\n4. Prescriptive — What should we do? (optimal price/stock plan).",
            listOf("1M: Role of evidence-based decision making", "4M: Descriptive, Diagnostic, Predictive & Prescriptive analytics with examples")),
        l(7, 6, t6, 12L, "Explain the data science lifecycle in detail and discuss the role of data in decision making with examples. ★",
            "Data Science merges statistics, programming, ML & domain expertise.\nLifecycle phases & tools: (1) Problem definition (KPIs), (2) Data collection (SQL, APIs, Scrapy), (3) Data cleaning (Pandas, Excel), (4) EDA (Matplotlib, Seaborn, Tableau), (5) Modelling (Scikit-learn, TensorFlow), (6) Evaluation (accuracy, precision, recall, RMSE, cross-validation), (7) Deployment & monitoring (APIs, cloud, MLOps) + feedback loop.\nRole in decision making: evidence-based, trend discovery, forecasting, cost optimization, personalization, risk/fraud management.\nSector examples: Retail (stock planning — Amazon/Flipkart), Healthcare (outbreak prediction), Banking (credit-risk loan approval), Government (smart cities), Agriculture (weather/soil crop choice), Sports (strategy). Challenges: data quality, privacy/ethics, bias, skills.",
            listOf("2M: Definition & 7-stage lifecycle diagram", "4M: Detailed phase activities & tools table", "3M: Role of data in decision making (6 benefits)", "3M: Sector examples table, challenges & conclusion"))
    )
}
