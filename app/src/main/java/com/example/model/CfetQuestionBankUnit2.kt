package com.example.model

/**
 * CFET Unit 2.0: Data Representation
 * 53 Objective (1M) + 14 Short (5M) + 7 Long (10–12M) = 74 questions across Topics T1–T7.
 */
internal fun cfetUnit2Questions(): List<TestQuestion> {
    val uId = "u_100108_2"
    val uTitle = "Unit 2.0: Data Representation"

    val t1 = "T1. Data Representation & Number Systems"
    val t2 = "T2. Decimal ↔ Binary Conversion"
    val t3 = "T3. Binary, Octal & Hexadecimal Conversion"
    val t4 = "T4. Binary Arithmetic (Add & Subtract)"
    val t5 = "T5. Signed Numbers – 1's Complement"
    val t6 = "T6. Signed Numbers – 2's Complement"
    val t7 = "T7. ASCII, Unicode & Memory Storage"

    fun q(n: Int, tn: Int, tt: String, p: String, opts: List<String>, ans: Int, f: String = "", exp: String = "") =
        CfetQuestionBank.mcq(2, uId, uTitle, n, tn, tt, p, opts, ans, f, "NONE", exp)

    fun s(n: Int, tn: Int, tt: String, p: String, ans: String, sc: List<String>, f: String = "") =
        CfetQuestionBank.shortQ(2, uId, uTitle, n, tn, tt, p, ans, sc, f)

    fun l(n: Int, tn: Int, tt: String, m: Long, p: String, ans: String, sc: List<String>, f: String = "") =
        CfetQuestionBank.longQ(2, uId, uTitle, n, tn, tt, m, p, ans, sc, f)

    return listOf(
        // T1: Objective Q1–Q8
        q(1, 1, t1, "The base (radix) of the hexadecimal number system is:", listOf("8", "10", "16", "2"), 2),
        q(2, 1, t1, "Which of the following is NOT a valid binary number?", listOf("1010", "1102", "0001", "1111"), 1, exp = "Binary allows only digits 0 and 1."),
        q(3, 1, t1, "The digits used in the octal number system are:", listOf("0–7", "0–8", "0–9", "0–F"), 0),
        q(4, 1, t1, "Hexadecimal digit 'F' is equal to decimal:", listOf("10", "14", "15", "16"), 2),
        q(5, 1, t1, "Why do computers use the binary number system?", listOf("It is easy for humans", "Electronic circuits have two stable states (ON/OFF)", "It needs more memory", "It is faster to type"), 1),
        q(6, 1, t1, "One byte consists of:", listOf("4 bits", "8 bits", "16 bits", "32 bits"), 1),
        q(7, 1, t1, "Minimum number of bits needed to represent decimal numbers 0 to 15 is:", listOf("3", "4", "5", "8"), 1, exp = "2⁴ = 16 combinations."),
        q(8, 1, t1, "In BCD, each decimal digit is represented using:", listOf("3 bits", "4 bits", "7 bits", "8 bits"), 1),
        // T1: Short S1–S2 & Long L1
        s(1, 1, t1, "Explain the different number systems used in computers with their base and digits. ★",
            "Decimal (base 10, digits 0–9, e.g. (259)₁₀), Binary (base 2, digits 0–1, e.g. (1011)₂), Octal (base 8, digits 0–7, e.g. (157)₈), Hexadecimal (base 16, 0–9 & A–F where A=10…F=15, e.g. (3AF)₁₆). Positional value = Σ(digit × base^position). Example: (1011)₂ = 1×2³ + 0×2² + 1×2¹ + 1×2⁰ = 11.",
            listOf("3M: Table of Decimal, Binary, Octal & Hex with base, digits & examples", "2M: Positional weight formula & worked example"),
            f = "N = Σ (dᵢ × rⁱ)"),
        s(2, 1, t1, "What is BCD? Represent (45)₁₀ in BCD.",
            "Binary Coded Decimal encodes each decimal digit separately into its 4-bit binary (8-4-2-1) code. Valid codes: 0000–1001; invalid: 1010–1111. (45)₁₀ → 4 = 0100, 5 = 0101 ⇒ (0100 0101)_BCD (pure binary is 101101). Used in digital clocks, calculators & financial systems.",
            listOf("2M: Definition of BCD & valid 8-4-2-1 range", "2M: Correct conversion (45)₁₀ = 0100 0101", "1M: Practical uses"),
            f = "(45)₁₀ → 4(0100) 5(0101) = 0100 0101_BCD"),
        l(1, 1, t1, 10L, "What is data representation? Explain the various number systems and why binary is used in computers.",
            "Data representation encodes numbers (binary, 1's/2's complement, IEEE 754), text (ASCII/Unicode), images (RGB pixels), and audio/video (MP3/MP4) into 0s and 1s.\nNumber systems: Decimal (10), Binary (2), Octal (8, 3-bit shorthand), Hexadecimal (16, 4-bit shorthand for addresses/colors), all positional N = Σ dᵢ·rⁱ with 0–15 equivalence table.\nWhy binary: transistor switches have two stable ON/OFF states, simple/cheap/reliable hardware with high noise immunity, direct Boolean algebra mapping to logic gates. Units: bit → nibble (4) → byte (8) → word (16/32/64).",
            listOf("2M: Definition & types of data represented", "3M: Number systems comparison & positional formula", "3M: 0–15 equivalence table (Dec/Bin/Oct/Hex)", "2M: Why binary is used & storage units")),

        // T2: Objective Q9–Q15
        q(9, 2, t2, "The decimal number 25 in binary is:", listOf("10101", "11001", "11010", "10011"), 1),
        q(10, 2, t2, "The binary number 1010 in decimal is:", listOf("8", "9", "10", "12"), 2),
        q(11, 2, t2, "To convert the integer part of a decimal number to binary we use:", listOf("Repeated multiplication by 2", "Repeated division by 2", "Repeated addition", "Repeated subtraction of 10"), 1),
        q(12, 2, t2, "To convert the fractional part of a decimal number to binary we use:", listOf("Repeated division by 2", "Repeated multiplication by 2", "Repeated division by 10", "Square root"), 1),
        q(13, 2, t2, "The decimal fraction 0.5 in binary is:", listOf("0.01", "0.1", "0.11", "0.001"), 1),
        q(14, 2, t2, "The decimal value of binary 1111 is:", listOf("14", "15", "16", "31"), 1),
        q(15, 2, t2, "Binary 100000 equals decimal:", listOf("16", "32", "64", "20"), 1),
        // T2: Short S3–S4 & Long L2
        s(3, 2, t2, "Convert (45.625)₁₀ into binary. ★",
            "Integer part (45): 45÷2=22 r1, 22÷2=11 r0, 11÷2=5 r1, 5÷2=2 r1, 2÷2=1 r0, 1÷2=0 r1 → read bottom-to-top: 101101.\nFraction (0.625): 0.625×2=1.25 (1), 0.25×2=0.5 (0), 0.5×2=1.0 (1) → read top-to-bottom: .101.\nResult: (45.625)₁₀ = (101101.101)₂.",
            listOf("2.5M: Repeated division by 2 for 45 → 101101", "2M: Repeated multiplication by 2 for 0.625 → .101", "0.5M: Combined result (101101.101)₂"),
            f = "(45.625)₁₀ = (101101.101)₂"),
        s(4, 2, t2, "Convert (1101.101)₂ into decimal. ★",
            "Integer: 1×2³ + 1×2² + 0×2¹ + 1×2⁰ = 8 + 4 + 0 + 1 = 13.\nFraction: 1×2⁻¹ + 0×2⁻² + 1×2⁻³ = 0.5 + 0 + 0.125 = 0.625.\nResult: (1101.101)₂ = (13.625)₁₀.",
            listOf("2.5M: Integer positional expansion = 13", "2M: Fractional negative-power expansion = 0.625", "0.5M: Final value (13.625)₁₀"),
            f = "1101.101₂ = 8+4+0+1 + 0.5+0+0.125 = 13.625₁₀"),
        l(2, 2, t2, 10L, "Explain with examples the methods of converting numbers from decimal to binary and binary to decimal (including fractions). ★",
            "A. Decimal → Binary: (i) Integer part by repeated division by 2, reading remainders upward. Ex: (156)₁₀ = (10011100)₂. (ii) Fraction by repeated multiplication by 2, reading integer carries downward. Ex: (0.375)₁₀ = (0.011)₂. Note non-terminating fractions like 0.1 stop at target precision.\nB. Binary → Decimal: Multiply each bit by 2^position (0,1,2... left; −1,−2,−3... right) and sum. Ex: (10011100.011)₂ = 128+16+8+4 + 0.25+0.125 = 156.375.",
            listOf("3M: Decimal integer → binary method & worked (156)₁₀ example", "2.5M: Decimal fraction → binary method & (0.375)₁₀ example", "3.5M: Binary → decimal positional method & verification", "1M: Key powers of 2 & precision note")),

        // T3: Objective Q16–Q23
        q(16, 3, t3, "To convert binary to octal, bits are grouped in sets of:", listOf("2", "3", "4", "8"), 1),
        q(17, 3, t3, "To convert binary to hexadecimal, bits are grouped in sets of:", listOf("2", "3", "4", "5"), 2),
        q(18, 3, t3, "The binary equivalent of hexadecimal 'F' is:", listOf("1110", "1111", "1010", "1100"), 1),
        q(19, 3, t3, "Octal number 17 equals decimal:", listOf("13", "14", "15", "17"), 2, exp = "1×8 + 7 = 15."),
        q(20, 3, t3, "Hexadecimal number A5 equals decimal:", listOf("155", "160", "165", "175"), 2, exp = "10×16 + 5 = 165."),
        q(21, 3, t3, "The binary number 10110110 in hexadecimal is:", listOf("A6", "B6", "B5", "C6"), 1, exp = "1011 = B, 0110 = 6."),
        q(22, 3, t3, "Octal number 777 equals decimal:", listOf("511", "512", "777", "255"), 0, exp = "7×64 + 7×8 + 7 = 511."),
        q(23, 3, t3, "Hexadecimal 1F in binary is:", listOf("11111", "10111", "11011", "01111"), 0, exp = "0001 1111 → 11111."),
        // T3: Short S5–S6 & Long L3
        s(5, 3, t3, "Convert (1101011)₂ into octal and hexadecimal. ★",
            "Octal (3-bit groups from right): 001 101 011 → (153)₈.\nHexadecimal (4-bit groups from right): 0110 1011 → (6B)₁₆.\nCheck: 64+32+8+2+1 = 107 = (153)₈ = (6B)₁₆.",
            listOf("2.5M: 3-bit grouping with zero-padding → (153)₈", "2.5M: 4-bit grouping with zero-padding → (6B)₁₆"),
            f = "(1101011)₂ = (153)₈ = (6B)₁₆ = (107)₁₀"),
        s(6, 3, t3, "Convert (2F5)₁₆ into binary, octal and decimal.",
            "Hex → Binary: 2=0010, F=1111, 5=0101 ⇒ (1011110101)₂.\nBinary → Octal (3-bit groups): 001 011 110 101 ⇒ (1365)₈.\nDecimal: 2×256 + 15×16 + 5 = 512 + 240 + 5 = (757)₁₀.",
            listOf("1.5M: Hex → Binary (1011110101)₂", "1.5M: Binary → Octal (1365)₈", "2M: Positional Decimal calculation (757)₁₀"),
            f = "(2F5)₁₆ = (1011110101)₂ = (1365)₈ = (757)₁₀"),
        l(3, 3, t3, 10L, "Explain the conversion among binary, octal and hexadecimal number systems with suitable examples.",
            "Because 8=2³ and 16=2⁴, 1 octal digit = 3 bits and 1 hex digit = 4 bits.\n1. Binary → Octal/Hex: group 3/4 bits from binary point. Ex: (1010.11)₂ = (12.6)₈ = (A.C)₁₆.\n2. Octal/Hex → Binary: expand each digit to 3/4 bits. Ex: (725)₈=(111010101)₂; (3AF)₁₆=(1110101111)₂.\n3. Octal ↔ Hex: convert via binary. Ex: (725)₈ → (1D5)₁₆.\n4. Octal/Hex → Decimal: positional weights ((725)₈=(1D5)₁₆=469).\n5. Decimal → Octal/Hex: repeated division by 8 or 16 ((255)₁₀=(FF)₁₆=(377)₈). Applications: memory addresses/colors (#FF5733) and Unix permissions (chmod 755).",
            listOf("2M: 0–15 lookup table & 2³/2⁴ grouping principle", "2M: Binary ↔ Octal/Hex with fractional example", "2M: Octal ↔ Hex via binary", "2M: Decimal ↔ Octal/Hex examples", "2M: Practical applications")),

        // T4: Objective Q24–Q30
        q(24, 4, t4, "In binary addition, 1 + 1 equals:", listOf("1", "2", "10 (sum 0, carry 1)", "11"), 2),
        q(25, 4, t4, "In binary addition, 1 + 1 + 1 equals:", listOf("11 (sum 1, carry 1)", "10", "111", "3"), 0),
        q(26, 4, t4, "(101) + (011) in binary is:", listOf("1000", "1010", "111", "1100"), 0, exp = "5 + 3 = 8 = 1000."),
        q(27, 4, t4, "(1010) − (0101) in binary is:", listOf("0101", "0111", "1111", "0011"), 0),
        q(28, 4, t4, "In binary subtraction 0 − 1 requires:", listOf("A carry", "A borrow", "No operation", "Shifting"), 1),
        q(29, 4, t4, "(11) × (11) in binary is:", listOf("110", "1001", "1111", "1010"), 1, exp = "3 × 3 = 9 = 1001."),
        q(30, 4, t4, "The carry generated by adding 1 + 1 is passed to:", listOf("The same bit", "The next higher bit", "The lower bit", "It is discarded"), 1),
        // T4: Short S7–S8 & Long L4
        s(7, 4, t4, "Add (1011)₂ and (1101)₂. ★",
            "Rules: 0+0=0, 0+1=1, 1+0=1, 1+1=0 carry 1, 1+1+1=1 carry 1.\nCarries: 1 1 1 1\n  1011 (11) + 1101 (13) = 11000 (24).\nResult = (11000)₂ = (24)₁₀.",
            listOf("2M: Binary addition rules stated", "2M: Bit-by-bit carry propagation shown", "1M: Final result (11000)₂ = 24"),
            f = "1011₂ + 1101₂ = 11000₂ (24₁₀)"),
        s(8, 4, t4, "Subtract (1101)₂ from (10110)₂ using the borrow method. ★",
            "Rules: 0−0=0, 1−0=1, 1−1=0, 0−1=1 with borrow 1 from next higher bit.\n  10110 (22) − 01101 (13) = 01001 (9).\nResult = (01001)₂ = (9)₁₀.",
            listOf("2M: Binary subtraction & borrow rules", "2M: Step-by-step borrow computation", "1M: Final verified result (01001)₂ = 9"),
            f = "10110₂ − 01101₂ = 01001₂ (9₁₀)"),
        l(4, 4, t4, 10L, "Explain binary addition, subtraction and multiplication with rules and suitable examples.",
            "1. Addition truth table (0+0=0, 0+1=1, 1+0=1, 1+1=0 c1): Ex 1011 + 1101 = 11000 (24).\n2. Subtraction truth table (0−0=0, 1−0=1, 1−1=0, 0−1=1 b1): Ex 10110 − 01101 = 01001 (9). Hardware alternative: A + 2's complement(B).\n3. Multiplication (0×0=0, 0×1=0, 1×0=0, 1×1=1): Ex 1101 × 101 (13×5) = 1101 + 00000 + 110100 = 1000001 (65).\n4. Division: repeated subtract-and-shift (1100 ÷ 100 = 11). ALU note: shift-and-add / shift-and-subtract.",
            listOf("3M: Binary addition rules & worked example", "3M: Binary subtraction rules, borrow example & 2's comp note", "3M: Binary multiplication rules & 1101 × 101 = 1000001 example", "1M: Binary division & ALU hardware note")),

        // T5: Objective Q31–Q37
        q(31, 5, t5, "The 1's complement of 10110 is:", listOf("01001", "01010", "10111", "11001"), 0),
        q(32, 5, t5, "In signed representation, the MSB (leftmost bit) = 1 indicates:", listOf("Positive number", "Negative number", "Overflow", "Zero"), 1),
        q(33, 5, t5, "The range of an n-bit 1's complement number is:", listOf("−2^(n−1) to +2^(n−1)−1", "−(2^(n−1)−1) to +(2^(n−1)−1)", "0 to 2^n−1", "−2^n to +2^n"), 1),
        q(34, 5, t5, "An 8-bit number in 1's complement can represent values from:", listOf("−128 to +127", "−127 to +127", "−255 to +255", "0 to 255"), 1),
        q(35, 5, t5, "In 1's complement representation, zero has:", listOf("One representation", "Two representations (+0 and −0)", "No representation", "Three representations"), 1, exp = "00000000 (+0) and 11111111 (−0)."),
        q(36, 5, t5, "The 'end-around carry' is used in:", listOf("2's complement addition", "1's complement addition", "BCD addition", "Gray code"), 1),
        q(37, 5, t5, "1's complement of a number is obtained by:", listOf("Adding 1", "Inverting all bits", "Shifting left", "Subtracting 1"), 1),
        // T5: Short S9–S10 & Long L5
        s(9, 5, t5, "Explain signed number representation methods. ★",
            "MSB is sign bit (0=+, 1=−):\n1. Sign-magnitude: MSB=1, rest=magnitude; 8-bit range −127 to +127; two zeros.\n2. 1's complement: invert all bits of +N; range −127 to +127; two zeros.\n3. 2's complement: 1's complement + 1; range −128 to +127; single zero.\n4-bit example for −5: SM=1101, 1's=1010, 2's=1011.",
            listOf("3M: Comparison of Sign-magnitude, 1's & 2's complement (formation, 8-bit range, zeros)", "2M: Sign bit rule & 4-bit example for −5")),
        s(10, 5, t5, "Represent (−25)₁₀ in 8-bit 1's complement form. ★",
            "+25 in 8-bit binary = 0001 1001.\nInvert every bit to get 1's complement = 1110 0110.\n∴ (−25)₁₀ = 11100110 in 8-bit 1's complement.",
            listOf("2M: Correct 8-bit binary of +25 (00011001)", "3M: Bitwise inversion yielding 11100110"),
            f = "+25 = 00011001 → 1's comp (−25) = 11100110"),
        l(5, 5, t5, 10L, "Explain 1's complement representation of signed numbers. Perform subtraction using 1's complement with examples.",
            "Representation: positive uses MSB 0; −N inverts every bit of +N; range −(2^(n−1)−1) to +(2^(n−1)−1); two zeros (+0=00000000, −0=11111111).\nSubtraction A−B: add A + 1's comp(B). If carry=1, add end-around carry → positive result. If no carry → result is negative, take 1's comp for magnitude.\nEx 1 (7−5, 4-bit): 0111 + 1010 = 1 0001 → end-around carry 0001+1 = 0010 (+2).\nEx 2 (5−7, 4-bit): 0101 + 1000 = 1101 (no carry) → −(1's comp 1101 = 0010) = −2.\nDrawbacks: dual zero & end-around carry.",
            listOf("2.5M: Representation, range & dual-zero drawback", "2.5M: 4-step subtraction algorithm (end-around carry rule)", "2.5M: Positive result example (7−5 = +2)", "2.5M: Negative result example (5−7 = −2) & pros/cons")),

        // T6: Objective Q38–Q45
        q(38, 6, t6, "The 2's complement of a binary number is obtained by:", listOf("Inverting bits only", "Adding 1 to its 1's complement", "Subtracting 1", "Shifting right"), 1),
        q(39, 6, t6, "The 2's complement of 0101 is:", listOf("1010", "1011", "1100", "1001"), 1),
        q(40, 6, t6, "An 8-bit 2's complement number can represent:", listOf("−127 to +127", "−128 to +127", "−256 to +255", "0 to 255"), 1),
        q(41, 6, t6, "In 2's complement, zero has:", listOf("Two representations", "One representation", "No representation", "Three representations"), 1),
        q(42, 6, t6, "Which signed representation is used by most modern computers?", listOf("Sign-magnitude", "1's complement", "2's complement", "BCD"), 2),
        q(43, 6, t6, "The 8-bit 2's complement representation of −1 is:", listOf("10000001", "11111110", "11111111", "10000000"), 2),
        q(44, 6, t6, "In 2's complement addition, a carry out of the MSB is:", listOf("Added back", "Discarded", "Stored as sign", "An error always"), 1),
        q(45, 6, t6, "Adding two positive numbers gives a negative result. This indicates:", listOf("Underflow", "Overflow", "Borrow", "Parity error"), 1),
        // T6: Short S11–S12 & Long L6
        s(11, 6, t6, "Find the 2's complement representation of (−45)₁₀ using 8 bits. ★",
            "+45 = 0010 1101.\n1's complement = 1101 0010.\nAdd 1 = 1101 0011.\n∴ (−45)₁₀ = (11010011)₂. Check: 256 − 45 = 211 = 11010011.",
            listOf("1.5M: 8-bit binary of +45 (00101101)", "1.5M: 1's complement (11010010)", "2M: Add 1 → 11010011 & verification"),
            f = "+45=00101101 → 1's=11010010 → +1 = 11010011"),
        s(12, 6, t6, "Why is 2's complement preferred over 1's complement and sign-magnitude? ★",
            "1. Only one representation of zero (00000000).\n2. Addition & subtraction share the exact same adder circuit (A−B = A + (−B)).\n3. No end-around carry needed — final carry is discarded.\n4. Larger negative range (−2^(n−1) to +2^(n−1)−1).\n5. Simple hardware: invert + add 1.",
            listOf("1M each for all 5 advantages")),
        l(6, 6, t6, 12L, "Explain 2's complement representation. Perform addition and subtraction using 2's complement and explain overflow. ★",
            "Representation: +N has MSB 0; −N = 2ⁿ − N = 1's comp + 1; range −2^(n−1) to +2^(n−1)−1 (8-bit: −128..+127); single zero.\nSubtraction A−B: compute A + 2's comp(B). Carry out → discard, result positive. No carry → result negative in 2's comp.\nEx 1 (13−6, 8-bit): 00001101 + 11111010 = 1 00000111 → discard carry → +7.\nEx 2 (6−13, 8-bit): 00000110 + 11110011 = 11111001 (no carry) → 2's comp = 00000111 ⇒ −7.\nOverflow: adding two same-sign numbers yields opposite sign (carry into MSB ≠ carry out). Ex (4-bit): 0111 (+7) + 0101 (+5) = 1100 (−4) ⇒ OVERFLOW.",
            listOf("3M: Definition, formula, range & single-zero property", "3M: Subtraction rule + positive example (13−6 = +7)", "3M: Negative example (6−13 = −7)", "3M: Overflow rule, 4-bit example (+7 + +5) & advantages")),

        // T7: Objective Q46–Q53
        q(46, 7, t7, "Standard ASCII uses how many bits per character?", listOf("5", "7", "8", "16"), 1, exp = "128 characters (0–127)."),
        q(47, 7, t7, "The ASCII value of capital 'A' is:", listOf("60", "65", "97", "48"), 1),
        q(48, 7, t7, "The ASCII value of small 'a' is:", listOf("65", "90", "97", "122"), 2),
        q(49, 7, t7, "The ASCII value of digit '0' is:", listOf("0", "32", "48", "64"), 2),
        q(50, 7, t7, "ASCII stands for:", listOf("American Standard Code for Information Interchange", "American Standard Code for Internet Interchange", "Advanced Standard Code for Information Interchange", "American Symbolic Code for Information Interchange"), 0),
        q(51, 7, t7, "Unicode UTF-8 uses:", listOf("Always 1 byte", "1 to 4 bytes per character", "Always 2 bytes", "Always 4 bytes"), 1),
        q(52, 7, t7, "Extended ASCII uses ______ bits and represents 256 characters.", listOf("7", "8", "10", "16"), 1),
        q(53, 7, t7, "Which standard can represent characters of almost all world languages and emojis?", listOf("ASCII", "EBCDIC", "Unicode", "BCD"), 2),
        // T7: Short S13–S14 & Long L7
        s(13, 7, t7, "Differentiate between ASCII and Unicode. ★",
            "ASCII: American Standard Code for Information Interchange; 7-bit (8-bit extended); 128 (256 ext) characters; English only.\nUnicode: Universal character set; 8/16/32 bits (UTF-8, UTF-16, UTF-32); 1,114,112 code points (U+0000–U+10FFFF); all world languages, symbols & emojis; first 128 code points identical to ASCII.",
            listOf("2M: Full form & bit widths", "2M: Character count & language coverage", "1M: Backward compatibility with ASCII")),
        s(14, 7, t7, "How is the string \"Hi\" stored in memory?",
            "Each character is stored as its 1-byte ASCII code in consecutive bytes:\n'H' = 72 = 01001000 (0x48).\n'i' = 105 = 01101001 (0x69).\nMemory holds 01001000 01101001 in two consecutive byte locations.",
            listOf("2M: Correct ASCII decimal/hex values ('H'=72/48, 'i'=105/69)", "2M: Exact 8-bit binary patterns", "1M: Consecutive byte storage explanation"),
            f = "\"Hi\" → 01001000 (0x48) | 01101001 (0x69)"),
        l(7, 7, t7, 10L, "Explain ASCII and Unicode character encoding and how characters are represented in memory. ★",
            "1. ASCII: 7-bit code (128 chars: 33 control + 95 printable); Extended ASCII is 8-bit (256 chars). Key codes: '0'=48, 'A'=65 (01000001), 'B'=66, 'a'=97 (01100001), space=32 (lower = upper + 32).\n2. Unicode: assigns unique code point U+XXXX to every world character & emoji. Encodings: UTF-8 (1–4 bytes, ASCII-compatible, web standard; 'A'=41, 'अ'=E0 A4 85), UTF-16 (2 or 4 bytes, Java/Windows), UTF-32 (4 bytes fixed).\n3. Storage in memory: consecutive bytes; \"Hello\" = 48 65 6C 6C 6F (5 bytes; C-string appends 00). Other codes: EBCDIC, BCD, Gray code.",
            listOf("3M: Standard & Extended ASCII with key character codes", "4M: Unicode code points & UTF-8 / UTF-16 / UTF-32 comparison", "3M: Memory storage example (\"Hello\" in hex/bytes) & other codes"))
    )
}
