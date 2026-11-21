package com.example.model

/**
 * BEEE Unit 5.0: Digital, Analog Electronics, Measurement & Electrical Safety — Full 60-question bank (Q1–Q60).
 */
internal fun beeeUnit5Questions(): List<TestQuestion> {
    val u = 5
    val uId = "u_100111_5"
    val uTitle = "Unit 5.0: Digital, Analog Electronics & Electrical Safety"

    fun m(q: Int, t: Int, topic: String, p: String, opts: List<String>, ans: Int, f: String = "", d: String = "NONE", exp: String = "") =
        BeeeQuestionBank.mcq(u, uId, uTitle, q, t, topic, p, opts, ans, f, d, exp)

    fun s(q: Int, t: Int, topic: String, marks: Long, p: String, ans: String, scheme: List<String>, f: String = "", d: String = "NONE") =
        BeeeQuestionBank.subj(u, uId, uTitle, q, t, topic, marks, p, ans, scheme, f, d)

    val t1 = "Number Systems & Binary Arithmetic"
    val t2 = "Logic Gates, Universal Gates & Boolean Algebra"
    val t3 = "LEDs, 7-Segment Display & Digital/Analog ICs"
    val t4 = "IC 7805/7912, Multimeter & CRO"
    val t5 = "Sensors, Transducers & Power Systems"
    val t6 = "Electrical Safety: Fuse, MCB & MCCB"
    val t7 = "Earthing: Purpose & Methods"

    return listOf(
        // TOPIC 1
        m(1, 1, t1, "The binary equivalent of decimal 10 is", listOf("1010", "1100", "1001", "0110"), 0),
        m(2, 1, t1, "The hexadecimal number 2D in decimal is", listOf("35", "45", "55", "29"), 1, "2×16 + 13×1 = 45"),
        m(3, 1, t1, "Binary 1 + 1 gives", listOf("2", "10", "11", "0 with no carry"), 1),
        m(4, 1, t1, "The octal equivalent of binary 101 110 is", listOf("56", "65", "46", "72"), 0),
        m(5, 1, t1, "How many bits are needed to represent one hexadecimal digit?", listOf("2", "3", "4", "8"), 2),
        m(6, 1, t1, "The 2's complement of 1011 is", listOf("0100", "0101", "1010", "1100"), 1, "1's comp (0100) + 1 = 0101"),
        s(
            7, 1, t1, 5,
            "Convert (45)₁₀ into binary, octal and hexadecimal. Convert (1011.101)₂ into decimal.",
            "Decimal 45 → Binary (repeated ÷2): (101101)₂. Octal (3-bit groups 101 101): (55)₈. Hexadecimal (4-bit groups 0010 1101): (2D)₁₆. Binary (1011.101)₂ → Decimal: 1×8 + 0×4 + 1×2 + 1×1 + 1×0.5 + 0×0.25 + 1×0.125 = 11.625.",
            listOf(
                "2M: Convert (45)₁₀ to (101101)₂ showing remainders",
                "1.5M: Group bits for Octal (55)₈ and Hex (2D)₁₆",
                "1.5M: Convert (1011.101)₂ = 11.625"
            ),
            "(45)₁₀ = (101101)₂ = (55)₈ = (2D)₁₆   |   (1011.101)₂ = 11.625"
        ),
        s(
            8, 1, t1, 5,
            "Perform: (i) 1011 + 1101, (ii) 1101 − 1011 using 2's complement.",
            "(i) Addition: 1011 (11) + 1101 (13) = 11000 (24), showing bit-by-bit carries. (ii) Subtraction via 2's complement: 2's complement of 1011 = 0100 + 1 = 0101. Add: 1101 + 0101 = 1 0010. Discard end-around carry ⇒ 0010 = 2 (13 − 11 = 2). Rule: carry out ⇒ positive result (discard carry); no carry ⇒ negative in 2's complement.",
            listOf(
                "2M: Binary addition 1011 + 1101 = 11000 with carry steps",
                "2M: 2's complement of 1011 (= 0101) and addition 1101 + 0101 = 1 0010",
                "1M: Discard carry rule ⇒ result 0010 (decimal 2)"
            ),
            "1011 + 1101 = 11000   |   1101 + (0101) = 1 0010 ⇒ 0010"
        ),
        s(
            9, 1, t1, 10,
            "Explain the binary, octal and hexadecimal number systems. Describe the conversion methods between them with one example each and explain binary addition and subtraction (with 1's / 2's complement).",
            "Positional value = Σ digit × base^pos (binary base 2, octal base 8, hex base 16). Conversions: Dec→Bin divide by 2 ((25)₁₀ = (11001)₂); Bin→Dec sum powers of 2; Bin→Oct/Hex group 3/4 bits ((11001)₂ = (31)₈ = (19)₁₆); Hex→Bin 4 bits/digit ((A5)₁₆ = 1010 0101). Binary addition rules (0+0=0, 0+1=1, 1+1=10, 1+1+1=11; ex: 0110+0111=1101). Subtraction: 1's comp = invert bits, 2's comp = 1's comp + 1; A − B = A + (2's comp of B); discard carry for positive result (ex: 1101 − 0110 = 1101 + 1010 = 1 0111 ⇒ 0111 = 7).",
            listOf(
                "3M: Explain Binary, Octal, Hex positional systems & bases",
                "3M: Conversion methods between all bases with worked examples",
                "2M: Binary addition rules & worked example",
                "2M: 1's and 2's complement subtraction method & worked example"
            )
        ),

        // TOPIC 2
        m(10, 2, t2, "The output of a 2-input AND gate is 1 only when", listOf("Both inputs are 1", "Any input is 1", "Both inputs are 0", "Inputs differ"), 0, "Y = A · B", "LOGIC_GATE"),
        m(11, 2, t2, "Which gates are called universal gates?", listOf("AND and OR", "NAND and NOR", "XOR and XNOR", "NOT and AND"), 1, "", "LOGIC_GATE"),
        m(12, 2, t2, "De Morgan's theorem states (A + B)' =", listOf("A' + B'", "A'B'", "AB", "A + B'"), 1, "(A + B)' = A' · B'"),
        m(13, 2, t2, "The output of an XOR gate is 1 when", listOf("Both inputs are same", "Inputs are different", "Both are 1", "Both are 0"), 1, "Y = A ⊕ B = A'B + AB'", "LOGIC_GATE"),
        m(14, 2, t2, "A + A'B simplifies to", listOf("A", "B", "A + B", "AB"), 2),
        m(15, 2, t2, "A NAND gate with both inputs tied together acts as a", listOf("AND gate", "NOT gate", "OR gate", "XOR gate"), 1, "Y = (A · A)' = A'", "LOGIC_GATE"),
        s(
            16, 2, t2, 5,
            "Draw the symbols and write the truth tables of AND, OR, NOT, NAND, NOR, XOR and XNOR gates.",
            "For inputs (A,B) = 00, 01, 10, 11: AND = 0,0,0,1; OR = 0,1,1,1; NAND = 1,1,1,0; NOR = 1,0,0,0; XOR = 0,1,1,0; XNOR = 1,0,0,1. NOT: 0→1, 1→0. Draw standard symbols: AND (D-shape), OR (curved shield), NOT (triangle + bubble), NAND/NOR (AND/OR + bubble), XOR/XNOR (extra input curve).",
            listOf(
                "2.5M: Draw all 7 standard logic gate symbols accurately",
                "2.5M: Complete truth tables for all 7 gates"
            ),
            "AND: A·B | OR: A+B | NAND: (AB)' | NOR: (A+B)' | XOR: A⊕B",
            "LOGIC_GATE"
        ),
        s(
            17, 2, t2, 5,
            "Why are NAND and NOR called universal gates? Realise NOT, AND and OR using NAND gates.",
            "Universal gates can implement any Boolean function on their own and are cheap/easy to fabricate. NAND realisations: NOT: tie both inputs Y = (A·A)' = A'; AND: NAND followed by NAND-NOT Y = ((AB)')' = AB; OR: invert A and B with NAND-NOTs then feed into NAND Y = (A'·B')' = A + B (De Morgan). NOR works analogously.",
            listOf(
                "1M: Explain why NAND & NOR are universal",
                "4M: Circuit diagrams & Boolean proof for NOT, AND, and OR using only NAND gates"
            ),
            "NOT: (A·A)'  |  AND: ((AB)')'  |  OR: (A'·B')' = A + B",
            "LOGIC_GATE"
        ),
        s(
            18, 2, t2, 5,
            "State and prove De Morgan's theorems using truth tables. Simplify Y = AB + A'B + AB'.",
            "Theorems: (A + B)' = A'·B' and (AB)' = A' + B'. Truth table for AB = 00, 01, 10, 11 verifies both sides match identically (1,0,0,0 and 1,1,1,0). Simplification: Y = AB + A'B + AB' = B(A + A') + AB' = B + AB' = A + B (using absorption A + A'B = A + B).",
            listOf(
                "2M: State both De Morgan's theorems",
                "1.5M: Verify both theorems using a complete 4-row truth table",
                "1.5M: Step-by-step Boolean simplification to Y = A + B"
            ),
            "(A + B)' = A'B',  (AB)' = A' + B'   |   Y = AB + A'B + AB' = A + B"
        ),
        s(
            19, 2, t2, 10,
            "Explain the basic and universal logic gates with symbols, Boolean expressions and truth tables. Prove that NAND is a universal gate and state the laws of Boolean algebra.",
            "Basic gates: AND (AB), OR (A+B), NOT (A'). Derived/Universal: NAND ((AB)'), NOR ((A+B)'), XOR (A'B+AB'), XNOR (AB+A'B'). NAND universality proof: construct NOT, AND, and OR purely from NAND. Boolean Laws: Commutative (A+B=B+A, AB=BA), Associative, Distributive (A(B+C)=AB+AC; A+BC=(A+B)(A+C)), Identity/Null, Complement (A+A'=1, AA'=0), Idempotent, Absorption (A+AB=A), De Morgan's.",
            listOf(
                "3M: Symbols, expressions, and truth tables of basic & universal gates",
                "3M: Complete proof of NAND universality (NOT, AND, OR diagrams)",
                "4M: State all Boolean algebra laws accurately with expressions"
            ),
            "A + AB = A  |  A + A'B = A + B  |  (AB)' = A' + B'",
            "LOGIC_GATE"
        ),

        // TOPIC 3
        m(20, 3, t3, "An LED emits light when it is", listOf("Reverse biased", "Forward biased", "Unbiased", "Over-heated"), 1),
        m(21, 3, t3, "A seven-segment display has ___ segments (excluding decimal point).", listOf("5", "7", "8", "10"), 1),
        m(22, 3, t3, "The IC 7447 is a BCD-to-seven-segment decoder for", listOf("Common-cathode display", "Common-anode display", "LCD", "Dot-matrix"), 1),
        m(23, 3, t3, "The IC 741 is a", listOf("Timer", "Op-amp", "Voltage regulator", "Counter"), 1),
        m(24, 3, t3, "To display '8' on a seven-segment display", listOf("All segments are ON", "Segments a–f only", "Only g is ON", "Only b and c"), 0),
        s(
            25, 3, t3, 5,
            "Explain the working and applications of an LED. Calculate the series resistor for a 5 V supply, V_F = 2 V and I_F = 10 mA.",
            "Working: Forward-biased p–n junction of direct band-gap semiconductor (GaAs, GaAsP, GaP, GaN); electron-hole recombination releases photons (electroluminescence); band-gap sets colour. Advantages: low power, long life (>50,000 h), fast switching, rugged. Uses: indicators, displays, remotes, lighting, optocouplers. Series current-limiting resistor: R = (V_S − V_F)/I_F = (5 − 2)/10 mA = 300 Ω.",
            listOf(
                "2M: Electroluminescence working principle & semiconductor materials",
                "1M: Advantages & practical applications",
                "2M: Correct calculation R = (5 − 2)/0.01 = 300 Ω"
            ),
            "R = (V_S − V_F) / I_F = (5 − 2) / 10 mA = 300 Ω"
        ),
        s(
            26, 3, t3, 5,
            "Explain a seven-segment display. Give the truth table for digits 0–9 (common-cathode, segments a–g).",
            "Seven LEDs (a–g) arranged in a figure-8. Common-cathode: cathodes grounded, segment lights on logic 1. Common-anode: anodes to V_CC, lights on logic 0. Driven by BCD decoders (7447 CA / 7448 CC). Common-cathode ON codes (abcdefg): 0→1111110, 1→0110000, 2→1101101, 3→1111001, 4→0110011, 5→1011011, 6→1011111, 7→1110000, 8→1111111, 9→1111011.",
            listOf(
                "2M: Figure-8 segment diagram (a–g) and common-cathode vs common-anode explanation",
                "3M: Complete accurate truth table for digits 0 through 9"
            )
        ),
        s(
            27, 3, t3, 5,
            "Give an overview of digital and analog integrated circuits (ICs).",
            "IC: complete circuit of transistors, diodes, R and C fabricated on one silicon chip (SSI, MSI, LSI, VLSI). Digital ICs: operate on two logic levels (0/1) — gates (7408, 7432, 7400), flip-flops (7474), counters (7490), decoders (7447), CPUs, memory. Analog ICs: process continuous signals — op-amp 741, timer 555, regulators 7805/7912, audio amp LM386. Advantages: compact, low cost, low power, high reliability.",
            listOf(
                "1M: Define IC & integration scales (SSI–VLSI)",
                "2M: Digital ICs definition & ≥4 numbered examples",
                "2M: Analog ICs definition, ≥3 numbered examples & advantages"
            )
        ),

        // TOPIC 4
        m(28, 4, t4, "IC 7805 provides a fixed output of", listOf("+12 V", "+5 V", "−5 V", "−12 V"), 1),
        m(29, 4, t4, "IC 7912 is a", listOf("Positive 12 V regulator", "Negative 12 V regulator", "Op-amp", "Timer"), 1),
        m(30, 4, t4, "A CRO is used to", listOf("Measure only resistance", "Display and measure waveforms", "Generate power", "Store data"), 1),
        m(31, 4, t4, "In a multimeter, to measure current the meter is connected in", listOf("Parallel", "Series", "Either", "Not connected"), 1),
        m(32, 4, t4, "The electron beam in a CRO is deflected by", listOf("Deflection plates", "Anode", "Filament", "Screen"), 0),
        s(
            33, 4, t4, 5,
            "Explain the function and application of the IC 7805 and IC 7912 voltage regulators.",
            "IC 7805: 3-terminal fixed positive +5 V regulator (input 7–25 V, up to 1 A; pins 1=In, 2=Gnd, 3=Out). IC 7912: 3-terminal fixed negative −12 V regulator (pins 1=Gnd, 2=In, 3=Out). Features: internal reference, error amp, series-pass transistor, thermal shutdown, short-circuit current limiting (0.33 µF input / 0.1 µF output caps for stability; heat sink for high load). Uses: TTL +5 V logic, microcontrollers, op-amp dual ±12 V supply, chargers.",
            listOf(
                "2M: Functions, voltage ratings & pinouts of 7805 (+5 V) and 7912 (−12 V)",
                "1.5M: Built-in protection features & stability capacitors",
                "1.5M: Practical electronics applications"
            )
        ),
        s(
            34, 4, t4, 5,
            "Explain the basic features and applications of a digital multimeter.",
            "Combines voltmeter, ammeter and ohmmeter with LCD display, high input impedance (~10 MΩ), high accuracy, and auto-ranging. Measures: DC/AC voltage (parallel), DC/AC current (series), resistance (circuit powered off), continuity buzzer, diode test, transistor h_FE, capacitance, frequency. Uses: troubleshooting, testing components & supplies. Precaution: select right mode/range; never measure resistance on a live circuit.",
            listOf(
                "2M: Core features & connection rules (V parallel, I series, R power-off)",
                "2M: Measurement capabilities (continuity, diode, h_FE, C, f)",
                "1M: Applications & key safety precaution"
            )
        ),
        s(
            35, 4, t4, 5,
            "Explain the block diagram, working and applications of a Cathode Ray Oscilloscope (CRO).",
            "Parts: (1) CRT (electron gun: heater, cathode, grid, anodes; X & Y deflection plates; fluorescent screen), (2) Vertical amplifier, (3) Horizontal time-base sawtooth generator, (4) Trigger circuit, (5) Power supply. Working: Focused electron beam is deflected vertically by amplified input signal (Y-plates) and swept horizontally at constant speed by sawtooth (X-plates), plotting amplitude-vs-time waveform. Uses: amplitude, frequency/period (f = 1/T), phase difference (Lissajous figures), circuit debugging.",
            listOf(
                "2M: Labelled CRO block diagram (CRT, Y-amp, time-base, trigger, PSU)",
                "2M: Working of X-plate sawtooth sweep + Y-plate signal deflection",
                "1M: Applications (V, f, T, Lissajous phase measurement)"
            )
        ),

        // TOPIC 5
        m(36, 5, t5, "A thermocouple is a", listOf("Temperature sensor", "Pressure sensor", "Light sensor", "Speed sensor"), 0),
        m(37, 5, t5, "LDR resistance ___ with increase in light intensity.", listOf("Increases", "Decreases", "Remains same", "Becomes infinite"), 1),
        m(38, 5, t5, "Power is transmitted at high voltage to", listOf("Increase current", "Reduce I²R loss", "Increase loss", "Reduce frequency"), 1, "Loss = I²R = (P / V cos φ)² R"),
        m(39, 5, t5, "LVDT is used to measure", listOf("Temperature", "Displacement", "Light", "Sound"), 1),
        m(40, 5, t5, "The domestic supply voltage in India is", listOf("110 V", "230 V (single-phase)", "440 V single-phase", "11 kV"), 1),
        s(
            41, 5, t5, 5,
            "What are sensors and transducers? Explain with types and uses.",
            "Sensor detects a physical quantity (temp, light, pressure, position) and produces a measurable signal. Transducer converts energy from one form to another (usually non-electrical to electrical; active/self-generating vs passive). Types: Temperature (thermocouple, RTD Pt100, thermistor); Light (LDR, photodiode); Displacement (LVDT, potentiometer, encoder); Force/Strain (strain gauge, piezoelectric, load cell); Proximity, Hall-effect, ultrasonic. Uses: automation, automotive, medical, smartphones, IoT.",
            listOf(
                "1.5M: Clear distinction between Sensor & Transducer (active vs passive)",
                "2.5M: At least 4 categories with accurate device examples",
                "1M: Real-world applications"
            )
        ),
        s(
            42, 5, t5, 5,
            "Explain the generation, transmission and distribution of electrical power with the help of a block diagram.",
            "Block diagram: Generating station (11–25 kV) → Step-up T/F → HV Transmission (132/220/400 kV) → Step-down grid sub-station → Sub-transmission (33/66 kV) → Primary distribution (11 kV) → Distribution T/F → Consumers (415 V 3-phase / 230 V 1-phase). High transmission voltage lowers current and I²R line losses.",
            listOf(
                "2M: Neat single-line block diagram with standard Indian voltage levels",
                "3M: Clear explanation of Generation, Transmission, Sub-transmission & Distribution stages"
            )
        ),
        s(
            43, 5, t5, 10,
            "Describe the structure of an electrical power system from generating station to consumer. Explain why high voltage is used for transmission, and compare overhead and underground systems.",
            "Structure: Generating station (11–25 kV) → Step-up substation → HV Transmission (132–765 kV) → Receiving substation → Sub-transmission (33–66 kV) → Distribution substation → 11 kV feeders → Distribution transformer → 415/230 V service mains → Consumer. High voltage rationale: For power P = VI cos φ, higher V reduces I ⇒ Loss = I²R = (P / V cos φ)² R drops as 1/V², reducing conductor size/cost & voltage drop. Overhead vs Underground: Overhead is cheaper, easy to repair, suited for long distance, weather-exposed; Underground is safer, weatherproof, costlier, used in dense cities. Distribution topologies: radial, ring-main, interconnected (3-phase 4-wire).",
            listOf(
                "3M: End-to-end power system structure & voltage hierarchy diagram",
                "3M: Mathematical proof Loss = (P / V cos φ)² R ∝ 1/V²",
                "3M: Overhead vs Underground comparison",
                "1M: Distribution system topologies (radial, ring-main, interconnected)"
            ),
            "Loss = I²R = (P / (V cos φ))² · R  ∝  1 / V²"
        ),

        // TOPIC 6
        m(44, 6, t6, "A fuse protects a circuit against", listOf("Over-voltage", "Overcurrent / short circuit", "Under-voltage", "Frequency change"), 1),
        m(45, 6, t6, "MCB stands for", listOf("Main Circuit Board", "Miniature Circuit Breaker", "Moulded Case Breaker", "Magnetic Coil Breaker"), 1),
        m(46, 6, t6, "MCCB is used for", listOf("Low current domestic circuits only", "High current industrial circuits", "Only for lighting", "Only for DC"), 1),
        m(47, 6, t6, "The tripping mechanism of an MCB for overload is", listOf("Magnetic", "Thermal (bimetallic strip)", "Electronic only", "Chemical"), 1),
        m(48, 6, t6, "The fuse is always connected in ___ with the circuit.", listOf("Parallel", "Series", "Either", "None"), 1),
        s(
            49, 6, t6, 5,
            "What are the electrical hazards? Give safe handling practices and measures to prevent them.",
            "Hazards: electric shock (muscle contraction, ventricular fibrillation >100 mA), burns, arc-flash/blast, short-circuit/overload fires, explosions. Preventive practices: Lockout/tagout & verify dead before work; use insulated tools, rubber gloves, mats, safety shoes/goggles; proper earthing + fuse/MCB/RCCB; never touch with wet hands or overload sockets; use CO₂/dry-powder extinguisher (never water) on electrical fires; switch off before touching shock victim, administer CPR.",
            listOf(
                "2M: List key electrical hazards (shock, burns, arc-flash, fire)",
                "3M: ≥5 concrete safe handling & emergency prevention practices"
            )
        ),
        s(
            50, 6, t6, 5,
            "Explain the construction and working of a fuse. Mention its types.",
            "Principle: Heating effect of current (H = I²Rt). A thin low-melting-point wire (tin-lead, copper, silver) connected in series melts and breaks the circuit when current exceeds rating. Types: rewirable (domestic), cartridge (glass/ceramic with sand filler), HRC (High Rupturing Capacity — industrial), drop-out, blade type. Pros: cheap, simple, fast on short-circuit. Cons: one-time use (must replace), inaccurate on slight overloads.",
            listOf(
                "2M: Heating principle H = I²Rt, series placement & materials",
                "2M: Types (rewirable, cartridge, HRC)",
                "1M: Advantages & disadvantages"
            ),
            "H = I² R t"
        ),
        s(
            51, 6, t6, 5,
            "Compare fuse, MCB and MCCB.",
            "Fuse: melts, one-time use, cheap, small loads/electronics. MCB (Miniature Circuit Breaker): thermal (bimetal overload) + magnetic (solenoid short-circuit) trip, resettable manual switch, 0.5–100 A, ~10 kA breaking capacity, domestic/commercial. MCCB (Moulded Case Circuit Breaker): adjustable thermal-magnetic/electronic trip, up to ~1600 A, 50–100 kA breaking capacity, industrial feeders & motors. MCB/MCCB offer faster, safer, resettable protection and switching.",
            listOf(
                "1.5M: Fuse characteristics & applications",
                "2M: MCB tripping mechanism, ratings (0.5–100 A, 10 kA) & uses",
                "1.5M: MCCB adjustable trip, ratings (up to 1600 A, 50–100 kA) & uses"
            )
        ),
        s(
            52, 6, t6, 10,
            "Explain the construction and working of an MCB with a neat diagram. State its advantages over a fuse and the meaning of B, C and D tripping curves.",
            "Construction: moulded insulated case, toggle switch, bimetallic strip (thermal), solenoid/plunger (magnetic) in series, fixed/moving contacts, arc chute splitter plates, terminals. Working: Overload heats & bends bimetallic strip releasing latch (delayed trip); short circuit energizes solenoid plunger for instant trip (ms); arc quenched in arc chute. Advantages over fuse: resettable, zero replacement cost, accurate, visible trip state, multi-pole switching, doubles as switch. Tripping curves: B (3–5 I_n: lighting/resistive), C (5–10 I_n: motors/inductive), D (10–20 I_n: high inrush/transformers). Note: RCCB/ELCB protects against earth-leakage shock.",
            listOf(
                "3M: Neat labelled MCB internal diagram & construction parts",
                "3M: Thermal overload vs magnetic short-circuit working + arc chute",
                "2M: Advantages over a fuse",
                "2M: Accurate B (3–5 I_n), C (5–10 I_n), D (10–20 I_n) tripping curves"
            )
        ),

        // TOPIC 7
        m(53, 7, t7, "The main purpose of earthing is", listOf("To increase voltage", "To protect persons and equipment from electric shock", "To reduce current", "To save energy"), 1),
        m(54, 7, t7, "Which of the following is a method of earthing?", listOf("Plate earthing", "Pipe earthing", "Both (a) and (b)", "None"), 2),
        m(55, 7, t7, "The earth resistance should be", listOf("As high as possible", "As low as possible", "Infinite", "Exactly 100 Ω"), 1),
        m(56, 7, t7, "Salt and charcoal are used around the earth electrode to", listOf("Reduce soil resistance and retain moisture", "Increase resistance", "Prevent corrosion only", "Decorate"), 0),
        m(57, 7, t7, "The earth wire is connected to the ___ of a 3-pin plug.", listOf("Longer, upper pin", "Shorter pin", "Live pin", "Neutral pin"), 0),
        s(
            58, 7, t7, 5,
            "What is earthing? State its purpose and importance.",
            "Earthing: deliberately connecting non-current-carrying metallic bodies/frames of equipment (and supply neutral) to the general mass of earth via a very low-resistance conductor. Purposes: (1) Protect humans from electric shock on insulation failure; (2) Provide low-R fault path so fuse/MCB/RCCB trips promptly; (3) Protect buildings/equipment from lightning/surges keeping body near 0 V; (4) Prevent leakage fires & stabilise system voltage.",
            listOf(
                "1.5M: Clear definition of earthing",
                "3.5M: All 4 purposes & safety importance points"
            )
        ),
        s(
            59, 7, t7, 5,
            "Explain pipe earthing and plate earthing.",
            "Pipe earthing: perforated GI pipe (~40 mm dia, 2.5–3 m long) driven vertically into ground, surrounded by alternate layers of charcoal and salt with a watering funnel at top to maintain low soil resistance; earth wire bolted at top (most common). Plate earthing: copper plate (60×60×0.315 cm) or GI plate (60×60×0.63 cm) buried vertically ~3 m deep surrounded by charcoal & salt with watering pipe and bolted earth lead. Resistance tested periodically by earth tester.",
            listOf(
                "2.5M: Pipe earthing dimensions, charcoal/salt layers & watering funnel",
                "2.5M: Plate earthing dimensions (Cu/GI), depth & earth resistance testing"
            )
        ),
        s(
            60, 7, t7, 10,
            "What is earthing? Why is it necessary in electrical installations? Describe with a neat sketch the pipe earthing method and the factors on which the earth resistance depends. Also explain the colour code and the 3-pin plug connection.",
            "Earthing: low-resistance path from metallic enclosures/neutral to earth. Necessity: on insulation fault, fault current flows safely to earth, tripping fuse/MCB and holding body at ≈0 V instead of shocking user. Pipe earthing: 3 m pit, 38–40 mm dia 2.5 m perforated GI pipe, alternate charcoal & rock-salt layers, bolted GI/Cu earth lead, funnel with wire mesh & cover for watering. Earth resistance factors: soil type/resistivity, moisture, temperature, electrode depth/size, spacing, chemical soil treatment. 3-pin plug & colour code: longer upper pin = Earth (Green, connects first/disconnects last); Live (Red/Brown); Neutral (Black/Blue). Checked via Megger/earth tester.",
            listOf(
                "2M: Definition & necessity of earthing during insulation fault",
                "3M: Neat labelled pipe earthing sketch & installation procedure",
                "2.5M: Factors affecting earth resistance (soil, moisture, depth, size, chemicals)",
                "2.5M: 3-pin plug geometry (longer earth pin) & wire colour code (Green/Red/Black)"
            )
        )
    )
}
