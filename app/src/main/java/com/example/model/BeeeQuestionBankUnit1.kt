package com.example.model

/**
 * BEEE Unit 1.0: Basic Electrical Circuits — Full 50-question bank (Q1–Q50).
 */
internal fun beeeUnit1Questions(): List<TestQuestion> {
    val u = 1
    val uId = "u_100111_1"
    val uTitle = "Unit 1.0: Basic Electrical Circuits"

    fun m(q: Int, t: Int, topic: String, p: String, opts: List<String>, ans: Int, f: String = "", d: String = "NONE", exp: String = "") =
        BeeeQuestionBank.mcq(u, uId, uTitle, q, t, topic, p, opts, ans, f, d, exp)

    fun s(q: Int, t: Int, topic: String, marks: Long, p: String, ans: String, scheme: List<String>, f: String = "", d: String = "NONE") =
        BeeeQuestionBank.subj(u, uId, uTitle, q, t, topic, marks, p, ans, scheme, f, d)

    val t1 = "Electrical Circuit Elements (R, L, C; Active & Passive)"
    val t2 = "Classification of Elements (Linear, Bilateral)"
    val t3 = "Basic Circuit Laws (Ohm's Law, KCL, KVL)"
    val t4 = "Circuit Configurations & Source Transformation"
    val t5 = "Mesh Analysis & Nodal Analysis"
    val t6 = "Divider Rules, Theorems & Star-Delta"

    return listOf(
        // TOPIC 1
        m(1, 1, t1, "The SI unit of inductance is", listOf("Farad", "Henry", "Ohm", "Weber"), 1),
        m(2, 1, t1, "Energy stored in a capacitor of capacitance C charged to voltage V is", listOf("CV", "½CV²", "½C²V", "CV²"), 1, "W = ∫ C v dv = ½CV²"),
        m(3, 1, t1, "Which of the following is an active element?", listOf("Resistor", "Inductor", "Capacitor", "Battery"), 3),
        m(4, 1, t1, "In DC steady state an ideal inductor behaves like a", listOf("Open circuit", "Short circuit", "Capacitor", "Diode"), 1, "v = L di/dt = 0 for steady DC"),
        m(5, 1, t1, "An ideal capacitor in DC steady state behaves like a", listOf("Open circuit", "Short circuit", "Resistor", "Inductor"), 0, "i = C dv/dt = 0 for steady DC"),
        s(
            6, 1, t1, 5,
            "Define resistance, inductance and capacitance. Write the voltage–current relation and the energy expression of each.",
            "Resistance (R): opposition to flow of current; unit ohm (Ω); v = iR; power p = i²R = v²/R (dissipated as heat). Inductance (L): property of a coil to oppose change in current; unit henry (H); v = L di/dt; energy W = ½ L I² (magnetic field). Capacitance (C): ability to store charge per unit voltage (C = q/v); unit farad (F); i = C dv/dt; energy W = ½ C V² (electric field). R dissipates energy while L and C store and return it.",
            listOf(
                "1.5M: Define R with unit (Ω), v = iR, and power p = i²R",
                "1.5M: Define L with unit (H), v = L di/dt, and W = ½ L I²",
                "1.5M: Define C with unit (F), i = C dv/dt, and W = ½ C V²",
                "0.5M: Note dissipation vs energy storage"
            ),
            "v = iR  |  v = L di/dt, W = ½LI²  |  i = C dv/dt, W = ½CV²",
            "RLC_CIRCUIT"
        ),
        s(
            7, 1, t1, 5,
            "Differentiate between active and passive circuit elements with examples.",
            "Active elements deliver/generate energy and can provide power gain (voltage source, current source, battery, generator, transistor, op-amp); require external power source. Passive elements only absorb (R) or store (L, C) energy and cannot supply net energy or power gain (resistor, inductor, capacitor); power is positive when current enters the positive terminal.",
            listOf(
                "2M: Clear definition and energy behavior of Active elements with ≥3 examples",
                "2M: Clear definition and energy behavior of Passive elements with examples (R, L, C)",
                "1M: Power sign convention & gain distinction"
            )
        ),
        s(
            8, 1, t1, 10,
            "Explain the behaviour of resistor, inductor and capacitor in an electric circuit. Derive the expressions for energy stored in L and C and give the equivalent of series and parallel combinations.",
            "1. Resistor: v = iR, p = vi = i²R (irreversible heat). Series: R_eq = R₁ + R₂ + ...; Parallel: 1/R_eq = 1/R₁ + 1/R₂ + .... 2. Inductor: v = L di/dt, p = L i di/dt ⇒ Energy W = ∫₀ᴵ L i di = ½ L I². Opposes sudden current change. Series: L_eq = L₁ + L₂; Parallel: 1/L_eq = 1/L₁ + 1/L₂. 3. Capacitor: i = C dv/dt, p = C v dv/dt ⇒ Energy W = ∫₀^V C v dv = ½ C V². Opposes sudden voltage change. Series: 1/C_eq = 1/C₁ + 1/C₂; Parallel: C_eq = C₁ + C₂. Summary: R is dissipative (Ω); L stores magnetic energy (H, short at DC); C stores electric energy (F, open at DC).",
            listOf(
                "2M: Resistor v-i relation, power, and series/parallel formulas",
                "3M: Inductor v = L di/dt, derivation W = ∫ L i di = ½LI², series/parallel formulas",
                "3M: Capacitor i = C dv/dt, derivation W = ∫ C v dv = ½CV², series/parallel formulas",
                "2M: Comparison table (AC/DC behaviour, units, energy storage vs dissipation)"
            ),
            "W_L = ∫₀ᴵ L i di = ½ L I²   |   W_C = ∫₀^V C v dv = ½ C V²",
            "RLC_CIRCUIT"
        ),

        // TOPIC 2
        m(9, 2, t2, "A bilateral element is one that", listOf("Has the same V–I characteristic for both directions of current", "Allows current in one direction only", "Has non-linear V–I curve", "Is always active"), 0),
        m(10, 2, t2, "Which of the following is a unilateral element?", listOf("Resistor", "Capacitor", "Diode", "Inductor"), 2),
        m(11, 2, t2, "A linear element obeys", listOf("Only Ohm's law", "Superposition and homogeneity", "Only KCL", "None"), 1),
        m(12, 2, t2, "The V–I characteristic of a linear resistor is", listOf("Parabola", "Straight line through origin", "Exponential", "Hyperbola"), 1),
        m(13, 2, t2, "Which is a nonlinear element?", listOf("Ideal resistor", "Air-core inductor", "Thermistor", "Ideal capacitor"), 2),
        s(
            14, 2, t2, 5,
            "Distinguish between linear and nonlinear elements with examples.",
            "Linear: V–I relation is a straight line through the origin; satisfies superposition (additivity) and homogeneity (scaling); Ohm's law applies; examples: ideal R, L, C; analysed easily with network theorems. Nonlinear: V–I relation is a curve; superposition fails; examples: diode, transistor, thermistor, filament lamp, iron-core inductor (saturation); requires graphical/iterative analysis.",
            listOf(
                "2M: Linear definition (straight line through origin, superposition + homogeneity) + examples",
                "2M: Nonlinear definition (curved V-I, superposition fails) + examples",
                "1M: Circuit analysis implication / V-I sketch"
            )
        ),
        s(
            15, 2, t2, 5,
            "Distinguish between unilateral and bilateral elements with examples.",
            "Bilateral: electrical properties remain identical regardless of current direction; V–I curve is symmetric about the origin; obeys reciprocity; examples: R, L, C, transmission line. Unilateral: property changes with current direction and conducts effectively in one direction; examples: diode, rectifier, transistor, vacuum tube.",
            listOf(
                "2M: Bilateral definition, symmetry about origin, reciprocity + examples",
                "2M: Unilateral definition, directional conduction + examples",
                "1M: Comparative summary / V-I symmetry note"
            )
        ),
        s(
            16, 2, t2, 10,
            "Classify circuit elements into linear/nonlinear, unilateral/bilateral, active/passive and lumped/distributed. Explain each with suitable V–I characteristics and examples.",
            "1. Active/Passive: sources deliver energy; R, L, C absorb/store. 2. Linear/Nonlinear: V ∝ I (straight line, superposition holds) vs curve (diode I = I_s(e^(V/ηV_T) − 1)). 3. Unilateral/Bilateral: equal conduction both ways (symmetric V–I) vs one-way conduction (diode). 4. Lumped/Distributed: concentrated at a point (bench resistor) vs spread along length (transmission line R, L, C per km). 5. Examples table: Resistor (passive, linear, bilateral, lumped); Diode (passive, nonlinear, unilateral); Battery (active, ideal linear, lumped); Transmission line (passive, linear, bilateral, distributed). V–I sketches: straight line through origin for R; exponential rise after ≈0.7 V for Si diode.",
            listOf(
                "2M: Active/Passive and Linear/Nonlinear definitions + diode equation",
                "2M: Unilateral/Bilateral and Lumped/Distributed definitions",
                "3M: Classification table of examples (Resistor, Diode, Battery, Transmission line)",
                "3M: Neat V–I characteristic sketches for linear resistor and Si diode"
            ),
            "I = I_s (e^(V / ηV_T) − 1)"
        ),

        // TOPIC 3
        m(17, 3, t3, "Ohm's law is valid only when", listOf("Temperature is constant", "Voltage is AC", "Current is zero", "Frequency is high"), 0, "V = I·R"),
        m(18, 3, t3, "KCL is based on the law of conservation of", listOf("Energy", "Charge", "Momentum", "Mass"), 1, "Σ I_in = Σ I_out"),
        m(19, 3, t3, "KVL is based on the law of conservation of", listOf("Charge", "Energy", "Mass", "Power"), 1, "Σ V = 0"),
        m(20, 3, t3, "At a node, currents entering = 4 A and 3 A; one current leaves = 2 A. The other leaving current is", listOf("9 A", "5 A", "1 A", "2 A"), 1, "4 + 3 = 2 + I ⇒ I = 5 A"),
        m(21, 3, t3, "Algebraic sum of voltages around any closed loop is", listOf("Infinity", "Equal to source", "Zero", "Unity"), 2, "Σ V = 0 (KVL)"),
        s(
            22, 3, t3, 5,
            "State and explain Kirchhoff's Current Law and Kirchhoff's Voltage Law with a suitable example.",
            "KCL: Algebraic sum of currents meeting at a node is zero (Σ I_in = Σ I_out), based on conservation of charge. Example: I₁ + I₂ = I₃ + I₄ at a junction. KVL: Algebraic sum of all voltages (rises and drops) around any closed loop is zero (Σ V = 0), based on conservation of energy. Example: battery E with series R₁, R₂ gives E − IR₁ − IR₂ = 0 ⇒ I = E/(R₁ + R₂).",
            listOf(
                "2.5M: KCL statement, charge conservation basis, and node equation example",
                "2.5M: KVL statement, energy conservation basis, and closed-loop equation example"
            ),
            "KCL: Σ I_in = Σ I_out   |   KVL: Σ V = 0"
        ),
        s(
            23, 3, t3, 5,
            "State Ohm's law. What are its limitations?",
            "Statement: At constant temperature, current through a conductor is directly proportional to the potential difference across it (V ∝ I ⇒ V = IR). Limitations: (1) Not applicable to nonlinear elements (diode, transistor, thermistor); (2) Invalid when temperature changes (R varies); (3) Not applicable to electrolytes, arc lamps, vacuum tubes and semiconductors; (4) Not applicable to unilateral networks.",
            listOf(
                "2M: Exact statement of Ohm's law with constant temperature condition and V = IR",
                "3M: At least 4 valid limitations with concrete examples"
            ),
            "V = I · R  (at constant temperature)"
        ),
        s(
            24, 3, t3, 10,
            "State Ohm's law and Kirchhoff's laws. A 24 V battery is connected to a 4 Ω resistor in series with a parallel combination of 6 Ω and 12 Ω. Find the total current, voltage across the parallel branch and the current in each branch.",
            "Laws: Ohm (V = IR), KCL (Σ I = 0 at node), KVL (Σ V = 0 in loop). Step 1: R_p = (6 × 12)/(6 + 12) = 72/18 = 4 Ω. Step 2: R_T = 4 + 4 = 8 Ω. Step 3: Total current I = 24/8 = 3 A. Step 4: Voltage across parallel part V_p = 3 × 4 = 12 V (KVL check: 24 − 12 − 12 = 0). Step 5: Branch currents I_6Ω = 12/6 = 2 A; I_12Ω = 12/12 = 1 A (KCL check: 2 + 1 = 3 A; Power check: 24×3 = 72 W).",
            listOf(
                "2M: State Ohm's law, KCL, and KVL clearly",
                "2M: Compute R_p = 4 Ω and total resistance R_T = 8 Ω",
                "2M: Compute total current I = 3 A",
                "2M: Compute parallel voltage V_p = 12 V",
                "2M: Compute branch currents I_6Ω = 2 A, I_12Ω = 1 A with KCL/KVL check"
            ),
            "R_p = (6×12)/(6+12) = 4 Ω ⇒ R_T = 8 Ω ⇒ I = 3 A, V_p = 12 V",
            "RLC_CIRCUIT"
        ),

        // TOPIC 4
        m(25, 4, t4, "Three resistors of 6 Ω each are connected in parallel. Equivalent resistance is", listOf("18 Ω", "6 Ω", "2 Ω", "3 Ω"), 2, "R_eq = 6 / 3 = 2 Ω"),
        m(26, 4, t4, "Internal resistance of an ideal voltage source is", listOf("Infinity", "Zero", "1 Ω", "Equal to load"), 1),
        m(27, 4, t4, "Internal resistance of an ideal current source is", listOf("Zero", "Infinity", "Unity", "Variable"), 1),
        m(28, 4, t4, "A 10 V source in series with 5 Ω is equivalent to a current source of ___ in parallel with 5 Ω", listOf("50 A", "2 A", "0.5 A", "15 A"), 1, "I_s = V_s / R_s = 10 / 5 = 2 A"),
        m(29, 4, t4, "In a series circuit, ___ remains the same through all elements.", listOf("Voltage", "Power", "Current", "Resistance"), 2),
        s(
            30, 4, t4, 5,
            "What is source transformation? Explain with an example.",
            "Definition: A practical voltage source (V_s in series with R_s) can be replaced by an equivalent practical current source (I_s in parallel with the same R_s), and vice versa, without changing the voltage/current at the external load. Relations: I_s = V_s / R_s and V_s = I_s R_s. Example: 10 V in series with 5 Ω ⇔ 2 A current source in parallel with 5 Ω. Arrow of current source points toward + terminal. Not valid for ideal sources (R_s = 0 or ∞).",
            listOf(
                "2M: Definition of source transformation and equivalence formulas I_s = V_s/R_s, V_s = I_s R_s",
                "2M: Worked numerical example with polarity/arrow direction",
                "1M: Limitations (invalid for ideal sources; internal power differs)"
            ),
            "I_s = V_s / R_s   ⇔   V_s = I_s · R_s"
        ),
        s(
            31, 4, t4, 5,
            "Compare series and parallel connection of resistors.",
            "Series: same current through all; V = V₁ + V₂ + ...; R_eq = R₁ + R₂ + ... (> largest R); if one opens, entire circuit breaks (old fairy lights). Parallel: same voltage across all; I = I₁ + I₂ + ...; 1/R_eq = Σ(1/R) (< smallest R); other branches keep working if one opens (home wiring); for two resistors R_eq = R₁R₂/(R₁ + R₂).",
            listOf(
                "2M: Series characteristics (current, voltage, R_eq formula, open-fault effect)",
                "2M: Parallel characteristics (voltage, current, 1/R_eq formula, R₁R₂/(R₁+R₂))",
                "1M: Practical applications (home wiring vs series strings)"
            )
        ),
        s(
            32, 4, t4, 10,
            "Explain ideal and practical voltage and current sources with V–I characteristics. Explain source transformation and use it to find the current through a 4 Ω load connected to a 12 V source with 2 Ω internal resistance.",
            "Ideal voltage source: constant V regardless of I (R_s = 0). Practical: V_t = V_s − I R_s (drooping line). Ideal current source: constant I regardless of V (R_s = ∞). Practical: I_L = I_s − V_t/R_s. Transformation: I_s = V_s/R_s with same R_s in parallel. Direct numerical: I = 12/(2 + 4) = 2 A, V_L = 8 V. Using transformation: I_s = 12/2 = 6 A in parallel with 2 Ω and 4 Ω load; by current divider I_L = 6 × 2/(2 + 4) = 2 A (matches).",
            listOf(
                "3M: Ideal vs practical voltage & current sources with V_t, I_L equations and V–I characteristics",
                "2M: Source transformation principle and circuit diagrams",
                "2M: Direct calculation I = 2 A, V_L = 8 V",
                "3M: Source-transformed calculation I_s = 6 A and current divider I_L = 2 A"
            ),
            "V_t = V_s − I R_s  |  I_s = 12/2 = 6 A ⇒ I_L = 6 × 2/(2+4) = 2 A"
        ),

        // TOPIC 5
        m(33, 5, t5, "Mesh analysis is based on", listOf("KCL", "KVL", "Ohm's law only", "Thevenin's theorem"), 1),
        m(34, 5, t5, "Nodal analysis is based on", listOf("KCL", "KVL", "Superposition", "Norton"), 0),
        m(35, 5, t5, "A circuit has 5 nodes. Number of independent nodal equations is", listOf("5", "4", "6", "3"), 1, "n − 1 = 5 − 1 = 4"),
        m(36, 5, t5, "Mesh analysis is applicable only to", listOf("Planar circuits", "Non-planar circuits", "DC only", "Nonlinear circuits"), 0),
        m(37, 5, t5, "A mesh is a loop which", listOf("Contains other loops", "Contains no other loop inside it", "Has only sources", "Has no resistor"), 1),
        s(
            38, 5, t5, 5,
            "Write the steps of mesh analysis.",
            "1. Identify planar circuit and meshes; assign clockwise mesh currents I₁, I₂, ... 2. Apply KVL to each mesh (shared resistor carries difference of adjacent mesh currents). 3. Boundary current source fixes that mesh current; shared current source forms a supermesh. 4. Solve simultaneous equations (Cramer's rule / substitution). 5. Compute branch currents from mesh currents (or their difference for shared branches).",
            listOf(
                "1.5M: Identify planar meshes and assign clockwise mesh currents",
                "1.5M: Apply KVL per mesh including shared-branch difference term",
                "1M: Mention boundary current source / supermesh handling",
                "1M: Solve simultaneous equations and deduce branch currents"
            )
        ),
        s(
            39, 5, t5, 5,
            "Write the steps of nodal analysis.",
            "1. Mark all nodes and pick a reference node (ground, 0 V — typically node with most branches). 2. Assign unknown node voltages V₁, V₂, ... to remaining (n − 1) nodes. 3. Apply KCL at each non-reference node with branch currents as (V_node − V_adj)/R. 4. Voltage source between two non-reference nodes forms a supernode. 5. Solve for node voltages and compute branch currents via Ohm's law.",
            listOf(
                "1.5M: Reference node selection and (n − 1) node voltage assignments",
                "1.5M: KCL formulation using (V_node − V_adjacent)/R",
                "1M: Mention supernode case",
                "1M: Solve for voltages and find branch currents"
            )
        ),
        s(
            40, 5, t5, 10,
            "Using mesh analysis find the current in each branch: Mesh 1 contains a 12 V source and a 2 Ω resistor, Mesh 2 contains a 2 Ω resistor and a 6 V source opposing the mesh current; the two meshes share a 4 Ω resistor.",
            "Assign clockwise mesh currents I₁, I₂. Mesh 1 (KVL): 12 = 2I₁ + 4(I₁ − I₂) ⇒ 6I₁ − 4I₂ = 12 ...(i). Mesh 2 (KVL): −6 = 2I₂ + 4(I₂ − I₁) ⇒ −4I₁ + 6I₂ = −6 ...(ii). Determinant Δ = 36 − 16 = 20. I₁ = (72 − 24)/20 = 2.4 A; I₂ = (−36 + 48)/20 = 0.6 A. Branch currents: 12 V branch = 2.4 A; 6 V branch = 0.6 A; shared 4 Ω resistor = I₁ − I₂ = 1.8 A.",
            listOf(
                "2M: Circuit diagram and clockwise mesh current assignment",
                "3M: Correct KVL equations: 6I₁ − 4I₂ = 12 and −4I₁ + 6I₂ = −6",
                "3M: Solve accurately for I₁ = 2.4 A and I₂ = 0.6 A",
                "2M: State all branch currents including shared 4 Ω = 1.8 A and verify"
            ),
            "6I₁ − 4I₂ = 12  |  −4I₁ + 6I₂ = −6  ⇒  I₁ = 2.4 A, I₂ = 0.6 A"
        ),
        s(
            41, 5, t5, 10,
            "Using nodal analysis, find the voltage at the common node: branch A has 10 V source in series with 2 Ω, branch B has 5 V source in series with 4 Ω, branch C is a 4 Ω load to ground. Find the current in each branch.",
            "Let V be the common node voltage (reference = ground). KCL at node: (10 − V)/2 + (5 − V)/4 = V/4. Multiply by 4: 2(10 − V) + (5 − V) = V ⇒ 25 − 3V = V ⇒ 4V = 25 ⇒ V = 6.25 V. Branch currents: I_A = (10 − 6.25)/2 = 1.875 A; I_B = (5 − 6.25)/4 = −0.3125 A (5 V source absorbs/charges); I_C = 6.25/4 = 1.5625 A. KCL check: 1.875 − 0.3125 = 1.5625 A.",
            listOf(
                "2M: Circuit diagram with common node V and ground reference",
                "3M: KCL equation (10 − V)/2 + (5 − V)/4 = V/4",
                "2M: Solve accurately for V = 6.25 V",
                "3M: Branch currents I_A = 1.875 A, I_B = −0.3125 A, I_C = 1.5625 A with KCL check"
            ),
            "(10 − V)/2 + (5 − V)/4 = V/4  ⇒  V = 6.25 V"
        ),

        // TOPIC 6
        m(42, 6, t6, "In a voltage divider with R₁ and R₂ in series, voltage across R₂ is", listOf("V R₁/(R₁+R₂)", "V R₂/(R₁+R₂)", "V/(R₁R₂)", "V(R₁+R₂)"), 1),
        m(43, 6, t6, "In a current divider with R₁ and R₂ in parallel, current through R₁ is", listOf("I R₁/(R₁+R₂)", "I R₂/(R₁+R₂)", "I(R₁+R₂)", "I/R₁"), 1),
        m(44, 6, t6, "Three equal resistors of 30 Ω in delta are converted to star. Each star resistor is", listOf("90 Ω", "30 Ω", "10 Ω", "15 Ω"), 2, "R_Y = R_Δ / 3 = 30 / 3 = 10 Ω"),
        m(45, 6, t6, "Three equal 5 Ω resistors in star are converted to delta. Each delta resistor is", listOf("5/3 Ω", "15 Ω", "10 Ω", "25 Ω"), 1, "R_Δ = 3 R_Y = 15 Ω"),
        m(46, 6, t6, "Superposition theorem is applicable to", listOf("Linear bilateral networks", "Nonlinear networks", "Unilateral networks", "Power calculations"), 0),
        s(
            47, 6, t6, 5,
            "Derive the voltage divider and current divider rules.",
            "Voltage divider (series R₁, R₂ across V): I = V/(R₁ + R₂) ⇒ V₁ = V·R₁/(R₁ + R₂), V₂ = V·R₂/(R₁ + R₂). Current divider (parallel R₁, R₂ carrying I): V = I·R₁R₂/(R₁ + R₂) ⇒ I₁ = V/R₁ = I·R₂/(R₁ + R₂), I₂ = I·R₁/(R₁ + R₂). Example: 9 A into 6 Ω ∥ 12 Ω gives I₆ = 6 A, I₁₂ = 3 A.",
            listOf(
                "2M: Voltage divider derivation V₁ = V R₁/(R₁+R₂), V₂ = V R₂/(R₁+R₂)",
                "2M: Current divider derivation I₁ = I R₂/(R₁+R₂), I₂ = I R₁/(R₁+R₂)",
                "1M: Short worked numerical example"
            ),
            "V₂ = V·R₂/(R₁+R₂)   |   I₁ = I·R₂/(R₁+R₂)"
        ),
        s(
            48, 6, t6, 5,
            "State superposition theorem and Thevenin's theorem. (Frequently asked – bonus)",
            "Superposition: In a linear bilateral network with multiple independent sources, the response (I or V) in any branch equals the algebraic sum of responses from each source acting alone (other voltage sources shorted, current sources opened). Thevenin: Any linear two-terminal network can be replaced by a single voltage source V_th (open-circuit terminal voltage) in series with R_th (resistance seen from terminals with independent sources deactivated); I_L = V_th/(R_th + R_L). Max power transfer occurs when R_L = R_th, P_max = V_th²/(4R_th).",
            listOf(
                "2M: Superposition theorem statement + source deactivation rules",
                "2M: Thevenin's theorem statement (V_th, R_th, I_L = V_th/(R_th + R_L))",
                "1M: Maximum power transfer condition R_L = R_th"
            ),
            "I_L = V_th / (R_th + R_L)   |   P_max = V_th² / (4 R_th)"
        ),
        s(
            49, 6, t6, 10,
            "Derive the formulae for delta-to-star transformation. Convert a delta with R_ab = 30 Ω, R_bc = 20 Ω, R_ca = 10 Ω into its equivalent star.",
            "Equate terminal-pair resistances: R_a + R_b = R_ab(R_bc + R_ca)/Σ ...(1), R_b + R_c = R_bc(R_ab + R_ca)/Σ ...(2), R_c + R_a = R_ca(R_ab + R_bc)/Σ ...(3), where Σ = R_ab + R_bc + R_ca. Taking (1)+(3)−(2): 2R_a = 2R_ab R_ca/Σ ⇒ R_a = R_ab R_ca/Σ; similarly R_b = R_ab R_bc/Σ, R_c = R_bc R_ca/Σ. Numerical: Σ = 30+20+10 = 60 Ω; R_a = 30×10/60 = 5 Ω; R_b = 30×20/60 = 10 Ω; R_c = 20×10/60 = 3.33 Ω. Check a–b: star = 5+10 = 15 Ω; delta = 30 ∥ 30 = 15 Ω.",
            listOf(
                "2M: Draw labelled Delta and Star diagrams & terminal equivalence principle",
                "4M: Formulate 3 terminal equations and derive R_a, R_b, R_c formulas",
                "3M: Numerical calculation Σ = 60 Ω, R_a = 5 Ω, R_b = 10 Ω, R_c = 3.33 Ω",
                "1M: Verification between terminals a–b (15 Ω)"
            ),
            "R_a = R_ab·R_ca / Σ,  R_b = R_ab·R_bc / Σ,  R_c = R_bc·R_ca / Σ"
        ),
        s(
            50, 6, t6, 10,
            "State the conditions for balanced star–delta conversion. Three 30 Ω resistors form a delta; find the equivalent star and verify the resistance between any two terminals. If 20 V is applied across two terminals, find the current drawn.",
            "Balanced network condition: R_ab = R_bc = R_ca = R_Δ ⇒ R_Y = R_Δ/3 and R_Δ = 3R_Y. For 30 Ω delta: R_Y = 30/3 = 10 Ω per star branch. Terminal resistance verification: Delta = 30 ∥ (30 + 30) = 30 ∥ 60 = 20 Ω; Star = 10 + 10 = 20 Ω (equal). Current with 20 V across two terminals: I = 20 / 20 = 1 A. Use: converts bridge networks to series-parallel and simplifies 3-phase loads.",
            listOf(
                "2M: State balanced conversion conditions R_Y = R_Δ/3 and R_Δ = 3R_Y",
                "2M: Find equivalent star resistance R_Y = 10 Ω",
                "3M: Verify terminal-to-terminal resistance = 20 Ω for both delta and star",
                "2M: Calculate current I = 20/20 = 1 A",
                "1M: State practical applications (bridges & 3-phase loads)"
            ),
            "R_Y = R_Δ / 3 = 10 Ω  ⇒  R_ab = 20 Ω  ⇒  I = 20/20 = 1 A"
        )
    )
}
