package com.example.model

internal fun math1Unit4Questions(): List<TestQuestion> {
    val uId = "u_100102_4"
    val uTitle = "Unit 4.0 • Integral Calculus"

    val t1 = "4.1 Differentiation under the Integral Sign; Leibnitz's Rule"
    val t2 = "4.2 Double Integrals, Change of Order & Polar Co-ordinates"
    val t3 = "4.3 Triple Integrals — Spherical & Cylindrical Polar Co-ordinates"
    val t4 = "4.4 Beta, Gamma & Elliptic Integrals; Relation between Beta and Gamma"

    fun q(
        num: Int,
        tNum: Int,
        tTitle: String,
        prompt: String,
        opts: List<String>,
        ans: Int,
        exp: String,
        formula: String = ""
    ) = Math1QuestionBank.mcq(4, uId, uTitle, num, tNum, tTitle, prompt, opts, ans, formula = formula, explanation = exp)

    fun s(
        num: Int,
        tNum: Int,
        tTitle: String,
        prompt: String,
        ans: String,
        scheme: List<String>,
        formula: String = "",
        diagram: String = "NONE"
    ) = Math1QuestionBank.shortQ(4, uId, uTitle, num, tNum, tTitle, prompt, ans, scheme, formula = formula, diagram = diagram)

    fun l(
        num: Int,
        tNum: Int,
        tTitle: String,
        marks: Long,
        prompt: String,
        ans: String,
        scheme: List<String>,
        formula: String = "",
        diagram: String = "NONE"
    ) = Math1QuestionBank.longQ(4, uId, uTitle, num, tNum, tTitle, marks, prompt, ans, scheme, formula = formula, diagram = diagram)

    return listOf(
        // ==================== TOPIC 4.1 ====================
        q(1, 1, t1, "For constant limits a, b: d/dα ∫ₐᵇ f(x, α) dx =",
            listOf("∫ f dx", "∫ (∂f/∂α) dx", "f(b, α) − f(a, α)", "0"), 1,
            "Differentiate under the integral sign.",
            formula = "d/dα ∫ₐᵇ f(x, α) dx = ∫ₐᵇ (∂f/∂α) dx"),
        q(2, 1, t1, "∫₀¹ xᵅ ln x dx = d/dα ∫₀¹ xᵅ dx =",
            listOf("1/(α + 1)", "−1/(α + 1)²", "ln(α + 1)", "α + 1"), 1,
            "d/dα [1/(α + 1)] = −1/(α + 1)²."),
        q(3, 1, t1, "∫₀^∞ e⁻ᵅˣ dx (α > 0) equals",
            listOf("α", "1/α", "α²", "eᵅ"), 1,
            "Standard exponential integral."),
        q(4, 1, t1, "In Leibnitz's rule with variable limits, extra terms come from",
            listOf("the integrand only", "the limits u(α), v(α)", "α only", "none"), 1,
            "Boundary terms f(v, α)·v′ − f(u, α)·u′."),
        q(5, 1, t1, "Leibnitz's rule requires f and ∂f/∂α to be",
            listOf("discontinuous", "continuous", "constant", "linear"), 1,
            "Continuity assumptions."),
        s(1, 1, t1, "Evaluate I(a) = ∫₀¹ (xᵃ − 1)/ln x dx, a ≥ 0.",
            "Differentiate w.r.t. a: I′(a) = ∫₀¹ (xᵃ ln x)/ln x dx = ∫₀¹ xᵃ dx = 1/(a + 1).\n" +
                "Integrate: I(a) = ln(a + 1) + C.\n" +
                "At a = 0 the integrand is 0 ⇒ I(0) = 0 ⇒ C = 0.\n" +
                "Therefore I(a) = ln(1 + a).",
            listOf(
                "2.5M: Differentiate under integral sign to obtain I′(a) = 1/(a + 1)",
                "2.5M: Integrate and determine C = 0 from I(0) = 0 ⇒ I(a) = ln(1 + a)"
            ),
            formula = "I(a) = ∫₀¹ (xᵃ − 1)/ln x dx = ln(1 + a)"),
        s(2, 1, t1, "Evaluate I(a) = ∫₀^∞ e⁻ˣ sin(ax)/x dx.",
            "Differentiate w.r.t. a: I′(a) = ∫₀^∞ e⁻ˣ cos(ax) dx = 1/(1 + a²).\n" +
                "Integrating: I(a) = tan⁻¹a + C.\n" +
                "Since I(0) = 0, C = 0.\n" +
                "Therefore I(a) = tan⁻¹a.",
            listOf(
                "2.5M: Differentiate w.r.t. a to get I′(a) = ∫₀^∞ e⁻ˣ cos(ax) dx = 1/(1 + a²)",
                "2.5M: Integrate and use I(0) = 0 ⇒ I(a) = tan⁻¹a"
            ),
            formula = "I(a) = ∫₀^∞ e⁻ˣ sin(ax)/x dx = tan⁻¹a"),
        l(1, 1, t1, 10L, "(a) Evaluate ∫₀^(π/2) ln(1 + a cos x)/cos x dx, |a| < 1. (b) Find d/dx ∫ₓ^(x²) (sin xt)/t dt.",
            "(a) (6 marks): Let I(a) = ∫₀^(π/2) ln(1 + a cos x)/cos x dx. Differentiate:\n" +
                "I′(a) = ∫₀^(π/2) dx/(1 + a cos x).\n" +
                "Put t = tan(x/2), cos x = (1 − t²)/(1 + t²), dx = 2 dt/(1 + t²):\n" +
                "I′(a) = ∫₀¹ 2 dt/[(1 + a) + (1 − a)t²] = (2/√(1 − a²)) tan⁻¹[√((1 − a)/(1 + a))].\n" +
                "Let a = cos β (0 < β < π/2): √((1 − a)/(1 + a)) = tan(β/2) ⇒ tan⁻¹(…) = β/2, and √(1 − a²) = sin β. So I′(a) = β/sin β = cos⁻¹a / √(1 − a²).\n" +
                "Integrate: I(a) = −½(cos⁻¹a)² + C. At a = 0, I(0) = 0 ⇒ C = ½(π/2)² = π²/8.\n" +
                "Thus I(a) = π²/8 − ½(cos⁻¹a)².\n" +
                "(b) (4 marks): Leibnitz rule with u = x, v = x², f(t, x) = sin(xt)/t:\n" +
                "d/dx = ∫ₓ^(x²) cos(xt) dt + [sin(x·x²)/x²]·2x − [sin(x·x)/x]·1\n" +
                "= [sin(xt)/x]ₓ^(x²) + 2 sin(x³)/x − sin(x²)/x = [sin x³ − sin x²]/x + 2 sin(x³)/x − sin(x²)/x\n" +
                "= (3 sin x³ − 2 sin x²)/x.",
            listOf(
                "3M: Part (a) differentiate to I′(a) and evaluate via t = tan(x/2) to cos⁻¹a/√(1 − a²)",
                "3M: Part (a) integrate and apply I(0) = 0 to get π²/8 − ½(cos⁻¹a)²",
                "4M: Part (b) apply variable-limit Leibnitz rule and simplify to (3 sin x³ − 2 sin x²)/x"
            ),
            formula = "I(a) = π²/8 − ½(cos⁻¹a)²"),

        // ==================== TOPIC 4.2 ====================
        q(6, 2, t2, "∫₀¹ ∫₀¹ xy dx dy =",
            listOf("1/2", "1/4", "1", "1/8"), 1,
            "(½)(½) = 1/4."),
        q(7, 2, t2, "Area element in polar co-ordinates is",
            listOf("dr dθ", "r dr dθ", "r² dr dθ", "dr/r"), 1,
            "Jacobian J = r."),
        q(8, 2, t2, "The area of a region R is ∬_R",
            listOf("x dx dy", "dx dy", "xy dx dy", "(x + y) dx dy"), 1,
            "Area corresponds to integrand 1."),
        q(9, 2, t2, "∫₋∞^∞ e⁻ˣ² dx =",
            listOf("1", "π", "√π", "π/2"), 2,
            "Gaussian integral equals √π.",
            formula = "∫₋∞^∞ e⁻ˣ² dx = √π"),
        q(10, 2, t2, "Changing the order of integration requires",
            listOf("new limits from the region", "no change", "changing integrand", "adding a constant"), 0,
            "Re-describe the same geometric region."),
        s(3, 2, t2, "Evaluate ∫₀¹ ∫₀ˣ (x + y) dy dx.",
            "Inner integral: ∫₀ˣ (x + y) dy = [xy + y²/2]₀ˣ = x² + x²/2 = 3x²/2.\n" +
                "Outer integral: ∫₀¹ (3x²/2) dx = [x³/2]₀¹ = 1/2.",
            listOf(
                "2.5M: Inner integration w.r.t. y giving 3x²/2",
                "2.5M: Outer integration w.r.t. x giving 1/2"
            )),
        s(4, 2, t2, "Change the order of integration and evaluate ∫₀¹ ∫ₓ¹ e^(y²) dy dx.",
            "Region: 0 ≤ x ≤ y ≤ 1 (triangle). Reversing order: y from 0 to 1, x from 0 to y.\n" +
                "∫₀¹ ∫₀ʸ e^(y²) dx dy = ∫₀¹ y e^(y²) dy = [e^(y²)/2]₀¹ = (e − 1)/2.",
            listOf(
                "2.5M: Identify triangular region 0 ≤ x ≤ y ≤ 1 and reverse limits to ∫₀¹ ∫₀ʸ",
                "2.5M: Evaluate ∫₀¹ y e^(y²) dy = (e − 1)/2"
            ),
            formula = "∫₀¹ ∫₀ʸ e^(y²) dx dy = (e − 1)/2",
            diagram = "INTEGRAL_AREA"),
        l(2, 2, t2, 12L, "(a) Evaluate ∫₀ᵃ ∫₀^√(a²−x²) y²√(x² + y²) dy dx by changing to polar co-ordinates. (b) Using polar co-ordinates, prove ∫₋∞^∞ e⁻ˣ² dx = √π.",
            "(a) (6 marks): Region 0 ≤ y ≤ √(a² − x²), 0 ≤ x ≤ a ⇒ first-quadrant quarter-disc x² + y² ≤ a²: 0 ≤ r ≤ a, 0 ≤ θ ≤ π/2.\n" +
                "Put x = r cos θ, y = r sin θ, dy dx = r dr dθ:\n" +
                "I = ∫₀^(π/2) ∫₀ᵃ (r² sin²θ)(r)(r) dr dθ = (∫₀ᵃ r⁴ dr)(∫₀^(π/2) sin²θ dθ)\n" +
                "= (a⁵/5)·(π/4) = πa⁵/20.\n" +
                "(b) (6 marks): Let I = ∫₀^∞ e⁻ˣ² dx. Then I² = ∫₀^∞ ∫₀^∞ e⁻⁽ˣ²⁺ʸ²⁾ dx dy over the first quadrant.\n" +
                "In polar: 0 ≤ r < ∞, 0 ≤ θ ≤ π/2:\n" +
                "I² = ∫₀^(π/2) ∫₀^∞ e⁻ʳ² r dr dθ = (π/2)·[−e⁻ʳ²/2]₀^∞ = (π/2)(1/2) = π/4.\n" +
                "So I = √π/2 and since e⁻ˣ² is even, ∫₋∞^∞ e⁻ˣ² dx = 2I = √π. ∎",
            listOf(
                "3M: Part (a) identify quarter-disc polar limits and transform integrand with Jacobian r",
                "3M: Part (a) separate integrals and evaluate πa⁵/20",
                "3M: Part (b) form I² over first quadrant and convert to polar coordinates",
                "3M: Part (b) evaluate I² = π/4 ⇒ ∫₋∞^∞ e⁻ˣ² dx = √π"
            ),
            formula = "(a) πa⁵/20   (b) ∫₋∞^∞ e⁻ˣ² dx = √π",
            diagram = "INTEGRAL_AREA"),

        // ==================== TOPIC 4.3 ====================
        q(11, 3, t3, "Volume element in spherical polar co-ordinates is",
            listOf("r dr dθ dφ", "r² sin θ dr dθ dφ", "r sin θ dr dθ dφ", "dr dθ dφ"), 1,
            "Jacobian is r² sin θ.",
            formula = "dV = r² sin θ dr dθ dφ"),
        q(12, 3, t3, "Volume element in cylindrical co-ordinates is",
            listOf("r dr dθ dz", "r² dr dθ dz", "dr dθ dz", "r sin θ dr dθ dz"), 0,
            "Jacobian is r."),
        q(13, 3, t3, "Volume of a sphere of radius a is",
            listOf("πa³", "4πa²", "4πa³/3", "2πa³/3"), 2,
            "Standard volume formula 4πa³/3."),
        q(14, 3, t3, "∫₀¹ ∫₀¹ ∫₀¹ xyz dx dy dz =",
            listOf("1", "1/2", "1/4", "1/8"), 3,
            "(½)³ = 1/8."),
        q(15, 3, t3, "Triple integral ∭_V dV gives the",
            listOf("area", "volume of V", "mass always", "surface area"), 1,
            "Integrand 1 yields volume of V."),
        s(5, 3, t3, "Evaluate ∫₀¹ ∫₀² ∫₀³ xyz dz dy dx.",
            "The limits are constants and the integrand separates:\n" +
                "(∫₀¹ x dx)(∫₀² y dy)(∫₀³ z dz) = (1/2)(2)(9/2) = 9/2.",
            listOf(
                "2M: Separate product of single integrals",
                "3M: Evaluate (1/2)(2)(9/2) = 9/2"
            )),
        s(6, 3, t3, "Find the volume of a sphere of radius a using spherical co-ordinates.",
            "In spherical polar coordinates: 0 ≤ r ≤ a, 0 ≤ θ ≤ π, 0 ≤ φ ≤ 2π, dV = r² sin θ dr dθ dφ.\n" +
                "V = ∫₀^(2π) ∫₀^π ∫₀ᵃ r² sin θ dr dθ dφ = (2π)(2)(a³/3) = 4πa³/3.",
            listOf(
                "2M: Correct spherical limits and volume element r² sin θ dr dθ dφ",
                "3M: Evaluate each integral to obtain 4πa³/3"
            ),
            formula = "V = ∫₀^(2π) ∫₀^π ∫₀ᵃ r² sin θ dr dθ dφ = 4πa³/3"),
        l(3, 3, t3, 12L, "(a) Evaluate ∭ (x² + y² + z²) dV over the sphere x² + y² + z² ≤ a². (b) Evaluate ∭ z dV over the region bounded by the cone z = √(x² + y²) and the plane z = h.",
            "(a) (5 marks): Spherical co-ordinates: x² + y² + z² = r²; 0 ≤ r ≤ a, 0 ≤ θ ≤ π, 0 ≤ φ ≤ 2π.\n" +
                "I = ∫₀^(2π) dφ ∫₀^π sin θ dθ ∫₀ᵃ r²·r² dr = (2π)(2)(a⁵/5) = 4πa⁵/5.\n" +
                "(b) (7 marks): Cylindrical co-ordinates (r, θ, z): the cone is z = r and the region is r ≤ z ≤ h. Equivalently, for each z ∈ [0, h], r runs from 0 to z and θ from 0 to 2π.\n" +
                "I = ∫₀ʰ ∫₀^(2π) ∫₀^z z · r dr dθ dz = ∫₀ʰ z·(2π)(z²/2) dz = π ∫₀ʰ z³ dz = πh⁴/4.",
            listOf(
                "5M: Part (a) spherical setup and evaluation ⇒ 4πa⁵/5",
                "3.5M: Part (b) cylindrical limits 0 ≤ z ≤ h, 0 ≤ r ≤ z, 0 ≤ θ ≤ 2π with dV = r dr dθ dz",
                "3.5M: Part (b) integrate step-by-step to obtain πh⁴/4"
            ),
            formula = "(a) 4πa⁵/5   (b) πh⁴/4"),

        // ==================== TOPIC 4.4 ====================
        q(16, 4, t4, "Γ(½) =",
            listOf("1", "√π", "π", "π/2"), 1,
            "Standard Gamma value Γ(½) = √π."),
        q(17, 4, t4, "Γ(5) =",
            listOf("5", "24", "120", "4"), 1,
            "Γ(n) = (n − 1)! = 4! = 24."),
        q(18, 4, t4, "B(m, n) =",
            listOf("Γ(m)Γ(n)/Γ(m + n)", "Γ(m + n)/Γ(m)Γ(n)", "Γ(m) + Γ(n)", "Γ(mn)"), 0,
            "Beta–Gamma relation.",
            formula = "B(m, n) = Γ(m)Γ(n) / Γ(m + n)"),
        q(19, 4, t4, "B(m, n) is",
            listOf("B(n, m)", "−B(n, m)", "1/B(n, m)", "B(m, n + 1)"), 0,
            "Beta function is symmetric."),
        q(20, 4, t4, "The complete elliptic integral K(0) =",
            listOf("0", "π/2", "π", "1"), 1,
            "Integrand becomes 1 over [0, π/2]."),
        q(21, 4, t4, "Γ(n + 1) =",
            listOf("Γ(n)", "n Γ(n)", "Γ(n)/n", "n + Γ(n)"), 1,
            "Gamma reduction formula."),
        s(7, 4, t4, "Evaluate ∫₀^(π/2) sin⁴θ cos²θ dθ using Beta–Gamma functions.",
            "Using ∫₀^(π/2) sinᵖθ cos^qθ dθ = ½ B((p+1)/2, (q+1)/2):\n" +
                "I = ½ B(5/2, 3/2) = ½ Γ(5/2)Γ(3/2)/Γ(4).\n" +
                "Γ(3/2) = ½√π; Γ(5/2) = (3/2)(½)√π = 3√π/4; Γ(4) = 3! = 6.\n" +
                "I = ½ · (3√π/4)(√π/2)/6 = ½ · (3π/8)/6 = π/32.",
            listOf(
                "2M: Convert trigonometric integral to ½ B(5/2, 3/2) = ½ Γ(5/2)Γ(3/2)/Γ(4)",
                "3M: Evaluate Gamma factors and simplify to π/32"
            ),
            formula = "∫₀^(π/2) sin⁴θ cos²θ dθ = π/32"),
        s(8, 4, t4, "Prove that Γ(½) = √π.",
            "B(½, ½) = Γ(½)Γ(½)/Γ(1) = [Γ(½)]².\n" +
                "Also B(½, ½) = 2 ∫₀^(π/2) sin⁰θ cos⁰θ dθ = 2·(π/2) = π.\n" +
                "Hence [Γ(½)]² = π ⇒ Γ(½) = √π (since Γ(½) > 0). ∎",
            listOf(
                "2.5M: Express B(½, ½) = [Γ(½)]²",
                "2.5M: Evaluate B(½, ½) = π via trigonometric form and conclude Γ(½) = √π"
            )),
        s(9, 4, t4, "Define the complete elliptic integrals of the first and second kind.",
            "First kind: K(k) = ∫₀^(π/2) dθ / √(1 − k² sin²θ), 0 < k < 1 (k = modulus).\n" +
                "Second kind: E(k) = ∫₀^(π/2) √(1 − k² sin²θ) dθ.\n" +
                "Special values: K(0) = E(0) = π/2. These integrals cannot be written in terms of elementary functions; they arise in the arc length of an ellipse and the exact period of a simple pendulum.",
            listOf(
                "2M: Correct integral definition of K(k) with modulus 0 < k < 1",
                "2M: Correct integral definition of E(k)",
                "1M: Special values K(0) = E(0) = π/2 and physical/geometric applications"
            ),
            formula = "K(k) = ∫₀^(π/2) dθ/√(1−k²sin²θ),  E(k) = ∫₀^(π/2) √(1−k²sin²θ) dθ"),
        l(4, 4, t4, 10L, "(a) Prove that B(m, n) = Γ(m)Γ(n)/Γ(m + n). (b) Evaluate ∫₀¹ x⁴(1 − x)³ dx.",
            "(a) Proof (7 marks): Using x² as variable, Γ(m) = 2 ∫₀^∞ x^(2m−1) e⁻ˣ² dx and Γ(n) = 2 ∫₀^∞ y^(2n−1) e⁻ʸ² dy.\n" +
                "Hence Γ(m)Γ(n) = 4 ∫₀^∞ ∫₀^∞ x^(2m−1) y^(2n−1) e⁻⁽ˣ²⁺ʸ²⁾ dx dy.\n" +
                "Switch to polar co-ordinates x = r cos θ, y = r sin θ, dx dy = r dr dθ (0 ≤ r < ∞, 0 ≤ θ ≤ π/2):\n" +
                "Γ(m)Γ(n) = [2 ∫₀^∞ r^(2(m+n)−1) e⁻ʳ² dr] · [2 ∫₀^(π/2) cos^(2m−1)θ sin^(2n−1)θ dθ].\n" +
                "The first bracket equals Γ(m + n); the second equals B(m, n) (via x = sin²θ in definition of B).\n" +
                "So Γ(m)Γ(n) = Γ(m + n)·B(m, n) ⇒ B(m, n) = Γ(m)Γ(n)/Γ(m + n) ∎.\n" +
                "(b) (3 marks): ∫₀¹ x⁴(1 − x)³ dx = B(5, 4) = Γ(5)Γ(4)/Γ(9) = (4! × 3!)/8! = (24 × 6)/40320 = 1/280.",
            listOf(
                "2.5M: Express Γ(m)Γ(n) as a double integral in x and y",
                "3M: Convert to polar coordinates and factor into r and θ integrals",
                "1.5M: Identify Γ(m + n) and B(m, n) to prove B(m, n) = Γ(m)Γ(n)/Γ(m + n)",
                "3M: Part (b) recognize B(5, 4) and compute 1/280"
            ),
            formula = "B(m, n) = Γ(m)Γ(n)/Γ(m + n);  B(5, 4) = 1/280")
    )
}
