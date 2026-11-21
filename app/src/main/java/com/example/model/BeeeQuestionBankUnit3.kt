package com.example.model

/**
 * BEEE Unit 3.0: Basic Electrical Machines — Full 47-question bank (Q1–Q47).
 */
internal fun beeeUnit3Questions(): List<TestQuestion> {
    val u = 3
    val uId = "u_100111_3"
    val uTitle = "Unit 3.0: Basic Electrical Machines"

    fun m(q: Int, t: Int, topic: String, p: String, opts: List<String>, ans: Int, f: String = "", d: String = "NONE", exp: String = "") =
        BeeeQuestionBank.mcq(u, uId, uTitle, q, t, topic, p, opts, ans, f, d, exp)

    fun s(q: Int, t: Int, topic: String, marks: Long, p: String, ans: String, scheme: List<String>, f: String = "", d: String = "NONE") =
        BeeeQuestionBank.subj(u, uId, uTitle, q, t, topic, marks, p, ans, scheme, f, d)

    val t1 = "Electromagnetic Principles (Self & Mutual Inductance)"
    val t2 = "DC Machines (Principle, Construction, EMF)"
    val t3 = "Single-Phase Transformer (Working & EMF)"
    val t4 = "Transformer Performance & Regulation"
    val t5 = "Induction Motors (1-Phase & 3-Phase)"
    val t6 = "Motor Applications & FHP Motors"

    return listOf(
        // TOPIC 1
        m(1, 1, t1, "Faraday's law of electromagnetic induction states that the induced emf is proportional to", listOf("Flux", "Rate of change of flux linkage", "Resistance", "Current"), 1, "e = −N dφ/dt"),
        m(2, 1, t1, "The negative sign in e = −N dφ/dt is explained by", listOf("Ohm's law", "Lenz's law", "Ampere's law", "Coulomb's law"), 1),
        m(3, 1, t1, "Fleming's left-hand rule is used to find the direction of", listOf("Induced emf", "Force on a current-carrying conductor", "Flux", "Voltage"), 1),
        m(4, 1, t1, "Coefficient of coupling k of two coils lies between", listOf("−1 and 1", "0 and 1", "1 and 2", "0 and ∞"), 1, "M = k√(L₁L₂), 0 ≤ k ≤ 1"),
        m(5, 1, t1, "Unit of mutual inductance is", listOf("Farad", "Henry", "Weber", "Tesla"), 1),
        s(
            6, 1, t1, 5,
            "State Faraday's laws of electromagnetic induction and Lenz's law.",
            "First law: Whenever magnetic flux linked with a coil changes, an emf is induced in it. Second law: Magnitude of induced emf equals rate of change of flux linkage, e = −N dφ/dt. Lenz's law: Direction of induced emf/current opposes the cause producing it (gives the minus sign; follows conservation of energy). Types: statically induced (transformer, self/mutual) and dynamically induced (e = Blv, generator).",
            listOf(
                "2M: State Faraday's 1st and 2nd laws with e = −N dφ/dt",
                "2M: State Lenz's law and explain conservation of energy",
                "1M: Distinguish statically vs dynamically induced emf"
            ),
            "e = −N dφ/dt   |   e = B l v sin θ"
        ),
        s(
            7, 1, t1, 5,
            "Define self inductance and mutual inductance. Write the expression for the equivalent inductance of two coupled coils in series.",
            "Self inductance (L): property of a coil opposing current change in itself; L = Nφ/I = −e/(di/dt) = μN²A/l. Mutual inductance (M): emf induced in one coil due to changing current in a neighbouring coil; M = N₂φ₁₂/I₁ = k√(L₁L₂). Series connection: series aiding L_eq = L₁ + L₂ + 2M; series opposing L_eq = L₁ + L₂ − 2M.",
            listOf(
                "2M: Define self inductance L with formula L = Nφ/I = μN²A/l",
                "2M: Define mutual inductance M with M = k√(L₁L₂)",
                "1M: Series aiding (L₁ + L₂ + 2M) and opposing (L₁ + L₂ − 2M)"
            ),
            "M = k√(L₁L₂)   |   L_eq = L₁ + L₂ ± 2M"
        ),

        // TOPIC 2
        m(8, 2, t2, "The function of the commutator in a DC generator is to", listOf("Convert AC to DC", "Increase emf", "Reduce losses", "Provide cooling"), 0),
        m(9, 2, t2, "In a lap winding the number of parallel paths A equals", listOf("2", "P (number of poles)", "P/2", "1"), 1),
        m(10, 2, t2, "Back emf in a DC motor is", listOf("V + I_a R_a", "V − I_a R_a", "V", "I_a R_a"), 1, "E_b = V − I_a R_a"),
        m(11, 2, t2, "A DC series motor is preferred for", listOf("Fans", "Traction and cranes (high starting torque)", "Lathes", "Constant speed drives"), 1),
        m(12, 2, t2, "The armature core of a DC machine is laminated to reduce", listOf("Hysteresis loss", "Eddy current loss", "Copper loss", "Friction loss"), 1),
        s(
            13, 2, t2, 5,
            "Explain the working principle and construction of a DC machine.",
            "Principle: Generator — conductor cutting flux induces e = Blv (Faraday, Fleming's right-hand rule). Motor — current-carrying conductor in magnetic field experiences force F = BIl (Fleming's left-hand rule). Construction: Yoke (outer frame carrying flux), Poles & field winding (main flux), Armature (laminated slotted iron core + winding), Commutator (mica-insulated copper segments converting AC to DC), Carbon brushes (collect current).",
            listOf(
                "2M: Working principle for generator (e = Blv) and motor (F = BIl)",
                "3M: Labelled roles of Yoke, Poles/Field, Armature, Commutator, and Brushes"
            ),
            "Generator: e = B l v   |   Motor: F = B I l"
        ),
        s(
            14, 2, t2, 5,
            "Classify DC machines with neat connection diagrams and mention one application of each.",
            "1. Separately excited: independent field supply (testing, Ward-Leonard speed control). 2. Shunt: field in parallel with armature, nearly constant speed (lathes, fans, pumps). 3. Series: field in series with armature, very high starting torque, never start on no-load (traction, cranes, hoists). 4. Compound (cumulative/differential; short/long shunt): combines both (elevators, rolling mills, presses).",
            listOf(
                "3M: Classify all 4 types with neat field-armature connection diagrams",
                "2M: Mention characteristic & one real application for each type"
            )
        ),
        s(
            15, 2, t2, 10,
            "Derive the EMF equation of a DC generator. A 4-pole, lap-wound generator has 500 armature conductors, flux per pole 0.02 Wb and runs at 1000 rpm. Calculate the generated emf.",
            "Let φ = flux/pole (Wb), P = poles, Z = armature conductors, A = parallel paths, N = speed (rpm). Flux cut by 1 conductor in 1 revolution = Pφ; time for 1 rev = 60/N s; avg emf/conductor = PφN/60 V. Conductors in series per path = Z/A ⇒ E_g = PφZN/(60A) V. Lap: A = P ⇒ E = φZN/60. Wave: A = 2 ⇒ E = PφZN/120. Numerical: lap A = P = 4 ⇒ E = (4 × 0.02 × 500 × 1000)/(60 × 4) = 40,000/240 = 166.7 V.",
            listOf(
                "5M: Step-by-step derivation of E_g = PφZN/(60A) V",
                "2M: State Lap (A = P) and Wave (A = 2) special cases",
                "3M: Substitute given values accurately to get E_g = 166.7 V"
            ),
            "E_g = P φ Z N / (60 A)   ⇒   E_g = 166.7 V"
        ),
        s(
            16, 2, t2, 10,
            "Explain the working of a DC motor. Derive the back emf and torque equations. A 220 V shunt motor has R_a = 0.5 Ω and armature current 20 A; find back emf and power developed.",
            "Working: Armature conductors in pole flux experience F = BIl creating torque. Rotation induces opposing back emf E_b (Lenz's law). Voltage eq: V = E_b + I_a R_a ⇒ I_a = (V − E_b)/R_a (starter needed at start when E_b = 0). Torque: E_b I_a = Tω = T(2πN/60) with E_b = PφZN/(60A) ⇒ T_a = (PZ/2πA)φI_a = 0.159(PZ/A)φI_a N·m (shunt T ∝ I_a; series T ∝ I_a²). Numerical: E_b = 220 − 20×0.5 = 210 V; mechanical power developed = E_b I_a = 210 × 20 = 4200 W.",
            listOf(
                "2M: Working principle & significance of back emf E_b",
                "4M: Derive V = E_b + I_a R_a and torque equation T_a = 0.159 (PZ/A) φ I_a N·m",
                "4M: Numerical E_b = 210 V and Power developed = 4200 W"
            ),
            "E_b = V − I_a R_a = 210 V   |   T_a = 0.159 (PZ/A) φ I_a N·m"
        ),

        // TOPIC 3
        m(17, 3, t3, "A transformer works on the principle of", listOf("Self induction", "Mutual induction", "Fleming's rule", "Ampere's law"), 1),
        m(18, 3, t3, "A transformer cannot work on", listOf("AC", "DC", "Low frequency", "High voltage"), 1),
        m(19, 3, t3, "The core of a transformer is laminated to reduce", listOf("Copper loss", "Eddy current loss", "Hysteresis loss", "Leakage flux"), 1),
        m(20, 3, t3, "EMF equation of a transformer is", listOf("E = 2.22 f N φ_m", "E = 4.44 f N φ_m", "E = 1.11 f N φ_m", "E = 4 f N φ_m"), 1),
        m(21, 3, t3, "A step-up transformer has", listOf("N₂ < N₁", "N₂ = N₁", "N₂ > N₁", "No windings"), 2),
        s(
            22, 3, t3, 5,
            "Explain the necessity and working principle of a single-phase transformer.",
            "Necessity: Power is generated at ~11 kV, stepped up to 132–400 kV for low I²R transmission loss, and stepped down to 415/230 V for consumers efficiently (≈98%). Principle: Mutual induction — AC voltage V₁ on primary (N₁ turns) sets up alternating flux φ in the laminated core, linking secondary (N₂ turns) and inducing E₂ without electrical connection or frequency change. Ratio: V₂/V₁ = N₂/N₁ = I₁/I₂ = K.",
            listOf(
                "2M: Necessity in generation, HV transmission, and distribution",
                "2M: Working principle via mutual induction & alternating core flux",
                "1M: Turns/voltage/current ratio V₂/V₁ = N₂/N₁ = I₁/I₂ = K"
            ),
            "V₂ / V₁ = N₂ / N₁ = I₁ / I₂ = K"
        ),
        s(
            23, 3, t3, 5,
            "Describe the construction of core-type and shell-type transformers.",
            "Core: thin 0.35 mm silicon steel laminations (~4% Si) insulated by varnish to cut eddy current & hysteresis losses. Core-type: windings surround two limbs of rectangular core, single magnetic path, preferred for HV, easier to repair. Shell-type: three-limb core surrounds windings on central limb, two parallel flux paths, lower leakage, used for LV/high-current. Copper HV & LV concentric windings (LV near core) + tank, conservator, breather, Buchholz relay & oil.",
            listOf(
                "1.5M: Laminated silicon steel core material & purpose",
                "2.5M: Core-type vs Shell-type structural differences & diagrams",
                "1M: Concentric windings & power transformer accessories"
            )
        ),
        s(
            24, 3, t3, 10,
            "Derive the EMF equation of a transformer. A 1-phase, 50 Hz transformer has 1100 primary turns and is supplied at 2200 V. Find the maximum flux in the core and the number of secondary turns for 220 V secondary.",
            "Let φ = φ_m sin ωt. Induced emf e = −N dφ/dt = −Nωφ_m cos ωt ⇒ peak E_m = 2πf N φ_m. RMS value E = E_m/√2 = (2π/√2) f N φ_m = 4.44 f N φ_m. Hence E₁ = 4.44 f N₁ φ_m, E₂ = 4.44 f N₂ φ_m, E₂/E₁ = N₂/N₁ = K. Numerical: φ_m = 2200 / (4.44 × 50 × 1100) = 9.0 × 10⁻³ Wb = 9 mWb. N₂ = 1100 × (220/2200) = 110 turns.",
            listOf(
                "5M: Complete calculus derivation from φ = φ_m sin ωt to E = 4.44 f N φ_m",
                "3M: Calculate maximum flux φ_m = 9.0 × 10⁻³ Wb (9 mWb)",
                "2M: Calculate secondary turns N₂ = 110 turns"
            ),
            "e = −N dφ/dt  ⇒  E = 4.44 f N φ_m   |   φ_m = 9 mWb, N₂ = 110"
        ),

        // TOPIC 4
        m(25, 4, t4, "The turns ratio of a transformer is 1:5; it is a", listOf("Step-down", "Step-up", "Isolation", "Auto"), 1),
        m(26, 4, t4, "If primary current is 5 A and K = 0.1 (N₂/N₁), secondary current is", listOf("0.5 A", "50 A", "5 A", "0.05 A"), 1, "I₂ = I₁ / K = 5 / 0.1 = 50 A"),
        m(27, 4, t4, "Voltage regulation of a good transformer should be", listOf("High", "Low", "Zero always", "Negative always"), 1),
        m(28, 4, t4, "Iron losses of a transformer depend on", listOf("Load current", "Supply voltage and frequency", "Power factor", "Winding resistance"), 1),
        m(29, 4, t4, "Transformer efficiency is maximum when", listOf("Cu loss = Iron loss", "Cu loss = 0", "Load is zero", "Iron loss = 0"), 0),
        s(
            30, 4, t4, 5,
            "Define voltage regulation. Why is it significant?",
            "Voltage regulation is the change in secondary terminal voltage from no-load to full-load at constant primary voltage and given pf, expressed as a percentage of no-load voltage: % VR = [(E₂ − V₂)/E₂] × 100. Significance: low VR (2–5%) keeps appliance voltage steady; high VR causes dim lights and motor overheating. Depends on load current and pf (lagging ⇒ positive VR; leading ⇒ can be zero or negative). Example: E₂ = 200 V, V₂ = 192 V ⇒ VR = 4%.",
            listOf(
                "2M: Exact definition and formula % VR = (E₂ − V₂)/E₂ × 100",
                "2M: Practical significance and effect of load pf",
                "1M: Numerical example"
            ),
            "% VR = [(E₂ − V₂) / E₂] × 100"
        ),
        s(
            31, 4, t4, 5,
            "Write the losses in a transformer and calculate the full-load efficiency of a 5 kVA unity-pf transformer with iron loss 50 W and full-load copper loss 100 W.",
            "Losses: (1) Iron/core loss = hysteresis + eddy current (constant, depends on V and f). (2) Copper loss = I²R in windings (variable, proportional to load²). Numerical: Output = 5000 × 1 = 5000 W; Total loss = 50 + 100 = 150 W; η = [5000 / (5000 + 150)] × 100 = 97.09%.",
            listOf(
                "2M: Explain Iron loss (constant) and Copper loss (variable I²R)",
                "3M: Compute Output = 5000 W, Total loss = 150 W, and η = 97.09%"
            ),
            "η = Output / (Output + P_i + P_cu) = 5000 / 5150 = 97.09%"
        ),
        s(
            32, 4, t4, 10,
            "Explain the voltage ratio, current ratio and the ideal-transformer relations. A 10 kVA, 2000/200 V transformer: find the rated currents and the turns ratio. Explain the effect of load power factor on regulation.",
            "Ideal transformer (zero loss/leakage, infinite μ): E₁ = V₁, E₂ = V₂, V₁I₁ = V₂I₂ ⇒ V₂/V₁ = N₂/N₁ = I₁/I₂ = K. Numerical: I₁ = 10,000/2000 = 5 A; I₂ = 10,000/200 = 50 A; K = 200/2000 = 0.1 (step-down 10:1). Effect of pf: % VR ≈ [(I R₀₂ cos φ ± I X₀₂ sin φ)/V₂] × 100. Lagging pf (+ sign): positive VR, max at cos φ = R₀₂/Z₀₂; Unity pf: small resistive drop; Leading pf (− sign): zero or negative VR (voltage rises on load).",
            listOf(
                "3M: Ideal transformer assumptions & V₂/V₁ = N₂/N₁ = I₁/I₂ = K",
                "3M: Rated currents I₁ = 5 A, I₂ = 50 A and turns ratio K = 0.1",
                "4M: Regulation formula & lagging, unity, leading pf analysis"
            ),
            "I₁ = 5 A,  I₂ = 50 A,  K = 0.1   |   %VR ≈ (I R₀₂ cos φ ± I X₀₂ sin φ)/V₂ × 100"
        ),

        // TOPIC 5
        m(33, 5, t5, "Synchronous speed of a 4-pole, 50 Hz machine is", listOf("3000 rpm", "1500 rpm", "1000 rpm", "750 rpm"), 1, "N_s = 120f/P = 120×50/4 = 1500 rpm"),
        m(34, 5, t5, "An induction motor runs at 1440 rpm with 4 poles at 50 Hz. Its slip is", listOf("2%", "4%", "6%", "10%"), 1, "s = (1500 − 1440)/1500 = 4%"),
        m(35, 5, t5, "A single-phase induction motor is", listOf("Self-starting", "Not self-starting", "Always synchronous", "A DC motor"), 1),
        m(36, 5, t5, "Slip of an induction motor at standstill is", listOf("0", "1", "0.5", "∞"), 1, "At N = 0, s = (N_s − 0)/N_s = 1"),
        m(37, 5, t5, "The rotor of a 3-phase induction motor always runs ___ synchronous speed.", listOf("At", "Below", "Above", "Twice"), 1),
        s(
            38, 5, t5, 5,
            "Explain the working principle of a three-phase induction motor.",
            "3-phase supply to 120°-spaced stator windings produces a constant-magnitude Rotating Magnetic Field (RMF) at N_s = 120f/P. RMF cuts stationary rotor conductors inducing emf (Faraday) and rotor current. By Lenz's law, rotor current opposes relative motion, creating torque that drags the rotor in the RMF direction. Rotor never reaches N_s (else zero relative speed ⇒ zero torque); slip s = (N_s − N)/N_s (typically 2–5%). No electrical rotor connection ⇒ induction motor.",
            listOf(
                "2M: RMF creation at N_s = 120f/P",
                "2M: Faraday induction + Lenz's law torque explanation",
                "1M: Why N < N_s and slip formula s = (N_s − N)/N_s"
            ),
            "N_s = 120 f / P   |   s = (N_s − N) / N_s"
        ),
        s(
            39, 5, t5, 5,
            "Why is a single-phase induction motor not self-starting? How is it made self-starting?",
            "Double-revolving field theory: a single-phase pulsating field resolves into two equal and opposite rotating fields; at standstill their torques cancel ⇒ net starting torque = 0. Made self-starting by adding an auxiliary winding displaced 90° electrically with a capacitor/resistor for phase-split: (1) Split-phase (fans, washing machines); (2) Capacitor-start / capacitor-run (compressors, pumps, refrigerators); (3) Shaded-pole (small fans, hair dryers).",
            listOf(
                "2M: Double-revolving field theory explaining zero starting torque",
                "3M: Phase-splitting auxiliary winding principle + 3 starting types & uses"
            )
        ),
        s(
            40, 5, t5, 12,
            "Describe the construction of a 3-phase induction motor and explain the production of the rotating magnetic field. Define slip and find rotor speed and rotor frequency for a 6-pole, 50 Hz motor with 4% slip.",
            "Construction: Stator (laminated silicon-steel core with 3-phase slots in cast-iron frame); Rotor (squirrel-cage: shorted Cu/Al bars; slip-ring: 3-phase wound rotor with slip rings/brushes); small air-gap 0.3–1 mm. RMF: 3-phase currents spaced 120° produce φ_R, φ_Y, φ_B whose resultant has constant magnitude 1.5 φ_m rotating at N_s = 120f/P (swapping two leads reverses direction). Slip s = (N_s − N)/N_s. Numerical: N_s = 120×50/6 = 1000 rpm; N = 1000(1 − 0.04) = 960 rpm; rotor frequency f_r = s·f = 0.04×50 = 2 Hz. Advantages: rugged, cheap, self-starting; Disadvantages: poor speed control, low cage starting torque.",
            listOf(
                "3M: Stator, Squirrel-cage & Slip-ring rotor construction",
                "4M: RMF phasor derivation showing constant 1.5 φ_m at N_s = 120f/P",
                "3M: Numerical N_s = 1000 rpm, N = 960 rpm, f_r = 2 Hz",
                "2M: Advantages & disadvantages summary"
            ),
            "N_s = 120×50/6 = 1000 rpm  ⇒  N = 960 rpm,  f_r = s·f = 2 Hz"
        ),

        // TOPIC 6
        m(41, 6, t6, "A universal motor can run on", listOf("AC only", "DC only", "Both AC and DC", "Three-phase only"), 2),
        m(42, 6, t6, "Which FHP motor is used in table fans and hair dryers?", listOf("Shaded-pole", "Slip-ring", "Synchronous", "Series DC"), 0),
        m(43, 6, t6, "Vacuum cleaners and mixers use a", listOf("Universal motor", "Cage motor", "Stepper motor", "Wound-rotor motor"), 0),
        m(44, 6, t6, "Slip-ring induction motors are used where", listOf("Low starting torque is enough", "High starting torque is needed", "Constant speed is required", "Small power is needed"), 1),
        m(45, 6, t6, "An FHP motor has a rating", listOf("< 1 hp", "> 10 hp", "> 100 hp", "Exactly 5 hp"), 0, "1 hp = 746 W"),
        s(
            46, 6, t6, 5,
            "List the applications of three-phase induction motors.",
            "Squirrel-cage: lathes, drilling machines, water pumps, blowers, fans, conveyors, flour mills, compressors, textile machines. Slip-ring (wound-rotor): cranes, hoists, elevators, rolling mills, heavy-start loads requiring high starting torque via external rotor resistance. Reasons: rugged construction, low cost, self-starting, high efficiency and nearly constant speed.",
            listOf(
                "2M: ≥5 applications of squirrel-cage induction motors",
                "2M: ≥4 applications of slip-ring induction motors (high starting torque)",
                "1M: Key reasons for industrial popularity"
            )
        ),
        s(
            47, 6, t6, 5,
            "What are fractional horsepower motors? Give types and applications.",
            "FHP motors are motors rated below 1 hp (746 W), typically single-phase for domestic/office equipment. Types & uses: (1) Split-phase: washing machines, blowers, grinders; (2) Capacitor-start: refrigerators, AC compressors, pumps; (3) Capacitor-run (PSC): ceiling fans, exhaust fans; (4) Shaded-pole: table fans, hair dryers; (5) Universal motor (AC/DC): mixers, vacuum cleaners, portable drills, sewing machines.",
            listOf(
                "1M: Define FHP motor (< 1 hp / 746 W)",
                "4M: List all 5 FHP motor types with accurate everyday applications"
            )
        )
    )
}
