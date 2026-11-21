package com.example.model

internal fun math1Unit1Questions(): List<TestQuestion> {
    val uId = "u_100102_1"
    val uTitle = "Unit 1.0 • Basic Linear Algebra"

    val t1 = "1.1 Elementary Transformations, Rank, Echelon & Normal Form"
    val t2 = "1.2 Linear Dependence & Independence of Vectors in Rⁿ"
    val t3 = "1.3 Consistency of a System of Linear Equations"
    val t4 = "1.4 Eigenvalues, Eigenvectors & Cayley–Hamilton Theorem"
    val t5 = "1.5 Similarity of Matrices & Diagonalization of Square Matrices"
    val t6 = "1.6 Quadratic Form & Canonical Form of Matrices"

    fun q(
        num: Int,
        tNum: Int,
        tTitle: String,
        prompt: String,
        opts: List<String>,
        ans: Int,
        exp: String,
        formula: String = ""
    ) = Math1QuestionBank.mcq(1, uId, uTitle, num, tNum, tTitle, prompt, opts, ans, formula = formula, explanation = exp)

    fun s(
        num: Int,
        tNum: Int,
        tTitle: String,
        prompt: String,
        ans: String,
        scheme: List<String>,
        formula: String = ""
    ) = Math1QuestionBank.shortQ(1, uId, uTitle, num, tNum, tTitle, prompt, ans, scheme, formula = formula)

    fun l(
        num: Int,
        tNum: Int,
        tTitle: String,
        marks: Long,
        prompt: String,
        ans: String,
        scheme: List<String>,
        formula: String = ""
    ) = Math1QuestionBank.longQ(1, uId, uTitle, num, tNum, tTitle, marks, prompt, ans, scheme, formula = formula)

    return listOf(
        // ==================== TOPIC 1.1 ====================
        q(1, 1, t1, "Rank of the 3×3 identity matrix is",
            listOf("1", "2", "3", "0"), 2,
            "I₃ has 3 non-zero rows in echelon form."),
        q(2, 1, t1, "Elementary row operations on a matrix do NOT change its",
            listOf("determinant", "rank", "trace", "eigenvalues"), 1,
            "Rank is invariant under elementary transformations."),
        q(3, 1, t1, "The maximum possible rank of a 3×4 matrix is",
            listOf("4", "3", "12", "7"), 1,
            "Rank ≤ min(rows, columns) = 3."),
        q(4, 1, t1, "Rank of the matrix with rows (1,2,3), (2,4,6), (3,6,9) is",
            listOf("1", "2", "3", "0"), 0,
            "All rows are multiples of (1,2,3)."),
        q(5, 1, t1, "The normal form of a matrix of rank r is",
            listOf("[Iᵣ 0; 0 0]", "[0 Iᵣ; 0 0]", "diag(r,r,r)", "upper triangular"), 0,
            "Normal form = identity of order r bordered by zeros.",
            formula = "N = [Iᵣ  0; 0  0]"),
        s(1, 1, t1, "Define rank of a matrix. Find the rank of A = [1 2 3; 2 4 6; 1 2 4].",
            "Rank: the number of non-zero rows in the echelon form of A (equivalently, the order of the largest non-zero minor).\n" +
                "Apply R₂ → R₂ − 2R₁ and R₃ → R₃ − R₁:\n" +
                "[1 2 3; 0 0 0; 0 0 1].\n" +
                "Swap R₂ ↔ R₃ to get echelon form:\n" +
                "[1 2 3; 0 0 1; 0 0 0].\n" +
                "Number of non-zero rows = 2, hence ρ(A) = 2.",
            listOf(
                "1.5M: Correct definition of rank of a matrix",
                "2.5M: Row reduction to echelon form [1 2 3; 0 0 1; 0 0 0]",
                "1M: Conclude ρ(A) = 2"
            ),
            formula = "A = [1 2 3; 2 4 6; 1 2 4] ⇒ ρ(A) = 2"),
        s(2, 1, t1, "State any five properties of the rank of a matrix.",
            "1. ρ(A) ≤ min(m, n) for an m×n matrix.\n" +
                "2. ρ(A) = ρ(Aᵀ).\n" +
                "3. Rank is unchanged by elementary row/column operations.\n" +
                "4. ρ(AB) ≤ min(ρ(A), ρ(B)).\n" +
                "5. A non-singular n×n matrix has rank n; the zero matrix has rank 0.\n" +
                "6. ρ(A) = r ⇔ some r×r minor is non-zero and every (r+1)×(r+1) minor is zero.",
            listOf(
                "5M: 1M for each valid property of matrix rank (any 5 of the 6 listed)"
            )),
        s(3, 1, t1, "Find the rank of A = [1 1 1; 1 2 3; 1 3 6] by reducing to echelon form.",
            "Apply R₂ → R₂ − R₁ and R₃ → R₃ − R₁:\n" +
                "[1 1 1; 0 1 2; 0 2 5].\n" +
                "Apply R₃ → R₃ − 2R₂:\n" +
                "[1 1 1; 0 1 2; 0 0 1].\n" +
                "Three non-zero rows ⇒ ρ(A) = 3 (A is non-singular).",
            listOf(
                "2M: First elimination step R₂ − R₁ and R₃ − R₁",
                "2M: Second elimination step R₃ − 2R₂ yielding upper triangular echelon form",
                "1M: Conclude ρ(A) = 3"
            ),
            formula = "A = [1 1 1; 1 2 3; 1 3 6] ⇒ [1 1 1; 0 1 2; 0 0 1]"),
        l(1, 1, t1, 10L, "Reduce A = [1 2 −1 3; 2 4 1 −2; 3 6 3 −7] to echelon form and then to normal form. Hence find its rank.",
            "Step 1 – Row operations (to echelon form):\n" +
                "R₂ → R₂ − 2R₁, R₃ → R₃ − 3R₁ gives [1 2 −1 3; 0 0 3 −8; 0 0 6 −16].\n" +
                "R₃ → R₃ − 2R₂ gives echelon form: [1 2 −1 3; 0 0 3 −8; 0 0 0 0].\n" +
                "Step 2 – Column operations (to normal form):\n" +
                "C₂ → C₂ − 2C₁, C₃ → C₃ + C₁, C₄ → C₄ − 3C₁ gives [1 0 0 0; 0 0 3 −8; 0 0 0 0].\n" +
                "Swap C₂ ↔ C₃: [1 0 0 0; 0 3 0 −8; 0 0 0 0].\n" +
                "C₄ → C₄ + (8/3)C₂: [1 0 0 0; 0 3 0 0; 0 0 0 0].\n" +
                "C₂ → C₂/3 gives normal form: [1 0 0 0; 0 1 0 0; 0 0 0 0] = [I₂ 0; 0 0].\n" +
                "Step 3 – Conclusion: The normal form contains I₂ (and echelon form has 2 non-zero rows), so ρ(A) = 2.",
            listOf(
                "4M: Row operations reducing A to echelon form [1 2 −1 3; 0 0 3 −8; 0 0 0 0]",
                "4M: Column operations reducing to normal form [I₂ 0; 0 0]",
                "2M: Correct conclusion ρ(A) = 2 verified from both forms"
            ),
            formula = "A = [1 2 −1 3; 2 4 1 −2; 3 6 3 −7] → [I₂ 0; 0 0]"),

        // ==================== TOPIC 1.2 ====================
        q(6, 2, t2, "Any set of vectors containing the zero vector is",
            listOf("linearly independent", "linearly dependent", "a basis", "orthogonal"), 1,
            "1·0 = 0 is a non-trivial combination giving 0."),
        q(7, 2, t2, "Any n + 1 vectors in Rⁿ are always",
            listOf("independent", "dependent", "orthogonal", "a basis"), 1,
            "Rank ≤ n < n + 1 vectors."),
        q(8, 2, t2, "Vectors v₁, …, vₖ are L.I. iff the rank of the matrix formed by them is",
            listOf("< k", "= k", "> k", "= 0"), 1,
            "Full row rank means independent."),
        q(9, 2, t2, "Three vectors in R³ are L.I. iff the determinant of the matrix formed is",
            listOf("zero", "non-zero", "1", "−1"), 1,
            "|A| ≠ 0 ⇔ ρ = 3."),
        q(10, 2, t2, "The vectors (1,0), (0,1), (1,1) in R² are",
            listOf("L.I.", "L.D.", "orthogonal", "a basis"), 1,
            "Three vectors in R²: (1,1) = (1,0) + (0,1)."),
        s(4, 2, t2, "Define linear dependence and independence. Test (1,2,3), (2,3,1), (3,1,2) for linear independence.",
            "Linear Dependence (L.D.): Vectors v₁,…,vₖ are L.D. if scalars c₁,…,cₖ, not all zero, exist with c₁v₁ + … + cₖvₖ = 0. Otherwise they are Linearly Independent (L.I.).\n" +
                "Determinant of matrix formed by the rows:\n" +
                "|1 2 3; 2 3 1; 3 1 2| = 1(6 − 1) − 2(4 − 3) + 3(2 − 9) = 5 − 2 − 21 = −18 ≠ 0.\n" +
                "Hence the vectors are linearly independent.",
            listOf(
                "2M: Precise definitions of linear dependence and independence",
                "2M: Correct determinant computation (= −18 ≠ 0)",
                "1M: Conclude vectors are linearly independent"
            ),
            formula = "|1 2 3; 2 3 1; 3 1 2| = −18 ≠ 0"),
        s(5, 2, t2, "Find λ for which (1,2,3), (2,1,4), (3,3,λ) are linearly dependent, and find the relation.",
            "Dependent ⇔ determinant = 0:\n" +
                "|1 2 3; 2 1 4; 3 3 λ| = (λ − 12) − 2(2λ − 12) + 3(6 − 3) = −3λ + 21 = 0 ⇒ λ = 7.\n" +
                "Put a(1,2,3) + b(2,1,4) + c(3,3,7) = 0:\n" +
                "a + 2b + 3c = 0 and 2a + b + 3c = 0 ⇒ b = a, c = −a.\n" +
                "Relation: v₁ + v₂ − v₃ = 0 (check 3rd component: 3 + 4 − 7 = 0 ✓).",
            listOf(
                "2.5M: Set determinant = 0 and solve −3λ + 21 = 0 ⇒ λ = 7",
                "2.5M: Derive and verify the linear relation v₁ + v₂ − v₃ = 0"
            ),
            formula = "|1 2 3; 2 1 4; 3 3 λ| = 0 ⇒ λ = 7"),
        l(2, 2, t2, 10L, "(a) Prove that any subset of a linearly independent set is linearly independent. (b) Show that v₁ = (1,2,3,1), v₂ = (2,−1,1,0), v₃ = (1,7,8,3) are linearly dependent and find the relation.",
            "(a) Proof (4 marks): Let {v₁,…,vₖ} be L.I. and take a subset {v₁,…,vₘ}, m ≤ k. Suppose c₁v₁ + … + cₘvₘ = 0. Then c₁v₁ + … + cₘvₘ + 0·vₘ₊₁ + … + 0·vₖ = 0. Since the whole set is L.I., every coefficient is 0 ⇒ c₁ = … = cₘ = 0. Hence the subset is L.I. ∎\n" +
                "(b) Solution (6 marks): Form the matrix with v₁, v₂, v₃ as rows:\n" +
                "A = [1 2 3 1; 2 −1 1 0; 1 7 8 3].\n" +
                "R₂ → R₂ − 2R₁, R₃ → R₃ − R₁ ⇒ [1 2 3 1; 0 −5 −5 −2; 0 5 5 2].\n" +
                "R₃ → R₃ + R₂ ⇒ [1 2 3 1; 0 −5 −5 −2; 0 0 0 0].\n" +
                "ρ(A) = 2 < 3 ⇒ the vectors are linearly dependent.\n" +
                "Tracing operations: new R₃ = (v₃ − v₁) + (v₂ − 2v₁) = v₃ + v₂ − 3v₁ = 0.\n" +
                "Relation: 3v₁ − v₂ − v₃ = 0. Check: 3(1,2,3,1) − (2,−1,1,0) = (1,7,8,3) = v₃ ✓.",
            listOf(
                "4M: Part (a) complete formal proof by padding remaining coefficients with 0",
                "3M: Part (b) row reduction showing ρ(A) = 2 < 3 (linearly dependent)",
                "3M: Tracing row operations to obtain and verify 3v₁ − v₂ − v₃ = 0"
            ),
            formula = "3v₁ − v₂ − v₃ = 0"),

        // ==================== TOPIC 1.3 ====================
        q(11, 3, t3, "The system AX = B is consistent iff",
            listOf("ρ(A) < ρ(A|B)", "ρ(A) = ρ(A|B)", "|A| = 0", "B = 0"), 1,
            "Rouché–Capelli theorem."),
        q(12, 3, t3, "A homogeneous system AX = 0 is always",
            listOf("inconsistent", "consistent", "having a unique non-trivial solution", "having no solution"), 1,
            "X = 0 is always a solution."),
        q(13, 3, t3, "If ρ(A) = ρ(A|B) = n (n unknowns), the system has",
            listOf("no solution", "unique solution", "infinitely many solutions", "two solutions"), 1,
            "Full rank ⇒ unique."),
        q(14, 3, t3, "If ρ(A) < ρ(A|B), the system is",
            listOf("consistent", "inconsistent", "homogeneous", "dependent"), 1,
            "A row 0 = non-zero appears."),
        q(15, 3, t3, "AX = 0 (n unknowns) has a non-trivial solution iff",
            listOf("ρ(A) = n", "ρ(A) < n", "ρ(A) > n", "|A| ≠ 0"), 1,
            "Free variables exist when ρ < n."),
        s(6, 3, t3, "State the condition for consistency. Test the consistency of x + y + z = 6, x + 2y + 3z = 14, x + 4y + 7z = 30 and solve.",
            "Condition: AX = B is consistent ⇔ ρ(A) = ρ(A|B).\n" +
                "Augmented matrix [A | B] = [1 1 1 | 6; 1 2 3 | 14; 1 4 7 | 30].\n" +
                "R₂ → R₂ − R₁, R₃ → R₃ − R₁ ⇒ [1 1 1 | 6; 0 1 2 | 8; 0 3 6 | 24].\n" +
                "R₃ → R₃ − 3R₂ ⇒ [1 1 1 | 6; 0 1 2 | 8; 0 0 0 | 0].\n" +
                "ρ(A) = ρ(A|B) = 2 < 3 ⇒ consistent with infinitely many solutions.\n" +
                "Let z = k: y = 8 − 2k, x = 6 − y − z = k − 2.\n" +
                "Solution: (x, y, z) = (k − 2, 8 − 2k, k).",
            listOf(
                "1M: State Rouché–Capelli condition ρ(A) = ρ(A|B)",
                "2M: Row reduction of [A|B] to echelon form",
                "2M: Identify ρ = 2 < 3 and express parametric solution (k − 2, 8 − 2k, k)"
            ),
            formula = "AX = B consistent ⇔ ρ(A) = ρ(A|B)"),
        s(7, 3, t3, "For what value of λ does x + y + z = 0, x + 2y + 3z = 0, x + 3y + λz = 0 have a non-trivial solution?",
            "Non-trivial solution ⇔ |A| = 0 (equivalently ρ(A) < 3).\n" +
                "|1 1 1; 1 2 3; 1 3 λ| = (2λ − 9) − (λ − 3) + (3 − 2) = λ − 5.\n" +
                "So |A| = 0 ⇒ λ = 5. For λ = 5 the system has infinitely many (non-trivial) solutions.",
            listOf(
                "1.5M: State condition |A| = 0 for non-trivial solution of AX = 0",
                "2.5M: Expand determinant to get λ − 5",
                "1M: Solve λ = 5"
            ),
            formula = "|1 1 1; 1 2 3; 1 3 λ| = λ − 5 = 0 ⇒ λ = 5"),
        l(3, 3, t3, 10L, "Investigate for what values of λ and μ the system x + y + z = 6, x + 2y + 3z = 10, x + 2y + λz = μ has (i) a unique solution (ii) infinitely many solutions (iii) no solution.",
            "Step 1 – Augmented matrix and reduction:\n" +
                "[1 1 1 | 6; 1 2 3 | 10; 1 2 λ | μ].\n" +
                "R₂ → R₂ − R₁, R₃ → R₃ − R₁ ⇒ [1 1 1 | 6; 0 1 2 | 4; 0 1 λ−1 | μ−6].\n" +
                "R₃ → R₃ − R₂ ⇒ [1 1 1 | 6; 0 1 2 | 4; 0 0 λ−3 | μ−10].\n" +
                "Step 2 – Discuss cases:\n" +
                "(i) Unique solution: λ ≠ 3 (any μ). Then ρ(A) = ρ(A|B) = 3 = number of unknowns.\n" +
                "(ii) Infinitely many solutions: λ = 3 and μ = 10. Then ρ(A) = ρ(A|B) = 2 < 3.\n" +
                "(iii) No solution: λ = 3 and μ ≠ 10. Then ρ(A) = 2 ≠ ρ(A|B) = 3 ⇒ inconsistent.\n" +
                "Step 3 – Solution in case (ii): y + 2z = 4 and x + y + z = 6. Put z = k ⇒ y = 4 − 2k, x = 2 + k. So (x, y, z) = (2 + k, 4 − 2k, k).",
            listOf(
                "4M: Reduce augmented matrix to [1 1 1 | 6; 0 1 2 | 4; 0 0 λ−3 | μ−10]",
                "4.5M: Correctly analyze all three cases (1.5M each for unique, infinite, no solution)",
                "1.5M: Parametric solution (2 + k, 4 − 2k, k) for case (ii)"
            ),
            formula = "[1 1 1 | 6; 0 1 2 | 4; 0 0 λ−3 | μ−10]"),

        // ==================== TOPIC 1.4 ====================
        q(16, 4, t4, "The sum of the eigenvalues of a square matrix equals its",
            listOf("determinant", "trace", "rank", "order"), 1,
            "Σλ = tr(A)."),
        q(17, 4, t4, "The product of the eigenvalues of A equals",
            listOf("trace", "|A|", "rank", "0"), 1,
            "Πλ = |A|."),
        q(18, 4, t4, "Eigenvalues of an upper-triangular matrix are",
            listOf("its diagonal entries", "all zero", "all one", "its row sums"), 0,
            "|A − λI| is the product of (aᵢᵢ − λ)."),
        q(19, 4, t4, "If λ is an eigenvalue of A, then an eigenvalue of A² is",
            listOf("λ", "2λ", "λ²", "1/λ"), 2,
            "A²X = λ²X."),
        q(20, 4, t4, "The Cayley–Hamilton theorem states that every square matrix",
            listOf("is diagonalizable", "satisfies its own characteristic equation", "has real eigenvalues", "has rank n"), 1,
            "p(A) = 0 where p(λ) = |A − λI|."),
        q(21, 4, t4, "If A is singular, then one eigenvalue of A is",
            listOf("1", "−1", "0", "undefined"), 2,
            "|A| = Πλ = 0."),
        s(8, 4, t4, "Find the eigenvalues and eigenvectors of A = [2 1; 1 2].",
            "Characteristic equation: |A − λI| = (2 − λ)² − 1 = λ² − 4λ + 3 = 0 ⇒ λ = 1, 3.\n" +
                "For λ = 1: (A − I)X = 0 ⇒ x + y = 0 ⇒ eigenvector X₁ = (1, −1)ᵀ.\n" +
                "For λ = 3: (A − 3I)X = 0 ⇒ −x + y = 0 ⇒ eigenvector X₂ = (1, 1)ᵀ.",
            listOf(
                "2M: Form and solve |A − λI| = λ² − 4λ + 3 = 0 ⇒ λ = 1, 3",
                "1.5M: Eigenvector X₁ = (1, −1)ᵀ for λ = 1",
                "1.5M: Eigenvector X₂ = (1, 1)ᵀ for λ = 3"
            ),
            formula = "|A − λI| = λ² − 4λ + 3 = 0"),
        s(9, 4, t4, "State the Cayley–Hamilton theorem and verify it for A = [1 2; 3 4].",
            "Theorem: Every square matrix satisfies its own characteristic equation.\n" +
                "Characteristic equation: λ² − (tr A)λ + |A| = λ² − 5λ − 2 = 0.\n" +
                "A² = [7 10; 15 22], 5A = [5 10; 15 20].\n" +
                "A² − 5A − 2I = [7−5−2  10−10; 15−15  22−20−2] = [0 0; 0 0] ✓.\n" +
                "Hence the theorem is verified.",
            listOf(
                "1M: Statement of Cayley–Hamilton theorem",
                "1.5M: Characteristic equation λ² − 5λ − 2 = 0",
                "2.5M: Compute A² and verify A² − 5A − 2I = 0"
            ),
            formula = "A² − 5A − 2I = 0"),
        l(4, 4, t4, 10L, "Find the eigenvalues and eigenvectors of A = [2 −1 1; −1 2 −1; 1 −1 2].",
            "Step 1 – Characteristic equation: tr A = 6; sum of principal 2×2 minors = 3 + 3 + 3 = 9; |A| = 2(3) + 1(−1) + 1(−1) = 4.\n" +
                "λ³ − 6λ² + 9λ − 4 = 0 ⇒ (λ − 1)²(λ − 4) = 0.\n" +
                "Eigenvalues: λ = 1, 1, 4 (check: sum = 6 = trace, product = 4 = |A|).\n" +
                "Step 2 – λ = 4: (A − 4I)X = 0 gives −2x − y + z = 0, −x − 2y − z = 0, x − y − 2z = 0. Adding the first two: −3x − 3y = 0 ⇒ y = −x; then z = x. Eigenvector X₁ = (1, −1, 1)ᵀ.\n" +
                "Step 3 – λ = 1 (repeated): (A − I)X = 0 reduces to the single equation x − y + z = 0 (rank 1 ⇒ 2 free variables).\n" +
                "Choose (y, z) = (1, 0) ⇒ x = 1: X₂ = (1, 1, 0)ᵀ.\n" +
                "Choose (y, z) = (1, 1) ⇒ x = 0: X₃ = (0, 1, 1)ᵀ.\n" +
                "Result: (1, −1, 1)ᵀ for λ = 4; two L.I. eigenvectors (1, 1, 0)ᵀ and (0, 1, 1)ᵀ for λ = 1.",
            listOf(
                "3.5M: Characteristic equation λ³ − 6λ² + 9λ − 4 = 0 and eigenvalues λ = 1, 1, 4",
                "2.5M: Eigenvector X₁ = (1, −1, 1)ᵀ for λ = 4",
                "4M: Two linearly independent eigenvectors X₂ = (1, 1, 0)ᵀ, X₃ = (0, 1, 1)ᵀ for λ = 1"
            ),
            formula = "λ³ − 6λ² + 9λ − 4 = (λ − 1)²(λ − 4) = 0"),
        l(5, 4, t4, 10L, "Verify the Cayley–Hamilton theorem for A = [2 1 1; 0 1 0; 1 1 2] and hence find A⁻¹.",
            "Characteristic equation: tr A = 5; sum of principal 2×2 minors = 2 + 3 + 2 = 7; |A| = 3.\n" +
                "λ³ − 5λ² + 7λ − 3 = 0.\n" +
                "Compute powers:\n" +
                "A² = [5 4 4; 0 1 0; 4 4 5], A³ = A·A² = [14 13 13; 0 1 0; 13 13 14].\n" +
                "Verify A³ − 5A² + 7A − 3I:\n" +
                "Row 1: (14−25+14−3, 13−20+7, 13−20+7) = (0,0,0);\n" +
                "Row 2: (0, 1−5+7−3, 0) = (0,0,0);\n" +
                "Row 3: (13−20+7, 13−20+7, 14−25+14−3) = (0,0,0). Zero matrix ✓.\n" +
                "Inverse: multiply by A⁻¹ ⇒ A² − 5A + 7I − 3A⁻¹ = 0 ⇒ A⁻¹ = (A² − 5A + 7I)/3.\n" +
                "A² − 5A + 7I = [2 −1 −1; 0 3 0; −1 −1 2].\n" +
                "A⁻¹ = (1/3)[2 −1 −1; 0 3 0; −1 −1 2].",
            listOf(
                "2.5M: Characteristic equation λ³ − 5λ² + 7λ − 3 = 0",
                "3M: Accurate computation of A² and A³",
                "2M: Verification A³ − 5A² + 7A − 3I = 0",
                "2.5M: Derivation and computation of A⁻¹ = (1/3)[2 −1 −1; 0 3 0; −1 −1 2]"
            ),
            formula = "A⁻¹ = (A² − 5A + 7I)/3"),

        // ==================== TOPIC 1.5 ====================
        q(22, 5, t5, "Similar matrices have the same",
            listOf("eigenvalues", "eigenvectors", "entries", "rows"), 0,
            "Same characteristic polynomial."),
        q(23, 5, t5, "An n×n matrix is diagonalizable iff it has",
            listOf("n distinct eigenvalues only", "n linearly independent eigenvectors", "zero determinant", "real entries"), 1,
            "Columns of P are eigenvectors."),
        q(24, 5, t5, "If A has n distinct eigenvalues then A is",
            listOf("not diagonalizable", "diagonalizable", "singular", "nilpotent"), 1,
            "Distinct eigenvalues ⇒ L.I. eigenvectors."),
        q(25, 5, t5, "If P⁻¹AP = D then Aⁿ equals",
            listOf("PDⁿP⁻¹", "P⁻¹DⁿP", "Dⁿ", "nPDP⁻¹"), 0,
            "A = PDP⁻¹ ⇒ Aⁿ = PDⁿP⁻¹.",
            formula = "A = PDP⁻¹ ⇒ Aⁿ = PDⁿP⁻¹"),
        q(26, 5, t5, "Eigenvectors of a real symmetric matrix for distinct eigenvalues are",
            listOf("parallel", "orthogonal", "equal", "zero"), 1,
            "Standard property of symmetric matrices."),
        s(10, 5, t5, "Define similar matrices and prove that similar matrices have the same eigenvalues.",
            "Definition: Matrices A and B are similar if B = P⁻¹AP for some non-singular matrix P.\n" +
                "Proof: |B − λI| = |P⁻¹AP − λP⁻¹P| = |P⁻¹(A − λI)P| = |P⁻¹||A − λI||P| = |A − λI|.\n" +
                "The characteristic polynomials are identical, hence A and B have the same eigenvalues. ∎",
            listOf(
                "1.5M: Definition B = P⁻¹AP with P non-singular",
                "2.5M: Determinant identity |B − λI| = |P⁻¹(A − λI)P| = |A − λI|",
                "1M: Conclusion that eigenvalues are identical"
            ),
            formula = "|B − λI| = |P⁻¹(A − λI)P| = |A − λI|"),
        s(11, 5, t5, "Is A = [1 1; 0 1] diagonalizable? Justify.",
            "Characteristic equation: (1 − λ)² = 0 ⇒ λ = 1 (repeated twice).\n" +
                "(A − I)X = 0 ⇒ y = 0, so rank(A − I) = 1 and only one L.I. eigenvector (1, 0)ᵀ exists.\n" +
                "We need 2 L.I. eigenvectors for a 2×2 matrix, so A is NOT diagonalizable.",
            listOf(
                "1.5M: Find repeated eigenvalue λ = 1",
                "2M: Show (A − I)X = 0 yields only 1 L.I. eigenvector (1, 0)ᵀ",
                "1.5M: Conclude A is not diagonalizable (geometric multiplicity 1 < algebraic multiplicity 2)"
            )),
        l(6, 5, t5, 10L, "Diagonalize A = [4 1; 2 3] and hence find A⁵.",
            "Step 1 – Eigenvalues: λ² − 7λ + 10 = 0 ⇒ λ = 2, 5.\n" +
                "Step 2 – Eigenvectors:\n" +
                "λ = 2: 2x + y = 0 ⇒ X₁ = (1, −2)ᵀ.\n" +
                "λ = 5: −x + y = 0 ⇒ X₂ = (1, 1)ᵀ.\n" +
                "Step 3 – Modal matrix:\n" +
                "P = [1 1; −2 1], |P| = 3, P⁻¹ = (1/3)[1 −1; 2 1], D = diag(2, 5).\n" +
                "Verification: P⁻¹AP = D.\n" +
                "Step 4 – Power: A⁵ = PD⁵P⁻¹ with D⁵ = diag(32, 3125).\n" +
                "PD⁵ = [32 3125; −64 3125].\n" +
                "A⁵ = (1/3)[32 3125; −64 3125][1 −1; 2 1] = (1/3)[6282 3093; 6186 3189] = [2094 1031; 2062 1063].\n" +
                "Check: trace = 2094 + 1063 = 3157 = 2⁵ + 5⁵ ✓.",
            listOf(
                "2M: Eigenvalues λ = 2, 5",
                "2.5M: Eigenvectors X₁ = (1, −2)ᵀ, X₂ = (1, 1)ᵀ",
                "2.5M: Modal matrix P, inverse P⁻¹, and diagonal D = diag(2, 5)",
                "3M: Compute A⁵ = PD⁵P⁻¹ = [2094 1031; 2062 1063]"
            ),
            formula = "A⁵ = P·diag(2⁵, 5⁵)·P⁻¹"),

        // ==================== TOPIC 1.6 ====================
        q(27, 6, t6, "Matrix of the quadratic form x² + 4xy + 3y² is",
            listOf("[1 4; 4 3]", "[1 2; 2 3]", "[1 2; 0 3]", "[1 1; 1 3]"), 1,
            "Off-diagonal entries are half the xy coefficient."),
        q(28, 6, t6, "Q is positive definite if all eigenvalues of its matrix are",
            listOf("negative", "zero", "positive", "complex"), 2,
            "Definition via eigenvalues."),
        q(29, 6, t6, "Rank of a quadratic form equals the",
            listOf("no. of non-zero terms in canonical form", "order of A", "trace of A", "|A|"), 0,
            "Rank = rank of the matrix A."),
        q(30, 6, t6, "Index of a quadratic form is the number of",
            listOf("negative terms", "positive terms in canonical form", "zero terms", "variables"), 1,
            "Index p = number of positive squares."),
        q(31, 6, t6, "Signature of a quadratic form with p positive and q negative terms is",
            listOf("p + q", "p − q", "p·q", "q − p"), 1,
            "s = p − q = 2p − r."),
        s(12, 6, t6, "Write the matrix of Q = x² + 2y² + 3z² + 2xy − 2yz + 4zx.",
            "Diagonal entries: coefficients of x², y², z² = 1, 2, 3.\n" +
                "Off-diagonal entries: a₁₂ = ½(2) = 1, a₂₃ = ½(−2) = −1, a₁₃ = ½(4) = 2.\n" +
                "Symmetric matrix A = [1 1 2; 1 2 −1; 2 −1 3].",
            listOf(
                "2M: Diagonal entries 1, 2, 3",
                "3M: Halving cross-term coefficients to form symmetric A = [1 1 2; 1 2 −1; 2 −1 3]"
            ),
            formula = "Q = XᵀAX, A = [1 1 2; 1 2 −1; 2 −1 3]"),
        s(13, 6, t6, "Determine the nature of Q = 2x² − 2xy + 2y².",
            "Matrix A = [2 −1; −1 2].\n" +
                "Characteristic equation: (2 − λ)² − 1 = 0 ⇒ λ = 1, 3.\n" +
                "Both eigenvalues are positive ⇒ Q is positive definite (rank 2, index 2, signature 2).",
            listOf(
                "1.5M: Symmetric matrix A = [2 −1; −1 2]",
                "2M: Eigenvalues λ = 1, 3",
                "1.5M: Conclude positive definite (rank = 2, index = 2, signature = 2)"
            )),
        l(7, 6, t6, 12L, "Reduce the quadratic form Q = x² + 3y² + 3z² + 2yz to canonical form by an orthogonal transformation. State its rank, index, signature and nature.",
            "Step 1 – Matrix: A = [1 0 0; 0 3 1; 0 1 3].\n" +
                "Step 2 – Eigenvalues: A is block diagonal. Block [3 1; 1 3] gives (3 − λ)² = 1 ⇒ λ = 2, 4; first block gives λ = 1. So λ = 1, 2, 4 (all distinct).\n" +
                "Step 3 – Eigenvectors (mutually orthogonal, symmetric matrix):\n" +
                "λ = 1: X₁ = (1, 0, 0) ⇒ e₁ = (1, 0, 0).\n" +
                "λ = 2: x = 0, y + z = 0 ⇒ X₂ = (0, 1, −1) ⇒ e₂ = (0, 1, −1)/√2.\n" +
                "λ = 4: x = 0, y − z = 0 ⇒ X₃ = (0, 1, 1) ⇒ e₃ = (0, 1, 1)/√2.\n" +
                "Step 4 – Orthogonal transformation X = PY with P = [e₁ e₂ e₃]:\n" +
                "x = y₁, y = (y₂ + y₃)/√2, z = (−y₂ + y₃)/√2.\n" +
                "Step 5 – Canonical form: PᵀAP = diag(1, 2, 4) ⇒ Q = y₁² + 2y₂² + 4y₃².\n" +
                "Rank = 3, Index = 3, Signature = 3; all eigenvalues > 0 ⇒ Q is positive definite.",
            listOf(
                "1.5M: Symmetric matrix A = [1 0 0; 0 3 1; 0 1 3]",
                "2.5M: Eigenvalues λ = 1, 2, 4",
                "3.5M: Normalized orthogonal eigenvectors e₁, e₂, e₃ and orthogonal matrix P",
                "2.5M: Canonical form Q = y₁² + 2y₂² + 4y₃² and transformation X = PY",
                "2M: Rank = 3, Index = 3, Signature = 3, Nature = positive definite"
            ),
            formula = "Q = y₁² + 2y₂² + 4y₃²")
    )
}
