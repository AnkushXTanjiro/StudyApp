package com.example.model

/**
 * CFET Unit 1.0: Fundamentals of Computer Systems
 * 51 Objective (1M) + 17 Short (5M) + 10 Long (10–12M) = 78 questions across Topics T1–T7.
 */
internal fun cfetUnit1Questions(): List<TestQuestion> {
    val uId = "u_100108_1"
    val uTitle = "Unit 1.0: Fundamentals of Computer Systems"

    val t1 = "T1. Classification & Functional Units"
    val t2 = "T2. CPU Organization & Working"
    val t3 = "T3. Memory Architecture & Hierarchy"
    val t4 = "T4. CPU Registers & Cache Impact"
    val t5 = "T5. Motherboard & Components"
    val t6 = "T6. Von-Neumann Architecture"
    val t7 = "T7. Languages, Compiler & Interpreter"

    fun q(n: Int, tn: Int, tt: String, p: String, opts: List<String>, ans: Int, f: String = "", exp: String = "") =
        CfetQuestionBank.mcq(1, uId, uTitle, n, tn, tt, p, opts, ans, f, "NONE", exp)

    fun s(n: Int, tn: Int, tt: String, p: String, ans: String, sc: List<String>, f: String = "") =
        CfetQuestionBank.shortQ(1, uId, uTitle, n, tn, tt, p, ans, sc, f)

    fun l(n: Int, tn: Int, tt: String, m: Long, p: String, ans: String, sc: List<String>, f: String = "") =
        CfetQuestionBank.longQ(1, uId, uTitle, n, tn, tt, m, p, ans, sc, f)

    return listOf(
        // T1: Objective Q1–Q8
        q(1, 1, t1, "Which type of computer can process both analog and digital data?", listOf("Analog computer", "Digital computer", "Hybrid computer", "Mainframe"), 2, exp = "Hybrid = analog + digital (e.g., ICU monitors)."),
        q(2, 1, t1, "Which of the following is NOT a classification of computers on the basis of size?", listOf("Supercomputer", "Mainframe", "Hybrid", "Minicomputer"), 2, exp = "Hybrid is a classification by type of data handled."),
        q(3, 1, t1, "Which computers are used for weather forecasting and nuclear simulation?", listOf("Microcomputers", "Supercomputers", "Minicomputers", "Workstations"), 1),
        q(4, 1, t1, "Which functional unit of a computer controls and coordinates all other units?", listOf("ALU", "Memory unit", "Control unit", "Output unit"), 2),
        q(5, 1, t1, "First generation computers used which electronic component?", listOf("Transistors", "Vacuum tubes", "ICs", "Microprocessors"), 1, exp = "e.g., ENIAC, UNIVAC."),
        q(6, 1, t1, "The ALU and CU together form the:", listOf("Memory", "CPU", "Motherboard", "Chipset"), 1),
        q(7, 1, t1, "Which of the following is an input device?", listOf("Plotter", "Scanner", "Speaker", "Monitor"), 1),
        q(8, 1, t1, "Third generation computers were based on:", listOf("Vacuum tubes", "Transistors", "Integrated Circuits", "VLSI"), 2),
        // T1: Short S1–S3 & Long L1–L2
        s(1, 1, t1, "Classify computers on the basis of size and give one use of each. ★",
            "1. Supercomputer: Fastest, thousands of processors measured in FLOPS — weather forecasting, nuclear simulation, space research (PARAM, Fugaku).\n2. Mainframe: Large, supports thousands of simultaneous users with high reliability — banks, railways, insurance, airlines.\n3. Minicomputer: Mid-sized multi-user (tens to hundreds) — small-business servers, industrial process control.\n4. Microcomputer: Single-user built around a microprocessor — desktop, laptop, tablet, smartphone.",
            listOf("1.5M: Supercomputer features & use", "1.5M: Mainframe features & use", "1M: Minicomputer features & use", "1M: Microcomputer features & use")),
        s(2, 1, t1, "Differentiate between analog, digital and hybrid computers. ★",
            "Analog handles continuous physical quantities (low accuracy, limited storage — speedometer, thermometer). Digital handles discrete binary 0/1 data (high accuracy, large storage — PC, laptop). Hybrid combines both analog and digital (high accuracy, large storage — ICU/ECG monitor, petrol pump).",
            listOf("2M: Data handled & accuracy comparison", "1.5M: Storage & working differences", "1.5M: Real-world examples for all three")),
        s(3, 1, t1, "Write a short note on the generations of computers.",
            "1st Gen (1940–56): Vacuum tubes (ENIAC, UNIVAC). 2nd Gen (1956–63): Transistors (IBM 1401). 3rd Gen (1964–71): Integrated Circuits SSI/MSI (IBM 360). 4th Gen (1971–present): Microprocessor LSI/VLSI (Intel 4004, PCs). 5th Gen (Present–future): ULSI, AI, Quantum (Robots, AI systems). Trend: smaller, faster, cheaper, more reliable.",
            listOf("3M: All 5 generations with period & technology", "1M: Representative examples", "1M: Overall evolution trend")),
        l(1, 1, t1, 10L, "Explain the functional units of a computer with a neat block diagram. ★",
            "Five functional units: (1) Input Unit converts user data/instructions to binary (keyboard, mouse, scanner). (2) Output Unit converts binary results to human-readable form (monitor, printer). (3) Memory Unit stores programs, data & intermediate results (Primary: RAM/ROM/Cache; Secondary: HDD/SSD). (4) ALU performs arithmetic (+,−,×,÷) and logic (AND, OR, NOT, compare). (5) Control Unit fetches, decodes instructions and issues control signals. CPU = ALU + CU + Registers. Data flow: Input → Memory → (CU+ALU) → Memory → Output.",
            listOf("2M: Neat block diagram showing Input, CPU (ALU+CU+Registers), Memory, Output & buses", "4M: Explanation of Input, Output & Memory units", "3M: Explanation of ALU & Control Unit", "1M: Data & control signal flow summary")),
        l(2, 1, t1, 10L, "Explain in detail the classification of computers (by size, by purpose and by type of data).",
            "A. By data handled: Analog (continuous signals — speedometer), Digital (binary — PC), Hybrid (combined — ICU monitor).\nB. By purpose: General purpose (PC, laptop) vs Special purpose (ATM, traffic controller, washing machine).\nC. By size & capacity: Supercomputer (Peta-FLOPS, PARAM Siddhi/Summit), Mainframe (thousands of users, IBM Z), Minicomputer (tens–hundreds of users, PDP-11/VAX), Microcomputer (single user — desktop, laptop, tablet, workstation, embedded).",
            listOf("3M: Classification by data handled (Analog/Digital/Hybrid)", "2M: Classification by purpose (General vs Special)", "4M: Classification by size/capacity with speed, cost & examples", "1M: Microcomputer subtypes & conclusion")),

        // T2: Objective Q9–Q15
        q(9, 2, t2, "The correct sequence of the instruction cycle is:", listOf("Decode–Fetch–Execute", "Fetch–Decode–Execute", "Execute–Fetch–Decode", "Fetch–Execute–Decode"), 1),
        q(10, 2, t2, "Which register holds the address of the next instruction to be fetched?", listOf("IR", "MAR", "Program Counter", "Accumulator"), 2),
        q(11, 2, t2, "The speed of a CPU is measured in:", listOf("Bytes", "Hertz (GHz)", "Pixels", "Watts"), 1, exp = "Clock speed in GHz; performance also in MIPS/FLOPS."),
        q(12, 2, t2, "Which unit of the CPU generates timing and control signals?", listOf("ALU", "Control Unit", "Cache", "Register"), 1),
        q(13, 2, t2, "The number of bits a CPU can process at one time is called:", listOf("Bandwidth", "Word length", "Bus width", "Clock rate"), 1),
        q(14, 2, t2, "Which of the following is NOT a part of the CPU?", listOf("ALU", "Control Unit", "Registers", "Hard disk"), 3),
        q(15, 2, t2, "Multi-core processor means:", listOf("Many CPUs on separate boards", "Multiple processing cores on one chip", "Multiple RAMs", "Multiple monitors"), 1),
        // T2: Short S4–S5 & Long L3
        s(4, 2, t2, "Explain the instruction cycle (fetch–decode–execute) of a CPU. ★",
            "1. Fetch: PC → MAR; Memory[MAR] → MDR → IR; PC = PC + 1.\n2. Decode: Control Unit decodes opcode in IR and locates operands.\n3. Execute: ALU, memory, or I/O performs the required operation.\n4. Store: Result is written back to a register or memory. Loop continues until HALT.",
            listOf("1.5M: Flow/register transfer sequence", "1M: Fetch stage", "1M: Decode stage", "1.5M: Execute & Store stages"),
            f = "PC → MAR ; Memory[MAR] → MDR → IR ; PC ← PC + 1"),
        s(5, 2, t2, "Describe the roles of ALU and Control Unit.",
            "ALU: performs arithmetic (+,−,×,÷) and logic (AND, OR, NOT, XOR, compare, shift) on register data and sets status flags (zero, carry, overflow).\nControl Unit: fetches and decodes instructions, generates timing/control signals, directs data flow between CPU/memory/I/O, and controls execution sequence via PC.",
            listOf("2.5M: Four key roles of ALU including flags", "2.5M: Four key roles of Control Unit")),
        l(3, 2, t2, 12L, "Describe the organization of a CPU and explain how it works. ★",
            "Organization: (1) ALU (arithmetic/logic), (2) Control Unit (fetch/decode/control signals), (3) Registers (PC, IR, MAR, MDR, ACC), (4) Internal buses & system clock, (5) L1/L2 Cache.\nWorking: Repeats Fetch (PC→MAR→MDR→IR, PC+1) → Decode (CU interprets opcode) → Execute (ALU/Mem/I-O) → Store billions of times/sec. Modern CPUs add pipelining, multi-core, and cache.\nPerformance factors: clock speed (GHz), cores/threads, word length (32/64-bit), cache size, bus width & RAM speed.",
            listOf("3M: CPU block diagram with ALU, CU, Registers, Memory & I/O", "3M: Organization components (ALU, CU, Registers, Buses, Cache)", "4M: Detailed Fetch-Decode-Execute-Store working + pipelining", "2M: Factors affecting CPU performance & conclusion")),

        // T3: Objective Q16–Q23
        q(16, 3, t3, "Which of the following memories is volatile?", listOf("ROM", "RAM", "EPROM", "Flash BIOS"), 1, exp = "RAM loses its contents when power is off."),
        q(17, 3, t3, "Which memory is the fastest?", listOf("Hard disk", "RAM", "Cache", "CPU registers"), 3),
        q(18, 3, t3, "1 KB is equal to:", listOf("1000 bits", "1024 bytes", "1024 bits", "8 bytes"), 1),
        q(19, 3, t3, "Cache memory is placed between:", listOf("RAM and Hard disk", "CPU and RAM", "ROM and RAM", "CPU and I/O"), 1),
        q(20, 3, t3, "Cache memory is built using:", listOf("DRAM", "SRAM", "EEPROM", "Magnetic tape"), 1),
        q(21, 3, t3, "Which memory needs periodic refreshing?", listOf("SRAM", "DRAM", "ROM", "PROM"), 1, exp = "DRAM stores bits as charge in capacitors that leak."),
        q(22, 3, t3, "Virtual memory is a part of the ______ used as an extension of RAM.", listOf("Cache", "Hard disk", "Register", "ROM"), 1),
        q(23, 3, t3, "The smallest unit of memory is:", listOf("Byte", "Nibble", "Bit", "Word"), 2),
        // T3: Short S6–S8 & Long L4–L5
        s(6, 3, t3, "Differentiate between RAM and ROM. ★",
            "RAM (Random Access Memory): volatile, read/write, stores active programs & data, faster & costlier per GB, types: SRAM, DRAM (DDR).\nROM (Read Only Memory): non-volatile, mostly read-only, stores BIOS/bootstrap firmware, slower & cheaper, types: PROM, EPROM, EEPROM, Flash.",
            listOf("2M: Full form, volatility & read/write operation", "1.5M: Usage & speed/cost", "1.5M: Types of RAM and ROM")),
        s(7, 3, t3, "Differentiate between SRAM and DRAM.",
            "SRAM uses flip-flops (6 transistors/bit), needs no refresh, is faster/expensive/low density, used for cache.\nDRAM uses 1 transistor + 1 capacitor/bit, requires periodic refresh, is slower/cheaper/high density, used for main memory.",
            listOf("2M: Circuit structure & refresh requirement", "1.5M: Speed, cost & density", "1.5M: Primary applications")),
        s(8, 3, t3, "Write a short note on types of ROM.",
            "1. MROM (Mask ROM): factory-programmed, unchangeable. 2. PROM: programmable once by user. 3. EPROM: erased with UV light and reprogrammed. 4. EEPROM: erased electrically byte-wise (BIOS). 5. Flash: block-wise electrical erase (pen drives, SSDs).",
            listOf("1M each for MROM, PROM, EPROM, EEPROM, Flash")),
        l(4, 3, t3, 10L, "Explain the memory hierarchy with a neat diagram. Why is it needed? ★",
            "Memory hierarchy organizes storage from top to bottom: Registers (~0.3 ns, bytes, volatile) → Cache L1/L2/L3 (SRAM, 1–10 ns, KB–MB) → Main Memory RAM (DRAM, 50–100 ns, GB) → Secondary SSD/HDD (0.1–10 ms, GB–TB, non-volatile) → Tertiary Tape/Cloud (seconds, TB–PB).\nPrinciple: Locality of reference (temporal & spatial).\nWhy needed: bridges CPU–RAM speed gap, balances cost vs performance, gives illusion of large fast memory. Moving down: cost/bit ↓, capacity ↑, access time ↑.",
            listOf("3M: Memory hierarchy pyramid diagram with latency & capacity", "3M: Comparison table (Level, Tech, Speed, Capacity, Volatility)", "2M: Locality of reference (temporal & spatial)", "2M: Need for hierarchy & trade-off trend")),
        l(5, 3, t3, 10L, "Explain memory architecture and representation (bit, byte, word, address, capacity).",
            "Architecture: array of storage cells each holding a word at a unique address, accessed via Address Bus (n unidirectional lines → MAR), Data Bus (m bidirectional lines ↔ MDR), and Control Bus (R/W, CS).\nUnits: 1 Bit (0/1), 1 Nibble (4 bits), 1 Byte (8 bits), 1 KB = 2¹⁰ B, 1 MB = 2²⁰ B, 1 GB = 2³⁰ B, 1 TB = 2⁴⁰ B.\nFormula: Capacity = 2ⁿ × m bits. Example: 16 address lines, 8-bit word → 2¹⁶ × 1 B = 64 KB. Byte-addressable storage uses little-endian or big-endian order.",
            listOf("3M: Cell array architecture & Address/Data/Control buses diagram", "3M: Bit, nibble, byte, KB, MB, GB, TB table", "2M: Capacity formula 2ⁿ × m with worked 64 KB example", "2M: Byte-addressability & endianness"),
            f = "Capacity = 2ⁿ × m bits"),

        // T4: Objective Q24–Q30
        q(24, 4, t4, "Which register holds the instruction currently being executed?", listOf("PC", "IR", "MAR", "MDR"), 1),
        q(25, 4, t4, "MAR stands for:", listOf("Memory Access Register", "Memory Address Register", "Main Address Register", "Machine Address Register"), 1),
        q(26, 4, t4, "The register that stores intermediate results of ALU operations is:", listOf("Accumulator", "PC", "IR", "SP"), 0),
        q(27, 4, t4, "A 'cache hit' occurs when:", listOf("Data is found in cache", "Data is not in cache", "Cache is full", "CPU is idle"), 0),
        q(28, 4, t4, "Cache memory works on the principle of:", listOf("Locality of reference", "Time-sharing", "Spooling", "Paging"), 0),
        q(29, 4, t4, "Which cache level is the smallest and fastest?", listOf("L1", "L2", "L3", "L4"), 0),
        q(30, 4, t4, "If hit ratio increases, the average memory access time:", listOf("Increases", "Decreases", "Remains same", "Becomes infinite"), 1),
        // T4: Short S9–S10 & Long L6
        s(9, 4, t4, "Explain the different types of CPU registers. ★",
            "PC (next instruction address), IR (current instruction), MAR (memory address to read/write), MDR/MBR (data read/written), ACC (intermediate ALU results), Status/Flag Register (carry, zero, sign, overflow), SP (top of stack), General-purpose registers (AX, BX, R0–R15).",
            listOf("3M: Special-purpose registers (PC, IR, MAR, MDR, ACC)", "2M: Flag register, Stack Pointer & General-purpose registers")),
        s(10, 4, t4, "What is cache memory? How does it improve system performance?",
            "Cache is small, fast SRAM between CPU and RAM holding copies of frequently accessed data/instructions. Hit serves in 1–10 ns; miss fetches from RAM (50–100 ns). Locality of reference yields 90–99% hit ratio. Effective access time Teff = h·Tc + (1−h)·Tm. Example: h=0.9, Tc=10 ns, Tm=100 ns → Teff = 19 ns (vs 100 ns).",
            listOf("1.5M: Definition & placement", "1.5M: Hit/miss & locality of reference", "2M: Teff formula with numerical example"),
            f = "T_eff = h × T_c + (1 − h) × T_m"),
        l(6, 4, t4, 12L, "Explain CPU registers and discuss the impact of cache memory on system performance. ★",
            "1. CPU Registers: fastest in-CPU storage (PC, IR, MAR/MDR, ACC, Flags, SP).\n2. Cache levels: L1 (32–64 KB, inside core, fastest), L2 (256 KB–2 MB/core), L3 (shared, multi-MB).\n3. Working: CPU checks cache → HIT (fast return) or MISS (fetch block from RAM, store in cache).\n4. Mapping: Direct, Fully Associative, Set-Associative. Replacement: LRU, FIFO, Random. Write: write-through vs write-back.\n5. Performance impact: Teff = h·Tc + (1−h)·Tm; boosts CPU utilization, cuts bus traffic; thrashing occurs on poor locality. Numerical: h=0.95, Tc=5 ns, Tm=80 ns → Teff = 8.75 ns.",
            listOf("3M: CPU registers table & role", "2M: Cache levels (L1/L2/L3) & Hit/Miss flow", "3M: Mapping (Direct/Assoc/Set-Assoc), Replacement & Write policies", "4M: Performance impact, Teff formula & worked numerical (8.75 ns)"),
            f = "T_eff = h·T_c + (1 − h)·T_m = 0.95×5 + 0.05×80 = 8.75 ns"),

        // T5: Objective Q31–Q37
        q(31, 5, t5, "The main circuit board of a computer is the:", listOf("Chipset", "Motherboard", "Backplane", "Daughter card"), 1),
        q(32, 5, t5, "The CMOS battery on the motherboard is used to:", listOf("Power the CPU", "Retain BIOS settings, date and time", "Charge USB devices", "Cool the CPU"), 1),
        q(33, 5, t5, "Which slot is used to install a graphics card?", listOf("DIMM", "SATA", "PCI Express (PCIe)", "PS/2"), 2),
        q(34, 5, t5, "The BIOS is stored in:", listOf("RAM", "Flash ROM / EEPROM", "Cache", "Hard disk"), 1),
        q(35, 5, t5, "Which bus carries the memory address from the CPU?", listOf("Data bus", "Control bus", "Address bus", "Power bus"), 2),
        q(36, 5, t5, "RAM modules are inserted into:", listOf("PCIe slots", "DIMM slots", "SATA ports", "ISA slots"), 1),
        q(37, 5, t5, "The chipset on a motherboard:", listOf("Stores data permanently", "Manages data flow between CPU, memory and peripherals", "Displays output", "Cools the CPU"), 1),
        // T5: Short S11–S12 & Long L7
        s(11, 5, t5, "Name and explain the main components of a motherboard. ★",
            "CPU socket (LGA/PGA/AM5), RAM (DIMM) slots, Chipset (Northbridge/Southbridge or PCH), BIOS/UEFI chip (POST & booting), CMOS battery (BIOS settings & RTC), Expansion slots (PCIe/PCI), Storage connectors (SATA, M.2/NVMe), 24-pin ATX Power connector & I/O ports (USB, HDMI, LAN, audio).",
            listOf("3M: Core slots & chips (CPU socket, DIMM, Chipset, BIOS/UEFI, CMOS)", "2M: Expansion (PCIe), Storage (SATA/M.2), ATX power & I/O ports")),
        s(12, 5, t5, "Write a short note on system buses.",
            "1. Address bus: unidirectional (CPU → memory), carries memory/I-O address; n lines address 2ⁿ locations.\n2. Data bus: bidirectional, carries actual data; width = word size.\n3. Control bus: bidirectional, carries Read/Write, interrupt, clock, and reset signals.",
            listOf("2M: Address bus (direction & 2ⁿ addressing)", "1.5M: Data bus (bidirectional & word size)", "1.5M: Control bus signals")),
        l(7, 5, t5, 10L, "What is a motherboard? Explain its components with the help of a layout diagram.",
            "Motherboard (mainboard) is the main PCB connecting CPU, memory, storage & peripherals via buses and power tracks. Components: CPU socket + heatsink/fan, DIMM RAM slots (DDR4/5), Chipset, BIOS/UEFI chip, CMOS battery, PCIe expansion slots, SATA/M.2 connectors, 24-pin ATX & 4/8-pin CPU power connectors, I/O panel (USB, HDMI, Ethernet, audio), System buses (Address, Data, Control). Form factors: ATX, micro-ATX, mini-ITX.",
            listOf("3M: Simplified motherboard layout diagram", "5M: Detailed explanation of all 10 major components", "2M: Form factors (ATX, micro-ATX, mini-ITX) & conclusion")),

        // T6: Objective Q38–Q43
        q(38, 6, t6, "The stored-program concept was proposed by:", listOf("Charles Babbage", "John von Neumann", "Alan Turing", "Blaise Pascal"), 1),
        q(39, 6, t6, "In Von Neumann architecture, instructions and data are stored in:", listOf("Separate memories", "Same memory", "Only ROM", "Only registers"), 1),
        q(40, 6, t6, "Von Neumann bottleneck refers to:", listOf("Slow ALU", "Limited data transfer rate between CPU and memory over a shared bus", "Small cache", "Slow keyboard"), 1),
        q(41, 6, t6, "Which architecture has separate memories and buses for instructions and data?", listOf("Von Neumann", "Harvard", "Neural", "Dataflow"), 1),
        q(42, 6, t6, "Instructions in a Von Neumann machine are executed:", listOf("In parallel always", "Sequentially", "Randomly", "Only by ALU"), 1),
        q(43, 6, t6, "Which of these is a component of the Von Neumann model?", listOf("Control unit", "Compiler", "Operating system", "Internet"), 0),
        // T6: Short S13–S14 & Long L8
        s(13, 6, t6, "What is the Von Neumann bottleneck? How can it be reduced?",
            "Because one shared bus carries both instructions and data, the CPU cannot fetch an instruction and read/write data simultaneously, leaving the CPU idle waiting on memory. Remedies: L1/L2/L3 cache memory, pipelining & prefetching, branch prediction, Modified Harvard design (separate I-cache & D-cache), wider/faster buses and multi-core processors.",
            listOf("2M: Clear explanation of shared-bus bottleneck", "3M: Five practical remedies (cache, pipelining, branch prediction, split I/D-cache, wider buses)")),
        s(14, 6, t6, "Differentiate between Von Neumann and Harvard architecture. ★",
            "Von Neumann: single memory for data+instructions, one shared bus, slower (bottleneck), simple/cheaper, used in general PCs/servers.\nHarvard: separate instruction & data memory, separate buses, faster (parallel access), complex/costlier, used in microcontrollers & DSPs (AVR, PIC).",
            listOf("2M: Memory & bus differences", "1.5M: Speed & cost/complexity", "1.5M: Typical applications (PCs vs MCUs/DSPs)")),
        l(8, 6, t6, 12L, "Explain Von Neumann architecture with a neat diagram. State its features and limitations. ★",
            "Proposed in 1945 by John von Neumann based on the stored-program concept: instructions and data share the same read–write memory and are executed sequentially. Components: CPU (ALU + CU + Registers), Main Memory, I/O Units, System Bus. Working: load program/data → PC fetches instruction to IR → CU decodes & executes via ALU/memory → PC increments until HALT. Features: stored-program, sequential execution, single memory/bus, binary representation. Limitations: Von Neumann bottleneck, accidental overwrite/buffer-overflow risk, limited parallelism.",
            listOf("3M: Neat Von Neumann architecture block diagram", "3M: Components & 4-step working cycle", "3M: Core features (stored-program, sequential, single bus)", "3M: Limitations (bottleneck, security, parallelism) & conclusion")),

        // T7: Objective Q44–Q51
        q(44, 7, t7, "The only language directly understood by the CPU is:", listOf("Assembly", "Machine language", "C", "Python"), 1),
        q(45, 7, t7, "An assembler converts:", listOf("High-level code to machine code", "Assembly code to machine code", "Machine code to C", "Source code to byte code"), 1),
        q(46, 7, t7, "A compiler translates the program:", listOf("Line by line", "Entire program at once", "Only variables", "Only functions"), 1),
        q(47, 7, t7, "Which translator executes the program line by line?", listOf("Compiler", "Assembler", "Interpreter", "Linker"), 2),
        q(48, 7, t7, "Mnemonics such as ADD, MOV, SUB are used in:", listOf("Machine language", "Assembly language", "High-level language", "4GL"), 1),
        q(49, 7, t7, "Which of the following is a high-level language?", listOf("Binary code", "Assembly", "C++", "Opcode"), 2),
        q(50, 7, t7, "Which is generally faster at execution?", listOf("Interpreted code", "Compiled code", "Both same", "Depends on the monitor"), 1),
        q(51, 7, t7, "The program that combines object files and libraries into an executable is:", listOf("Loader", "Linker", "Debugger", "Editor"), 1),
        // T7: Short S15–S17 & Long L9–L10
        s(15, 7, t7, "Differentiate between compiler and interpreter. ★",
            "Compiler translates whole program at once, generates object/executable file, fast execution, reports all errors after full scan, uses more memory (C, C++, javac).\nInterpreter translates line by line, no separate object file, slower execution, stops at first error, uses less memory (Python, JavaScript, Ruby, BASIC).",
            listOf("2M: Translation unit, output & execution speed", "2M: Error reporting & memory usage", "1M: Examples for both")),
        s(16, 7, t7, "Compare machine language, assembly language and high-level language. ★",
            "Machine: 0s & 1s, no translator, machine-dependent, very difficult, fastest.\nAssembly: mnemonics (ADD, MOV), Assembler, machine-dependent, difficult, fast.\nHigh-level: English-like statements, Compiler/Interpreter, portable, easy to use, slower than low-level.",
            listOf("2M: Form & translator comparison", "2M: Portability, ease of use & speed", "1M: Clear tabular summary")),
        s(17, 7, t7, "Write a short note on assembler, linker and loader.",
            "Assembler: converts assembly language into machine (object) code.\nLinker: combines object modules and library routines into a single executable and resolves external references.\nLoader: loads the executable from disk into RAM and initiates CPU execution.",
            listOf("1.5M: Assembler role", "2M: Linker role & external reference resolution", "1.5M: Loader role")),
        l(9, 7, t7, 10L, "Explain the levels of programming languages (machine, assembly, high-level) with merits and demerits.",
            "1. Machine language (1st Gen): binary opcodes directly executed by CPU. Merits: fastest, no translator. Demerits: hard to write/debug, machine-dependent, error-prone.\n2. Assembly language (2nd Gen): mnemonics & symbolic addresses (MOV A, 61H; ADD B); needs assembler. Merits: easier than binary, efficient hardware control. Demerits: machine-dependent, hard for large systems.\n3. High-level language (3rd Gen+): C, C++, Java, Python; needs compiler/interpreter. Merits: easy, portable, concise, easier debugging. Demerits: slower, less direct hardware control.\nExample: c = a + b (C) ⇒ MOV AX,a / ADD AX,b / MOV c,AX (Assembly) ⇒ hex/binary machine code.",
            listOf("2.5M: Machine language with merits & demerits", "2.5M: Assembly language with merits & demerits", "3M: High-level language with merits & demerits", "2M: Comparison table & c = a + b translation example")),
        l(10, 7, t7, 12L, "What are language translators? Explain compiler, interpreter and assembler with the compilation process. ★",
            "A language translator converts source code into executable target code. (1) Assembler: Assembly → machine code (1-to-1). (2) Compiler: whole HLL → object code, reports all syntax errors, fast repeated runs. (3) Interpreter: executes statement-by-statement, easy debugging, slower runtime.\nCompilation pipeline: Source (.c) → Preprocessor → Compiler → Assembly (.s) → Assembler → Object (.o) → Linker + Libraries → Executable (.exe) → Loader → RAM → CPU.\nCompiler phases: Lexical, Syntax, Semantic, Intermediate code generation, Optimization, Target code generation. Hybrid: Java bytecode + JVM JIT; Python bytecode.",
            listOf("3M: Definition + Assembler, Compiler & Interpreter", "3M: Complete compilation pipeline diagram (.c → .exe → RAM)", "3M: Six phases of a compiler", "3M: Comparison table & hybrid (Java/Python) note"))
    )
}
