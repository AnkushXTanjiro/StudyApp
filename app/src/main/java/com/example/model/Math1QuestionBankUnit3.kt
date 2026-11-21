package com.example.model

internal fun math1Unit3Questions(): List<TestQuestion> {
    val uId = "u_100102_3"
    val uTitle = "Unit 3.0 • Differential Calculus: Several Variables"

    val t1 = "3.1 Partial Differentiation & Euler's Theorems"
    val t2 = "3.2 Limit, Continuity & Differentiability of Functions of Several Variables"
    val t3 = "3.3 Taylor's Theorem, Maclaurin's Theorem & Related Problems"
    val t4 = "3.4 Maxima & Minima of Several Variables; Lagrange's Multiplier Method"

    fun q(
        num: Int,
        tNum: Int,
        tTitle: String,
        prompt: String,
        opts: List<String>,
        ans: Int,
        exp: String,
        formula: String = ""
    ) = Math1QuestionBank.mcq(3, uId, uTitle, num, tNum, tTitle, prompt, opts, ans, formula = formula, explanation = exp)

    fun s(
        num: Int,
        tNum: Int,
        tTitle: String,
        prompt: String,
        ans: String,
        scheme: List<String>,
        formula: String = ""
    ) = Math1QuestionBank.shortQ(3, uId, uTitle, num, tNum, tTitle, prompt, ans, scheme, formula = formula)

    fun l(
        num: Int,
        tNum: Int,
        tTitle: String,
        marks: Long,
        prompt: String,
        ans: String,
        scheme: List<String>,
        formula: String = ""
    ) = Math1QuestionBank.longQ(3, uId, uTitle, num, tNum, tTitle, marks, prompt, ans, scheme, formula = formula)

    return listOf(
        // ==================== TOPIC 3.1 ====================
        q(1, 1, t1, "Euler's theorem for a homogeneous function of degree n states",
            listOf("x fₓ + y fᵧ = n f", "fₓ + fᵧ = n f", "x fₓ − y fᵧ = n f", "fₓᵧ = n f"), 0,
            "Standard statement of Euler's theorem.",
            formula = "x ∂f/∂x + y ∂f/∂y = n f"),
        q(2, 1, t1, "The degree of the homogeneous function x³ + y³ is",
            listOf("1", "2", "3", "6"), 2,
            "Each term has total degree 3."),
        q(3, 1, t1, "∂²f/∂x∂y = ∂²f/∂y∂x provided",
            listOf("f is linear", "the mixed partials are continuous", "f is homogeneous", "x = y"), 1,
            "Schwarz/Clairaut theorem."),
        q(4, 1, t1, "If u = x² + y², then ∂u/∂x =",
            listOf("x", "2x", "2y", "2x + 2y"), 1,
            "Treat y as constant."),
        q(5, 1, t1, "For a homogeneous f of degree n: x²fₓₓ + 2xy fₓᵧ + y²fᵧᵧ =",
            listOf("n f", "n² f", "n(n−1) f", "n(n+1) f"), 2,
            "Second-order Euler theorem."),
        s(1, 1, t1, "If u = log[(x² + y²)/(x + y)], show that x uₓ + y uᵧ = 1.",
            "Put f = eᵘ = (x² + y²)/(x + y), which is homogeneous of degree 2 − 1 = 1.\n" +
                "By Euler's theorem: x fₓ + y fᵧ = 1·f.\n" +
                "Since fₓ = eᵘ uₓ and fᵧ = eᵘ uᵧ:\n" +
                "eᵘ(x uₓ + y uᵧ) = eᵘ ⇒ x uₓ + y uᵧ = 1 ∎.",
            listOf(
                "2M: Transform to f = eᵘ = (x² + y²)/(x + y) and identify degree n = 1",
                "3M: Apply Euler's theorem and divide by eᵘ to obtain x uₓ + y uᵧ = 1"
            ),
            formula = "x uₓ + y uᵧ = 1"),
        s(2, 1, t1, "If u = tan⁻¹[(x³ + y³)/(x − y)], show that x uₓ + y uᵧ = sin 2u.",
            "Let f = tan u = (x³ + y³)/(x − y), homogeneous of degree 3 − 1 = 2.\n" +
                "By Euler's theorem: x fₓ + y fᵧ = 2f ⇒ sec²u (x uₓ + y uᵧ) = 2 tan u.\n" +
                "Therefore x uₓ + y uᵧ = 2 tan u cos²u = 2 sin u cos u = sin 2u ∎.",
            listOf(
                "2M: Set f = tan u and determine degree n = 2",
                "3M: Apply Euler's theorem and simplify 2 tan u cos²u = sin 2u"
            ),
            formula = "x uₓ + y uᵧ = 2 tan u cos²u = sin 2u"),
        l(1, 1, t1, 10L, "(a) State and prove Euler's theorem on homogeneous functions of two variables. (b) If u = sin⁻¹[(x + y)/(√x + √y)], prove x uₓ + y uᵧ = ½ tan u.",
            "(a) Statement (1 mark): If f(x, y) is homogeneous of degree n, then x fₓ + y fᵧ = n f.\n" +
                "Proof (4 marks): Homogeneity gives f(tx, ty) = tⁿ f(x, y) for all t. Differentiate both sides w.r.t. t by chain rule:\n" +
                "x f₁(tx, ty) + y f₂(tx, ty) = n tⁿ⁻¹ f(x, y).\n" +
                "Put t = 1: x fₓ(x, y) + y fᵧ(x, y) = n f(x, y). ∎\n" +
                "(b) (5 marks): Let f = sin u = (x + y)/(√x + √y). Numerator has degree 1, denominator degree ½ ⇒ f is homogeneous of degree 1 − ½ = ½.\n" +
                "By Euler's theorem: x fₓ + y fᵧ = ½ f ⇒ cos u (x uₓ + y uᵧ) = ½ sin u.\n" +
                "Hence x uₓ + y uᵧ = ½ tan u ∎.",
            listOf(
                "1M: Statement of Euler's theorem",
                "4M: Chain-rule differentiation of f(tx, ty) = tⁿ f(x, y) and putting t = 1",
                "5M: Part (b) identifying f = sin u of degree ½ and deriving x uₓ + y uᵧ = ½ tan u"
            ),
            formula = "x ∂u/∂x + y ∂u/∂y = ½ tan u"),

        // ==================== TOPIC 3.2 ====================
        q(6, 2, t2, "lim((x,y)→(0,0)) xy/(x² + y²)",
            listOf("= 0", "= 1", "= 1/2", "does not exist"), 3,
            "Along y = mx the value m/(1 + m²) depends on m."),
        q(7, 2, t2, "f is continuous at (a, b) if",
            listOf("fₓ exists", "lim f(x,y) = f(a,b)", "fᵧ exists", "f(a,b) = 0"), 1,
            "Definition of continuity at (a, b)."),
        q(8, 2, t2, "Existence of both partial derivatives at a point guarantees continuity",
            listOf("always", "never", "not necessarily", "only for polynomials"), 2,
            "A counter-example is xy/(x² + y²)."),
        q(9, 2, t2, "lim((x,y)→(0,0)) x²y/(x² + y²) =",
            listOf("1", "0", "1/2", "∞"), 1,
            "|x²y/(x² + y²)| ≤ |y| → 0."),
        q(10, 2, t2, "If fₓ, fᵧ exist and are continuous near a point, then f is",
            listOf("discontinuous", "differentiable", "unbounded", "constant"), 1,
            "Sufficient condition for differentiability."),
        s(3, 2, t2, "Show that lim((x,y)→(0,0)) xy/(x² + y²) does not exist.",
            "Approach (0, 0) along the line y = mx:\n" +
                "xy/(x² + y²) = mx²/(x²(1 + m²)) = m/(1 + m²).\n" +
                "The value depends on m (m = 0 gives 0, m = 1 gives ½). Different paths give different limits ⇒ the limit does not exist.",
            listOf(
                "2.5M: Substitute y = mx to get m/(1 + m²)",
                "2.5M: Show path dependence (e.g., m = 0 vs m = 1) and conclude limit does not exist"
            ),
            formula = "lim(y=mx) xy/(x² + y²) = m/(1 + m²)"),
        s(4, 2, t2, "Discuss continuity at the origin of f(x, y) = x²y/(x² + y²) for (x, y) ≠ (0, 0), f(0, 0) = 0.",
            "For (x, y) ≠ (0, 0): |f(x, y)| = x²|y|/(x² + y²) ≤ |y| (since 0 ≤ x² ≤ x² + y²).\n" +
                "As (x, y) → (0, 0), |y| → 0 ⇒ f(x, y) → 0 = f(0, 0).\n" +
                "Hence f is continuous at the origin.",
            listOf(
                "3M: Establish bound |f(x, y)| ≤ |y| (or polar r cos²θ sin θ → 0)",
                "2M: Conclude lim f(x, y) = 0 = f(0, 0), hence continuous at (0, 0)"
            )),
        l(2, 2, t2, 10L, "Let f(x, y) = xy(x² − y²)/(x² + y²) for (x, y) ≠ (0, 0) and f(0, 0) = 0. Show that fₓᵧ(0, 0) ≠ fᵧₓ(0, 0).",
            "Step 1 – fₓ(0, y) for y ≠ 0:\n" +
                "fₓ(0, y) = lim(h→0) [f(h, y) − f(0, y)]/h = lim(h→0) y(h² − y²)/(h² + y²) = −y.\n" +
                "Step 2 – fₓᵧ(0, 0) = d/dy[fₓ(0, y)] at y = 0 = d/dy(−y) = −1.\n" +
                "Step 3 – fᵧ(x, 0) for x ≠ 0:\n" +
                "fᵧ(x, 0) = lim(k→0) [f(x, k) − f(x, 0)]/k = lim(k→0) x(x² − k²)/(x² + k²) = x.\n" +
                "Step 4 – fᵧₓ(0, 0) = d/dx[fᵧ(x, 0)] at x = 0 = d/dx(x) = +1.\n" +
                "Thus fₓᵧ(0, 0) = −1 ≠ 1 = fᵧₓ(0, 0). The mixed partials are unequal because fₓᵧ and fᵧₓ are not continuous at the origin (Schwarz's theorem hypothesis fails).",
            listOf(
                "2.5M: Compute fₓ(0, y) = −y using first principles",
                "2M: Evaluate fₓᵧ(0, 0) = −1",
                "2.5M: Compute fᵧ(x, 0) = x using first principles",
                "2M: Evaluate fᵧₓ(0, 0) = +1",
                "1M: Conclude fₓᵧ(0, 0) ≠ fᵧₓ(0, 0) and note Schwarz's condition"
            ),
            formula = "fₓᵧ(0, 0) = −1 ≠ +1 = fᵧₓ(0, 0)"),

        // ==================== TOPIC 3.3 ====================
        q(11, 3, t3, "Taylor's series of f(x) about x = a is Σ",
            listOf("f⁽ⁿ⁾(0) xⁿ / n!", "f⁽ⁿ⁾(a)(x − a)ⁿ / n!", "f⁽ⁿ⁾(a) xⁿ", "f(a)(x − a)ⁿ"), 1,
            "Standard definition of Taylor's series."),
        q(12, 3, t3, "Maclaurin's series is Taylor's series about",
            listOf("x = 1", "x = a", "x = 0", "x = π"), 2,
            "Expansion centre a = 0."),
        q(13, 3, t3, "The Maclaurin series of eˣ is",
            listOf("1 + x + x²/2! + x³/3! + …", "1 − x + x²/2! − …", "x + x³/3! + …", "1 + x² + x⁴ + …"), 0,
            "All derivatives of eˣ equal 1 at x = 0."),
        q(14, 3, t3, "The Maclaurin series of sin x begins",
            listOf("1 − x²/2! + …", "x − x³/3! + …", "x + x³/3! + …", "1 + x + …"), 1,
            "Odd function: x − x³/3! + x⁵/5! − …"),
        q(15, 3, t3, "In Taylor's expansion of f(a + h, b + k), the second-order term is",
            listOf("hk fₓᵧ", "(1/2!)(h²fₓₓ + 2hk fₓᵧ + k²fᵧᵧ)", "h²fₓₓ + k²fᵧᵧ", "fₓₓ + fᵧᵧ"), 1,
            "Standard two-variable Taylor form.",
            formula = "(1/2!)(h²fₓₓ + 2hk fₓᵧ + k²fᵧᵧ)"),
        s(5, 3, t3, "Expand log(1 + x) in powers of x up to the term in x⁴ (Maclaurin).",
            "f(x) = ln(1 + x): f(0) = 0;\n" +
                "f′(x) = 1/(1 + x) ⇒ f′(0) = 1;\n" +
                "f″(x) = −1/(1 + x)² ⇒ f″(0) = −1;\n" +
                "f‴(x) = 2/(1 + x)³ ⇒ f‴(0) = 2;\n" +
                "f⁽⁴⁾(x) = −6/(1 + x)⁴ ⇒ f⁽⁴⁾(0) = −6.\n" +
                "ln(1 + x) = x − x²/2 + x³/3 − x⁴/4 + … (−1 < x ≤ 1).",
            listOf(
                "3M: Compute derivatives up to 4th order at x = 0",
                "2M: Substitute in Maclaurin series to get x − x²/2 + x³/3 − x⁴/4"
            ),
            formula = "ln(1 + x) = x − x²/2 + x³/3 − x⁴/4 + …"),
        s(6, 3, t3, "Expand f(x, y) = eˣ cos y about (0, 0) up to second-degree terms.",
            "f(0,0) = 1; fₓ = eˣ cos y → 1; fᵧ = −eˣ sin y → 0.\n" +
                "fₓₓ = eˣ cos y → 1; fₓᵧ = −eˣ sin y → 0; fᵧᵧ = −eˣ cos y → −1.\n" +
                "eˣ cos y ≈ 1 + x + ½(x² − y²) + …",
            listOf(
                "3M: Evaluate f, fₓ, fᵧ, fₓₓ, fₓᵧ, fᵧᵧ at (0, 0)",
                "2M: Assemble Taylor expansion 1 + x + ½(x² − y²)"
            ),
            formula = "eˣ cos y ≈ 1 + x + ½(x² − y²)"),
        l(3, 3, t3, 10L, "Expand f(x, y) = x²y + 3y − 2 in powers of (x − 1) and (y + 2) using Taylor's theorem.",
            "Step 1 – Point of expansion (a, b) = (1, −2). Let h = x − 1, k = y + 2.\n" +
                "Step 2 – Value and partial derivatives at (1, −2):\n" +
                "• f = x²y + 3y − 2 → (1)(−2) − 6 − 2 = −10.\n" +
                "• fₓ = 2xy = −4; fᵧ = x² + 3 = 4.\n" +
                "• fₓₓ = 2y = −4; fₓᵧ = 2x = 2; fᵧᵧ = 0.\n" +
                "• fₓₓₓ = 0; fₓₓᵧ = 2; fₓᵧᵧ = 0; fᵧᵧᵧ = 0. (All 4th-order derivatives are zero.)\n" +
                "Step 3 – Taylor's formula:\n" +
                "f(x, y) = f(a, b) + (h fₓ + k fᵧ) + (1/2!)(h² fₓₓ + 2hk fₓᵧ + k² fᵧᵧ) + (1/3!)(3h²k fₓₓᵧ)\n" +
                "= −10 + (−4h + 4k) + ½(−4h² + 4hk) + h²k\n" +
                "x²y + 3y − 2 = −10 − 4(x − 1) + 4(y + 2) − 2(x − 1)² + 2(x − 1)(y + 2) + (x − 1)²(y + 2).\n" +
                "Check at (2, −1): LHS = −4 − 3 − 2 = −9; RHS (h = k = 1) = −10 − 4 + 4 − 2 + 2 + 1 = −9 ✓.",
            listOf(
                "1M: Identify (a, b) = (1, −2) and h = x − 1, k = y + 2",
                "4.5M: Evaluate f and all partial derivatives up to 3rd order at (1, −2)",
                "3.5M: Substitute into two-variable Taylor formula and simplify",
                "1M: Final polynomial expansion (with optional verification)"
            ),
            formula = "−10 − 4(x−1) + 4(y+2) − 2(x−1)² + 2(x−1)(y+2) + (x−1)²(y+2)"),

        // ==================== TOPIC 3.4 ====================
        q(16, 4, t4, "A necessary condition for an extremum of f(x, y) is",
            listOf("f = 0", "fₓ = 0 and fᵧ = 0", "fₓₓ = 0", "fₓᵧ = 0"), 1,
            "Stationary point condition."),
        q(17, 4, t4, "At a stationary point, f has a minimum if",
            listOf("rt − s² > 0 and r > 0", "rt − s² > 0 and r < 0", "rt − s² < 0", "rt − s² = 0"), 0,
            "Second-derivative test for local minimum."),
        q(18, 4, t4, "If rt − s² < 0 at a stationary point, it is a",
            listOf("maximum", "minimum", "saddle point", "point of inflexion only"), 2,
            "Neither max nor min (saddle point)."),
        q(19, 4, t4, "The stationary point of f = x² + y² is",
            listOf("(1, 1)", "(0, 0)", "(1, 0)", "none"), 1,
            "fₓ = 2x = 0, fᵧ = 2y = 0 ⇒ (0, 0)."),
        q(20, 4, t4, "In Lagrange's method, to optimize f subject to φ = 0 we form",
            listOf("F = f·φ", "F = f + λφ", "F = f/φ", "F = f − φ²"), 1,
            "λ is the Lagrange multiplier."),
        s(7, 4, t4, "Find the extreme values of f(x, y) = x³ + y³ − 3axy (a > 0).",
            "fₓ = 3x² − 3ay = 0, fᵧ = 3y² − 3ax = 0 ⇒ y = x²/a and x⁴/a² = ax ⇒ x(x³ − a³) = 0 ⇒ x = 0 or x = a.\n" +
                "Stationary points: (0, 0) and (a, a).\n" +
                "r = fₓₓ = 6x, s = fₓᵧ = −3a, t = fᵧᵧ = 6y.\n" +
                "• At (0, 0): rt − s² = −9a² < 0 ⇒ saddle point (no extremum).\n" +
                "• At (a, a): rt − s² = 36a² − 9a² = 27a² > 0 and r = 6a > 0 ⇒ minimum, value f(a, a) = −a³.",
            listOf(
                "2M: Solve fₓ = 0, fᵧ = 0 to find stationary points (0, 0) and (a, a)",
                "1.5M: Compute r = 6x, s = −3a, t = 6y",
                "1.5M: Classify (0, 0) as saddle point and (a, a) as minimum with value −a³"
            ),
            formula = "f_min(a, a) = −a³"),
        s(8, 4, t4, "Find the minimum value of x² + y² + z² subject to x + y + z = 3 using Lagrange's multipliers.",
            "F = x² + y² + z² + λ(x + y + z − 3).\n" +
                "Fₓ = 2x + λ = 0, Fᵧ = 2y + λ = 0, F_z = 2z + λ = 0 ⇒ x = y = z = −λ/2.\n" +
                "Constraint: 3x = 3 ⇒ x = y = z = 1.\n" +
                "Minimum value = 1² + 1² + 1² = 3 at (1, 1, 1).",
            listOf(
                "1.5M: Form Lagrangian F = x² + y² + z² + λ(x + y + z − 3)",
                "2M: Set Fₓ = Fᵧ = F_z = 0 ⇒ x = y = z",
                "1.5M: Apply constraint to get (1, 1, 1) and minimum value = 3"
            )),
        l(4, 4, t4, 12L, "Using Lagrange's multiplier method, find the dimensions of a rectangular box (closed) of maximum volume whose total surface area is S.",
            "Step 1 – Set-up: Maximize V = xyz subject to φ = xy + yz + zx − S/2 = 0 (since surface area 2(xy + yz + zx) = S).\n" +
                "Step 2 – Lagrange function: F = xyz + λ(xy + yz + zx − S/2).\n" +
                "Step 3 – Stationary conditions:\n" +
                "Fₓ = yz + λ(y + z) = 0, Fᵧ = xz + λ(x + z) = 0, F_z = xy + λ(x + y) = 0.\n" +
                "Step 4 – Eliminate λ: Multiply the three equations by x, y, z respectively:\n" +
                "xyz + λx(y + z) = 0, xyz + λy(x + z) = 0, xyz + λz(x + y) = 0.\n" +
                "Subtract first two: λ(xz − yz) = λz(x − y) = 0 ⇒ x = y (since λ, z ≠ 0). Similarly from second & third, y = z. Hence x = y = z.\n" +
                "Step 5 – Use the constraint: 6x² = S ⇒ x = √(S/6).\n" +
                "Result: The box of maximum volume is a cube of side √(S/6), with V_max = (S/6)³/².",
            listOf(
                "2M: Formulate V = xyz and constraint φ = xy + yz + zx − S/2 = 0",
                "2M: Construct F = xyz + λφ and write Fₓ = Fᵧ = F_z = 0",
                "4M: Eliminate λ systematically to prove x = y = z",
                "2.5M: Substitute into 6x² = S ⇒ x = y = z = √(S/6)",
                "1.5M: State cube conclusion and V_max = (S/6)³/²"
            ),
            formula = "x = y = z = √(S/6),   V_max = (S/6)³/²")
    )
}
