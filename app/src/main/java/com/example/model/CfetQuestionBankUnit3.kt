package com.example.model

/**
 * CFET Unit 3.0: Computer Software and Operating Systems
 * 54 Objective (1M) + 15 Short (5M) + 9 Long (10–12M) = 78 questions across Topics T1–T7.
 */
internal fun cfetUnit3Questions(): List<TestQuestion> {
    val uId = "u_100108_3"
    val uTitle = "Unit 3.0: Computer Software & Operating Systems"

    val t1 = "T1. Intro & Types of Software"
    val t2 = "T2. System vs Application Software"
    val t3 = "T3. OS & Objectives of OS"
    val t4 = "T4. Types of Operating Systems"
    val t5 = "T5. OS Functions (Process & Memory)"
    val t6 = "T6. User Interface (CLI vs GUI)"
    val t7 = "T7. File Systems (Win, Linux, macOS)"

    fun q(n: Int, tn: Int, tt: String, p: String, opts: List<String>, ans: Int, f: String = "", exp: String = "") =
        CfetQuestionBank.mcq(3, uId, uTitle, n, tn, tt, p, opts, ans, f, "NONE", exp)

    fun s(n: Int, tn: Int, tt: String, p: String, ans: String, sc: List<String>, f: String = "") =
        CfetQuestionBank.shortQ(3, uId, uTitle, n, tn, tt, p, ans, sc, f)

    fun l(n: Int, tn: Int, tt: String, m: Long, p: String, ans: String, sc: List<String>, f: String = "") =
        CfetQuestionBank.longQ(3, uId, uTitle, n, tn, tt, m, p, ans, sc, f)

    return listOf(
        // T1: Objective Q1–Q7
        q(1, 1, t1, "Software is best defined as:", listOf("Physical parts of a computer", "A set of programs and instructions that tell hardware what to do", "Only the operating system", "Input devices"), 1),
        q(2, 1, t1, "Software permanently stored in ROM to control a hardware device is called:", listOf("Freeware", "Firmware", "Shareware", "Middleware"), 1),
        q(3, 1, t1, "Which of the following is application software?", listOf("Device driver", "Compiler", "MS Excel", "BIOS"), 2),
        q(4, 1, t1, "Antivirus and disk-cleanup programs are examples of:", listOf("Utility software", "Application software", "Firmware", "Hardware"), 0),
        q(5, 1, t1, "Software whose source code is freely available for modification is:", listOf("Proprietary", "Open-source", "Shareware", "Demo"), 1),
        q(6, 1, t1, "Software that is distributed free of cost but whose source code is not available is:", listOf("Freeware", "Open source", "Commercial", "Firmware"), 0),
        q(7, 1, t1, "Which of the following is NOT software?", listOf("Windows", "Python", "Keyboard", "Chrome"), 2),
        // T1: Short S1–S2 & Long L1
        s(1, 1, t1, "Define software. Differentiate between hardware and software. ★",
            "Software is a collection of programs, data, and documentation instructing hardware what to do.\nHardware: physical/tangible, cannot be copied by command, wears out with time (CPU, RAM, monitor).\nSoftware: intangible instructions, can be copied/installed/uninstalled, doesn't wear out but may get bugs/viruses (Windows, MS Office, Chrome). Both need each other.",
            listOf("1M: Clear definition of software", "4M: Four crisp comparison points with examples")),
        s(2, 1, t1, "Differentiate between open-source, proprietary, freeware and shareware software.",
            "Open source: source available to modify/redistribute, usually free (Linux, Firefox, LibreOffice). Proprietary: closed source owned by vendor, paid licence (Windows, MS Office). Freeware: closed source, free (Adobe Reader, Skype). Shareware: closed source, free trial then pay (WinRAR, trial antivirus).",
            listOf("1.25M each for Open-source, Proprietary, Freeware, Shareware with example")),
        l(1, 1, t1, 10L, "What is software? Explain the classification of software with suitable examples.",
            "Software is the non-physical part of a computer. Classification:\n1. System Software: OS (Windows, Linux), Language translators (compiler, assembler, interpreter), Device drivers, Utility software (antivirus, backup, disk defragmenter, compressors), Firmware (BIOS, router ROM).\n2. Application Software: General purpose (Word, Excel, Chrome), Specific purpose (Tally, railway reservation, hospital mgmt), Custom/bespoke, Web & mobile apps.\n3. Middleware: connects systems (DB connectors). By licensing: Open source, Proprietary, Freeware, Shareware, Public domain.",
            listOf("2M: Classification tree diagram", "3M: System software subtypes & examples", "3M: Application software subtypes & examples", "2M: Utility, Firmware, Middleware & Licensing overview")),

        // T2: Objective Q8–Q14
        q(8, 2, t2, "Which of the following is system software?", listOf("Operating system", "Photoshop", "VLC player", "Tally"), 0),
        q(9, 2, t2, "A device driver is:", listOf("Application software for gaming", "System software that lets the OS communicate with hardware", "Hardware", "A network cable"), 1),
        q(10, 2, t2, "Compiler, assembler and interpreter belong to:", listOf("Application software", "System software (language translators)", "Utility only", "Firmware only"), 1),
        q(11, 2, t2, "Software designed specifically for a particular organisation is called:", listOf("Packaged software", "Custom (bespoke) software", "Freeware", "Firmware"), 1),
        q(12, 2, t2, "Which software runs directly on the hardware and provides services to other software?", listOf("Application software", "System software", "Web page", "Game"), 1),
        q(13, 2, t2, "Web browsers, media players and spreadsheets are:", listOf("System software", "Application software", "Firmware", "Drivers"), 1),
        q(14, 2, t2, "Which is a utility program?", listOf("Disk defragmenter", "MS PowerPoint", "Python", "Google Maps"), 0),
        // T2: Short S3–S4 & Long L2
        s(3, 2, t2, "Differentiate between system software and application software. ★",
            "System software: manages hardware & provides app platform, independent of apps, written in low-level C/assembly, runs continuously in background (Windows, Linux, drivers, compilers).\nApplication software: performs user-specific tasks, depends on system software, written in high-level Java/Python/C#, runs on demand (MS Word, Chrome, Tally, games).",
            listOf("2.5M: Purpose, dependency & implementation language", "2.5M: Runtime behavior & examples")),
        s(4, 2, t2, "List the types of application software with examples.",
            "Word processing (MS Word, Google Docs), Spreadsheet (Excel, Calc), Presentation (PowerPoint, Impress), Database (MS Access, MySQL front-ends), Multimedia (VLC, Photoshop), Web browsers (Chrome, Firefox), Enterprise/ERP (SAP, Tally), Educational/Entertainment (e-learning apps, games).",
            listOf("5M: Listing at least 6–8 categories with accurate examples")),
        l(2, 2, t2, 10L, "Explain system software and application software in detail with examples. How do they differ? ★",
            "System software controls hardware and supports applications: OS, language translators, device drivers, utility programs, firmware.\nApplication software serves end-user tasks: general-purpose packaged (MS Office, Chrome), specific-purpose (payroll, railway reservation), custom/bespoke, web/mobile apps (Gmail, UPI).\nLayered view: User ↔ Application Software ↔ System Programs/Shell ↔ OS Kernel ↔ Hardware. Comparison across role, interaction, installation & examples.",
            listOf("3M: Detailed system software types & examples", "3M: Detailed application software types & examples", "2M: Layered architecture diagram (User → App → System → Kernel → Hardware)", "2M: Structured comparison table")),

        // T3: Objective Q15–Q21
        q(15, 3, t3, "An operating system is:", listOf("An application program", "An interface between the user and the computer hardware", "A programming language", "An input device"), 1),
        q(16, 3, t3, "Which of the following is an operating system?", listOf("Oracle", "Linux", "MS Word", "Python"), 1),
        q(17, 3, t3, "The core part of the operating system that interacts directly with hardware is the:", listOf("Shell", "Kernel", "Compiler", "Editor"), 1),
        q(18, 3, t3, "The process of loading the operating system into main memory when the computer is switched on is:", listOf("Compiling", "Booting", "Spooling", "Paging"), 1),
        q(19, 3, t3, "Which is NOT an objective of an operating system?", listOf("Convenience", "Efficiency", "Ability to evolve", "Increase hardware cost"), 3),
        q(20, 3, t3, "The OS acts as a ______ of computer resources.", listOf("Owner", "Manager", "Customer", "Programmer"), 1),
        q(21, 3, t3, "The first program that runs when a computer boots is the:", listOf("Application", "Bootstrap loader (BIOS/UEFI)", "Browser", "Antivirus"), 1),
        // T3: Short S5–S6 & Long L3
        s(5, 3, t3, "What is an operating system? State its main objectives. ★",
            "An OS is system software managing hardware/software resources and providing an interface for users and apps.\nObjectives: (1) Convenience (easy GUI/CLI), (2) Efficiency (optimal CPU, memory, I/O usage), (3) Ability to evolve (support new hardware/updates seamlessly), (4) Resource management & protection. Examples: Windows, Linux, macOS, Android, iOS.",
            listOf("1.5M: OS definition & examples", "3.5M: Four core objectives explained")),
        s(6, 3, t3, "Explain the OS as a resource manager and as an extended (virtual) machine.",
            "Resource manager: allocates CPU time, memory, files & I/O among competing programs, tracks usage, and resolves conflicts.\nExtended (virtual) machine: hides low-level hardware complexity and presents clean abstractions like files, processes, and windows so developers don't program disk controllers directly.",
            listOf("2.5M: OS as Resource Manager", "2.5M: OS as Extended / Virtual Machine")),
        l(3, 3, t3, 10L, "What is an operating system? Explain its objectives, role and services. ★",
            "OS acts as intermediary between user and hardware.\nObjectives: convenience, efficiency, ability to evolve, security/protection.\nRoles: resource manager, extended machine, control program.\nServices: program execution, I/O operations, file-system manipulation, communication (IPC/networking), error detection/handling, resource allocation/accounting/security.\nExamples: Desktop (Windows, macOS, Linux), Mobile (Android, iOS), Server (Linux), Real-time (VxWorks).",
            listOf("2M: Definition & layered diagram of OS position", "2M: Four objectives", "3M: Three roles (resource manager, extended machine, control program)", "3M: Six OS services, examples & conclusion")),

        // T4: Objective Q22–Q29
        q(22, 4, t4, "In a batch operating system:", listOf("The user interacts directly with the program during execution", "Similar jobs are collected and executed in batches without user interaction", "Many CPUs work in lock-step", "Jobs must finish within deadlines"), 1),
        q(23, 4, t4, "Time-sharing systems give each user:", listOf("A fixed deadline", "A small slice of CPU time (time quantum)", "A separate computer", "Unlimited CPU"), 1),
        q(24, 4, t4, "Which OS is used in missile control, air-traffic control and industrial robots?", listOf("Batch OS", "Real-time OS", "Time-sharing OS", "Single-user OS"), 1),
        q(25, 4, t4, "In a ______ real-time system, missing a deadline may lead to catastrophic failure.", listOf("Soft", "Hard", "Batch", "Distributed"), 1),
        q(26, 4, t4, "A distributed operating system:", listOf("Runs on a single CPU", "Manages a group of independent computers and makes them appear as a single system", "Runs only one job at a time", "Cannot use networks"), 1),
        q(27, 4, t4, "Which of the following is an example of an RTOS?", listOf("VxWorks", "MS-DOS", "Windows 98", "Ubuntu Desktop"), 0),
        q(28, 4, t4, "Multiprogramming mainly increases:", listOf("Printer speed", "CPU utilisation", "Disk size", "Network cost"), 1),
        q(29, 4, t4, "CPU scheduling in time-sharing systems commonly uses:", listOf("FCFS only", "Round Robin", "None", "LIFO"), 1),
        // T4: Short S7–S8 & Long L4
        s(7, 4, t4, "Differentiate between batch and time-sharing operating systems. ★",
            "Batch OS: no user interaction during execution, maximizes throughput, job runs till completion/I-O, long response time (hours) — early IBM mainframes, payroll.\nTime-sharing OS: interactive, minimizes response time, CPU divided in time slices, short response time (seconds) — UNIX, Linux, Windows.",
            listOf("2.5M: Interaction, objective & CPU usage", "2.5M: Response time & examples")),
        s(8, 4, t4, "Differentiate between hard and soft real-time systems. ★",
            "Hard real-time: deadline strictly met, failure is catastrophic, little/no virtual memory — pacemaker, airbag, missile control.\nSoft real-time: occasional deadline miss tolerated, only quality degrades, may use virtual memory — video streaming, online gaming.",
            listOf("2.5M: Deadline strictness & failure impact", "2.5M: Virtual memory & real-world examples")),
        l(4, 4, t4, 12L, "Explain the different types of operating systems – batch, time-sharing, distributed and real-time – with their advantages, disadvantages and examples. ★",
            "1. Batch OS: operator groups similar jobs, no interaction. Adv: bulk throughput. Disadv: long turnaround, CPU idle during I/O. Ex: payroll, IBM OS/360.\n2. Time-sharing OS: CPU sliced into quanta (Round Robin). Adv: quick interactive response, multi-user. Disadv: context-switch overhead, security. Ex: UNIX, Linux, Windows.\n3. Distributed OS: networked independent computers appear as one coherent system. Adv: resource sharing, fault tolerance, scalability. Disadv: complex, network dependent. Ex: LOCUS, Amoeba, cloud clusters.\n4. Real-time OS (Hard vs Soft): strict time limits. Adv: predictable, reliable. Disadv: costly, complex. Ex: VxWorks, QNX, FreeRTOS. Also note multiprogramming, multiprocessing, multithreading.",
            listOf("2.5M: Batch OS (working, pros/cons, examples)", "2.5M: Time-sharing OS (working, pros/cons, examples)", "2.5M: Distributed OS (working, pros/cons, examples)", "2.5M: Real-time OS (Hard/Soft, pros/cons, examples)", "2M: Summary comparison table")),

        // T5: Objective Q30–Q38
        q(30, 5, t5, "A process is:", listOf("A program stored on disk", "A program in execution", "A compiler", "A file"), 1),
        q(31, 5, t5, "Which data structure stores the information about a process?", listOf("File table", "Process Control Block (PCB)", "Stack only", "Cache"), 1),
        q(32, 5, t5, "Which of the following is NOT a process state?", listOf("Ready", "Running", "Waiting", "Compiling"), 3),
        q(33, 5, t5, "Which scheduling algorithm is non-pre-emptive and serves jobs in arrival order?", listOf("Round Robin", "FCFS", "SRTF", "Priority pre-emptive"), 1),
        q(34, 5, t5, "Paging divides physical memory into fixed-size blocks called:", listOf("Segments", "Frames", "Sectors", "Cylinders"), 1),
        q(35, 5, t5, "A situation in which two or more processes wait forever for resources held by each other is:", listOf("Starvation", "Deadlock", "Spooling", "Paging"), 1),
        q(36, 5, t5, "Virtual memory allows execution of:", listOf("Only small programs", "Programs larger than physical memory", "Programs without CPU", "Only batch jobs"), 1),
        q(37, 5, t5, "Switching the CPU from one process to another is called:", listOf("Context switch", "Booting", "Swapping disk", "Spooling"), 0),
        q(38, 5, t5, "Excessive paging activity that degrades performance is called:", listOf("Fragmentation", "Thrashing", "Caching", "Spooling"), 1),
        // T5: Short S9–S11 & Long L5–L7
        s(9, 5, t5, "Explain the different states of a process with a state diagram. ★",
            "5 states: New (being created) → Ready (in ready queue awaiting CPU) ⇄ Running (executing; timeout returns to Ready) → Terminated (finished); Running → Waiting/Blocked (waiting for I/O or event) → Ready (I/O done). OS tracks each process via Process Control Block (PCB: PID, state, PC, registers, memory info, open files).",
            listOf("2M: Process state transition diagram (New/Ready/Running/Waiting/Terminated)", "2M: Clear explanation of all 5 states", "1M: PCB fields mentioned")),
        s(10, 5, t5, "Differentiate between paging and segmentation. ★",
            "Paging: fixed-size pages/frames, invisible to programmer, causes internal fragmentation, uses Page Table.\nSegmentation: variable-size logical segments (code, data, stack), visible to programmer, causes external fragmentation, uses Segment Table.",
            listOf("2.5M: Block sizing & programmer visibility", "2.5M: Fragmentation type & mapping table")),
        s(11, 5, t5, "Differentiate between a program and a process.",
            "Program: passive entity (file on disk), exists as long as stored, holds no runtime resources, one program can spawn many processes.\nProcess: active entity (program in execution), exists only while executing, needs CPU/memory/I-O, each process has its own PCB.",
            listOf("2.5M: Passive vs active & lifetime", "2.5M: Resource allocation & 1-to-many PCB relationship")),
        l(5, 5, t5, 10L, "Explain the major functions of an operating system.",
            "1. Process management (creation/termination, scheduling FCFS/SJF/RR/Priority, sync, IPC, deadlock).\n2. Memory management (track/allocate/deallocate, paging, segmentation, virtual memory, swapping).\n3. File management (CRUD files, directories, permissions, allocation).\n4. Device I/O management (drivers, buffering, caching, spooling, interrupts).\n5. Secondary storage (free space, disk scheduling).\n6. Security & protection (authentication, access control, encryption).\n7. UI, error detection, accounting & networking.",
            listOf("3M: Process & Memory management", "3M: File, Device I/O & Secondary-storage management", "2M: Security, UI & accounting", "2M: Layered kernel diagram")),
        l(6, 5, t5, 12L, "Explain process management and CPU scheduling. Calculate the average waiting time for FCFS, SJF and Round Robin (q = 4) for processes P1, P2, P3 with burst times 24, 3, 3 (all arrive at time 0).",
            "(a) FCFS: Gantt | P1(0–24) | P2(24–27) | P3(27–30) |. Waiting: P1=0, P2=24, P3=27 ⇒ Avg = (0+24+27)/3 = 17 ms (convoy effect).\n(b) SJF: Gantt | P2(0–3) | P3(3–6) | P1(6–30) |. Waiting: P2=0, P3=3, P1=6 ⇒ Avg = (0+3+6)/3 = 3 ms (optimal).\n(c) Round Robin (q=4): Gantt | P1 0–4 | P2 4–7 | P3 7–10 | P1 10–30 |. Waiting: P1=10−4=6, P2=4, P3=7 ⇒ Avg = (6+4+7)/3 = 5.67 ms (fair).\nConclusion: SJF minimizes avg waiting time; RR provides fairness & low response time.",
            listOf("2M: Process management & scheduling criteria", "3M: FCFS Gantt chart & Avg = 17 ms", "3M: SJF Gantt chart & Avg = 3 ms", "3M: Round Robin (q=4) Gantt chart & Avg = 5.67 ms", "1M: Summary table & conclusion"),
            f = "Avg Wait: FCFS = 17 ms | SJF = 3 ms | RR(q=4) = 5.67 ms"),
        l(7, 5, t5, 12L, "Explain memory management techniques of an operating system (contiguous allocation, paging, segmentation, virtual memory).",
            "1. Contiguous allocation: Fixed partition (internal fragmentation) vs Variable partition (external fragmentation; First/Best/Worst fit; compaction).\n2. Paging: logical pages mapped to equal-sized physical frames via Page Table; logical addr = (page#, offset); no external fragmentation.\n3. Segmentation: variable logical segments (code, data, stack) via Segment Table (base, limit); external fragmentation.\n4. Virtual memory: demand paging loads needed pages into RAM on page fault; replacement algorithms (FIFO, LRU, Optimal); thrashing when page faults dominate.\n5. Swapping: moving processes between RAM and disk.",
            listOf("2.5M: Contiguous allocation (Fixed/Variable, fit strategies, compaction)", "3M: Paging & address translation", "2M: Segmentation & Segment Table", "3.5M: Virtual memory, demand paging, FIFO/LRU/Optimal & thrashing", "1M: Swapping & comparison table")),

        // T6: Objective Q39–Q46
        q(39, 6, t6, "In a CLI, the user interacts with the computer by:", listOf("Clicking icons", "Typing text commands", "Touching the screen", "Speaking"), 1),
        q(40, 6, t6, "GUI stands for:", listOf("General User Interface", "Graphical User Interface", "Graphic Universal Interface", "Group User Interface"), 1),
        q(41, 6, t6, "Which of the following is a CLI?", listOf("Windows desktop", "Linux Bash terminal", "macOS Finder", "Android home screen"), 1),
        q(42, 6, t6, "WIMP in GUI stands for:", listOf("Windows, Icons, Menus, Pointer", "Web, Internet, Mail, Print", "Windows, Input, Memory, Processor", "None"), 0),
        q(43, 6, t6, "Which interface needs fewer system resources?", listOf("GUI", "CLI", "Both same", "Touch UI"), 1),
        q(44, 6, t6, "Which Windows command lists files in a directory?", listOf("ls", "dir", "list", "show"), 1),
        q(45, 6, t6, "Which Linux command displays the current working directory?", listOf("cd", "pwd", "dir", "path"), 1),
        q(46, 6, t6, "Which interface is easier for beginners?", listOf("CLI", "GUI", "Machine code", "Assembly"), 1),
        // T6: Short S12–S13 & Long L8
        s(12, 6, t6, "Differentiate between CLI and GUI. ★",
            "CLI: typed text commands, steep learning curve, very low resources, fast for experts/scripting, high precision (Bash, CMD, PowerShell).\nGUI: mouse/touch WIMP elements, intuitive/easy, high RAM/graphics usage, slower for repetitive tasks (Windows, macOS, GNOME).",
            listOf("2.5M: Interaction, ease of use & resource requirements", "2.5M: Speed/automation, precision & examples")),
        s(13, 6, t6, "Name the types of user interface.",
            "1. Command Line Interface (CLI), 2. Graphical User Interface (GUI), 3. Menu-driven interface (ATM, BIOS), 4. Touch interface (smartphones), 5. Voice / Natural User Interface (Alexa, Siri), 6. Form-based / Web interface.",
            listOf("5M: Naming and briefly illustrating at least 5–6 UI types")),
        l(8, 6, t6, 10L, "Explain Command Line Interface and Graphical User Interface with their advantages, disadvantages and commonly used commands.",
            "1. CLI: shell reads typed commands. Adv: lightweight, fast, scriptable, SSH remote admin. Disadv: steep learning curve, no visual cues, typo-prone.\nCommon commands (Linux/macOS vs Windows): list (ls/dir), change dir (cd/cd), make dir (mkdir/md), copy (cp/copy), move/rename (mv/move), delete (rm/del), show file (cat/type), current dir (pwd/cd), clear (clear/cls).\n2. GUI: WIMP (Windows, Icons, Menus, Pointer; Xerox PARC → Mac/Windows). Adv: beginner-friendly, multitasking, visual feedback, drag-and-drop. Disadv: higher RAM/CPU/GPU overhead, slower for bulk automation.",
            listOf("3M: CLI explanation with advantages & disadvantages", "3M: Command comparison table (Linux/macOS vs Windows)", "3M: GUI (WIMP) explanation with advantages & disadvantages", "1M: Practical conclusion")),

        // T7: Objective Q47–Q54
        q(47, 7, t7, "The default file system of modern Windows is:", listOf("FAT16", "NTFS", "ext4", "APFS"), 1),
        q(48, 7, t7, "The default file system of most Linux distributions is:", listOf("NTFS", "ext4", "APFS", "FAT32"), 1),
        q(49, 7, t7, "The default file system of modern macOS is:", listOf("APFS", "ext3", "NTFS", "exFAT"), 0),
        q(50, 7, t7, "The top-level directory in Linux is called:", listOf("C:\\", "/ (root)", "Desktop", "Home"), 1),
        q(51, 7, t7, "The maximum size of a single file in FAT32 is:", listOf("1 GB", "2 GB", "4 GB", "16 GB"), 2),
        q(52, 7, t7, "Which operating system treats file names as case-sensitive?", listOf("Windows (default)", "Linux", "DOS", "None"), 1),
        q(53, 7, t7, "In Linux, the permission set rwxr-xr-x is numerically:", listOf("644", "755", "777", "700"), 1),
        q(54, 7, t7, "A file system is responsible for:", listOf("Compiling programs", "Organising, storing and retrieving data on storage devices", "Printing", "Networking only"), 1),
        // T7: Short S14–S15 & Long L9
        s(14, 7, t7, "What is a file system? List common file attributes and operations. ★",
            "A file system is the method and data structure an OS uses to name, store, organize, and retrieve files on storage.\nAttributes: name, type, size, location, protection/permissions, timestamps (created/modified), owner.\nOperations: create, open, read, write, seek, close, delete, rename, truncate.",
            listOf("1M: Definition", "2M: Common file attributes", "2M: Common file operations")),
        s(15, 7, t7, "Compare the file systems of Windows, Linux and macOS. ★",
            "Windows: NTFS (FAT32/exFAT), drive letters C:\\ D:\\, separator \\, case-insensitive, ACLs, journaling (NTFS).\nLinux: ext4 (XFS/Btrfs), single tree from / root, separator /, case-sensitive, rwx owner/group/others, journaling.\nmacOS: APFS (HFS+), single tree from /, separator /, usually case-insensitive, POSIX + ACLs, copy-on-write journaling.",
            listOf("2M: File systems, structure & path separators", "3M: Case-sensitivity, permissions & journaling comparison")),
        l(9, 7, t7, 12L, "Explain file system basics – file concept, directory structure, access and allocation methods – with examples from Windows, Linux and macOS. ★",
            "1. File concept & types: text, binary, executable, directory, device files.\n2. Directory structures: single-level, two-level, tree-structured (modern OSs), acyclic-graph; absolute vs relative paths; Linux tree (/bin, /etc, /home, /usr, /var, /tmp, /dev, /boot).\n3. Access methods: sequential, direct (random), indexed.\n4. Allocation methods: Contiguous (external frag), Linked (slow random access), Indexed (index block overhead).\n5. Protection: Linux rwx (chmod 755 = rwxr-xr-x), Windows ACLs, macOS POSIX+ACLs.\n6. OS highlights: Windows NTFS (EFS/compression), Linux ext4/XFS/Btrfs, macOS APFS (snapshots, copy-on-write, cloning).",
            listOf("2M: File concept & types", "2.5M: Directory structures, paths & Linux root tree", "2.5M: Access methods & Contiguous/Linked/Indexed allocation table", "2M: File protection (chmod 755, ACLs)", "3M: Windows, Linux & macOS file system comparison"))
    )
}
