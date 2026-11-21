package com.example.model

/**
 * BEEE Unit 2.0: A.C. Circuits — Full 47-question bank (Q1–Q47).
 */
internal fun beeeUnit2Questions(): List<TestQuestion> {
    val u = 2
    val uId = "u_100111_2"
    val uTitle = "Unit 2.0: A.C. Circuits"

    fun m(q: Int, t: Int, topic: String, p: String, opts: List<String>, ans: Int, f: String = "", d: String = "NONE", exp: String = "") =
        BeeeQuestionBank.mcq(u, uId, uTitle, q, t, topic, p, opts, ans, f, d, exp)

    fun s(q: Int, t: Int, topic: String, marks: Long, p: String, ans: String, scheme: List<String>, f: String = "", d: String = "NONE") =
        BeeeQuestionBank.subj(u, uId, uTitle, q, t, topic, marks, p, ans, scheme, f, d)

    val t1 = "Signals and Waveforms"
    val t2 = "AC Quantities (Peak, Average & RMS)"
    val t3 = "Waveform Factors (Peak & Form Factor)"
    val t4 = "Phase and Phasor Representation"
    val t5 = "Impedance, Admittance & Power Triangle"
    val t6 = "Three-Phase Systems (Star & Delta)"

    return listOf(
        // TOPIC 1
        m(1, 1, t1, "The time taken to complete one cycle of an alternating quantity is called", listOf("Frequency", "Time period", "Phase", "Amplitude"), 1),
        m(2, 1, t1, "If frequency is 50 Hz, the time period is", listOf("50 ms", "20 ms", "2 ms", "0.2 s"), 1, "T = 1/f = 1/50 = 0.02 s = 20 ms"),
        m(3, 1, t1, "Angular frequency ω is equal to", listOf("πf", "2πf", "f/2π", "2π/f"), 1, "ω = 2πf rad/s"),
        m(4, 1, t1, "Which of the following is a periodic non-sinusoidal waveform?", listOf("Square wave", "Pure sine wave", "DC", "None"), 0),
        s(
            5, 1, t1, 5,
            "Define: cycle, time period, frequency, amplitude, angular frequency. Write the equation of a sinusoidal voltage.",
            "Cycle: one complete set of positive and negative values. Time period (T): time for one cycle (s). Frequency (f): cycles per second, f = 1/T (Hz). Amplitude (V_m): peak value. Angular frequency: ω = 2πf rad/s. Equation: v(t) = V_m sin(ωt ± φ). Example: v = 311 sin 314t ⇒ V_m = 311 V, ω = 314 rad/s, f = 50 Hz, T = 20 ms.",
            listOf(
                "3M: Accurate definitions of all 5 terms with SI units",
                "1M: Sinusoidal voltage equation v(t) = V_m sin(ωt ± φ)",
                "1M: Worked numerical example"
            ),
            "v(t) = V_m sin(ωt ± φ),   ω = 2πf,   T = 1/f"
        ),
        s(
            6, 1, t1, 5,
            "Name and sketch the common electrical waveforms. Mention one application of each.",
            "1. Sine wave: mains supply, AC generators. 2. Square wave: digital clocks, switching circuits. 3. Triangular wave: function generators, sweep circuits. 4. Sawtooth wave: time-base of CRO, TV scanning. 5. Pulse train: digital communication, radar. Sketch amplitude vs time marking peak value and period T.",
            listOf(
                "2.5M: Name and sketch all 5 waveforms with peak & period labelled",
                "2.5M: One accurate engineering application for each waveform"
            )
        ),

        // TOPIC 2
        m(7, 2, t2, "RMS value of a sinusoidal current with peak value 10 A is", listOf("10 A", "7.07 A", "6.37 A", "14.14 A"), 1, "I_rms = 10 / √2 = 7.07 A"),
        m(8, 2, t2, "Average value (half cycle) of a sine wave of peak V_m is", listOf("0.707 V_m", "0.637 V_m", "V_m / 2", "1.11 V_m"), 1, "V_avg = 2V_m / π = 0.637 V_m"),
        m(9, 2, t2, "Average value of a pure sine wave over a full cycle is", listOf("V_m", "0.637 V_m", "Zero", "0.707 V_m"), 2),
        m(10, 2, t2, "The 230 V AC mains has a peak value of about", listOf("230 V", "325 V", "460 V", "163 V"), 1, "V_m = 230 × √2 ≈ 325 V"),
        m(11, 2, t2, "RMS value is also called", listOf("Effective value", "Average value", "Instantaneous value", "Peak value"), 0),
        s(
            12, 2, t2, 5,
            "Define peak, average and RMS value. Why is RMS value used for AC measurement?",
            "Peak value (V_m): maximum instantaneous value. Average value: mean of instantaneous values over a half cycle (full-cycle average is zero); V_avg = 0.637 V_m. RMS value: square root of mean of squares over a full cycle; V_rms = 0.707 V_m. RMS is used because it equals the DC value producing the exact same heating effect in a resistor (P = I_rms² R).",
            listOf(
                "3M: Define peak, average (half-cycle), and RMS values with sine relations",
                "2M: Explain DC heating equivalence (P = I_rms² R) reason for AC ratings"
            ),
            "V_avg = 0.637 V_m   |   V_rms = 0.707 V_m   |   P = I_rms² R"
        ),
        s(
            13, 2, t2, 5,
            "A voltage is v = 311 sin 314t. Find the RMS value, average value (half cycle), frequency and time period.",
            "Comparing with v = V_m sin(ωt): V_m = 311 V, ω = 314 rad/s. V_rms = 311/√2 = 220 V. V_avg = 0.637 × 311 = 198 V. Frequency f = ω/(2π) = 314/(2π) = 50 Hz. Time period T = 1/50 = 0.02 s = 20 ms.",
            listOf(
                "1M: Identify V_m = 311 V and ω = 314 rad/s",
                "1.5M: Calculate V_rms = 220 V",
                "1M: Calculate V_avg = 198 V",
                "1.5M: Calculate f = 50 Hz and T = 20 ms"
            ),
            "v = 311 sin 314t  ⇒  V_rms = 220 V, V_avg = 198 V, f = 50 Hz"
        ),
        s(
            14, 2, t2, 10,
            "Derive the expressions for the RMS and average values of a sinusoidal voltage v = V_m sin ωt.",
            "RMS value: V_rms = √[(1/2π) ∫₀^(2π) V_m² sin²θ dθ]. Using sin²θ = (1 − cos 2θ)/2: ∫₀^(2π) sin²θ dθ = [θ/2 − sin 2θ/4]₀^(2π) = π. Thus V_rms = √[V_m²·π/(2π)] = V_m/√2 = 0.707 V_m. Average value (half cycle): V_avg = (1/π) ∫₀^π V_m sin θ dθ = (V_m/π)[−cos θ]₀^π = 2V_m/π = 0.637 V_m (full-cycle average = 0). Form factor = 1.11; Peak factor = 1.414.",
            listOf(
                "4M: Set up RMS integral and evaluate ∫₀^(2π) sin²θ dθ = π to get V_rms = V_m/√2 = 0.707 V_m",
                "4M: Set up half-cycle average integral and evaluate to get V_avg = 2V_m/π = 0.637 V_m",
                "2M: Note full-cycle zero average and state FF = 1.11, PF = 1.414"
            ),
            "V_rms = √[(1/2π) ∫₀^(2π) V_m² sin²θ dθ] = V_m/√2  |  V_avg = (1/π) ∫₀^π V_m sin θ dθ = 2V_m/π",
            "INTEGRAL_AREA"
        ),

        // TOPIC 3
        m(15, 3, t3, "Form factor of a sinusoidal wave is", listOf("1.414", "1.11", "1.57", "0.707"), 1),
        m(16, 3, t3, "Peak factor of a sinusoidal wave is", listOf("1.11", "1.414", "1.732", "2"), 1),
        m(17, 3, t3, "Form factor of a square wave is", listOf("1", "1.11", "1.414", "2"), 0),
        m(18, 3, t3, "Peak factor =", listOf("V_rms / V_avg", "V_m / V_rms", "V_avg / V_m", "V_m / V_avg"), 1),
        s(
            19, 3, t3, 5,
            "Define form factor and peak factor. Calculate them for a sine wave.",
            "Form factor = V_rms / V_avg. Peak factor (crest factor) = V_m / V_rms. For a sine wave: V_rms = 0.707 V_m, V_avg = 0.637 V_m ⇒ Form factor = 0.707 / 0.637 = 1.11; Peak factor = V_m / (0.707 V_m) = 1.414. Significance: peak factor indicates dielectric stress on insulation; form factor converts average-reading meters to RMS.",
            listOf(
                "2M: Definitions of Form Factor and Peak Factor",
                "2M: Calculations FF = 1.11 and PF = 1.414 for sine wave",
                "1M: Engineering significance of both factors"
            ),
            "FF = V_rms / V_avg = 1.11   |   PF = V_m / V_rms = 1.414"
        ),
        s(
            20, 3, t3, 5,
            "Find the form factor of a triangular wave having peak value V_m.",
            "For a symmetrical triangular wave: half-cycle average V_avg = V_m / 2 and RMS value V_rms = V_m / √3. Form factor = (V_m/√3)/(V_m/2) = 2/√3 = 1.155. Peak factor = V_m/(V_m/√3) = √3 = 1.732.",
            listOf(
                "2M: State V_avg = V_m/2 and V_rms = V_m/√3 for triangular wave",
                "2M: Compute Form factor = 2/√3 = 1.155",
                "1M: Compute Peak factor = √3 = 1.732"
            ),
            "FF = (V_m/√3) / (V_m/2) = 2/√3 = 1.155"
        ),

        // TOPIC 4
        m(21, 4, t4, "In a purely inductive circuit, current ___ voltage by 90°.", listOf("Leads", "Lags", "Is in phase with", "Is opposite to"), 1),
        m(22, 4, t4, "In a purely capacitive circuit, current ___ voltage by 90°.", listOf("Leads", "Lags", "Is in phase with", "Is zero"), 0),
        m(23, 4, t4, "In a purely resistive circuit the phase angle between V and I is", listOf("0°", "90°", "180°", "45°"), 0),
        m(24, 4, t4, "A phasor rotates in the ___ direction.", listOf("Clockwise", "Anticlockwise", "Random", "Fixed"), 1),
        m(25, 4, t4, "v = 100 sin(ωt + 30°) and i = 5 sin(ωt − 45°). Voltage leads current by", listOf("15°", "75°", "30°", "45°"), 1, "φ = +30° − (−45°) = 75°"),
        s(
            26, 4, t4, 5,
            "Explain leading, lagging and in-phase quantities with waveforms and phasors.",
            "In-phase: reach maxima and zeros simultaneously (φ = 0°, e.g., V and I in a resistor). Leading: reaches positive peak earlier (current in capacitor leads V by 90°, drawn anticlockwise at +angle). Lagging: reaches peak later (current in inductor lags V by 90°, drawn clockwise at −angle).",
            listOf(
                "3M: Clear definitions of in-phase, leading, and lagging with R/L/C examples",
                "2M: Neat waveform and phasor diagram conventions (+anticlockwise, −clockwise)"
            )
        ),
        s(
            27, 4, t4, 5,
            "Represent v = 141.4 sin(314t + 30°) and i = 10 sin(314t − 30°) as phasors and find the phase difference.",
            "RMS voltage phasor V = 141.4/√2 ∠+30° = 100 V ∠+30°. RMS current phasor I = 10/√2 ∠−30° = 7.07 A ∠−30°. Phase difference φ = 30° − (−30°) = 60°; voltage leads current by 60° (current lags by 60°). Phasor diagram shows two arrows from origin at +30° and −30° with 60° arc between them.",
            listOf(
                "2M: Compute RMS phasors V = 100∠+30° V and I = 7.07∠−30° A",
                "2M: Compute phase difference φ = 60° (V leads I / I lags V)",
                "1M: Neat labelled phasor diagram"
            ),
            "V = 100∠+30° V,  I = 7.07∠−30° A  ⇒  φ = 60°"
        ),
        s(
            28, 4, t4, 10,
            "Explain the phasor representation of sinusoidal quantities. Draw the waveforms and phasor diagrams of pure R, pure L and pure C circuits and derive the phase relation in each.",
            "Phasor: line of length V_m or V_rms rotating anticlockwise at ω; vertical projection gives instantaneous value; rectangular V = a + jb, polar V = |V|∠φ. Pure R: v = V_m sin ωt ⇒ i = (V_m/R) sin ωt ⇒ φ = 0°, P = VI. Pure L: i = I_m sin ωt ⇒ v = L di/dt = ωL I_m cos ωt = V_m sin(ωt + 90°) ⇒ current lags by 90°, X_L = ωL, P_avg = 0. Pure C: v = V_m sin ωt ⇒ i = C dv/dt = ωC V_m cos ωt = I_m sin(ωt + 90°) ⇒ current leads by 90°, X_C = 1/(ωC), P_avg = 0. Advantage: converts AC differential equations into simple vector algebra V = IZ.",
            listOf(
                "2M: Phasor definition, rectangular & polar forms, and advantages",
                "2M: Pure R derivation (φ = 0°, P = VI) + diagram",
                "3M: Pure L derivation via v = L di/dt (I lags V by 90°, X_L = 2πfL) + diagram",
                "3M: Pure C derivation via i = C dv/dt (I leads V by 90°, X_C = 1/2πfC) + diagram"
            ),
            "Pure L: v = L di/dt ⇒ I lags 90°  |  Pure C: i = C dv/dt ⇒ I leads 90°",
            "RLC_CIRCUIT"
        ),

        // TOPIC 5
        m(29, 5, t5, "Unit of impedance is", listOf("Siemens", "Ohm", "Henry", "Farad"), 1),
        m(30, 5, t5, "Unit of admittance is", listOf("Ohm", "Mho (Siemens)", "Volt", "VAR"), 1),
        m(31, 5, t5, "Power factor of a purely inductive circuit is", listOf("1", "0", "0.5", "0.707"), 1, "cos 90° = 0"),
        m(32, 5, t5, "Real power (active power) is measured in", listOf("VA", "VAR", "Watt", "Joule"), 2),
        m(33, 5, t5, "For an R–L series circuit with R = 6 Ω and X_L = 8 Ω, impedance is", listOf("14 Ω", "10 Ω", "2 Ω", "48 Ω"), 1, "Z = √(6² + 8²) = 10 Ω"),
        m(34, 5, t5, "The power factor of an R–L series circuit with R = 6 Ω and X_L = 8 Ω is", listOf("0.8 lead", "0.6 lag", "0.6 lead", "0.8 lag"), 1, "cos φ = R/Z = 6/10 = 0.6 lag"),
        s(
            35, 5, t5, 5,
            "Define impedance and admittance. Write the expression of impedance for series R–L–C circuit.",
            "Impedance (Z): total opposition to AC current offered by R, L, C; Z = V/I (Ω). Admittance (Y): reciprocal of impedance, Y = 1/Z = G + jB (siemens/mho). Series R–L–C: Z = R + j(X_L − X_C), |Z| = √[R² + (ωL − 1/ωC)²], φ = tan⁻¹[(X_L − X_C)/R]. X_L > X_C inductive (lagging); X_C > X_L capacitive (leading); X_L = X_C resonance (Z = R).",
            listOf(
                "2M: Define Impedance (Ω) and Admittance Y = G + jB (Siemens)",
                "2M: Write Z = R + j(X_L − X_C), |Z|, and phase angle φ",
                "1M: State inductive, capacitive, and resonance conditions"
            ),
            "Z = R + j(X_L − X_C),   |Z| = √[R² + (ωL − 1/ωC)²]",
            "RLC_CIRCUIT"
        ),
        s(
            36, 5, t5, 5,
            "What is power factor? Why is a low power factor undesirable?",
            "Power factor = cos φ = True Power / Apparent Power = P/S = R/Z (0 to 1). Disadvantages of low pf: (1) Higher current I = P/(V cos φ) causing larger I²R copper losses; (2) Larger conductor & switchgear size ⇒ higher cost; (3) Poor voltage regulation and wasted kVA capacity of transformers/generators; (4) Utility tariff penalties. Improvement: connect shunt capacitor banks or synchronous condensers.",
            listOf(
                "1.5M: Define power factor cos φ = P/S = R/Z",
                "2.5M: List ≥4 disadvantages of low power factor",
                "1M: State improvement methods (capacitor bank / synchronous condenser)"
            ),
            "cos φ = P / S = R / Z,   I = P / (V cos φ)"
        ),
        s(
            37, 5, t5, 10,
            "Explain the power triangle. A series R–L circuit with R = 6 Ω and X_L = 8 Ω is connected to 100 V, 50 Hz supply. Calculate impedance, current, power factor, active, reactive and apparent power.",
            "Power triangle: right triangle with base P = VI cos φ (W), height Q = VI sin φ (VAR), hypotenuse S = VI (VA); S² = P² + Q². Numerical: Z = √(6² + 8²) = 10 Ω; I = 100/10 = 10 A (lagging); cos φ = 6/10 = 0.6 lagging (φ = 53.13°); P = I²R = 600 W; Q = I²X_L = 800 VAR; S = VI = 1000 VA (check √(600² + 800²) = 1000).",
            listOf(
                "3M: Power triangle diagram + definitions & formulas for P, Q, S",
                "2M: Z = 10 Ω and I = 10 A lagging",
                "2M: Power factor cos φ = 0.6 lagging (φ = 53.13°)",
                "3M: P = 600 W, Q = 800 VAR, S = 1000 VA with units"
            ),
            "S² = P² + Q²  |  Z = 10 Ω, I = 10 A, P = 600 W, Q = 800 VAR, S = 1000 VA",
            "RLC_CIRCUIT"
        ),
        s(
            38, 5, t5, 12,
            "Derive the impedance, phase angle and power factor of a series R–L–C circuit. State the condition of resonance and the resonant frequency.",
            "For current i = I_m sin ωt in series R, L, C: V_R = IR (in phase), V_L = IX_L (leads I by 90°), V_C = IX_C (lags I by 90°). Phasor sum: V = √[V_R² + (V_L − V_C)²] = I√[R² + (X_L − X_C)²] ⇒ Z = √[R² + (X_L − X_C)²], φ = tan⁻¹[(X_L − X_C)/R], cos φ = R/Z. Cases: X_L > X_C lagging; X_L < X_C leading; X_L = X_C unity pf. Resonance: X_L = X_C ⇒ Z_min = R, I_max = V/R, pf = 1, resonant frequency f_r = 1/(2π√(LC)). Quality factor Q = X_L/R gives voltage magnification V_L = V_C = QV.",
            listOf(
                "3M: Circuit & phasor diagrams for all three cases",
                "4M: Derive Z = √[R² + (X_L − X_C)²], φ, and cos φ = R/Z",
                "3M: Resonance condition (X_L = X_C, Z = R, pf = 1) and f_r = 1/(2π√(LC))",
                "2M: Quality factor Q = X_L/R and voltage magnification note"
            ),
            "Z = √[R² + (X_L − X_C)²],   f_r = 1 / (2π√(LC))",
            "RLC_CIRCUIT"
        ),

        // TOPIC 6
        m(39, 6, t6, "Phase difference between the three voltages in a 3-phase supply is", listOf("90°", "120°", "180°", "60°"), 1),
        m(40, 6, t6, "In a star connection, line voltage =", listOf("Phase voltage", "√3 × phase voltage", "3 × phase voltage", "Phase voltage / √3"), 1, "V_L = √3 V_ph"),
        m(41, 6, t6, "In a delta connection, line current =", listOf("Phase current", "√3 × phase current", "Phase current / √3", "3 × phase current"), 1, "I_L = √3 I_ph"),
        m(42, 6, t6, "Total power of a balanced 3-phase load is", listOf("VI cos φ", "√3 V_L I_L cos φ", "3 V_L I_L", "V_L I_L sin φ"), 1, "P = √3 V_L I_L cos φ"),
        m(43, 6, t6, "Neutral current in a balanced star load is", listOf("Maximum", "Zero", "3 I_ph", "I_ph"), 1),
        s(
            44, 6, t6, 5,
            "Give the advantages of three-phase systems over single-phase systems.",
            "1. ~1.5× higher output for the same frame size, cheaper per kW. 2. Constant (non-pulsating) total instantaneous power ⇒ smooth motor running. 3. Requires less conductor material (~75% copper of single-phase). 4. 3-phase induction motors are self-starting, simple and rugged. 5. Higher efficiency, better voltage regulation, and provides both 3-phase and single-phase (phase-neutral) supply.",
            listOf(
                "3M: Power/size advantage, constant power, and ~25% conductor copper saving",
                "2M: Self-starting motors, better regulation, and dual 3φ/1φ availability"
            )
        ),
        s(
            45, 6, t6, 5,
            "What is phase sequence? What is a balanced and an unbalanced load?",
            "Phase sequence: order in which the three phase voltages reach their positive maxima (e.g., R–Y–B); interchanging any two lines reverses motor rotation. Balanced load/supply: equal voltage magnitudes 120° apart and equal impedances in all three phases ⇒ equal phase currents and neutral current I_N = 0. Unbalanced load: unequal phase impedances ⇒ unequal currents/angles and non-zero neutral current I_N = I_R + I_Y + I_B (phasor sum).",
            listOf(
                "2M: Define phase sequence (R-Y-B) and effect of reversing two lines",
                "1.5M: Define balanced load (equal Z, I_N = 0)",
                "1.5M: Define unbalanced load (unequal Z, I_N = I_R + I_Y + I_B)"
            )
        ),
        s(
            46, 6, t6, 12,
            "Derive the relations between line and phase quantities for a balanced star-connected load. A balanced star load of (8 + j6) Ω per phase is connected to a 400 V, 3-phase supply. Find line current, power factor and total power.",
            "Star connection: common neutral ⇒ I_L = I_ph. Line voltage V_RY = V_R − V_Y (phasor difference with 120° apart) ⇒ |V_RY| = 2 V_ph cos 30° = √3 V_ph, leading V_R by 30°. Total power P = 3 V_ph I_ph cos φ = √3 V_L I_L cos φ. Numerical: V_ph = 400/√3 = 230.9 V; Z_ph = √(8² + 6²) = 10 Ω; I_L = I_ph = 230.9/10 = 23.09 A; cos φ = 8/10 = 0.8 lagging; P = √3 × 400 × 23.09 × 0.8 = 12,800 W = 12.8 kW.",
            listOf(
                "4M: Star diagram & phasor derivation showing I_L = I_ph and V_L = √3 V_ph",
                "2M: Power formula derivation P = √3 V_L I_L cos φ",
                "3M: Numerical V_ph = 230.9 V, Z_ph = 10 Ω, I_L = 23.09 A",
                "3M: Power factor = 0.8 lagging and Total Power P = 12.8 kW"
            ),
            "V_L = √3 V_ph,  I_L = I_ph,  P = √3 V_L I_L cos φ = 12.8 kW"
        ),
        s(
            47, 6, t6, 10,
            "Derive the relation between line and phase quantities in a balanced delta connection and compare star and delta connections.",
            "Delta: end of each phase joined to start of next ⇒ V_L = V_ph. By KCL at junction, I_R = I_RY − I_BR (phasors) ⇒ |I_L| = 2 I_ph cos 30° = √3 I_ph (lags phase current by 30°). Power P = 3 V_ph I_ph cos φ = √3 V_L I_L cos φ. Comparison: Star has V_L = √3 V_ph, I_L = I_ph, neutral available (4-wire distribution, lower phase insulation); Delta has V_L = V_ph, I_L = √3 I_ph, no neutral (heavy loads, motors, 3× power/current vs star for same Z).",
            listOf(
                "4M: Delta diagram & KCL phasor derivation V_L = V_ph and I_L = √3 I_ph",
                "2M: Derive P = √3 V_L I_L cos φ",
                "4M: Structured comparison of Star vs Delta (neutral, voltage/current, insulation, 3× power ratio, uses)"
            ),
            "V_L = V_ph,   I_L = √3 I_ph,   P_Δ = 3 P_Y"
        )
    )
}
