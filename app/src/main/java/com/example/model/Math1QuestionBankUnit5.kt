package com.example.model

internal fun math1Unit5Questions(): List<TestQuestion> {
    val uId = "u_100102_5"
    val uTitle = "Unit 5.0 • Vector Calculus"

    val t1 = "5.1 Scalar & Vector Functions; Gradient, Divergence, Curl; Tangent Plane; Directional Derivative"
    val t2 = "5.2 Line, Surface & Volume Integrals — Green's, Stokes' and Gauss Divergence Theorems"

    fun q(
        num: Int,
        tNum: Int,
        tTitle: String,
        prompt: String,
        opts: List<String>,
        ans: Int,
        exp: String,
        formula: String = ""
    ) = Math1QuestionBank.mcq(5, uId, uTitle, num, tNum, tTitle, prompt, opts, ans, formula = formula, explanation = exp)

    fun s(
        num: Int,
        tNum: Int,
        tTitle: String,
        prompt: String,
        ans: String,
        scheme: List<String>,
        formula: String = "",
        diagram: String = "NONE"
    ) = Math1QuestionBank.shortQ(5, uId, uTitle, num, tNum, tTitle, prompt, ans, scheme, formula = formula, diagram = diagram)

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
    ) = Math1QuestionBank.longQ(5, uId, uTitle, num, tNum, tTitle, marks, prompt, ans, scheme, formula = formula, diagram = diagram)

    return listOf(
        // ==================== TOPIC 5.1 ====================
        q(1, 1, t1, "The gradient ∇φ points in the direction of",
            listOf("minimum increase of φ", "maximum rate of increase of φ", "zero change", "the x-axis"), 1,
            "Maximum directional derivative occurs along ∇φ."),
        q(2, 1, t1, "curl(grad φ) =",
            listOf("φ", "0", "∇²φ", "grad φ"), 1,
            "Always the zero vector."),
        q(3, 1, t1, "div(curl F) =",
            listOf("F", "0", "∇²F", "curl F"), 1,
            "Always zero."),
        q(4, 1, t1, "A vector field F with curl F = 0 is called",
            listOf("solenoidal", "irrotational", "harmonic only", "constant"), 1,
            "Irrotational (conservative) vector field."),
        q(5, 1, t1, "For r = xi + yj + zk, div r =",
            listOf("0", "1", "3", "r"), 2,
            "∂x/∂x + ∂y/∂y + ∂z/∂z = 1 + 1 + 1 = 3."),
        q(6, 1, t1, "If div F = 0, F is",
            listOf("irrotational", "solenoidal", "scalar", "conservative"), 1,
            "Definition of a solenoidal vector field."),
        s(1, 1, t1, "Find grad φ at (1, 1, 1) where φ = x²yz.",
            "∇φ = (∂φ/∂x)i + (∂φ/∂y)j + (∂φ/∂z)k = (2xyz)i + (x²z)j + (x²y)k.\n" +
                "At (1, 1, 1): ∇φ = 2i + j + k.",
            listOf(
                "3M: Compute partial derivatives ∇φ = (2xyz)i + (x²z)j + (x²y)k",
                "2M: Evaluate at (1, 1, 1) to get 2i + j + k"
            ),
            formula = "∇φ|(1,1,1) = 2i + j + k"),
        s(2, 1, t1, "Show that F = (y + z)i + (z + x)j + (x + y)k is irrotational and find its scalar potential.",
            "P = y + z, Q = z + x, R = x + y.\n" +
                "curl F = i(Rᵧ − Q_z) + j(P_z − Rₓ) + k(Qₓ − Pᵧ) = i(1 − 1) + j(1 − 1) + k(1 − 1) = 0. So F is irrotational.\n" +
                "F = ∇φ ⇒ φₓ = y + z ⇒ φ = xy + xz + g(y, z);\n" +
                "φᵧ = x + z ⇒ gᵧ = z ⇒ g = yz + c.\n" +
                "Scalar potential: φ = xy + yz + zx + c.",
            listOf(
                "2.5M: Compute curl F = 0 and conclude F is irrotational",
                "2.5M: Integrate F = ∇φ to obtain φ = xy + yz + zx + c"
            ),
            formula = "curl F = 0,   φ = xy + yz + zx + c"),
        l(1, 1, t1, 12L, "(a) Find the directional derivative of φ = xy² + yz³ at (1, −1, 1) in the direction of i + 2j + 2k. (b) Find the equation of the tangent plane and normal line to z = x² + y² at (1, 2, 5). (c) Prove curl(grad φ) = 0.",
            "(a) (4 marks): ∇φ = y²i + (2xy + z³)j + 3yz²k. At (1, −1, 1): ∇φ = i + (−2 + 1)j − 3k = i − j − 3k.\n" +
                "Unit vector â = (i + 2j + 2k)/√(1 + 4 + 4) = (i + 2j + 2k)/3.\n" +
                "D_â φ = ∇φ·â = (1 − 2 − 6)/3 = −7/3.\n" +
                "(b) (4 marks): Surface Φ = x² + y² − z = 0. ∇Φ = 2xi + 2yj − k = 2i + 4j − k at (1, 2, 5).\n" +
                "Tangent plane: 2(x − 1) + 4(y − 2) − (z − 5) = 0 ⇒ 2x + 4y − z = 5.\n" +
                "Normal line: (x − 1)/2 = (y − 2)/4 = (z − 5)/(−1).\n" +
                "(c) (4 marks): ∇φ = φₓi + φᵧj + φ_zk. Then\n" +
                "curl(∇φ) = i(φ_zy − φ_yz) + j(φ_xz − φ_zx) + k(φ_yx − φ_xy).\n" +
                "For φ with continuous second partials, mixed partials are equal, so every bracket vanishes ⇒ curl(grad φ) = 0. ∎",
            listOf(
                "4M: Part (a) gradient at (1,−1,1), unit vector â, and D_â φ = −7/3",
                "4M: Part (b) normal vector ∇Φ = 2i + 4j − k, tangent plane 2x + 4y − z = 5, normal line",
                "4M: Part (c) expand curl(∇φ) determinant and invoke equality of mixed partials"
            ),
            formula = "D_â φ = −7/3;  2x + 4y − z = 5;  curl(grad φ) = 0"),

        // ==================== TOPIC 5.2 ====================
        q(7, 2, t2, "Green's theorem: ∮(P dx + Q dy) =",
            listOf("∬(Qₓ − Pᵧ) dA", "∬(Pₓ + Qᵧ) dA", "∬(Pᵧ − Qₓ) dA", "∬ PQ dA"), 0,
            "Plane form of Stokes' theorem.",
            formula = "∮_C (P dx + Q dy) = ∬_R (∂Q/∂x − ∂P/∂y) dA"),
        q(8, 2, t2, "Stokes' theorem connects a line integral with a",
            listOf("volume integral", "surface integral of curl F", "double integral of F", "scalar integral"), 1,
            "∮ F·dr = ∬ (curl F)·n̂ dS."),
        q(9, 2, t2, "Gauss' divergence theorem: ∯ F·n̂ dS =",
            listOf("∭ div F dV", "∭ curl F dV", "∬ div F dS", "∭ F dV"), 0,
            "Converts closed surface integral to volume integral.",
            formula = "∯_S F·n̂ dS = ∭_V div F dV"),
        q(10, 2, t2, "Work done by a conservative force around a closed path is",
            listOf("positive", "negative", "zero", "infinite"), 2,
            "curl F = 0 ⇒ ∮ F·dr = 0."),
        q(11, 2, t2, "For a closed surface enclosing volume V, ∯ r·n̂ dS =",
            listOf("V", "2V", "3V", "0"), 2,
            "div r = 3 ⇒ ∭ 3 dV = 3V."),
        s(3, 2, t2, "Using Green's theorem, find the area of the ellipse x = a cos t, y = b sin t.",
            "By Green's theorem: Area = ½ ∮(x dy − y dx).\n" +
                "dx = −a sin t dt, dy = b cos t dt.\n" +
                "A = ½ ∫₀^(2π) (ab cos²t + ab sin²t) dt = ½ ab (2π) = πab.",
            listOf(
                "2M: State Green's area formula A = ½ ∮(x dy − y dx)",
                "3M: Substitute ellipse parametrization and integrate to get πab"
            ),
            formula = "A = ½ ∮(x dy − y dx) = πab",
            diagram = "INTEGRAL_AREA"),
        s(4, 2, t2, "Using Gauss' theorem, evaluate ∯ r·n̂ dS over a sphere of radius a.",
            "div r = ∂x/∂x + ∂y/∂y + ∂z/∂z = 3.\n" +
                "By Gauss' divergence theorem:\n" +
                "∯ r·n̂ dS = ∭ 3 dV = 3V = 3(4πa³/3) = 4πa³.",
            listOf(
                "2M: Compute div r = 3 and apply Gauss' theorem",
                "3M: Substitute sphere volume V = 4πa³/3 to obtain 4πa³"
            )),
        s(5, 2, t2, "Evaluate ∫_C (x² dx + xy dy) along y = x from (0, 0) to (1, 1).",
            "On C: y = x, dy = dx ⇒ x² dx + x·x dx = 2x² dx.\n" +
                "∫₀¹ 2x² dx = [2x³/3]₀¹ = 2/3.",
            listOf(
                "2.5M: Substitute y = x and dy = dx to get 2x² dx",
                "2.5M: Integrate from 0 to 1 to obtain 2/3"
            )),
        l(2, 2, t2, 14L, "Verify Stokes' theorem for F = (2x − y)i − yz²j − y²z k over the upper half of the sphere x² + y² + z² = 1 bounded by the circle C: x² + y² = 1, z = 0.",
            "Step 1 – Line integral ∮_C F·dr: On C: z = 0, x = cos t, y = sin t (0 ≤ t ≤ 2π); F = (2x − y)i; dr = dx i + dy j.\n" +
                "∮ F·dr = ∮ (2x − y) dx = ∫₀^(2π) (2 cos t − sin t)(−sin t) dt = ∫₀^(2π) (−2 sin t cos t + sin²t) dt = 0 + π = π.\n" +
                "Step 2 – curl F: P = 2x − y, Q = −yz², R = −y²z:\n" +
                "curl F = i(Rᵧ − Q_z) + j(P_z − Rₓ) + k(Qₓ − Pᵧ) = i(−2yz + 2yz) + j(0 − 0) + k(0 + 1) = k.\n" +
                "Step 3 – Surface integral ∬ (curl F)·n̂ dS: curl F·n̂ = k·n̂. Since n̂ dS projects onto the xy-plane as dx dy (k·n̂ dS = dx dy):\n" +
                "∬ k·n̂ dS = ∬_(x²+y²≤1) dx dy = π(1)² = π.\n" +
                "Step 4 – Conclusion: Line integral = π = surface integral. Stokes' theorem is verified.",
            listOf(
                "5M: Parametrize C and evaluate line integral ∮_C F·dr = π",
                "4M: Compute curl F = k accurately",
                "4M: Project surface integral ∬ (curl F)·n̂ dS onto unit disc to get π",
                "1M: Conclude LHS = RHS = π (Stokes' theorem verified)"
            ),
            formula = "∮_C F·dr = ∬_S (curl F)·n̂ dS = π",
            diagram = "INTEGRAL_AREA"),
        l(3, 2, t2, 10L, "Verify the divergence theorem for F = x²i + y²j + z²k over the cube 0 ≤ x, y, z ≤ 1.",
            "Volume integral: div F = 2x + 2y + 2z.\n" +
                "∭ div F dV = 2 ∫₀¹ ∫₀¹ ∫₀¹ (x + y + z) dx dy dz = 2(½ + ½ + ½) = 3.\n" +
                "Surface integral (6 faces, F·n̂ on each):\n" +
                "• x = 1 (n̂ = i): F·n̂ = x² = 1 ⇒ integral = 1;  x = 0 (n̂ = −i): −x² = 0 ⇒ 0.\n" +
                "• y = 1 (n̂ = j): y² = 1 ⇒ 1;  y = 0 (n̂ = −j): 0.\n" +
                "• z = 1 (n̂ = k): z² = 1 ⇒ 1;  z = 0 (n̂ = −k): 0.\n" +
                "Total ∯ F·n̂ dS = 1 + 0 + 1 + 0 + 1 + 0 = 3.\n" +
                "Both sides equal 3. Gauss divergence theorem is verified.",
            listOf(
                "4M: Compute div F = 2(x + y + z) and evaluate ∭ div F dV = 3",
                "5M: Evaluate flux ∬ F·n̂ dS across all 6 faces of the unit cube (= 3)",
                "1M: Conclude LHS = RHS = 3 (Gauss divergence theorem verified)"
            ),
            formula = "∯_S F·n̂ dS = ∭_V div F dV = 3")
    )
}
