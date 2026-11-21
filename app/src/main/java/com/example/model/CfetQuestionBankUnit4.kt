package com.example.model

/**
 * CFET Unit 4.0: Computer Networks and Internet Basics
 * 74 Objective (1M) + 24 Short (5M) + 12 Long (10–12M) = 110 questions across Topics T1–T9.
 */
internal fun cfetUnit4Questions(): List<TestQuestion> {
    val uId = "u_100108_4"
    val uTitle = "Unit 4.0: Computer Networks & Internet Basics"

    val t1 = "T1. Intro to Computer Networks"
    val t2 = "T2. Importance of Networking"
    val t3 = "T3. Data Transmission Media"
    val t4 = "T4. Modes, Switching & Performance"
    val t5 = "T5. Network Types (LAN, WAN, MAN)"
    val t6 = "T6. Topologies & Protocols"
    val t7 = "T7. Network Devices & Wireless"
    val t8 = "T8. Internet & IP Addressing"
    val t9 = "T9. TCP/UDP, HTTP, FTP, DNS & Client-Server"

    fun q(n: Int, tn: Int, tt: String, p: String, opts: List<String>, ans: Int, f: String = "", exp: String = "") =
        CfetQuestionBank.mcq(4, uId, uTitle, n, tn, tt, p, opts, ans, f, "NONE", exp)

    fun s(n: Int, tn: Int, tt: String, p: String, ans: String, sc: List<String>, f: String = "") =
        CfetQuestionBank.shortQ(4, uId, uTitle, n, tn, tt, p, ans, sc, f)

    fun l(n: Int, tn: Int, tt: String, m: Long, p: String, ans: String, sc: List<String>, f: String = "") =
        CfetQuestionBank.longQ(4, uId, uTitle, n, tn, tt, m, p, ans, sc, f)

    return listOf(
        // T1: Q1–Q7, S1–S2, L1
        q(1, 1, t1, "A computer network is:", listOf("A single computer with many users", "A set of interconnected computers/devices that can share data and resources", "A software only", "A type of memory"), 1),
        q(2, 1, t1, "Each device connected to a network (computer, printer, etc.) is called a:", listOf("Link", "Node", "Packet", "Protocol"), 1),
        q(3, 1, t1, "A set of rules governing communication between devices is a:", listOf("Topology", "Protocol", "Gateway", "Node"), 1),
        q(4, 1, t1, "The first operational packet-switching network was:", listOf("ARPANET", "Ethernet", "NSFNET", "Intranet"), 0),
        q(5, 1, t1, "The Internet is best described as:", listOf("A single LAN", "A network of networks", "A web browser", "A search engine"), 1),
        q(6, 1, t1, "Data transfer speed of a network is measured in:", listOf("bps (bits per second)", "Hertz", "Watts", "Pixels"), 0),
        q(7, 1, t1, "Which of the following is NOT a basic component of a network?", listOf("Sender/receiver nodes", "Transmission medium", "Protocols", "Compiler"), 3),
        s(1, 1, t1, "Define computer network. Explain its basic components. ★",
            "A computer network connects two or more devices via links to exchange data and share resources.\nComponents: Nodes/hosts (PC, server, printer, phone), Transmission medium (wired UTP/fibre or wireless radio), Network devices (hub, switch, router, modem, NIC), Protocols (TCP/IP, HTTP, Ethernet), Network software/OS. Data communication elements: Message, Sender, Medium, Receiver, Protocol.",
            listOf("1.5M: Clear definition", "3.5M: Five basic components & communication model")),
        s(2, 1, t1, "Write a short note on the history and evolution of computer networks.",
            "1969: ARPANET (US DoD) connects 4 universities via packet switching. 1973–83: TCP/IP developed and adopted on 1 Jan 1983. 1983: Ethernet (IEEE 802.3) drives LAN growth. 1989–91: WWW invented by Tim Berners-Lee at CERN. 1990s: Commercial Internet, ISPs, browsers. 2000s–now: broadband, Wi-Fi, 4G/5G, cloud & IoT.",
            listOf("5M: Key chronological milestones from ARPANET (1969) to modern 5G/Cloud/IoT")),
        l(1, 1, t1, 10L, "What is a computer network? Explain its components, working and classification briefly.",
            "Interconnection of autonomous devices to share data & resources.\nComponents: End devices, transmission media, connecting devices (NIC/switch/router/AP), protocols (TCP/IP), network OS.\nWorking: Sender app → split into packets → IP/MAC addressing → medium & switch/router forwarding → reassembled at receiver → delivered to app.\nClassification: By area (PAN, LAN, MAN, WAN), Topology (Bus, Star, Ring, Mesh, Hybrid), Architecture (Client-server, P2P), Medium (Wired, Wireless).",
            listOf("2M: Definition & 5 components", "3M: Step-by-step packet transmission flow", "4M: Classification by area, topology, architecture & medium", "1M: Conclusion")),

        // T2: Q8–Q13, S3–S4, L2
        q(8, 2, t2, "Which of the following is an advantage of networking?", listOf("Resource sharing", "Hardware failure", "Virus-proof systems", "No need of passwords"), 0),
        q(9, 2, t2, "Sharing a single printer among many computers is an example of:", listOf("Hardware resource sharing", "Software piracy", "Overclocking", "Booting"), 0),
        q(10, 2, t2, "Which is a disadvantage of networking?", listOf("Fast communication", "Security risks / hacking", "Centralised backup", "Cost saving"), 1),
        q(11, 2, t2, "E-mail, video-conferencing and chat are applications of networks for:", listOf("Communication", "Printing", "Compilation", "Storage only"), 0),
        q(12, 2, t2, "Centralised data storage on a server helps in:", listOf("Easy backup and data security management", "Slowing the network", "Removing the need for users", "Deleting data"), 0),
        q(13, 2, t2, "Which of the following is an application of networking?", listOf("Online banking", "ATM", "E-commerce", "All of these"), 3),
        s(3, 2, t2, "Explain the advantages and disadvantages of computer networking. ★",
            "Advantages: Resource sharing (printers, files, Internet), fast communication (email/video), centralized data & backup, cost saving, reliability via alternate paths, scalability.\nDisadvantages: Initial setup/maintenance cost, security threats (viruses, hacking), server/backbone failure affects all, skilled admin needed, privacy concerns, congestion under load.",
            listOf("2.5M: Five advantages", "2.5M: Five disadvantages")),
        s(4, 2, t2, "List any five applications of computer networks.",
            "1. E-mail, messaging & video-conferencing. 2. Online banking, UPI & e-commerce. 3. E-learning & digital libraries (SWAYAM, NPTEL). 4. Railway/airline reservation & e-governance (DigiLocker). 5. Cloud computing, remote work, IoT & smart homes. 6. Social media & entertainment streaming.",
            listOf("1M each for any 5 well-explained applications")),
        l(2, 2, t2, 10L, "Discuss the importance and applications of networking in modern life.",
            "Importance: resource sharing, communication (VoIP/email/video), collaboration (cloud drives), centralized management/backup, cost effectiveness, reliability, scalability.\nApplications across sectors: Education (online classes, NPTEL), Banking (net-banking, ATM, UPI), Business (e-commerce, ERP), Healthcare (tele-medicine, e-records), Government (Aadhaar, DigiLocker), Entertainment (OTT streaming, gaming).\nChallenges: security, privacy, cost, bandwidth limits & skilled administration.",
            listOf("4M: Seven benefits/importance points", "4M: Sector-wise applications table (6 sectors)", "2M: Key challenges & summary")),

        // T3: Q14–Q21, S5–S7, L3
        q(14, 3, t3, "Which of the following is a guided (wired) transmission medium?", listOf("Microwave", "Optical fibre", "Infrared", "Satellite"), 1),
        q(15, 3, t3, "Which transmission medium uses light pulses to carry data?", listOf("Twisted pair", "Coaxial cable", "Optical fibre", "Radio waves"), 2),
        q(16, 3, t3, "The connector used with UTP Ethernet cables is:", listOf("BNC", "RJ-45", "RJ-11", "SC"), 1),
        q(17, 3, t3, "Which medium is immune to electromagnetic interference?", listOf("UTP", "Coaxial", "Optical fibre", "Microwave"), 2),
        q(18, 3, t3, "Cable TV commonly uses which cable?", listOf("UTP", "Coaxial cable", "Fibre only", "Telephone wire"), 1),
        q(19, 3, t3, "Which of the following is an unguided medium?", listOf("Coaxial", "Twisted pair", "Radio waves", "Optical fibre"), 2),
        q(20, 3, t3, "Maximum segment length of a standard UTP Ethernet cable is:", listOf("10 m", "100 m", "500 m", "1 km"), 1),
        q(21, 3, t3, "The principle behind optical fibre transmission is:", listOf("Total internal reflection", "Diffusion", "Induction", "Refraction only"), 0),
        s(5, 3, t3, "Compare twisted pair, coaxial and optical fibre cables. ★",
            "Twisted pair: electrical, up to 10 Gbps (Cat6a), low EMI immunity (STP better), 100 m, low cost, LAN/telephone.\nCoaxial: electrical, ~1 Gbps, medium EMI immunity, ~500 m, medium cost, Cable TV/early Ethernet.\nOptical fibre: light, Tbps range, immune to EMI, many km, high cost, backbone/ISP links.",
            listOf("2.5M: Signal type, speed & EMI immunity", "2.5M: Max distance, cost & typical uses")),
        s(6, 3, t3, "Explain the structure of an optical fibre cable and single-mode vs multi-mode fibre.",
            "Structure: Core (thin glass/plastic carrying light), Cladding (lower refractive index causing total internal reflection), Buffer/jacket (protective outer coating).\nSingle-mode: ~9 µm core, laser source, tens of km, higher cost.\nMulti-mode: 50–62.5 µm core, LED source, up to ~2 km, lower cost.",
            listOf("2M: Core, Cladding (TIR) & Buffer jacket", "3M: Single-mode vs Multi-mode comparison")),
        s(7, 3, t3, "Explain unguided (wireless) transmission media.",
            "Radio waves (3 kHz–1 GHz): omnidirectional, pass through walls — FM radio, Wi-Fi, Bluetooth, cellular.\nMicrowaves (1–300 GHz): directional line-of-sight dish antennas — satellite & point-to-point links.\nInfrared (300 GHz–400 THz): short range, line-of-sight, blocked by walls — TV remote controls.",
            listOf("2M: Radio waves", "1.5M: Microwaves", "1.5M: Infrared with frequency bands & uses")),
        l(3, 3, t3, 12L, "Explain the different data transmission media (guided and unguided) with their advantages and disadvantages. ★",
            "A. Guided (wired): (1) Twisted pair (UTP/STP, twisted to reduce crosstalk; cheap, 100 m limit). (2) Coaxial cable (copper core, insulator, shield, jacket; better EMI/bandwidth, bulky; Cable TV/CCTV). (3) Optical fibre (glass core+cladding via TIR; Tbps speed, long distance, EMI immune, secure; costly/fragile).\nB. Unguided (wireless): Radio waves (omnidirectional, Wi-Fi/BT/mobile), Microwaves (line-of-sight towers, telecom backbone), Infrared (short-range remotes), Satellite (wide coverage, high latency; DTH/GPS/VSAT). Pros: mobility, no cabling. Cons: interference, weather, lower security. Selection: cost, speed, distance, security, EMI.",
            listOf("2M: Classification tree (Guided vs Unguided)", "4.5M: Detailed guided media with pros/cons", "4.5M: Detailed unguided media table with pros/cons", "1M: Media selection criteria")),

        // T4: Q22–Q29, S8–S10, L4
        q(22, 4, t4, "Transmission in which data flows only in one direction is:", listOf("Simplex", "Half duplex", "Full duplex", "Multiplex"), 0),
        q(23, 4, t4, "A walkie-talkie is an example of:", listOf("Simplex", "Half duplex", "Full duplex", "Broadcast"), 1),
        q(24, 4, t4, "A telephone conversation is an example of:", listOf("Simplex", "Half duplex", "Full duplex", "None"), 2),
        q(25, 4, t4, "In packet switching, the message is:", listOf("Sent as one continuous stream", "Divided into small packets routed independently", "Not divided at all", "Stored on tape"), 1),
        q(26, 4, t4, "The traditional telephone network (PSTN) uses:", listOf("Packet switching", "Circuit switching", "Message switching", "None"), 1),
        q(27, 4, t4, "In serial transmission data bits are sent:", listOf("All together on separate lines", "One after another over a single line", "Only in bytes", "Only by wireless"), 1),
        q(28, 4, t4, "The maximum data rate of a channel is called its:", listOf("Latency", "Bandwidth", "Jitter", "Protocol"), 1),
        q(29, 4, t4, "Time taken by a packet to travel from source to destination is:", listOf("Throughput", "Latency (delay)", "Bandwidth", "Jitter"), 1),
        s(8, 4, t4, "Explain the transmission modes: simplex, half duplex and full duplex. ★",
            "Simplex (A → B): one-way only (keyboard → PC, TV/radio broadcast).\nHalf duplex (A ↔ B alternately): both ways, one at a time (walkie-talkie, CB radio).\nFull duplex (A ⇔ B simultaneously): both ways at once (telephone, mobile call, Ethernet switch).",
            listOf("3M: Definitions & direction diagrams for all 3 modes", "2M: Real-world examples for each")),
        s(9, 4, t4, "Differentiate between circuit switching and packet switching. ★",
            "Circuit switching: dedicated path set up first, bandwidth wasted when idle, constant delay after setup, whole call drops if link fails (PSTN telephone).\nPacket switching: no dedicated path (packets routed independently), shared efficient bandwidth, variable delay, packets rerouted on failure (Internet/IP).",
            listOf("2.5M: Path allocation & bandwidth efficiency", "2.5M: Delay, fault tolerance & examples")),
        s(10, 4, t4, "Differentiate between serial and parallel transmission, and synchronous and asynchronous transmission.",
            "Serial vs Parallel: Serial sends bits one by one on 1 wire (cheaper, long distance — USB, Ethernet); Parallel sends multiple bits simultaneously on many wires (faster short distance — old printer port).\nSynchronous vs Asynchronous: Synchronous sends clock-synchronized blocks (high speed, efficient); Asynchronous uses start/stop bits per character (simpler, higher overhead).",
            listOf("2.5M: Serial vs Parallel comparison", "2.5M: Synchronous vs Asynchronous comparison")),
        l(4, 4, t4, 12L, "Explain data transmission in a network: transmission modes, serial/parallel and synchronous/asynchronous transmission and switching techniques.",
            "1. Modes: Simplex, Half duplex, Full duplex.\n2. Serial (USB/SATA/Ethernet) vs Parallel (internal buses/old printer).\n3. Asynchronous (start/stop bits, irregular gaps) vs Synchronous (common clock, continuous block).\n4. Switching: Circuit switching (setup→transfer→release), Packet switching (datagram/virtual circuit, independent routing & reassembly), Message switching (store-and-forward hop-by-hop).\n5. Performance metrics: Bandwidth (max capacity), Throughput (actual rate), Latency (delay), Jitter (delay variation), Error rate. Multiplexing (FDM, TDM).",
            listOf("2M: Transmission modes", "2.5M: Serial/Parallel & Sync/Async", "4M: Circuit, Packet & Message switching", "3.5M: 5 performance measures & multiplexing")),

        // T5: Q30–Q37, S11, L5
        q(30, 5, t5, "A network covering a single building or campus is a:", listOf("LAN", "MAN", "WAN", "Internet"), 0),
        q(31, 5, t5, "A network spread over a city is called:", listOf("PAN", "LAN", "MAN", "WAN"), 2),
        q(32, 5, t5, "The Internet is the largest example of a:", listOf("LAN", "MAN", "WAN", "PAN"), 2),
        q(33, 5, t5, "Ethernet is mainly a ______ technology.", listOf("LAN", "WAN", "MAN only", "Satellite"), 0),
        q(34, 5, t5, "A network of Bluetooth devices around a person (~10 m) is a:", listOf("PAN", "MAN", "WAN", "VPN"), 0),
        q(35, 5, t5, "Which network generally has the highest data rate and lowest delay?", listOf("WAN", "MAN", "LAN", "Satellite"), 2),
        q(36, 5, t5, "Cable TV network in a city is an example of:", listOf("LAN", "MAN", "PAN", "None"), 1),
        q(37, 5, t5, "Which technology is commonly used in WANs?", listOf("Leased lines, MPLS, satellite", "Hubs only", "Bluetooth", "USB"), 0),
        s(11, 5, t5, "Differentiate between LAN, MAN and WAN. ★",
            "LAN: ≤1 km (room/campus), 100 Mbps–10 Gbps, private ownership, low cost & error rate, Ethernet/Wi-Fi (college lab).\nMAN: 5–50 km (city), moderate speed/cost/errors, private/public, FDDI/cable/fibre (Cable TV, city Wi-Fi).\nWAN: country/world, lower speed, many ISPs/govt, high cost & error rate, leased line/MPLS/satellite (Internet).",
            listOf("2.5M: Coverage area, speed & ownership", "2.5M: Cost, error rate, technologies & examples")),
        l(5, 5, t5, 10L, "Explain the types of networks – PAN, LAN, MAN and WAN – with their characteristics, advantages and examples.",
            "1. PAN (~10 m): Bluetooth headset, USB tethering.\n2. LAN (≤1 km): lab/office/campus, Ethernet/Wi-Fi star topology. Adv: high speed, low cost, private. Disadv: limited area.\n3. MAN (~50 km): city-wide interconnecting LANs via fibre/microwave (Cable TV, bank branches).\n4. WAN (Global): spans continents via leased lines, satellites, undersea cables (Internet). Adv: global reach. Disadv: costly, slower, security risk. Also: VPN (secure tunnel), CAN (campus), SAN (storage).",
            listOf("1.5M: PAN with example", "2.5M: LAN characteristics, pros/cons", "2M: MAN characteristics & examples", "2.5M: WAN characteristics, pros/cons", "1.5M: Summary table + VPN/CAN/SAN")),

        // T6: Q38–Q46, S12–S14, L6–L7
        q(38, 6, t6, "In which topology are all nodes connected to a central hub/switch?", listOf("Bus", "Star", "Ring", "Mesh"), 1),
        q(39, 6, t6, "In a bus topology, the ends of the cable are fitted with:", listOf("Hubs", "Terminators", "Routers", "Repeaters"), 1),
        q(40, 6, t6, "Token passing is commonly used in:", listOf("Bus", "Star", "Ring", "Mesh"), 2),
        q(41, 6, t6, "In a fully connected mesh with n nodes, number of links is:", listOf("n", "n−1", "n(n−1)/2", "2n"), 2, f = "Links = n(n − 1) / 2"),
        q(42, 6, t6, "Which topology is the most reliable and fault-tolerant?", listOf("Bus", "Star", "Ring", "Mesh"), 3),
        q(43, 6, t6, "The most widely used LAN topology today is:", listOf("Star", "Bus", "Ring", "Mesh"), 0),
        q(44, 6, t6, "Failure of the central device in a star topology causes:", listOf("Only one node to fail", "Whole network to fail", "No effect", "Only half of nodes to fail"), 1),
        q(45, 6, t6, "A combination of two or more topologies is called:", listOf("Hybrid topology", "Tree only", "Linear", "Dual"), 0),
        q(46, 6, t6, "A set of rules for communication between devices is called a:", listOf("Protocol", "Topology", "Packet", "Medium"), 0),
        s(12, 6, t6, "Compare Star, Bus, Ring and Mesh topologies. ★",
            "Star: easy install/fault-isolation, single cable failure local; central hub is SPOF & more cable.\nBus: cheap, less cable; backbone fault stops all, collisions.\nRing: orderly token passing, no collisions; break stops ring unless dual-ring, disruptive expansion.\nMesh: highest reliability & dedicated links; costly, n(n−1)/2 links & n−1 ports/node.",
            listOf("1.25M each for Star, Bus, Ring, Mesh pros & cons")),
        s(13, 6, t6, "What is a communication protocol? State its key elements. ★",
            "A protocol is a set of rules governing how data is formatted, transmitted, received, and acknowledged.\nThree key elements: (1) Syntax — structure/format of data (header, payload, trailer). (2) Semantics — meaning of each field, control & error handling. (3) Timing — when data is sent and at what rate (synchronization). Examples: TCP/IP, HTTP, FTP, SMTP, Ethernet, 802.11.",
            listOf("1.5M: Definition & examples", "3.5M: Syntax, Semantics & Timing clearly explained")),
        s(14, 6, t6, "Calculate the number of links and ports required for a full mesh of 6 devices.",
            "For n = 6 devices:\nLinks = n(n − 1) / 2 = 6 × 5 / 2 = 15 duplex links.\nPorts per device = n − 1 = 5 ports.\nTotal I/O ports across network = 6 × 5 = 30 ports.",
            listOf("2.5M: Links formula n(n−1)/2 = 15", "2.5M: Ports per device (5) & total ports (30)"),
            f = "Links = n(n−1)/2 = 15 ; Ports/node = n−1 = 5 (Total = 30)"),
        l(6, 6, t6, 12L, "Explain Star, Bus, Ring and Mesh network topologies with neat diagrams, advantages and disadvantages. ★",
            "Topology is the physical/logical layout of nodes & links:\n1. Star (central switch/hub): easy troubleshooting & expansion; central SPOF & more cable.\n2. Bus (shared backbone + terminators, CSMA/CD): cheap & simple; backbone fault stops all, collisions.\n3. Ring (closed loop, token passing): collision-free under load; single break stops traffic (dual ring mitigates).\n4. Mesh (full mesh = n(n−1)/2 links): ultra-reliable & secure; expensive cabling & ports.\nHybrid/Tree combines topologies (e.g., star-bus) for large enterprises.",
            listOf("3M: Neat diagrams of Star, Bus (with terminators), Ring & Full Mesh", "2M each (8M): Star, Bus, Ring, Mesh working, pros & cons", "1M: Hybrid/Tree topology")),
        l(7, 6, t6, 12L, "Explain communication protocols. Describe the OSI reference model and the TCP/IP model. ★",
            "Protocol defines syntax, semantics & timing in layered stacks.\nOSI 7 Layers (All People Seem To Need Data Processing): 7-Application (HTTP/FTP/DNS, Data), 6-Presentation (encryption/compression, Data), 5-Session (dialog/sync, Data), 4-Transport (TCP/UDP, Segment), 3-Network (IP/routing, Packet), 2-Data Link (Ethernet/MAC, Frame), 1-Physical (cables/signals, Bits).\nTCP/IP 4 Layers: Application (HTTP/FTP/SMTP/DNS), Transport (TCP/UDP), Internet (IP/ICMP/ARP), Network Access (Ethernet/Wi-Fi).\nOSI (ISO, 7 layers, conceptual reference) vs TCP/IP (DoD/ARPANET, 4 layers, practical Internet model).",
            listOf("2M: Protocol concept & layering", "4.5M: All 7 OSI layers with functions & PDUs", "3.5M: TCP/IP 4-layer stack diagram & protocols", "2M: OSI vs TCP/IP comparison table")),

        // T7: Q47–Q55, S15–S17, L8–L9
        q(47, 7, t7, "Which device broadcasts incoming data to all its ports?", listOf("Switch", "Hub", "Router", "Bridge"), 1),
        q(48, 7, t7, "A switch forwards frames based on:", listOf("IP address", "MAC address", "URL", "Port number only"), 1),
        q(49, 7, t7, "Which device connects different networks and routes packets using IP addresses?", listOf("Hub", "Repeater", "Router", "NIC"), 2),
        q(50, 7, t7, "A modem converts:", listOf("Digital to analog and analog to digital", "Text to image", "IP to MAC", "AC to DC"), 0),
        q(51, 7, t7, "A device that connects two networks using different protocols/architectures is a:", listOf("Gateway", "Hub", "Switch", "Repeater"), 0),
        q(52, 7, t7, "Wi-Fi is based on which IEEE standard?", listOf("802.3", "802.11", "802.15", "802.5"), 1),
        q(53, 7, t7, "Typical range of Bluetooth (Class 2) is about:", listOf("1 km", "10 m", "100 km", "1 cm"), 1),
        q(54, 7, t7, "The hardware that lets a computer connect to a network is the:", listOf("NIC (Network Interface Card)", "CPU", "GPU", "SMPS"), 0),
        q(55, 7, t7, "A repeater is used to:", listOf("Regenerate/amplify signals to extend distance", "Route packets", "Convert protocols", "Assign IP addresses"), 0),
        s(15, 7, t7, "Differentiate between hub and switch. ★",
            "Hub: Physical layer (L1), broadcasts to all ports, shared bandwidth with collisions, half duplex, cheap/obsolete.\nSwitch: Data Link layer (L2), forwards only to destination port via MAC table, dedicated bandwidth/collision-free, full duplex, intelligent & widely used.",
            listOf("2.5M: OSI layer & forwarding mechanism", "2.5M: Bandwidth/collisions, duplex mode & usage")),
        s(16, 7, t7, "Differentiate between switch and router. ★",
            "Switch: connects devices inside a LAN, Layer 2 MAC addresses, forwards frames, no NAT/firewall.\nRouter: connects different networks (LAN–WAN), Layer 3 IP addresses, forwards packets along best routing-table path, supports NAT, DHCP & firewall.",
            listOf("2.5M: Scope (LAN vs Inter-network) & OSI layer/addressing", "2.5M: PDU forwarded & NAT/DHCP/firewall features")),
        s(17, 7, t7, "Write short notes on modem and gateway.",
            "Modem (Modulator–Demodulator): converts digital data to analog signals over telephone/cable lines and back; connects home/office to ISP (DSL, cable modem).\nGateway: connects two networks using dissimilar protocols/architectures by translating protocols (e.g., LAN to Internet, email gateway); default gateway is typically the router's IP.",
            listOf("2.5M: Modem full form, modulation/demodulation & ISP link", "2.5M: Gateway protocol conversion & default gateway")),
        l(8, 7, t7, 10L, "Explain various networking devices – hub, switch, router, modem, gateway, repeater, bridge and access point.",
            "NIC (L1–2, MAC address), Repeater (L1, regenerates weak signal), Hub (L1, multi-port repeater broadcasting to all), Bridge (L2, connects 2 LAN segments filtering by MAC), Switch (L2, multi-port bridge with MAC table), Router (L3, routes IP packets, NAT/DHCP/firewall), Modem (L1, modulates/demodulates for ISP), Gateway (L4–7, protocol converter), Access Point (L2, bridges Wi-Fi clients to wired LAN).\nFlow: Internet — Modem — Router/Gateway — Switch — PCs/Printer & Access Point ~~~ Phones/Laptops. Key rule: Hub=broadcast, Switch=MAC unicast, Router=IP routing.",
            listOf("6M: All 9 devices with OSI layer & function", "2.5M: Home/office interconnection diagram", "1.5M: Hub vs Switch vs Router synthesis")),
        l(9, 7, t7, 10L, "Explain wireless networking and its technologies – Wi-Fi, Bluetooth, cellular (4G/5G), NFC, Zigbee and WiMAX.",
            "Wireless networking uses radio/microwaves/infrared via wireless NIC, AP, router & antennas.\nTechnologies: Wi-Fi (IEEE 802.11 a/b/g/n/ac/ax, 30–100 m, WLAN), Bluetooth (802.15.1, ~10 m, PAN), Cellular 4G/5G (LTE/NR, km, mobile data), NFC (ISO 14443, <4 cm, contactless pay), Zigbee (802.15.4, 10–100 m, IoT sensors), WiMAX (802.16, up to 50 km, wireless MAN).\nModes: Infrastructure (via AP) vs Ad-hoc (P2P). Adv: mobility, no cabling, scalability. Disadv: security (use WPA2/WPA3), interference, lower speed than wired.",
            listOf("5M: All 6 wireless technologies with standard, range & use", "2M: Infrastructure vs Ad-hoc modes", "3M: Advantages, disadvantages & WPA2/WPA3 security")),

        // T8: Q56–Q64, S18–S20, L10
        q(56, 8, t8, "An IPv4 address is ______ bits long.", listOf("16", "32", "64", "128"), 1),
        q(57, 8, t8, "An IPv6 address is ______ bits long.", listOf("32", "64", "128", "256"), 2),
        q(58, 8, t8, "Which of the following is a valid IPv4 address?", listOf("192.168.1.10", "256.10.1.1", "192.168.1", "192.168.1.1.1"), 0),
        q(59, 8, t8, "An address 192.168.10.5 belongs to class:", listOf("A", "B", "C", "D"), 2, exp = "Class C first octet: 192–223."),
        q(60, 8, t8, "The loopback address in IPv4 is:", listOf("0.0.0.0", "127.0.0.1", "255.255.255.255", "10.0.0.1"), 1),
        q(61, 8, t8, "Which of the following is a private IP address range?", listOf("8.8.8.0/24", "192.168.0.0/16", "100.0.0.0/8", "1.1.1.0/24"), 1),
        q(62, 8, t8, "Dynamic allocation of IP addresses is done by:", listOf("DNS", "DHCP", "FTP", "SMTP"), 1),
        q(63, 8, t8, "The Internet is a:", listOf("Single computer", "Global network of interconnected networks using TCP/IP", "Search engine", "Software"), 1),
        q(64, 8, t8, "Class D IP addresses (224–239) are used for:", listOf("Unicast", "Multicast", "Broadcast only", "Private use"), 1),
        s(18, 8, t8, "What is the Internet? Name its basic services. ★",
            "The Internet is a global network of interconnected networks communicating over TCP/IP (Internet ≠ WWW; Web is one service on the Internet).\nServices: World Wide Web (WWW), E-mail, File Transfer (FTP), Remote login (SSH/Telnet), VoIP, streaming, cloud services & social media.",
            listOf("2M: Definition & Internet vs WWW distinction", "3M: Five basic Internet services")),
        s(19, 8, t8, "What is an IP address? Explain IPv4 address classes. ★",
            "An IP address is a unique logical address identifying a host & its network. IPv4 is 32 bits written as 4 decimal octets.\nClass A: 1–126 (/8, 255.0.0.0, very large). Class B: 128–191 (/16, 255.255.0.0, medium). Class C: 192–223 (/24, 255.255.255.0, small). Class D: 224–239 (Multicast). Class E: 240–255 (Experimental). 127.x.x.x is reserved for loopback.",
            listOf("1.5M: IP address & 32-bit dotted-decimal definition", "3.5M: Classes A–E with first-octet range, default masks & loopback note")),
        s(20, 8, t8, "Differentiate between IPv4 and IPv6. ★",
            "IPv4: 32 bits, dotted decimal (192.168.1.1), ~4.3B (2³²) addresses, variable 20–60 B header, optional IPsec, manual/DHCP.\nIPv6: 128 bits, colon-hex (2001:db8::1), ~3.4×10³⁸ (2¹²⁸) addresses, fixed 40 B header, IPsec built-in, auto-configuration.",
            listOf("2.5M: Size, format & address space", "2.5M: Header, security & configuration")),
        l(10, 8, t8, 10L, "Explain the Internet and IP addressing in detail: IPv4 classes, private addresses, static/dynamic addressing and IPv6.",
            "Internet: global WAN via ISPs & TCP/IP (origin ARPANET 1969).\nIP address = Network ID + Host ID. Classes A (8.24, ~16.7M hosts), B (16.16, 65,534 hosts), C (24.8, 254 hosts), D (multicast), E (reserved).\nPrivate ranges (NAT): 10.0.0.0/8, 172.16.0.0–172.31.255.255, 192.168.0.0/16. Loopback 127.0.0.1; Broadcast 255.255.255.255.\nStatic (fixed for servers) vs Dynamic (leased via DHCP).\nIPv6: 128-bit 8×4 hex groups (2001:db8:85a3::8a2e:370:7334), solves exhaustion, built-in IPsec & autoconfig. MAC (48-bit physical) vs IP (logical).",
            listOf("2M: Internet & Network/Host ID concept", "3M: IPv4 Classes A–E table with host counts", "2.5M: Private/special addresses & Static vs Dynamic (DHCP)", "2.5M: IPv6 format, benefits & MAC vs IP note")),

        // T9: Q65–Q74, S21–S24, L11–L12
        q(65, 9, t9, "Which protocol is connection-oriented and reliable?", listOf("UDP", "TCP", "IP", "ICMP"), 1),
        q(66, 9, t9, "Which protocol is connectionless and faster but unreliable?", listOf("TCP", "UDP", "FTP", "HTTP"), 1),
        q(67, 9, t9, "The default port number of HTTP is:", listOf("21", "25", "80", "443"), 2),
        q(68, 9, t9, "The default port number of HTTPS is:", listOf("80", "443", "53", "22"), 1),
        q(69, 9, t9, "DNS is used to:", listOf("Convert domain names into IP addresses", "Send e-mail", "Transfer files", "Assign MAC addresses"), 0),
        q(70, 9, t9, "FTP uses ports:", listOf("20 and 21", "25 and 110", "53 and 67", "80 and 443"), 0),
        q(71, 9, t9, "URL stands for:", listOf("Uniform Resource Locator", "Universal Resource Link", "Unified Remote Locator", "Uniform Router Label"), 0),
        q(72, 9, t9, "TCP connection establishment uses a:", listOf("Two-way handshake", "Three-way handshake", "No handshake", "Four-way handshake only"), 1),
        q(73, 9, t9, "In client–server architecture, the web browser acts as a:", listOf("Server", "Client", "Router", "Switch"), 1),
        q(74, 9, t9, "Which application commonly uses UDP?", listOf("File transfer", "Video streaming / VoIP / DNS queries", "Online banking forms", "E-mail download"), 1),
        s(21, 9, t9, "Differentiate between TCP and UDP. ★",
            "TCP: connection-oriented (3-way handshake), reliable (ACK, retransmission, ordering), slower, 20-byte header, flow/congestion control (HTTP, FTP, SMTP).\nUDP: connectionless, unreliable (no ACK), faster/low overhead, 8-byte header, no flow/congestion control (DNS, VoIP, streaming, gaming).",
            listOf("2.5M: Connection, reliability & speed", "2.5M: Header size, flow/congestion control & applications")),
        s(22, 9, t9, "What is DNS? Explain how a domain name is resolved. ★",
            "DNS (Domain Name System, port 53) is a distributed hierarchical database mapping domain names (www.beu.ac.in) to IP addresses.\nResolution steps: (1) Browser checks local cache/hosts file. (2) Sends query to ISP recursive resolver. (3) Resolver queries Root server → TLD server (.in) → Authoritative server for the domain. (4) IP address returned, cached, and used to connect.",
            listOf("1.5M: DNS definition & port 53", "3.5M: 4-step hierarchical resolution flow (Cache → Resolver → Root → TLD → Authoritative)")),
        s(23, 9, t9, "Explain the structure of a URL with an example.",
            "Example: https://www.example.com:443/courses/cse/index.html?id=5#top\n1. Protocol/scheme (https)\n2. Domain name / host (www.example.com)\n3. Port (optional, 443)\n4. Path to resource (/courses/cse/index.html)\n5. Query string (?id=5)\n6. Fragment (#top).",
            listOf("1.5M: Complete annotated URL example", "3.5M: All 6 URL components explained"),
            f = "https://www.example.com:443/courses/cse/index.html?id=5#top"),
        s(24, 9, t9, "Write short notes on HTTP and FTP.",
            "HTTP (HyperText Transfer Protocol): stateless application-layer request–response protocol of the Web; port 80 (HTTPS = HTTP+TLS, port 443); methods GET, POST, PUT, DELETE; status codes 200 OK, 404 Not Found, 500 Server Error.\nFTP (File Transfer Protocol): transfers files between client & server; port 21 (control), port 20 (data); supports login/anonymous; SFTP is the secure variant.",
            listOf("2.5M: HTTP/HTTPS ports, methods & status codes", "2.5M: FTP dual ports (21/20), auth & SFTP")),
        l(11, 9, t9, 10L, "Explain the client–server architecture with a neat diagram. Compare it with peer-to-peer. ★",
            "In client–server, an always-on server provides services/resources and clients send requests (e.g. HTTP GET) over LAN/Internet.\nTypes: 2-tier (client ↔ server/DB) and 3-tier (browser ↔ app server ↔ DB server). Examples: web, mail, file, DB servers.\nClient–Server vs Peer-to-Peer: distinct roles vs dual roles; centralized vs decentralized mgmt; stronger vs weak security; higher server cost vs low cost; high vs poor scalability (Web/banking vs BitTorrent/home LAN).",
            listOf("2.5M: Client–Server diagram & 3-step request-response working", "2M: 2-tier vs 3-tier & server examples", "4M: Client–Server vs P2P comparison table", "1.5M: Advantages & disadvantages")),
        l(12, 9, t9, 12L, "Explain the TCP/IP protocol suite and the application protocols HTTP, FTP and DNS with their working.",
            "TCP/IP 4 layers: Application (HTTP/FTP/SMTP/DNS/Telnet), Transport (TCP/UDP), Internet (IP/ICMP/ARP), Network Access (Ethernet/Wi-Fi).\n1. Transport: TCP (reliable, 3-way handshake SYN → SYN-ACK → ACK, flow/congestion control) vs UDP (fast, connectionless).\n2. Internet: IPv4/IPv6 addressing & routing; ICMP ping.\n3. Application protocols & ports: HTTP/HTTPS (80/443), FTP (21/20), DNS (53), SMTP/POP3/IMAP (25/110/143), Telnet/SSH (23/22).\nEnd-to-end example (opening www.beu.ac.in): DNS lookup → TCP 3-way handshake (80/443) → HTTP GET → 200 OK HTML response → close/reuse.",
            listOf("2.5M: TCP/IP 4-layer stack diagram", "3M: TCP 3-way handshake diagram, UDP & Internet layer (IP/ICMP)", "3.5M: Application protocols & standard ports table", "3M: End-to-end walkthrough of opening www.beu.ac.in"))
    )
}
