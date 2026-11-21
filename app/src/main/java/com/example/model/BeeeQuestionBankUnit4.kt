package com.example.model

/**
 * BEEE Unit 4.0: Semiconductor Devices — Full 48-question bank (Q1–Q48).
 */
internal fun beeeUnit4Questions(): List<TestQuestion> {
    val u = 4
    val uId = "u_100111_4"
    val uTitle = "Unit 4.0: Semiconductor Devices"

    fun m(q: Int, t: Int, topic: String, p: String, opts: List<String>, ans: Int, f: String = "", d: String = "NONE", exp: String = "") =
        BeeeQuestionBank.mcq(u, uId, uTitle, q, t, topic, p, opts, ans, f, d, exp)

    fun s(q: Int, t: Int, topic: String, marks: Long, p: String, ans: String, scheme: List<String>, f: String = "", d: String = "NONE") =
        BeeeQuestionBank.subj(u, uId, uTitle, q, t, topic, marks, p, ans, scheme, f, d)

    val t1 = "Semiconductor Materials (Intrinsic & Extrinsic)"
    val t2 = "p-n Junction Diode & Biasing"
    val t3 = "Diode Temperature Effects & Breakdown"
    val t4 = "Rectifiers, Filters & Zener Regulator"
    val t5 = "BJT Fundamentals (NPN, PNP, α & β)"
    val t6 = "BJT Applications (CE Amplifier & Switch)"

    return listOf(
        // TOPIC 1
        m(1, 1, t1, "A pentavalent impurity added to pure silicon produces", listOf("P-type semiconductor", "N-type semiconductor", "Intrinsic semiconductor", "Insulator"), 1),
        m(2, 1, t1, "In a P-type semiconductor the majority carriers are", listOf("Electrons", "Holes", "Protons", "Neutrons"), 1),
        m(3, 1, t1, "Energy gap of silicon at room temperature is about", listOf("0.67 eV", "1.1 eV", "5 eV", "0.1 eV"), 1),
        m(4, 1, t1, "The resistance of a semiconductor with rise in temperature", listOf("Increases", "Decreases", "Remains constant", "Becomes infinite"), 1),
        m(5, 1, t1, "Which is a trivalent impurity?", listOf("Phosphorus", "Arsenic", "Boron", "Antimony"), 2),
        s(
            6, 1, t1, 5,
            "Differentiate between intrinsic and extrinsic semiconductors.",
            "Intrinsic: pure semiconductor (Si, Ge) with no doping; equal electrons & holes (n = p = n_i); low, strongly temperature-dependent conductivity; starting base material. Extrinsic: doped with tiny impurity (~1 in 10⁷); one carrier dominates (N-type electrons or P-type holes); much higher conductivity controlled by doping; used directly in diodes, transistors and ICs.",
            listOf(
                "2.5M: Intrinsic definition, n = p = n_i, low conductivity, temperature dependence",
                "2.5M: Extrinsic doping ratio, majority carriers, controlled high conductivity, device uses"
            ),
            "n · p = n_i²"
        ),
        s(
            7, 1, t1, 5,
            "Explain N-type and P-type semiconductors with the help of energy bands / lattice diagrams.",
            "N-type: doped with pentavalent donors (P, As, Sb); 4 electrons form covalent bonds, 5th is free ⇒ donor level just below conduction band; electrons = majority, holes = minority; fixed +ve donor ion. P-type: doped with trivalent acceptors (B, Al, Ga); 3 valence electrons leave 1 incomplete bond (hole) ⇒ acceptor level just above valence band; holes = majority, electrons = minority; fixed −ve acceptor ion. Both are electrically neutral overall.",
            listOf(
                "2M: N-type doping, donor level, majority/minority carriers",
                "2M: P-type doping, acceptor level, majority/minority carriers",
                "1M: Electrical neutrality note + lattice / energy-band diagram"
            )
        ),

        // TOPIC 2
        m(8, 2, t2, "Barrier potential of a silicon diode is about", listOf("0.3 V", "0.7 V", "1.1 V", "5 V"), 1),
        m(9, 2, t2, "In forward bias, the width of the depletion region", listOf("Increases", "Decreases", "Remains same", "Becomes infinite"), 1),
        m(10, 2, t2, "A diode in reverse bias carries only", listOf("Large forward current", "Small leakage current", "No current at all", "Infinite current"), 1),
        m(11, 2, t2, "The p–n junction diode conducts when the P-side is connected to", listOf("Negative terminal", "Positive terminal", "Ground only", "Neither"), 1),
        m(12, 2, t2, "The knee (cut-in) voltage of germanium diode is", listOf("0.7 V", "0.3 V", "1.2 V", "0.1 V"), 1),
        s(
            13, 2, t2, 5,
            "Explain the formation of the depletion region and barrier potential in a p–n junction.",
            "When P and N regions join, electrons diffuse N→P and holes P→N, recombining near the junction to uncover fixed positive donor ions (N-side) and fixed negative acceptor ions (P-side). This mobile-carrier-free zone is the depletion (space-charge) region. The fixed ions create an N→P electric field opposing further diffusion; the resulting potential difference is the barrier potential (≈0.7 V Si, ≈0.3 V Ge). At equilibrium, diffusion current = drift current ⇒ net current = 0.",
            listOf(
                "2M: Diffusion & recombination forming fixed donor/acceptor ions",
                "2M: Depletion region & opposing electric field creating barrier potential (0.7 V Si / 0.3 V Ge)",
                "1M: Equilibrium condition (I_diffusion = I_drift)"
            )
        ),
        s(
            14, 2, t2, 5,
            "Compare forward and reverse bias of a p–n junction diode.",
            "Forward bias: P to +ve, N to −ve; applied field opposes barrier; depletion layer narrows; majority carriers cross; current rises exponentially after V_γ; low resistance (ohms). Reverse bias: P to −ve, N to +ve; depletion layer widens; majority carriers pulled back; only tiny minority leakage current I_s (nA–µA); very high resistance (MΩ); breakdown occurs above V_BR.",
            listOf(
                "2.5M: Forward bias polarity, narrow depletion layer, majority current, low R",
                "2.5M: Reverse bias polarity, wide depletion layer, tiny leakage I_s, high R & breakdown"
            )
        ),
        s(
            15, 2, t2, 10,
            "Draw and explain the V–I characteristics of a p–n junction diode in forward and reverse bias. Define dynamic resistance and PIV.",
            "Forward V–I (1st quadrant): negligible current for V < V_γ (0.7 V Si / 0.3 V Ge); exponential rise thereafter obeying I = I_s(e^(V/ηV_T) − 1), V_T ≈ 26 mV at 300 K (η = 1 Ge, ≈2 Si at low I). Reverse V–I (3rd quadrant): tiny constant I_s (µA Ge, nA Si) until breakdown voltage V_BR where current rises sharply. Dynamic (AC) resistance: r_d = ΔV/ΔI = ηV_T/I_D ≈ 26 mV / I_D (static R = V/I). PIV (Peak Inverse Voltage): maximum reverse voltage a diode withstands safely without breakdown.",
            listOf(
                "3M: Neat labelled forward & reverse V–I characteristic graph",
                "3M: Forward & reverse region explanation with Shockley diode equation",
                "2M: Define dynamic resistance r_d = ηV_T/I_D vs static resistance",
                "2M: Define PIV and ideal vs practical diode models"
            ),
            "I = I_s (e^(V / ηV_T) − 1),   r_d = ηV_T / I_D ≈ 26 mV / I_D"
        ),

        // TOPIC 3
        m(16, 3, t3, "Reverse saturation current of a diode ___ for every 10 °C rise in temperature.", listOf("Halves", "Doubles", "Remains same", "Triples"), 1),
        m(17, 3, t3, "Zener breakdown occurs in", listOf("Lightly doped, wide junction", "Heavily doped, narrow junction", "Intrinsic material", "Metals"), 1),
        m(18, 3, t3, "Avalanche breakdown is caused by", listOf("Impact ionisation", "Tunnelling of electrons", "Heating only", "Light"), 0),
        m(19, 3, t3, "With rise in temperature, forward voltage drop of a diode", listOf("Increases", "Decreases", "Is unchanged", "Becomes zero"), 1, "Falls by ≈ 2 mV/°C"),
        s(
            20, 3, t3, 5,
            "Explain the effect of temperature on a p–n junction diode.",
            "1. Reverse saturation current I_s doubles for every 10 °C rise due to thermal generation of minority carriers (Ge leaks more than Si). 2. Forward voltage drop falls by ≈2 mV/°C (curve shifts left). 3. Breakdown voltage: Zener V_Z decreases (negative temp coeff), Avalanche increases (positive temp coeff). 4. High temperature risks thermal runaway; Si works up to ≈175 °C vs Ge ≈75 °C.",
            listOf(
                "2M: Reverse current doubling every 10 °C & forward drop −2 mV/°C",
                "2M: Opposite temperature effects on Zener vs Avalanche breakdown",
                "1M: Thermal runaway & Si (175 °C) vs Ge (75 °C) limits"
            )
        ),
        s(
            21, 3, t3, 5,
            "Differentiate between Zener and avalanche breakdown.",
            "Zener: heavily doped, narrow depletion layer; intense E-field (>10⁷ V/m) rips valence electrons via field ionisation/tunnelling; occurs at V_Z < 5 V; negative temp coeff; sharp knee. Avalanche: lightly doped, wide depletion layer; accelerated minority carriers create carrier multiplication via impact ionisation; occurs at V > 6 V; positive temp coeff; softer knee. Between 5–6 V both act together (near-zero temp coeff).",
            listOf(
                "2M: Zener mechanism (heavy doping, narrow junction, field emission, <5 V, −ve TC)",
                "2M: Avalanche mechanism (light doping, wide junction, impact ionisation, >6 V, +ve TC)",
                "1M: Transition region 5–6 V (zero temp coefficient)"
            )
        ),

        // TOPIC 4
        m(22, 4, t4, "Ripple factor of a full-wave rectifier is", listOf("1.21", "0.482", "0.121", "0.707"), 1),
        m(23, 4, t4, "Maximum efficiency of a half-wave rectifier is", listOf("40.6%", "81.2%", "100%", "50%"), 0),
        m(24, 4, t4, "PIV of each diode in a bridge rectifier is", listOf("V_m", "2V_m", "V_m / 2", "V_m / √2"), 0),
        m(25, 4, t4, "D.C. output of a full-wave rectifier (ideal) is", listOf("V_m / π", "2V_m / π", "V_m / 2", "V_m"), 1),
        m(26, 4, t4, "A Zener diode regulator is operated in", listOf("Forward bias", "Reverse bias (breakdown)", "No bias", "Saturation"), 1),
        m(27, 4, t4, "A capacitor filter ___ the ripple in the rectifier output.", listOf("Increases", "Reduces", "Doubles", "Has no effect on"), 1),
        s(
            28, 4, t4, 5,
            "Explain the working of a half-wave rectifier with a neat circuit diagram and waveforms. Write its important parameters.",
            "Circuit: AC transformer secondary, single diode, load R_L in series. Working: Positive half cycle ⇒ diode forward biased, conducts; Negative half cycle ⇒ reverse biased, output = 0 ⇒ pulsating DC. Parameters: V_dc = V_m/π = 0.318 V_m; V_rms = V_m/2; I_dc = I_m/π; Ripple factor γ = 1.21; η_max = 40.6%; PIV = V_m; TUF = 0.287. Disadvantages: high ripple, low efficiency, DC core saturation.",
            listOf(
                "2M: Circuit diagram & input/output waveforms",
                "1.5M: Positive & negative half-cycle operation",
                "1.5M: Key parameters (V_dc, V_rms, γ = 1.21, η = 40.6%, PIV = V_m)"
            ),
            "V_dc = V_m / π,   γ = 1.21,   η_max = 40.6%,   PIV = V_m"
        ),
        s(
            29, 4, t4, 5,
            "Explain the working of a bridge full-wave rectifier. Compare it with a centre-tapped full-wave rectifier.",
            "Bridge: 4 diodes D₁–D₄; positive half cycle D₁ & D₃ conduct; negative half cycle D₂ & D₄ conduct; unidirectional load current in both halves. V_dc = 2V_m/π, γ = 0.482, η = 81.2%, PIV = V_m. Comparison: Bridge avoids centre-tapped transformer, halves PIV (V_m vs 2V_m), has higher TUF, but uses 4 diodes and suffers two diode drops (≈1.4 V Si). Centre-tap uses 2 diodes with PIV = 2V_m.",
            listOf(
                "2.5M: Bridge circuit, alternating diode pairs D₁D₃ / D₂D₄, waveforms & parameters",
                "2.5M: Comparison with centre-tap (transformer, diode count, PIV, voltage drop, TUF)"
            ),
            "V_dc = 2V_m / π,   γ = 0.482,   η_max = 81.2%"
        ),
        s(
            30, 4, t4, 5,
            "Explain the capacitor filter. Why is it required after a rectifier?",
            "Rectifier output is pulsating DC containing heavy AC ripple. A shunt capacitor across R_L charges quickly to V_m during rising input peaks and discharges slowly through R_L when diodes turn off, smoothing the output toward steady DC. Larger C and R_L reduce ripple: FWR ripple factor γ ≈ 1/(4√3 f C R_L). Other filters: choke (L), LC, and π-filter (C–L–C).",
            listOf(
                "2M: Need for filtering pulsating DC",
                "2M: Charging/discharging action with smoothed waveform sketch",
                "1M: Ripple factor formula γ ≈ 1/(4√3 f C R_L) & other filter types"
            ),
            "γ_FWR ≈ 1 / (4√3 f C R_L)"
        ),
        s(
            31, 4, t4, 10,
            "Explain the working of a Zener diode as a voltage regulator. Design a regulator for 5.1 V output, load 100 mA, input 12 V (Zener min current 10 mA).",
            "Principle: In reverse breakdown, Zener maintains nearly constant V_Z across I_Z,min to I_Z,max. Circuit: V_in → series resistor R_s → parallel Zener (cathode to node) and load R_L. Line regulation: V_in ↑ ⇒ I_Z ↑, excess drops across R_s, V_out stays V_Z. Load regulation: I_L ↑ ⇒ I_Z ↓ by equal amount. I_s = I_Z + I_L = (V_in − V_Z)/R_s. Design: I_s = 100 + 10 = 110 mA; R_s = (12 − 5.1)/0.110 = 62.7 Ω ≈ 62 Ω; P_Rs = (0.11)²×62 ≈ 0.75 W (use 1 W); max Zener power (no load) = 5.1×0.11 ≈ 0.56 W (choose 1 W Zener).",
            listOf(
                "3M: Circuit diagram & line/load regulation explanation",
                "2M: Current balance equation I_s = I_Z + I_L = (V_in − V_Z)/R_s",
                "3M: Design R_s = 62.7 Ω (62 Ω standard) with I_s = 110 mA",
                "2M: Power ratings for R_s (1 W) and Zener (1 W) + applications"
            ),
            "R_s = (V_in − V_Z) / (I_L + I_Z,min) = (12 − 5.1) / 0.11 = 62.7 Ω"
        ),
        s(
            32, 4, t4, 10,
            "A full-wave bridge rectifier is fed from a 20 V (rms) transformer secondary. Calculate peak voltage, DC output and PIV (ideal diodes). Derive V_dc for a full-wave rectifier.",
            "Derivation: V_dc = (1/π) ∫₀^π V_m sin θ dθ = (V_m/π)[−cos θ]₀^π = 2V_m/π = 0.636 V_m. V_rms = V_m/√2; γ = √[(V_rms/V_dc)² − 1] = 0.482; η = 8/π² = 81.2%. Numerical: V_m = 20 × √2 = 28.28 V; V_dc = 2 × 28.28 / π = 18.0 V (ideal; ≈16.6 V with two 0.7 V Si drops); PIV = V_m = 28.28 V (bridge).",
            listOf(
                "5M: Calculus derivation of V_dc = (1/π) ∫₀^π V_m sin θ dθ = 2V_m/π, γ = 0.482, η = 81.2%",
                "2M: Compute peak voltage V_m = 28.28 V",
                "3M: Compute V_dc = 18.0 V and PIV = 28.28 V"
            ),
            "V_dc = (1/π) ∫₀^π V_m sin θ dθ = 2V_m / π = 18.0 V,   PIV = 28.28 V",
            "INTEGRAL_AREA"
        ),

        // TOPIC 5
        m(33, 5, t5, "In a BJT the base region is", listOf("Heavily doped and thick", "Lightly doped and thin", "Heavily doped and thin", "Undoped"), 1),
        m(34, 5, t5, "For active operation the emitter–base junction is", listOf("Reverse biased", "Forward biased", "Unbiased", "Broken"), 1),
        m(35, 5, t5, "Relation between current gains is", listOf("β = α/(1 − α)", "β = 1 − α", "β = α(1 − α)", "β = 1/α"), 0, "β = α / (1 − α)"),
        m(36, 5, t5, "If I_B = 40 µA and β = 100, I_C equals", listOf("4 mA", "0.4 mA", "40 mA", "4 µA"), 0, "I_C = β I_B = 100 × 40 µA = 4 mA"),
        m(37, 5, t5, "The arrow in the symbol of an NPN transistor points", listOf("Into the base", "Out of the emitter", "Into the collector", "Out of the base"), 1),
        s(
            38, 5, t5, 5,
            "Explain the construction and symbols of NPN and PNP transistors.",
            "NPN: thin P-type base sandwiched between N-type emitter & collector; electrons are majority carriers; emitter arrow points outward. PNP: thin N-type base between P-type emitter & collector; holes are majority; emitter arrow points inward. Doping & area: Emitter heavily doped (injects carriers), Base very thin & lightly doped (minimizes recombination), Collector moderately doped & largest area (heat dissipation). NPN is preferred due to higher electron mobility (faster).",
            listOf(
                "2M: NPN & PNP layer structure and circuit symbols (arrow direction)",
                "2M: Doping levels & physical area roles of Emitter, Base, Collector",
                "1M: Reason NPN is preferred over PNP"
            )
        ),
        s(
            39, 5, t5, 5,
            "Explain the working of an NPN transistor in the active region.",
            "EB junction is forward biased (V_BE ≈ 0.7 V) and CB junction is reverse biased. Electrons from heavily doped N-emitter inject into thin P-base. Only ~1–5% recombine with holes (producing small I_B); remaining ~95–99% are swept into the reverse-biased collector forming I_C. Equations: I_E = I_B + I_C; α = I_C/I_E ≈ 0.98; β = I_C/I_B ≈ 50–300. Small base current controls large collector current ⇒ current amplification.",
            listOf(
                "2M: Active-region biasing (EB forward, CB reverse) & carrier flow",
                "2M: Equations I_E = I_B + I_C, α = I_C/I_E, β = I_C/I_B",
                "1M: Current amplification conclusion"
            ),
            "I_E = I_B + I_C,   α = I_C / I_E,   β = I_C / I_B = α / (1 − α)"
        ),
        s(
            40, 5, t5, 10,
            "Explain the three configurations of a BJT (CB, CE, CC) and compare them. For a transistor with I_B = 40 µA and β = 100 find I_C, I_E and α.",
            "CB: input emitter, output collector; gain α < 1; low R_in, high R_out; 0° phase shift (HF circuits). CE: input base, output collector; high current gain β; medium R_in/R_out; very high power gain; 180° phase shift (amplifiers/switches). CC (emitter follower): input base, output emitter; current gain (β + 1), voltage gain ≈1; high R_in, low R_out; 0° phase shift (buffer/impedance matching). Numerical: I_C = βI_B = 100 × 40 µA = 4 mA; I_E = 0.04 + 4 = 4.04 mA; α = I_C/I_E = 4/4.04 = 0.99 (check β/(1+β) = 100/101 = 0.99).",
            listOf(
                "6M: Detailed explanation & comparison table of CB, CE, and CC (gains, R_in/R_out, phase shift, uses)",
                "4M: Numerical I_C = 4 mA, I_E = 4.04 mA, α = 0.99"
            ),
            "I_C = 4 mA,   I_E = 4.04 mA,   α = 4 / 4.04 = 0.99"
        ),

        // TOPIC 6
        m(41, 6, t6, "When a transistor is used as a switch (ON), it operates in", listOf("Cut-off region", "Active region", "Saturation region", "Breakdown region"), 2),
        m(42, 6, t6, "When a transistor is OFF as a switch it is in the", listOf("Cut-off region", "Saturation region", "Active region", "Reverse active"), 0),
        m(43, 6, t6, "A CE amplifier produces a phase shift of", listOf("0°", "90°", "180°", "360°"), 2),
        m(44, 6, t6, "V_CE(sat) of a silicon transistor is about", listOf("5 V", "0.2 V", "0.7 V", "12 V"), 1),
        m(45, 6, t6, "The transistor as an amplifier works in the", listOf("Cut-off region", "Active region", "Saturation region", "None"), 1),
        s(
            46, 6, t6, 5,
            "Explain how a transistor works as a switch.",
            "Circuit: NPN transistor with base resistor R_B and collector load R_C (LED/relay). OFF state (cut-off): V_in = 0 V ⇒ I_B = 0, I_C ≈ 0 ⇒ V_CE ≈ V_CC (open switch). ON state (saturation): high input drives I_B ≥ I_C(sat)/β ⇒ V_CE(sat) ≈ 0.2 V and I_C(sat) ≈ V_CC/R_C (closed switch). Acts as inverter (high input ⇒ low collector output). Used in digital logic, relay/LED drivers.",
            listOf(
                "1.5M: Circuit diagram of NPN transistor switch",
                "1.5M: Cut-off (OFF) conditions (I_B = 0, V_CE = V_CC)",
                "2M: Saturation (ON) conditions (I_B ≥ I_C(sat)/β, V_CE ≈ 0.2 V) & applications"
            )
        ),
        s(
            47, 6, t6, 5,
            "Draw the circuit of a CE amplifier and explain its working.",
            "Components: NPN transistor, voltage-divider bias R₁–R₂, collector resistor R_C, emitter resistor R_E with bypass capacitor C_E, coupling capacitors C₁, C₂, supply V_CC. Working: Bias fixes Q-point in active region. Small AC input varies I_B ⇒ I_C = βI_B varies strongly ⇒ large amplified voltage drop across R_C. Positive input swing increases I_C ⇒ lowers V_C = V_CC − I_C R_C ⇒ 180° inverted amplified output. C₁, C₂ block DC/pass AC; C_E prevents AC gain loss.",
            listOf(
                "2M: Neat labelled CE amplifier circuit diagram",
                "2M: Working mechanism & 180° phase inversion explanation",
                "1M: Roles of coupling capacitors C₁, C₂ and bypass capacitor C_E"
            ),
            "A_v = −g_m R_C ≈ −β R_C / r_π   (180° phase shift)"
        ),
        s(
            48, 6, t6, 10,
            "Explain the operation of a transistor as a switch using the regions of the output characteristics. For V_CC = 5 V, R_C = 1 kΩ, β = 100, find R_B to saturate the transistor from a 5 V input (use an overdrive factor of 2).",
            "Output characteristics (I_C vs V_CE) with DC load line from cut-off (V_CE = V_CC, I_C = 0 ⇒ OFF) to saturation (I_C(sat) = (V_CC − V_CE(sat))/R_C, V_CE ≈ 0.2 V ⇒ ON), with active region in between. Numerical: I_C(sat) = (5 − 0.2)/1 kΩ = 4.8 mA. Minimum I_B = 4.8 mA / 100 = 48 µA. With overdrive factor 2: I_B = 2 × 48 = 96 µA. R_B = (V_in − V_BE)/I_B = (5 − 0.7)/96 µA ≈ 44.8 kΩ (choose 43 kΩ standard). Applications: logic gates, relay/LED drivers, inverters, SMPS.",
            listOf(
                "4M: Output characteristic curve with load line marking cut-off, active & saturation points",
                "2M: Compute I_C(sat) = 4.8 mA and minimum I_B = 48 µA",
                "3M: Apply overdrive ×2 (I_B = 96 µA) and find R_B = 44.8 kΩ",
                "1M: Applications of transistor switching"
            ),
            "I_C(sat) = 4.8 mA  ⇒  I_B = 96 µA (×2)  ⇒  R_B = (5 − 0.7)/96 µA = 44.8 kΩ"
        )
    )
}
