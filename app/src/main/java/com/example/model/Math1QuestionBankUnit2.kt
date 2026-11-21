package com.example.model

internal fun math1Unit2Questions(): List<TestQuestion> {
    val uId = "u_100102_2"
    val uTitle = "Unit 2.0 • Differential Calculus: Single Variable"

    val t1 = "2.1 Higher Order Derivatives, Successive Differentiation & Leibnitz's Theorem"
    val t2 = "2.2 Mean Value Theorems — Rolle's, Lagrange's & Cauchy's"
    val t3 = "2.3 Indeterminate Forms & L'Hôpital's Rule"
    val t4 = "2.4 Tangents & Normals of Algebraic and Polar Curves, Curvature"

    fun q(
        num: Int,
        tNum: Int,
        tTitle: String,
        prompt: String,
        opts: List<String>,
        ans: Int,
        exp: String,
        formula: String = ""
    ) = Math1QuestionBank.mcq(2, uId, uTitle, num, tNum, tTitle, prompt, opts, ans, formula = formula, explanation = exp)

    fun s(
        num: Int,
        tNum: Int,
        tTitle: String,
        prompt: String,
        ans: String,
        scheme: List<String>,
        formula: String = ""
    ) = Math1QuestionBank.shortQ(2, uId, uTitle, num, tNum, tTitle, prompt, ans, scheme, formula = formula)

    fun l(
        num: Int,
        tNum: Int,
        tTitle: String,
        marks: Long,
        prompt: String,
        ans: String,
        scheme: List<String>,
        formula: String = ""
    ) = Math1QuestionBank.longQ(2, uId, uTitle, num, tNum, tTitle, marks, prompt, ans, scheme, formula = formula)

    return listOf(
        // ==================== TOPIC 2.1 ====================
        q(1, 1, t1, "The n-th derivative of eᵃˣ is",
            listOf("a eᵃˣ", "aⁿ eᵃˣ", "n a eᵃˣ", "eᵃˣ / aⁿ"), 1,
            "Each differentiation multiplies by a.",
            formula = "dⁿ/dxⁿ (eᵃˣ) = aⁿ eᵃˣ"),
        q(2, 1, t1, "The n-th derivative of sin x is",
            listOf("sin(x + nπ)", "sin(x + nπ/2)", "cos(x + nπ/2)", "n sin x"), 1,
            "Each derivative shifts the phase by π/2."),
        q(3, 1, t1, "The n-th derivative of 1/(x + a) is",
            listOf("(−1)ⁿ n!/(x + a)ⁿ⁺¹", "n!/(x + a)ⁿ", "(−1)ⁿ/(x + a)ⁿ", "n/(x + a)ⁿ⁺¹"), 0,
            "Differentiate repeatedly: −1/(x+a)², 2/(x+a)³ …"),
        q(4, 1, t1, "The n-th derivative of xⁿ is",
            listOf("n", "n!", "x", "0"), 1,
            "dⁿ/dxⁿ(xⁿ) = n!."),
        q(5, 1, t1, "The (n + 1)-th derivative of a polynomial of degree n is",
            listOf("n!", "1", "0", "n + 1"), 2,
            "Degree drops by 1 each time."),
        s(1, 1, t1, "Find the n-th derivative of y = 1/(x² − a²).",
            "Partial fractions: 1/(x² − a²) = (1/2a)[1/(x − a) − 1/(x + a)].\n" +
                "Using (1/(x + c))ₙ = (−1)ⁿ n!/(x + c)ⁿ⁺¹:\n" +
                "yₙ = ((−1)ⁿ n! / 2a) [ 1/(x − a)ⁿ⁺¹ − 1/(x + a)ⁿ⁺¹ ].",
            listOf(
                "2M: Partial fraction decomposition (1/2a)[1/(x − a) − 1/(x + a)]",
                "3M: Apply standard n-th derivative formula to obtain yₙ"
            ),
            formula = "yₙ = ((−1)ⁿ n! / 2a)[1/(x−a)ⁿ⁺¹ − 1/(x+a)ⁿ⁺¹]"),
        s(2, 1, t1, "State Leibnitz's theorem and find the n-th derivative of x²eˣ.",
            "Leibnitz's theorem: If u, v are functions of x, then (uv)ₙ = uₙv + ⁿC₁ uₙ₋₁v₁ + ⁿC₂ uₙ₋₂v₂ + … + uvₙ.\n" +
                "Take u = eˣ, v = x²: uₖ = eˣ; v₁ = 2x, v₂ = 2, v₃ = 0.\n" +
                "(x²eˣ)ₙ = eˣx² + n·eˣ·2x + [n(n−1)/2]·eˣ·2 = eˣ[x² + 2nx + n(n − 1)].",
            listOf(
                "2M: Correct statement of Leibnitz's theorem",
                "3M: Choose u = eˣ, v = x² and expand to eˣ[x² + 2nx + n(n − 1)]"
            ),
            formula = "(uv)ₙ = Σ ⁿCᵣ uₙ₋ᵣ vᵣ"),
        l(1, 1, t1, 10L, "If y = sin(m sin⁻¹x), prove that (1 − x²)yₙ₊₂ − (2n + 1)x yₙ₊₁ + (m² − n²)yₙ = 0. Hence find yₙ(0).",
            "Step 1 – First relation: y = sin(m sin⁻¹x) ⇒ y₁ = m cos(m sin⁻¹x)/√(1 − x²), so (1 − x²)y₁² = m²cos²(m sin⁻¹x) = m²(1 − y²).\n" +
                "Step 2 – Differentiate: 2(1 − x²)y₁y₂ − 2xy₁² = −2m²yy₁. Divide by 2y₁:\n" +
                "(1 − x²)y₂ − xy₁ + m²y = 0 …(1).\n" +
                "Step 3 – Differentiate (1) n times by Leibnitz's theorem:\n" +
                "(1 − x²)yₙ₊₂ + n(−2x)yₙ₊₁ + [n(n−1)/2](−2)yₙ − [xyₙ₊₁ + nyₙ] + m²yₙ = 0.\n" +
                "Collect terms: coeff of yₙ₊₁ = −(2n + 1)x; coeff of yₙ = −n(n − 1) − n + m² = m² − n².\n" +
                "Hence (1 − x²)yₙ₊₂ − (2n + 1)xyₙ₊₁ + (m² − n²)yₙ = 0 ∎.\n" +
                "Step 4 – At x = 0: y(0) = 0, y₁(0) = m. Putting x = 0 gives yₙ₊₂(0) = (n² − m²)yₙ(0).\n" +
                "Thus y₂(0) = 0, y₃(0) = (1 − m²)m, y₄(0) = 0, y₅(0) = (9 − m²)(1 − m²)m, … (all even-order derivatives vanish at 0).",
            listOf(
                "2.5M: Derive (1 − x²)y₁² = m²(1 − y²) and (1 − x²)y₂ − xy₁ + m²y = 0",
                "4.5M: Apply Leibnitz's theorem n times and simplify coefficients to prove identity",
                "3M: Substitute x = 0 for recurrence yₙ₊₂(0) = (n² − m²)yₙ(0) and evaluate yₙ(0)"
            ),
            formula = "(1 − x²)yₙ₊₂ − (2n + 1)x yₙ₊₁ + (m² − n²)yₙ = 0"),

        // ==================== TOPIC 2.2 ====================
        q(6, 2, t2, "Which condition is NOT required in Rolle's theorem?",
            listOf("continuous on [a,b]", "differentiable on (a,b)", "f(a) = f(b)", "f(x) > 0"), 3,
            "Positivity is not needed."),
        q(7, 2, t2, "Lagrange's mean value theorem gives f′(c) =",
            listOf("0", "[f(b) − f(a)]/(b − a)", "f(b)·f(a)", "[f(b) + f(a)]/2"), 1,
            "Slope of the chord."),
        q(8, 2, t2, "Cauchy's mean value theorem uses",
            listOf("one function", "two functions f and g", "three functions", "no function"), 1,
            "f′(c)/g′(c) = Δf/Δg."),
        q(9, 2, t2, "For f(x) = x³ on [0, 2], the Lagrange value c is",
            listOf("1", "2/√3", "√2", "4/3"), 1,
            "3c² = (8 − 0)/2 = 4 ⇒ c = 2/√3."),
        q(10, 2, t2, "Rolle's theorem for f(x) = sin x on [0, π] gives c =",
            listOf("0", "π", "π/2", "π/4"), 2,
            "f′(c) = cos c = 0 ⇒ c = π/2."),
        s(3, 2, t2, "Verify Rolle's theorem for f(x) = sin x on [0, π].",
            "(i) sin x is continuous on [0, π];\n" +
                "(ii) sin x is differentiable on (0, π);\n" +
                "(iii) f(0) = sin 0 = 0 = sin π = f(π). All conditions hold.\n" +
                "f′(x) = cos x = 0 ⇒ x = π/2 ∈ (0, π). So c = π/2 exists. Verified.",
            listOf(
                "2.5M: Verify all three conditions of Rolle's theorem",
                "2.5M: Solve f′(c) = cos c = 0 and verify c = π/2 ∈ (0, π)"
            )),
        s(4, 2, t2, "Verify Lagrange's mean value theorem for f(x) = x(x − 1)(x − 2) on [0, ½].",
            "f(x) = x³ − 3x² + 2x is a polynomial ⇒ continuous on [0, ½] and differentiable on (0, ½).\n" +
                "f(0) = 0, f(½) = ½·(−½)·(−3/2) = 3/8. Chord slope = (3/8)/(½) = 3/4.\n" +
                "f′(c) = 3c² − 6c + 2 = 3/4 ⇒ 12c² − 24c + 5 = 0 ⇒ c = 1 ± √21/6.\n" +
                "Only c = 1 − √21/6 ≈ 0.236 lies in (0, ½). Verified.",
            listOf(
                "1.5M: Check continuity, differentiability, and chord slope = 3/4",
                "2.5M: Solve 12c² − 24c + 5 = 0 ⇒ c = 1 ± √21/6",
                "1M: Identify c = 1 − √21/6 ≈ 0.236 ∈ (0, ½)"
            ),
            formula = "3c² − 6c + 2 = 3/4 ⇒ c = 1 − √21/6"),
        s(5, 2, t2, "Verify Cauchy's mean value theorem for f(x) = x², g(x) = x³ on [1, 2].",
            "Both f and g are continuous on [1, 2] and differentiable on (1, 2); g′(x) = 3x² ≠ 0 on (1, 2).\n" +
                "[f(2) − f(1)]/[g(2) − g(1)] = (4 − 1)/(8 − 1) = 3/7.\n" +
                "f′(c)/g′(c) = 2c/(3c²) = 2/(3c).\n" +
                "Equating: 2/(3c) = 3/7 ⇒ c = 14/9 ≈ 1.56 ∈ (1, 2). Verified.",
            listOf(
                "1.5M: Verify conditions including g′(x) ≠ 0",
                "2.5M: Equate f′(c)/g′(c) = Δf/Δg ⇒ 2/(3c) = 3/7",
                "1M: Verify c = 14/9 ∈ (1, 2)"
            ),
            formula = "f′(c)/g′(c) = [f(2)−f(1)]/[g(2)−g(1)]"),
        l(2, 2, t2, 10L, "(a) State and prove Lagrange's mean value theorem using Rolle's theorem. (b) Using it show that (b − a)/(1 + b²) < tan⁻¹b − tan⁻¹a < (b − a)/(1 + a²) for 0 < a < b.",
            "(a) Statement (1 mark): If f is continuous on [a, b] and differentiable on (a, b), then ∃ c ∈ (a, b) with f′(c) = [f(b) − f(a)]/(b − a).\n" +
                "Proof (4 marks): Define φ(x) = f(x) − f(a) − [(f(b) − f(a))/(b − a)](x − a).\n" +
                "• φ is continuous on [a, b] and differentiable on (a, b).\n" +
                "• φ(a) = 0 and φ(b) = f(b) − f(a) − (f(b) − f(a)) = 0 ⇒ φ(a) = φ(b).\n" +
                "By Rolle's theorem ∃ c ∈ (a, b) with φ′(c) = 0, i.e., f′(c) − [f(b) − f(a)]/(b − a) = 0. ∎\n" +
                "(b) Application (5 marks): Let f(x) = tan⁻¹x, continuous and differentiable everywhere. On [a, b], ∃ c ∈ (a, b) such that:\n" +
                "(tan⁻¹b − tan⁻¹a)/(b − a) = 1/(1 + c²).\n" +
                "Since 0 < a < c < b, 1 + a² < 1 + c² < 1 + b² ⇒ 1/(1 + b²) < 1/(1 + c²) < 1/(1 + a²).\n" +
                "Multiplying by (b − a) > 0 gives (b − a)/(1 + b²) < tan⁻¹b − tan⁻¹a < (b − a)/(1 + a²). ∎",
            listOf(
                "1M: Statement of Lagrange's MVT",
                "4M: Auxiliary function φ(x), verification of Rolle's conditions, and derivation of f′(c)",
                "5M: Apply LMVT to f(x) = tan⁻¹x and bound 1/(1 + c²) to prove inequality"
            ),
            formula = "(b − a)/(1 + b²) < tan⁻¹b − tan⁻¹a < (b − a)/(1 + a²)"),

        // ==================== TOPIC 2.3 ====================
        q(11, 3, t3, "Which of the following is NOT an indeterminate form?",
            listOf("0/0", "∞ − ∞", "1^∞", "0/∞"), 3,
            "0/∞ = 0 (determinate)."),
        q(12, 3, t3, "lim(x→0) (sin x)/x =",
            listOf("0", "1", "∞", "does not exist"), 1,
            "Standard limit."),
        q(13, 3, t3, "lim(x→0) (1 − cos x)/x² =",
            listOf("0", "1", "1/2", "2"), 2,
            "Apply L'Hôpital twice: sin x/(2x) → 1/2."),
        q(14, 3, t3, "lim(x→∞) x/eˣ =",
            listOf("∞", "1", "0", "e"), 2,
            "Exponential dominates polynomial."),
        q(15, 3, t3, "For the form 1^∞, one first takes",
            listOf("square", "logarithm", "reciprocal", "derivative of base only"), 1,
            "Evaluate lim g·ln f then exponentiate."),
        s(6, 3, t3, "Evaluate lim(x→0) (eˣ − e⁻ˣ − 2x)/(x − sin x).",
            "Form 0/0. Apply L'Hôpital's rule repeatedly:\n" +
                "(eˣ + e⁻ˣ − 2)/(1 − cos x)  [0/0]\n" +
                "→ (eˣ − e⁻ˣ)/sin x  [0/0]\n" +
                "→ (eˣ + e⁻ˣ)/cos x.\n" +
                "At x = 0, the value is (1 + 1)/1 = 2.",
            listOf(
                "1M: Identify 0/0 form",
                "3M: Three successive L'Hôpital differentiations",
                "1M: Evaluate at x = 0 to get 2"
            ),
            formula = "lim(x→0) (eˣ − e⁻ˣ − 2x)/(x − sin x) = 2"),
        s(7, 3, t3, "Evaluate lim(x→0) (1/x − 1/sin x).",
            "Form ∞ − ∞ ⇒ combine into a single fraction: (sin x − x)/(x sin x), form 0/0.\n" +
                "Apply L'Hôpital:\n" +
                "(cos x − 1)/(sin x + x cos x)  [0/0]\n" +
                "→ (−sin x)/(2 cos x − x sin x) → 0/2 = 0.\n" +
                "Limit = 0.",
            listOf(
                "1.5M: Rewrite ∞ − ∞ as (sin x − x)/(x sin x) (0/0 form)",
                "2.5M: Apply L'Hôpital twice accurately",
                "1M: Conclude limit = 0"
            ),
            formula = "lim(x→0) (1/x − 1/sin x) = 0"),
        l(3, 3, t3, 10L, "Evaluate (a) lim(x→0) (1/x)^(tan x)  (b) lim(x→0) (cos x)^(1/x²)  (c) lim(x→0) (sin x / x)^(1/x²).",
            "(a) Form ∞⁰: Let L = (1/x)^(tan x). ln L = −tan x · ln x = −(ln x)/cot x (form ∞/∞).\n" +
                "L'Hôpital: −(1/x)/(−cosec²x) = sin²x/x → 0 ⇒ ln L → 0 ⇒ L → e⁰ = 1.\n" +
                "(b) Form 1^∞: ln L = ln(cos x)/x² (form 0/0).\n" +
                "L'Hôpital: (−tan x)/(2x) → −1/2 (since tan x/x → 1) ⇒ L = e^(−1/2).\n" +
                "(c) Form 1^∞: ln L = ln(sin x/x)/x² (form 0/0).\n" +
                "Differentiate: (cot x − 1/x)/(2x) = (x cos x − sin x)/(2x² sin x) (0/0).\n" +
                "L'Hôpital again: (−x sin x)/(4x sin x + 2x² cos x) = −sin x/(4 sin x + 2x cos x) → −1/(4 + 2) = −1/6.\n" +
                "So L = e^(−1/6).",
            listOf(
                "3M: Part (a) log transform + L'Hôpital ⇒ L = 1",
                "3M: Part (b) log transform + L'Hôpital ⇒ L = e^(−1/2)",
                "4M: Part (c) log transform + repeated L'Hôpital ⇒ L = e^(−1/6)"
            ),
            formula = "(a) 1   (b) e^(−1/2)   (c) e^(−1/6)"),

        // ==================== TOPIC 2.4 ====================
        q(16, 4, t4, "Slope of the tangent to y = x² at x = 1 is",
            listOf("1", "2", "0", "−1"), 1,
            "dy/dx = 2x = 2."),
        q(17, 4, t4, "Radius of curvature for y = f(x) is ρ =",
            listOf("(1 + y₁²)³/² / y₂", "y₂ / (1 + y₁²)³/²", "(1 + y₁) / y₂", "y₁ / y₂"), 0,
            "Standard Cartesian formula.",
            formula = "ρ = (1 + y₁²)³/² / y₂"),
        q(18, 4, t4, "Curvature of a circle of radius r is",
            listOf("r", "1/r", "r²", "0"), 1,
            "κ = 1/ρ."),
        q(19, 4, t4, "The curvature of a straight line is",
            listOf("1", "∞", "0", "undefined"), 2,
            "y₂ = 0 ⇒ κ = 0."),
        q(20, 4, t4, "In polar form, the angle ψ between the radius vector and tangent satisfies",
            listOf("tan ψ = dr/dθ", "tan ψ = r dθ/dr", "tan ψ = r", "tan ψ = dθ/dr"), 1,
            "Standard polar formula: tan ψ = r dθ/dr."),
        s(8, 4, t4, "Find the equations of the tangent and normal to y = x³ at (1, 1).",
            "dy/dx = 3x² = 3 at x = 1.\n" +
                "Tangent: y − 1 = 3(x − 1) ⇒ 3x − y − 2 = 0.\n" +
                "Normal: y − 1 = −(1/3)(x − 1) ⇒ x + 3y − 4 = 0.",
            listOf(
                "1M: Slope dy/dx = 3 at (1, 1)",
                "2M: Tangent equation 3x − y − 2 = 0",
                "2M: Normal equation x + 3y − 4 = 0"
            )),
        s(9, 4, t4, "Find the radius of curvature of y = eˣ at the point (0, 1).",
            "y₁ = eˣ = 1, y₂ = eˣ = 1 at x = 0.\n" +
                "ρ = (1 + y₁²)³/² / y₂ = (1 + 1)³/² / 1 = 2√2.",
            listOf(
                "2M: Evaluate y₁ = 1 and y₂ = 1 at (0, 1)",
                "3M: Substitute in ρ = (1 + y₁²)³/² / y₂ to get 2√2"
            ),
            formula = "ρ = (1 + 1)³/² / 1 = 2√2"),
        s(10, 4, t4, "For the cardioid r = a(1 + cos θ) show that ψ = π/2 + θ/2.",
            "dr/dθ = −a sin θ.\n" +
                "tan ψ = r/(dr/dθ) = a(1 + cos θ)/(−a sin θ)\n" +
                "= −2cos²(θ/2)/(2 sin(θ/2)cos(θ/2)) = −cot(θ/2) = tan(π/2 + θ/2).\n" +
                "Hence ψ = π/2 + θ/2.",
            listOf(
                "1.5M: Differentiate dr/dθ = −a sin θ and write tan ψ = r/(dr/dθ)",
                "2.5M: Apply half-angle identities to simplify to −cot(θ/2)",
                "1M: Express as tan(π/2 + θ/2) ⇒ ψ = π/2 + θ/2"
            ),
            formula = "tan ψ = r dθ/dr = −cot(θ/2) = tan(π/2 + θ/2)"),
        l(4, 4, t4, 10L, "Find the radius of curvature at any point t of the cycloid x = a(t − sin t), y = a(1 − cos t).",
            "Step 1 – Derivatives w.r.t. t:\n" +
                "x′ = a(1 − cos t), y′ = a sin t, x″ = a sin t, y″ = a cos t.\n" +
                "Step 2 – Parametric formula: ρ = (x′² + y′²)³/² / (x′y″ − y′x″).\n" +
                "Step 3 – Numerator: x′² + y′² = a²[(1 − cos t)² + sin²t] = a²(2 − 2 cos t) = 4a² sin²(t/2).\n" +
                "So (x′² + y′²)³/² = 8a³ sin³(t/2).\n" +
                "Step 4 – Denominator: x′y″ − y′x″ = a²(1 − cos t)cos t − a² sin²t = a²(cos t − 1) = −2a² sin²(t/2).\n" +
                "Step 5 – Result: ρ = 8a³ sin³(t/2) / (−2a² sin²(t/2)) = −4a sin(t/2).\n" +
                "|ρ| = 4a sin(t/2). (The negative sign indicates direction of centre of curvature; at cusp t = 0, ρ = 0; at top t = π, ρ = 4a.)",
            listOf(
                "2M: Compute x′, y′, x″, y″",
                "1.5M: State parametric curvature formula",
                "3M: Simplify numerator to 8a³ sin³(t/2)",
                "2M: Simplify denominator to −2a² sin²(t/2)",
                "1.5M: Final magnitude |ρ| = 4a sin(t/2)"
            ),
            formula = "ρ = (x′² + y′²)³/² / (x′y″ − y′x″) ⇒ |ρ| = 4a sin(t/2)")
    )
}
